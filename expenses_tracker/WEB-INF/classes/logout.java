import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class logout extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='styles.css'> <title>Logout</title> </head><body>");

        HttpSession sess = req.getSession();
        if (sess != null) {
            sess.invalidate();
        }
        res.sendRedirect("login.html");
    }
}