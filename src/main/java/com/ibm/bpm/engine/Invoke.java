package com.ibm.bpm.engine;

import org.activiti.engine.delegate.DelegateExecution;

public class Invoke implements org.activiti.engine.delegate.JavaDelegate{

	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		 Thread.sleep(10000);  
	        //System.out.println("variavles=" + execution.getVariables());  
	     //   execution.setVariable("task1", "I am task 1");  
	            
	}

}
