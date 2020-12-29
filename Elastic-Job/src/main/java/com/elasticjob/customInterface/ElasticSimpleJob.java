package com.elasticjob.customInterface;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 自定义elastic-job注解
 * @projectName: TaskScheduling
 * @packageName: com.elasticjob.customInterface
 * @author: llf
 * @createTime: 2020/12/28 18:17
 * @version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSimpleJob {

    @AliasFor("cron")
    public abstract String value() default "";

    /**
     * cron表达式
     */
    @AliasFor("value")
    public abstract String cron() default "";

    /**
     * job名称
     */
    public abstract String jobName() default "";

    /**
     * 总分片数
     */
    public abstract int shardingTotalCount() default 1;

    /**
     * 分片参数
     */
    public abstract String shardingItemParameters() default "";

    /**
     * 任务参数
     */
    public abstract String jobParameter() default "";

    /**
     * 数据源
     */
    public abstract String dataSource() default "";

    /**
     * 信息描述
     */
    public abstract String description() default "";

    /**
     * 是否不可用
     */
    public abstract boolean disabled() default false;

    /**
     * 是否重写
     */
    public abstract boolean overwrite() default false;

    /**
     * 是否快速失败
     */
    public abstract boolean failover() default true;

    /**
     * 幂等机制，来确保同一条数据不会被多个Job同时处理，避免同一条数据被同一个Job实例的多个线程处理
     */
    public abstract boolean monitorExecution() default true;
}
