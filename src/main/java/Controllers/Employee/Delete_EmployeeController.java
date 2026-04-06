// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Employee;

import Model.EmployeeDataStore;
import Model.EmployeeDetails;
import Model.LeavedEmployeeDetails;
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

public class Delete_EmployeeController implements Initializable {

    @FXML private Button btn_update_employee;
    @FXML private Button btn_refersh;
    @FXML private TextField emp_id;
    @FXML private TextField reg_txt_emp_emgtel;
    @FXML private TextField reg_txt_emp_username;
    @FXML private TextField reg_txt_emp_prn_no;
    @FXML private TextField reg_txt_emp_phnmb;
    @FXML private TableView<EmployeeDetails> tableEmployee;
    @FXML private TableColumn<EmployeeDetails, String> col_id;
    @FXML private TableColumn<EmployeeDetails, String> col_name;
    @FXML private TableColumn<EmployeeDetails, String> col_prn_no;
    @FXML private TableColumn<EmployeeDetails, String> col_phonenumber;
    @FXML private TableColumn<EmployeeDetails, String> col_emgtel;
    @FXML private Button btn_back;
    @FXML private DatePicker dateLeaved;

    private ObservableList<EmployeeDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp_id.setDisable(true);
        reg_txt_emp_username.setDisable(true);
        reg_txt_emp_prn_no.setDisable(true);
        reg_txt_emp_phnmb.setDisable(true);
        reg_txt_emp_emgtel.setDisable(true);
        autoRefresh();
    }

    @FXML
    private void deleteEmployeeButtonAction(MouseEvent event) {
        String id = emp_id.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select an employee from the table first!");
            return;
        }
        EmployeeDetails emp = EmployeeDataStore.findById(id);
        if (emp == null) { JOptionPane.showMessageDialog(null, "Employee not found!"); return; }

        String leaveDate = (dateLeaved.getValue() != null)
                ? dateLeaved.getValue().toString() : LocalDate.now().toString();

        LeavedEmployeeDetails leaved = new LeavedEmployeeDetails(
                emp.getId(), emp.getName(), emp.getPrnNo(),
                emp.getTel(), emp.getEmgTel(), leaveDate);
        EmployeeDataStore.leavedEmployees.add(leaved);
        EmployeeDataStore.removeById(id);

        JOptionPane.showMessageDialog(null, "Employee Deleted and Archived!");
        clearFields();
        autoRefresh();
    }

    private void autoRefresh() {
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
    private void refreshButtionClickAction(MouseEvent event) { autoRefresh(); }

    @FXML
    private void displaySelectedAction(MouseEvent event) {
        EmployeeDetails emp = tableEmployee.getSelectionModel().getSelectedItem();
        if (emp == null) return;
        emp_id.setText(emp.getId());
        reg_txt_emp_username.setText(emp.getName());
        reg_txt_emp_prn_no.setText(emp.getPrnNo());
        reg_txt_emp_phnmb.setText(emp.getTel());
        reg_txt_emp_emgtel.setText(emp.getEmgTel());
    }

    private void clearFields() {
        emp_id.setText(""); reg_txt_emp_username.setText("");
        reg_txt_emp_prn_no.setText(""); reg_txt_emp_phnmb.setText("");
        reg_txt_emp_emgtel.setText(""); dateLeaved.setValue(null);
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
