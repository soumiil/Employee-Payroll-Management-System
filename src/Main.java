import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        // // FULLTIME EMPLOYEES
        // FullTime obj = new FullTime(101, "aditya", map);
        // obj.setSalary(5000);
        // System.out.println("Your salary is : " + obj.getSalary());
        // obj.getSalarySlip();
        
        // // PARTTIME EMPLOYEES
        // PartTime pt1 = new PartTime("shiva", 2000);
        // pt1.setSalary(2000);
        // System.out.println("your salary is : " + pt1.getSalary());
        // pt1.getSalarySlip();
        
        // PartTime pt2 = new PartTime("SOUMIL", 1000);
        // pt2.setSalary(2000);
        // System.out.println("your salary is : " + pt2.getSalary());
        // pt2.getSalarySlip();
        
        // PartTime pt3 = new PartTime("PALAK", 100);
        // pt3.setSalary(2000);
        // System.out.println("your salary is : " + pt3.getSalary());
        // pt3.getSalarySlip();
        
        // List<PartTime> partTimeEmployees = new ArrayList<>(List.of(pt1, pt2, pt3));
        // System.out.println("PartTime Employees: " + partTimeEmployees);
        
        // // CONTRACT EMPLOYEES
        // Contract obj4 = new Contract("aditya", 100);
        // obj4.setSalary(2000);
        // System.out.println("your salary is : " + obj4.getSalary());
        // obj4.getSalarySlip();
        
        // System.out.println(map);
        
        HashMap<Integer, FullTime> map = new HashMap<>();
        List<FullTime> ftEmployees = new ArrayList<>();
        List<PartTime> ptEmployees = new ArrayList<>();
        List<Contract> ctEmployees = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print(
                    "Create an Employee\n1 - FullTime Employee\n2 - PartTime Employee\n3 - Contract Employee\n4 - Exit\nEnter your choice: ");
            int n = sc.nextInt();
            sc.nextLine();
            int id;
            String name;

            switch (n) {
                case 1:
                    System.out.print("\nEnter Id: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\nEnter name: ");
                    name = sc.nextLine();
                    Employee e = new FullTime(id, name, map);
                    ftEmployees.add((FullTime) e);
                    System.out.println(ftEmployees);
                    break;
                case 2:
                    System.out.print("\nEnter Id: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\nEnter name: ");
                    name = sc.nextLine();
                    e = new PartTime(name, id);
                    ptEmployees.add((PartTime) e);
                    System.out.println(ptEmployees);
                    break;
                case 3:
                    System.out.print("\nEnter Id: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\nEnter name: ");
                    name = sc.nextLine();
                    e = new Contract(name, id);
                    ctEmployees.add((Contract) e);
                    System.out.println(ctEmployees);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Enter a valid choice.");
                    break;
            }
        }
    }
}
