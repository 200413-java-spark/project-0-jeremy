package com.revature.jt.project0.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
    )
public class HelloServlet extends HttpServlet {

    /**
    *
    */
    private static final long serialVersionUID = -5636684876021802333L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.write("hello".getBytes());
        out.flush();
        out.close();
    }

}