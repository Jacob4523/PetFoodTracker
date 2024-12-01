import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

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

        return String.format("Monthly Food Usage Summary:\nHay: %.2f ounces\nPellets: %.2f ounces\nTreats: %d units",
                totalHay, totalPellets, totalTreats);
    }

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pet Food Tracker");

        Label hayLabel = new Label("Enter the amount of hay in ounces:");
        TextField hayInput = new TextField();
        Button addHayButton = new Button("Add Hay");
        addHayButton.setOnAction(e -> addHay(Double.parseDouble(hayInput.getText())));

        Label pelletsLabel = new Label("Enter the amount of pellets in ounces:");
        TextField pelletsInput = new TextField();
        Button addPelletsButton = new Button("Add Pellets");
        addPelletsButton.setOnAction(e -> addPellets(Double.parseDouble(pelletsInput.getText())));

        Label treatsLabel = new Label("Enter the number of treats:");
        TextField treatsInput = new TextField();
        Button addTreatsButton = new Button("Add Treats");
        addTreatsButton.setOnAction(e -> addTreats(Integer.parseInt(treatsInput.getText())));

        Button summaryButton = new Button("View Monthly Summary");
        Label summaryLabel = new Label();
        summaryButton.setOnAction(e -> summaryLabel.setText(summarizeMonthlyUsage()));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(hayLabel, hayInput, addHayButton, pelletsLabel, pelletsInput, addPelletsButton, treatsLabel, treatsInput, addTreatsButton, summaryButton, summaryLabel);

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}