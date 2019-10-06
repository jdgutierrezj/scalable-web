package com.waes.jgu.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * DTO for representing the response
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@Data
@ApiModel(description="Class representing the output with the result of the comparison")
public class DiffResponse {
	@ApiModelProperty(notes="Possible values \"EQUAL\", \"NOT EQUAL\", \"LENGTH EQUAL\"")
	private String result;
	@ApiModelProperty(notes="List of differences when exists")
	private List<DiffResult> differences;
}
