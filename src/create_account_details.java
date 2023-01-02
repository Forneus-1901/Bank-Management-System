import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import java.util.Random;   

public class create_account_details {

	protected Shell shell;
	private Text text_user_newname;
	private Text text_new_phoneno;
	private Text text_user_new_email;
	private Text text_user_addharcard_no;
	private Text text_user_newaddress;
	Connection con;
	/**
	 * Launch the application.
	 * @param args
	 */
	//	public static void main(String[] args) {
	//		try {
	//			create_account_details window = new create_account_details();
	//			window.open();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	create_account_details(Connection con){
		this.con =con;

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

	public long generateRandom(int length) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(605, 420);
		shell.setText("SWT Application");

		Label lblCreateAccount = new Label(shell, SWT.NONE);
		lblCreateAccount.setBounds(170, 10, 181, 20);
		lblCreateAccount.setText("CREATE NEW ACCOUNT");

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(22, 45, 70, 20);
		lblName.setText("NAME:");

		Label lblPhoneNo = new Label(shell, SWT.NONE);
		lblPhoneNo.setBounds(22, 87, 70, 20);
		lblPhoneNo.setText("Phone No:");

		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setBounds(22, 131, 70, 20);
		lblEmail.setText("Email:");

		Label lblAddharCardNo = new Label(shell, SWT.NONE);
		lblAddharCardNo.setBounds(10, 168, 111, 20);
		lblAddharCardNo.setText("Addhar card no:");

		text_user_newname = new Text(shell, SWT.BORDER);
		text_user_newname.setBounds(121, 45, 250, 26);

		text_new_phoneno = new Text(shell, SWT.BORDER);
		text_new_phoneno.setBounds(121, 87, 250, 26);

		text_user_new_email = new Text(shell, SWT.BORDER);
		text_user_new_email.setBounds(121, 125, 250, 26);

		text_user_addharcard_no = new Text(shell, SWT.BORDER);
		text_user_addharcard_no.setBounds(121, 162, 250, 26);

		Label lblAddress = new Label(shell, SWT.NONE);
		lblAddress.setBounds(22, 203, 70, 20);
		lblAddress.setText("Address:");

		text_user_newaddress = new Text(shell, SWT.BORDER);
		text_user_newaddress.setBounds(121, 197, 250, 26);

		Combo txt_account_type = new Combo(shell, SWT.NONE);
		txt_account_type.setToolTipText("Select Account Type");
		txt_account_type.setBounds(121, 241, 250, 28);
		txt_account_type.setItems("Saving Account","Current Account");
		txt_account_type.setText("Select Account Type");


		Label lblAccountType = new Label(shell, SWT.NONE);
		lblAccountType.setBounds(20, 244, 90, 25);
		lblAccountType.setText("Account Type");



		Button btnRegister = new Button(shell, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String user_name =text_user_newname.getText();
				String phone_no=text_new_phoneno.getText();
				String email= text_user_new_email.getText();
				String addhar_number=text_user_addharcard_no.getText();
				String address=text_user_newaddress.getText();
				String account_type=txt_account_type.getText();

				//				account_type != 'c a' && account_type != 's a'
				if(user_name.length()==0 ||phone_no.length()==0||email.length()==0 ||addhar_number.length()==0||address.length()==0) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter all details");
					return;
				}

				
				if(!account_type.equalsIgnoreCase("current account") && !account_type.equalsIgnoreCase("saving account") ) {
					MessageDialog.openError(shell, "Error", "SELECT  ACCOUNT TYPE");
					return;
				}


				int group_id=2;
				String query = "insert into customer(cus_name,cus_phone_no,cus_address,email_address,addhar_no,user_group_id ) "
						+ " values('"+user_name+"','"+phone_no+"','"+address+"','"+email+"','"+addhar_number+"', "+group_id+");";

				System.out.println(query);
				try {
					Statement stmt = (Statement) con.createStatement();
					stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

					ResultSet rs = (ResultSet) stmt.getGeneratedKeys(); 
//					System.out.println(rs.next());
					if(rs.next()) {
//						System.out.println("I am in");
						int cus_id = rs.getInt(1);
						System.out.println(cus_id);
						Random random = new Random();   
						long card_number = generateRandom(12);
						int cvv = random.nextInt(900) + 100;
						System.out.println(card_number);
						System.out.println(cvv);
						String  query2="insert into account (password,account_type,account_balance,card_no,cvv,cus_id)"
								+ " values ('"+null+"','"+account_type.toLowerCase()+"','"+0+"','"+card_number+"','"+cvv+"','"+cus_id+"');";


						stmt.executeUpdate(query2,Statement.RETURN_GENERATED_KEYS);
						ResultSet ab=(ResultSet) stmt.getGeneratedKeys();
					//	System.out.println(ab.next());

						if(ab.next()){
							int account_number=ab.getInt(1);
							MessageDialog.openInformation(shell,"Congrants","Account number: "+account_number+"\nCard Number: "+card_number+"\nCVV: "+cvv);
						}
					}					
				}	
				catch (Exception ab) {
					MessageDialog.openError(shell, "Error", ""+ab);
				}
			}





		});
		btnRegister.setBounds(196, 295, 90, 30);
		btnRegister.setText("REGISTER");

		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();

			}

		});
		btnExit.setBounds(487, 333, 90, 30);
		btnExit.setText("EXIT");

	}      
}
