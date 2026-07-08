import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lab2 extends JFrame implements ActionListener {
    private final JTextField display;
    private double firstNumber = 0;
    private String pendingOperator = "";
    private boolean startNewNumber = true;
    private boolean hasFirstNumber = false;

    public Lab2() {
        super("เครื่องคิดเลขสีชมพู");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(80, 220, 220));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        display = new JTextField("0");
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(90, 20, 60));
        display.setPreferredSize(new Dimension(280, 60));
        mainPanel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 8, 8));
        buttonPanel.setBackground(new Color(80, 220, 220));

        String[] labels = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+"};
        for (String label : labels) {
            JButton button = new JButton(label);
            button.setFont(new Font("SansSerif", Font.BOLD, 20));
            button.setFocusPainted(false);
            button.setBackground(new Color(255, 182, 193));
            button.setForeground(new Color(80, 20, 60));
            button.setOpaque(true);
            button.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 160), 2));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            appendDigit(command);
        } else if (command.equals(".")) {
            appendDecimal();
        } else if (command.equals("C")) {
            clear();
        } else if (command.equals("=")) {
            calculateResult();
        } else {
            handleOperator(command);
        }
    }

    private void appendDigit(String digit) {
        if (startNewNumber) {
            display.setText(digit);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + digit);
        }
    }

    private void appendDecimal() {
        String current = display.getText();
        if (!current.contains(".")) {
            if (startNewNumber) {
                display.setText("0.");
                startNewNumber = false;
            } else {
                display.setText(current + ".");
            }
        }
    }

    private void clear() {
        display.setText("0");
        firstNumber = 0;
        pendingOperator = "";
        startNewNumber = true;
        hasFirstNumber = false;
    }

    private void handleOperator(String operator) {
        if (!hasFirstNumber) {
            firstNumber = Double.parseDouble(display.getText());
            hasFirstNumber = true;
        } else if (!pendingOperator.isEmpty() && !startNewNumber) {
            double secondNumber = Double.parseDouble(display.getText());
            firstNumber = calculate(firstNumber, secondNumber, pendingOperator);
            display.setText(formatResult(firstNumber));
        }

        pendingOperator = operator;
        startNewNumber = true;
    }

    private void calculateResult() {
        if (!hasFirstNumber || pendingOperator.isEmpty() || startNewNumber) {
            return;
        }

        double secondNumber = Double.parseDouble(display.getText());
        double result = calculate(firstNumber, secondNumber, pendingOperator);
        display.setText(formatResult(result));
        firstNumber = result;
        pendingOperator = "";
        startNewNumber = true;
        hasFirstNumber = true;
    }

    private double calculate(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> (b == 0) ? Double.NaN : a / b;
            default -> b;
        };
    }

    private String formatResult(double value) {
        if (Double.isNaN(value)) {
            return "Error";
        }
        if (value == Math.rint(value)) {
            return String.format("%.0f", value);
        }
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lab2::new);
    }
}
