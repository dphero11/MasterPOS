package myposapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddStock extends JFrame {

	/**
	 * Declaration of variables.
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textProdname;
	private JTextField textQuantity;
	private JTextField textPrice;
	private JFileChooser upfile;
	private JPanel panel;
	private JLabel lblProdname;
	private JLabel lblQuantity;
	private JLabel lblPrice;
	private JLabel lbl_ImageIns;
	private JLabel lblPic;
	private JButton btnUpload;
	private JButton btnAddStock;
	private File file = null;

	Connection con;
	Random random;

	/**
	 * Constructor to initialize frame and its components.
	 */

	AddStock(){

		setTitle("ADD NEW PRODUCT(S)");
		getContentPane().setLayout(null);
		setVisible(false);
		setResizable(false);
		setBounds(200, 200, 664, 365);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(6, 0, 652, 333);
		getContentPane().add(panel);
		panel.setLayout(null);

		textProdname = new JTextField();
		textProdname.setColumns(10);
		textProdname.setBounds(34, 76, 101, 26);
		panel.add(textProdname);

		textQuantity = new JTextField();
		textQuantity.setColumns(10);
		textQuantity.setBounds(202, 76, 101, 26);
		panel.add(textQuantity);

		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(359, 76, 101, 26);
		panel.add(textPrice);

		lblProdname = new JLabel("Prod_Name");
		lblProdname.setBounds(43, 43, 76, 16);
		panel.add(lblProdname);

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(213, 43, 61, 16);
		panel.add(lblQuantity);

		lblPrice = new JLabel("Price");
		lblPrice.setBounds(384, 43, 47, 16);
		panel.add(lblPrice);

		lbl_ImageIns = new JLabel("Prod_Image");
		lbl_ImageIns.setBounds(544, 43, 81, 16);
		panel.add(lbl_ImageIns);

		lblPic = new JLabel("");
		lblPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblPic.setBounds(234, 116, 179, 131);
		panel.add(lblPic);

		btnUpload = new JButton("UPLOAD");
		btnUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				upfile = new JFileChooser("/Users/user/Desktop/ImageIcons/Products");

				upfile.setAcceptAllFileFilterUsed(true);
				FileNameExtensionFilter extfilter = new FileNameExtensionFilter(".png",".jpg");
				upfile.addChoosableFileFilter(extfilter);

				int response = upfile.showDialog(null,"Attach");

				if(response==JFileChooser.APPROVE_OPTION) {

					file = new File(upfile.getSelectedFile(),"");

					btnUpload.setText("DONE !");
					btnUpload.setForeground(Color.BLUE);

					String filepath = file.getPath();

					Icon pic = new ImageIcon(new ImageIcon(filepath).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					lblPic.setIcon(pic);

				}

			}
		});
		btnUpload.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnUpload.setBounds(518, 71, 117, 34);
		panel.add(btnUpload);

		btnAddStock = new JButton("ADD NEW STOCK");
		btnAddStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String prodname = textProdname.getText();
				String pquantity = textQuantity.getText();
				String price = textPrice.getText();
				FileInputStream selected_file = null;
				
				if(prodname.length()==0||pquantity.length()==0||price.length()==0) {
					
					JOptionPane.showMessageDialog(null,"Field(s) cannot be empty !","ERROR",JOptionPane.ERROR_MESSAGE);
				}else {
					

					try {

						if(file != null) {

							selected_file = new FileInputStream(upfile.getSelectedFile());

						}else {

							selected_file = null;

							}

					} catch (FileNotFoundException e2) {

						e2.printStackTrace();
					}


						String mainrand = myrandom();

						con = SaleSystem.connect();

					try {

						String addquery = "INSERT INTO Inventory_2(Prod_Code,Prod_Name,Quantity,Price,Picture)VALUES(?,?,?,?,?)";
						PreparedStatement pst = con.prepareStatement(addquery);

						pst.setString(1,mainrand);
						pst.setString(2,prodname);
						pst.setString(3,pquantity);
						pst.setString(4,price);
						pst.setBinaryStream(5,selected_file);
						pst.execute();

						con.close();

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

						 JOptionPane.showMessageDialog(null,"Product with ID "+mainrand+" Saved !","Done",JOptionPane.INFORMATION_MESSAGE);

						 textProdname.setText("");
						 textQuantity.setText("");
						 textPrice.setText("");
						 btnUpload.setText("UPLOAD");
						 btnUpload.setForeground(Color.BLACK);
						 lblPic.setIcon(null);
					
				}
					
			}
		});

		btnAddStock.setBounds(167, 259, 314, 45);
		panel.add(btnAddStock);

	}
	/**
	 * This method returns an auto generated product code for
	 * each new product added to stock. Using Math.random() method.
	 *
	 */
	public String myrandom() {

		int min = 1111;
		int max = 9999;
		int random2 = (int) Math.floor(Math.random()*(max-min+1)+min);
		String prescript = "PO";
		String pcodeins = prescript+random2;

		return pcodeins;

	}

}
