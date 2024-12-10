import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RecipeController {

    private final Scanner scanner = new Scanner(System.in);

    public void addRecipe() {
        System.out.println("Enter Recipe Title:");
        String title = scanner.nextLine();

        System.out.println("Enter Ingredients (comma-separated):");
        String ingredients = scanner.nextLine();

        System.out.println("Enter Instructions:");
        String instructions = scanner.nextLine();

        String sql = "INSERT INTO recipes (title, ingredients, instructions) VALUES (?, ?, ?)";

        if (executeUpdate(sql, title, ingredients, instructions)) {
            System.out.println("Recipe added successfully!");
        } else {
            System.out.println("Failed to add the recipe.");
        }
    }

    public void searchRecipe() {
        System.out.println("Enter recipe title or keyword to search:");
        String keyword = scanner.nextLine();

        String sql = "SELECT * FROM recipes WHERE title LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    displayRecipe(rs);
                }
                if (!found) {
                    System.out.println("No recipes found matching the keyword: " + keyword);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for recipes: " + e.getMessage());
        }
    }

    public void viewRecipes() {
        String sql = "SELECT * FROM recipes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                displayRecipe(rs);
            }
            if (!found) {
                System.out.println("No recipes found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving recipes: " + e.getMessage());
        }
    }

    public void editRecipe() {
        System.out.println("Enter the title of the recipe you want to edit:");
        String title = scanner.nextLine();

        System.out.println("Enter updated ingredients (comma-separated):");
        String ingredients = scanner.nextLine();

        System.out.println("Enter updated instructions:");
        String instructions = scanner.nextLine();

        String sql = "UPDATE recipes SET ingredients = ?, instructions = ? WHERE title = ?";
        if (executeUpdate(sql, ingredients, instructions, title)) {
            System.out.println("Recipe updated successfully!");
        } else {
            System.out.println("Failed to update the recipe. Please ensure the title exists.");
        }
    }

    private boolean executeUpdate(String sql, String... params) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            return false;
        }
    }

    private void displayRecipe(ResultSet rs) throws SQLException {
        System.out.println("Recipe ID: " + rs.getInt("id"));
        System.out.println("Title: " + rs.getString("title"));
        System.out.println("Ingredients: " + rs.getString("ingredients"));
        System.out.println("Instructions: " + rs.getString("instructions"));
        System.out.println("----------------------------------------------------");
    }

    public static void main(String[] args) {
        RecipeController controller = new RecipeController();
        Scanner menuScanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Recipe Management System ===");
            System.out.println("1. Add Recipe");
            System.out.println("2. Search Recipe");
            System.out.println("3. View All Recipes");
            System.out.println("4. Edit Recipe");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = menuScanner.nextInt();
            menuScanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    controller.addRecipe();
                    break;
                case 2:
                    controller.searchRecipe();
                    break;
                case 3:
                    controller.viewRecipes();
                    break;
                case 4:
                    controller.editRecipe();
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
