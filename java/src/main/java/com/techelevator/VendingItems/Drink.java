package com.techelevator.VendingItems;

public class Drink extends VendingMachineItem {

	public Drink(String itemName, String itemPrice) {
		super(itemName, itemPrice);
	}

	@Override
	public String getEatingNoise() {
		return "Glug Glug, Yum!";
	}

}
