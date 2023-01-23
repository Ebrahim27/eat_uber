package com.ebi.snap_food.security.controller;





import com.ebi.snap_food.security.model.JwtRequest;
import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.security.model.UsersRoles;
import com.ebi.snap_food.security.service.JwtUserDetailsService;
import com.ebi.snap_food.security.service.UserRolesService;
import com.ebi.snap_food.security.service.UsersService;
import com.ebi.snap_food.infra.dto.CriminalUserSms;
import com.ebi.snap_food.security.config.JwtTokenUtil;
import com.ebi.snap_food.security.dto.UsersDto;
import com.ebi.snap_food.security.model.JwtResponse;
import com.ebi.snap_food.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserRolesService userRolesService;

	@Autowired
	private UsersService usersService;



	@Value("${sms.sending.active}")
	private String smsActive;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getActivitycode());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		 if (authenticationRequest.getDeviceType()==null) authenticationRequest.setDeviceType("unknow");

		//for penel roles
		if (!authenticationRequest.getPanel().isEmpty() && authenticationRequest.getPanel().equals("panel")) {
			List<UsersRoles> usersRoles = userRolesService.findUsersRolesByName("adminPanel", userDetails.getUsername());
			if (usersRoles.isEmpty()) {
				return  ResponseEntity.badRequest().headers
						(new HttpHeaders()).
						body(new ErrorMessage(400,"دسترسی غیر مجاز!! "));
			}
		}
		//
///////new for quest user ///////////////////////
		Users user =usersService.getUser(userDetails.getUsername());
		boolean guest=true;
		//if (user.getGuest()!= Boolean.parseBoolean(null)){
			guest=user.getGuest();
		//}
//////////////////////////////////////////////////
		final String token = jwtTokenUtil.generateToken(userDetails,authenticationRequest.getDeviceType());
		JwtResponse jwtResponse=new JwtResponse(token,guest);
		return ResponseEntity.ok(jwtResponse);
	}
	
	@RequestMapping(value = "/generatecode", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UsersDto user) throws Exception {

		return userDetailsService.update(user);
	}
	//پیامک به مقصر
	@RequestMapping(value = "/generatecodeCriminal", method = RequestMethod.POST)
	public ResponseEntity<?> generatecodeCriminal(@RequestBody CriminalUserSms criminalUserSms) throws Exception {

		return userDetailsService.updateCriminalUser(criminalUserSms);
	}
	@RequestMapping(value = "/saveOrUpdatePanelUser", method = RequestMethod.POST)
	public ResponseEntity<?> createPanelUser(@RequestBody UsersDto user) throws Exception {

		return userDetailsService.updateUserPanel(user);
	}

	private void authenticate(String username, String activitycode) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, activitycode));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@RequestMapping(value = "/authenticateWT", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateWT(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getActivitycode());
			return ResponseEntity.ok().headers
					(new HttpHeaders()).
					body(new ErrorMessage(200, "تایید "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
					(new HttpHeaders()).
					body(new ErrorMessage(403, "عدم تایید "));
		}


	}

	@RequestMapping(value = "/authenticatePanel", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenPanel(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticateP(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		if (authenticationRequest.getDeviceType()==null) authenticationRequest.setDeviceType("unknow");

		//for penel roles
		if (!authenticationRequest.getPanel().isEmpty() && authenticationRequest.getPanel().equals("panel")) {
			List<UsersRoles> usersRoles = userRolesService.findUsersRolesByName("adminPanel", userDetails.getUsername());
			if (usersRoles.isEmpty()) {
				return  ResponseEntity.badRequest().headers
						(new HttpHeaders()).
						body(new ErrorMessage(400,"دسترسی غیر مجاز!! "));
			}
		}
		//
///////new for quest user ///////////////////////
		Users user =usersService.getUser(userDetails.getUsername());
		boolean guest=true;
		//if (user.getGuest()!= Boolean.parseBoolean(null)){
		guest=user.getGuest();
		//}
//////////////////////////////////////////////////
		final String token = jwtTokenUtil.generateToken(userDetails,authenticationRequest.getDeviceType());
		JwtResponse jwtResponse=new JwtResponse(token,guest);
		return ResponseEntity.ok(jwtResponse);
	}


	@RequestMapping(value = "/getcodeForCntr", method = RequestMethod.POST)
	public ResponseEntity<?> getcodeForCntr(@RequestBody UsersDto user) throws Exception {

		return userDetailsService.getCodeCntr(user);
	}


	private void authenticateP(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}