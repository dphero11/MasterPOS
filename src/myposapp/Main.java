package myposapp;

import java.awt.EventQueue;


 class Main {

	//static Login login  = new Login();


public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
			try {

				Login login  = new Login();
				login.setVisible(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});



	}



}
