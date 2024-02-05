package com.example.demo3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "abbasServlet", value = "/abbas-servlet")
public class AbbasServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        // Retrieve the username parameter from the request
        String username = request.getParameter("username");

        // Hello message
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello, " + (username != null ? username : "Guest") + "!</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
