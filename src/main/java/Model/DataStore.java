// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Model;

import java.util.List;

/**
 * Central in-memory store for all active and leaved students.
 *
 * Data is loaded from JSON files when the class is first used (app start),
 * and saved back to disk after every mutating operation via
 * {@link FileStorage}.  Data therefore survives application restarts.
 */
public class DataStore {

    // â”€â”€ Loaded from disk at class initialisation â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static List<StudentDetails>       students;
    public static List<LeavedStudentDetails> leavedStudents;

    /** Auto-increment counter â€“ loaded from disk so IDs stay unique */
    private static int idCounter;

    // â”€â”€ Public API â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /** Load all data from JSON files (called once on startup). */
    public static void load() {
        students       = FileStorage.loadStudents();
        leavedStudents = FileStorage.loadLeavedStudents();
        idCounter      = FileStorage.loadIdCounter();

        // Ensure the counter is always higher than any existing ID
        for (StudentDetails s : students) {
            try {
                int n = Integer.parseInt(s.getId());
                if (n >= idCounter) idCounter = n + 1;
            } catch (NumberFormatException ignored) {}
        }
        for (LeavedStudentDetails s : leavedStudents) {
            try {
                int n = Integer.parseInt(s.getId());
                if (n >= idCounter) idCounter = n + 1;
            } catch (NumberFormatException ignored) {}
        }
    }

    /** Persist both lists and the counter to disk. Call after every mutation. */
    public static void save() {
        FileStorage.saveStudents(students);
        FileStorage.saveLeavedStudents(leavedStudents);
        FileStorage.saveIdCounter(idCounter);
    }

    /** Returns the next unique ID string and increments the counter. */
    public static String nextId() {
        return String.valueOf(idCounter++);
    }

    // â”€â”€ PRN helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public static boolean prnExists(String prn) {
        for (StudentDetails s : students)
            if (s.getPrnNo().equalsIgnoreCase(prn.trim())) return true;
        return false;
    }

    public static boolean prnExistsExcluding(String prn, String excludeId) {
        for (StudentDetails s : students)
            if (s.getPrnNo().equalsIgnoreCase(prn.trim()) && !s.getId().equals(excludeId))
                return true;
        return false;
    }

    // â”€â”€ Finder helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    public static StudentDetails findByPrn(String prn) {
        for (StudentDetails s : students)
            if (s.getPrnNo().equalsIgnoreCase(prn.trim())) return s;
        return null;
    }

    public static StudentDetails findById(String id) {
        for (StudentDetails s : students)
            if (s.getId().equals(id)) return s;
        return null;
    }

    // â”€â”€ Mutators â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /** Removes an active student by id. Returns true if removed. */
    public static boolean removeById(String id) {
        return students.removeIf(s -> s.getId().equals(id));
    }
}
