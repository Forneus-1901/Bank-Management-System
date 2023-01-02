import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.internal.text.revisions.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mysql.jdbc.Statement;

public class modify_account {

	protected Shell shell;
	private Text text_account;
	Connection con;
	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			modify_account window = new modify_account();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	modify_account(Connection con){
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label lblModifyAccount = new Label(shell, SWT.NONE);
		lblModifyAccount.setBounds(102, 35, 139, 20);
		lblModifyAccount.setText("MODIFY ACCOUNT");
		
		Label lblAccountNumber = new Label(shell, SWT.NONE);
		lblAccountNumber.setBounds(10, 97, 148, 20);
		lblAccountNumber.setText("ACCOUNT NUMBER");
		
		
		text_account = new Text(shell, SWT.BORDER);
		text_account.setBounds(164, 91, 258, 26);
		
		Button btnActivate = new Button(shell, SWT.NONE);
		btnActivate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(text_account.getText().length()==0 ) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter ACCOUNT NUMBER");
					return;
				}
				
				String query = "update account set active='1' where account_no="+text_account.getText()+";";
				try {
					Statement stmt = (Statement) con.createStatement();
					stmt.executeUpdate(query);
					MessageDialog.openError(shell, "Success", "Account Activated !!");
					shell.close();
					
				}catch(Exception ee) {
					MessageDialog.openError(shell, "Error", "Account doesn't exists !!");
				}
			}
		});
		btnActivate.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnActivate.setBounds(102, 149, 101, 30);
		btnActivate.setText("ACTIVATE");

		
		Button btnDeactivate = new Button(shell, SWT.NONE);
		btnDeactivate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(text_account.getText().length()==0 ) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter ACCOUNT NUMBER");
					return;
				}
				
				String query = "update account set active='0' where account_no="+text_account.getText()+";";
				try {
					Statement stmt = (Statement) con.createStatement();
					stmt.executeUpdate(query);
					MessageDialog.openError(shell, "Success", "Account Deactivated !!");
					shell.close();
					
				}catch(Exception ee) {
					MessageDialog.openError(shell, "Error", "Account doesn't exists !!");
				}	
				
			}
		});
		btnDeactivate.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDeactivate.setBounds(240, 149, 109, 30);
		btnDeactivate.setText("DEACTIVATE");
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
					shell.close();
				
			}
		});
		btnExit.setBounds(192, 213, 90, 30);
		btnExit.setText("EXIT");

	}
}
