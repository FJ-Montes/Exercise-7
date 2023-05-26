import java.sql.*;

public class Exer7 {

    public static void main(String[] args) {
        
        String url = "jdbc:postgresql://localhost:5432/JavaTraining?encoding=UTF8"; 
        String username = "postgres"; 
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            System.out.print("Enter the primary key value: ");
            int primaryKeyValue = scanner.nextInt();

            
            String sqlQuery = "SELECT * FROM Product_items WHERE Code = ?";
            PreparedStatement prepStatement = conn.prepareStatement(sqlQuery);
            prepStatement.setInt(1, primaryKeyValue);
            ResultSet resultSet = prepStatement.executeQuery();

            
            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                Date orderDate = resultSet.getDate("Order_Date");
                String region = resultSet.getString("Region");
                String rep = resultSet.getString("Rep");
                String item = resultSet.getString("Item");
                String units = resultSet.getString("Units");
                double unitCost = resultSet.getDouble("Unit_Cost");
                double total = resultSet.getDouble("Total");

                System.out.println("Code: " + code);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Region: " + region);
                System.out.println("Rep: " + rep);
                System.out.println("Item: " + item);
                System.out.println("Units: " + units);
                System.out.println("Unit Cost: " + unitCost);
                System.out.println("Total: " + total);
            }

           
            resultSet.close();
            prepStatement.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
