package com.executorservice.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 为解决Timer单线程问题，引入线程池，每一个被调度的任务都会被线程池中的一个线程去执行，因此任务可以并发执行
 * @projectName: TaskScheduling
 * @packageName: com.executorservice.examples
 * @author: llf
 * @createTime: 2020/12/24 14:54
 * @version: 1.0
 */
public class ScheduledExecutorServiceTest implements Runnable {

    private String jobName = "";

    public ScheduledExecutorServiceTest(String jobName) {
        super();
        this.jobName = jobName;
    }
    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        long initialDelay1 = 1;
        long period1 = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(
                new ScheduledExecutorServiceTest("job1"), initialDelay1,
                period1, TimeUnit.SECONDS);

        long initialDelay2 = 1;
        long delay2 = 1;
        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        service.scheduleWithFixedDelay(
                new ScheduledExecutorServiceTest("job2"), initialDelay2,
                delay2, TimeUnit.SECONDS);
    }
}
