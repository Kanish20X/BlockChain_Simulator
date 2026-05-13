
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class BlockchainGUI {

    private static ArrayList<Block> blockchain = new ArrayList<>();
    private static int difficulty = 4;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Blockchain Simulator");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel inputPanel = new JPanel();
        JTextField dataField = new JTextField(15);
        JButton mineButton = new JButton("Mine Block");
        JButton validateButton = new JButton("Check Validity");
        JButton tamperButton = new JButton("Tamper Block");

        tamperButton.setBackground(Color.RED);
        tamperButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Data:"));
        inputPanel.add(dataField);
        inputPanel.add(mineButton);
        inputPanel.add(validateButton);
        inputPanel.add(tamperButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Mine Logic
        mineButton.addActionListener(e -> {
            String data = dataField.getText();
            if (data.isEmpty()) {
                return;
            }

            String prevHash = blockchain.isEmpty() ? "0" : blockchain.get(blockchain.size() - 1).hash;
            Block newBlock = new Block(data, prevHash);

            displayArea.append("Mining block " + (blockchain.size() + 1) + "...\n");
            newBlock.mineBlock(difficulty);
            blockchain.add(newBlock);

            displayArea.append("Success! Hash: " + newBlock.hash + "\n\n");
            dataField.setText("");
        });

        // Validate Logic
        validateButton.addActionListener(e -> {
            boolean isValid = isChainValid();
            String msg = isValid ? "Chain is Valid!" : "Chain is INVALID/TAMPERED!";
            JOptionPane.showMessageDialog(frame, msg);
        });

        // Tamper Logic
        tamperButton.addActionListener(e -> {
            if (blockchain.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No blocks to tamper!");
                return;
            }
            try {
                String idxStr = JOptionPane.showInputDialog(frame, "Enter Index (0-" + (blockchain.size() - 1) + "):");
                int idx = Integer.parseInt(idxStr);
                String newData = JOptionPane.showInputDialog(frame, "Enter fake data:");

                blockchain.get(idx).setData(newData);
                displayArea.append("!!! TAMPERED WITH BLOCK " + idx + " !!!\n\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error in input.");
            }
        });

        frame.setVisible(true);
    }

    public static Boolean isChainValid() {
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for (int i = 1; i < blockchain.size(); i++) {
            Block current = blockchain.get(i);
            Block previous = blockchain.get(i - 1);
            if (!current.hash.equals(current.calculateHash())) {
                return false;
            }
            if (!previous.hash.equals(current.previousHash)) {
                return false;
            }
            if (!current.hash.substring(0, difficulty).equals(hashTarget)) {
                return false;
            }
        }
        return true;
    }
}
