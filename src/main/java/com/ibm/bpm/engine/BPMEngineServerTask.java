package com.ibm.bpm.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class BPMEngineServerTask {
	public void generateEngine() {

	ProcessEngine processEngine = ProcessEngineConfiguration
			.createProcessEngineConfigurationFromResource(
					"diagrams/activiti.cfg.xml").buildProcessEngine();	
	
	 RepositoryService repositoryService=processEngine.getRepositoryService();
//	 Deployment deployment = repositoryService.createDeployment()
//			  .addClasspathResource("diagrams/calculator.bpmn20.xml")
//		  .deploy();
	
	       RuntimeService runtimeService  = processEngine.getRuntimeService();   
	        //IdentityService identityService=processEngine.getIdentityService();   
	            
	        Map<String,Object> map=new HashMap<String,Object>();   
	        map.put("input1", 2);    
	        map.put("input2", 3);   
	        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process5", map);
	     
	           
	        TaskService taskService = processEngine.getTaskService();   
	        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
			for (Task task : tasks) {
				System.out.println("Task available: " + task.getName());
			}   

			Task task = tasks.get(0);
			taskService.claim(task.getId(), "kermit");
			taskService.complete(task.getId());
			
 
	}
	public static void main(String[] args){
		BPMEngineServerTask bpm = new BPMEngineServerTask();
		bpm.generateEngine();
	}

}
