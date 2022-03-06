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

	public String getDate() {
		return this.date;
	}

	public String getType() {
		return this.type;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "\n" + "Date = " + this.date + "\n" + "Type = " + this.type + "\n" + "Amount = " + this.amount + "\n";

	}

}
