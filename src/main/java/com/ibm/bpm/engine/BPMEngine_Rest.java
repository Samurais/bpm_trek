package com.ibm.bpm.engine;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class BPMEngine_Rest {
	//rest API 
	public void generateEngine() {
		ProcessEngineConfiguration
		.createProcessEngineConfigurationFromResource(
				"diagrams/activiti.cfg.xml").buildProcessEngine();
				
		ProcessEngine processEngine= ProcessEngines .getDefaultProcessEngine(); 
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().name("VacationRequest.bpmn20.xml").deploy();//rest api
	      
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().listPage(0, 1));   
		
		
		XMLWriter writer = null;
        SAXReader reader = new SAXReader();

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");

        Map<String, Object> variables = new HashMap<String, Object>();
        String filePath = System.getProperty("user.dir")+"/src/main/resources/diagrams/variables.xml";
        File file = new File(filePath);
        if (file.exists()) {
            Document document;
			try {
				document = reader.read(file);
				variables = XmlUtils.Dom2Map(document);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
        }
        IdentityService identityService = processEngine.getIdentityService();
        
		String groupId = "accountancy";
/*		String type = "accountancy-role"; //如果type是assignment可以list到 页面的队列里
        
        User user = identityService.newUser("johndoe");
        user.setPassword("xxx");
        user.setFirstName("John");
    	user.setLastName("Doe");
    	user.setEmail("johndoe@alfresco.com");
        identityService.saveUser(user);
        
        Group group = identityService.newGroup(groupId);
        group.setType(type);
        identityService.saveGroup(group);
     
        identityService.createMembership("johndoe", groupId);*/
        
     //   identityService.createMembership("kermit", groupId);
        
        
        
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);
		System.out.println("pdf id is "+processInstance.getProcessDefinitionId());
		String message = "Number of process instances: " + runtimeService.createProcessInstanceQuery().count();
		System.out.println(message);      
		   
		// Fetch all tasks for the management group
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		for (Task task : tasks) {
			System.out.println("Task available: " + task.getName());
		}   

		Task task = tasks.get(0);

	      
		Map<String, Object> taskVariables = new HashMap<String, Object>();	
	    String filePath1 = System.getProperty("user.dir")+"/src/main/resources/diagrams/taskVariables.xml";
        File file1 = new File(filePath1);
        if (file1.exists()) {
            Document document;
			try {
				document = reader.read(file1);
				taskVariables = XmlUtils.Dom2Map(document);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		

		taskService.complete(task.getId(), taskVariables);
		
		
		ManagementService managementService = processEngine.getManagementService();
		
		
		HistoryService historyService = processEngine.getHistoryService();
		FormService formService = processEngine.getFormService();
		
		
	//	HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
	//	System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());  
		            
	}
	public static void main(String[] args){
		BPMEngine_Rest bpm = new BPMEngine_Rest();
		bpm.generateEngine();
	}
	
}
