import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

// Main class for Weekly Expense Tracker
public class WeeklyExpenseTracker extends JFrame {

    // GUI Components section
    JComboBox<String> dayBox, categoryBox;
    JTextField amountField;
    JTextArea descriptionArea, outputArea;

    // Store expenses
    ArrayList<Expense> expenseList = new ArrayList<>();

    // Constructor
    public WeeklyExpenseTracker() {

        // Window settings
        setTitle("Weekly Expense Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Day dropdown
        String[] days = {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        };

        // Category dropdown
        String[] categories = {
                "Groceries",
                "Eating Out",
                "Petrol",
                "Taxi",
                "Bills",
                "Rent",
                "Others"
        };

        // Top panel
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Day
        panel.add(new JLabel("Choose Day:"));
        dayBox = new JComboBox<>(days);
        panel.add(dayBox);

        // Category
        panel.add(new JLabel("Choose Category:"));
        categoryBox = new JComboBox<>(categories);
        panel.add(categoryBox);

        // Amount
        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        // Description
        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        panel.add(new JScrollPane(descriptionArea));

        // Buttons
        JButton addButton = new JButton("Add Expense");
        JButton totalButton = new JButton("Total Weekly Expense");

        panel.add(addButton);
        panel.add(totalButton);

        add(panel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Bottom button
        JButton viewButton = new JButton("View Expenses Grouped by Category");

        add(viewButton, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addExpense());

        totalButton.addActionListener(e -> showTotal());

        viewButton.addActionListener(e -> showByCategory());
    }

    // Add expense function
    public void addExpense() {

        try {

            String day = dayBox.getSelectedItem().toString();

            String category = categoryBox.getSelectedItem().toString();

            String amountText = amountField.getText().trim();

            String description = descriptionArea.getText().trim();

            // Empty amount check
            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please enter amount.");

                return;
            }

            // Convert amount
            double amount = Double.parseDouble(amountText);

            // Negative amount check
            if (amount <= 0) {

                JOptionPane.showMessageDialog(this,
                        "Amount must be greater than 0.");

                return;
            }

            // Empty description
            if (description.isEmpty()) {

                description = "No description";
            }

            // Add to list
            expenseList.add(
                    new Expense(day, category, amount, description)
            );

            JOptionPane.showMessageDialog(this,
                    "Expense Added Successfully!");

            // Clear fields
            amountField.setText("");
            descriptionArea.setText("");

        }

        // Invalid number check
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number.");
        }
    }

    // Total expense function
    public void showTotal() {

        double total = 0;

        for (Expense expense : expenseList) {

            total += expense.amount;
        }

        outputArea.setText(
                "Total Weekly Expense: $"
                        + String.format("%.2f", total)
        );
    }

    // Group expenses by category
    public void showByCategory() {

        // Empty list check
        if (expenseList.isEmpty()) {

            outputArea.setText("No expenses added.");

            return;
        }

        String[] categories = {
                "Groceries",
                "Eating Out",
                "Petrol",
                "Taxi",
                "Bills",
                "Rent",
                "Others"
        };

        StringBuilder result = new StringBuilder();

        // Loop through categories
        for (String category : categories) {

            result.append("\nCategory: ")
                    .append(category)
                    .append("\n");

            double categoryTotal = 0;

            boolean found = false;

            // Loop through expenses
            for (Expense expense : expenseList) {

                if (expense.category.equals(category)) {

                    result.append("Day: ")
                            .append(expense.day)

                            .append(" | Amount: $")
                            .append(String.format("%.2f", expense.amount))

                            .append(" | Description: ")
                            .append(expense.description)

                            .append("\n");

                    categoryTotal += expense.amount;

                    found = true;
                }
            }

            // If found
            if (found) {

                result.append("Category Total: $")
                        .append(String.format("%.2f", categoryTotal))
                        .append("\n");
            }

            // If not found
            else {

                result.append("No expense in this category.\n");
            }
        }

        // Show output
        outputArea.setText(result.toString());
    }

    // Main method starts program
    public static void main(String[] args) {

        WeeklyExpenseTracker app = new WeeklyExpenseTracker();

        app.setVisible(true);
    }
}

// Expense class
class Expense {

    String day;
    String category;
    double amount;
    String description;

    // Constructor
    public Expense(String day,
                   String category,
                   double amount,
                   String description) {

        this.day = day;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
}
