package com.ibm.bpm.engine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;

public class TwoDeployment {
	public void generateEngine() {
		//创建Activiti 引擎
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"diagrams/activiti.cfg.xml").buildProcessEngine();		
		//获得Activiti服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//部署流程
		repositoryService.createDeployment()
		  .addClasspathResource("diagrams/VacationRequest.bpmn20.xml")
		  .deploy();
		
		repositoryService.createDeployment()
		  .addClasspathResource("diagrams/VacationRequest.bpmn20.xml")
		  .deploy();

	      
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());   
	}

}
