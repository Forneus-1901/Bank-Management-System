import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class user_detail_application {

	protected Shell shell;
	protected Text text_user_account_number;
	private Text text_user_name;
	private Text text_user_phone_no;
	private Text text_user_email;
	private Text text_user_address;
	Connection con;
	String acc_no;
	int customer_id;
	/**
	 * Launch the application.
	 * @param args
	 */
//		public static void main(String[] args) {
//			try {
//				user_detail_application window = new user_detail_application();
//				window.open();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	user_detail_application(Connection con,String acc_no, int customer_id){
		this.con = con;
		this.acc_no = acc_no;
		this.customer_id=customer_id;		
	}



	void setData() {
		try {
			Statement stmt = (Statement) con.createStatement();

			String query=	"Select * from  account as a  inner join  customer as c  on a.cus_id=c.cus_id where account_no="+acc_no+";";
			System.out.println(query);
			ResultSet am = stmt.executeQuery(query);
			while(am.next()){
				System.out.println(am.getInt("account_no"));	

				int account_no = am.getInt("account_no");
				String user_name=am.getString("cus_name");
				String phone_no=am.getString("cus_phone_no");
				String email=am.getString("email_address");
				String address=am.getString("cus_address");

				text_user_account_number.setText(""+account_no);
				text_user_address.setText(address);
				text_user_email.setText(email);
				text_user_phone_no.setText(phone_no);
				text_user_name.setText(user_name);
			}
		}
		catch (Exception ab) {
			MessageDialog.openError(shell, "Error", ""+ab);
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
		setData();
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
		shell.setSize(1333, 772);
		shell.setText("SWT Application");

		text_user_account_number = new Text(shell, SWT.BORDER);
		text_user_account_number.setEditable(false);
		text_user_account_number.setBounds(207, 89, 700, 26);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(32, 92, 134, 20);
		lblName.setText("Account Number:");

		Label lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setBounds(96, 169, 70, 20);
		lblPhone.setText("Name:");

		text_user_name = new Text(shell, SWT.BORDER);
		text_user_name.setBounds(207, 169, 700, 26);

		Label lblUserDetails = new Label(shell, SWT.NONE);
		lblUserDetails.setBounds(464, 30, 123, 20);
		lblUserDetails.setText("USER DETAILS");

		Label lblAddress = new Label(shell, SWT.NONE);
		lblAddress.setBounds(96, 264, 70, 20);
		lblAddress.setText("Phone No:");

		text_user_phone_no = new Text(shell, SWT.BORDER);
		text_user_phone_no.setBounds(207, 261, 700, 26);

		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setText("Email:");
		lblEmail.setBounds(96, 371, 70, 20);

		text_user_email = new Text(shell, SWT.BORDER);
		text_user_email.setBounds(207, 371, 700, 26);

		Label lblAccountNumber = new Label(shell, SWT.NONE);
		lblAccountNumber.setBounds(81, 476, 63, 20);
		lblAccountNumber.setText("Address:");

		text_user_address = new Text(shell, SWT.BORDER);
		text_user_address.setBounds(210, 470, 697, 26);

		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();					
			}
		});
		btnExit.setBounds(1162, 668, 111, 30);
		btnExit.setText("     EXIT");

		Button btnUpdate = new Button(shell, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//String account_no = text_user_account_number.getText();
				String user_name =	text_user_name.getText();
				String phone_no = text_user_phone_no.getText();
				String email = text_user_email.getText();
				String address = text_user_address.getText();
				if(user_name.length()==0 || phone_no.length()==0 || email.length()==0 || address.length()==0 ) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter all details");
					return;
				}
				
				String query = "update customer set cus_name = '"+user_name+"',cus_phone_no = '"
						+phone_no+"',email_address = '"+email+"',cus_address = '"+address+"' where cus_id = "+customer_id +" ;";
				System.out.println(query);
try {
				Statement stmt = con.createStatement();
				int rs=stmt.executeUpdate(query);
				if(rs!=0) {
					MessageDialog.openInformation(shell, "HURRAY", "SUCESSFULLY UPDATE");
				}
				else {
					MessageDialog.openError(shell, "ERROR", "SORRY IT CANNOT UPDATE");
				}
				
				
}
catch(Exception ee) {
	
}			
}
		
		
		
		
		});
		btnUpdate.setBounds(488, 564, 90, 30);
		btnUpdate.setText("UPDATE");
		String[] items = new String[] { "Male", "Female" };


	}
}
