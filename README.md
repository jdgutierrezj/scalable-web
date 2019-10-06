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

$ <PROJECT_FOLDER>/mvn spring-boot:run -Dspring.profiles.active=dev

## How to execute 
### Local
Inside scalable-web root folder execute the next command

> To execute it with a dummy Java collection as a database:

    $ <PROJECT_FOLDER>mvn spring-boot:run -Dspring.profiles.active=dev

> To execute it with a H2 in memory database:

    $ <PROJECT_FOLDER>mvn spring-boot:run -Dspring.profiles.active=prod

Now you can point your browser to http://localhost:8080
### Amazon Web Services

> This option has a dependency from [terraform](http://terraform.io)

Set your environment variables with the next commands:

    
	$ export AWS_ACCESS_KEY_ID="youraccesskey"
	$ export AWS_SECRET_ACCESS_KEY="yoursecretkey"
	$ export AWS_DEFAULT_REGION="us-west-2"
	

## How to test
Please choose the option more suitable for you
### Curl

### Postman
