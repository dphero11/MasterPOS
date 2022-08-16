package myposapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class UpdateInventory extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -5562155871262465209L;


	private JTextField textField_Prodname;
	private JTextField textField_Prodcode;
	private JTextField textField_Qty;
	private JTextField textField_Price;

	Connection con;


		UpdateInventory() {

		setTitle("UPDATE INVENTORY");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(450,301);
		setLocationRelativeTo(getParent());
		setVisible(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 261);
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel);
		panel.setLayout(null);


		JLabel lbl_Prodcode = new JLabel("Prod_Code");
		lbl_Prodcode.setBounds(37, 11, 71, 16);
		panel.add(lbl_Prodcode);

		JLabel lbl_Prodname = new JLabel("Prod_Name");
		lbl_Prodname.setBounds(133, 11, 71, 16);
		panel.add(lbl_Prodname);

		JLabel lbl_Quantity = new JLabel("Quantity");
		lbl_Quantity.setBounds(247, 11, 66, 16);
		panel.add(lbl_Quantity);

		JLabel lbl_Price = new JLabel("Price");
		lbl_Price.setBounds(344, 11, 47, 16);
		panel.add(lbl_Price);

		textField_Price = new JTextField();
		textField_Price.setEditable(false);
		textField_Price.setColumns(10);
		textField_Price.setBounds(330, 39, 71, 26);
		panel.add(textField_Price);

		textField_Qty = new JTextField();
		textField_Qty.setEditable(true);
		textField_Qty.setColumns(10);
		textField_Qty.setBounds(236, 39, 87, 26);
		panel.add(textField_Qty);

		textField_Prodname = new JTextField();
		textField_Prodname.setEditable(false);
		textField_Prodname.setColumns(10);
		textField_Prodname.setBounds(126, 39, 98, 26);
		panel.add(textField_Prodname);

		textField_Prodcode = new JTextField();
		textField_Prodcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {

					String prod_code = textField_Prodcode.getText().trim();

					 con = SaleSystem.connect();
					String query01 = "SELECT * FROM Inventory_2 WHERE Prod_Code = ? ";
					try {

						PreparedStatement pstmt = con.prepareStatement(query01);
						pstmt.setString(1, prod_code);
						ResultSet rs= pstmt.executeQuery();


						if(!rs.next()) {
							JOptionPane.showMessageDialog(null,"Product Code Not Found","ERROR",JOptionPane.ERROR_MESSAGE);
						}else {
							String prodname  = rs.getString("Prod_Name");
							String    price = rs.getString("Price");

							textField_Prodname.setText(prodname);
							textField_Price.setText(price);
						}


					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				}
			}
		});
		textField_Prodcode.setEditable(true);
		textField_Prodcode.setColumns(10);
		textField_Prodcode.setBounds(37, 39, 71, 26);
		panel.add(textField_Prodcode);

		JButton btnUpdateStock = new JButton("UPDATE STOCK");
		btnUpdateStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String tname = textField_Prodname.getText();
				String tqty = textField_Qty.getText();
				con = SaleSystem.connect();
				String query02 = "UPDATE Inventory_2 SET Quantity = Quantity +'"+tqty+"' WHERE Prod_Name = '"+tname+"'";

				try {

					PreparedStatement pstmt2 = con.prepareStatement(query02);
					pstmt2.executeUpdate();
					con.close();
					JOptionPane.showMessageDialog(null,"SUCCESS","Columns Updated",JOptionPane.INFORMATION_MESSAGE);

					textField_Prodcode.setText("");
					textField_Prodname.setText("");
					textField_Qty.setText("");
					textField_Price.setText("");


				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnUpdateStock.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdateStock.setBounds(133, 122, 164, 43);
		panel.add(btnUpdateStock);



	}







}
