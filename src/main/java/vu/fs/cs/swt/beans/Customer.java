package vu.fs.cs.swt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.UUID;

import javax.persistence.*;

@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = -2723446500566528020L;
	
	@Id
	@GeneratedValue
	private long _id;
	@Column(name = "first_name", nullable=false, length=30)
	private String _firstName;
	@Column(name = "last_name", nullable=false, length=30)
	private String _lastName;
	@Column(name = "username", nullable=false, length=25, unique=true)
	private String _username;
	@Column(name = "password", nullable=false, length=25)
	private String _password;
	@Column(name = "account_number", nullable=false, unique=true)
	private String _accountNumber;
	@Column(name = "beginning_balance", nullable=false)
	private Double _beginningBalance;
	@Column(name = "end_balance", nullable=false)
	private Double _endBalance;
	@Column(name = "initiate_loan", nullable=false)
	private Boolean _initiateLoan;
	@Column(name = "payment_loan", nullable=false)
	private Boolean _paymentLoan;
	@Column(name = "deposit_savings", nullable=false)
	private Boolean _depositSavings;
	@Column(name = "withdraw_savings", nullable=false)
	private Boolean _withdrawSavings;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "_customer", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Loan> _loans;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "_customer", fetch = FetchType.EAGER, orphanRemoval = true)
	private Saving _savingsAccount;
	
	public Customer() {}
	
	public Customer(String firstName, String lastName, String username,
			String password) throws Exception {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setUsername(username);
		this.setPassword(password);
		_accountNumber = UUID.randomUUID().toString();
		this.setBeginningBalance(0.0);
		this.setEndBalance(0.0);
		_savingsAccount = new Saving(this);
		this._loans = new ArrayList<Loan>();
		this.setInitiateLoan(false);
		this.setPaymentLoan(false);
		this.setDepositSavings(false);
		this.setWithdrawSavings(false);
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public String getFirstName() {
		return _firstName;
	}
	public void setFirstName(String firstName) throws Exception{
		Pattern pattern = Pattern.compile("[a-zA-Z]{2,30}");
	    if (!pattern.matcher(firstName).matches()) {
	        throw new Exception("First name should contain only letters (min 2, max 30)");
	    }
		this._firstName = firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String lastName) throws Exception{
		Pattern pattern = Pattern.compile("[a-zA-Z]{2,30}");
	    if (!pattern.matcher(lastName).matches()) {
	        throw new Exception("Last name should contain only letters (min 2, max 30");
	    }
		this._lastName = lastName;
	}
	public String getUsername() {
		return _username;
	}
	public void setUsername(String username) throws Exception{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]{5,25}");
	    if (!pattern.matcher(username).matches()) {
	        throw new Exception("Username should contain only alphanumeric characters (min 5, max 25)");
	    }
		this._username = username;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) throws Exception{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6,25}");
	    if (!pattern.matcher(password).matches()) {
	        throw new Exception("Password should contain only alphanumeric characters (min 6, max 25)");
	    }
		this._password = password;
	}
	public String getAccountNumber() {
		return _accountNumber;
	}
	public Double getBeginningBalance() {
		return _beginningBalance;
	}
	public void setBeginningBalance(Double _beginningBalance) throws Exception {
		if(_beginningBalance < 0) {
			throw new Exception("The balance cannot be negative");
		}
		this._beginningBalance = _beginningBalance;
	}
	public Double getEndBalance() {
		return _endBalance;
	}
	public void setEndBalance(Double _endBalance) throws Exception {
		if(_endBalance < 0) {
			throw new Exception("The balance cannot be negative");
		}
		this._endBalance = _endBalance;
	}
	public Saving getSavingsAccount() {
		return _savingsAccount;
	}
	public List<Loan> getLoans() {
		return _loans;
	}
	public void setLoans(List<Loan> _loans) throws Exception{
		if(this._loans.size() == 0 && _loans.size() > 3) {
			throw new Exception("A customer can have up to three loans.");
		}
		if(this._loans.size() == 1 && _loans.size() > 2) {
			throw new Exception("A customer can have up to three loans.");
		}
		if(this._loans.size() == 2 && _loans.size() > 1) {
			throw new Exception("A customer can have up to three loans.");
		}
		if(this._loans.size() >= 3) {
			throw new Exception("This customer already has three loans.");
		}
		this._loans = _loans;
	}
	public Boolean getInitiateLoan() {
		return _initiateLoan;
	}
	public void setInitiateLoan(Boolean _initiateLoan) {
		this._initiateLoan = _initiateLoan;
	}
	public Boolean getPaymentLoan() {
		return _paymentLoan;
	}
	public void setPaymentLoan(Boolean _paymentLoan) {
		this._paymentLoan = _paymentLoan;
	}
	public Boolean getDepositSavings() {
		return _depositSavings;
	}
	public void setDepositSavings(Boolean _depositSavings) {
		this._depositSavings = _depositSavings;
	}
	public Boolean getWithdrawSavings() {
		return _withdrawSavings;
	}
	public void setWithdrawSavings(Boolean _withdrawSavings) {
		this._withdrawSavings = _withdrawSavings;
	}
	
	public boolean correctPassword(String pass){
		return this._password.equals(pass);
	}
	public void addLoan(Loan loan) throws Exception {
		if(this._loans.size() >= 3) {
			throw new Exception("This customer already has three loans");
		}
		this._loans.add(loan);
	}
	public void removeLoan(Loan loan) throws Exception {
		if(this._loans.size() == 0) {
			throw new Exception("This customer has no loans");
		}
		this._loans.remove(loan);
	}
}
