package myposapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


	 class AdminControl extends JFrame{

		/**
		 *
		 */

		private static final long serialVersionUID = -5148290466842511860L;

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl dpi = new JDatePanelImpl(model);
		JDatePickerImpl jdp = new JDatePickerImpl(dpi, new MyCal());
		String sdate;

		Connection con;
		int reportID;

		    AdminControl() {

			new JFrame("Administrator Settings");
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setTitle("AMEN GROCERY STORE");
			setVisible(false);
			setBounds(100,100,620,520);
			setResizable(false);
			setLocationRelativeTo(null);
			getContentPane().setLayout(null);

			JPanel panel = new JPanel();
			panel.setBackground(Color.LIGHT_GRAY);
			panel.setBounds(6, 6, 608, 480);
			getContentPane().add(panel);
			panel.setLayout(null);
			jdp.getJFormattedTextField().setText("Select Date");
			jdp.getJFormattedTextField().setEnabled(false);
			jdp.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
			jdp.setSize(166, 29);
			jdp.setLocation(153, 126);
			panel.add(jdp);

			JButton btnInventory = new JButton(" Inventory Report");
			btnInventory.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					reportinv();

				}
			});
			btnInventory.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnInventory.setBackground(Color.DARK_GRAY);
			btnInventory.setBounds(6, 173, 136, 44);
			panel.add(btnInventory);

			JButton btnSales = new JButton("Daily Sales");
			btnSales.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					String selectedDate = jdp.getJFormattedTextField().getText();

					sdate =	selectedDate.toString();


					if(sdate.length() == 10) {

						reportsales(sdate);

					}else {

						System.out.println("Error Occured");
						JOptionPane.showMessageDialog(null,"Please Choose a Date","ERROR",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnSales.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnSales.setBounds(6, 117, 136, 44);
			panel.add(btnSales);

			JButton btnlogout = new JButton("Logout");
			btnlogout.setBackground(Color.CYAN);
			btnlogout.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnlogout.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					Login log1 = new Login();
					log1.setVisible(true);
					dispose();

				}
			});
			btnlogout.setBounds(6, 340, 136, 44);
			panel.add(btnlogout);

			JButton btnUsrMgt = new JButton("Manage Users");
			btnUsrMgt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					ManageUsers manageUsers = new ManageUsers();
					manageUsers.setVisible(true);

				}
			});
			btnUsrMgt.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnUsrMgt.setBounds(6, 61, 136, 44);
			panel.add(btnUsrMgt);

			JButton btnUpdateStock = new JButton("Update Stock");
			btnUpdateStock.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					UpdateInventory upinv = new UpdateInventory();
					upinv.setVisible(true);

				}
			});
			btnUpdateStock.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnUpdateStock.setBounds(6, 229, 136, 44);
			panel.add(btnUpdateStock);

			JButton btnAddNewProducts = new JButton("Add New Product");
			btnAddNewProducts.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					AddStock newadd = new AddStock();
					newadd.setVisible(true);
				}
			});
			btnAddNewProducts.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			btnAddNewProducts.setBounds(6, 284, 136, 44);
			panel.add(btnAddNewProducts);

		}

/**
 * This method displays the Inventory Report.
 */

	void reportinv() {

			HashMap<String, Object> hm = new HashMap<>();


			try {

				con = SaleSystem.connect();

				hm.put("Invoiceno",reportID);

				JasperDesign jdesign = JRXmlLoader.load("/Users/user/eclipse-workspace/MainPos/src/myposapp/InventoryReport.jrxml");
				JasperReport ireport = JasperCompileManager.compileReport(jdesign);
				JasperPrint jprint = JasperFillManager.fillReport(ireport,hm,con);


				JasperViewer.viewReport(jprint,false);


			} catch (JRException e) {

				e.printStackTrace();
			}
		}

/**
 * This method takes the date of sale as a parameter
 * to print out Daily sales of each product.
 * @param saledate
 */

	 void reportsales(String saledate) {


				HashMap<String, Object> hm = new HashMap<>();

				try {

					con = SaleSystem.connect();

					hm.put("Report_Date",saledate);

					JasperDesign jdesign = JRXmlLoader.load("/Users/user/eclipse-workspace/MainPos/src/myposapp/Sales Report.jrxml");
					JasperReport ireport = JasperCompileManager.compileReport(jdesign);
					JasperPrint jprint = JasperFillManager.fillReport(ireport,hm,con);

					JasperViewer.viewReport(jprint,false);

				} catch (JRException e) {

					e.printStackTrace();
				}

			}
}
