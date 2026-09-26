package payroll;

import payroll.ui.MenuController;

/**
 * Entry point for the Employee Payroll Management System.
 */
public class Main {
    public static void main(String[] args) {
        MenuController menu = new MenuController();
        menu.start();
    }
}
