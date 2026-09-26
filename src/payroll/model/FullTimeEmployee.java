package payroll.model;

import payroll.exception.InvalidEmployeeDataException;
import java.time.LocalDate;

/**
 * Concept: Inheritance (Extends abstract Employee)
 */
public class FullTimeEmployee extends Employee {
    private double basicSalary;
    private double hra;
    private double da;
    private double bonus;
    private double pfDeduction;

    public FullTimeEmployee(String employeeId, String name, String department, String email, LocalDate joiningDate,
                            double basicSalary, double hra, double da, double bonus, double pfDeduction) {
        super(employeeId, name, department, email, joiningDate);
        setBasicSalary(basicSalary);
        this.hra = hra;
        this.da = da;
        setBonus(bonus);
        setPfDeduction(pfDeduction);
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary < 0) {
            throw new InvalidEmployeeDataException("Basic salary cannot be negative.");
        }
        this.basicSalary = basicSalary;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getDa() {
        return da;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) throw new InvalidEmployeeDataException("Bonus cannot be negative.");
        this.bonus = bonus;
    }

    public double getPfDeduction() {
        return pfDeduction;
    }

    public void setPfDeduction(double pfDeduction) {
        if (pfDeduction < 0) throw new InvalidEmployeeDataException("PF Deduction cannot be negative.");
        this.pfDeduction = pfDeduction;
    }

    // Concept: Method Overriding
    @Override
    public double calculateSalary() {
        return basicSalary + hra + da + bonus;
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }

    @Override
    public double calculateTax() {
        double gross = calculateSalary();
        if (gross > 50000) {
            return 0.10 * gross;
        } else if (gross > 25000) {
            return 0.05 * gross;
        }
        return 0;
    }

    @Override
    public double calculateNetSalary() {
        return calculateSalary() - pfDeduction - calculateTax();
    }
}
