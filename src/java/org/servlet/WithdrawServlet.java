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
@WebServlet(name = "WithdrawServlet", urlPatterns = {"/withdraw"})
public class WithdrawServlet extends HttpServlet {

    private final static String DB_URL = "jdbc:oracle:thin:@192.168.1.139:1521:orcl";
    private final static String DB_USER = "CMSINTERN3";
    private final static String DB_PASSWORD = "password";
    private final static String SQL = "UPDATE USERS SET initial_amount WHERE username=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int withdraw_amount = Integer.parseInt(req.getParameter("withdraw_amount"));//get HTML form data
        String username = req.getParameter("uname");
        int balance = 0;
        boolean error = false;
        resp.setContentType("text/html"); //set output type as HTML
        PrintWriter out = resp.getWriter();//in order to send the respose to the client

        //check login details in DB
        if (!error) {
            try {
                DriverManager.registerDriver(new OracleDriver());
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                    PreparedStatement ps = null, ps2 = null;
                    ResultSet rs = null;

                    try {
                        ps = con.prepareCall("SELECT initial_amount FROM USERS WHERE username=?");
                        ps.setString(1, username);
                        
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int initial_amount = rs.getInt("initial_amount");
                            balance = initial_amount - withdraw_amount;

                        } else {
                            out.write("<div style='color:red;'>Wrong amount..!</div>");
                        }

                        ps2 = con.prepareCall("UPDATE USERS SET initial_amount = ? WHERE username = ?");
                        ps2.setInt(1, balance);
                        ps2.setString(2, username);
                        ps2.executeUpdate();

                    } catch (SQLException ex) {
                        out.println(ex);
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                                ps.close();
                            } else if (ps != null) {
                                ps.close();
                            }

                            if (ps2 != null) {
                                ps2.close();
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
            } catch (SQLException ex) {
                out.println(ex);
            }
        }
        req.setAttribute("initial_amount", balance);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
        rd.forward(req, resp);
    }

}
