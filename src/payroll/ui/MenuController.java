package payroll.ui;

import payroll.exception.DuplicateEmployeeException;
import payroll.exception.EmployeeNotFoundException;
import payroll.exception.InvalidEmployeeDataException;
import payroll.exception.PayrollCalculationException;
import payroll.model.ContractEmployee;
import payroll.model.Employee;
import payroll.model.FullTimeEmployee;
import payroll.model.PartTimeEmployee;
import payroll.model.SalarySlip;
import payroll.repository.EmployeeRepository;
import payroll.service.PayrollProcessor;
import payroll.service.SalarySlipGenerator;
import payroll.util.GenericUtils;
import payroll.util.InputValidator;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuController {
    // Concept: Generic repository
    private final EmployeeRepository<Employee> repository = new EmployeeRepository<>();
    private final PayrollProcessor processor = new PayrollProcessor();
    private final SalarySlipGenerator slipGenerator = new SalarySlipGenerator();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        preloadData();
        boolean exit = false;

        while (!exit) {
            printMenu();
            int choice = InputValidator.readInt(scanner, "Enter your choice: ");

            // Concept: multi-catch and exception handling used throughout the flow to ensure no crashes
            try {
                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: viewAllEmployees(); break;
                    case 3: searchEmployee(); break;
                    case 4: updateEmployee(); break;
                    case 5: deleteEmployee(); break;
                    case 6: calculateSalary(); break;
                    case 7: generateSlip(); break;
                    case 8: runPayroll(); break;
                    case 9: showReports(); break;
                    case 10: 
                        exit = true; 
                        System.out.println("Exiting System. Goodbye!"); 
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                }
            } catch (InvalidEmployeeDataException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (Exception e) {
                // Catch-all to prevent crashes
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Employee Payroll Management System ---");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Calculate Salary for an Employee");
        System.out.println("7. Generate Salary Slip for an Employee");
        System.out.println("8. Run Payroll for All Employees");
        System.out.println("9. Reports");
        System.out.println("10. Exit");
    }

    private void addEmployee() {
        System.out.println("\n[ Add Employee ]");
        System.out.println("Select Type: 1. Full-Time  2. Part-Time  3. Contract");
        int type = InputValidator.readInt(scanner, "Choice: ");

        String id = InputValidator.readString(scanner, "Employee ID (e.g. EMP001): ");
        if (repository.exists(id)) {
            System.out.println("Error: Employee ID already exists.");
            return;
        }

        String name = InputValidator.readString(scanner, "Name: ");
        String dept = InputValidator.readString(scanner, "Department: ");
        String email = InputValidator.readString(scanner, "Email: ");
        LocalDate joinDate = InputValidator.readDate(scanner, "Joining Date");

        Employee newEmp = null;
        if (type == 1) {
            double basic = InputValidator.readDouble(scanner, "Basic Salary: ");
            double hra = InputValidator.readDouble(scanner, "HRA: ");
            double da = InputValidator.readDouble(scanner, "DA: ");
            double bonus = InputValidator.readDouble(scanner, "Bonus: ");
            double pf = InputValidator.readDouble(scanner, "PF Deduction: ");
            newEmp = new FullTimeEmployee(id, name, dept, email, joinDate, basic, hra, da, bonus, pf);
        } else if (type == 2) {
            double rate = InputValidator.readDouble(scanner, "Hourly Rate: ");
            int hours = InputValidator.readInt(scanner, "Hours Worked: ");
            newEmp = new PartTimeEmployee(id, name, dept, email, joinDate, rate, hours);
        } else if (type == 3) {
            double contractAmount = InputValidator.readDouble(scanner, "Contract Amount: ");
            int duration = InputValidator.readInt(scanner, "Duration (months): ");
            LocalDate endDate = InputValidator.readDate(scanner, "End Date");
            double perfBonus = InputValidator.readDouble(scanner, "Performance Bonus: ");
            newEmp = new ContractEmployee(id, name, dept, email, joinDate, contractAmount, duration, endDate, perfBonus);
        } else {
            System.out.println("Invalid type selected.");
            return;
        }

        try {
            repository.add(newEmp);
            System.out.println("Employee added successfully!");
        } catch (DuplicateEmployeeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllEmployees() {
        System.out.println("\n[ All Employees ]");
        List<Employee> all = repository.getAll();
        if (all.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee e : all) {
            System.out.println(e);
        }
    }

    private void searchEmployee() {
        String id = InputValidator.readString(scanner, "Enter Employee ID to search: ");
        try {
            Employee emp = repository.findById(id);
            System.out.println("Found: " + emp);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        String id = InputValidator.readString(scanner, "Enter Employee ID to update: ");
        try {
            Employee emp = repository.findById(id);
            System.out.println("Current details: " + emp);
            System.out.println("Enter new details (or just press enter to keep old name/dept - simplification for this project, here we require all inputs again for the selected type's specific fields):");
            System.out.println("Note: Please use 'Delete' then 'Add' if employee type changed. Here we update basic info.");
            String name = InputValidator.readString(scanner, "New Name: ");
            String dept = InputValidator.readString(scanner, "New Department: ");
            emp.setName(name);
            emp.setDepartment(dept);
            repository.update(emp);
            System.out.println("Employee updated successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        String id = InputValidator.readString(scanner, "Enter Employee ID to delete: ");
        try {
            repository.remove(id);
            System.out.println("Employee deleted successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void calculateSalary() {
        String id = InputValidator.readString(scanner, "Enter Employee ID: ");
        try {
            Employee emp = repository.findById(id);
            System.out.println("\n[ Salary Details for " + emp.getName() + " ]");
            // Concept: Polymorphism (calculateSalary/Tax/NetSalary method dynamically dispatched based on object type)
            System.out.println("Type        : " + emp.getEmployeeType());
            System.out.println("Gross Salary: " + emp.calculateSalary());
            System.out.println("Tax         : " + emp.calculateTax());
            System.out.println("Net Salary  : " + emp.calculateNetSalary());
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generateSlip() {
        String id = InputValidator.readString(scanner, "Enter Employee ID: ");
        try {
            Employee emp = repository.findById(id);
            Month currentMonth = LocalDate.now().getMonth();
            int currentYear = LocalDate.now().getYear();
            
            SalarySlip slip = new SalarySlip("SLP-" + currentYear + "-" + emp.getEmployeeId(), currentMonth.toString() + " " + currentYear, emp);
            
            System.out.print("Save to file? (y/n): ");
            String save = scanner.nextLine().trim().toLowerCase();
            boolean saveToFile = save.equals("y") || save.equals("yes");
            
            slipGenerator.printAndSaveSlip(slip, saveToFile);
            
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void runPayroll() {
        System.out.println("\n[ Running Global Payroll ]");
        List<Employee> all = repository.getAll();
        if (all.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }

        try {
            // Polymorphism check block
            System.out.println("--- Polymorphism Check (Runtime Dispatch) ---");
            for (Employee e : all) {
                // Concept: Polymorphism in action - `e` is of type Employee, but the correct `calculateSalary` is called.
                System.out.println(e.getEmployeeType() + " -> " + String.format("%.2f", e.calculateSalary())); 
            }
            System.out.println("---------------------------------------------");

            // Wildcard method demonstration
            double total = processor.calculateTotalPayrollWildcard(all);
            System.out.printf("Total Payroll for %d employees: ₹ %,.2f\n", all.size(), total);

            System.out.print("Generate slips for everyone? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) {
                Month currentMonth = LocalDate.now().getMonth();
                int currentYear = LocalDate.now().getYear();
                List<SalarySlip> slips = processor.generateSlips(all, currentMonth, currentYear);
                for (SalarySlip slip : slips) {
                    slipGenerator.printAndSaveSlip(slip, true);
                }
            }

        } catch (PayrollCalculationException e) {
            System.out.println("Payroll Error: " + e.getMessage());
        }
    }

    private void showReports() {
        System.out.println("\n[ Reports ]");
        List<Employee> all = repository.getAll();
        
        System.out.println("a. Employees by type");
        System.out.println("b. Department-wise payroll");
        System.out.println("c. Highest paid employee");
        String choice = InputValidator.readString(scanner, "Choose report: ").toLowerCase();

        switch (choice) {
            case "a":
                System.out.println("- Full-Time:");
                // Concept: Generic filter method
                GenericUtils.filter(all, e -> e instanceof FullTimeEmployee).forEach(e -> System.out.println("  " + e));
                System.out.println("- Part-Time:");
                GenericUtils.filter(all, e -> e instanceof PartTimeEmployee).forEach(e -> System.out.println("  " + e));
                System.out.println("- Contract:");
                GenericUtils.filter(all, e -> e instanceof ContractEmployee).forEach(e -> System.out.println("  " + e));
                break;
            case "b":
                // Concept: Generic grouping method
                Map<String, List<Employee>> deptMap = GenericUtils.groupByDepartment(all);
                for (Map.Entry<String, List<Employee>> entry : deptMap.entrySet()) {
                    try {
                        double deptTotal = processor.calculateTotalPayrollWildcard(entry.getValue());
                        System.out.printf("Department: %s | Total Payroll: %,.2f | Employees: %d\n", entry.getKey(), deptTotal, entry.getValue().size());
                    } catch (PayrollCalculationException e) {
                        System.out.println("Error calculating for dept: " + entry.getKey());
                    }
                }
                break;
            case "c":
                // Concept: Generic max method with Comparator
                Employee highest = GenericUtils.max(all, (e1, e2) -> Double.compare(e1.calculateNetSalary(), e2.calculateNetSalary()));
                if (highest != null) {
                    System.out.println("Highest Paid Employee: " + highest.getName() + " (" + highest.getEmployeeType() + ") Net Salary: " + highest.calculateNetSalary());
                } else {
                    System.out.println("No employees found.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void preloadData() {
        try {
            repository.add(new FullTimeEmployee("EMP001", "Priya Sharma", "Engineering", "priya@example.com", LocalDate.of(2022, 1, 10), 40000, 8000, 4000, 3000, 4800));
            repository.add(new FullTimeEmployee("EMP002", "Rahul Gupta", "HR", "rahul@example.com", LocalDate.of(2021, 5, 15), 35000, 7000, 3500, 2000, 4200));
            repository.add(new PartTimeEmployee("EMP003", "Anil Kumar", "Support", "anil@example.com", LocalDate.of(2023, 2, 20), 200, 100)); // 20k
            repository.add(new PartTimeEmployee("EMP004", "Sunita Verma", "Support", "sunita@example.com", LocalDate.of(2023, 3, 1), 250, 130)); // 32500 + overtime
            repository.add(new ContractEmployee("EMP005", "Vikram Singh", "Marketing", "vikram@example.com", LocalDate.of(2023, 1, 1), 600000, 12, LocalDate.of(2023, 12, 31), 5000)); // 50k + 5k = 55k
            repository.add(new ContractEmployee("EMP006", "Meera Desai", "Engineering", "meera@example.com", LocalDate.of(2023, 6, 1), 300000, 6, LocalDate.of(2023, 12, 1), 0)); // 50k
            System.out.println("-> Loaded 6 sample employees.");
        } catch (DuplicateEmployeeException e) {
            System.out.println("Failed to preload data.");
        }
    }
}
