package com.movies.demo.batchprocessor;


import lombok.Getter;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter

public class BatchJobs {
    @Autowired
    private List<Job> jobs;

    /*public List<Job> getJobs() {
        return jobs;
    }*/
}