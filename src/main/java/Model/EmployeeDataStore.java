// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Model;

import java.util.List;

/**
 * In-memory data store for employees. Mirrors DataStore but for the employee section.
 */
public class EmployeeDataStore {

    public static List<EmployeeDetails>       employees;
    public static List<LeavedEmployeeDetails> leavedEmployees;

    private static int idCounter = 1;

    public static String nextId() { return String.valueOf(idCounter++); }

    public static boolean prnExists(String prn) {
        for (EmployeeDetails e : employees)
            if (e.getPrnNo().equalsIgnoreCase(prn.trim())) return true;
        return false;
    }

    public static boolean prnExistsExcluding(String prn, String excludeId) {
        for (EmployeeDetails e : employees)
            if (e.getPrnNo().equalsIgnoreCase(prn.trim()) && !e.getId().equals(excludeId)) return true;
        return false;
    }

    public static EmployeeDetails findById(String id) {
        for (EmployeeDetails e : employees)
            if (e.getId().equals(id)) return e;
        return null;
    }

    public static boolean removeById(String id) {
        return employees.removeIf(e -> e.getId().equals(id));
    }
}
