package fr.lelionvert.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.lelionvert.bankaccounct.services.AccountServices;
import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.entities.Operation;
import fr.lelionvert.bankaccount.exception.AccountException;

public class AccountDepositTest {

	@Mock
	private AccountServices accountService;
	
	@BeforeEach
	public void init() {
		accountService = new AccountServices();
	}

	@Test
	void shouldCreateABankAccountAndAddSomeMoney() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterDeposit = accountService.deposit(100L, account);
		assertNotNull(accountAfterDeposit);
		assertNotEquals(account.getAmountAccount(), accountAfterDeposit.getAmountAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 200L);
	}
	
	@Test
	void shouldAnErrorWhenMakeDepositWithNullAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.deposit(null, account);
		});
		assertEquals(exeption.getMessage(), "Le montant à déposer sur votre compte n'est pas renseigné");
	}
	
	@Test
	void shouldCreateABankAccountAndMakeDepositAndWithdrawal() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new HashSet<Operation>()).build();
		Account accountAfterOperations = accountService.deposit(100L, account);
		accountAfterOperations = accountService.withdrawal(50L, accountAfterOperations);
		assertNotNull(accountAfterOperations);
		assertEquals(accountAfterOperations.getOperations().size(), 2);
		assertEquals(accountAfterOperations.getAmountAccount(), 150L);
	}
}
