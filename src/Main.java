import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

interface Employee {
    int getSalary();
    void setSalary(int salary);
    void getSalarySlip();
}

class FullTime implements Employee {
    int salary;
    Integer id;
    String name;

    FullTime(Integer id, String name, HashMap<Integer, FullTime> map) {
        this.name = name;
        map.put(id, this);
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override 
    public void getSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\" + name + "SalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }

    }

    @Override
    public String toString() {
        return "FullTime [salary=" + salary + ", id=" + id + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + salary;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FullTime other = (FullTime) obj;
        if (salary != other.salary)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}

class Contract implements Employee {
    int salary;
    String name;

    Contract(String name) {
        this.name = name;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override 
    public void getSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\" + name + "contractSalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + salary;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contract other = (Contract) obj;
        if (salary != other.salary)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Contract [salary=" + salary + ", name=" + name + "]";
    }

}

class PartTime implements Employee {
    int salary = 20000;
    String name;

    PartTime(String name) {
        this.name = name;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override 
    public void getSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\" + name + "PartimeSalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + salary;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartTime other = (PartTime) obj;
        if (salary != other.salary)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PartTime [salary=" + salary + ", name=" + name + "]";
    }

}

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, FullTime> map = new HashMap<>();

        // FULLTIME EMPLOYEES
        FullTime obj = new FullTime(101, "aditya", map);
        obj.setSalary(5000);
        System.out.println("Your salary is : " + obj.getSalary());
        obj.getSalarySlip();

        // PARTTIME EMPLOYEES
        PartTime pt1 = new PartTime("shiva");
        pt1.setSalary(2000);
        System.out.println("your salary is : " + pt1.getSalary());
        pt1.getSalarySlip();

        PartTime pt2 = new PartTime("SOUMIL");
        pt2.setSalary(2000);
        System.out.println("your salary is : " + pt2.getSalary());
        pt2.getSalarySlip();

        PartTime pt3 = new PartTime("PALAK ");
        pt3.setSalary(2000);
        System.out.println("your salary is : " + pt3.getSalary());
        pt3.getSalarySlip();

        List<PartTime> partTimeEmployees = new ArrayList<>(List.of(pt1, pt2, pt3));
        System.out.println("PartTime Employees: " + partTimeEmployees);

        // CONTRACT EMPLOYEES
        Contract obj4 = new Contract("aditya");
        obj4.setSalary(2000);
        System.out.println("your salary is : " + obj4.getSalary());
        obj4.getSalarySlip();

        System.out.println(map);

    }
}
