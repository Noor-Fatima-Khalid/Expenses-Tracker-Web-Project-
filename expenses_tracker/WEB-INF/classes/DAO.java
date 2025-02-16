import java.sql.*;
import java.util.ArrayList;

public class DAO {
    private Connection con;

    public DAO() throws ClassNotFoundException, SQLException {
        establishConnection();
    }

    private void establishConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1/expensetracker";
        con = DriverManager.getConnection(url, "root", "root");
    }

    public boolean addsUser (String name, String mail, String password, String type) throws SQLException {
        String query = "INSERT INTO users (name ,email,password, type) VALUES ('" + name + "', '" + mail + "', '" + password + "', '" + type + "')";
        PreparedStatement ps = con.prepareStatement(query);
        int updates = ps.executeUpdate(query);
        ps.close();
        if (updates > 0)
            return true;
        else 
            return false;
    }

    public User validatesUser(String name, String password) throws SQLException {
        String query = "SELECT id, name, password, type FROM users WHERE name = '" + name + "'AND password = '" + password + "'";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("name");
            String userType = rs.getString("type");

            user = new User(id, username, userType);
        }
        rs.close();
        ps.close();
        return user;
    }


    public DashboardInfo getInfo() throws SQLException {
        DashboardInfo info = new DashboardInfo();

        String query = "SELECT COUNT(*) AS rcount, SUM(amount) AS asum FROM expenses";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            info.setCount(rs.getInt("rcount"));
            info.setAmount(rs.getDouble("asum"));
        }
        rs.close();
        ps.close();
        return info;
    }

    public boolean addsCategory(String name, String limits) throws SQLException {
        String query = "INSERT INTO categories (name, limits) VALUES ('" + name + "', '" + limits + "')";
        PreparedStatement ps = con.prepareStatement(query);
        int updates = ps.executeUpdate();
        ps.close();
        if (updates > 0)
            return true;
        else
            return false;
    }    
    
    public ArrayList<Category> viewsCategories() throws SQLException {
        ArrayList<Category> list = new ArrayList<>();

        String query = "SELECT * FROM categories";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {    
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String limits = rs.getString("limits");
            list.add(new Category (id, name, limits));
        }
        rs.close();
        ps.close();
        return list;
    }

    public boolean updatesCategory(String change_name, String limits) throws SQLException {
        String query = "SELECT id, name FROM categories WHERE name='" + change_name + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        int updates=0;
        if (rs.next()) {
            int upd_id = rs.getInt("id");
            String upd_query = "UPDATE categories SET name='" + change_name + "', limits='" + limits + "' WHERE id=" + upd_id;
            updates = st.executeUpdate(upd_query);
            rs.close();
            st.close();
        }
        if (updates > 0)
            return true;
        else
            return false;
    }

    public boolean deletesCategory(String del_name) throws SQLException {
        String query = "DELETE FROM categories WHERE name='" + del_name + "'";
        Statement st = con.createStatement();
        int updates = st.executeUpdate(query);
        st.close();
        if (updates > 0)
            return true;
        else
            return false;
    }

    public ArrayList viewsAll () throws SQLException {
        ArrayList <Expenses> list = new ArrayList<> ();
        String query = "SELECT * FROM expenses";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {    
            int id = rs.getInt("id");
            int user_id = rs.getInt("user");
            int category_id = rs.getInt("category");
            int amount = rs.getInt("amount");
            String date = rs.getString("date");
            
            list.add (new Expenses (id, user_id, category_id, amount, date));
        }
        return list;
    }

    public boolean deletesExpense(String del_id) throws SQLException {
        String query = "DELETE FROM expenses WHERE id='" + del_id + "'";
        PreparedStatement ps = con.prepareStatement(query);
        int updates = ps.executeUpdate();
        ps.close();

        if (updates > 0)
            return true;
        else
            return false;
    }

    public DashboardInfo getUserInfo(String username) throws SQLException {
        DashboardInfo info = new DashboardInfo();

        String query = "SELECT id FROM users WHERE name = '" + username + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
                int id = rs.getInt("id");
                query = "SELECT SUM(amount) AS total_expenses FROM expenses WHERE user ='" + id + "'";
                Statement st_expense = con.createStatement();
                ResultSet rs1 = st_expense.executeQuery(query);
                while (rs1.next()) {
                    info.setAmount(rs1.getDouble("total_expenses"));
                }
                rs1.close();
                st_expense.close();
            }
        rs.close();
        st.close();
        return info;
    }

    public boolean addsExpense(int userid, String category, String amount, String date) throws SQLException {
        String query1 = "SELECT id FROM categories WHERE id = '" + category + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query1);
        int updates = 0;
           
        if (rs.next()) {
            String query = "INSERT INTO expenses (user, category, amount, date) VALUES ('" + userid + "', '" + category + "', '" + amount + "', '" + date + "')";
            PreparedStatement ps = con.prepareStatement(query);
            updates = ps.executeUpdate();
            ps.close();
        }
        rs.close();
        st.close();
        if (updates > 0)
            return true;
        else
            return false;
    }

    public ArrayList viewsExpenses (int userid) throws SQLException {
        ArrayList <Expenses> list = new ArrayList<> ();
        String query = "SELECT * FROM expenses WHERE user = '" + userid + "'";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {    
            int id = rs.getInt("id");
            int user_id = rs.getInt("user");
            int category_id = rs.getInt("category");
            int amount = rs.getInt("amount");
            String date = rs.getString("date");
            list.add (new Expenses (id, user_id, category_id, amount, date)); 
        }
        rs.close();
        ps.close();    
        return list;
    }

    public boolean updatesExpense(String e_id, String upd_amount) throws SQLException {
        String query1 = "SELECT id FROM expenses WHERE id = '" + e_id + "'";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query1);
        int updates=0;
        
        if (rs.next()) {
            String query = "UPDATE expenses SET amount = '" + upd_amount + "' WHERE id='" + e_id + "'";
            Statement st = con.createStatement();
            updates = st.executeUpdate(query);
            st.close();
        }
        if (updates > 0)
            return true;
        else
            return false;
    }

    public boolean deletesUserExpense(String del_id) throws SQLException {
        String query = "DELETE FROM expenses WHERE id='" + del_id + "'";
        Statement st = con.createStatement();
        int updates = st.executeUpdate(query);
        st.close();

        if (updates > 0)
            return true;
        else
            return false;
    }

    public void closeConnection() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}