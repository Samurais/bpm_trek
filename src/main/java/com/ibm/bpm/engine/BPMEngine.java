package com.ibm.bpm.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import java.util.Arrays;
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
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class BPMEngine {

	public void generateEngine() throws FileNotFoundException {
		FileInputStream ifs = new FileInputStream(new File(
				"src/main/resources/diagrams/activiti.cfg.xml"));

		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromInputStream(ifs)
				.buildProcessEngine();
		ProcessEngine processEngine1 = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();

		String configFile = new String(
				"src/main/resources/diagrams/VacationRequest.bpmn20.xml");
		Deployment deployment = repositoryService.createDeployment()
				.addInputStream(configFile, new FileInputStream(configFile))
				.deploy();

		// repositoryService.createDeployment()
		// .addClasspathResource("diagrams/Copy (2) of VacationRequest.bpmn20.xml")
		// .deploy();

		// repositoryService.createDeployment()
		// .addClasspathResource("diagrams/Copy (2) of VacationRequest.bpmn20.xml")
		// .deploy();

		System.out.println("Number of process definitions: "
				+ repositoryService.createProcessDefinitionQuery().count());

		XMLWriter writer = null;
		SAXReader reader = new SAXReader();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");

		Map<String, Object> variables = new HashMap<String, Object>();
		String filePath = System.getProperty("user.dir")
				+ "/src/main/resources/diagrams/variables.xml";
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
		// IdentityService identityService = processEngine.getIdentityService();
		//
		// String groupId = "accountancy";
		// User user = identityService.newUser("johndoe");
		// user.setPassword("xxx");
		// user.setFirstName("John");
		// user.setLastName("Doe");
		// user.setEmail("johndoe@alfresco.com");
		// identityService.saveUser(user);
		// String type = "accountancy-role";
		//
		// Group group = identityService.newGroup(groupId);
		// group.setType(type);
		// identityService.saveGroup(group);
		//
		// identityService.createMembership("johndoe", groupId);

		// identityService.createMembership("kermit", groupId);

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationRequest", variables);
		System.out.println("pdf id is "
				+ processInstance.getProcessDefinitionId());
		// Verify that we started a new process instance
		String message = "Number of process instances: "
				+ runtimeService.createProcessInstanceQuery().count();
		System.out.println(message);

		// Fetch all tasks for the management group
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(
				"management").list();
		for (Task task : tasks) {
			System.out.println("Task available: " + task.getName());
		}

		Task task = tasks.get(0);

		Map<String, Object> taskVariables = new HashMap<String, Object>();
		String filePath1 = System.getProperty("user.dir")
				+ "/src/main/resources/diagrams/taskVariables.xml";
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

		// Map<String, Object> variables_test = new HashMap<String, Object>();
		// String filePath_test =
		// System.getProperty("user.dir")+"/src/main/resources/diagrams/variables_test.xml";
		// File file_test = new File(filePath_test);
		// if (file_test.exists()) {
		// Document document;
		// try {
		// document = reader.read(file_test);
		// variables_test = XmlUtils.Dom2Map(document);
		// } catch (DocumentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// ProcessInstance processInstance_test =
		// runtimeService.startProcessInstanceByKey("vacationRequest_test",
		// variables_test);
		//
		// String messaget = "Number of process instances: " +
		// runtimeService.createProcessInstanceQuery().count();
		// System.out.println(messaget);
		//		   
		// // Fetch all tasks for the management group
		//		
		// List<Task> taskst =
		// taskService.createTaskQuery().taskCandidateGroup("management").list();
		// for (Task taskt : taskst) {
		// System.out.println("Task_test available: " +
		// taskt.getName()+"_test");
		// }
		//
		// Task taskt = taskst.get(0);
		//	      
		// Map<String, Object> taskVariables_test = new HashMap<String,
		// Object>();
		// String filePatht =
		// System.getProperty("user.dir")+"/src/main/resources/diagrams/taskVariables_test.xml";
		// File filet = new File(filePatht);
		// if (filet.exists()) {
		// Document document;
		// try {
		// document = reader.read(filet);
		// taskVariables_test = XmlUtils.Dom2Map(document);
		// } catch (DocumentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//		
		//
		// taskService.complete(taskt.getId(), taskVariables_test);

		ManagementService managementService = processEngine
				.getManagementService();

		HistoryService historyService = processEngine.getHistoryService();
		FormService formService = processEngine.getFormService();

		// HistoricProcessInstance historicProcessInstance =
		// historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		// System.out.println("Process instance end time: " +
		// historicProcessInstance.getEndTime());

	}

	public static void main(String[] args) {
		BPMEngine bpm = new BPMEngine();
		try {
			bpm.generateEngine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
