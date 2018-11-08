package com.movies.demo.batchprocessor.person;

import com.movies.demo.DTOs.PersonDto;
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
public class PersonBatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;

    @Value("${batchprocessor.chunksize:100}")
    int chunkSize;

    @Autowired
    public PersonBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<PersonSource> tsvPersonReader() {
        FlatFileItemReader<PersonSource> reader = new FlatFileItemReader<PersonSource>();

        reader.setResource(new ClassPathResource("name.basics.tsv"));

        reader.setLineMapper(new DefaultLineMapper<PersonSource>() {{
            setLineTokenizer(new TsvTokenizer() {{
                setNames(new String[] {"id", "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonSource>() {{
                setTargetType(PersonSource.class);
            }});
        }});

        return reader;
    }

    @Bean
    ItemProcessor<PersonSource, PersonDto> tsvPersonProcessor() {
        return new PersonProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<PersonDto> tsvPersonWriter() {
        JdbcBatchItemWriter<PersonDto> tsvWriter = new JdbcBatchItemWriter<PersonDto>();
        tsvWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PersonDto>());
        tsvWriter.setSql("INSERT INTO person (id, primary_name, birth_year, death_year) VALUES (:id, :primaryName, :birthYear, :deathYear)");

        tsvWriter.setDataSource(dataSource);
        return tsvWriter;
    }

    @Bean
    public Step tsvPersonFileToDBStep() {
        return stepBuilderFactory.get("tsvFPersonFileToDatabaseStep")
                .<PersonSource, PersonDto>chunk(chunkSize)
                .reader(tsvPersonReader())
                .processor(tsvPersonProcessor())
                .writer(tsvPersonWriter())
                .build();
    }

    @Order(2)
    @Bean
    Job tsvPersonFileToDatabaseJob(PersonJobCompletionListener listener) {
        return jobBuilderFactory.get("tsvPersonFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(tsvPersonFileToDBStep())
                .end()
                .build();
    }
}