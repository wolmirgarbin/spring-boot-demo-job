package com.example.demojobag.quartzspring.jobs;

import com.example.demojobag.quartzspring.TesteService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {

    @Autowired
    private TesteService testeService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println( context.getJobDetail().getKey().getName() +": "+ context.getJobDetail().getJobDataMap().get("nome") );

        testeService.executeSampleJob();
    }

}