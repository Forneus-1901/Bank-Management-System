import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Text;
import com.mysql.jdbc.Statement;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TableViewerColumn;
import models.History;

public class account_statement_details  {


	protected Shell shell;
	Connection con;
	String acc_no;
	boolean isAdmin;

	private Table table_1;


	/**
	 * Launch the application.
	 * @param args
	 */
//		public static void main(String[] args) {
//			try {
//				account_statement_details window = new account_statement_details();
//				window.open();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	account_statement_details(Connection con, String acc_no, boolean isAdmin){
		this.con = con;
		this.acc_no = acc_no;
		this.isAdmin = isAdmin;
	}
	//	

	void init() {
		
		
		String query = "select id,status,card_name,bank_name,ifsc,remarks,transfer_acc_no,acc_no,txn_amount,avail_balance,updated_at from transcation_log where transfer_acc_no="+acc_no+";";
		String query_total = "select count(*) as total from transcation_log where transfer_acc_no="+acc_no+";";
		
		if(isAdmin) {
			query = "select id,status,card_name,bank_name,ifsc,remarks,transfer_acc_no,acc_no,txn_amount,avail_balance,updated_at from transcation_log;";
			query_total = "select count(*) as total from transcation_log;";
		}
		
		System.out.println(query_total);
		System.out.println(query);
		try {
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			Statement stmt2 = (Statement) con.createStatement();
			ResultSet rss = stmt2.executeQuery(query_total);

						
			String[] columnNames = {"Card Name", "Bank Name", "IFSC","Status","Account No","Transfer From","Txn Amount","Avail Bal.","Txn Date","Remarks"};
			if(isAdmin) {
				columnNames = new String[] {"Card Name", "Bank Name", "IFSC","Status","Account No","Transfer From","Txn Amount","Avail Bal.","Txn Date","Remarks","Action"};
			}

			for(int i = 0;i<columnNames.length;i++) {
				TableColumn column = new TableColumn(table_1, SWT.NULL);
				column.setText(columnNames[i]);
			}

			int total = 0;
			if(rss.next()) {
				total = rss.getInt("total");
			}
			
			int ii = 0;
			while(rs.next()) {

				String card_name = rs.getString("card_name");
				String bank_name = rs.getString("bank_name");
				String ifsc = rs.getString("ifsc");
				String remarks = rs.getString("remarks");
				String transfer_acc_no = rs.getString("transfer_acc_no");
				String acc_no = rs.getString("acc_no");
				String txn_amount = rs.getString("txn_amount");
				String avail_balance = rs.getString("avail_balance");
				String updated_at = rs.getString("updated_at");
				String status = rs.getString("status");
				int id = rs.getInt("id");

				final TableItem item = new TableItem(table_1, SWT.NULL);
				item.setText(0,card_name);
				item.setText(1,bank_name);
				item.setText(2,ifsc);
				item.setText(3,status);
				item.setText(4,acc_no);
				item.setText(5,transfer_acc_no);
				item.setText(6,txn_amount);
				item.setText(7,avail_balance);
				item.setText(8,updated_at);
				item.setText(9,remarks);
				
				if(isAdmin) {
					Button button = new Button(table_1,SWT.None);
					button.setText("Delete");
					System.out.println("id = "+id);
					item.setData("id", id);
					item.setData("index", ii);
//					control.setBackground(item.getBackground());
					TableEditor editor = new TableEditor(table_1);
					editor.grabHorizontal  = true;
					editor.grabVertical = true;
					editor.setEditor(button , item, 10);
					editor.layout();
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							super.widgetSelected(e);
							
							int id = (int) item.getData("id");
							int index = (int) item.getData("index");
							
							String delete_query = "delete from transcation_log where id="+id+";";
							System.out.println(delete_query);
							try {
								stmt.executeUpdate(delete_query);
								MessageDialog.openInformation(shell, "Record Deleted", "Record deleted successfully !!");
								
							} catch (SQLException e1) {
								System.out.println("error "+e);
							}
							table_1.remove(index);
							table_1.redraw();
						
						}
					});
					ii++;
				}
					


			}

			for (int i = 0; i < columnNames.length; i++) {
				table_1.getColumn(i).pack();
			}


		}catch(Exception ee) {
			MessageDialog.openError(shell, "Error", ""+ee);
		}

	}
	
	void delete() {
		
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
		shell.setSize(1180, 708);
		shell.setText("SWT Application");

		Label lblAccountNo = new Label(shell, SWT.NONE);
		lblAccountNo.setBounds(47, 47, 171, 20);
		lblAccountNo.setText("Account No: "+acc_no);

		Label lblIfscCodeKkbk = new Label(shell, SWT.NONE);
		lblIfscCodeKkbk.setBounds(47, 85, 171, 20);
		lblIfscCodeKkbk.setText("IFSC Code: KKBK0000672");
		lblIfscCodeKkbk.setVisible(false);

		Label lblStatementOfAccount = new Label(shell, SWT.NONE);
		lblStatementOfAccount.setBounds(159, 10, 214, 20);
		lblStatementOfAccount.setText("STATEMENT OF ACCOUNT");

		table_1 = new Table(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		table_1.setBounds(10, 136, 1110, 514);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);


		//		TableColumn tblclmnCardName = new TableColumn(table_1, SWT.NONE);
		//		tblclmnCardName.setWidth(100);
		//		tblclmnCardName.setText("Card Name");
		//		
		//		TableColumn tblclmnAccountNo = new TableColumn(table_1, SWT.NONE);
		//		tblclmnAccountNo.setWidth(100);
		//		tblclmnAccountNo.setText("Account No");
		//		
		//		TableColumn tblclmnTransferFrom = new TableColumn(table_1, SWT.NONE);
		//		tblclmnTransferFrom.setWidth(118);
		//		tblclmnTransferFrom.setText("Transfer From");
		//		
		//		TableColumn tblclmnAccount = new TableColumn(table_1, SWT.NONE);
		//		tblclmnAccount.setWidth(101);
		//		tblclmnAccount.setText("Amount");
		//		
		//		TableColumn tblclmnTranscationDate = new TableColumn(table_1, SWT.NONE);
		//		tblclmnTranscationDate.setWidth(167);
		//		tblclmnTranscationDate.setText("Transcation Date");
		//		
		//		TableColumn tblclmnRemarks = new TableColumn(table_1, SWT.NONE);
		//		tblclmnRemarks.setWidth(145);
		//		tblclmnRemarks.setText("Remarks");




	}
}

