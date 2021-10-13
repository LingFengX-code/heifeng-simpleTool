//package com.hfeng.xboot.activiti7.test;
//
//
//import org.activiti.engine.*;
//import org.activiti.engine.history.HistoricActivityInstance;
//import org.activiti.engine.history.HistoricActivityInstanceQuery;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.repository.ProcessDefinitionQuery;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @Author: xlf
// * @Date: 2021/06/07/14:39
// * @Description:    与spring整合的代码示例
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class Activiti7CodeDemo {
//    ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("application.yml");
//    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//
//    /**
//     * 部署流程定义：
//     * 部署流程定义就是要将上边绘制的图形即流程定义（.bpmn）部署在工作流程引擎 activiti 中，方法如下：
//     */
//    @Test
//    public void codeTest1() {
//        // 获取repositoryService
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        //也可以通过input/io拿到
////        // bpmn输入流
////        InputStream inputStream_bpmn = this
////                .getClass()
////                .getClassLoader()
////                .getResourceAsStream(
////                        "diagram/holiday.bpmn");
////        // 图片输入流
////        InputStream inputStream_png = this
////                .getClass()
////                .getClassLoader()
////                .getResourceAsStream(
////                        " diagram/holiday2.png");
//        //部署对象
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("processes/holiday.bpmn") // bpmn文 件
//                .addClasspathResource("processes/holiday2.png")  // 图片文件
//                .name("请假申请流程")
//                .deploy();
//        System.out.println("流程部署id:" + deployment.getId());
//        System.out.println("流程部署名称:" + deployment.getName());
//    }
//
//    /**
//     * 启动一个流程实例
//     *   流程定义部署在 activiti 后就可以通过工作流管理业务流程了，也就是说上边部署的请假申请流程可以使用了。
//     * 针对该流程，启动一个流程表示发起一个新的请假申请单，这就相当于 java 类与 java 对象的关系，类定义好
//     * 后需要 new 创建一个对象使用，当然可以 new 多个对象。对于请假申请流程，张三发起一个请假申请单需要启
//     * 动一个流程实例，请假申请单发起一个请假单也需要启动一个流程实例。
//     */
//    @Test
//    public void codeTest2() {
//        // 获取RunTimeService
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        // 根据流程定义key启动流程 -- 此key为自定义的
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayTest1");
//        System.out.println(" 流程定义 id ： " +processInstance.getProcessDefinitionId());
//        System.out.println("流程实例id：" + processInstance.getId());
//        System.out.println(" 当前活动 Id ： " +processInstance.getActivityId());
//    }
//
//    /**
//     * 任务查询
//     *   流程启动后，各个任务的负责人就可以查询自己当前需要处理的任务，查询出来的任务都是该用户
//     * 的待办任务。
//     */
//    @Test
//    public void codeTest3() {
//        // 任务负责人
//        String assignee = "zhangsan";
//        // 创建TaskService
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> list = taskService.createTaskQuery()//
//                .processDefinitionKey("holidayTest1")//
//                .taskAssignee(assignee)//只查询该任务负责人的任务
//                .list();
//        for (Task task : list) {
//            System.out.println(" 流 程 实 例 id ： " + task.getProcessInstanceId());
//            System.out.println("任务id：" + task.getId());
//            System.out.println("任务负责人：" + task.getAssignee());
//            System.out.println("任务名称：" + task.getName());
//        }
//    }
//
//    /**
//     * 任务处理
//     *   任务负责人查询待办任务，选择任务进行处理，完成任务
//     */
//    @Test
//    public void codeTest4() {
//        //任务id：任务负责人待办任务中查询出来的
//        String taskId = "8305";
//        // 创建TaskService
//        TaskService taskService = processEngine.getTaskService();
//        //完成任务
//        taskService.complete(taskId);
//        System.out.println("完成任务id="+taskId);
//    }
//
//    /*---------------------以下为工作流的其他操作代码--------------------*/
//    /**
//     * 流程定义查询
//     */
//    @Test
//    public void queryProceccDefinition() {
//        // 流程定义key
//        String processDefinitionKey = "holiday";
//        // 获取repositoryService
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        // 查询流程定义
//        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
//        //遍历查询结果
//        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(processDefinitionKey).orderByProcessDefinitionVersion().desc().list();
//        for (ProcessDefinition processDefinition : list) {
//            System.out.println("------------------------");
//            System.out.println("流程部署id： " + processDefinition.getDeploymentId());
//            System.out.println("流程定义id：" + processDefinition.getId());
//            System.out.println("流程定义名称：" + processDefinition.getName());
//            System.out.println("流程定义key：" + processDefinition.getKey());
//            System.out.println("流程定义版本：" + processDefinition.getVersion());
//        }
//    }
//
//    /**
//     * 流程定义的删除
//     * 删除已经部署成功的流程定义
//     */
//    @Test
//    public void deleteDeployment() {
//        // 流程部署id
//        String deploymentId = "8801";
//        // 通过流程引擎获取repositoryService
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        //删除流程定义，如果该流程定义已有流程实例启动则删除时出错
//        repositoryService.deleteDeployment(deploymentId);
//        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
//        //repositoryService.deleteDeployment(deploymentId, true);
//    }
//
//    /**
//     * 流程历史信息的查看
//     */
//    @Test
//    public void QueryHistoric(){
//        HistoryService historyService = processEngine.getHistoryService();
//        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery();
//        query.processInstanceId("1501");
//        List<HistoricActivityInstance> list = query.list();
//        for(HistoricActivityInstance ai :list) {
//            System.out.println(ai.getActivityId());
//            System.out.println(ai.getActivityName());
//            System.out.println(ai.getProcessDefinitionId());
//            System.out.println(ai.getProcessInstanceId());
//            System.out.println("==============================");
//        }
//    }
//
//}
