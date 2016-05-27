package com.example.sayhello;

public class Hello {
	
	private String message = "Hello World";
	
	public Hello() {
		super();
	}

	public Hello(String fromApi) {
		super();
		this.message = "Hello "+fromApi+"!!!";
	}

	public String getName() {
		return message;
	}	

}
