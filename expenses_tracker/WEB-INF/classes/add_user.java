import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class add_user extends HttpServlet {
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        String name = req.getParameter("name");
        String mail = req.getParameter("mail");
        String password = req.getParameter("pswd");
        String type = req.getParameter("type");
        
        try {
            DAO dao = new DAO();
            boolean updates = dao.addsUser(name, mail,password, type);
            if (updates){
                HttpSession sess = req.getSession(true);
                sess.setAttribute("username", name);
                sess.setAttribute("usertype", type);

                if (type.equals("admin"))
                    res.sendRedirect("adminDashboard");
                else
                    res.sendRedirect("userDashboard");
            }
            else 
                out.println("<html><body><h1>User could not be added! Try again.</h1></body></html>");
            
            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}