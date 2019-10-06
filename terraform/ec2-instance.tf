data "aws_ami" "amazon-linux-2" {
	most_recent = true
	owners = ["amazon"]
	
	filter {
	   name   = "owner-alias"
	   values = ["amazon"]
	}
	
	
	filter {
	 name   = "name"
	 values = ["amzn2-ami-hvm*"]
	}
}

resource "aws_security_group" "allow_8080" {
	name        = "allow_8080"
	description = "Allow HTTP inbound traffic"

	ingress {
	  from_port   = 8080
	  to_port     = 8080
	  protocol    = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
	}
	
	tags = {
	  Name = "allow_8080"
	}
}

resource "aws_default_subnet" "default" {                                   
  availability_zone = "us-east-1a"                        
}

resource "aws_instance" "waes_jgu" {
	ami                         = "${data.aws_ami.amazon-linux-2.id}"
	associate_public_ip_address = true
	instance_type               = "t2.micro"
	vpc_security_group_ids      = ["${aws_security_group.allow_8080.id}"]
	subnet_id                   = "${aws_default_subnet.default.id}"
	user_data					= <<EOT
									#!/bin/bash -x
								   	yum update -y
								   	yum install -y java-1.8.0-openjdk.x86_64 docker git
								   	git clone https://github.com/jdgutierrezj/scalable-web.git
								   	cd scalable-web
       							   	mvn clean install
       							   	java -jar target/scalable-web-1.0.0.jar -Dspring.profiles.active=prod
       							   EOT
    
    tags = {
    	Name = "WaesServer"
    }       							   
}