package myposapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;



	class Login extends JFrame {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField username_textField;
		private JPasswordField passwordField;

		 
		 PreparedStatement pst;
		 ResultSet rs;


			/**
			 * Create the frame.
			 */
		 		Login() {
				setResizable(false);
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				setBounds(200, 200, 664, 484);
				setLocationRelativeTo(null);
				setTitle("AMEN GROCERY STORE");
				contentPane = new JPanel();
				contentPane.setBackground(Color.LIGHT_GRAY);
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPane.setLayout(null);
				setContentPane(contentPane);
				setVisible(false);

				JLabel lblusername = new JLabel("Username :");
				lblusername.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
				lblusername.setBounds(188, 135, 81, 16);
				contentPane.add(lblusername);

				JLabel lblpass = new JLabel("Password :");
				lblpass.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
				lblpass.setBounds(193, 183, 91, 26);
				contentPane.add(lblpass);

				username_textField = new JTextField();
				username_textField.setBounds(279, 124, 130, 33);
				contentPane.add(username_textField);
				username_textField.setColumns(10);

				passwordField = new JPasswordField();
				passwordField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==KeyEvent.VK_ENTER) {

							logon();
						}
					}
				});
				passwordField.setBounds(279, 179, 130, 33);
				contentPane.add(passwordField);

				JButton login_btn = new JButton("Login");
				login_btn.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==KeyEvent.VK_ENTER) {

							logon();
						}
					}
				});
				login_btn.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
				//login_btn.setForeground(Color.BLACK);
				login_btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String txtusername = username_textField.getText();
						String txtpassword = String.valueOf(passwordField.getPassword());


									try {

									Connection con = SaleSystem.connect();

										String query  = "SELECT * FROM Users WHERE `Username` = '"+txtusername+"' AND `Password` = '"+txtpassword+"' ";


											pst = con.prepareStatement(query);

											rs = pst.executeQuery();

										if(rs.next()) {

											int rstat = rs.getInt(4);

												if(rstat == 0) {

													JOptionPane.showMessageDialog(null,"Admin Login Succesful","Login Accepted",JOptionPane.INFORMATION_MESSAGE);
													setVisible(false);

													AdminControl admin = new AdminControl();
													admin.setVisible(true);
												}else if(rstat== 1) {

													JOptionPane.showMessageDialog(null,"Cashier Login Succesful","Login Accepted",JOptionPane.INFORMATION_MESSAGE);
													setVisible(false);
													SaleSystem  salesys = new SaleSystem();
													salesys.setVisible(true);
												}

												
										}else {
											JOptionPane.showMessageDialog(null,"Incorrect Username or Password","Error",JOptionPane.ERROR_MESSAGE);
										}
									} catch (HeadlessException | SQLException e1) {
										
										e1.printStackTrace();
									}


					}
				});
				login_btn.setBackground(Color.BLUE);
				login_btn.setBounds(295, 245, 100, 29);
				contentPane.add(login_btn);
			}

		 		
		 		

 private void logon() {

	 	
	 
		String txtusername = username_textField.getText();
		String txtpassword = String.valueOf(passwordField.getPassword());

				

				try {

					Connection con = SaleSystem.connect();

						String query  = "SELECT * FROM Users WHERE `Username` = '"+txtusername+"' AND `Password` = '"+txtpassword+"' ";

							//con = DriverManager.getConnection();
							
							pst = con.prepareStatement(query);

							rs = pst.executeQuery();

						if(rs.next()) {

							int rstat = rs.getInt(4);

								if(rstat == 0) {

									JOptionPane.showMessageDialog(null,"Admin Login Succesful","Login Accepted",JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);

									AdminControl admin = new AdminControl();
									admin.setVisible(true);
								}else if(rstat== 1) {

									JOptionPane.showMessageDialog(null,"Cashier Login Succesful","Login Accepted",JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
									SaleSystem  salesys = new SaleSystem();
									salesys.setVisible(true);
								}


						}else {
							JOptionPane.showMessageDialog(null,"Incorrect Username or Password","Error",JOptionPane.ERROR_MESSAGE);
						}
						
						con.close();
						
					} catch (HeadlessException | SQLException e1) {
						
						e1.printStackTrace();
					}


 }



}
