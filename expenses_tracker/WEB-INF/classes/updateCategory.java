import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class updateCategory extends HttpServlet {
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

        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Update Category</title> </head><body>");
        
        try {
            DAO dao = new DAO();
            ArrayList<Category> list = dao.viewsCategories();
            
            out.println ("<table>");
            out.println ("<th> id </th> <th> Name </th> <th> Limit </th>");
            for (Category l:list)
                out.println ("<tr><td>" + l.getId() + "</td><td>" + l.getName() + "</td><td>" + l.getLimit() + "</td></tr>");
            out.println ("</table>"); 

            out.println ("<form action='updateCategory' method='POST'>");
            out.println ("<label for='name'>Category Name:</label>");
            out.println ("<input type='text' id='name' name='name' required>");
            out.println ("<label for='limits'>Limit of the amount in this category:</label>");
            out.println ("<input type='text' id='limits' name='limits' required>");
            out.println ("<input type='submit' value='Save'>");
            out.println ("</form>");
            out.println("<form action='userDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form></body></html>");
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
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
        

        String change_name = req.getParameter("name");
        String limits = req.getParameter("limits");
        
        try {
            DAO dao = new DAO();
            boolean ans = dao.updatesCategory(change_name, limits);

            out.println ("<html><head> <link rel='stylesheet' href='styles.css'></head>");
            out.println ("<body><h1> Category updated!</h1></body></html>");
            out.println("<form action='adminDashboard' method='GET'><input type='submit' value='Back to Dashboard'></form>");
            out.println("</body></html>");
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }

}