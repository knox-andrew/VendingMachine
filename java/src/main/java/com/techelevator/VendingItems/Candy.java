package com.techelevator.VendingItems;

public class Candy extends VendingMachineItem {

	public Candy(String itemName, String itemPrice) {
		super(itemName, itemPrice);
	}

	@Override
	public String getEatingNoise() {
		return "Munch Munch, Yum!";
	}

}
