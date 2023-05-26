import java.sql.*;
import java.util.Scanner;

public class Exer9 {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/JavaTraining?encoding=UTF8";
        String username = "postgres";
        String password = "1234";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Get user input for the code
            int code = getUserInputInt("Enter the code: ");

            // Retrieve existing data from the database
            String selectQuery = "SELECT * FROM Product_items WHERE Code = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, code);
            ResultSet resultSet = selectStatement.executeQuery();

            // Check if the code exists in the database
            if (resultSet.next()) {
                // Retrieve existing column values
                int orderDate = resultSet.getDate("Order_Date").toLocalDate().getDayOfMonth();
                String region = resultSet.getString("Region");
                String rep = resultSet.getString("Rep");
                String item = resultSet.getString("Item");
                String units = resultSet.getString("Units");
                double unitCost = resultSet.getDouble("Unit_Cost");
                double total = resultSet.getDouble("Total");

                // Display existing information
                System.out.println("Code: " + code);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Region: " + region);
                System.out.println("Rep: " + rep);
                System.out.println("Item: " + item);
                System.out.println("Units: " + units);
                System.out.println("Unit Cost: " + unitCost);
                System.out.println("Total: " + total);

                // Get user input for the new cost
                double newCost = getUserInputDouble("Enter the new cost: ");

                // Calculate the new total
                double newTotal = newCost * Double.parseDouble(units);

                // Update the database with the new unit cost and total
                String updateQuery = "UPDATE Product_items SET Unit_Cost = ?, Total = ? WHERE Code = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, newCost);
                updateStatement.setDouble(2, newTotal);
                updateStatement.setInt(3, code);
                updateStatement.executeUpdate();

                System.out.println("Update successful! New Unit Cost: " + newCost);
                System.out.println("New Total: " + newTotal);

                // Fetch and print the new information after the update
                PreparedStatement fetchStatement = connection.prepareStatement(selectQuery);
                fetchStatement.setInt(1, code);
                ResultSet newResultSet = fetchStatement.executeQuery();

                if (newResultSet.next()) {
                    int newOrderDate = newResultSet.getDate("Order_Date").toLocalDate().getDayOfMonth();
                    String newRegion = newResultSet.getString("Region");
                    String newRep = newResultSet.getString("Rep");
                    String newItem = newResultSet.getString("Item");
                    String newUnits = newResultSet.getString("Units");
                    double newUnitCost = newResultSet.getDouble("Unit_Cost");
                    double newTotalUpdated = newResultSet.getDouble("Total");

                    System.out.println("Code: " + code);
                    System.out.println("Order Date: " + newOrderDate);
                    System.out.println("Region: " + newRegion);
                    System.out.println("Rep: " + newRep);
                    System.out.println("Item: " + newItem);
                    System.out.println("Units: " + newUnits);
                    System.out.println("New Unit Cost (Updated): " + newUnitCost);
                    System.out.println("New Total (Updated): " + newTotalUpdated);
                }

                newResultSet.close();
                fetchStatement.close();
            } else {
                System.out.println("Code not found in the database.");
            }

            // Close the resources
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get user input as integer
    private static int getUserInputInt(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // Helper method to get user input as double
    private static double getUserInputDouble(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();

    }

}
