package com.techelevator.VendingItems;

public class Gum extends VendingMachineItem {

	public Gum(String itemName, String itemPrice) {
		super(itemName, itemPrice);
	}

	@Override
	public String getEatingNoise() {
		return "Chew Chew, Yum!";
	}

}
