import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class PetFoodTracker extends Application {
    private double totalHay;
    private double totalPellets;
    private int totalTreats;
    private int daysInMonth;
    private int selectedMonth;

    public PetFoodTracker() {
        this.totalHay = 0;
        this.totalPellets = 0;
        this.totalTreats = 0;
    }

    public void setMonth(int month) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        this.daysInMonth = yearMonth.lengthOfMonth();
        this.selectedMonth = month;
        this.totalHay = 0;
        this.totalPellets = 0;
        this.totalTreats = 0;
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

    public void clearTotals() {
        totalHay = 0;
        totalPellets = 0;
        totalTreats = 0;
    }

    public String summarizeMonthlyUsage() {
        int divisor = (selectedMonth == 2) ? 28 : daysInMonth;
        double averageHay = totalHay / divisor;
        double averagePellets = totalPellets / divisor;
        double averageTreats = (double) totalTreats / divisor;

        return String.format("Monthly Food Usage Summary:\nHay: %.2f ounces\nPellets: %.2f ounces\nTreats: %d units\n\nAverage Daily Usage:\nHay: %.2f ounces\nPellets: %.2f ounces\nTreats: %.2f units",
                totalHay, totalPellets, totalTreats, averageHay, averagePellets, averageTreats);
    }

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pet Food Tracker");

        Label monthLabel = new Label("Select month:");
        ComboBox<String> monthComboBox = new ComboBox<>();
        for (int i = 1; i <= 12; i++) {
            String monthName = YearMonth.of(0, i).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            monthComboBox.getItems().add(monthName);
        }

        Button setMonthButton = new Button("Set Month");
        Label setMonthMessage = new Label();
        setMonthButton.setOnAction(e -> {
            try {
                String monthName = monthComboBox.getValue();
                int month = YearMonth.of(0, 1).getMonth().getValue();
                for (int i = 1; i <= 12; i++) {
                    if (YearMonth.of(0, i).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).equals(monthName)) {
                        month = i;
                        break;
                    }
                }
                setMonth(month);
                setMonthMessage.setText("Month set successfully!");
            } catch (NullPointerException ex) {
                setMonthMessage.setText("An error occurred");
            }
        });

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

        Button clearButton = new Button("Clear Totals");
        Label clearMessage = new Label();
        clearButton.setOnAction(e -> {
            clearTotals();
            clearMessage.setText("Totals cleared successfully!");
        });

        Button summaryButton = new Button("View Monthly Summary");
        Label summaryLabel = new Label();
        summaryButton.setOnAction(e -> summaryLabel.setText(summarizeMonthlyUsage()));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(monthLabel, monthComboBox, setMonthButton, setMonthMessage, hayLabel, hayInput, addHayButton, hayMessage, pelletsLabel, pelletsInput, addPelletsButton, pelletsMessage, treatsLabel, treatsInput, addTreatsButton, treatsMessage, clearButton, clearMessage, summaryButton, summaryLabel);

        Scene scene = new Scene(layout, 300, 760);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}