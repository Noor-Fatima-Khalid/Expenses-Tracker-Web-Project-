import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class addExpense extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Add Expense</title> </head><body>");

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

        try {
            DAO dao = new DAO();
            ArrayList <Category> list = dao.viewsCategories();
    
            out.println("<table>");
            out.println("<tr><th>Id</th><th>Category Name</th><th>Limit</th></tr>");
            for (Category l:list)
                out.println("<tr><td>" + l.getId() + "</td><td>" + l.getName() + "</td><td>" + l.getLimit() + "</td></tr>");
            out.println("</table>");
            
            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }

        out.println ("<form action='addExpense' method='POST'>");
        out.println ("<label for='c_id'>Category id:</label>");
        out.println ("<input type='text' id='c_id' name='c_id' required>");
        out.println ("<label for='amount'>Amount:</label>");
        out.println ("<input type='text' id='amount' name='amount' required>");
        out.println ("<label for='date'>Date:</label>");
        out.println ("<input type='date' id='date' name='date' required>");
        out.println ("<input type='submit' value='Save'>");
        out.println ("</form>");

        out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
        out.println("</body> </html>");

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
        
        int userid = (int)sess.getAttribute("id");
        String category = req.getParameter("c_id");
        String amount = req.getParameter("amount");
        String date = req.getParameter("date");

        try {
            out.println ("<html><head> <link rel='stylesheet' href='styles.css'></head>");
            DAO dao = new DAO();
            boolean ans = dao.addsExpense(userid, category, amount, date);
            if (ans) {
                out.println("<body><h1>Expense added!</h1>");
            } else {
                out.println("<body><h1>Category not found!</h1>");
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