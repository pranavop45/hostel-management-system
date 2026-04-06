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
 * Controller for New Student Registration (in-memory).
 */
public class New_StudentController implements Initializable {

    @FXML private TextField reg_txt_username;
    @FXML private TextField reg_txt_prn_no;
    @FXML private TextField reg_txt_email;
    @FXML private TextField reg_txt_phnmb;
    @FXML private TextField reg_txt_address;
    @FXML private TextField reg_txt_guardname;
    @FXML private TextField reg_txt_guardtel;
    @FXML private ComboBox<String> cmb_hostel_block;
    @FXML private Button btn_back;
    @FXML private Button btn_reg_student;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmb_hostel_block.setItems(FXCollections.observableArrayList("B1", "B2", "B3", "B4"));
    }

    @FXML
    private void registerButtonAction(MouseEvent event) {
        String name        = reg_txt_username.getText().trim();
        String prnNo       = reg_txt_prn_no.getText().trim();
        String email       = reg_txt_email.getText().trim();
        String phone       = reg_txt_phnmb.getText().trim();
        String address     = reg_txt_address.getText().trim();
        String guardName   = reg_txt_guardname.getText().trim();
        String guardTel    = reg_txt_guardtel.getText().trim();
        String hostelBlock = cmb_hostel_block.getValue();

        // Validation
        if (name.isEmpty() || prnNo.isEmpty() || email.isEmpty()
                || phone.isEmpty() || address.isEmpty()
                || guardName.isEmpty() || guardTel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All Fields Are Required!");
            return;
        }
        if (hostelBlock == null || hostelBlock.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a Hostel Block!");
            return;
        }
        if (!phone.matches("\\d{7,15}")) {
            JOptionPane.showMessageDialog(null, "Invalid Contact Number! Use 7-15 digits.");
            return;
        }
        if (DataStore.prnExists(prnNo)) {
            JOptionPane.showMessageDialog(null, "PRN No already exists! Use a unique PRN.");
            return;
        }

        // Create and store student
        String id = DataStore.nextId();
        StudentDetails student = new StudentDetails(
                id, name, prnNo, email, phone, address, guardName, guardTel, hostelBlock, "0");
        DataStore.students.add(student);
        DataStore.save();                              // â† persist to JSON

        JOptionPane.showMessageDialog(null, "Student Registered Successfully! Data saved.");
        clearForm();
    }

    @FXML
    private void back_btn_clicked(MouseEvent event) throws IOException {
        btn_back.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Student/Student_Menu.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void clearForm() {
        reg_txt_username.clear();
        reg_txt_prn_no.clear();
        reg_txt_email.clear();
        reg_txt_phnmb.clear();
        reg_txt_address.clear();
        reg_txt_guardname.clear();
        reg_txt_guardtel.clear();
        cmb_hostel_block.setValue(null);
    }
}
