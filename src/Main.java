import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

abstract class employees {
    abstract int generateSalary();
}

class Fulltime extends employees {
    int salary = 50000;
    Integer id;
    String name;

    Fulltime(Integer id, String name, HashMap<Integer, Fulltime> map) {

        this.name = name;
        map.put(id, this);

    }

    @Override
    int generateSalary() {
        return salary;
    }

    void generateSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\$(name)SalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }

    }

    @Override
    public String toString() {
        return "Fulltime [salary=" + salary + ", id=" + id + ", name=" + name + "]";
    }
}

class Contract extends employees {
    int salary = 30000;
    String name;

    Contract(String name) {
        this.name = name;
    }

    @Override
    int generateSalary() {

        return salary;
    }

    void generateSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\$(name)contractSalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }
    }
}

class Parttime extends employees {
    int salary = 20000;
    String name;

    Parttime(String name) {

        this.name = name;

    }

    @Override
    int generateSalary() {
        return salary;
    }

    void generateSalarySlip() {
        try {
            FileWriter fw = new FileWriter("D:\\PartimeSalarySlip.txt");
            fw.write("Your Salary : " + salary);
            fw.close();
            System.out.println("salary slip generated");
        } catch (IOException e) {
            System.out.println(e);

        }
    }
}

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Fulltime> map = new HashMap<>();
        Fulltime obj = new Fulltime(101, "aditya", map);
        System.out.println("Your salary is : " + obj.generateSalary());
        obj.generateSalarySlip();

        Parttime obj3 = new Parttime("shiva");
        System.out.println("your salary is : " + obj3.generateSalary());
        obj3.generateSalarySlip();
        Contract obj4 = new Contract("aditya");
        System.out.println("your salary is : " + obj4.generateSalary());
        obj4.generateSalarySlip();
        Parttime obj5 = new Parttime("aditya");
        System.out.println("your salary is : " + obj5.generateSalary());
        obj5.generateSalarySlip();

        System.out.println(map);

    }
}
