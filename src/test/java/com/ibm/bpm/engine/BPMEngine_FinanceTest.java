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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class BPMEngine_FinanceTest {
	public void generateEngine() {
		//����Activiti ����
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"diagrams/activiti.cfg.xml").buildProcessEngine();		
		//���Activiti����
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//��������
		Deployment deployment = 	repositoryService.createDeployment()
		  .addClasspathResource("diagrams/FinancialReportProcess.bpmn20.xml")
		  .deploy();
		
	      
		RuntimeService runtimeService = processEngine.getRuntimeService();

		String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();
		//�ж������Ƿ����
		
		
		
		
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
		
	}
	
	public static void main(String[] args){
		BPMEngine_FinanceTest bpm = new BPMEngine_FinanceTest();
		bpm.generateEngine();
	}
	

}
