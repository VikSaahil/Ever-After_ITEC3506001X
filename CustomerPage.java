package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.collections.FXCollections;
import java.time.LocalDate;

public class CustomerPage {
    private Stage stage;

    public CustomerPage(Stage stage) {
        this.stage = stage;
        try {
            File iconFile = new File("E:/JavaFx/WeddingPlannerJavaFx/everandafter.jpg");
            Image icon = new Image(iconFile.toURI().toString());
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Couldn't load window icon: " + e.getMessage());
        }
    }

    public void show() {
        stage.setTitle("Wedding Planning System - Customer Portal");

        // Create menu bar (unchanged)
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu requestsMenu = new Menu("My Requests");
        Menu helpMenu = new Menu("Help");
        
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> logout());
        
        fileMenu.getItems().addAll(new MenuItem("My Profile"), new SeparatorMenuItem(), logoutMenuItem);
        requestsMenu.getItems().addAll(new MenuItem("View My Requests"), new MenuItem("Request History"));
        helpMenu.getItems().addAll(new MenuItem("Contact Support"), new MenuItem("FAQs"));
        menuBar.getMenus().addAll(fileMenu, requestsMenu, helpMenu);

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
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(spacer, logoutButton);

        // Create main content
        Label welcomeLabel = new Label("Welcome to Your Wedding Planning Portal!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ===== CENTERED CONTAINERS =====
        // Create a centered container for all boxes
        VBox centeredContainer = new VBox(20);
        centeredContainer.setAlignment(Pos.TOP_CENTER);
        centeredContainer.setPadding(new Insets(20));
        centeredContainer.setMaxWidth(800); // Limit the maximum width

        // Wedding Date Selection Section
        VBox dateBox = createStyledBox("Select Your Wedding Date:", new DatePicker());
        DatePicker datePicker = (DatePicker) dateBox.getChildren().get(1);
        datePicker.setValue(LocalDate.now().plusMonths(3));
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now().plusWeeks(2)) || 
                          date.isAfter(LocalDate.now().plusYears(1)));
            }
        });

        // Service Request Section
        VBox serviceBox = createStyledBox("Select Service Package:", new VBox(10));
        ToggleGroup serviceGroup = new ToggleGroup();
        RadioButton premiumRadio = new RadioButton("Premium Package");
        RadioButton budgetRadio = new RadioButton("Budget Friendly Package");
        premiumRadio.setToggleGroup(serviceGroup);
        budgetRadio.setToggleGroup(serviceGroup);
        premiumRadio.setSelected(true);
        ((VBox)serviceBox.getChildren().get(1)).getChildren().addAll(premiumRadio, budgetRadio);

        // Wedding Theme Section
        VBox themeBox = createStyledBox("Select Wedding Theme:", new ComboBox<>(
            FXCollections.observableArrayList(
                "Christian Wedding", "Hindu Wedding", "Buddhist Wedding",
                "Muslim Wedding", "Civil Ceremony", "Beach Wedding",
                "Garden Wedding", "Traditional Wedding"
            )
        ));
        ComboBox<String> themeComboBox = (ComboBox<String>) themeBox.getChildren().get(1);
        themeComboBox.getSelectionModel().selectFirst();

        // Calendar Section
        VBox calendarBox = createStyledBox("Your Wedding Calendar", new TextArea());
        TextArea calendarView = (TextArea) calendarBox.getChildren().get(1);
        calendarView.setEditable(false);
        calendarView.setStyle("-fx-font-size: 14px; -fx-min-height: 150px; -fx-pref-height: 150px;");
        calendarView.setText("No events scheduled yet. Your appointments will appear here after booking.");
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> updateCalendarView(calendarView, newDate));

        // Additional Requirements
        VBox requirementsBox = createStyledBox("Additional Requirements:", new TextArea());
        TextArea requirementsArea = (TextArea) requirementsBox.getChildren().get(1);
        requirementsArea.setPromptText("Enter any special requests or requirements...");

        // Submit Button
        Button submitButton = new Button("Proceed To Payment");
        submitButton.setStyle("-fx-background-color: #4a6fa5; -fx-text-fill: white; -fx-font-size: 16px;");
        submitButton.setPrefWidth(200);
        submitButton.setOnAction(e -> showPaymentDialog(
            datePicker.getValue(),
            premiumRadio.isSelected() ? "Premium" : "Budget",
            themeComboBox.getValue(),
            requirementsArea.getText(),
            calendarView
        ));

        // Add all components to the centered container
        centeredContainer.getChildren().addAll(
            welcomeLabel,
            dateBox,
            serviceBox,
            themeBox,
            calendarBox,
            requirementsBox,
            submitButton
        );

        // Create main layout
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(new ScrollPane(centeredContainer));
        
        // Set scene
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to create styled boxes
    private VBox createStyledBox(String title, javafx.scene.Node content) {
        Label label = new Label(title);
        if (content instanceof TextArea) {
            ((TextArea)content).setWrapText(true);
        }
        
        VBox box = new VBox(10, label, content);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #ffffff;");
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(600);
        return box;
    }

    private void showPaymentDialog(LocalDate weddingDate, String serviceType, 
                                 String theme, String requirements, TextArea calendarView) {
        Stage paymentStage = new Stage();
        paymentStage.setTitle("Payment Information");
        
        // Payment method selection
        Label paymentMethodLabel = new Label("Select Payment Method:");
        ToggleGroup paymentGroup = new ToggleGroup();
        
        RadioButton visaRadio = new RadioButton("VISA");
        visaRadio.setToggleGroup(paymentGroup);
        visaRadio.setSelected(true);
        
        RadioButton mastercardRadio = new RadioButton("Mastercard");
        mastercardRadio.setToggleGroup(paymentGroup);
        
        HBox paymentMethodBox = new HBox(20, visaRadio, mastercardRadio);
        paymentMethodBox.setAlignment(Pos.CENTER_LEFT);
        
        // Card details
        Label cardNumberLabel = new Label("Card Number:");
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("1234 5678 9012 3456");
        
        HBox cvvExpiryBox = new HBox(20);
        Label cvvLabel = new Label("CVV:");
        TextField cvvField = new TextField();
        cvvField.setPromptText("123");
        cvvField.setPrefWidth(80);
        
        Label expiryLabel = new Label("Expiry Date (MM/YY):");
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        expiryField.setPrefWidth(100);
        
        cvvExpiryBox.getChildren().addAll(cvvLabel, cvvField, expiryLabel, expiryField);
        
        // Submit payment button
        Button payButton = new Button("Confirm Payment");
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        payButton.setOnAction(payEvent -> {
            if (cardNumberField.getText().trim().isEmpty() || 
                cvvField.getText().trim().isEmpty() || 
                expiryField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", 
                         "Please fill in all payment details.");
                return;
            }
            
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "Your wedding has been booked!", 
                "Wedding Date: " + weddingDate + "\n" +
                "Service Package: " + serviceType + "\n" +
                "Wedding Theme: " + theme + "\n" +
                "Additional Requirements: " + (requirements.isEmpty() ? "None" : requirements) +
                "\n\nPayment Method: " + (visaRadio.isSelected() ? "VISA" : "Mastercard") +
                "\nLast 4 Digits: " + cardNumberField.getText().trim().substring(Math.max(0, cardNumberField.getText().trim().length() - 4))
            );
            
            updateCalendarView(calendarView, weddingDate);
            paymentStage.close();
        });
        
        VBox paymentLayout = new VBox(20);
        paymentLayout.setPadding(new Insets(20));
        paymentLayout.setAlignment(Pos.CENTER);
        paymentLayout.getChildren().addAll(
            paymentMethodLabel,
            paymentMethodBox,
            cardNumberLabel,
            cardNumberField,
            cvvExpiryBox,
            payButton
        );
        
        Scene paymentScene = new Scene(paymentLayout, 400, 300);
        paymentStage.setScene(paymentScene);
        paymentStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateCalendarView(TextArea calendarView, LocalDate weddingDate) {
        if (weddingDate != null) {
            String calendarText = "Wedding Day: " + weddingDate + "\n\n" +
                               "Upcoming Appointments:\n" +
                               "1. Initial Consultation - " + weddingDate.minusMonths(2) + "\n" +
                               "2. Venue Visit - " + weddingDate.minusMonths(1) + "\n" +
                               "3. Final Planning Meeting - " + weddingDate.minusWeeks(1);
            calendarView.setText(calendarText);
        }
    }

    private void logout() {
        stage.close();
        new Main().start(new Stage());
    }
}