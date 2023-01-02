import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class login_details_page {

	protected Shell shell;
	private Text txt_account_no;
	private Text txt_password;
	Connection con;

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			login_details_page window = new login_details_page();
			window.jdbc_connection();
			window.open();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
	public void jdbc_connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bms?characterEncoding=utf8","root","password");
			System.out.println("Connected");


			//			String query_coustmer="insert into customer (cus_name,cus_phone_no,cus_address) values('aditya',9970507646,'new raut wadi')";
			//			stmt.execute(query_coustmer);
			//			con.close();




		}catch(Exception e) {
			System.out.println("Error during connection "+e);
		}
	}


	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(519, 362);
		shell.setText("SWT Application");

		Label lblWelcome = new Label(shell, SWT.NONE);
		lblWelcome.setBounds(204, 22, 70, 20);
		lblWelcome.setText("WELCOME");

		Label lblAccountNumber = new Label(shell, SWT.NONE);
		lblAccountNumber.setBounds(33, 84, 129, 20);
		lblAccountNumber.setText("Account Number:");



		Label label = new Label(shell, SWT.NONE);
		label.setBounds(33, 153, 70, 20);

		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(33, 129, 70, 20);
		lblPassword.setText("Password:");


		txt_account_no = new Text(shell, SWT.BORDER);
		txt_account_no.setBounds(168, 78, 231, 26);



		txt_password = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		txt_password.setBounds(168, 126, 231, 26);

		Button btnFrogotPassword = new Button(shell, SWT.NONE);
		btnFrogotPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frogot_password_details window = new frogot_password_details(con);
				window.open();

			}
		});
		btnFrogotPassword.setBounds(10, 275, 157, 30);
		btnFrogotPassword.setText("FROGOT PASSWORD");

		Button btnCreateAccount = new Button(shell, SWT.NONE);
		btnCreateAccount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				create_account_details window = new create_account_details(con);
				window.open();

			}
		});
		btnCreateAccount.setBounds(316, 275, 175, 30);
		btnCreateAccount.setText("CREATE ACCOUNT");

		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String acc_no = txt_account_no.getText();
				String password = txt_password.getText();

				boolean isPresent = false;
			
				if(acc_no.isEmpty() || password.isEmpty()) {
					MessageDialog.openError(shell, "Error", "Please enter account no or password");
					return;
				}

				
				//System.out.println("select * from account where account_no="+acc_no+" and password='"+password+"';");
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select * from account a inner join customer c on c.cus_id=a.cus_id where account_no="+acc_no+" and password='"+password+"';");

					if(rs.next()) {
						
						int customer_id = rs.getInt("cus_id");
						int user_group_id = rs.getInt("user_group_id");
						int acc_status = rs.getInt("active");
						
						boolean isAdmin = false;
						if(user_group_id == 1)
							isAdmin = true;
						if(acc_status == 1) {
							shell.close();
							Dash_board_details window = new Dash_board_details(con,acc_no,customer_id,isAdmin);
							window.open();
						}else {
							MessageDialog.openInformation(shell, "Account Deleted", "You are no longer customer with us !!");
						}
						  

					}
					else {
						MessageDialog.openError(shell, "Login", "Invalid credentials");
					}

					//					con.close();

				}catch(Exception ee) {
					MessageDialog.openError(shell, "Server Error", ""+ee);

				}




			}
		});
		btnLogin.setBounds(184, 199, 90, 30);
		btnLogin.setText("LOGIN");

	}
}
