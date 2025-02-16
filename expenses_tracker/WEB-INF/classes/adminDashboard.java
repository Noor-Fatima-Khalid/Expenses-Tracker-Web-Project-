import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class adminDashboard extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {      
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println ("<html><head> <link rel='stylesheet' href='dashboardStyles.css'> <title>Admin Dashboard</title> </head><body>");
        out.println ("<h1>Admin Dashboard</h1>");
        
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
            DashboardInfo info = dao.getInfo();
            
            out.println ("<h3 class='totals'> Total Records: " + info.getCount() + "</h3>");
            out.println ("<h3 class='totals'> Total Expenses: $" + info.getAmount() + "</h3>");
            
            
            out.println("<div class='container'> <div class='sections'>");
            out.println("<h3>Categories</h3> <div class='buttons_gr'>");   
            out.println("<form action='addCategory' method='GET'><input type='submit' value='Add Category'></form>");
            out.println("<form action='viewCategories' method='GET'><input type='submit' value='View Categories'></form>");
            out.println("<form action='updateCategory' method='GET'><input type='submit' value='Update Category'></form>");
            out.println("<form action='deleteCategory' method='GET'><input type='submit' value='Delete Category'></form>");
            out.println("</div></div>");

            out.println("<div class='sections'>");
            out.println("<h3>Expenses</h3> <div class='buttons_gr'>"); 
            out.println("<form action='viewAll' method='GET'><input type='submit' value='View Expenses'></form>");
            out.println("<form action='deleteExpense' method='GET'><input type='submit' value='Remove Expense'></form>");
            out.println("</div></div></div>");
            
            out.println("<br/><br/><form action='logout' method='GET'><input type='submit' value='Logout' id='logout'></form>");
            out.println("</body></html>");

            dao.closeConnection();
        } catch (SQLException e) {
            out.println ("<h1>SQL Exception " + e.getMessage() + "</h1></body></html>");
        } catch (ClassNotFoundException e) {
            out.println ("<h1>Class Not Found! " + e.getMessage() + "</h1></body></html>");
        }
    }
}