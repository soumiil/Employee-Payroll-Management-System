package payroll.util;

import payroll.model.Employee;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Reusable generic utilities for collections.
 */
public class GenericUtils {

    /**
     * Concept: Generic Method with Bounded Type
     * Filters a list based on a condition.
     */
    public static List<Employee> filter(List<? extends Employee> list, Predicate<Employee> condition) {
        return list.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    /**
     * Concept: Generic Method with Comparator
     * Finds the maximum element in a list based on a provided Comparator.
     * Documenting: Chosen the Comparator version over `<T extends Employee &
     * Comparable<T>>` because
     * inheriting multiple parametrizations of Comparable causes type erasure
     * conflicts in Java.
     */
    public static Employee max(List<? extends Employee> list, Comparator<Employee> comp) {
        return list.stream()
                .max(comp)
                .orElse(null);
    }

    /**
     * Concept: Generic Method with Map and List aggregation
     * Groups employees by their department.
     */
    public static Map<String, List<Employee>> groupByDepartment(List<? extends Employee> list) {
        Map<String, List<Employee>> map = new HashMap<>();
        for (Employee emp : list) {
            map.putIfAbsent(emp.getDepartment(), new ArrayList<>());
            map.get(emp.getDepartment()).add(emp);
        }
        return map;
    }
}
