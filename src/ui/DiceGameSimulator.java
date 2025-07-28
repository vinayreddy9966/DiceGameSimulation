package ui;

import game.DiceGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class DiceGameSimulator extends JFrame {

    private JTextArea resultArea;
    private JTextField diceField, simField;

    public DiceGameSimulator() {
        setTitle("Dice Game Simulator");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel diceLabel = new JLabel("Number of Dice:");
        diceField = new JTextField("5", 5);

        JLabel simLabel = new JLabel("Number of Simulations:");
        simField = new JTextField("10000", 10);

        JButton runButton = new JButton("Run Simulation");
        runButton.addActionListener(this::runSimulation);

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(diceLabel);
        inputPanel.add(diceField);
        inputPanel.add(simLabel);
        inputPanel.add(simField);
        inputPanel.add(runButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void runSimulation(ActionEvent e) {
        int numDice, numSim;
        try {
            numDice = Integer.parseInt(diceField.getText().trim());
            numSim = Integer.parseInt(simField.getText().trim());
            if (numDice <= 0 || numSim <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid positive integers.");
            return;
        }

        resultArea.setText("Running simulation...\n");

        long start = System.currentTimeMillis();
        Map<Integer, Integer> result = DiceGame.simulateGames(numSim, numDice);
        long end = System.currentTimeMillis();
        double duration = (end - start) / 1000.0;

        resultArea.append("Number of simulations was " + numSim + " using " + numDice + " dice.\n");
        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            int score = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / numSim;
            resultArea.append(String.format("Total %d occurs %.2f occurred %d times.\n", score, probability, count));
        }
        resultArea.append(String.format("Total simulation took %.2f seconds.\n", duration));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DiceGameSimulator().setVisible(true);
        });
    }
}
