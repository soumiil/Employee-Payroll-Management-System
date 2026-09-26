package payroll.exception;

/**
 * Thrown when an employee with a specific ID is not found in the repository.
 * Concept: Checked Exception
 */
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
