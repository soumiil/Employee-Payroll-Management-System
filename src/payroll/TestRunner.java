package payroll;

import payroll.model.ContractEmployee;
import payroll.model.FullTimeEmployee;
import payroll.model.PartTimeEmployee;

import java.time.LocalDate;

/**
 * Validates salary calculation logic based on given rules.
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("Starting automated salary tests...");
        int passed = 0;
        int failed = 0;

        // Test 1: Full-Time Employee calculation
        // basic=40k, HRA=8k, DA=4k, bonus=3k, pf=4.8k
        // gross = 55k
        // tax = 10% of 55k = 5.5k
        // net = 55k - 4.8k - 5.5k = 44.7k
        FullTimeEmployee ft = new FullTimeEmployee("T1", "FT", "D", "a@b.com", LocalDate.now(), 40000, 8000, 4000, 3000, 4800);
        if (assertEquals(55000.0, ft.calculateSalary(), "FT Gross")) passed++; else failed++;
        if (assertEquals(5500.0, ft.calculateTax(), "FT Tax")) passed++; else failed++;
        if (assertEquals(44700.0, ft.calculateNetSalary(), "FT Net")) passed++; else failed++;

        // Test 2: Part-Time Employee (no overtime)
        // rate=200, hours=100
        // gross = 20k
        // tax = 0 (since <= 25000)
        // net = 20k
        PartTimeEmployee pt1 = new PartTimeEmployee("T2", "PT1", "D", "a@b.com", LocalDate.now(), 200, 100);
        if (assertEquals(20000.0, pt1.calculateSalary(), "PT1 Gross")) passed++; else failed++;
        if (assertEquals(0.0, pt1.calculateTax(), "PT1 Tax")) passed++; else failed++;
        if (assertEquals(20000.0, pt1.calculateNetSalary(), "PT1 Net")) passed++; else failed++;

        // Test 3: Part-Time Employee (with overtime)
        // rate=200, hours=130
        // gross = (120*200) + (10*200*1.5) = 24000 + 3000 = 27000
        // wait, my formula in code: gross = 130 * 200 + (10 * 200 * 0.5) = 26000 + 1000 = 27000
        // tax = 5% of 27000 = 1350
        // net = 27000 - 1350 = 25650
        PartTimeEmployee pt2 = new PartTimeEmployee("T3", "PT2", "D", "a@b.com", LocalDate.now(), 200, 130);
        if (assertEquals(27000.0, pt2.calculateSalary(), "PT2 Gross")) passed++; else failed++;
        if (assertEquals(1350.0, pt2.calculateTax(), "PT2 Tax")) passed++; else failed++;
        if (assertEquals(25650.0, pt2.calculateNetSalary(), "PT2 Net")) passed++; else failed++;

        // Test 4: Contract Employee
        // contractAmount=600000, duration=12, bonus=5000
        // monthly base = 50000
        // gross = 50000 + 5000 = 55000
        // tax = 10% of 55000 = 5500
        // net = 55000 - 5500 = 49500
        ContractEmployee ce = new ContractEmployee("T4", "CE", "D", "a@b.com", LocalDate.now(), 600000, 12, LocalDate.now(), 5000);
        if (assertEquals(55000.0, ce.calculateSalary(), "CE Gross")) passed++; else failed++;
        if (assertEquals(5500.0, ce.calculateTax(), "CE Tax")) passed++; else failed++;
        if (assertEquals(49500.0, ce.calculateNetSalary(), "CE Net")) passed++; else failed++;

        System.out.println("Tests Finished. Passed: " + passed + " / Failed: " + failed);
    }

    private static boolean assertEquals(double expected, double actual, String testName) {
        if (Math.abs(expected - actual) < 0.01) {
            System.out.println("PASS: " + testName);
            return true;
        } else {
            System.err.println("FAIL: " + testName + " | Expected: " + expected + ", Actual: " + actual);
            return false;
        }
    }
}
