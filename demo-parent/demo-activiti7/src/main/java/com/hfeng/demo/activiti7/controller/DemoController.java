package com.hfeng.demo.activiti7.controller;

import com.hfeng.demo.activiti7.utils.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: xlf
 * @Date: 2021/06/07/16:28
 * @Description: activiti7接口的演示，后续可以直接使用本代码来调用接口实现流程控制。
 */
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/hello")
    public void hello() {
        //首先，取出项目中的最多 10 个流程定义
        Page<ProcessDefinition> processDefinitionPage =
                processRuntime.processDefinitions(Pageable.of(0, 10));
        if (processDefinitionPage.getTotalItems() > 0) {
            //然后，对取出的流程进行启动
            for (ProcessDefinition definition : processDefinitionPage.getContent()) {
                logger.info("流程定义信息：" + definition);
                //
                processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionId(definition.getId()).build());
            }
        }
        //完成 流程启动后， 由于当前项目中 只有 other.bpmn 一个流程，且该流程在设计时，已分配给 activitiTeam 组
        //因此我们登录一个activitiTeam组成员,该账号信息会被设置到security上下文中，activiti会对其信息进行读取
        //获取当前用户任务，最多 10 个
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        //由于目前只有一个流程，两个任务，我们尝试一下完成一个，看看会发生什么变化
        if (taskPage.getTotalItems() > 0) {
            for (Task task : taskPage.getContent()) {
                logger.info("任务信息：" + task);
                //注意，完成任务前必须先声明

                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                //完成任务

                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build()
                );
            }
        }
        //上一轮任务完成，再看一下，现在流程是否走到了 second？
        Page<Task> taskPage2 = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage2.getTotalItems() > 0) {
            logger.info("任务信息：" + taskPage2.getContent());
        }
    }
}
