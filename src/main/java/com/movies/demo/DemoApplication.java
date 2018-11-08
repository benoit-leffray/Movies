package com.movies.demo;

import com.movies.demo.batchprocessor.BatchJobs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableWebMvc
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

        // set custom exception handler
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
        BatchJobs batchJobs = (BatchJobs) ctx.getBean("jobs");

        //launchJobs(jobLauncher, batchJobs);
    }

    /**
     * jobs provides the job list in their proper order
     *
     * @return list of jobs in the proper execution order
     */
    @Bean
    public BatchJobs jobs() {
        return new BatchJobs();
    }

    /**
     * launchJobs launches the batch jobs in the proper order
     * @param jobLauncher job launcher
     * @param jobs batch jobs that need to be launched
     */
    private static void launchJobs(JobLauncher jobLauncher, BatchJobs jobs) {

        jobs.getJobs().forEach(job -> {
            try {
                jobLauncher.run(job, new JobParameters());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        });
    }
}