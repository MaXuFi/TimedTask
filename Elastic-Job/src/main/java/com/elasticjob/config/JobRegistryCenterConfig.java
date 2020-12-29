package com.elasticjob.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 根据配置，实例化zookeeper
 * @projectName: TaskScheduling
 * @packageName: com.elasticjob.config
 * @author: llf
 * @createTime: 2020/12/28 11:23
 * @version: 1.0
 */
@Configuration
/**
 * 当括号中的内容为true时，使用该注解的类被实例化
 */
@ConditionalOnExpression("'${elaticjob.zookeeper.server-lists}'.length() > 0")
public class JobRegistryCenterConfig {
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList,
                                             @Value("${elaticjob.zookeeper.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

}
