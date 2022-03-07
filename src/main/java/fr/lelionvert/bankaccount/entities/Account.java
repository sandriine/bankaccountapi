package fr.lelionvert.bankaccount.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
@AllArgsConstructor
public class Account {
	private final Long idAccount;
	private final Long amountAccount;
}
