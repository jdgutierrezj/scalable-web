# Scalable Web Assignment!

This is a REST API built with Spring Boot to solve the assignment for Java Developer on WAES
## Architecture


## Stack

 - Java 1.8
 - Apache Maven 3.3
 - Spring Boot 2
 - Swagger 2

## Requirements

 - Java 1.8+
 - Apache Maven 3.3+

## How to build
Clone the repository on your local machine

    git clone https://github.com/jdgutierrezj/scalable-web


## How to execute 
### Local
#### Maven Plugin
Inside ***scalable-web*** PROJECT FOLDER execute the next command

> To execute it with a dummy Java collection as a database:

    $ mvn spring-boot:run -Dspring.profiles.active=dev

> To execute it with a H2 in memory database:

    $ mvn spring-boot:run -Dspring.profiles.active=prod
#### JAR Packaged
Build the jar with the next command:

    $ mvn clean install
Run the application by executing the next command:

> Dummy Java collection as a database

    $ java -jar target/scalable-web-1.0.0.jar -Dspring.profiles.active=dev
> H2 in memory database

    $ java -jar target/scalable-web-1.0.0.jar -Dspring.profiles.active=prod
Now you can point your browser to http://localhost:8080
### Amazon Web Services

> This option has a dependency from [terraform](http://terraform.io)

Set your environment variables with the next commands:
    
	$ export AWS_ACCESS_KEY_ID="youraccesskey"
	$ export AWS_SECRET_ACCESS_KEY="yoursecretkey"
	$ export AWS_DEFAULT_REGION="us-west-2"
	
Deploy de application to Amazon Web Services using the file scalable-web.tf provided with the next command:

    $ terraform apply

## How to test
This application exposes the next endpoints to test:

|URL  |HTTP Method  |Description  |
|--|--|--|
|/v1/diff/{id}/left  |POST  |Receives a payload in JSON format with the attribute base64Data which has to contain a valid base64 sequence, also it's important to note that this endpoint is only allowed to invoque once with the same transaction **id** by design decision |
|/v1/diff/{id}/right  |POST  |Receives a payload in JSON format with the attribute base64Data which has to contain a valid base64 sequence, also it's important to note that this endpoint is only allowed to invoque once with the same transaction **id** by design decision  |
|/v1/diff/{id} |GET  |Calculate and return if the strings are EQUAL, LENGTH EQUAL or NOT EQUAL, it returns a JSON payload  |


Please choose the option more suitable for you:
### Unit and Integration's tests
Execute the next command to see the results:

    mvn test

### Curl

### Postman
