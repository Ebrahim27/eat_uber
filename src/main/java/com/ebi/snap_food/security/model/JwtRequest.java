package com.ebi.snap_food.security.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String activitycode;
	private String panel="";
	private String deviceType;
	private String password;

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getPanel() {
		return panel;
	}

	public void setPanel(String panel) {
		this.panel = panel;
	}

	//private String password;


	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String activitycode) {
		this.setUsername(username);
		this.setActivitycode(activitycode);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActivitycode() {
		System.out.println("activity code "+this.activitycode);
		return this.activitycode;
	}

	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}