package com.movies.demo.batchprocessor.title;

import com.movies.demo.DTOs.TitleDto;
import com.movies.demo.batchprocessor.TsvTokenizer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class TitleBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;

    @Value("${batchprocessor.chunksize:100}")
    int chunkSize;

    @Autowired
    public TitleBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<TitleSource> tsvTitleReader() {
        FlatFileItemReader<TitleSource> reader = new FlatFileItemReader<TitleSource>();

        reader.setResource(new ClassPathResource("title.basics.tsv"));

        reader.setLineMapper(new DefaultLineMapper<TitleSource>() {{
            setLineTokenizer(new TsvTokenizer() {{
                setNames(new String[] {"id", "type", "primaryTitle", "originalTitle", "isAdult", "startYear", "endYear", "runtimeMinutes", "genres"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TitleSource>() {{
                setTargetType(TitleSource.class);
            }});
        }});

        return reader;
    }

    @Bean
    ItemProcessor<TitleSource, TitleDto> tsvTitleProcessor() {
        return new TitleProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TitleDto> tsvTitleWriter() {
        JdbcBatchItemWriter<TitleDto> tsvWriter = new JdbcBatchItemWriter<TitleDto>();
        tsvWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TitleDto>());
        tsvWriter.setSql("INSERT INTO title (id, type, primary_title, original_title, is_adult, start_year, end_year, runtime_minutes, genres) "
                + "VALUES (:id, :type, :primaryTitle, :originalTitle, :isAdult, :startYear, :endYear, :runtimeMinutes, :genres)");

        tsvWriter.setDataSource(dataSource);
        return tsvWriter;
    }

    @Bean
    public Step tsvTitleFileToDataBaseStep() {
        return stepBuilderFactory.get("tsvTitleFileToDatabaseStep")
                .<TitleSource, TitleDto>chunk(chunkSize)
                .reader(tsvTitleReader())
                .processor(tsvTitleProcessor())
                .writer(tsvTitleWriter())
                .build();
    }

    @Order(1)
    @Bean
    Job csvTitleFileToDatabaseJob(TitleJobCompletionListener listener) {
        return jobBuilderFactory.get("tsvTitleFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(tsvTitleFileToDataBaseStep())
                .end()
                .build();
    }
}
