package com.waes.jgu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manage incomplete entry when one or more sides of the comparison have not been received
 * This Unchecked Exception is translated by Spring to HTTP 422 code
 *  
 * @author Jeison Gutierrez jdgutierrezj
 *
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntryIncompleteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1116912257076158323L;
	
	public EntryIncompleteException(String message, Throwable err) {
		super(message, err);
	}
	
	public EntryIncompleteException(String message) {
		super(message);
	}
}
