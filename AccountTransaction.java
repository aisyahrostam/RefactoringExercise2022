public class AccountTransaction {

	String date;
	String type;
	double amount;

	public AccountTransaction() {
		this.date = "";
		this.type = "";
		this.amount = 0;

	}

	public AccountTransaction(String date, String type, double amount) {
		this.date = date;
		this.type = type;

		this.amount = amount;

	}

	public String getType() {
		return this.type;
	}

	public String toString() {
		return "\n" + "Date = " + this.date + "\n" + "Type = " + this.type + "\n" + "Amount = " + this.amount + "\n";

	}

}
