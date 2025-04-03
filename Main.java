package application;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set application window icon
        try {
            File iconFile = new File("E:/JavaFx/WeddingPlannerJavaFx/everandafter.jpg");
            Image icon = new Image(iconFile.toURI().toString());
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Couldn't load window icon: " + e.getMessage());
        }

        showLoginPage(primaryStage);
    }

    private void showLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Wedding Planning System - Login");

        // Create main container with centered content
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40)); // Increased padding for better spacing
        mainContainer.setStyle("-fx-background-color: #f5f5f5;");

        // Create a centered content container to hold all elements
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(400);

        // Logo with centered alignment
        ImageView logo = null;
        try {
            File logoFile = new File("logo.png");
            if (logoFile.exists()) {
                Image logoImage = new Image(logoFile.toURI().toString());
                logo = new ImageView(logoImage);
                logo.setFitHeight(80);
                logo.setPreserveRatio(true);
                
                // Add spacing container above the logo
                Pane topSpacer = new Pane();
                topSpacer.setMinHeight(20); // Adjust this value as needed
                contentBox.getChildren().add(topSpacer);
            }
        } catch (Exception e) {
            System.out.println("Couldn't load logo: " + e.getMessage());
        }

        // Title and subtitle
        Text scenetitle = new Text("Wedding Planning System");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        scenetitle.setFill(Color.DARKBLUE);
        
        Text subtitle = new Text("Please login to continue");
        subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        // Login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 0);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 0);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 1);

        // Role selection
        Label roleLabel = new Label("Login as:");
        grid.add(roleLabel, 0, 2);

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton adminRadio = new RadioButton("Admin");
        adminRadio.setToggleGroup(roleGroup);
        adminRadio.setSelected(true);
        
        RadioButton staffRadio = new RadioButton("Staff");
        staffRadio.setToggleGroup(roleGroup);
        
        RadioButton customerRadio = new RadioButton("Customer");
        customerRadio.setToggleGroup(roleGroup);
        
        HBox roleBox = new HBox(10, adminRadio, staffRadio, customerRadio);
        grid.add(roleBox, 1, 2);

        Button btn = new Button("Sign in");
        btn.setStyle("-fx-background-color: #4a6fa5; -fx-text-fill: white;");
        grid.add(btn, 1, 3);

        // Registration link
        Hyperlink registerLink = new Hyperlink("New customer? Register here");
        grid.add(registerLink, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 5);
        
        // Login button action
        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            
            if (adminRadio.isSelected()) {
                if (username.equals("admin") && password.equals("123")) {
                    primaryStage.close();
                    new AdminPage(new Stage()).show();
                } else {
                    showError(actiontarget, "Invalid admin credentials");
                }
            } 
            else if (staffRadio.isSelected()) {
                if (username.equals("staff") && password.equals("staff1")) {
                    primaryStage.close();
                    new StaffPage(new Stage()).show();
                } else {
                    showError(actiontarget, "Invalid staff credentials");
                }
            }
            else if (customerRadio.isSelected()) {
                if (username.equals("Joe") && password.equals("1234")) {
                    primaryStage.close();
                    new CustomerPage(new Stage()).show();
                } else {
                    showError(actiontarget, "Invalid customer credentials");
                }
            }
        });

        registerLink.setOnAction(e -> {
            actiontarget.setFill(Color.GREEN);
            actiontarget.setText("Registration would be implemented here");
        });

        // Add components to content box
        if (logo != null) {
            contentBox.getChildren().addAll(logo, scenetitle, subtitle, grid);
        } else {
            contentBox.getChildren().addAll(scenetitle, subtitle, grid);
        }

        // Add bottom spacer for equal spacing
        Pane bottomSpacer = new Pane();
        bottomSpacer.setMinHeight(20); // Same as topSpacer
        contentBox.getChildren().add(bottomSpacer);

        // Add content box to main container
        mainContainer.getChildren().add(contentBox);
        
        // Set scene
        Scene scene = new Scene(mainContainer, 500, 500); // Increased height for better spacing
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(Text target, String message) {
        target.setFill(Color.FIREBRICK);
        target.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}