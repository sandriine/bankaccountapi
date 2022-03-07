package fr.lelionvert.bankaccounct.services;

import org.springframework.stereotype.Service;

import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.exception.AccountException;


@Service
public class AccountServices {

	public Account deposit(Long amount, Account account) {
		if(amount == null) {
			throw new AccountException("Le montant a d�poser sur votre compte n'est pas renseign�");
		}
		return Account.builder()
				.idAccount(account.getIdAccount())
				.amountAccount(account.getAmountAccount()+amount)
				.build();
	}
}
