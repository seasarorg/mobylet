package org.mobylet.example.struts2.action;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;

import com.opensymphony.xwork2.ActionSupport;

public class SampleAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Mobylet mobylet;

	private String message;

	public String hello() {
		return SUCCESS;
	}

	public String resize() {
		return SUCCESS;
	}

	public String profile() {
		mobylet = MobyletFactory.getInstance();
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Mobylet getMobylet() {
		return mobylet;
	}
}
