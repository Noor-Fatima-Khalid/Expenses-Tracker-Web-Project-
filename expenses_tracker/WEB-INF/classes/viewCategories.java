import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

public class viewCategories extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>View Categories</title> </head><body>");

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
            out.println ("</table></body></html>");
            
            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}