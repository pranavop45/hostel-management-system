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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class Update_EmployeeController implements Initializable {

    @FXML private Button btn_refersh;
    @FXML private Button btn_back;
    @FXML private TableView<EmployeeDetails> tableEmployee;
    @FXML private TableColumn<EmployeeDetails, String> col_id;
    @FXML private TableColumn<EmployeeDetails, String> col_name;
    @FXML private TableColumn<EmployeeDetails, String> col_prn_no;
    @FXML private TableColumn<EmployeeDetails, String> col_phonenumber;
    @FXML private TableColumn<EmployeeDetails, String> col_emgtel;
    @FXML private Button btn_update_employee;
    @FXML private TextField emp_id;
    @FXML private TextField reg_txt_emp_emgtel;
    @FXML private TextField reg_txt_emp_username;
    @FXML private TextField reg_txt_emp_prn_no;
    @FXML private TextField reg_txt_emp_phnmb;

    private ObservableList<EmployeeDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void updateEmployeeButtonAction(MouseEvent event) {
        String id     = emp_id.getText().trim();
        String name   = reg_txt_emp_username.getText().trim();
        String prnNo  = reg_txt_emp_prn_no.getText().trim();
        String tel    = reg_txt_emp_phnmb.getText().trim();
        String emgTel = reg_txt_emp_emgtel.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select an employee from the table first!");
            return;
        }
        if (name.isEmpty() || prnNo.isEmpty() || tel.isEmpty() || emgTel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Some Fields are missing!");
            return;
        }
        if (EmployeeDataStore.prnExistsExcluding(prnNo, id)) {
            JOptionPane.showMessageDialog(null, "PRN No already exists!");
            return;
        }

        EmployeeDetails emp = EmployeeDataStore.findById(id);
        if (emp == null) { JOptionPane.showMessageDialog(null, "Employee not found!"); return; }

        emp.setName(name);
        emp.setPrnNo(prnNo);
        emp.setTel(tel);
        emp.seEmgTel(emgTel);

        JOptionPane.showMessageDialog(null, "Employee Updated!");
        clearForm();
        autoRefresh();
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

    private void clearForm() {
        emp_id.clear(); reg_txt_emp_username.clear();
        reg_txt_emp_prn_no.clear(); reg_txt_emp_phnmb.clear(); reg_txt_emp_emgtel.clear();
    }
}
