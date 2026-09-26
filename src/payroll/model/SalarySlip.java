package payroll.model;

import java.time.LocalDate;

public class SalarySlip {
    private String slipId;
    private String employeeId;
    private String name;
    private String type;
    private String monthYear;
    private double grossSalary;
    private double tax;
    private double otherDeductions;
    private double netSalary;
    private LocalDate generatedDate;
    
    // Additional fields for displaying specific type information
    private Employee employeeObj; // used for custom formatting

    public SalarySlip(String slipId, String monthYear, Employee employee) {
        this.slipId = slipId;
        this.employeeId = employee.getEmployeeId();
        this.name = employee.getName();
        this.type = employee.getEmployeeType();
        this.monthYear = monthYear;
        this.grossSalary = employee.calculateSalary();
        this.tax = employee.calculateTax();
        this.netSalary = employee.calculateNetSalary();
        this.generatedDate = LocalDate.now();
        this.employeeObj = employee;
        
        this.otherDeductions = grossSalary - netSalary - tax; 
        // to handle PF for FullTime without needing instanceof strictly everywhere, though for format we might need it.
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                  SALARY SLIP\n");
        sb.append("==================================================\n");
        sb.append(String.format("Slip No      : %s\n", slipId));
        sb.append(String.format("Month / Year : %s\n", monthYear));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Employee ID  : %s\n", employeeId));
        sb.append(String.format("Name         : %s\n", name));
        sb.append(String.format("Type         : %s\n", type));
        sb.append(String.format("Department   : %s\n", employeeObj.getDepartment()));
        sb.append("--------------------------------------------------\n");
        sb.append("EARNINGS\n");
        
        // Output type specific earnings
        if (employeeObj instanceof FullTimeEmployee) {
            FullTimeEmployee ft = (FullTimeEmployee) employeeObj;
            sb.append(String.format("  Basic Salary          : %,11.2f\n", ft.getBasicSalary()));
            sb.append(String.format("  HRA                   : %,11.2f\n", ft.getHra()));
            sb.append(String.format("  DA                    : %,11.2f\n", ft.getDa()));
            sb.append(String.format("  Bonus                 : %,11.2f\n", ft.getBonus()));
        } else if (employeeObj instanceof PartTimeEmployee) {
            PartTimeEmployee pt = (PartTimeEmployee) employeeObj;
            sb.append(String.format("  Hourly Rate           : %,11.2f\n", pt.getHourlyRate()));
            sb.append(String.format("  Hours Worked          : %11d\n", pt.getHoursWorked()));
        } else if (employeeObj instanceof ContractEmployee) {
            ContractEmployee ce = (ContractEmployee) employeeObj;
            sb.append(String.format("  Monthly Base          : %,11.2f\n", ce.getContractAmount() / ce.getContractDurationMonths()));
            sb.append(String.format("  Performance Bonus     : %,11.2f\n", ce.getPerformanceBonus()));
        }
        
        sb.append(String.format("  Gross Salary          : %,11.2f\n", grossSalary));
        sb.append("--------------------------------------------------\n");
        sb.append("DEDUCTIONS\n");
        
        if (employeeObj instanceof FullTimeEmployee) {
            FullTimeEmployee ft = (FullTimeEmployee) employeeObj;
            sb.append(String.format("  Provident Fund        : %,11.2f\n", ft.getPfDeduction()));
        }
        sb.append(String.format("  Income Tax            : %,11.2f\n", tax));
        sb.append(String.format("  Total Deductions      : %,11.2f\n", (grossSalary - netSalary)));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("NET SALARY              : %,11.2f\n", netSalary));
        sb.append("==================================================\n");
        sb.append(String.format("Generated on: %s\n", generatedDate.toString()));
        return sb.toString();
    }
    
    // Getters for file writing operations if needed
    public String getSlipId() { return slipId; }
    public String getEmployeeId() { return employeeId; }
}
