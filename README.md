**Expense Tracker**
A web-based expense tracker that helps users manage their expenses efficiently. Admins can manage categories and monitor overall spending, while users can log, view, and manage their personal expenses.  

**Features**  

Authentication  
- **Sign Up** – New users can create an account.  
- **Login** – Users and admins can securely log in.  
- **Session Management** – Ensures secure user sessions.  
- **Logout** – Users can log out anytime.  

User Features  
- **Log Expenses** – Enter expenses with amount, category, and date.  
- **View Expense History** – Track past spending records.  
- **Edit Expenses** – Update or modify existing entries.  
- **Delete Expenses** – Remove expense entries.  

Admin Features  
- **Create Categories** – Define categories (e.g., food, clothing, transport).  
- **View Total Expenses** – Monitor the total spending of all users.  
- **Update Categories & Limits** – Modify expense categories and set limits.  
- **Delete Expenses & Categories** – Remove unnecessary data.  

Tech Stack  
- **Frontend:** HTML, CSS, JavaScript   
- **Backend:** Java  
- **Database:** MySQL
  
🔧 Installation & Setup  

**1. Clone the Repository**  
Open your terminal and run:  
```bash
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker
```

**2. Set Up the Database**  
- Use **MySQL** and create a database:  
  ```sql
  CREATE DATABASE expense_tracker;
  ```
- Update the database connection settings in `application.properties` (for Spring Boot) or `persistence.xml` (for JPA).  

### **3. Configure Environment Variables**  
Modify `application.properties` (for Spring Boot) or `web.xml` (for a traditional Java web app) with:  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **4. Build & Deploy the Application**  
For **Maven**:  
```bash
mvn clean install
```
For **Gradle**:  
```bash
gradle build
```

### **5. Deploy to Tomcat**  
- Copy the generated `.war` file (from `target/` folder) to the `webapps` folder in Tomcat.  
- Start Tomcat using:  
  ```bash
  cd /path/to/tomcat/bin
  ./catalina.sh run  # (Linux/Mac)
  catalina.bat run   # (Windows)
  ```
- Access the app at:  
  ```
  http://localhost:8080/expense-tracker
  ```
