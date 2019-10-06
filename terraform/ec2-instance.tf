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

resource "aws_security_group" "allow_8080_22" {
	name        = "allow_8080_22"
	description = "Allow HTTP inbound traffic"

	ingress {
	  from_port   = 8080
	  to_port     = 8080
	  protocol    = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
	}
	
	ingress {
	  from_port   = 22
	  to_port     = 22
	  protocol    = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
	}
	
	egress {
	  from_port   = 8080
	  to_port     = 8080
	  protocol    = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
	}
	
	egress {
	  from_port   = 22
	  to_port     = 22
	  protocol    = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
	}
	
	tags = {
	  Name = "allow_8080_22"
	}
}

resource "aws_instance" "waes_jgu" {
	ami                         = "${data.aws_ami.amazon-linux-2.id}"
	associate_public_ip_address = true
	instance_type               = "t2.micro"
	vpc_security_group_ids      = ["${aws_security_group.allow_8080_22.id}"]
	key_name					= "jgu-keypair"
	user_data   		    = "${file("install_scalable_web.sh")}"
    
    tags = {
    	Name = "WaesServer"
    }       							   
}
