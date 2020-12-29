package com.elasticjob.example;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.elasticjob.customInterface.ElasticSimpleJob;
import org.springframework.stereotype.Component;

/**
 * @description: 使用自定义注解，simpleJob2测试
 * @projectName: TaskScheduling
 * @packageName: com.elasticjob.example
 * @author: llf
 * @createTime: 2020/12/28 18:25
 * @version: 1.0
 */
@Component
@ElasticSimpleJob(jobName = "MySimpleJob2", cron = "0 0/2 * * * ?",shardingTotalCount = 2, description = "测试自定义注解")
public class MySimpleJob2 implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("||||||||||自定义测试job2");
    }
}
