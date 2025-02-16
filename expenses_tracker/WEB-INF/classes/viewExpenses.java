import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class viewExpenses extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>View Expenses</title> </head><body>");

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
            out.println ("<th> id </th> <th> user </th> <th> category </th> <th> amount </th> <th> date </th>");
            for (Expenses l:list)
                out.println ("<tr><td>" + l.getId() + "</td><td>" + l.getUser_id() + "</td><td>" + l.getCategory_id() + "</td><td>" + l.getAmount() + "</td><td>" + l.getDate() + "</td></tr>");
            out.println ("</table>");
            
            out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form></body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}