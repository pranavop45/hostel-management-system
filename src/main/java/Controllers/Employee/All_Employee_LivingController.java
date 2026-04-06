// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Employee;

import Model.EmployeeDataStore;
import Model.EmployeeDetails;
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

public class All_Employee_LivingController implements Initializable {

    @FXML private TableView<EmployeeDetails> tableEmployee;
    @FXML private TableColumn<EmployeeDetails, String> col_id;
    @FXML private TableColumn<EmployeeDetails, String> col_name;
    @FXML private TableColumn<EmployeeDetails, String> col_prn_no;
    @FXML private TableColumn<EmployeeDetails, String> col_phonenumber;
    @FXML private TableColumn<EmployeeDetails, String> col_emgtel;
    @FXML private Button btn_back;

    private ObservableList<EmployeeDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList(EmployeeDataStore.employees);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prn_no.setCellValueFactory(new PropertyValueFactory<>("prnNo"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
        col_emgtel.setCellValueFactory(new PropertyValueFactory<>("emgTel"));
        tableEmployee.setItems(null);
        tableEmployee.setItems(data);
    }

    @FXML
    private void back_btn_clicked(MouseEvent event) throws IOException {
        btn_back.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Employee/Employee_Menu.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
