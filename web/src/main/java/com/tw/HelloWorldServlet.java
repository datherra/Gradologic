package com.tw;

import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;


public class HelloWorldServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();

    out.println("Profit!");
  }

}