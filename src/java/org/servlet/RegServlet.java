/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "RegServlet", urlPatterns = {"/reg"})
public class RegServlet extends HttpServlet {

    private final static String REG_PAGE = "register.html";
    private final static String LOGIN_PAGE = "index.html";
    private final static String DB_URL = "jdbc:oracle:thin:@192.168.1.139:1521:orcl";
    private final static String DB_USER = "CMSINTERN3";
    private final static String DB_PASSWORD = "password";
//    private final static String SQL = "INSERT INTO USERS(email,username,password,telephone)values('"+email+"','"+username+"','"+password+"','"+telephone+"')";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(REG_PAGE); //redirect to login if method is GET
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username"); //get HTML form data
        String password = req.getParameter("password");
        int telephone = Integer.parseInt(req.getParameter("telephone"));
        int initial_amount = Integer.parseInt(req.getParameter("initial_amount"));

        boolean error = false;
        resp.setContentType("text/html"); //set output type as HTML
        PrintWriter out = resp.getWriter();//in order to send the respose to the client

        if (!error) {
            try {
                DriverManager.registerDriver(new OracleDriver());
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    try (Statement st = con.createStatement()) {
                        int rs = st.executeUpdate("INSERT INTO USERS(email,username,password,telephone,initial_amount)values('" + email + "','" + username + "','" + password + "','" + telephone + "','"+initial_amount+"')");

                        PrintWriter o = resp.getWriter();
                        resp.setContentType("text/html");
                        o.println("<script type=\"text/javascript\">");
                        o.println("alert('Successfully Inserted!Please Login');");
                        o.println("</script>");

                        //resp.sendRedirect(LOGIN_PAGE);

                    }
                }
            } catch (SQLException ex) {
                out.println(ex);
            }
        }
        //redirect to login page with messages
        req.getRequestDispatcher(REG_PAGE).include(req, resp);
    }

}
