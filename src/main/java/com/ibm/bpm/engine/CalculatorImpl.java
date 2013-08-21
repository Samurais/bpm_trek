package com.ibm.bpm.engine;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;


@WebService(endpointInterface = "com.ibm.bpm.engine.Calculator", serviceName = "calculator")
public class CalculatorImpl implements Calculator {

	public static void main(String[] args) {
		Calculator calculator;
		Server server;
		calculator = new CalculatorImpl();
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setServiceClass(Calculator.class);
		svrFactory.setAddress("http://localhost:18080/calculator");
		svrFactory.setServiceBean(calculator);
		svrFactory.getInInterceptors().add(new LoggingInInterceptor());
		svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		server = svrFactory.create();
		server.start();
	}

	@WebMethod
	@WebResult(name = "num3")
	public int plus(@WebParam(name = "num1") int num1,
			@WebParam(name = "num2") int num2) {
		// TODO Auto-generated method stub
		System.out.println(num2+num1);
		
		return num2+num1;
	}

	@WebMethod
	@WebResult(name = "num4")
	public int minus(@WebParam(name = "num1") int num1,
			@WebParam(name = "num2") int num2) {
		// TODO Auto-generated method stub
		System.out.println(num2-num1);
		
		return num2-num1;
	}


	
	
}