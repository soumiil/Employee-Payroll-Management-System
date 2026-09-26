import java.io.FileWriter;
import java.io.IOException;

public class PartTime implements Employee {
    int salary;
    String name;
    int id;

    PartTime(String name, int id) {
        this.name = name;
        this.id = id;
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
