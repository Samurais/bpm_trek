package com.ibm.bpm.engine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;

public class TwoDeployment {
	public void generateEngine() {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"diagrams/activiti.cfg.xml").buildProcessEngine();		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		  .addClasspathResource("diagrams/VacationRequest.bpmn20.xml")
		  .deploy();
		
		repositoryService.createDeployment()
		  .addClasspathResource("diagrams/VacationRequest.bpmn20.xml")
		  .deploy();

	      
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());   
	}

}
