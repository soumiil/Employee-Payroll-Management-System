package payroll.service;

import payroll.exception.PayrollCalculationException;
import payroll.model.Employee;
import payroll.model.SalarySlip;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle payroll processing using generic methods.
 */
public class PayrollProcessor {

    /**
     * Concept: Generic Method with Bounded Type (<T extends Employee>)
     * Calculates the total payroll for a list of specific employee types.
     */
    public <T extends Employee> double calculateTotalPayroll(List<T> employees) throws PayrollCalculationException {
        if (employees == null) {
            throw new PayrollCalculationException("Employee list cannot be null.");
        }
        double total = 0;
        for (T emp : employees) {
            // Concept: Polymorphism in action (calculateNetSalary behaves differently based on subclass)
            total += emp.calculateNetSalary();
        }
        return total;
    }

    /**
     * Concept: Bounded Wildcard (List<? extends Employee>)
     * Calculates total payroll.
     * Difference from named type parameter (<T extends Employee>):
     * - The wildcard version is slightly more restrictive inside the method if you needed to add elements to the list or return the exact type,
     *   because the exact type of the list is unknown. However, for read-only operations like iteration, it is more concise and equally powerful.
     */
    public double calculateTotalPayrollWildcard(List<? extends Employee> employees) throws PayrollCalculationException {
        if (employees == null) {
            throw new PayrollCalculationException("Employee list cannot be null.");
        }
        double total = 0;
        for (Employee emp : employees) {
            total += emp.calculateNetSalary();
        }
        return total;
    }

    /**
     * Generates salary slips for a list of employees for a specific month and year.
     */
    public <T extends Employee> List<SalarySlip> generateSlips(List<T> employees, Month month, int year) {
        List<SalarySlip> slips = new ArrayList<>();
        int sequence = 1;
        String monthYear = month.toString() + " " + year;
        for (T emp : employees) {
            String slipId = String.format("SLP-%d-%02d-%03d", year, month.getValue(), sequence++);
            slips.add(new SalarySlip(slipId, monthYear, emp));
        }
        return slips;
    }

    /**
     * Finds the highest paid employee in the given list.
     */
    public <T extends Employee> T findHighestPaid(List<T> employees) {
        if (employees == null || employees.isEmpty()) return null;
        T highestPaid = employees.get(0);
        for (int i = 1; i < employees.size(); i++) {
            if (employees.get(i).calculateNetSalary() > highestPaid.calculateNetSalary()) {
                highestPaid = employees.get(i);
            }
        }
        return highestPaid;
    }
}
