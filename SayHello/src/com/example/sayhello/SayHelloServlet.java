package com.example.sayhello;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SayHelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Logger log = Logger.getLogger(SayHelloServlet.class.getName());

		String[] arg = {"helloword-0001"};
		GettingStarted.main(arg);
		
		log(arg.toString());
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
