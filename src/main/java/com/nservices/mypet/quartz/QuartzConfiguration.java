package com.nservices.mypet.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
@Slf4j
public class QuartzConfiguration {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(CustomJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Custom Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Trigger")
                .withDescription("Sample trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(1))
                .build();
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();

        log.info(scheduler.getJobKeys(GroupMatcher.anyGroup()).toString());
        scheduler.start();
        //scheduler.scheduleJob(job, trigger);
        //scheduler.start();
        return scheduler;
    }
}
