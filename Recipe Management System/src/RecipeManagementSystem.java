import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class RecipeManagementSystem extends Application {
    private final Map<String, String> userAccounts = new HashMap<>();
    private final Map<String, String[]> storedRecipes = new HashMap<>();
    private Stage appStage;

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #0078D7; -fx-padding: 12 24; -fx-border-radius: 8; -fx-background-radius: 8;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #005A9E; -fx-padding: 12 24; -fx-border-radius: 8; -fx-background-radius: 8;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #0078D7; -fx-padding: 12 24; -fx-border-radius: 8; -fx-background-radius: 8;"));
    }

    @Override
    public void start(Stage stage) {
        this.appStage = stage;
        initializeLoginScreen();
    }

    private void initializeLoginScreen() {
        appStage.setTitle("User Login");

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");

        Button loginBtn = new Button("Log In");
        Button registerBtn = new Button("Sign Up");

        styleButton(loginBtn);
        styleButton(registerBtn);

        loginBtn.setOnAction(event -> handleLogin(usernameInput.getText().trim(), passwordInput.getText().trim()));
        registerBtn.setOnAction(event -> initializeRegistrationScreen());

        VBox loginLayout = new VBox(15, new Label("Welcome to Recipe Manager"), usernameInput, passwordInput, loginBtn, registerBtn);
        loginLayout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-spacing: 15;");

        appStage.setScene(new Scene(loginLayout, 300, 250));
        appStage.show();
    }

    private void handleLogin(String username, String password) {
        if (userAccounts.containsKey(username) && userAccounts.get(username).equals(password)) {
            displayMessage("Login Successful", "Welcome, " + username + "!", Alert.AlertType.INFORMATION);
            showMainMenu();
        } else {
            displayMessage("Login Failed", "Incorrect username or password.", Alert.AlertType.ERROR);
        }
    }

    private void initializeRegistrationScreen() {
        Stage registrationStage = new Stage();
        registrationStage.setTitle("User Registration");

        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("Choose a username");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Create a password");

        Button registerBtn = new Button("Register");

        styleButton(registerBtn);

        registerBtn.setOnAction(event -> {
            String newUsername = newUsernameField.getText().trim();
            String newPassword = newPasswordField.getText().trim();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                displayMessage("Error", "Both fields must be filled.", Alert.AlertType.ERROR);
            } else if (userAccounts.containsKey(newUsername)) {
                displayMessage("Error", "Username already exists.", Alert.AlertType.ERROR);
            } else {
                userAccounts.put(newUsername, newPassword);
                displayMessage("Success", "Registration completed. You may now log in.", Alert.AlertType.INFORMATION);
                registrationStage.close();
            }
        });

        VBox registrationLayout = new VBox(15, new Label("Register New Account"), newUsernameField, newPasswordField, registerBtn);
        registrationLayout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-spacing: 15;");

        registrationStage.setScene(new Scene(registrationLayout, 300, 250));
        registrationStage.show();
    }

    private void showMainMenu() {
        // Create buttons with updated labels
        Button addRecipeBtn = new Button("+ Add Recipe");
        Button searchRecipeBtn = new Button("🔍 Search Recipe");
        Button viewAllBtn = new Button("📖 View All Recipes");
        Button modifyRecipeBtn = new Button("📝 Edit Recipe");
        Button exitBtn = new Button("❗ Exit");
    
        // Style buttons to match the screenshot
        styleButton(addRecipeBtn);
        styleButton(searchRecipeBtn);
        styleButton(viewAllBtn);
        styleButton(modifyRecipeBtn);
        styleButton(exitBtn);
    
        // Set button actions
        addRecipeBtn.setOnAction(event -> initializeAddRecipeScreen());
        searchRecipeBtn.setOnAction(event -> initializeSearchScreen());
        viewAllBtn.setOnAction(event -> initializeRecipeListScreen());
        modifyRecipeBtn.setOnAction(event -> initializeEditRecipeScreen());
        exitBtn.setOnAction(event -> appStage.close());
    
        // Main menu title
        Label titleLabel = new Label("📖 Recipe Management System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
    
        // Arrange components in a vertical box
        VBox mainMenuLayout = new VBox(15, titleLabel, addRecipeBtn, searchRecipeBtn, viewAllBtn, modifyRecipeBtn, exitBtn);
        mainMenuLayout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-spacing: 15; -fx-background-color: #f4f4f4;");
    
        // Set scene
        appStage.setScene(new Scene(mainMenuLayout, 400, 400));
    }    

    private void initializeAddRecipeScreen() {
        Stage addRecipeStage = new Stage();
        addRecipeStage.setTitle("Add a New Recipe");

        TextField recipeNameField = new TextField();
        recipeNameField.setPromptText("Recipe Name");

        TextArea ingredientsArea = new TextArea();
        ingredientsArea.setPromptText("List ingredients (comma-separated)");

        TextField preparationTimeField = new TextField();
        preparationTimeField.setPromptText("Preparation Time (in minutes)");

        TextArea instructionsArea = new TextArea();
        instructionsArea.setPromptText("Write preparation instructions");

        Button saveRecipeBtn = new Button("Save Recipe");
        styleButton(saveRecipeBtn);

        saveRecipeBtn.setOnAction(event -> {
            String recipeName = recipeNameField.getText().trim();
            String ingredients = ingredientsArea.getText().trim();
            String prepTime = preparationTimeField.getText().trim();
            String instructions = instructionsArea.getText().trim();

            if (recipeName.isEmpty() || ingredients.isEmpty() || prepTime.isEmpty() || instructions.isEmpty()) {
                displayMessage("Error", "All fields must be completed.", Alert.AlertType.ERROR);
            } else if (storedRecipes.containsKey(recipeName)) {
                displayMessage("Error", "This recipe already exists.", Alert.AlertType.ERROR);
            } else {
                storedRecipes.put(recipeName, new String[]{ingredients, prepTime, instructions});
                displayMessage("Success", "Recipe added successfully.", Alert.AlertType.INFORMATION);
                addRecipeStage.close();
            }
        });

        VBox addRecipeLayout = new VBox(15, new Label("Add New Recipe"), recipeNameField, ingredientsArea, preparationTimeField, instructionsArea, saveRecipeBtn);
        addRecipeLayout.setStyle("-fx-padding: 30; -fx-spacing: 15;");

        addRecipeStage.setScene(new Scene(addRecipeLayout, 400, 400));
        addRecipeStage.show();
    }

    private void initializeSearchScreen() {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search for a Recipe");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter recipe name");

        TextArea searchResultArea = new TextArea();
        searchResultArea.setEditable(false);
        searchResultArea.setPromptText("Search results will appear here...");

        Button searchButton = new Button("Search Recipe");
        styleButtons(searchButton);

        searchButton.setOnAction(event -> {
            String recipeName = searchField.getText().trim();
            if (storedRecipes.containsKey(recipeName)) {
                String[] recipeDetails = storedRecipes.get(recipeName);
                searchResultArea.setText(
                        "Recipe Name: " + recipeName +
                                "\nIngredients: " + recipeDetails[0] +
                                "\nPreparation Time: " + recipeDetails[1] + " minutes" +
                                "\n\nInstructions:\n" + recipeDetails[2]
                );
            } else {
                searchResultArea.setText("No recipe found with the name: " + recipeName);
            }
        });

        VBox searchLayout = new VBox(15, new Label("Search Recipes"), searchField, searchButton, searchResultArea);
        searchLayout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-spacing: 15;");

        searchStage.setScene(new Scene(searchLayout, 400, 350));
        searchStage.show();
    }

    private void initializeRecipeListScreen() {
        Stage listStage = new Stage();
        listStage.setTitle("View All Recipes");

        ListView<String> recipeNamesList = new ListView<>();
        recipeNamesList.getItems().addAll(storedRecipes.keySet());

        TextArea recipeDetailsArea = new TextArea();
        recipeDetailsArea.setEditable(false);

        recipeNamesList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String[] recipeDetails = storedRecipes.get(newVal);
                recipeDetailsArea.setText(
                        "Recipe Name: " + newVal +
                                "\nIngredients: " + recipeDetails[0] +
                                "\nPreparation Time: " + recipeDetails[1] + " minutes" +
                                "\n\nInstructions:\n" + recipeDetails[2]
                );
            }
        });

        HBox listLayout = new HBox(20, recipeNamesList, recipeDetailsArea);
        listLayout.setStyle("-fx-padding: 20; -fx-spacing: 20;");

        listStage.setScene(new Scene(listLayout, 600, 400));
        listStage.show();
    }

    private void initializeEditRecipeScreen() {
        Stage editStage = new Stage();
        editStage.setTitle("Edit an Existing Recipe");

        TextField recipeToEditField = new TextField();
        recipeToEditField.setPromptText("Enter recipe name to edit");

        TextArea newIngredientsArea = new TextArea();
        newIngredientsArea.setPromptText("Update ingredients (comma-separated)");

        TextField newPrepTimeField = new TextField();
        newPrepTimeField.setPromptText("Update preparation time (in minutes)");

        TextArea newInstructionsArea = new TextArea();
        newInstructionsArea.setPromptText("Update preparation instructions");

        Button saveChangesButton = new Button("Save Changes");
        styleButtons(saveChangesButton);

        saveChangesButton.setOnAction(event -> {
            String recipeName = recipeToEditField.getText().trim();

            if (storedRecipes.containsKey(recipeName)) {
                String newIngredients = newIngredientsArea.getText().trim();
                String newPrepTime = newPrepTimeField.getText().trim();
                String newInstructions = newInstructionsArea.getText().trim();

                if (newIngredients.isEmpty() || newPrepTime.isEmpty() || newInstructions.isEmpty()) {
                    displayMessage("Error", "All fields must be completed.", Alert.AlertType.ERROR);
                } else {
                    storedRecipes.put(recipeName, new String[]{newIngredients, newPrepTime, newInstructions});
                    displayMessage("Success", "Recipe updated successfully!", Alert.AlertType.INFORMATION);
                    editStage.close();
                }
            } else {
                displayMessage("Error", "Recipe not found.", Alert.AlertType.ERROR);
            }
        });

        VBox editLayout = new VBox(15, new Label("Edit Recipe"), recipeToEditField, newIngredientsArea, newPrepTimeField, newInstructionsArea, saveChangesButton);
        editLayout.setStyle("-fx-padding: 30; -fx-spacing: 15;");

        editStage.setScene(new Scene(editLayout, 400, 450));
        editStage.show();
    }

    private void displayMessage(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void styleButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setStyle("-fx-font-size: 14px; -fx-background-color: #007BFF; -fx-text-fill: white;");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
