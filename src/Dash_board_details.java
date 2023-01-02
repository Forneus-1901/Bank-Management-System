import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.Statement;

import org.eclipse.swt.widgets.Button;

import java.sql.Connection;
import java.sql.ResultSet;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Dash_board_details {

	protected Shell shell;
	Connection con;
	String acc_no;
	int customer_id;
	boolean isAdmin;
	/**
	 * Launch the application.
	 * @param args
	 */
//			public static void main(String[] args) {
//				try {
//					Dash_board_details window = new Dash_board_details();
//					window.open();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
	Dash_board_details(Connection con ,String acc_no,int customer_id,boolean isAdmin){
		this.con = con;
		this.acc_no=acc_no;
		this.customer_id=customer_id;	
		this.isAdmin = isAdmin;
	}
//	

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
		shell.setSize(720, 471);
		shell.setText("SWT Application");

		Button btnUserDetails = new Button(shell, SWT.NONE);
		btnUserDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				user_detail_application window = new user_detail_application(con,acc_no,customer_id);
				window.open();


			}
		});
		btnUserDetails.setBounds(23, 99, 151, 30);
		btnUserDetails.setText("USER DETAILS");
		Button btnCheckBalance = new Button(shell, SWT.NONE);
		btnCheckBalance.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				check_balanc_details window = new check_balanc_details( con,acc_no);
				window.open();


			}
		});
		btnCheckBalance.setBounds(23, 185, 151, 30);
		btnCheckBalance.setText("CHECK BALANCE");

		Button btnViewStatement = new Button(shell, SWT.NONE);
		btnViewStatement.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				account_statement_details window = new account_statement_details(con,acc_no,isAdmin);
				window.open();

			}
		});
		btnViewStatement.setBounds(23, 269, 150, 30);
		btnViewStatement.setText("VIEW STATEMENT");

		Button btnSendMoney = new Button(shell, SWT.NONE);
		btnSendMoney.addSelectionListener(new SelectionAdapter() {
			@Override

			public void widgetSelected(SelectionEvent e) {

				sending_money_details window = new sending_money_details(con,acc_no);
				window. open();


			}
		});
		btnSendMoney.setBounds(481, 99, 179, 30);
		btnSendMoney.setText("SEND MONEY");

		Button btnApplyForCredit = new Button(shell, SWT.NONE);
		btnApplyForCredit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				apply_for_credit_card_process window = new apply_for_credit_card_process();
				window.open();

			}
		});
		btnApplyForCredit.setBounds(481, 185, 179, 30);
		btnApplyForCredit.setText("APPLY FOR CREDIT CARD");

		Button logout = new Button(shell, SWT.NONE);
		logout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				login_details_page window = new login_details_page();
		window.jdbc_connection();
				window.open();
		
			}
		});
		logout.setBounds(481, 269, 179, 30);
		logout.setText("LOGOUT");

		Label lblWelcomeToOur = new Label(shell, SWT.NONE);
		lblWelcomeToOur.setBounds(248, 32, 179, 20);
		lblWelcomeToOur.setText("WELCOME TO OUR BANK");
		
		Button btnAccountModify = new Button(shell, SWT.NONE);
		btnAccountModify.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modify_account obj = new modify_account(con);
				obj.open();
			}
		});
		
		btnAccountModify.setBounds(263, 185, 138, 30);
		btnAccountModify.setText("MODIFY ACCOUNT");
		btnAccountModify.setVisible(isAdmin);
		
		Label lblGuidedByProf = new Label(shell, SWT.NONE);
		lblGuidedByProf.setBounds(161, 382, 414, 20);
		lblGuidedByProf.setText("GUIDED BY: PROF. JOYCE LEMOS | PROF. ASITHA NADAR");


}
}
	
	
	
	
	
	
	
	
	