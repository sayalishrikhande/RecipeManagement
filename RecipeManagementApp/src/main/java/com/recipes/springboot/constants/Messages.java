package com.recipes.springboot.constants;

public enum Messages {
	
	NOT_FOUND("Recipe not found"), SUCCESSFUL("Successful"), UNSUCCESSFUL("FAILURE");
	

	private final String message;

	Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
