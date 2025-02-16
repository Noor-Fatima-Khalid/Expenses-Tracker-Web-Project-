import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class validation extends HttpServlet {
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        String name = req.getParameter("name");
        String password = req.getParameter("pswd");
        
        try {
            DAO dao = new DAO();
            User user = dao.validatesUser(name, password);
            if (user != null) {
                int id = user.getId();
                String username = user.getName();
                String usertype = user.getType();
                HttpSession sess = req.getSession(true);
                sess.setAttribute("id", id);
                sess.setAttribute("username", username);
                sess.setAttribute("usertype", usertype);

                if (usertype.equals("admin"))
                    res.sendRedirect("adminDashboard");
                else
                    res.sendRedirect("userDashboard");
            }
            else {
                out.println("<html><body><h1>Record not found. Please make an account </h1></body></html>");
            }
            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<html><body><h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<html><body><h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}