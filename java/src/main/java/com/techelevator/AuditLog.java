package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.techelevator.VendingItems.VendingMachineItem;

public class AuditLog {
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	
	private PrintWriter auditWriter;
	
	public AuditLog() {
		File auditFile = new File("Audit.txt");
		try {
			auditWriter = new PrintWriter(auditFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void recordDeposit(BigDecimal depositAmount, BigDecimal currentBalance) {
		auditWriter.println(dtf.format(LocalDateTime.now()) + " " + "FEED MONEY: " + "$" + depositAmount + 
										" $" + currentBalance);
	}
	
	public void recordPurchase(VendingMachineItem item, String slotKey, BigDecimal currentBalance) {
		auditWriter.println(dtf.format(LocalDateTime.now()) + " " + item.getItemName() + " " + 
										slotKey + " $" + item.getItemPrice() + " $" + currentBalance);
	}
	
	public void recordChangeReturn(BigDecimal prevBalance) {
		auditWriter.println(dtf.format(LocalDateTime.now()) + " " + "GIVE CHANGE: " + "$" + 
										prevBalance + " $0.00");
	}
	
	public void closeAudit() {
		auditWriter.close();
	}
}
