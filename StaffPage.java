package application;

import java.io.File;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

public class StaffPage {
    private Stage stage;

    public StaffPage(Stage stage) {
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
        stage.setTitle("Wedding Planning System - Staff Dashboard");

        // Create menu bar (limited options for staff)
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu clientsMenu = new Menu("Clients");
        Menu viewMenu = new Menu("View");
        
        // Add menu items
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> logout());
        
        fileMenu.getItems().addAll(
            new MenuItem("Change Password"),
            new SeparatorMenuItem(),
            logoutMenuItem
        );
        
        clientsMenu.getItems().addAll(
            new MenuItem("View Client Details"),
            new MenuItem("Add Booking")
        );
        
        viewMenu.getItems().addAll(
            new MenuItem("View Bookings"),
            new MenuItem("View Calendar")
        );
        
        menuBar.getMenus().addAll(fileMenu, clientsMenu, viewMenu);

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
        Label welcomeLabel = new Label("Welcome, Staff Member!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Create buttons
        Button viewClientsBtn = new Button("View Clients");
        Button addBookingBtn = new Button("Add Booking");
        Button viewCalendarBtn = new Button("View Calendar");
        Button viewReportsBtn = new Button("View Reports");
        
        // Style buttons to be equal size
        String buttonStyle = "-fx-min-width: 150px; -fx-pref-width: 150px; -fx-max-width: 150px; " +
                             "-fx-min-height: 60px; -fx-pref-height: 60px; -fx-max-height: 60px; " +
                             "-fx-font-size: 14px;";
        viewClientsBtn.setStyle(buttonStyle);
        addBookingBtn.setStyle(buttonStyle);
        viewCalendarBtn.setStyle(buttonStyle);
        viewReportsBtn.setStyle(buttonStyle);
        
        // Create horizontal button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(viewClientsBtn, addBookingBtn, viewCalendarBtn, viewReportsBtn);
        
        // Create calendar section
        Label calendarLabel = new Label("Event Calendar");
        calendarLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 0 0 10 0;");
        
        // Create date picker
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setStyle("-fx-font-size: 14px;");
        
        // Create calendar view (simple version - could be replaced with a proper calendar component)
        TextArea calendarView = new TextArea();
        calendarView.setEditable(false);
        calendarView.setStyle("-fx-font-size: 14px; -fx-min-height: 200px; -fx-pref-height: 200px;");
        calendarView.setText("No events scheduled for today.");
        
        // Update calendar view when date changes
        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            calendarView.setText("Events for " + selectedDate.toString() + ":\n\n" +
                               (selectedDate.equals(LocalDate.now()) ? 
                                "1. Meeting with client at 10:00 AM\n" +
                                "2. Venue inspection at 2:00 PM" : 
                                "No events scheduled"));
        });
        
        // Create calendar container
        VBox calendarBox = new VBox(10);
        calendarBox.setAlignment(Pos.TOP_CENTER);
        calendarBox.setPadding(new Insets(20));
        calendarBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-radius: 5px;");
        calendarBox.getChildren().addAll(calendarLabel, datePicker, calendarView);
        
        // Create main content container
        VBox mainContent = new VBox(30);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(20));
        mainContent.getChildren().addAll(welcomeLabel, buttonBox, calendarBox);

        // Create main layout
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainContent);
        
        // Set scene
        Scene scene = new Scene(root, 900, 700); // Increased size to accommodate calendar
        stage.setScene(scene);
        stage.show();
    }

    private void logout() {
        stage.close();
        new Main().start(new Stage());
    }

    public void setOnHidden(Object object) {
        // Can be implemented if needed
    }
}