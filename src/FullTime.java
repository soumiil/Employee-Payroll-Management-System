import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FullTime implements Employee {
    int salary;
    Integer id;
    String name;

    FullTime(Integer id, String name, HashMap<Integer, FullTime> map) {
        this.id = id;
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
