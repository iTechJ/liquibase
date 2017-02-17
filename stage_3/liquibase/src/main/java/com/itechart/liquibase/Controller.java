package com.itechart.liquibase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Simplest controller, showing the schema structure
 */
@WebServlet(name = "Controller")
public class Controller extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Simplest controller, showing the schema structure
     *
     * @param request  http request
     * @param response http response
     */
    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MissingResourceException {

        String dest = "main";
        try {
            ResourceBundle rb = ResourceBundle.getBundle("mvn");
            String user = rb.getString("db.user");
            String password = rb.getString("db.password");
            String url = rb.getString("db.url");

            try (
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement()
            ) {
                String query = "show tables";
                ResultSet rs = statement.executeQuery(query);
                ResultSetMetaData md = rs.getMetaData();
                ArrayList<String> tables = new ArrayList<>();
                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        tables.add(rs.getString(i));
                    }
                }
                request.setAttribute("dataz", tables);
                rs.close();
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getLocalizedMessage());
            dest = "error";
        }

        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher(dest + ".jsp");
        rd.forward(request, response);
    }
}
