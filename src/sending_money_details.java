import java.math.BigInteger;
import java.sql.Connection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.math.BigDecimal;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class sending_money_details {
	Connection con;

	protected Shell shell;
	private Text text_card_holder_name;
	private Text text__bank_name;
	private Text text_bank_ifsc;
	private Text text_amount_send;
	private Text text_remarks;
	private Text txt_acc_no;

	String acc_no;

	/**
	 * Launch the application.
	 * @param args
	 * @return 
	 */
//		public static void main(String[] args) {
//			try {
//				sending_money_details window = new sending_money_details();
//				window.open();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
	
	sending_money_details(Connection con,String acc_no){
		this.con = con;
		this.acc_no = acc_no;
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
		shell.setSize(480, 476);
		shell.setText("SWT Application");

		Label lblBankName = new Label(shell, SWT.NONE);
		lblBankName.setBounds(10, 58, 134, 20);
		lblBankName.setText("Card Holder Name:");

		text_card_holder_name = new Text(shell, SWT.BORDER);
		text_card_holder_name.setBounds(153, 52, 256, 26);

		Label lblBankName_1 = new Label(shell, SWT.NONE);
		lblBankName_1.setBounds(10, 144, 98, 20);
		lblBankName_1.setText("Bank Name:");

		text__bank_name = new Text(shell, SWT.BORDER);
		text__bank_name.setBounds(153, 141, 256, 26);

		Label lblBankIfsc = new Label(shell, SWT.NONE);
		lblBankIfsc.setBounds(10, 193, 70, 20);
		lblBankIfsc.setText("Bank IFSC:");

		text_bank_ifsc = new Text(shell, SWT.BORDER);
		text_bank_ifsc.setBounds(153, 190, 256, 26);

		Label lblAmount = new Label(shell, SWT.NONE);
		lblAmount.setBounds(10, 235, 70, 20);
		lblAmount.setText("Amount:");

		text_amount_send = new Text(shell, SWT.BORDER);
		text_amount_send.setBounds(153, 232, 256, 26);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 283, 90, 20);
		lblNewLabel.setText("REMARKS:");

		text_remarks = new Text(shell, SWT.BORDER);
		text_remarks.setBounds(153, 280, 256, 71);

		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnExit.setBounds(283, 370, 90, 30);
		btnExit.setText("EXIT");


		Label lblSendMoney = new Label(shell, SWT.NONE);
		lblSendMoney.setBounds(179, 10, 98, 20);
		lblSendMoney.setText("SEND MONEY");

		txt_acc_no = new Text(shell, SWT.BORDER);
		txt_acc_no.setBounds(153, 95, 256, 26);

		Label lblAccountNo = new Label(shell, SWT.NONE);
		lblAccountNo.setBounds(10, 101, 98, 20);
		lblAccountNo.setText("Account No:");

		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String card_name = text_card_holder_name.getText();
				String acc_number = txt_acc_no.getText();
				String bank_name = text__bank_name.getText();
				String ifcs_code = text_bank_ifsc.getText();
				String amount =  text_amount_send.getText();
				String remarks = text_remarks.getText();
				if(card_name.length()==0 ||acc_number.length()==0||bank_name.length()==0 ||ifcs_code.length()==0||amount.length()==0) {
					MessageDialog.openError(shell, "INFORMATION", "Please enter all details");
					return;
				}

				String query_1 = "select account_balance from account where account_no="+acc_no;
				try {
					Statement stmt = (Statement) con.createStatement();
					ResultSet rs = (ResultSet) stmt.executeQuery(query_1);
					if(rs.next()) {
						String amt = rs.getString("account_balance");
						
						float send_amt = Float.parseFloat(amount);
						float avail_bal = Float.parseFloat(amt);
						
						if(send_amt > avail_bal) {
							MessageDialog.openInformation(shell, "Insufficent Balance", "Available balance is "+avail_bal+". Please enter less amount !!");
						}else {
							float rem_bal = avail_bal - send_amt;
							String insert_query = "insert into transcation_log (txn_amount,avail_balance,status,card_name,bank_name,ifsc,remarks,acc_no,transfer_acc_no) "
									+ "values("+amount+", "+ rem_bal +", 'Debited', '"+card_name+"', '"+bank_name+"', '"+ifcs_code+"', '"+remarks+"', '"+acc_number+"', '"+acc_no+"');";
							
							stmt.executeUpdate(insert_query);
							
							String update_query = "update account set account_balance="+rem_bal+" where account_no="+acc_no+";";
							int result = stmt.executeUpdate(update_query);
							
							if(result == 1) {
								MessageDialog.openInformation(shell, "Success", "Amount transferred successfully !!");
								shell.close();
							}else {
								MessageDialog.openError(shell, "Failed", "Amount transferred failed !!");
							}
						}
						
						
					}
				}catch(Exception ee) {
					MessageDialog.openError(shell, "Error", ""+ee);
				}

				

				
			}
		});
		btnSubmit.setBounds(177, 370, 90, 30);
		btnSubmit.setText("Submit");

	}
}
