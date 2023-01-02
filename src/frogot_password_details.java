import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;
import java.sql.DriverManager;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class frogot_password_details {

	protected Shell shell;
	private Text text_debit_card_no;
	private Text text_cvv_no;
	private Text text_new_password;
	Connection con;

	/**
	 * Launch the application.
	 * @param args
	 */
	//	public static void main(String[] args) {
	//		try {
	//			Class.forName("com.mysql.jdbc.Driver");
	//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bms?characterEncoding=utf8","root","password");
	//			System.out.println("Connected");
	//			
	//			
	//			
	//			
	//			
	//			frogot_password_details window = new frogot_password_details();
	//			window.open();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	frogot_password_details(Connection con){
		this.con = con;
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
		shell.setSize(495, 344);
		shell.setText("SWT Application");

		Label lblFrogotPassword = new Label(shell, SWT.NONE);
		lblFrogotPassword.setBounds(142, 20, 137, 20);
		lblFrogotPassword.setText("FROGOT PASSWORD");

		Label lblDebitCardNo = new Label(shell, SWT.NONE);
		lblDebitCardNo.setBounds(10, 86, 125, 20);
		lblDebitCardNo.setText("DEBIT CARD NO:");

		text_debit_card_no = new Text(shell, SWT.BORDER);
		text_debit_card_no.setBounds(141, 80, 244, 26);

		Label lblCvv = new Label(shell, SWT.NONE);
		lblCvv.setBounds(22, 132, 70, 20);
		lblCvv.setText("CVV:");

		text_cvv_no = new Text(shell, SWT.BORDER);
		text_cvv_no.setBounds(142, 132, 243, 26);

		Label lblNewPassword = new Label(shell, SWT.NONE);
		lblNewPassword.setBounds(10, 188, 108, 20);
		lblNewPassword.setText("New Password:");

		text_new_password = new Text(shell, SWT.BORDER);
		text_new_password.setBounds(142, 182, 244, 26);

		Button btnSetPassword = new Button(shell, SWT.NONE);
		btnSetPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String debit_cardno=text_debit_card_no.getText();
				String cvv_no=text_cvv_no.getText();
				String new_password=text_new_password.getText();
				if(debit_cardno.length()==0 ||cvv_no.length()==0 || new_password.length()==0) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter all Details");
					return;
				}

				System.out.println("select  account_no  from account  where   card_no="+debit_cardno+" and  cvv="+cvv_no+";");

				System.out.println(" update   account   set password ='" +new_password+"' where account_no=1;" );	

//				21312312
				try {
					Statement stmt = (Statement) con.createStatement();
					ResultSet ms=(ResultSet) stmt.executeQuery("select  account_no  from account  where   card_no="+debit_cardno+" and  cvv="+cvv_no+";");

					//					ms.next()

					
					if (ms.next()) {

						int acc_no=ms.getInt("account_no");
						System.out.println(acc_no);
						
//						MessageDialog.openInformation(shell, cvv_no, new_password);

						int pass= stmt.executeUpdate("update account set password ='" +new_password+"' where account_no="+ acc_no +";");	
						if(pass == 1) {
							MessageDialog.openInformation(shell, "Password Changed", "Password changed successfully!!");
						}


					}
					else {
						MessageDialog.openError(shell, "Error", "no data found");

					}
				}


				catch(Exception fs) {
					MessageDialog.openError(shell, "Server Error", ""+fs);


				}		
			}






		});
		btnSetPassword.setBounds(171, 237, 159, 30);
		btnSetPassword.setText("SET PASSWORD");

		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();

			}
		});
		btnExit.setBounds(377, 257, 90, 30);
		btnExit.setText("EXIT");

	}

}
