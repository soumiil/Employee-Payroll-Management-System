package payroll.util;

import payroll.model.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
    public static <T extends Employee> List<T> filter(List<T> list, Predicate<T> condition) {
        /* updation by Soumil Agarwal - employee filtering using a stream */
        List<T> result = list.stream().filter(condition).collect(Collectors.toList());
        return result;
    }

    /**
     * Concept: Generic Method with Comparator
     * Finds the maximum element in a list based on a provided Comparator.
     * Documenting: Chosen the Comparator version over `<T extends Employee & Comparable<T>>` because 
     * inheriting multiple parametrizations of Comparable causes type erasure conflicts in Java.
     */
    public static <T extends Employee> T max(List<T> list, Comparator<T> comp) {
        /* updation by Soumil Agarwal - employee filtering using a stream */
        Optional<T> maxItem = list.stream().max(comp);
        return maxItem.orElse(null);
    }

    /**
     * Concept: Generic Method with multiple type parameters
     * Maps elements of type T to type R.
     */
    public static <T, R> List<R> mapAll(List<T> list, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(mapper.apply(item));
        }
        return result;
    }

    /**
     * Concept: Generic Method with Map and List aggregation
     * Groups employees by their department.
     */
    public static <T extends Employee> Map<String, List<T>> groupByDepartment(List<T> list) {
        Map<String, List<T>> map = new HashMap<>();
        for (T emp : list) {
            map.putIfAbsent(emp.getDepartment(), new ArrayList<>());
            map.get(emp.getDepartment()).add(emp);
        }
        return map;
    }
}
