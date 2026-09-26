package payroll.model;

import payroll.exception.InvalidEmployeeDataException;
import java.time.LocalDate;

/**
 * Concept: Inheritance (Extends abstract Employee)
 */
public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String employeeId, String name, String department, String email, LocalDate joiningDate,
                            double hourlyRate, int hoursWorked) {
        super(employeeId, name, department, email, joiningDate);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new InvalidEmployeeDataException("Hourly rate cannot be negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        if (hoursWorked < 0 || hoursWorked > 300) {
            throw new InvalidEmployeeDataException("Hours worked must be between 0 and 300.");
        }
        this.hoursWorked = hoursWorked;
    }

    // Concept: Method Overriding
    @Override
    public double calculateSalary() {
        double gross = hourlyRate * hoursWorked;
        if (hoursWorked > 120) {
            int overtimeHours = hoursWorked - 120;
            // The first 120 hours are already calculated in gross (at 1.0x).
            // We need to add 0.5x for the overtime hours to make it 1.5x total for those hours.
            gross += (overtimeHours * hourlyRate * 0.5); 
        }
        return gross;
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }

    @Override
    public double calculateTax() {
        double gross = calculateSalary();
        if (gross > 25000) {
            return 0.05 * gross;
        }
        return 0;
    }

    @Override
    public double calculateNetSalary() {
        return calculateSalary() - calculateTax();
    }
}
