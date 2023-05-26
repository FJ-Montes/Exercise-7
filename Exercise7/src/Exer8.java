import java.sql.*;
import java.util.Scanner;

public class Exer8 {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/JavaTraining?encoding=UTF8";
        String username = "postgres";
        String password = "1234";
        
        try {
            //the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            //Get user input for the region name
            String region = getUserInput("Enter the region name: ");

            //Create and execute the SQL query
            String query = "SELECT * FROM Product_items WHERE LOWER(Region) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, region);
            ResultSet resultSet = statement.executeQuery();

            //Get the result set metadata
            ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();

            //table headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metadata.getColumnName(i) + "\t");
            }
            System.out.println();

            // Process the query results
            while (resultSet.next()) {
                // Print row data
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get user input
    private static String getUserInput(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
        // TODO Auto-generated method stub

    }

}
