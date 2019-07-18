package com.techelevator;

import java.math.BigDecimal;

public class VendingChange {
	private int quarters, dimes, nickels;
	
	public VendingChange(BigDecimal change) {
		int pennies = (int)change.multiply(BigDecimal.valueOf(100)).longValue();
		this.quarters = pennies / 25;
		pennies = pennies % 25;
		this.dimes = pennies / 10;
		pennies = pennies % 10;
		this.nickels = pennies / 5;
	}

	public int getQuarters() {
		return quarters;
	}

	public int getDimes() {
		return dimes;
	}

	public int getNickels() {
		return nickels;
	}
	
	@Override
	public String toString() {
		return "Your change:\n" + "\tQuarters: " + this.quarters + "\n"
								+ "\tDimes: " + this.dimes + "\n"
								+ "\tNickels: " + this.nickels;
	}
}
