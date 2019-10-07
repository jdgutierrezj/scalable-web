# Scalable Web Assignment!

This is a REST API built with Spring Boot to solve the assignment for Java veloper on WAES
## Design
According to the requirement, this API exposes 3 endpoints which are explained in the table below. I took these design decisions:

 - Each one of the endpoints that receives data just can be invoqued one per  **id**, the next invocation with the same **id** will throw an ***InmutableDataException*** which is translated by spring to a HTTP BadRequest (422) response
 - I am using a validation with [Base64.Decoder](https://docs.oracle.com/javase/8/docs/api/java/util/Base64.Decoder.html#decode-java.lang.String-) hence the string passed has to be valid
 - I am assuming that the string is binary content so I just calculated string differences by using the java version of [this](https://github.com/google/diff-match-patch) library

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

    $ git clone https://github.com/jdgutierrezj/scalable-web

Change into the ***scalable-web*** folder and build the project:

    $ mvn clean install
The executable jar will be located in this path:

    $ scalable-web/target/scalable-web-1.0.0.jar

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
	$ export AWS_DEFAULT_REGION="us-east-1"
	
Change into the *terraform* subfolder and deploy the application to Amazon Web Services using the files *.tf provided with the next command:

    $ terraform init
    $ terraform plan
    $ terraform apply

You will have to type "**yes**" to allow the creation and when finish the output will be the public IP:

![Terraform Output](https://github.com/jdgutierrezj/scalable-web/blob/master/postman/TerraformOutput.png)

Then you can use that IP to point your browser or your test:
![Terraform Output](https://github.com/jdgutierrezj/scalable-web/blob/master/postman/AWSSwagger.png)

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

![JUnit Result](https://github.com/jdgutierrezj/scalable-web/blob/master/postman/MVNJunitResults.png)

Or in eclipse:

![JUnit Result](https://github.com/jdgutierrezj/scalable-web/blob/master/postman/JUnitResults.png)
### Curl
**Some Scenarios:**

*Send **valid** data to left side:*

```
curl -i --data \
{"base64Data":"iVBORw0KGgoAAAANSUhEUgAABQAAAAPTCAYAAAGYmZTaAAAAAXNSR0IArs4=" }' \
-H "Content-Type: application/json" -X POST http://localhost:8080/v1/diff/1/left
```
*Send **valid** data to right side:*

```
curl -i --data \
{"base64Data":"iVBORw0KGgoAAAANSUhEUgAABQAAAAPTCAYAAAGYmZTaAAAAAXNSR0IArs4=" }' \
-H "Content-Type: application/json" -X POST http://localhost:8080/v1/diff/1/right
```
*Getting differences:*
```
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/v1/diff/1
```
*Send **invalid** data to left side:*

```
curl -i --data \
{"base64Content":"iVBORw0KGgoAAAANSUhEUgAABQAAAAPT $CAYAAAGYmZTaAAAAAXNSR0IArs4=" }' \
-H "Content-Type: application/json" -X POST http://localhost:3000/v1/diff/1/right
```

### Postman
Inside the subfolder postman you will be find a Postman Collection to import and execute:

![Postman Collection](https://github.com/jdgutierrezj/scalable-web/blob/master/postman/Collection-Postman.png)
