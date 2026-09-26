package payroll.exception;

/**
 * Thrown when trying to add an employee with an ID that already exists.
 * Concept: Checked Exception
 */
public class DuplicateEmployeeException extends Exception {
    public DuplicateEmployeeException(String message) {
        super(message);
    }
}
