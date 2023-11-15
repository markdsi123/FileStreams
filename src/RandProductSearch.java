import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch {
    private JFrame frame;
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Random Product Search");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(3, 1, 10, 10));

        frame.getContentPane().add(new JLabel("Enter Partial Product Name:"));
        searchField = new JTextField();
        frame.getContentPane().add(searchField);

        JButton searchButton = new JButton("Search");
        frame.getContentPane().add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.getContentPane().add(scrollPane);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });

        frame.setVisible(true);
    }

    private void searchProducts() {
        try {
            String partialName = searchField.getText().trim().toLowerCase();

            // Open the RandomAccessFile in read-only mode
            RandomAccessFile randomAccessFile = new RandomAccessFile("productData.dat", "r");

            // Read records and display matching products
            resultArea.setText(""); // Clear previous results

            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String record = randomAccessFile.readUTF();

                // Parse the record into a Product object
                Product product = parseRecord(record);

                // Check if the product name contains the partial name
                if (product != null && product.getName().toLowerCase().contains(partialName)) {
                    resultArea.append(product.toString() + "\n");
                }
            }

            // Close the RandomAccessFile
            randomAccessFile.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Product parseRecord(String record) {
        try {
            String name = record.substring(0, 35).trim();
            String description = record.substring(35, 110).trim();
            String id = record.substring(110, 116).trim();

            // Extract the cost part and remove any non-numeric characters
            String costStr = record.substring(116).replaceAll("[^0-9.]", "");
            double cost = Double.parseDouble(costStr);

            return new Product(name, description, id, cost);
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            // Handle the exception or log it
            ex.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RandProductSearch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}