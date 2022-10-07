package com.sayali.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sayali.springboot.common.ValidateInput;
import com.sayali.springboot.models.AthenticationResponse;
import com.sayali.springboot.models.AuthenticationRequest;
import com.sayali.springboot.services.serviceImpl.MyUserDetailsServiceImpl;
import com.sayali.springboot.util.RecipeManagementUtil;

@RestController
@RequestMapping("/v1/recipes/authenticate")
public class SecurityController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsServiceImpl userDetailsService;

	@Autowired
	private RecipeManagementUtil recipeManagementUtil;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getUserpassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);

		}

		ValidateInput validateInput = new ValidateInput();

		boolean isValid = validateInput.validateAuthenticationRequest(authenticationRequest);

		if (isValid) {

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

			final String jwtToken = recipeManagementUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AthenticationResponse(jwtToken));
		} else {
			return ResponseEntity.ok(null);
		}
	}


}
