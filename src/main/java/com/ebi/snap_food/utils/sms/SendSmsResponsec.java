package com.ebi.snap_food.utils.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendSmsResponsec {

	@JsonProperty("RetStatus")
	private int retStatus;

	@JsonProperty("StrRetStatus")
	private String strRetStatus;

	@JsonProperty("Value")
	private String value;

	public void setRetStatus(int retStatus){
		this.retStatus = retStatus;
	}

	public int getRetStatus(){
		return retStatus;
	}

	public void setStrRetStatus(String strRetStatus){
		this.strRetStatus = strRetStatus;
	}

	public String getStrRetStatus(){
		return strRetStatus;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}