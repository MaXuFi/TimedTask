package com.project.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义测试userJob
 * @projectName: TaskScheduling
 * @packageName: com.project.job
 * @author: llf
 * @createTime: 2021/1/5 15:27
 * @version: 1.0
 */
@Component
public class UserJob {

    @XxlJob("userJobHandler")
    public ReturnT<String> userJobHandler(String param) throws Exception{
        XxlJobHelper.log("任务日志输出");
        System.out.println("自定义userJob执行");
        return ReturnT.SUCCESS;
    }

}
