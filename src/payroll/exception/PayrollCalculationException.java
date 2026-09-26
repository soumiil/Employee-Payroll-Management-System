package payroll.exception;

/**
 * Thrown when there is an error during payroll calculation (e.g., invalid parameters or math errors).
 * Concept: Checked Exception
 */
public class PayrollCalculationException extends Exception {
    public PayrollCalculationException(String message) {
        super(message);
    }
    
    // Concept: Exception chaining support
    public PayrollCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
