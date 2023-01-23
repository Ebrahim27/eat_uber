package com.ebi.snap_food.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		//httpSecurity.cors().and();
		// We don't need CSRF for this example
		httpSecurity

				.cors().and().csrf().disable()//cors origin by hajilou
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/swagger-ui/*","/swagger-ui","/swagger-ui/","/swagger-ui.html","/register","/generatecode",
				"/pic/*","/pic/**","/pic/*/*/*","/pic/*/*","/policyTypes/*","/download/*"//).permitAll().
				,"/Third-party-damage/save","/damage_persons/save","/horofVasatpelak/getAllHorof",
				"/ThirdPartyDamageDocsAPI/upload","/ThirdPartyDamageDocsAPI/uploadPersonDocs","/ThirdPartyDamageDocsAPI/uploadCoroki"
				,"/externalService/getSanhabPolicyInquiry","/authenticateWT","/Third-party-damage/evaluationRequest","/InsuranceCompany/getInsuranceCompany",
				"/generatecodeCriminal","/authenticatePanel","/payment/paymentBack","/payment/paymentBack2").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		///////////////////for xss  by hajilou  //////////////////////
		httpSecurity
				.headers()
				.xssProtection()
				.and()
				.contentSecurityPolicy("script-src 'self'");
		/////////////////////////////////////////////////////////////;

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}