import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class addCategory extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        HttpSession sess = req.getSession(false);
        if (sess == null)
        {
            res.sendRedirect("login.html");
            return;
        }
        String attr = (String)sess.getAttribute("usertype");
        if (attr == null || !attr.equals("admin"))
        {
            res.sendRedirect("login.html");
            return;
        }

        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Add Category</title> </head><body>");
        out.println ("<form action='addCategory' method='POST'>");
        out.println ("<label for='name'>Category Name:</label>");
        out.println ("<input type='text' id='name' name='name' required>");
        out.println ("<label for='limits'>Limit of the amount in this category:</label>");
        out.println ("<input type='text' id='limits' name='limits' required>");
        out.println ("<input type='submit' value='Save'>");
        out.println ("</form></body></html>");
    }
   
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        HttpSession sess = req.getSession(false);
        if (sess == null)
        {
            res.sendRedirect("login.html");
            return;
        }
        String attr = (String)sess.getAttribute("usertype");
        if (attr == null || !attr.equals("admin"))
        {
            res.sendRedirect("login.html");
            return;
        }
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'></head>");
        out.println ("<body><h1>New Category added!</h1>");
        out.println("<form action='adminDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
        out.println("</body></html>");
        
        String name = req.getParameter("name");
        String limits = req.getParameter("limits");
        
        try {
            DAO dao = new DAO();
            dao.addsCategory(name, limits);
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}