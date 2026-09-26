package payroll.model;

import payroll.exception.InvalidEmployeeDataException;
import java.time.LocalDate;

/**
 * Concept: Inheritance (Extends abstract Employee)
 */
public class ContractEmployee extends Employee {
    private double contractAmount;
    private int contractDurationMonths;
    private LocalDate contractEndDate;
    private double performanceBonus;

    public ContractEmployee(String employeeId, String name, String department, String email, LocalDate joiningDate,
                            double contractAmount, int contractDurationMonths, LocalDate contractEndDate, double performanceBonus) {
        super(employeeId, name, department, email, joiningDate);
        setContractAmount(contractAmount);
        setContractDurationMonths(contractDurationMonths);
        this.contractEndDate = contractEndDate;
        setPerformanceBonus(performanceBonus);
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        if (contractAmount < 0) {
            throw new InvalidEmployeeDataException("Contract amount cannot be negative.");
        }
        this.contractAmount = contractAmount;
    }

    public int getContractDurationMonths() {
        return contractDurationMonths;
    }

    public void setContractDurationMonths(int contractDurationMonths) {
        if (contractDurationMonths <= 0) {
            throw new InvalidEmployeeDataException("Contract duration must be strictly positive.");
        }
        this.contractDurationMonths = contractDurationMonths;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public double getPerformanceBonus() {
        return performanceBonus;
    }

    public void setPerformanceBonus(double performanceBonus) {
        if (performanceBonus < 0) {
            throw new InvalidEmployeeDataException("Performance bonus cannot be negative.");
        }
        this.performanceBonus = performanceBonus;
    }

    // Concept: Method Overriding
    @Override
    public double calculateSalary() {
        return (contractAmount / contractDurationMonths) + performanceBonus;
    }

    @Override
    public String getEmployeeType() {
        return "Contract";
    }

    @Override
    public double calculateTax() {
        // Flat 10% TDS
        return calculateSalary() * 0.10;
    }

    @Override
    public double calculateNetSalary() {
        return calculateSalary() - calculateTax();
    }
}
