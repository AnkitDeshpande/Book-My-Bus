package com.masai.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex, WebRequest req) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SomethingWentWrongException.class)
	public ResponseEntity<ErrorDetails> handleSomethingWentWrongException(SomethingWentWrongException ex,WebRequest req) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@ExceptionHandler(AdminException.class)
//	public ResponseEntity<ErrorDetails> handleAdminException(AdminException ex,WebRequest req) {
//		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), req.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	@ExceptionHandler(UserException.class)
//	public ResponseEntity<ErrorDetails> handleUserException(UserException ex,WebRequest req) {
//		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), req.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}