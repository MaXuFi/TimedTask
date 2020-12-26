package com.springtask.example;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description: SpringTask注解方式完成定时示例
 * @projectName: TaskScheduling
 * @packageName: com.springtask.example
 * @author: llf
 * @createTime: 2020/12/24 23:15
 * @version: 1.0
 */
@Component
@Async
public class TestTask {

    /**
     * 使用cron属性可按照指定时间执行，本例指的是每1分钟执行一次；
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void test1() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "-----------test1-----------");
    }

    /**
     * 使用cron属性可按照指定时间执行，本例指的是每1分钟执行一次；
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void test2() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "-----------test2-----------");
    }

}
