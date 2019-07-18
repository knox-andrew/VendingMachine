package com.techelevator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.VendingItems.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class VendingMachine {
	
	public static final String CHIPS_IDENTIFIER = "Chip";
	public static final String CANDY_IDENTIFIER = "Candy";
	public static final String DRINK_IDENTIFIER = "Drink";
	public static final String GUM_IDENTIFIER = "Gum";
		
	private Map<String, VendingMachineItem> vendingItems = new LinkedHashMap<>();
	private BigDecimal balance = new BigDecimal(0);
	private AuditLog log = new AuditLog();
	
	
	public VendingMachine() {
		File vendingFile = new File("VendingMachine.txt");
		try (Scanner reader = new Scanner(vendingFile)) {
			while(reader.hasNextLine()) {
				String[] line = reader.nextLine().split("\\|");
				
				String slotKey = line[0], itemName = line[1], 
						priceAsString = line[2], itemType = line[3];
				
				VendingMachineItem vmItem = null;
				if(itemType.equals(CHIPS_IDENTIFIER)) {
					vmItem = new Chip(itemName, priceAsString);
					
				} else if(itemType.equals(CANDY_IDENTIFIER)) {
					vmItem = new Candy(itemName, priceAsString);
					
				} else if(itemType.equals(DRINK_IDENTIFIER)) {
					vmItem = new Drink(itemName, priceAsString);
					
				} else if(itemType.equals(GUM_IDENTIFIER)){
					vmItem = new Gum(itemName, priceAsString);
				}
				
				if(vmItem != null) {
					vendingItems.put(slotKey, vmItem);
				}
			}
		
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
			
	public String purchaseItem(String slotKey) {
		String message = null;
		VendingMachineItem item = vendingItems.get(slotKey);
		
		if(item == null) {
			message = "That item doesn't exist";
		} else if(item.getItemAmount() == 0) {
			message = "Item out of stock";
		} else if(balance.compareTo(item.getItemPrice()) == -1) {
			message = "Insufficient funds, please make additional deposit";
		} else {
			this.subtractFromBalance(item.getItemPrice());
			item.decrementStock();
			message = item.getEatingNoise();
			
			log.recordPurchase(item, slotKey, this.balance);
			try {
				InputStream inStream = new FileInputStream("17011__cognito-perceptu__coke-machine-hum.wav");
				AudioStream audioStream = new AudioStream(inStream);
				AudioPlayer.player.start(audioStream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return message;
	}
	
	public void createSalesReport() {
		File report = new File("SalesReport.txt");
		BigDecimal totalSales = BigDecimal.ZERO;
		VendingMachineItem item;
		
		try (PrintWriter reportWriter = new PrintWriter(report)) {
			for(String key : vendingItems.keySet()) {
				item = vendingItems.get(key);
				totalSales = totalSales.add(item.getRevenueFromItem());
				reportWriter.println(item.getItemName() + "|" + item.numSold());
			}
			
			reportWriter.println("\n\n***TOTAL SALES** $" + totalSales);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void closeAuditFile() {
		log.closeAudit();
	}
	
	public String getChange() {
		BigDecimal prevBalance = this.balance;
		VendingChange change = new VendingChange(this.balance);
		this.balance = BigDecimal.ZERO;
		
		log.recordChangeReturn(prevBalance);		
		try {
			InputStream inStream = new FileInputStream("324241__soundatadm__coin-drop.wav");
			AudioStream audioStream = new AudioStream(inStream);
			AudioPlayer.player.start(audioStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return change.toString();
	}
	
	
	
	public String deposit(String depositAsString) {
		String message = null;
		try {
			BigDecimal deposit = new BigDecimal(depositAsString);
			deposit = deposit.setScale(2);
			balance = balance.add(deposit);
			message = "Current balance: $" + this.getBalance();
			
			log.recordDeposit(deposit, this.balance);
			try {
				InputStream inStream = new FileInputStream("324243__soundatadm__money-vending-machine.wav");
				AudioStream audioStream = new AudioStream(inStream);
				AudioPlayer.player.start(audioStream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			message = "Please only deposit legal tender";
		} catch (ArithmeticException e) {
			message = "Please only deposit whole dollars";
		}
		
		return message;
	}
		
	public void subtractFromBalance(BigDecimal amount) {
		balance = balance.subtract(amount);
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	/**
	 * Compiles all the items w/ info in the vending machine 
	 * and returns them concatenated for display purposes
	 */
	@Override
	public String toString() {
		String result = "";
		
		for(String key : vendingItems.keySet()) {
			result += "\n" + key + " " + vendingItems.get(key);
		}
		
		return result;
	}
}
