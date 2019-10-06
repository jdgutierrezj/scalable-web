package com.waes.jgu.service;

import com.waes.jgu.domain.EntryData;
import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.enums.Side;
import com.waes.jgu.exception.EntryIncompleteException;
import com.waes.jgu.exception.EntryNotFoundException;
import com.waes.jgu.exception.InmutableDataException;
import com.waes.jgu.exception.InvalidDataException;

/**
 * Interface that exposes the operations to handle storing and comparison capabilities
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
public interface DiffService {
	
	/**
	 * Save the entry with the base64 sequence for one side or the comparison
	 * 
	 * @param id the unique identifier of the entry
	 * @param side side of the comparison left or right
	 * @param base64Data sequence of characters that contains a valid base64 encoded binary data 
	 * 
	 * @return object just created or updated
	 * 
	 * @throws InvalidDataException when the request is trying to stored twice the same side of the comparison 
	 * @throws InmutableDataException when the request is trying to stored twice the same side of the comparison
	 * */
	EntryData saveData(String id, Side side, String base64Data) throws InvalidDataException, InmutableDataException ;
	
	/**
	 * Get the differences between both sides of the comparison
	 * 
	 * @param id the unique identifier of the entry
	 * 
	 * @return response with the result processed
	 * 
	 * @throws EntryNotFoundException when the entry was not found
	 * @throws EntryIncompleteException when one or more sides of the comparison have not been received
	 * */
	DiffResponse getDiff(String id) throws EntryNotFoundException, EntryIncompleteException ;
}
