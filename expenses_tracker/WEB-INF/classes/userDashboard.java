import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class userDashboard extends HttpServlet {
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
        String username = (String)sess.getAttribute("username");
        if (attr == null || !attr.equals("user"))
        {
            res.sendRedirect("login.html");
            return;
        }

        out.println ("<html><head> <link rel='stylesheet' href='dashboardStyles.css'> <title>User Dashboard</title> </head>");
        out.println ("<body><h1> User Dashboard </h1>");
        try {
            DAO dao = new DAO();
            DashboardInfo info = dao.getUserInfo(username);
            out.println ("<h3 class='totals'> Total Expenses: $" + info.getAmount() + "</h3><br/>");

            out.println("<div class='sections'>");
            out.println("<h3>Expenses</h3> <div class='buttons_gr'>");   
            out.println("<form action='addExpense' method='GET'><input type='submit' value='Add Expense'></form>");
            out.println("<form action='viewExpenses' method='GET'><input type='submit' value='View Expenses'></form>");
            out.println("<form action='updateExpense' method='GET'><input type='submit' value='Update Expense'></form>");
            out.println("<form action='deleteUserExpense' method='GET'><input type='submit' value='Remove Expense'></form>");
            out.println("</div></div>");

            out.println("<form action='logout' method='GET'><input type='submit' value='Logout' id='logout'></form>");
            out.println("</body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}