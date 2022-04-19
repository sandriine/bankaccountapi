package fr.lelionvert.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.lelionvert.bankaccounct.services.AccountServices;
import fr.lelionvert.bankaccount.entities.Account;
import fr.lelionvert.bankaccount.entities.Operation;
import fr.lelionvert.bankaccount.exception.AccountException;

public class AccountWithdrawalTest {
	
	@Mock
	private AccountServices accountService;
	
	@BeforeEach
	public void init() {
		accountService = new AccountServices();
	}
	
	@Test
	void shouldCreateABankAccountAndRemoveSomeMoney() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(10L).operations(new ArrayList<Operation>()).build();
		Account accountAfterDeposit = accountService.withdrawal(8L, account);
		assertNotNull(accountAfterDeposit);
		assertNotEquals(account.getAmountAccount(), accountAfterDeposit.getAmountAccount());
		assertEquals(account.getIdAccount(), accountAfterDeposit.getIdAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 2L);
	}
	
	@Test
	void shouldAnErrorWhenMakeWithdrawalWithAmountEqualAmountAccount() {
		Account account = Account.builder().idAccount(123456789L).amountAccount(10L).operations(new ArrayList<Operation>()).build();
		Account accountAfterDeposit = accountService.withdrawal(10L, account);
		assertEquals(account.getIdAccount(), accountAfterDeposit.getIdAccount());
		assertEquals(accountAfterDeposit.getAmountAccount(), 0L);
	}
	
	@Test
	void shouldAnErrorWhenMakeWithdrawalWithInvalidAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new ArrayList<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.withdrawal(200L, account);
		});
		assertEquals(exeption.getMessage(), "Le solde de votre compte est insuffisant");
	}
	
	@Test
	void shouldAnErrorWhenMakeWithdrawalWithNullAmount() {
		final Account account = Account.builder().idAccount(123456789L).amountAccount(100L).operations(new ArrayList<Operation>()).build();
		Exception exeption = assertThrows(AccountException.class, () -> {
			 accountService.withdrawal(null, account);
		});
		assertEquals(exeption.getMessage(), "Le montant a retirer de votre compte n'est pas renseigné");
	}

}
