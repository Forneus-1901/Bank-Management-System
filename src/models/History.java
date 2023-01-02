package models;

public class History {
	
	String txn_amount;
	String avail_balance;
	String status;
	String updated_at;
	String card_name;
	String bank_name;
	String ifsc;
	String remarks;
	String acc_no;
	String transfer_acc_no;
	
	History(String txtamt,String avail,String status, String updated_at,String cardName,String bName,String ifsc,String remarks,String acc,String transfer){
		this.txn_amount = txtamt;
		this.avail_balance = avail;
		this.status = status;
		this.updated_at = updated_at;
		this.card_name = cardName;
		this.bank_name = bName;
		this.ifsc = ifsc;
		this.remarks = remarks;
		this.acc_no = acc;
		this.transfer_acc_no = transfer;
	}
}
