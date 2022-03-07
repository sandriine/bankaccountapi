package fr.lelionvert.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.lelionvert.bankaccounct.services.AccountServices;
import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.entities.Operation;
import fr.lelionvert.bankaccount.exception.AccountException;

class AccountServiceTest {
	
	@Mock
	private AccountServices accountService;
	
	@BeforeEach
	public void init() {
		accountService = new AccountServices();
	}

	@Test
	void makeDeposit() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterDeposit = accountService.deposit(100L, account);
		assertNotNull(accountAfterDeposit);
		assertNotEquals(account.getAmountAccount(), accountAfterDeposit.getAmountAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 200L);
	}
	
	@Test
	void makeDepositWithNullAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.deposit(null, account);
		});
		assertEquals(exeption.getMessage(), "Le montant a déposer sur votre compte n'est pas renseigné");
	}
	
	@Test
	void makeWithdrawal() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(10L).operations(new HashSet<Operation>()).build();
		Account accountAfterDeposit = accountService.withdrawal(8L, account);
		assertNotNull(accountAfterDeposit);
		assertNotEquals(account.getAmountAccount(), accountAfterDeposit.getAmountAccount());
		assertEquals(account.getIdAccount(), accountAfterDeposit.getIdAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 2L);
	}
	
	@Test
	void makeWithdrawalWithAmountEqualAmountAccount() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(10L).operations(new HashSet<Operation>()).build();
		Account accountAfterDeposit = accountService.withdrawal(10L, account);
		assertEquals(account.getIdAccount(), accountAfterDeposit.getIdAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 0L);
	}
	
	@Test
	void makeWithdrawalWithInvalidAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.withdrawal(200L, account);
		});
		assertEquals(exeption.getMessage(), "Le solde de votre compte est insuffisant");
	}
	
	@Test
	void makeWithdrawalWithNullAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.withdrawal(null, account);
		});
		assertEquals(exeption.getMessage(), "Le montant a retirer de votre compte n'est pas renseigné");
	}

	@Test
	void makeDepositAndWithdrawal() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterOperations = accountService.deposit(100L, account);
		accountAfterOperations = accountService.withdrawal(50L, accountAfterOperations);
		assertNotNull(accountAfterOperations);
		assertEquals(accountAfterOperations.getOperations().size(), 2);
		assertEquals(accountAfterOperations.getAmountAccount(), 150L);
	}
	
	@Test
	void displayOperationsHistory() {
		Account account = Account.builder().idAccount(1234567L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterOperations = accountService.deposit(100L, account);
		accountAfterOperations = accountService.withdrawal(50L, accountAfterOperations);
		String history = accountService.displayOperationsHistory(accountAfterOperations);
		Assertions.assertThat(accountAfterOperations.getOperations().size()).isEqualTo(2);
		Assertions.assertThat(accountAfterOperations.getOperations()).hasSize(2);
		Assertions.assertThat(history).hasLineCount(2);
	}
}
