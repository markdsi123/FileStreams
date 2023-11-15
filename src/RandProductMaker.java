import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class RandProductMaker extends JFrame {
    JPanel mainPnl, topPnl, centerPnl, bottomPnl;
    JButton exitBtn, addBtn,clearBtn;
    JLabel nameLbl,descLbl,idLbl,priceLbl,recordLbl;
    JTextField nameTF,descTF,idTF,priceTF,recordTF;
    int rec = 0;
    RandomAccessFile randomAccessFile;
    ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        RandProductMaker frame = new RandProductMaker();
    }
    public RandProductMaker()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screensize = kit.getScreenSize();

        int width = screensize.width;
        int height = screensize.height;
        setSize(width/3, height/2);
        setLocation(width/3,height/6);
        setTitle("Random Product Maker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createGUI();
        setVisible(true);
    }
    private void createGUI()
    {
        mainPnl = new JPanel();

        createTopPnl();
        createCenterPnl();
        createBottomPnl();

        mainPnl.setLayout(new BorderLayout());
        mainPnl.add(topPnl,BorderLayout.NORTH);
        mainPnl.add(centerPnl);
        mainPnl.add(bottomPnl,BorderLayout.SOUTH);

        add(mainPnl);
    }
    private void createTopPnl()
    {
        topPnl = new JPanel();
        topPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1)));
        recordLbl = new JLabel("Record counts: ");
        recordTF = new JTextField();
        recordTF.setText(String.valueOf(rec));
        recordTF.setEditable(false);
        recordTF.setBorder(null);

        topPnl.add(recordLbl);
        topPnl.add(recordTF);
    }
    private void createCenterPnl() {
        centerPnl = new JPanel();
        centerPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1),"Adding New Product"));
        centerPnl.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        nameLbl = new JLabel("Name: ");
        nameTF = new JTextField(15);

        descLbl = new JLabel("Description: ");
        descTF = new JTextField(15);

        idLbl = new JLabel("ID: ");
        idTF = new JTextField(15);

        priceLbl = new JLabel("Price: ");
        priceTF = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPnl.add(nameLbl, gbc);

        gbc.gridx = 1;
        centerPnl.add(nameTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPnl.add(descLbl, gbc);

        gbc.gridx = 1;
        centerPnl.add(descTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPnl.add(idLbl, gbc);

        gbc.gridx = 1;
        centerPnl.add(idTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        centerPnl.add(priceLbl, gbc);

        gbc.gridx = 1;
        centerPnl.add(priceTF, gbc);
    }
    private void createBottomPnl()
    {
        bottomPnl = new JPanel();
        addBtn = new JButton("Add");
        addBtn.setFont(new Font("Arial",Font.BOLD,14));
        addBtn.addActionListener(e -> {
            if (Product.isValid(nameTF, descTF, idTF,priceTF)) {
                try {
                    String name = Product.namePadding(nameTF.getText());
                    String desc = Product.descPadding(descTF.getText());
                    String id = Product.idPadding(idTF.getText());
                    double cost = Double.parseDouble(priceTF.getText());

                    try (RandomAccessFile randomAccessFile = new RandomAccessFile("src/ProductRecord.txt", "rw")) {
                        randomAccessFile.seek(randomAccessFile.length());
                        randomAccessFile.writeBytes(id + ", " + name + ", " + desc + ", " + String.format("%.2f", cost) + "\n");
                    } catch (IOException ae) {
                        ae.printStackTrace();
                    }

                    rec++;
                    recordTF.setText(String.valueOf(rec));
                    nameTF.setText("");
                    descTF.setText("");
                    idTF.setText("");
                    priceTF.setText("");
                    JOptionPane.showMessageDialog(null, "Record has been written to file", "Success!", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Make sure you complete all the fields");
            }
        });


        clearBtn=new JButton("Clear");
        clearBtn.addActionListener(e -> {
            clear();
        });

        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial",Font.BOLD,14));
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
        bottomPnl.add(addBtn);
        bottomPnl.add(exitBtn);
    }
    private void clear()
    {
        nameTF.setText("");
        descTF.setText("");
        idTF.setText("");
        priceTF.setText("");
    }

}