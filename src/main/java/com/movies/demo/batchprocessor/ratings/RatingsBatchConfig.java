package com.movies.demo.batchprocessor.ratings;

import com.movies.demo.DTOs.RatingsDto;
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
public class RatingsBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;

    @Value("${batchprocessor.chunksize:100}")
    int chunkSize;

    @Autowired
    public RatingsBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<RatingsSource> tsvRatingsReader() {
        FlatFileItemReader<RatingsSource> reader = new FlatFileItemReader<RatingsSource>();

        reader.setResource(new ClassPathResource("title.ratings.tsv"));

        reader.setLineMapper(new DefaultLineMapper<RatingsSource>() {{
            setLineTokenizer(new TsvTokenizer() {{
                setNames(new String[] {"id", "averageRating", "numVotes"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<RatingsSource>() {{
                setTargetType(RatingsSource.class);
            }});
        }});

        return reader;
    }

    @Bean
    ItemProcessor<RatingsSource, RatingsDto> tsvRatingsProcessor() {
        return new RatingsProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<RatingsDto> tsvRatingsWriter() {
        JdbcBatchItemWriter<RatingsDto> tsvWriter = new JdbcBatchItemWriter<RatingsDto>();
        tsvWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<RatingsDto>());
        tsvWriter.setSql("INSERT INTO ratings (title_id, average_rating, num_votes) VALUES (:id, :averageRating, :numVotes)");

        tsvWriter.setDataSource(dataSource);
        return tsvWriter;
    }

    @Bean
    public Step tsvRatingsFileToDataBaseStep() {
        return stepBuilderFactory.get("tsvRatingsFileToDatabaseStep")
                .<RatingsSource, RatingsDto>chunk(chunkSize)
                .reader(tsvRatingsReader())
                .processor(tsvRatingsProcessor())
                .writer(tsvRatingsWriter())
                .build();
    }

    @Order(3)
    @Bean
    Job tsvRatingsFileToDatabaseJob(RatingsJobCompletionListener listener) {
        return jobBuilderFactory.get("tsvRatingsFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(tsvRatingsFileToDataBaseStep())
                .end()
                .build();
    }
}