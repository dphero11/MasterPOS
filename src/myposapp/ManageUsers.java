package myposapp;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


	class ManageUsers extends JFrame {

		/**
		 *
		 */
		private static final long serialVersionUID = 2794397404984740834L;

		PreparedStatement delpst;
		PreparedStatement pst1;

	/**
	 *   Create the frame
	 */

		ManageUsers(){

			new JFrame();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("User Account Settings");
			setVisible(false);
			setSize(620,320);
			setLocationRelativeTo(getParent());
			setResizable(false);
			getContentPane().setLayout(null);

			JPanel umpanel = new JPanel();
			umpanel.setBackground(Color.LIGHT_GRAY);
			umpanel.setBounds(6, 6, 608, 282);
			getContentPane().add(umpanel);
			umpanel.setLayout(null);

			JButton btnaddusr = new JButton("Add New User Account");
			btnaddusr.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// connection and query to insert new entry into user account...
					AddUser addUser = new AddUser();
					 addUser.setVisible(true);

				}
			});
			btnaddusr.setBounds(16, 28, 188, 37);
			umpanel.add(btnaddusr);

			JButton btndelusr = new JButton("Delete User Account");
			btndelusr.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {


					DeleteUser  delUser = new DeleteUser();
					delUser.setVisible(true);

				}
			});
			btndelusr.setBounds(16, 89, 188, 37);
			umpanel.add(btndelusr);

			JButton btnprevious = new JButton("< Return to Previous Page");
			btnprevious.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// add code to dispose current frame and show the AdminControl Frame
					dispose();

				}
			});

			btnprevious.setBounds(185, 228, 203, 37);
			umpanel.add(btnprevious);

		}


	}
