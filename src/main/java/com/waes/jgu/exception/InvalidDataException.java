package com.waes.jgu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manage the situation when the request has an invalid base64 sequence
 * This Unchecked Exception is translated by Spring to HTTP 400 code
 *  
 * @author Jeison Gutierrez jdgutierrezj
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5879583846925205974L;

	public InvalidDataException(String message, Throwable err) {
		super(message, err);
	}
}
