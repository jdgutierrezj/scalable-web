package com.waes.jgu.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * DTO for representing the request
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@Data
@ApiModel(description="Class representing a request")
public class DiffRequest {
	@ApiModelProperty(notes="Binary object encoded in base64")
	@NotBlank(message = "Content must be provided")
	private String base64Data;
}
