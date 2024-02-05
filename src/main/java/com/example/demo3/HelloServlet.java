package com.example.demo3;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.demo3.Library;

import static java.lang.System.out;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*response.setContentType("text/html");

        // Retrieve the username parameter from the request
        String username = request.getParameter("username");

        // Hello message
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello, " + (username != null ? username : "Guest") + "!</h1>");
        out.println("</body></html>");*/
        Library library = new Library("jdbc:mysql://localhost:3306/jdbc", "root", "");
//        out.print(library.getAllBook());
    }

    public void destroy() {
    }
}