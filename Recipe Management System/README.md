Recipe Management System:
This is a simple JavaFX application to manage recipes. It allows users to sign up, log in, and perform various operations like adding, editing, searching, and viewing recipes.

Features:
•	User Authentication: Users can securely sign up and log in.
•	Add Recipes: Add recipes with details such as ingredients, preparation time, and instructions.
•	Search Recipes: Search for recipes by their name.
•	View All Recipes: View a list of all saved recipes.
•	Edit Recipes: Modify or update existing recipes.

Tech Stack:
•	Programming Language: Java
•	Framework: JavaFX

File Structure:
src/
├── DatabaseConnection.java   // For handling database connections (optional placeholder)
├── RecipeController.java     // Handles recipe-related operations
├── RecipeManagementSystem.java // Main application file
├── style.css                 // Contains styles for the application
README.md                     // This documentation file

How to Run:

1.	Install Requirements:

o	Install Java Development Kit (JDK 11 or above).

o	Download and set up the JavaFX SDK on your system. Extract the SDK to a folder (e.g., C:\javafx-sdk-23.0.1).

2.	Run via Command Line:

Navigate to the directory containing RecipeManagementSystem.java and run the following commands:

•	Compile the application: 

:-	javac --module-path "C:\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml RecipeManagementSystem.java

•	Run the application: 

:-	java --module-path "C:\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml RecipeManagementSystem

NOTE: Replace this "C:\javafx-sdk-23.0.1\lib" with the actual path to your JavaFX SDK's lib folder.

3.	Better Alternative:
It is recommended to use Visual Studio Code (VS Code) for a smoother experience, as it simplifies running and debugging.

Future Improvements:
•	Add persistent storage using files or databases.
•	Categorize recipes into groups like Vegetarian, Non-Vegetarian, etc.
•	Enhance the user interface with better styling.
Screenshots:  
LOG IN PAGE: 
![alt text](<Screenshot 2024-12-10 170534.png>)

MAIN MENU: 
![alt text](<Screenshot 2024-12-10 170723.png>)

ADD RECIPE PAGE:
 ![alt text](<Screenshot 2024-12-10 170934.png>)

REST OF THE PAGES will be in DOCUMENTATION 😊.