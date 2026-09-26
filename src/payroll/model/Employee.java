package payroll.model;

import payroll.exception.InvalidEmployeeDataException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Base representation of an Employee.
 * Concept: Abstract Class
 */
public abstract class Employee implements Comparable<Employee> {
    private String employeeId;
    private String name;
    private String department;
    private String email;
    private LocalDate joiningDate;

    public Employee(String employeeId, String name, String department, String email, LocalDate joiningDate) {
        setEmployeeId(employeeId);
        setName(name);
        setDepartment(department);
        setEmail(email);
        setJoiningDate(joiningDate);
    }

    // Concept: Abstract methods to be implemented by subclasses
    public abstract double calculateSalary();
    public abstract String getEmployeeType();

    /**
     * Concrete method shared by all types. Can be overridden.
     * Default tax calculation (can be customized by subclasses).
     */
    public double calculateTax() {
        return 0; // Default implementation, overridden in subclasses if logic differs
    }

    /**
     * Calculate net salary (gross - deductions).
     */
    public double calculateNetSalary() {
        return calculateSalary() - calculateTax(); // Needs refinement based on specific types
    }

    // Getters and Setters with validation (Concept: Exception handling / Input validation)
    
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Employee ID cannot be blank.");
        }
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Name cannot be blank.");
        }
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Department cannot be blank.");
        }
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmployeeDataException("Invalid email format.");
        }
        this.email = email;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        if (joiningDate != null && joiningDate.isAfter(LocalDate.now())) {
            throw new InvalidEmployeeDataException("Joining date cannot be in the future.");
        }
        this.joiningDate = joiningDate;
    }

    // Concept: Method Overriding (equals and hashCode based on ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    // Concept: Method Overriding (toString for clean summary)
    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) | Net Salary: %.2f", 
                employeeId, name, department, getEmployeeType(), calculateNetSalary());
    }

    // Concept: Comparable implementation based on net salary (for GenericUtils max)
    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.calculateNetSalary(), other.calculateNetSalary());
    }
}
