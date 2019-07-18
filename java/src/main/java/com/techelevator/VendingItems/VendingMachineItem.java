package com.techelevator.VendingItems;

import java.math.BigDecimal;

public abstract class VendingMachineItem {
	public static final int ITEM_STARTING_AMOUNT = 5;
	
	private String itemName;
	private BigDecimal itemPrice;
	private int itemAmount;
	
	public VendingMachineItem(String itemName, String priceAsString) {
		this.itemName = itemName;
		this.itemPrice = new BigDecimal(priceAsString);
		itemAmount = ITEM_STARTING_AMOUNT;
	}
	
	public abstract String getEatingNoise();

	public String getItemName() {
		return itemName;
	}
	
	public int numSold() {
		return ITEM_STARTING_AMOUNT - this.itemAmount;
	}
	
	public BigDecimal getRevenueFromItem() {
		return itemPrice.multiply(new BigDecimal(numSold()));
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	
	public int getItemAmount() {
		return this.itemAmount;
	}
	
	public void decrementStock() {
		if(itemAmount > 0) {
			this.itemAmount--;
		}
	}
	
	@Override
	public String toString() {
		String result = itemName + " $" + itemPrice;
		if(itemAmount == 0){
			result += " (Sold Out)";
		}
		return result;
	}
}
