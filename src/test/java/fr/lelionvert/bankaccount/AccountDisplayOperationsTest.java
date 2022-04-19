package fr.lelionvert.bankaccount;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.lelionvert.bankaccounct.services.AccountServices;
import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.entities.Operation;

class AccountDisplayOperationsTest {
	
	@Mock
	private AccountServices accountService;
	
	@BeforeEach
	public void init() {
		accountService = new AccountServices();
	}
	
	@Test
	void shouldCreateABankAccountAndDisplayOperationsHistory() {
		Account account = Account.builder().idAccount(1234567L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterOperations = accountService.deposit(100L, account);
		accountAfterOperations = accountService.withdrawal(50L, accountAfterOperations);
		String history = accountService.displayOperationsHistory(accountAfterOperations);
		Assertions.assertThat(accountAfterOperations.getOperations().size()).isEqualTo(2);
		Assertions.assertThat(accountAfterOperations.getOperations()).hasSize(2);
		Assertions.assertThat(history).hasLineCount(2);
	}
}
