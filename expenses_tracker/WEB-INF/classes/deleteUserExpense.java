import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class deleteUserExpense extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Delete User Expense </title> </head><body>");

        HttpSession sess = req.getSession(false);
        if (sess == null)       
        {
            res.sendRedirect("login.html");
            return;
        }
        String attr = (String)sess.getAttribute("usertype");
        int userid = (int)sess.getAttribute("id");
        if (attr == null || !attr.equals("user"))
        {
            res.sendRedirect("login.html");
            return;
        }

        try {
            DAO dao = new DAO();
            ArrayList<Expenses> list = dao.viewsExpenses(userid);

            out.println ("<table>");
            out.println ("<th> id </th> <th> user_id </th> <th> category_id </th> <th> amount </th> <th> date </th>");
            for (Expenses l:list)
                out.println ("<tr><td>" + l.getId() + "</td><td>" + l.getUser_id() + "</td><td>" + l.getCategory_id() + "</td><td>" + l.getAmount() + "</td><td>" + l.getDate() + "</td></tr>");
            out.println ("</table>");

            out.println ("<form action='deleteUserExpense' method='POST'>");
            out.println ("<label for='e_id'>Expense id:</label>");
            out.println ("<input type='text' id='e_id' name='e_id' required>");
            out.println ("<input type='submit' value='Delete'></form>");
            out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
            out.println("</body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }

    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        HttpSession sess = req.getSession(false);
        if (sess == null)
        {
            res.sendRedirect("login.html");
            return;
        }
        String attr = (String)sess.getAttribute("usertype");
        if (attr == null || !attr.equals("user"))
        {
            res.sendRedirect("login.html");
            return;
        }
        
        String del_id = req.getParameter("e_id");

        try {
            out.println ("<html><head> <link rel='stylesheet' href='styles.css'></head>");
            DAO dao = new DAO();
            boolean ans = dao.deletesUserExpense(del_id);
            if (ans) {
                out.println("<body><h1>Expense deleted!</h1>");
            } else {
                out.println("<body><h1>Expense not found!</h1>");
            }
            
            out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
            out.println("</body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}