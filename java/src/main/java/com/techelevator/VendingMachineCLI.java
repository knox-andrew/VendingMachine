package com.techelevator;

import java.math.BigDecimal;

import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report** Optional hidden menu item";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, 
														MAIN_MENU_OPTION_PURCHASE,
														MAIN_MENU_OPTION_EXIT, 
														MAIN_MENU_OPTION_SALES_REPORT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
															PURCHASE_MENU_OPTION_SELECT_PRODUCT, 
															PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	
	private static final String DEPOSIT_MENU_OPTION_ONE_DOLLAR = "1.00";
	private static final String DEPOSIT_MENU_OPTION_FIVE_DOLLAR = "5.00";
	private static final String DEPOSIT_MENU_OPTION_TEN_DOLLAR = "10.00";
	private static final String DEPOSIT_MENU_OPTION_TWENTY_DOLLAR = "20.00";
	
	private static final String[] DEPOSIT_MENU_OPTIONS = { DEPOSIT_MENU_OPTION_ONE_DOLLAR, 
															DEPOSIT_MENU_OPTION_FIVE_DOLLAR, 
															DEPOSIT_MENU_OPTION_TEN_DOLLAR, 
															DEPOSIT_MENU_OPTION_TWENTY_DOLLAR };
	private static final int MAIN_MENU = 0;
	private static final int PURCHASE_MENU = 1;

	private Menu menu;
	private static Scanner scan = new Scanner(System.in);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		VendingMachine vm = new VendingMachine();

		boolean repeat = true;
		int currentMenu = 0;
		while (repeat) {
			if(currentMenu == MAIN_MENU) {
				String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
				if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					System.out.println(vm);
				} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					currentMenu = PURCHASE_MENU;
				} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
					vm.closeAuditFile();
					repeat = false;
				} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
					System.out.println("\nSales Report Printed");
					vm.createSalesReport();
				}
			} else if(currentMenu == PURCHASE_MENU) {
				String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
					//System.out.print("Enter the amount (in whole dollars) to deposit >>> ");
					
					System.out.println("Enter bill to deposit");
					String billChoice = (String) menu.getChoiceFromOptions(DEPOSIT_MENU_OPTIONS);
					
					//String message = vm.deposit(scan.nextLine());
					String message = vm.deposit(billChoice);
					System.out.println(message);

				} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
					if (vm.getBalance().compareTo(BigDecimal.ZERO) == 0) {
						System.out.println("You haven't deposited any money");
					} else {
						System.out.println(vm);
						System.out.print("\nEnter the item slot >>> ");
						String message = vm.purchaseItem(scan.nextLine());
						System.out.println(message + "\n");
					}
				} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
					String message = vm.getChange();
					System.out.println(message);
					currentMenu = MAIN_MENU;
				}
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

}
