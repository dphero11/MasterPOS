package myposapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


	class DeleteUser extends JFrame{


		/**
		 *
		 */
		private static final long serialVersionUID = 7126848177943911855L;
		private JTextField textFieldDelUser;
		private JPanel delpanel;
		private JButton delbtn;
		private JLabel lblDelUser;

		Connection con;
		PreparedStatement dlpst;
		int rsd;


	DeleteUser(){

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("DELETE USER");
	setBounds(50,50,480,220);
	setLocationRelativeTo(null);
	getContentPane().setLayout(null);
	setResizable(false);
	setVisible(false);

	delpanel = new JPanel();
	delpanel.setBackground(Color.BLUE);
	delpanel.setBounds(6, 6, 468, 180);
	getContentPane().add(delpanel);
	delpanel.setLayout(null);

	delbtn = new JButton("DELETE USER ACCOUNT");
	delbtn.setFont(new Font("Lucida Grande", Font.BOLD, 13));
	delbtn.setBackground(Color.DARK_GRAY);
	delbtn.setBounds(137, 129, 205, 22);
	delpanel.add(delbtn);

	textFieldDelUser = new JTextField();
	textFieldDelUser.setBounds(137, 62, 205, 39);
	delpanel.add(textFieldDelUser);
	textFieldDelUser.setColumns(10);

	lblDelUser = new JLabel("Enter Username :");
	lblDelUser.setBounds(19, 71, 119, 21);
	delpanel.add(lblDelUser);
	delbtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {


			try {

				String delUser = textFieldDelUser.getText();
			
					
					con = SaleSystem.connect();

					String delquery = "DELETE FROM Users WHERE `Username` = ?";


					dlpst = con.prepareStatement(delquery);

					dlpst.setString(1,delUser);

					rsd = dlpst.executeUpdate();
					
					if(!(rsd==0)) {
						
						JOptionPane.showMessageDialog(null,"User Deleted","Record Deleted",JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						System.out.println(" This really run");
						
					}else {
						
						JOptionPane.showMessageDialog( null," No Record Found ! ","Delete Failed  ",JOptionPane.ERROR_MESSAGE);

					}
					con.close();
					
					
				

			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}

			
			
			}

		});

	}



}
