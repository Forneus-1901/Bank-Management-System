import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.math.BigDecimal;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class check_balanc_details {

	protected Shell shell;
	private Text user_name;
	private Text user_balance;
	Connection con;
	String acc_no;
	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			check_balanc_details window = new check_balanc_details();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	check_balanc_details(Connection con,String acc_no){
		this.con = con;
	    this.acc_no =acc_no;
	}
	
	
		
	
	
	 public void  init() {
		 
		 String query="Select * from account as a inner join customer as c  on a.cus_id= c.cus_id where account_no="+acc_no;
		 System.out.println(query);
		 try {
			Statement stmt = (Statement)con.createStatement();
			ResultSet rs= (ResultSet) stmt.executeQuery(query);
    if(rs.next()) {
    	String name=rs.getString("cus_name");
    	String acc_balance=rs.getString("account_balance");
    	  user_name.setText(name);
    	 user_balance.setText(""+new BigDecimal(acc_balance).toBigInteger());
    }

    
		 }
		 catch(Exception e) {
			 
			 MessageDialog.openError(shell, "error", ""+e);
			 
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
		  init();
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
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(31, 54, 70, 20);
		lblName.setText("Name:");
		
		user_name = new Text(shell, SWT.BORDER);
		user_name.setEditable(false);
		user_name.setBounds(131, 54, 209, 26);
		
		Label lblBalance = new Label(shell, SWT.NONE);
		lblBalance.setBounds(31, 121, 70, 20);
		lblBalance.setText("Balance:");
		
		user_balance = new Text(shell, SWT.BORDER);
		user_balance.setEditable(false);
		user_balance.setBounds(131, 118, 209, 26);
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnExit.setBounds(352, 213, 70, 30);
		btnExit.setText("   EXIT");
		
		Label lblBalanceDetails = new Label(shell, SWT.NONE);
		lblBalanceDetails.setBounds(131, 10, 132, 20);
		lblBalanceDetails.setText("BALANCE DETAILS");

	}
}
