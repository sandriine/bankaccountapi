package fr.lelionvert.bankaccounct.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.entities.Operation;
import fr.lelionvert.bankaccount.exception.AccountException;


@Service
public class AccountServices {

	public Account deposit(Long amount, Account account) {
		if(amount == null) {
			throw new AccountException("Le montant à déposer sur votre compte n'est pas renseigné");
		}
		
		Account accountAfterDeposit = Account.builder()
			.idAccount(account.getIdAccount())
			.amountAccount(account.getAmountAccount()+amount)
			.operations(account.getOperations())
			.build();
		
		this.addOperationHistory("Deposit", amount, accountAfterDeposit);
		
		return accountAfterDeposit;
	}
	
	public Account withdrawal(Long amount, Account account) {
		
		if(amount == null) {
			throw new AccountException("Le montant a retirer de votre compte n'est pas renseigné");
		}
		
		boolean validAmount = doWithdrawal(amount, account.getAmountAccount());
		if(!validAmount) {
			throw new AccountException("Le solde de votre compte est insuffisant");
		}
		
		Account accountAfterWithdrawal = Account.builder()
				.idAccount(account.getIdAccount())
				.amountAccount(account.getAmountAccount()-amount)
				.operations(account.getOperations())
				.build();
		
		this.addOperationHistory("Withdrawal", amount, accountAfterWithdrawal);

		return accountAfterWithdrawal;
	}
	
	private boolean doWithdrawal(Long amount, Long amountAccount) {
		return amountAccount >= amount;
	}
	
	private Account addOperationHistory(String operationName, Long amount, Account account) {
		Operation operation = Operation.builder()
				.amountOperation(amount)
				.balance(account.getAmountAccount())
				.dateOperation(new Date())
				.nameOperation(operationName)
				.build();
		List<Operation> op = account.getOperations();
		op.add(operation);
		return Account.builder()
				.idAccount(account.getIdAccount())
				.amountAccount(account.getAmountAccount())
				.operations(op)
				.build();
	}
	
	public String displayOperationsHistory(Account account) {
		String history = "";
		for(Operation operation: account.getOperations()) {
			history += operation.toString();
		}
		return history;
	}
}
