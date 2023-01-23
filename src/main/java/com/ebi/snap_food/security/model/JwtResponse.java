package com.ebi.snap_food.security.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final boolean guest ;


	public boolean getGuest() {
		return this.guest;
	}



	public JwtResponse(String jwttoken,boolean guest) {
		this.jwttoken = jwttoken;
		this.guest = guest;

	}

	public String getToken() {

		return this.jwttoken;
	}
}