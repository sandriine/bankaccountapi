package fr.lelionvert.bankaccount.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lelionvert.bankaccounct.services.AccountServices;
import fr.lelionvert.bankaccount.entities.Account;

@RestController
@RequestMapping("account")
public class AccountController {

	private AccountServices accountService;
	
	@Autowired
	public AccountController(final AccountServices accountService) {
		this.accountService = accountService;
	}

	@PutMapping("/{idAccount}/deposit/{amount}")
	public Account makeDeposit(@PathVariable Long idAccount, @PathVariable Long amount, @RequestBody Account account) {
		return accountService.deposit(amount, account);
	}
	
	@PutMapping("/{idAccount}/withdrawal/{amount}")
	public Account makeWithdrawal(@PathVariable Long idAccount, @PathVariable Long amount, @RequestBody Account account) {
		return accountService.withdrawal(amount, account);
	}
	
	@GetMapping("/{idAccount}/historyOperations")
	public String historyOperations(@PathVariable Long idAccount, @RequestBody Account account) {
		return accountService.displayOperationsHistory(account);
	}
}
