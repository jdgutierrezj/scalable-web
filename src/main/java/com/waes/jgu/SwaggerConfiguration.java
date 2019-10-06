package com.waes.jgu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Swagger Configuration for documenting the API
 * 
 * @see <a href="https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/">Documenting Spring Boot REST API with Swagger and SpringFox</a>
 * 
 * @author Jeison Gutierrez - jdgutierrezj
 * 
 * */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
	
	@Bean
	public Docket apiDocket() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.basePackage("com.waes.jgu"))
	            .paths(PathSelectors.ant("/v1/**"))
	            .build()
	            .apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("Assignement Scalable Web")
				.description("REST API built with Spring Boot to calculate differences between two base64 encoded binary data")
				.version("1.0.0")
				.license("Apache License")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Jeison Gutierrez", "http://linkedin.com/ui/jeisongutierrez", "jeisongutierrez@gmail.com"))
				.build();
	}
}
