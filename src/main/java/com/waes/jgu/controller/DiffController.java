package com.waes.jgu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.jgu.dto.DiffRequest;
import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.enums.Side;
import com.waes.jgu.exception.EntryIncompleteException;
import com.waes.jgu.exception.EntryNotFoundException;
import com.waes.jgu.exception.InmutableDataException;
import com.waes.jgu.exception.InvalidDataException;
import com.waes.jgu.service.DiffService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Main REST Controller to expose the operations needed to handle storing and comparison
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */

@Slf4j
@RestController
@RequestMapping("/v1/diff")
@Api
public class DiffController {
	
	/**
	 *  Service Bean to execute logic of storing and comparison
	 *  */
	private DiffService service;
	
	/** 
	 * Constructor that handles Dependency Injection, in this way is not needed "@Autowired"
	 * */
	public DiffController(DiffService service) {
		this.service = service;
	}
	
	/**
	 * Upload the left side of the comparison
	 * 
	 * @param id the unique identifier of the entry
	 * @param request contents the base64 data attribute
	 *  
	 * @return HTTP 201 successful request / HTTP 400 for a invalid Base64 data or if this side of the comparison was already stored.
	 * 
	 * @throws InmutableDataException when the request is trying to stored twice the same side of the comparison
	 * @throws InvalidDataException when the request has an invalid base64 sequence
	 */
	@PostMapping(path = "/{id}/left",consumes = "application/json")
	@ApiOperation("Store the left side of the comparison")
	@ApiResponses( value = {
			@ApiResponse(code=404, message="The comparison entry identified by the id was not found"),
			@ApiResponse(code=400, message="Invalid Base64 Data"),
			@ApiResponse(code=400, message="The left side of the comparison was already received")}
		)
	public ResponseEntity<String> storeLeftData(@PathVariable("id") final String id, @RequestBody final DiffRequest request) throws InmutableDataException, InvalidDataException {
		log.info(String.format("storeLeftData -> Id %s",id));
		service.saveData(id, Side.LEFT, request.getBase64Data());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Upload the right side of the comparison
	 * 
	 * @param id the unique identifier of the entry
	 * @param request contents the base64 data attribute
	 *  
	 * @return HTTP 201 successful request / HTTP 400 for a invalid Base64 data or if this side of the comparison was already stored.
	 * 
	 * @throws InmutableDataException when the request is trying to stored twice the same side of the comparison
	 * @throws InvalidDataException when the request has an invalid base64 sequence
	 */
	@PostMapping(path = "/{id}/right",consumes = "application/json")
	@ApiOperation("Store the right side of the comparison")
	@ApiResponses( value = {
			@ApiResponse(code=404, message="The comparison entry identified by the id was not found"),
			@ApiResponse(code=400, message="Invalid Base64 Data"),
			@ApiResponse(code=400, message="The right side of the comparison was already received")}
		)
	public ResponseEntity<String> storeRightData(@PathVariable("id") final String id, @RequestBody final DiffRequest request) throws InmutableDataException, InvalidDataException {
		log.info(String.format("storeRightData -> Id %s", id));	
		service.saveData(id, Side.RIGHT, request.getBase64Data());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Get the results of the comparison 
	 * 
	 * @param id the unique identifier of the entry
	 * 
	 * @return HTTP 200 successful request / HTTP 404 if the entry was not found / HTTP 422 if one or two of the sides of the comparison haven't been received.
	 * 
	 * @throws EntryNotFoundException when the entry was not found
	 * @throws EntryIncompleteException when one or more sides of the comparison have not been received
	 */
	@GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation("Get the results of the comparison")
	@ApiResponses( value = {
			@ApiResponse(code=422, message="The comparison entry identified by the id is incomplete"),
			@ApiResponse(code=404, message="The comparison entry identified by the id was not found")}
		)	
	public ResponseEntity<DiffResponse> getDiffData(@PathVariable("id") final String id) throws EntryNotFoundException, EntryIncompleteException {
		log.info(String.format("getDiffData -> Id %s", id));	
		DiffResponse response = service.getDiff(id);
		return new ResponseEntity<DiffResponse>(response,HttpStatus.OK);
	}
	
}
