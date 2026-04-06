// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles JSON file persistence for students and leaved students.
 *
 * Files are stored in the working directory (project root when run from IDE,
 * or the folder containing the JAR when run standalone).
 *
 * No database, no external server â€“ pure local file storage.
 */
public class FileStorage {

    // â”€â”€ File paths â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private static final String STUDENTS_FILE       = "students.json";
    private static final String LEAVED_FILE         = "leaved_students.json";
    private static final String ID_COUNTER_FILE     = "id_counter.json";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  PLAIN-JAVA DTO (used only for JSON â€“ avoids JavaFX property issues)
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /** Plain data object mirroring StudentDetails â€“ safe for Gson serialization */
    public static class StudentDTO {
        String id, name, prnNo, email, phoneNumber, address,
               guardName, guardTel, hostelBlock, fees;
    }

    /** Plain data object mirroring LeavedStudentDetails */
    public static class LeavedStudentDTO {
        String id, name, prnNo, email, phoneNumber, address,
               guardName, guardTel, hostelBlock, date;
    }

    /** Counter file DTO */
    private static class CounterDTO { int value; }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  SAVE
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /** Serialises the active student list to students.json */
    public static void saveStudents(List<StudentDetails> students) {
        List<StudentDTO> dtos = new ArrayList<>();
        for (StudentDetails s : students) {
            StudentDTO dto = new StudentDTO();
            dto.id          = s.getId();
            dto.name        = s.getName();
            dto.prnNo       = s.getPrnNo();
            dto.email       = s.getEmail();
            dto.phoneNumber = s.getPhoneNumber();
            dto.address     = s.getAddress();
            dto.guardName   = s.getGuardName();
            dto.guardTel    = s.getGuardTel();
            dto.hostelBlock = s.getHostelBlock();
            dto.fees        = s.getFees();
            dtos.add(dto);
        }
        writeJson(STUDENTS_FILE, dtos);
    }

    /** Serialises the leaved student list to leaved_students.json */
    public static void saveLeavedStudents(List<LeavedStudentDetails> leaved) {
        List<LeavedStudentDTO> dtos = new ArrayList<>();
        for (LeavedStudentDetails s : leaved) {
            LeavedStudentDTO dto = new LeavedStudentDTO();
            dto.id          = s.getId();
            dto.name        = s.getName();
            dto.prnNo       = s.getPrnNo();
            dto.email       = s.getEmail();
            dto.phoneNumber = s.getPhoneNumber();
            dto.address     = s.getAddress();
            dto.guardName   = s.getGuardName();
            dto.guardTel    = s.getGuardTel();
            dto.hostelBlock = s.getHostelBlock();
            dto.date        = s.getDate();
            dtos.add(dto);
        }
        writeJson(LEAVED_FILE, dtos);
    }

    /** Saves the current ID counter so IDs stay unique across restarts */
    public static void saveIdCounter(int counter) {
        CounterDTO dto = new CounterDTO();
        dto.value = counter;
        writeJson(ID_COUNTER_FILE, dto);
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  LOAD
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    /** Loads active students from students.json; returns empty list if missing/corrupt */
    public static List<StudentDetails> loadStudents() {
        String json = readJson(STUDENTS_FILE);
        if (json == null) return new ArrayList<>();
        try {
            Type type = new TypeToken<List<StudentDTO>>(){}.getType();
            List<StudentDTO> dtos = GSON.fromJson(json, type);
            if (dtos == null) return new ArrayList<>();
            List<StudentDetails> result = new ArrayList<>();
            for (StudentDTO dto : dtos) {
                result.add(new StudentDetails(
                        safe(dto.id), safe(dto.name), safe(dto.prnNo),
                        safe(dto.email), safe(dto.phoneNumber), safe(dto.address),
                        safe(dto.guardName), safe(dto.guardTel),
                        safe(dto.hostelBlock), safe(dto.fees)));
            }
            return result;
        } catch (Exception e) {
            System.err.println("[FileStorage] Failed to parse students.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Loads leaved students from leaved_students.json; returns empty list if missing/corrupt */
    public static List<LeavedStudentDetails> loadLeavedStudents() {
        String json = readJson(LEAVED_FILE);
        if (json == null) return new ArrayList<>();
        try {
            Type type = new TypeToken<List<LeavedStudentDTO>>(){}.getType();
            List<LeavedStudentDTO> dtos = GSON.fromJson(json, type);
            if (dtos == null) return new ArrayList<>();
            List<LeavedStudentDetails> result = new ArrayList<>();
            for (LeavedStudentDTO dto : dtos) {
                result.add(new LeavedStudentDetails(
                        safe(dto.id), safe(dto.name), safe(dto.prnNo),
                        safe(dto.email), safe(dto.phoneNumber), safe(dto.address),
                        safe(dto.guardName), safe(dto.guardTel),
                        safe(dto.hostelBlock), safe(dto.date)));
            }
            return result;
        } catch (Exception e) {
            System.err.println("[FileStorage] Failed to parse leaved_students.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Loads the saved ID counter (defaults to 1 if file missing) */
    public static int loadIdCounter() {
        String json = readJson(ID_COUNTER_FILE);
        if (json == null) return 1;
        try {
            CounterDTO dto = GSON.fromJson(json, CounterDTO.class);
            return (dto != null && dto.value > 0) ? dto.value : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    //  HELPERS
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

    private static void writeJson(String filename, Object obj) {
        try {
            Path path = Paths.get(filename);
            Files.writeString(path, GSON.toJson(obj), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("[FileStorage] Could not write " + filename + ": " + e.getMessage());
        }
    }

    private static String readJson(String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return null;
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("[FileStorage] Could not read " + filename + ": " + e.getMessage());
            return null;
        }
    }

    /** Returns the value, or empty string if null */
    private static String safe(String v) { return v != null ? v : ""; }
}
