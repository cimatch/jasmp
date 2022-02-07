package jp.co.atomsware.swift.tag;

public class Tag32A extends BaseTag {

	String date;
	String currency;
	String amount;

	private static final long serialVersionUID = 1L;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
		super.value = date + currency + amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
