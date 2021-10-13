package com.hfeng.demo.activiti7.test;

import com.hfeng.demo.activiti7.utils.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {
    /**
     * 实现流程定义相关操作
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 实现任务相关操作
     */
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    RepositoryService repositoryService;
    //bpmn文件在/processes/下的时候会自动部署
//    ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("application.yml");
//    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();


    /**
     * 部署流程定义  //bpmn文件在/processes/下的时候会自动部署
     *      要将上边绘制的图形即流程定义（.bpmn）部署在工作流程引擎 activiti 中，方法如下：
     */
    @Test
    public void codeTest1() {
        //部署对象
        Deployment deployment = repositoryService.createDeployment()
                .disableSchemaValidation() //禁用架构验证
                .addClasspathResource("processes/holiday.bpmn") // bpmn文 件
                .addClasspathResource("processes/holiday2.png")  // 图片文件
                .name("请假申请流程")
                .deploy();
        System.out.println("流程部署id:" + deployment.getId());
        System.out.println("流程部署名称:" + deployment.getName());
    }

    /**
     * 查看已部署的流程定义
     */
    @Test
    public void contextLoads() {
        securityUtil.logInAs("system");
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        System.out.println("可用的流程定义数量： " + processDefinitionPage.getTotalItems());
        for (org.activiti.api.process.model.ProcessDefinition pd : processDefinitionPage.getContent()) {
            System.out.println("流程定义：" + pd);
        }
    }

    /**
     * 启动流程实例 -- 真正一个流程启动了
     */
    @Test
    public void testStartProcess() {
        securityUtil.logInAs("system");
        ProcessInstance pi = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("holiday")  //流程定义key
                .build());
        System.out.println("流程实例ID：" + pi.getId());
    }

    /**
     * 查询任务，并完成自己的任务
     */
    @Test
    public void testTask() {
        securityUtil.logInAs("zhangsan");
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage.getTotalItems()>0){
            ///content里面是任务
            for (Task task:taskPage.getContent()){
                //任务的拾取
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                System.out.println("任务："+ task);
                //完成任务
                if(null != taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build()).getId()){
                    System.out.println("完成任务"+task.getName());
                };
            }
        }else{
            System.out.println("没有任务");
        }
        //再次查询新任务
        Page<Task> taskPage2=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage2.getTotalItems() > 0){
            System.out.println("任务："+taskPage2.getContent());
        }
    }
}
