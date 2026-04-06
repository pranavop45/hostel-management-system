// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Student;

import Model.DataStore;
import Model.LeavedStudentDetails;
import Model.StudentDetails;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * Controller for Delete Student (in-memory).
 * Moves the student to the leaved list before removing from active list.
 */
public class Delete_StudentController implements Initializable {

    @FXML private TableView<StudentDetails>  tableStudent;
    @FXML private TableColumn<StudentDetails, String> col_id;
    @FXML private TableColumn<StudentDetails, String> col_name;
    @FXML private TableColumn<StudentDetails, String> col_prn_no;
    @FXML private TableColumn<StudentDetails, String> col_email;
    @FXML private TableColumn<StudentDetails, String> col_phonenumber;
    @FXML private TableColumn<StudentDetails, String> col_address;
    @FXML private TableColumn<StudentDetails, String> col_g_name;
    @FXML private TableColumn<StudentDetails, String> col_g_tel;

    @FXML private TextField reg_txt_id;
    @FXML private TextField reg_txt_username;
    @FXML private TextField reg_txt_prn_no;
    @FXML private TextField reg_txt_email;
    @FXML private TextField reg_txt_phnmb;
    @FXML private TextField reg_txt_address;
    @FXML private TextField reg_txt_guardname;
    @FXML private TextField reg_txt_guardtel;
    @FXML private DatePicker dateLeaved;

    @FXML private Button btn_delete_student;
    @FXML private Button btn_refersh;
    @FXML private Button btn_back;

    private ObservableList<StudentDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Fields are read-only (display selected student info)
        reg_txt_prn_no.setDisable(true);
        reg_txt_id.setDisable(true);
        reg_txt_guardname.setDisable(true);
        reg_txt_guardtel.setDisable(true);
        reg_txt_email.setDisable(true);
        reg_txt_phnmb.setDisable(true);
        reg_txt_address.setDisable(true);
        reg_txt_username.setDisable(true);

        autoRefresh();
    }

    @FXML
    private void deleteStudentButtonAction(MouseEvent event) {
        String id = reg_txt_id.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a student from the table first!");
            return;
        }

        StudentDetails student = DataStore.findById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            return;
        }

        // Archive to leaved list
        String leaveDate = (dateLeaved.getValue() != null)
                ? dateLeaved.getValue().toString()
                : LocalDate.now().toString();

        LeavedStudentDetails leaved = new LeavedStudentDetails(
                student.getId(), student.getName(), student.getPrnNo(),
                student.getEmail(), student.getPhoneNumber(), student.getAddress(),
                student.getGuardName(), student.getGuardTel(),
                student.getHostelBlock(), leaveDate);
        DataStore.leavedStudents.add(leaved);

        // Remove from active list
        DataStore.removeById(id);
        DataStore.save();                              // â† persist both lists to JSON

        JOptionPane.showMessageDialog(null, "Student Deleted and Archived Successfully! Data saved.");
        clearFields();
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

        tableStudent.setItems(null);
        tableStudent.setItems(data);
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
    }

    private void clearFields() {
        reg_txt_id.setText("");
        reg_txt_username.setText("");
        reg_txt_prn_no.setText("");
        reg_txt_email.setText("");
        reg_txt_phnmb.setText("");
        reg_txt_address.setText("");
        reg_txt_guardname.setText("");
        reg_txt_guardtel.setText("");
        dateLeaved.setValue(null);
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
}
