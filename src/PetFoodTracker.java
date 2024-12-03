import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PetFoodTracker extends Application {
    private double totalHay;
    private double totalPellets;
    private int totalTreats;
    private LocalDate startDate;

    public PetFoodTracker() {
        this.totalHay = 0;
        this.totalPellets = 0;
        this.totalTreats = 0;
        this.startDate = LocalDate.now();
    }

    public void addHay(double amount) {
        totalHay += amount;
    }

    public void addPellets(double amount) {
        totalPellets += amount;
    }

    public void addTreats(int amount) {
        totalTreats += amount;
    }

    public String summarizeMonthlyUsage() {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getMonthValue() != startDate.getMonthValue() || currentDate.getYear() != startDate.getYear()) {
            return "Monthly summary is not available for a different month.";
        }

        long daysElapsed = ChronoUnit.DAYS.between(startDate, currentDate) + 1; 
        double averageHay = totalHay / daysElapsed;
        double averagePellets = totalPellets / daysElapsed;
        double averageTreats = (double) totalTreats / daysElapsed;

        return String.format("Monthly Food Usage Summary:\nHay: %.2f ounces\nPellets: %.2f ounces\nTreats: %d units\n\nAverage Daily Usage:\nHay: %.2f ounces\nPellets: %.2f ounces\nTreats: %.2f units",
                totalHay, totalPellets, totalTreats, averageHay, averagePellets, averageTreats);
    }

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pet Food Tracker");

        Label hayLabel = new Label("Enter the amount of hay in ounces:");
        TextField hayInput = new TextField();
        Label hayMessage = new Label();
        Button addHayButton = new Button("Add Hay");
        addHayButton.setOnAction(e -> {
            try {
                addHay(Double.parseDouble(hayInput.getText()));
                hayMessage.setText("Added successfully!");
            } catch (NumberFormatException ex) {
                hayMessage.setText("An error occurred");
            }
            hayInput.clear();
        });

        Label pelletsLabel = new Label("Enter the amount of pellets in ounces:");
        TextField pelletsInput = new TextField();
        Label pelletsMessage = new Label();
        Button addPelletsButton = new Button("Add Pellets");
        addPelletsButton.setOnAction(e -> {
            try {
                addPellets(Double.parseDouble(pelletsInput.getText()));
                pelletsMessage.setText("Added successfully!");
            } catch (NumberFormatException ex) {
                pelletsMessage.setText("An error occurred");
            }
            pelletsInput.clear();
        });

        Label treatsLabel = new Label("Enter the number of treats:");
        TextField treatsInput = new TextField();
        Label treatsMessage = new Label();
        Button addTreatsButton = new Button("Add Treats");
        addTreatsButton.setOnAction(e -> {
            try {
                addTreats(Integer.parseInt(treatsInput.getText()));
                treatsMessage.setText("Added successfully!");
            } catch (NumberFormatException ex) {
                treatsMessage.setText("An error occurred");
            }
            treatsInput.clear();
        });

        Button summaryButton = new Button("View Monthly Summary");
        Label summaryLabel = new Label();
        summaryButton.setOnAction(e -> summaryLabel.setText(summarizeMonthlyUsage()));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(hayLabel, hayInput, addHayButton, hayMessage, pelletsLabel, pelletsInput, addPelletsButton, pelletsMessage, treatsLabel, treatsInput, addTreatsButton, treatsMessage, summaryButton, summaryLabel);

        Scene scene = new Scene(layout, 300, 580);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}