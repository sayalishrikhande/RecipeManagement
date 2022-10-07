package com.recipes.springboot.util;

import com.recipes.springboot.models.AuthenticationRequest;

public class ValidateInput {

	public boolean validateAuthenticationRequest(AuthenticationRequest authenticationRequest) {

		boolean isValid = false;

		if (null != authenticationRequest.getUsername() && null != authenticationRequest.getUserpassword()
				&& authenticationRequest.getUsername().equalsIgnoreCase("myUser")
				&& authenticationRequest.getUserpassword().equalsIgnoreCase("mypass")) {
			isValid = true;
		}

		return isValid;
	}

}
