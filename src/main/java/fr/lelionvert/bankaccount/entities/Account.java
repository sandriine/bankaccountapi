package fr.lelionvert.bankaccount.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
public class Account {
	private final Long idAccount;
	private final Long amountAccount;
	private final List<Operation> operations;
}
