package myposapp;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


 	class SaleSystem extends JFrame {

	/**
	 *  Declaration of variables.
	 */
	private static final long serialVersionUID = -206872938100876947L;
	private JPanel contentPane;
	private JPanel mainPanel;
	private JPanel panel_1;
	private JPanel panel_2;

	private JTextField txtTotal;
	private JTextField txtPay;
	private JTextField txtBal;
	private JTextField txtAmount;
	private JTextField txtPname;
	private JTextField txtPrice;
	private JTextField txtPcode;
	private JButton btnAdd;
	private JButton btnCLR;
	private JButton btnPrintBill;
	private JButton btnLogout;

	private JLabel lblPay;
	private JLabel lblTotal;
	private JLabel lblBalance;
	private JLabel lblPcode;
	private JLabel lblPname;
	private JLabel lblPrice;
	private JLabel lblQty;
	private JLabel lblAmount;

	private JScrollPane scrollPane;
	private JTable table;

	static int NumRows = 0;
	static int NumCols = 4;
	private String[] col_name = {"Description","Quantity","Price","Amount"};

	Connection con;

	PreparedStatement pst;
	PreparedStatement pst1;
	ResultSet rs;
	int reportID;




	Instant instant = Instant.now();
	OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);


	DefaultTableModel dftm = new DefaultTableModel();



	/**
	 * Create the frame.
	 */
	  SaleSystem() {

		initialize();
		connect();
	}


	  	void report() {

		HashMap<String, Object> hm = new HashMap<>();

		connect();


		try {

			hm.put("Invoiceno",reportID);
			JasperDesign jdesign = JRXmlLoader.load("/Users/user/eclipse-workspace/MainPos/src/myposapp/Receipt.jrxml");
			JasperReport ireport = JasperCompileManager.compileReport(jdesign);
			JasperPrint jprint = JasperFillManager.fillReport(ireport,hm,con);
			JasperViewer.viewReport(jprint,false);


		} catch (JRException e) {

			e.printStackTrace();
		}
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

	  	 void balance () {

		 int total = Integer.parseInt(txtTotal.getText());
		 int pay = Integer.parseInt(txtPay.getText());
		 int bal = pay - total;
		 txtBal.setText(String.valueOf(bal));

	}

	/**
	 *  Handles transaction.
	 */

	    void sales(){

		String subtotal = txtTotal.getText();
		String pay = txtPay.getText();
		String balance = txtBal.getText();

		DateTimeFormatter f = DateTimeFormatter.ofPattern( "YYYY-MM-dd HH:mm:ss" );
		OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
		String newDate  = odt.format(f);
		System.out.println(newDate);


		int lastid = 0;

		if(pay.isEmpty()) {

			JOptionPane.showMessageDialog(this,"Please Enter Paid Amount !");

		}else {


			try {

				String query = "INSERT INTO `Sales`(Subtotal,Pay,Balance,Date)VALUES(?,?,?,?)";
				pst = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				pst.setString(1,subtotal);
				pst.setString(2,pay);
				pst.setString(3,balance);
				//pst.setTimestamp(4,mytime);
				pst.setString(4,newDate);
				pst.executeUpdate();

				 rs = pst.getGeneratedKeys();



				if(rs.next()){

					lastid = rs.getInt(1);
					reportID = lastid;

				}


					String query2 = "INSERT INTO `Product_Sale`(Sales_ID,Prod_Name,Price,Quantity,Total)VALUES(?,?,?,?,?)";

						pst1 = con.prepareStatement(query2);


						String prodname= "";
						String price = "";
						String quantity = "";
						String total = "";

						for(int i=0;i<table.getRowCount();i++ ) {

							prodname = (String)table.getValueAt(i,0);
							quantity = (String)table.getValueAt(i,1);
							price = (String)table.getValueAt(i,2);
							total =	(String)table.getValueAt(i,3);

							pst1.setInt(1,lastid);
							pst1.setString(2,prodname);
							pst1.setString(3,price);
							pst1.setString(4,quantity);
							pst1.setString(5,total);
							pst1.executeUpdate();
				  }


							con.close();

							JOptionPane.showMessageDialog(this,"Sale Completed");

							dftm = (DefaultTableModel) table.getModel();
							dftm.setRowCount(0);

							txtTotal.setText("");
							txtPay.setText("");
							txtBal.setText("");


							report();

			}catch (SQLException e1) {

				e1.printStackTrace();


				} }


			}

		/**
		 * Initialize Swing components.
		 */
	    void initialize() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 520);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("AMEN GROCERY STORE - POS");
		setVisible(false);

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(6, 6, 505, 163);
		mainPanel.add(panel_1);
		panel_1.setLayout(null);

		lblPcode = new JLabel("Prod. Code");
		lblPcode.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPcode.setBounds(6, 6, 88, 16);
		panel_1.add(lblPcode);

		lblPname = new JLabel("Prod. Name");
		lblPname.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPname.setBounds(117, 6, 124, 16);
		panel_1.add(lblPname);

		lblPrice = new JLabel("Price\n");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(305, 6, 61, 16);
		panel_1.add(lblPrice);

		lblQty = new JLabel("Qty");
		lblQty.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQty.setBounds(242, 6, 51, 16);
		panel_1.add(lblQty);

		lblAmount = new JLabel("Amount\n");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAmount.setBounds(378, 6, 80, 16);
		panel_1.add(lblAmount);

		txtPname = new JTextField();
		txtPname.setBounds(117, 34, 113, 29);
		txtPname.setEditable(false);
		panel_1.add(txtPname);

		txtPrice = new JTextField();
		txtPrice.setBounds(305, 34, 61, 29);
		txtPrice.setEditable(false);
		panel_1.add(txtPrice);

		txtPcode = new JTextField();
		txtPcode.addKeyListener(new KeyAdapter() {


			@Override
			  public void keyPressed(KeyEvent e) {

				 if(e.getKeyCode() == KeyEvent.VK_ENTER) {

					 String pcode = txtPcode.getText();

					 try {

						String query = "Select * from Inventory WHERE `Prod_Code` = ?";
						pst = con.prepareStatement(query);
						pst.setString(1, pcode);
						rs = pst.executeQuery();


						if(!rs.next()) {

							Component dialog  = new JOptionPane();
							JOptionPane.showMessageDialog( dialog,"Product Code Not Found !!! ","ERROR",JOptionPane.ERROR_MESSAGE);

						}else {

							 String pname = rs.getString("Prod_Name");
							 String price = rs.getString("Price");

							 txtPname.setText(pname.trim());
							 txtPrice.setText(price.trim());

						}


					} catch (SQLException e1) {

						e1.printStackTrace();

					}

				 }


			}
		});

		txtPcode.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtPcode.setForeground(Color.YELLOW);
		txtPcode.setBackground(Color.BLACK);
		txtPcode.setCaretColor(Color.YELLOW);
		txtPcode.setBounds(6, 34, 99, 29);
		panel_1.add(txtPcode);


		txtAmount = new JTextField();
		txtAmount.setBounds(378, 34, 88, 29);
		txtAmount.setEditable(false);
		panel_1.add(txtAmount);


		SpinnerModel spmodel = new SpinnerNumberModel(0,0,100,1);
		final JSpinner txtQty = new JSpinner(spmodel);
		txtQty.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int qty = Integer.parseInt(txtQty.getValue().toString());
				int price = Integer.parseInt(txtPrice.getText());
				int tot = qty*price;

				txtAmount.setText(String.valueOf(tot));

			}
		});
		txtQty.setBounds(242, 34, 51, 29);
		panel_1.add(txtQty);


		btnAdd = new JButton("ADD");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {


				dftm = (DefaultTableModel) table.getModel();

				int checkqty =  (Integer) txtQty.getValue();

				if(checkqty<=0) {

					Component dialog1  = new JOptionPane();
					JOptionPane.showMessageDialog( dialog1,"Please Enter a valid Quantity !!! ","Invalid Quantity",JOptionPane.INFORMATION_MESSAGE);


				}else { dftm.addRow(new Object[]

						 {

							txtPname.getText().toUpperCase(),
							txtQty.getValue().toString().toUpperCase(),
							txtPrice.getText().toUpperCase(),
							txtAmount.getText().toUpperCase()

							});

						}


				 int sum = 0;

				 for(int i=0; i<table.getRowCount(); i++) {

						 sum += Integer.parseInt(table.getValueAt(i, 3).toString());

				 }

				 	txtTotal.setText(String.valueOf(sum));
					txtPcode.setText("");
					txtPname.setText("");
					txtQty.setValue(0);
					txtPrice.setText("");
					txtAmount.setText("");
					txtPcode.requestFocusInWindow();

			}
		});


		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(305, 75, 67, 44);
		panel_1.add(btnAdd);


		btnCLR = new JButton("REMOVE");
		btnCLR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {


				dftm = (DefaultTableModel) table.getModel();


				if(dftm.getRowCount()<= 0) {

					Component dialog2  = new JOptionPane();
					JOptionPane.showMessageDialog( dialog2,"Add Record !!! ","Add Item To Cart",JOptionPane.INFORMATION_MESSAGE);

			}else {

				boolean notSelected = table.getSelectionModel().isSelectionEmpty();

					if(notSelected) {
						Component dialog3  = new JOptionPane();
					    JOptionPane.showMessageDialog( dialog3,"Select a row to remove !!! ","Choose Row",JOptionPane.INFORMATION_MESSAGE);
				       }else {

					dftm.removeRow(table.getSelectedRow());
				   }
			int sum = 0;

				for(int i=0; i<table.getRowCount(); i++) {

				    sum += Integer.parseInt(table.getValueAt(i, 3).toString());

				 }

				 	txtTotal.setText(String.valueOf(sum));
				 	txtPcode.requestFocusInWindow();
			}


		}});


		btnCLR.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCLR.setBounds(378, 75, 88, 44);
		panel_1.add(btnCLR);

		panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBackground(Color.GRAY);
		panel_2.setBounds(511, 6, 247, 470);
		mainPanel.add(panel_2);
		panel_2.setLayout(null);

		lblTotal = new JLabel("Total");
		lblTotal.setBounds(99, 6, 83, 19);
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_2.add(lblTotal);

		txtTotal = new JTextField();
		txtTotal.setBounds(72, 24, 110, 39);
		txtTotal.setEditable(false);
		panel_2.add(txtTotal);
		txtTotal.setColumns(10);

		lblPay = new JLabel("Cash Tendered");
		lblPay.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPay.setBounds(72, 75, 110, 16);
		panel_2.add(lblPay);

		txtPay = new JTextField();
		txtPay.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==KeyEvent.VK_ENTER) {

					balance();
				}
			}
		});
		txtPay.setBounds(72, 103, 110, 40);
		txtPay.setEditable(true);
		panel_2.add(txtPay);
		txtPay.setColumns(10);

		lblBalance = new JLabel("Balance");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBalance.setBounds(99, 152, 61, 16);
		panel_2.add(lblBalance);

		txtBal = new JTextField();
		txtBal.setBounds(72, 180, 110, 40);
		txtBal.setEditable(false);
		panel_2.add(txtBal);
		txtBal.setColumns(10);


		btnPrintBill = new JButton("PRINT BILL");
		btnPrintBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


					sales();

			}
		});

		btnPrintBill.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPrintBill.setBounds(72, 232, 117, 43);
		panel_2.add(btnPrintBill);

		btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogout.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnLogout.setBounds(72, 370, 117, 39);
		panel_2.add(btnLogout);



		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 169, 505, 307);
		mainPanel.add(scrollPane);

		table = new JTable(NumRows,NumCols) {

			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int NumRows, int NumCols) {
		       //all cells false
		       return false;
		    }

		};

		scrollPane.setViewportView(table);
		TableColumn tc;
		for(int i=0;i<col_name.length;i++){

			tc = table.getColumnModel().getColumn(i);
			tc.setHeaderValue(col_name[i]);
		}


		}



}
