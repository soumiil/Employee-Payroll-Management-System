package payroll.repository;

import payroll.exception.DuplicateEmployeeException;
import payroll.exception.EmployeeNotFoundException;
import payroll.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Concept: Generics and Bounded Types (<T extends Employee>)
 * Repository backed by a HashMap.
 */
public class EmployeeRepository<T extends Employee> {
    
    // Concept: HashMap used for O(1) lookups
    private final Map<String, T> store = new HashMap<>();

    public void add(T employee) throws DuplicateEmployeeException {
        if (store.containsKey(employee.getEmployeeId())) {
            throw new DuplicateEmployeeException("Employee with ID " + employee.getEmployeeId() + " already exists.");
        }
        store.put(employee.getEmployeeId(), employee);
    }

    public T findById(String id) throws EmployeeNotFoundException {
        T employee = store.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return employee;
    }

    public void update(T employee) throws EmployeeNotFoundException {
        if (!store.containsKey(employee.getEmployeeId())) {
            throw new EmployeeNotFoundException("Cannot update. Employee with ID " + employee.getEmployeeId() + " not found.");
        }
        store.put(employee.getEmployeeId(), employee);
    }

    public void remove(String id) throws EmployeeNotFoundException {
        if (!store.containsKey(id)) {
            throw new EmployeeNotFoundException("Cannot delete. Employee with ID " + id + " not found.");
        }
        store.remove(id);
    }

    // Concept: ArrayList for returning collections
    public ArrayList<T> getAll() {
        return new ArrayList<>(store.values());
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }

    public int count() {
        return store.size();
    }
}
