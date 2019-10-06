package com.waes.jgu.dto;

import com.waes.jgu.external.DiffMatchPatch;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 
 * DTO for representing each one of the differences that have been found
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@Data
@ApiModel(description="Class representing each one of the differences that have been found")
public class DiffResult {
	private DiffMatchPatch.Operation type;
	private String content;
	private int position;
	private int offset;
}
