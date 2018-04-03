package com.example.demojobag.samplequartz;

import org.quartz.CronScheduleBuilder;
  import org.quartz.JobBuilder;
  import org.quartz.JobDetail;
  import org.quartz.Scheduler;
  import org.quartz.SchedulerException;
  import org.quartz.SchedulerFactory;
  import org.quartz.Trigger;
  import org.quartz.TriggerBuilder;
  import org.quartz.impl.StdSchedulerFactory;
   
   
public class QuartzApp {

     public static void main(String[] args) {
           SchedulerFactory shedFact = new StdSchedulerFactory();

           try {
                  Scheduler scheduler = shedFact.getScheduler();
                  scheduler.start();

                  JobDetail job = JobBuilder.newJob(MyJob.class)
                                .usingJobData("nome", "Wolmir Garbin")
                                .withIdentity("validadorJOB", "grupo01")
                                .build();

                  Trigger trigger = TriggerBuilder.newTrigger()
                                .withIdentity("validadorTRIGGER","grupo01")
                                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                                .build();

                  scheduler.scheduleJob(job, trigger);

           } catch (SchedulerException e) {
                  e.printStackTrace();
           }
     }

}