package payroll.exception;

/**
 * Thrown when employee data is invalid (e.g., negative salary, blank name).
 * Concept: Unchecked Exception (extends RuntimeException)
 */
public class InvalidEmployeeDataException extends RuntimeException {
    public InvalidEmployeeDataException(String message) {
        super(message);
    }
}
