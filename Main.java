import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/product_management";
    static final String USER = "root";
    static final String PASSWORD = "Sujitha@17";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== PRODUCT MANAGEMENT MENU =====");
            System.out.println("1. Add Category");
            System.out.println("2. Add Attribute to Category");
            System.out.println("3. Add Product");
            System.out.println("4. Add Product Attribute Value");
            System.out.println("5. View Categories");
            System.out.println("6. View Products");
            System.out.println("7. Add User");
            System.out.println("8. View Users");
            System.out.println("9. Place Order");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> addCategory(sc);
                    case 2 -> addAttribute(sc);
                    case 3 -> addProduct(sc);
                    case 4 -> addProductAttributeValue(sc);
                    case 5 -> viewCategories();
                    case 6 -> viewProducts();
                    case 7 -> addUser(sc);
                    case 8 -> viewUsers();
                    case 9 -> placeOrder(sc);
                    case 10 -> {
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 1. Add Category
    static void addCategory(Scanner sc) throws Exception {
        System.out.print("Enter category name: ");
        String name = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO categories(category_name) VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Category added successfully!");
        }
    }

    // 2. Add Attribute
    static void addAttribute(Scanner sc) throws Exception {
        System.out.print("Enter attribute name: ");
        String name = sc.nextLine();
        System.out.print("Enter category id: ");
        int catId = sc.nextInt();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO attributes(attribute_name, category_id) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setInt(2, catId);
            ps.executeUpdate();
            System.out.println("Attribute added successfully!");
        }
    }

    // 3. Add Product
    static void addProduct(Scanner sc) throws Exception {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter category id: ");
        int catId = sc.nextInt();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products(product_name, category_id) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setInt(2, catId);
            ps.executeUpdate();
            System.out.println("Product added successfully!");
        }
    }

    // 4. Add Product Attribute Value
    static void addProductAttributeValue(Scanner sc) throws Exception {
        System.out.print("Enter product id: ");
        int productId = sc.nextInt();
        System.out.print("Enter attribute id: ");
        int attrId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter value: ");
        String value = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO product_attribute_values(product_id, attribute_id, value) VALUES(?, ?, ?)");
            ps.setInt(1, productId);
            ps.setInt(2, attrId);
            ps.setString(3, value);
            ps.executeUpdate();
            System.out.println("Product Attribute Value added successfully!");
        }
    }

    // 5. View Categories
    static void viewCategories() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categories");

            System.out.println("\n--- Categories ---");
            while (rs.next()) {
                System.out.println(rs.getInt("category_id") + " - " + rs.getString("category_name"));
            }
        }
    }

    // 6. View Products
    static void viewProducts() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");

            System.out.println("\n--- Products ---");
            while (rs.next()) {
                System.out.println(rs.getInt("product_id") + " - " + rs.getString("product_name") +
                        " (Category ID: " + rs.getInt("category_id") + ")");
            }
        }
    }

    // 7. Add User
    static void addUser(Scanner sc) throws Exception {
        System.out.print("Enter user name: ");
        String name = sc.nextLine();
        System.out.print("Enter user email: ");
        String email = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(user_name, user_email) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("User added successfully!");
        }
    }

    // 8. View Users
    static void viewUsers() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            System.out.println("\n--- Users ---");
            while (rs.next()) {
                System.out.println(rs.getInt("user_id") + " - " + rs.getString("user_name") + " (" + rs.getString("user_email") + ")");
            }
        }
    }

    // 9. Place Order
 
    public static void placeOrder( Scanner sc) throws SQLException {
        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Quantity: ");  
        int quantity = sc.nextInt();

        // Insert order into DB
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, userId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.executeUpdate();
                System.out.println("✅ Order placed successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
