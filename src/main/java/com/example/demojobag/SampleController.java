package com.example.demojobag;

import com.example.demojobag.quartzspring.jobs.SampleJob2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    //@Autowired private QuartzConfig executeJob;
    @Autowired private Scheduler scheduler;


    @GetMapping("/")
    public String ativar(@RequestParam("id") String id) {

        /*MeuJob meuJob = new MeuJob();
        meuJob.setId( id );

        executeJob.updateTriggerTask( meuJob );

        executeJob.stopTriggerTask( meuJob );*/

        return "ativado";
    }


    @GetMapping("/start/{nome}/{segundos}")
    public String start(@PathVariable("nome") String nome, @PathVariable("segundos") int segundos) throws SchedulerException {

        JobDetail job = JobBuilder.newJob(SampleJob2.class)
                .usingJobData("nome", "Wolmir Garbin")
                .withIdentity("job"+ nome, "grupo01")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger"+ nome,"grupo01")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/"+ segundos +" * * * * ?"))
                .build();

        scheduler.scheduleJob(job, trigger);

        return "Beleza!";
    }

    @GetMapping("/stop/{nome}")
    public String stop(@PathVariable("nome") String nome) throws SchedulerException {

        scheduler.interrupt( JobKey.jobKey("job"+ nome,"grupo01") );

        return "Parou!";
    }

    @GetMapping("/showAll")
    public String show() throws SchedulerException {
        System.out.println( "getJobGroupNames:"+ scheduler.getJobGroupNames().toString() );

        System.out.println( "getCalendarNames:"+ scheduler.getCalendarNames() );

        System.out.println( "getJobKeys:"+ scheduler.getJobKeys( GroupMatcher.anyGroup() ) );

        System.out.println( scheduler.getCurrentlyExecutingJobs() );

        return "Ver no log";
    }

}
