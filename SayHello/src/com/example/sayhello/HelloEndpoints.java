package com.example.sayhello;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;

@Api(name = "helloendpoints", version="v1", clientIds = {Constant.API_EXPLORER_CLIENT_ID},description = "Say Hello")
public class HelloEndpoints {
	
	@ApiMethod(name = "sayHello",path = "sayHello", httpMethod = HttpMethod.GET)
	public Hello sayHello(){
		return new Hello();
	}
	
	@ApiMethod(name = "sayHelloByName", path = "sayHelloByName", httpMethod = HttpMethod.GET)
	public Hello sayHelloByName(@Named("name") String name){
		return new Hello(name);
	}
	
}
