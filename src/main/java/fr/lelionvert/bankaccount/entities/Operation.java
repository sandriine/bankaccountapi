package fr.lelionvert.bankaccount.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
@AllArgsConstructor
public class Operation {

	private final Date dateOperation;
	private final String nameOperation;
	private final Long amountOperation;
	private final Long balance;
	
	@Override
	public String toString() {
		return "Date: " + dateOperation + ", Operation: " + nameOperation + ", Amount: "
				+ amountOperation + ", Balance: " + balance + "\n";
	}
	
	
	
}
