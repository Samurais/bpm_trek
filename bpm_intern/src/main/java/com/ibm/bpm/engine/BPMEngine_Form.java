package com.ibm.bpm.engine;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class BPMEngine_Form {

	public void generateEngine() {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"diagrams/activiti.cfg.xml").buildProcessEngine();		
		RepositoryService repositoryService = processEngine.getRepositoryService();
//		Deployment deployment = 	repositoryService.createDeployment()
//		  .addClasspathResource("diagrams/VacationRequest_deprecated_forms.bpmn20.xml")
//		  .deploy();
	//	System.out.println(repositoryService.getDeploymentResourceNames("diagrams/VacationRequest_deprecated_forms.bpmn20.xml").size());
	//	ProcessDefinition pdf = repositoryService.getProcessDefinition(deployment.getId());
	     
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		RuntimeService runtimeService = processEngine.getRuntimeService();

		String procId = runtimeService.startProcessInstanceByKey("vacationRequest_deprecated_forms",variables).getId();
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());   
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
		
	}
	
	public static void main(String[] args){
		BPMEngine_Form bpm = new BPMEngine_Form();
		bpm.generateEngine();
	}
	
}
