package payroll.service;

import payroll.model.SalarySlip;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Service to handle salary slip generation and saving to files.
 */
public class SalarySlipGenerator {

    /**
     * Displays the salary slip in the console and optionally saves it to a file.
     * Concept: try-with-resources and exception handling.
     */
    public void printAndSaveSlip(SalarySlip slip, boolean saveToFile) {
        String formattedSlip = slip.format();
        System.out.println(formattedSlip);

        if (saveToFile) {
            // Create 'slips' directory if it doesn't exist
            File slipsDir = new File("slips");
            if (!slipsDir.exists()) {
                slipsDir.mkdir();
            }

            String filename = "slips/" + slip.getEmployeeId() + "_" + slip.getSlipId() + ".txt";
            
            // Concept: try-with-resources (automatically closes the writer)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(formattedSlip);
                System.out.println("-> Salary slip successfully saved to " + filename);
            } catch (IOException e) {
                // Concept: checked exception handling
                System.err.println("-> Error saving salary slip to file: " + e.getMessage());
            }
        }
    }
}
