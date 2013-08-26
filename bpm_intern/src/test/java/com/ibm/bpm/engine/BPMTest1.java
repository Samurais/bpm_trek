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
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ibm.bpm.engine.XmlUtils;

public class BPMTest1 {
//	RepositoryService repositoryService;
//	RepositoryService repositoryService2;
	public void generateEngine1() {
		//´´½¨Activiti ÒýÇæ
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"diagrams/activiti.cfg.xml").buildProcessEngine();

		RepositoryService repositoryService = processEngine.getRepositoryService();

		XMLWriter writer = null;
        SAXReader reader = new SAXReader();

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");

    	TaskService taskService = processEngine.getTaskService();
    	List<Task> tasks2 = taskService.createTaskQuery().taskAssignee("Fozzie").list();
		for (Task task2 : tasks2) {
			System.out.println("Task available: " + task2.getName());
		}   

		Task task2 = tasks2.get(0);
	      
		Map<String, Object> taskVariables2 = new HashMap<String, Object>();	
	    String filePath2 = System.getProperty("user.dir")+"/src/main/resources/diagrams/taskVarible2.xml";
        File file2 = new File(filePath2);
        if (file2.exists()) {
            Document document;
			try {
				document = reader.read(file2);
				taskVariables2 = XmlUtils.Dom2Map(document);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        taskService.complete(task2.getId(), taskVariables2);
		
    //    RuntimeService runtimeService = processEngine.getRuntimeService();
  //      String procId = runtimeService.startProcessInstanceByKey("vacationRequest").getId(); 
        ManagementService managementService = processEngine.getManagementService();
		IdentityService identityService = processEngine.getIdentityService();
		HistoryService historyService = processEngine.getHistoryService();
		FormService formService = processEngine.getFormService();
		//ProcessEngines.destroy();
	//HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
	//System.out.println("Process instance end time: " + historicProcessInstance.getEndTime()); 
	}
	

	public static void main(String[] args){
		BPMTest1 bpm = new BPMTest1();
		bpm.generateEngine1();
	}
	
}
