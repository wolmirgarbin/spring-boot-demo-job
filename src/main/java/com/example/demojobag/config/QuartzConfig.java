package com.example.demojobag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//@Configuration
//@EnableScheduling
public class QuartzConfig implements SchedulingConfigurer {

    public static ScheduledTaskRegistrar taskRegistrar;

    @Autowired private ExecuteJob executeJob;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("myScheduler-");
        scheduler.setPoolSize(10);
        // block spring context stopping to allow SI pollers to complete
        // (to graceful shutdown still running tasks, without destroying beans used in these tasks)
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(20);
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        QuartzConfig.taskRegistrar = taskRegistrar;

        taskRegistrar.addTriggerTask(
                () -> work(),
                new CronTrigger("*/1 * * * * *"));

        // The second task sits in the scheduler queue and prevents scheduler to shutdown().
        // The task's cancellation lies in taskRegistrar's destroy() method, but no one
        // calls it until context bean destruction is in place. At the same time the context
        // waits for taskScheduler to terminate to continue beans destruction procedure.
        //taskRegistrar.addTriggerTask(
        //        () -> work(),
        //        new CronTrigger("0 0 0 */1 * *"));
    }


    public void updateTriggerTask(MeuJob job) {
        taskRegistrar.getScheduler().schedule(
                () -> executeJob.execute(job),
                new CronTrigger("*/1 * * * * *"));
    }


    public void stopTriggerTask(MeuJob job) {
        //taskRegistrar.getScheduler().forEach(System.out::println);
    }


    void work() {
        //System.out.println("De 1 em 1 minuto");
    }

    /*@Autowired private TaskScheduler executor;


    public void scheduling(final Runnable task) {
        // Schedule a task to run once at the given date (here in 1minute)
        executor.schedule(task, Date.from(LocalDateTime.now().plusMinutes(1)
                .atZone(ZoneId.systemDefault()).toInstant()));

        // Schedule a task that will run as soon as possible and every 1000ms
        executor.scheduleAtFixedRate(task, 1000);

        // Schedule a task that will first run at the given date and every 1000ms
        executor.scheduleAtFixedRate(task, Date.from(LocalDateTime.now().plusMinutes(1)
                .atZone(ZoneId.systemDefault()).toInstant()), 1000);

        // Schedule a task that will run as soon as possible and every 1000ms after the previous completion
        executor.scheduleWithFixedDelay(task, 1000);

        // Schedule a task that will run as soon as possible and every 1000ms after the previous completion
        executor.scheduleWithFixedDelay(task, Date.from(LocalDateTime.now().plusMinutes(1)
                .atZone(ZoneId.systemDefault()).toInstant()), 1000);

        // Schedule a task with the given cron expression
        executor.schedule(task, new CronTrigger("*5 * * * * MON-FRI"));
    }*/

}
