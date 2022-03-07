package fr.lelionvert.bankaccounct.services;

import org.springframework.stereotype.Service;

import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.exception.AccountException;


@Service
public class AccountServices {

	public Account deposit(Long amount, Account account) {
		if(amount == null) {
			throw new AccountException("Le montant a déposer sur votre compte n'est pas renseigné");
		}
		return Account.builder()
				.idAccount(account.getIdAccount())
				.amountAccount(account.getAmountAccount()+amount)
				.build();
	}
	
	public Account withdrawal(Long amount, Account account) {
		
		if(amount == null) {
			throw new AccountException("Le montant a retirer de votre compte n'est pas renseigné");
		}
		
		boolean validAmount = doWithdrawal(amount, account.getAmountAccount());
		if(!validAmount) {
			throw new AccountException("Le solde de votre compte est insuffisant");
		}

		return Account.builder()
				.idAccount(account.getIdAccount())
				.amountAccount(account.getAmountAccount()-amount)
				.build();
	}
	
	private boolean doWithdrawal(Long amount, Long amountAccount) {
		return amountAccount >= amount;
	}
}
