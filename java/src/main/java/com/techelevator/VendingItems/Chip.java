package com.techelevator.VendingItems;

public class Chip extends VendingMachineItem {

	public Chip(String itemName, String itemPrice) {
		super(itemName, itemPrice);
	}

	@Override
	public String getEatingNoise() {
		return "Crunch Crunch, Yum!";
	}

}
