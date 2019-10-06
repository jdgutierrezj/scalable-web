package com.waes.jgu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manage the situation when the request is trying to stored twice the same side of the comparison
 * This Unchecked Exception is translated by Spring to HTTP 400 code
 *  
 * @author Jeison Gutierrez jdgutierrezj
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InmutableDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004395865801345343L;

	public InmutableDataException(String message, Throwable err) {
		super(message, err);
	}
	
	public InmutableDataException(String message) {
		super(message);
	}
}
