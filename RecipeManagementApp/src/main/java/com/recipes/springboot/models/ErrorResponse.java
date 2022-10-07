package com.recipes.springboot.models;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private int status;
	private String message;
	private String errorCode;
	private String errorDetails;
	private HttpStatus httpStatus;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ErrorResponse(final HttpStatus httpStatus, final String errorDetails) {
		super();
		this.httpStatus = httpStatus;
		this.status = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();
		this.errorCode = httpStatus.name();
		this.errorDetails = errorDetails;
	}

}
