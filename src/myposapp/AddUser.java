package myposapp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


class AddUser  extends JFrame{

	/**
	 *
	 */

	private static final long serialVersionUID = -8069772418452532300L;
	private JTextField textFieldSetUsername;
	private JTextField textFieldSetUserPassword;

	PreparedStatement svpst;
	Connection con;
	private JTextField textFieldSetStatus;

	 //Icon icon = new ImageIcon("C:\\setting.png");

	AddUser(){

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ADD USER");
		setBounds(50,100,540,400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(false);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 528, 360);
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel);
		panel.setLayout(null);

		textFieldSetUsername = new JTextField();
		textFieldSetUsername.setBounds(198, 74, 130, 26);
		panel.add(textFieldSetUsername);
		textFieldSetUsername.setColumns(10);

		JLabel lblSetUsersName = new JLabel("Enter a new username :");
		lblSetUsersName.setBounds(16, 79, 159, 16);
		panel.add(lblSetUsersName);

		textFieldSetUserPassword = new JTextField();
		textFieldSetUserPassword.setColumns(10);
		textFieldSetUserPassword.setBounds(198, 112, 130, 26);
		panel.add(textFieldSetUserPassword);

		JLabel lblSetUsersPassword = new JLabel("Set new user password :");
		lblSetUsersPassword.setBounds(16, 117, 159, 16);
		panel.add(lblSetUsersPassword);

		textFieldSetStatus = new JTextField();
		textFieldSetStatus.setColumns(10);
		textFieldSetStatus.setBounds(198, 145, 130, 26);
		panel.add(textFieldSetStatus);
		setResizable(true);
		setVisible(true);

		JLabel lblSetUserStatus = new JLabel("Set new user status :");
		lblSetUserStatus.setBounds(37, 150, 130, 16);
		panel.add(lblSetUserStatus);

		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			

				try {
					
					String newUsername = textFieldSetUsername.getText();
					String newUserPass = textFieldSetUserPassword.getText();
					String newUserStatus = textFieldSetStatus.getText();
					
					boolean n1 =  newUsername.isEmpty();
					boolean n2 = newUserPass.isEmpty();
					boolean n3 = newUserStatus.isEmpty();
					
					System.out.println(newUsername);
					
					System.out.println(n1);
					System.out.println(n2);
					System.out.println(n3);
					
					if (n1 || n2 || n3) {
						JOptionPane.showMessageDialog(null,"Please enter valid info"," Columns cannot be blank",JOptionPane.ERROR_MESSAGE);
						return;
						
					} else {
						connect();

						String svquery = "INSERT INTO `Users`(Username,Password,Status)VALUES(?,?,?)";


						svpst= con.prepareStatement(svquery);

						svpst.setString(1,newUsername);
						svpst.setString(2,newUserPass);
						svpst.setInt(3,Integer.parseInt(newUserStatus));

						svpst.executeUpdate();

						con.close();
					}
					

					


				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}catch(NumberFormatException e2) {
					
					JOptionPane.showMessageDialog(null,"Please enter valid info"," Columns cannot be blank",JOptionPane.ERROR_MESSAGE);
					return;
				}
					
					
				

				JOptionPane.showMessageDialog(null,"New User Added","Record Saved",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);

			}
		});
		btnSave.setBounds(208, 218, 117, 29);
		panel.add(btnSave);



	}


	     void connect() {

		 String url ="jdbc:mysql://localhost:3306/AGS";
		 String username ="root";
		 String password ="Hero1234";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con =  DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}


}