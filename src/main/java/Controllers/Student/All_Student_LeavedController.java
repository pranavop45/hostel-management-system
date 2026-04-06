// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Student;

import Model.DataStore;
import Model.LeavedStudentDetails;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Shows all leaved/deleted students (from in-memory DataStore).
 */
public class All_Student_LeavedController implements Initializable {

    private ObservableList<LeavedStudentDetails> data;

    @FXML private TableView<LeavedStudentDetails>  tableStudent;
    @FXML private TableColumn<LeavedStudentDetails, String> col_id;
    @FXML private TableColumn<LeavedStudentDetails, String> col_name;
    @FXML private TableColumn<LeavedStudentDetails, String> col_prn_no;
    @FXML private TableColumn<LeavedStudentDetails, String> col_email;
    @FXML private TableColumn<LeavedStudentDetails, String> col_phonenumber;
    @FXML private TableColumn<LeavedStudentDetails, String> col_address;
    @FXML private TableColumn<LeavedStudentDetails, String> col_g_name;
    @FXML private TableColumn<LeavedStudentDetails, String> col_g_tel;
    @FXML private TableColumn<LeavedStudentDetails, String> col_l_date;

    @FXML private Button btn_back;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList(DataStore.leavedStudents);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prn_no.setCellValueFactory(new PropertyValueFactory<>("prnNo"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_g_name.setCellValueFactory(new PropertyValueFactory<>("guardName"));
        col_g_tel.setCellValueFactory(new PropertyValueFactory<>("guardTel"));
        col_l_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableStudent.setItems(null);
        tableStudent.setItems(data);
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
