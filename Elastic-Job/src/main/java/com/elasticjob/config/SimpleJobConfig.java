package com.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.elasticjob.customInterface.ElasticSimpleJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 动态添加Job任务配置
 * @projectName: TaskScheduling
 * @packageName: com.elasticjob.config
 * @author: llf
 * @createTime: 2020/12/28 18:22
 * @version: 1.0
 */
@Configuration
public class SimpleJobConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initElasticJob() {

        Map<String, SimpleJob> map = applicationContext.getBeansOfType(SimpleJob.class);

        for (Map.Entry<String, SimpleJob> entry : map.entrySet()) {
            SimpleJob simpleJob = entry.getValue();
            if (AopUtils.isAopProxy(simpleJob)) {
                try {
                    simpleJob = (SimpleJob) ((Advised) simpleJob).getTargetSource().getTarget();
                } catch (Exception e) {
                    throw new RuntimeException("==>代理类转换异常!", e);
                }
            }

            ElasticSimpleJob elasticSimpleJobAnnotation = simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);

            if (null != elasticSimpleJobAnnotation) {
                String cron = StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.cron(),
                        elasticSimpleJobAnnotation.value());
                String jobName = StringUtils.isBlank(elasticSimpleJobAnnotation.jobName()) ? simpleJob
                        .getClass().getName() : elasticSimpleJobAnnotation.jobName();
                boolean overwrite = elasticSimpleJobAnnotation.overwrite() ? true : false;
                boolean monitorExecution = elasticSimpleJobAnnotation.monitorExecution() ? true : false;

                //SimpleJob任务配置
                SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(
                        JobCoreConfiguration
                                .newBuilder(jobName, cron, elasticSimpleJobAnnotation.shardingTotalCount())
                                .shardingItemParameters(elasticSimpleJobAnnotation.shardingItemParameters())
                                .description(elasticSimpleJobAnnotation.description())
                                .failover(elasticSimpleJobAnnotation.failover())
                                .jobParameter(elasticSimpleJobAnnotation.jobParameter()).build(), simpleJob
                        .getClass().getCanonicalName());
                LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                        .newBuilder(simpleJobConfiguration).overwrite(overwrite)
                        .monitorExecution(monitorExecution).build();

//                //配置数据源
//                String dataSourceRef = elasticSimpleJobAnnotation.dataSource();
//                if (StringUtils.isNotBlank(dataSourceRef)) {
//
//                    if (!applicationContext.containsBean(dataSourceRef)) {
//                        throw new RuntimeException("not exist datasource [" + dataSourceRef + "] !");
//                    }
//
//                    DataSource dataSource = (DataSource) applicationContext.getBean(dataSourceRef);
//                    JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(
//                        dataSource);
//                    SpringJobScheduler jobScheduler = new SpringJobScheduler(simpleJob, regCenter,
//                        liteJobConfiguration, jobEventRdbConfiguration);
//                    jobScheduler.init();
//                }

                SpringJobScheduler jobScheduler = new SpringJobScheduler(simpleJob, regCenter,
                        liteJobConfiguration);
                jobScheduler.init();

            }
        }
    }
}
