import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class deleteCategory extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Delete Category</title> </head><body>");
    
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

        try {
            DAO dao = new DAO();
            ArrayList<Category> list = dao.viewsCategories();
            
            out.println ("<table>");
            out.println ("<th> id </th> <th> Name </th> <th> Limit </th>");
            for (Category l:list)
                out.println ("<tr><td>" + l.getId() + "</td><td>" + l.getName() + "</td><td>" + l.getLimit() + "</td></tr>");
            out.println ("</table>");  

            out.println ("<form action='deleteCategory' method='POST'>");
            out.println ("<label for='name'>Category Name:</label>");
            out.println ("<input type='text' id='name' name='name' required>");
            out.println ("<input type='submit' value='Delete'>");
            out.println ("</form>"); 
            
            out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form></body></html>");

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
        if (attr == null || !attr.equals("admin"))
        {
            res.sendRedirect("login.html");
            return;
        }
        
        String del_name = req.getParameter("name");
        
        try {
            DAO dao = new DAO();
            boolean ans = dao.deletesCategory(del_name);

            out.println("<html><head> <link rel='stylesheet' href='styles.css'></head>");
            if (ans) {
                out.println("<body><h1>Category deleted!</h1>");
            } else {
                out.println("<body><h1>Category not found!</h1>");
            }
            
            out.println("<form action='adminDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
            out.println("</body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}