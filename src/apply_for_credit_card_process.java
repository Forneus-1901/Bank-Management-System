import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.events.GestureEvent;

public class apply_for_credit_card_process {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			apply_for_credit_card_process window = new apply_for_credit_card_process();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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
		shell.setSize(872, 511);
		shell.setText("SWT Application");
		
		Label lblCreditCardApply = new Label(shell, SWT.NONE);
		lblCreditCardApply.setBounds(308, 10, 184, 20);
		lblCreditCardApply.setText("Credit Card apply process");
		
		Label lblHowDoBanks = new Label(shell, SWT.NONE);
		lblHowDoBanks.setBounds(10, 55, 317, 30);
		lblHowDoBanks.setText("How do banks process credit card application?");
		
		Label lblStepCompare = new Label(shell, SWT.NONE);
		lblStepCompare.setBounds(20, 91, 357, 20);
		lblStepCompare.setText("Step 1: Compare credit card offers.");
		
		Label lblStepSelect = new Label(shell, SWT.NONE);
		lblStepSelect.setBounds(20, 133, 474, 20);
		lblStepSelect.setText("Step 2: Select from the top cards offered by leading Indian banks.");
		
		Label lblStepCheck = new Label(shell, SWT.NONE);
		lblStepCheck.setBounds(20, 181, 480, 20);
		lblStepCheck.setText("Step 3: Check your eligibility by entering a few personal details.");
		
		Label lblStepEnjoy = new Label(shell, SWT.NONE);
		lblStepEnjoy.setBounds(22, 224, 544, 20);
		lblStepEnjoy.setText("Step 4: Enjoy instant approval online after filling out your application form");
		
		Label lblWhatIsThe = new Label(shell, SWT.NONE);
		lblWhatIsThe.setBounds(10, 272, 484, 20);
		lblWhatIsThe.setText("What is the minimum salary for credit card?");
		
		Label lblSalaryIsA = new Label(shell, SWT.NONE);
		lblSalaryIsA.setBounds(10, 310, 834, 73);
		lblSalaryIsA.setText("Salary is a crucial deciding factor for credit cards.\n Someone earning say Rs 50,000 per month is eligible for a different type of card than a person earning Rs 25,000 per month.\n On an average, income requirement is between Rs 1,44,000 and Rs 25,000 per month");
		
		Label lblCoustmerservice = new Label(shell, SWT.NONE);
		lblCoustmerservice.setBounds(10, 426, 126, 20);
		lblCoustmerservice.setText("CoustmerService: ");
		
		Link link_1 = new Link(shell, SWT.NONE);
		link_1.setBounds(207, 534, 60, 20);
		
		Link link_2 = new Link(shell, SWT.NONE);
		
		link_2.setTouchEnabled(true);
		link_2.setBounds(142, 426, 109, 20);
		link_2.setText("<a href=\"https://api.whatsapp.com/send?phone=919970507646\">Open Chat</a>");
		link_2.addSelectionListener(new SelectionAdapter() {
			 @Override
			    public void widgetSelected(SelectionEvent e) {
			        Program.launch("https://api.whatsapp.com/send?phone=919970507646");
			    }
		});
//		link_2.addSelectionListener(new SelectionAdapter()  {
//		    @Override
//		    public void widgetSelected(SelectionEvent e) {
//		        Program.launch("https:/eclipse.org");
//		    }
////		});
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnExit.setBounds(754, 421, 90, 30);
		btnExit.setText("EXIT");

	}
}
