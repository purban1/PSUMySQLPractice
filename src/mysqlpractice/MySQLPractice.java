package mysqlpractice;

import java.sql.*;

public class MySQLPractice {

    private Connection conn;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public static void main(String[] args) {

        MySQLPractice myDB = new MySQLPractice();

        // STEP 1: Set the fully qualified name of the JDBC driver class
        myDB.driverClassName = "com.mysql.jdbc.Driver";

        // STEP 2: Set the driver-specific URL (Uniform Resource Locator)
        // This is a server-specific address format
        myDB.url = "jdbc:mysql://localhost:3306/hr";

        // STEP 3: Set user name and password (if required)
        myDB.userName = "root";
        myDB.password = "";

        try {
            // This loads the class into memory
            Class.forName(myDB.driverClassName);

            // Now let the Driver Manager use the class to create a connection object
            // It figures this out from the url
            myDB.conn = DriverManager.getConnection(myDB.url, myDB.userName, myDB.password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "Error: Failed to load JDBC driver!");
            cnfex.printStackTrace();
            System.exit(1);  // terminate program
        } catch (SQLException sqlex) {
            System.err.println("Error: Unable to connect to database!");
            sqlex.printStackTrace();
            System.exit(1);  // terminate program
        }

        Statement stmt = null;

        ResultSet rs = null;

        // DB Queries – C.R.U.D.
        // RETREIVE records
        String qString1 = "SELECT * FROM employee";
        // DELETE records
        String delString = "DELETE from employee WHERE last_name = 'Blow'";
        // UPDATE records
        String addString = "UPDATE employee "
                + "SET phone = '531-111-2222', active = 0 WHERE last_name = 'Blow7'";
        // CREATE records
        String modString = "INSERT into employee (last_name, first_name, phone, active) "
                + "values ('Blow5', 'Joe', '999-123-2468', 1)";

        try {
            // Next use the connection object created earlier to create a statement object
            stmt = myDB.conn.createStatement();

            // Then use the executeQuery() method of the statement object
            // to execute the read-only query.
            // Be sure to check all the methods of the statement object in the API.
            // You would use an updateQuery() method, e.g., to insert or edit records
//                        int recordsDeleted = stmt.executeUpdate(sql2);


            int addCount = stmt.executeUpdate(addString);
            int delCount = stmt.executeUpdate(delString);
            int updateCount = stmt.executeUpdate(modString);
            rs = stmt.executeQuery(qString1);

            System.out.println("Records added: " + addCount + "\nRecords deleted: "
                    + delCount + "\nRecords updated: " + updateCount);
            
            System.out.println("============================");
            System.out.println("Output from SQL Server...");
            System.out.println("============================");

//                        rs.next();
//                        String firstName = rs.getString("FIRSTNAME");

            // The ResultSet contains the records returned by your query.
            // Loop through the ResultSet to extract the data
            int count = 0;
            while (rs.next()) {
                // Each record contains three display fields which we will reference
                // by number (1 based). Read up on other methods we could use in ResultSet.
                System.out.println("\nRecord No: " + (count + 1));
//                                System.out.println("\nID: " + rs.getInt(1)); // column one (ResultSet field)
                System.out.println("Last Name: " + rs.getString("last_name")); // named field
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Phone: " + rs.getString("phone"));
//                System.out.println("Hire Date: " + rs.getObject("HIREDATE"));
                count++;
            }
            System.out.println("==================\n" + count + " records found.");
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Make sure we close the statement and connection objects no matter what.
            // Since these also throw checked exceptions, we need a nested try-catch
            try {
                stmt.close();
                myDB.conn.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
