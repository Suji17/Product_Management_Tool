import java.sql.*;
import java.util.Scanner;

public class Main{
    // Database URL, username, password
    static final String URL = "jdbc:mysql://localhost:3306/product_management";
    static final String USER = "root"; // unga MySQL username
    static final String PASS = "Sujitha@17"; // unga MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Connection create
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to Database!");

            while (true) {
                System.out.println("\n----- Product Management -----");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: // ADD
                        System.out.print("Enter product name: ");
                        String name = sc.next();
                        System.out.print("Enter price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();

                        String insertSQL = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
                        PreparedStatement ps1 = con.prepareStatement(insertSQL);
                        ps1.setString(1, name);
                        ps1.setDouble(2, price);
                        ps1.setInt(3, qty);
                        ps1.executeUpdate();
                        System.out.println("✅ Product Added!");
                        break;

                    case 2: // VIEW
                        String selectSQL = "SELECT * FROM products";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(selectSQL);
                        System.out.println("\nID | Name | Price | Quantity");
                        System.out.println("----------------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + " | " +
                                               rs.getString("name") + " | " +
                                               rs.getDouble("price") + " | " +
                                               rs.getInt("quantity"));
                        }
                        break;

                    case 3: // UPDATE
                        System.out.print("Enter product id to update: ");
                        int id = sc.nextInt();
                        System.out.print("Enter new price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("Enter new quantity: ");
                        int newQty = sc.nextInt();

                        String updateSQL = "UPDATE products SET price=?, quantity=? WHERE id=?";
                        PreparedStatement ps2 = con.prepareStatement(updateSQL);
                        ps2.setDouble(1, newPrice);
                        ps2.setInt(2, newQty);
                        ps2.setInt(3, id);
                        int rowsUpdated = ps2.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("✅ Product Updated!");
                        } else {
                            System.out.println("❌ Product ID not found.");
                        }
                        break;

                    case 4: // DELETE
                        System.out.print("Enter product id to delete: ");
                        int delId = sc.nextInt();

                        String deleteSQL = "DELETE FROM products WHERE id=?";
                        PreparedStatement ps3 = con.prepareStatement(deleteSQL);
                        ps3.setInt(1, delId);
                        int rowsDeleted = ps3.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("✅ Product Deleted!");
                        } else {
                            System.out.println("❌ Product ID not found.");
                        }
                        break;

                    case 5: // EXIT
                        System.out.println("Exiting...");
                        con.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("❌ Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
