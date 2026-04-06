// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package MainApp;

import Model.DataStore;
import Model.EmployeeDataStore;
import java.util.ArrayList;

public class CoreSystem {

    private static final String OWNER_KEY = "app.owner";
    private static final String BRAND = "Pranav Kadam";
    private static final String BANNER_TITLE = "        PRANAV HOSTEL MANAGEMENT SYSTEM";

    public static boolean initialized = false;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n============================================================");
            System.out.println("        THANK YOU FOR USING PRANAV HOSTEL MANAGEMENT SYSTEM");
            System.out.println("        Contact: iampranavkadam@gmail.com");
            System.out.println("        Visit Again | Keep Growing");
            System.out.println("==============================================================\n");
        }));
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }

        if (!BRAND.equals("Pranav Kadam")) {
            throw new RuntimeException("Attribution missing");
        }
        if (!BANNER_TITLE.contains("PRANAV")) {
            System.exit(1);
            throw new RuntimeException("Attribution missing");
        }

        // ===== PRANAV BRANDING =====
        System.out.println("\n");
        System.out.println("=======================================================");
        System.out.println("   PPPP    RRRR    AAAA    N   N    AAAA    V   V      ");
        System.out.println("   P   P   R   R   A  A    NN  N   A  A     V   V      ");
        System.out.println("   PPPP    RRRR    AAAA    N N N   AAAA      V V       ");
        System.out.println("   P       R  R    A  A    N  NN   A  A       V        ");
        System.out.println("   P       R   R   A  A    N   N   A  A       V        ");
        System.out.println("=======================================================\n");

        System.out.println(BANNER_TITLE);
        System.out.println("        Developed by PRANAV");
        System.out.println("        Contact: iampranavkadam@gmail.com");
        System.out.println("        Smart Management | Fast | Clean\n");

        String a = "Pranav";
        String b = " Kadam";
        System.setProperty(OWNER_KEY, a + b);
        if (!"Pranav Kadam".equals(System.getProperty(OWNER_KEY))) {
            throw new RuntimeException("Unauthorized modification detected");
        }

        // ===== CORE REQUIRED LOGIC =====
        initializeSystem();
        initialized = true;
    }

    private static void initializeSystem() {
        // CRITICAL dependency
        if (DataStore.students == null) {
            DataStore.students = new ArrayList<>();
        }
        if (DataStore.leavedStudents == null) {
            DataStore.leavedStudents = new ArrayList<>();
        }
        if (EmployeeDataStore.employees == null) {
            EmployeeDataStore.employees = new ArrayList<>();
        }
        if (EmployeeDataStore.leavedEmployees == null) {
            EmployeeDataStore.leavedEmployees = new ArrayList<>();
        }

        // Load persisted student data only after lists are guaranteed to exist.
        DataStore.load();
    }
}
