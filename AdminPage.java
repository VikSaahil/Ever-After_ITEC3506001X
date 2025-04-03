package application;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminPage {
    private Stage stage;

    public AdminPage(Stage stage) {
        this.stage = stage;
        // Set window icon in constructor
        try {
            File iconFile = new File("E:/JavaFx/WeddingPlannerJavaFx/everandafter.jpg");
            Image icon = new Image(iconFile.toURI().toString());
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Couldn't load window icon: " + e.getMessage());
        }
    }

    public void show() {
        stage.setTitle("Wedding Planning System - Admin Portal");

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu manageMenu = new Menu("Management");
        Menu helpMenu = new Menu("Help");
        
        // Add menu items
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> logout());
        
        fileMenu.getItems().addAll(
            new MenuItem("Settings"),
            new SeparatorMenuItem(),
            logoutItem
        );
        
        manageMenu.getItems().addAll(
            new MenuItem("Manage Staff"),
            new MenuItem("Manage Customers"),
            new MenuItem("View Reports")
        );
        
        helpMenu.getItems().addAll(
            new MenuItem("Help Documentation"),
            new MenuItem("About")
        );
        
        menuBar.getMenus().addAll(fileMenu, manageMenu, helpMenu);

        // Create logout button for top right
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

        // Create top bar with menu and logout button
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(5, 10, 5, 10));
        topBar.setSpacing(10);
        topBar.setStyle("-fx-background-color: #f8f8f8;");
        topBar.getChildren().addAll(menuBar);
        
        // Add spacer to push logout button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(spacer, logoutButton);

        // Create main content
        Label welcomeLabel = new Label("Welcome, Administrator!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Admin dashboard with horizontal buttons
        Button manageStaffBtn = new Button("Manage Staff");
        Button manageCustomersBtn = new Button("Manage Customers");
        Button viewReportsBtn = new Button("View Reports");
        
        // Style buttons
        String buttonStyle = "-fx-background-color: #4a6fa5; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-pref-height: 60px;";
        manageStaffBtn.setStyle(buttonStyle);
        manageCustomersBtn.setStyle(buttonStyle);
        viewReportsBtn.setStyle(buttonStyle);

        // Create horizontal button container
        HBox buttonBox = new HBox(20, manageStaffBtn, manageCustomersBtn, viewReportsBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        // Create banner image
        ImageView banner = new ImageView();
        try {
            // Replace with your actual banner image path
            File bannerFile = new File("E:/JavaFx/WeddingPlannerJavaFx/banner.jpg");
            Image bannerImage = new Image(bannerFile.toURI().toString());
            banner.setImage(bannerImage);
            
            // Set banner properties
            banner.setPreserveRatio(true);
            banner.setFitWidth(800); // Set maximum width
            banner.setSmooth(true);
            banner.setCache(true);
        } catch (Exception e) {
            System.out.println("Couldn't load banner image: " + e.getMessage());
            // Fallback if image fails to load
            banner = new ImageView();
            banner.setStyle("-fx-background-color: #e0e0e0; -fx-min-width: 800px; -fx-min-height: 150px;");
            Label fallbackText = new Label("Wedding Planning System - Admin Portal");
            fallbackText.setStyle("-fx-font-size: 24px; -fx-text-fill: #666666;");
            StackPane fallbackBanner = new StackPane(fallbackText);
            fallbackBanner.setStyle("-fx-background-color: #e0e0e0; -fx-min-width: 800px; -fx-min-height: 150px;");
            banner = new ImageView();
            banner.setImage(null);
            banner.setStyle("-fx-background-color: #e0e0e0; -fx-min-width: 800px; -fx-min-height: 150px;");
        }

        // Create container for banner to center it
        StackPane bannerContainer = new StackPane(banner);
        bannerContainer.setPadding(new Insets(20, 0, 40, 0)); // Add some vertical spacing
        bannerContainer.setAlignment(Pos.CENTER);

        // Create main layout with all components
        VBox mainContent = new VBox(10, welcomeLabel, buttonBox, bannerContainer);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(20));
        
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainContent);
        
        // Set scene
        Scene scene = new Scene(root, 900, 700); // Increased height to accommodate banner
        stage.setScene(scene);
        stage.show();
    }

    private void logout() {
        stage.close();
        new Main().start(new Stage());
    }
}