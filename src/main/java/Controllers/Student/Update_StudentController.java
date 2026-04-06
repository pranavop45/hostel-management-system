// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Student;

import Model.DataStore;
import Model.StudentDetails;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * Controller for Update Student (in-memory).
 */
public class Update_StudentController implements Initializable {

    @FXML private TextField reg_txt_id;
    @FXML private TextField reg_txt_username;
    @FXML private TextField reg_txt_prn_no;
    @FXML private TextField reg_txt_email;
    @FXML private TextField reg_txt_phnmb;
    @FXML private TextField reg_txt_address;
    @FXML private TextField reg_txt_guardname;
    @FXML private TextField reg_txt_guardtel;
    @FXML private ComboBox<String> cmb_hostel_block;

    @FXML private TableView<StudentDetails>  tableStudent;
    @FXML private TableColumn<StudentDetails, String> col_id;
    @FXML private TableColumn<StudentDetails, String> col_name;
    @FXML private TableColumn<StudentDetails, String> col_prn_no;
    @FXML private TableColumn<StudentDetails, String> col_email;
    @FXML private TableColumn<StudentDetails, String> col_phonenumber;
    @FXML private TableColumn<StudentDetails, String> col_address;
    @FXML private TableColumn<StudentDetails, String> col_g_name;
    @FXML private TableColumn<StudentDetails, String> col_g_tel;
    @FXML private TableColumn<StudentDetails, String> col_hostel_block;

    @FXML private Button btn_update_student;
    @FXML private Button btn_refersh;
    @FXML private Button btn_back;

    private ObservableList<StudentDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmb_hostel_block.setItems(FXCollections.observableArrayList("B1", "B2", "B3", "B4"));
        autoRefresh();
    }

    private void autoRefresh() {
        data = FXCollections.observableArrayList(DataStore.students);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prn_no.setCellValueFactory(new PropertyValueFactory<>("prnNo"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_g_name.setCellValueFactory(new PropertyValueFactory<>("guardName"));
        col_g_tel.setCellValueFactory(new PropertyValueFactory<>("guardTel"));
        if (col_hostel_block != null)
            col_hostel_block.setCellValueFactory(new PropertyValueFactory<>("hostelBlock"));

        tableStudent.setItems(null);
        tableStudent.setItems(data);
    }

    @FXML
    private void updateStudentButtonAction(MouseEvent event) {
        String id          = reg_txt_id.getText().trim();
        String name        = reg_txt_username.getText().trim();
        String prnNo       = reg_txt_prn_no.getText().trim();
        String email       = reg_txt_email.getText().trim();
        String phone       = reg_txt_phnmb.getText().trim();
        String address     = reg_txt_address.getText().trim();
        String guardName   = reg_txt_guardname.getText().trim();
        String guardTel    = reg_txt_guardtel.getText().trim();
        String hostelBlock = cmb_hostel_block.getValue();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a student from the table first!");
            return;
        }
        if (name.isEmpty() || prnNo.isEmpty() || email.isEmpty()
                || phone.isEmpty() || address.isEmpty()
                || guardName.isEmpty() || guardTel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Some Fields are missing!");
            return;
        }
        if (hostelBlock == null || hostelBlock.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a Hostel Block!");
            return;
        }
        if (DataStore.prnExistsExcluding(prnNo, id)) {
            JOptionPane.showMessageDialog(null, "PRN No already exists for another student!");
            return;
        }

        StudentDetails student = DataStore.findById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            return;
        }

        student.setName(name);
        student.setPrnNo(prnNo);
        student.setEmail(email);
        student.setPhoneNumber(phone);
        student.setAddress(address);
        student.setGuardName(guardName);
        student.setGuardTel(guardTel);
        student.setHostelBlock(hostelBlock);

        DataStore.save();                              // â† persist to JSON
        JOptionPane.showMessageDialog(null, "Student Updated Successfully! Data saved.");
        clearForm();
        autoRefresh();
    }

    @FXML
    private void refreshButtionClickAction(MouseEvent event) {
        autoRefresh();
    }

    @FXML
    private void displaySelectedAction(MouseEvent event) {
        StudentDetails student = tableStudent.getSelectionModel().getSelectedItem();
        if (student == null) return;

        reg_txt_id.setText(student.getId());
        reg_txt_username.setText(student.getName());
        reg_txt_prn_no.setText(student.getPrnNo());
        reg_txt_email.setText(student.getEmail());
        reg_txt_phnmb.setText(student.getPhoneNumber());
        reg_txt_address.setText(student.getAddress());
        reg_txt_guardname.setText(student.getGuardName());
        reg_txt_guardtel.setText(student.getGuardTel());
        cmb_hostel_block.setValue(student.getHostelBlock());
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

    private void clearForm() {
        reg_txt_id.clear();
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
