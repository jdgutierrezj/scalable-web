package com.waes.jgu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manage the situation when the entry was not found
 * This Unchecked Exception is translated by Spring to HTTP 404 code
 *  
 * @author Jeison Gutierrez jdgutierrezj
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1799051323378498718L;
	
	public EntryNotFoundException(String message, Throwable err) {
		super(message, err);
	}
	
	public EntryNotFoundException(String message) {
		super(message);
	}
}
