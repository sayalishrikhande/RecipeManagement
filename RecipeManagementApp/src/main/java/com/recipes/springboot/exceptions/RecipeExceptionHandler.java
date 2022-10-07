package com.recipes.springboot.exceptions;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.recipes.springboot.models.ErrorResponse;

@RestControllerAdvice
public class RecipeExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse constraintException(final ConstraintViolationException ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return errResponse;
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class, InvalidInputException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse methodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return errResponse;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorResponse Forbidden(final AccessDeniedException ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
		return errResponse;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse resourceNotFound(final ResourceNotFoundException ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
		return errResponse;
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorResponse BadCredentials(final BadCredentialsException ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
		return errResponse;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse HandleException(final Exception ex) {
		final ErrorResponse errResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return errResponse;
	}

}
