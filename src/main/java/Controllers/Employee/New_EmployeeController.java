// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Employee;

import Model.EmployeeDataStore;
import Model.EmployeeDetails;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class New_EmployeeController implements Initializable {

    @FXML private Button btn_back;
    @FXML private TextField reg_txt_emp_username;
    @FXML private TextField reg_txt_emp_phnmb;
    @FXML private TextField reg_txt_emp_prn_no;
    @FXML private Button btn_reg_employee;
    @FXML private TextField reg_txt_emp_emgtel;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

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

    @FXML
    private void registerButtonAction(MouseEvent event) {
        String name   = reg_txt_emp_username.getText().trim();
        String prnNo  = reg_txt_emp_prn_no.getText().trim();
        String tel    = reg_txt_emp_emgtel.getText().trim();
        String emgTel = reg_txt_emp_phnmb.getText().trim();

        if (name.isEmpty() || prnNo.isEmpty() || tel.isEmpty() || emgTel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All Fields Are Required!");
            return;
        }
        if (EmployeeDataStore.prnExists(prnNo)) {
            JOptionPane.showMessageDialog(null, "PRN No already exists!");
            return;
        }

        EmployeeDetails emp = new EmployeeDetails(
                EmployeeDataStore.nextId(), name, prnNo, tel, emgTel);
        EmployeeDataStore.employees.add(emp);

        JOptionPane.showMessageDialog(null, "Employee Registered!");
        clearForm();
    }

    private void clearForm() {
        reg_txt_emp_username.clear();
        reg_txt_emp_prn_no.clear();
        reg_txt_emp_emgtel.clear();
        reg_txt_emp_phnmb.clear();
    }
}
