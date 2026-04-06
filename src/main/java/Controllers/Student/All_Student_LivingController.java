// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Student;

import Model.DataStore;
import Model.LeavedStudentDetails;
import Model.StudentDetails;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Shows all currently living students and allows direct table-selection delete.
 */
public class All_Student_LivingController implements Initializable {

    private ObservableList<StudentDetails> data;

    @FXML private TableView<StudentDetails>           tableStudent;
    @FXML private TableColumn<StudentDetails, String> col_id1;
    @FXML private TableColumn<StudentDetails, String> col_name1;
    @FXML private TableColumn<StudentDetails, String> col_prn_no1;
    @FXML private TableColumn<StudentDetails, String> col_email1;
    @FXML private TableColumn<StudentDetails, String> col_phonenumber1;
    @FXML private TableColumn<StudentDetails, String> col_address1;
    @FXML private TableColumn<StudentDetails, String> col_g_name1;
    @FXML private TableColumn<StudentDetails, String> col_g_tel1;
    @FXML private TableColumn<StudentDetails, String> col_hostel_block1;
    @FXML private TableColumn<StudentDetails, String> col_fees1;

    @FXML private Button btn_back;
    @FXML private Button btn_delete_student;

    // â”€â”€ Initialise â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindColumns();
        refreshTable();
    }

    private void bindColumns() {
        col_id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prn_no1.setCellValueFactory(new PropertyValueFactory<>("prnNo"));
        col_email1.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_phonenumber1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_address1.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_g_name1.setCellValueFactory(new PropertyValueFactory<>("guardName"));
        col_g_tel1.setCellValueFactory(new PropertyValueFactory<>("guardTel"));
        if (col_hostel_block1 != null)
            col_hostel_block1.setCellValueFactory(new PropertyValueFactory<>("hostelBlock"));
        if (col_fees1 != null)
            col_fees1.setCellValueFactory(new PropertyValueFactory<>("fees"));
    }

    private void refreshTable() {
        data = FXCollections.observableArrayList(DataStore.students);
        tableStudent.setItems(null);
        tableStudent.setItems(data);
    }

    // â”€â”€ DELETE flow â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    @FXML
    private void deleteStudentAction(MouseEvent event) {
        // Step 1: get the selected row
        StudentDetails selected = tableStudent.getSelectionModel().getSelectedItem();

        // Step 2: nothing selected?
        if (selected == null) {
            showAlert(AlertType.WARNING, "No Selection",
                    "Please select a student row from the table first.");
            return;
        }

        // Step 3+4: confirmation dialog
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete Student");
        confirm.setHeaderText("Permanently delete this student?");
        confirm.setContentText(
                "Name : " + selected.getName() + "\n" +
                "PRN  : " + selected.getPrnNo() + "\n" +
                "Block: " + selected.getHostelBlock() + "\n\n" +
                "This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return; // user cancelled
        }

        // Step 5: archive to leaved list
        LeavedStudentDetails leaved = new LeavedStudentDetails(
                selected.getId(), selected.getName(), selected.getPrnNo(),
                selected.getEmail(), selected.getPhoneNumber(), selected.getAddress(),
                selected.getGuardName(), selected.getGuardTel(),
                selected.getHostelBlock(), LocalDate.now().toString());
        DataStore.leavedStudents.add(leaved);

        // Step 6: remove from active list
        DataStore.students.remove(selected);

        // Step 7: persist to JSON
        DataStore.save();

        // Step 8: instant UI update â€” no restart needed
        refreshTable();

        showAlert(AlertType.INFORMATION, "Deleted",
                "Student '" + selected.getName() + "' (PRN: " + selected.getPrnNo()
                + ") has been removed and archived.");
    }

    // â”€â”€ Navigation â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    @FXML
    private void back_btn_clicked(MouseEvent event) throws IOException {
        btn_back.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                getClass().getResource("/FXML/Student/Student_Menu.fxml"));
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }

    // â”€â”€ Helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
