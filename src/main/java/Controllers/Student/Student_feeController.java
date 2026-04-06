// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Student;

import Model.DataStore;
import Model.StudentDetails;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * Fee management controller (in-memory).
 * Looks up a student by PRN and updates their fees field.
 */
public class Student_feeController implements Initializable {

    @FXML private Button btn_back;
    @FXML private TextField studentID;   // re-used as PRN field in FXML
    @FXML private TextField studentFee;
    @FXML private Button submit;
    @FXML private ComboBox<String> month;
    @FXML private ComboBox<String> year;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        month.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"));
        year.setItems(FXCollections.observableArrayList(
                "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));
    }

    @FXML
    private void back_btn_clicked(MouseEvent event) throws IOException {
        btn_back.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Student/Student_Menu.fxml"));
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void submitButtonAction(MouseEvent event) {
        String prn    = studentID.getText().trim();
        String feeStr = studentFee.getText().trim();

        if (prn.isEmpty() || feeStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "PRN No and Fee Amount are required!");
            return;
        }
        if (month.getValue() == null || year.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Please select Month and Year!");
            return;
        }

        StudentDetails student = DataStore.findByPrn(prn);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "No student found with PRN: " + prn);
            return;
        }

        // Validate fee is numeric
        try {
            Double.parseDouble(feeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Fee amount must be a valid number!");
            return;
        }

        student.setFees(feeStr);
        DataStore.save();                              // â† persist to JSON

        JOptionPane.showMessageDialog(null,
                "Fee of â‚¹" + feeStr + " recorded for " + student.getName()
                + " (" + month.getValue() + " " + year.getValue() + ") â€” Saved!");
        clearForm();
    }

    private void clearForm() {
        studentID.clear();
        studentFee.clear();
        month.setValue(null);
        year.setValue(null);
    }
}
