/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author pramod_d
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final static String PAGE = "index.html";
    private final static String HOME_PAGE = "/home.jsp";
    private final static String DB_URL = "jdbc:oracle:thin:@192.168.1.139:1521:orcl";
    private final static String DB_USER = "CMSINTERN3";
    private final static String DB_PASSWORD = "password";
    private final static String SQL = "SELECT initial_amount FROM USERS WHERE username=? and password=?";
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(PAGE); //redirect to login if method is GET

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); //get HTML form data
        String password = req.getParameter("password");
        boolean error = false;
        resp.setContentType("text/html"); //set output type as HTML
        PrintWriter out = resp.getWriter();//in order to send the respose to the client


        //check login details in DB
        if (!error) {
            try {
                DriverManager.registerDriver(new OracleDriver());
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    try (PreparedStatement ps = con.prepareCall(SQL)) {
                        ps.setString(1, username);
                        ps.setString(2, password);
                       
                        try (ResultSet rs = ps.executeQuery()) {
                            String initial_amount = "";
                            if (rs.next()) {
                                
                                initial_amount=rs.getString("initial_amount");
                                
                                req.setAttribute("username", username);
                                req.setAttribute("initial_amount", initial_amount);
                                
                                RequestDispatcher rd = getServletContext().getRequestDispatcher(HOME_PAGE);
                                rd.forward(req, resp);
                            } else {
                                out.write("<div style='color:red;'>Username or Password is Invalid..!</div>");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                out.println(ex);
            }
        }

        //redirect to login page with messages
        // req.getRequestDispatcher(PAGE).include(req, resp);
    }

}
