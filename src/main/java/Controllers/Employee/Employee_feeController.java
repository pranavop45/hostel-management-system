// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Controllers.Employee;

import Model.EmployeeDataStore;
import Model.EmployeeDetails;
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

public class Employee_feeController implements Initializable {

    @FXML private Button btn_back;
    @FXML private TextField emplyeeID;
    @FXML private TextField employeeFee;
    @FXML private Button submit;
    @FXML private ComboBox<String> month;
    @FXML private ComboBox<String> year;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        month.setItems(FXCollections.observableArrayList(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"));
        year.setItems(FXCollections.observableArrayList(
                "2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"));
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

    @FXML
    private void submitButtonAction(MouseEvent event) {
        String id     = emplyeeID.getText().trim();
        String salary = employeeFee.getText().trim();

        if (id.isEmpty() || salary.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Employee ID and Salary are required!");
            return;
        }
        if (month.getValue() == null || year.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Please select Month and Year!");
            return;
        }
        // Verify employee exists
        EmployeeDetails emp = EmployeeDataStore.findById(id);
        if (emp == null) {
            JOptionPane.showMessageDialog(null, "No employee found with ID: " + id);
            return;
        }
        try { Double.parseDouble(salary); }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Salary must be a valid number!");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Salary of â‚¹" + salary + " recorded for " + emp.getName()
                + " (" + month.getValue() + " " + year.getValue() + ")");
        clearForm();
    }

    private void clearForm() {
        emplyeeID.clear();
        employeeFee.clear();
        month.setValue(null);
        year.setValue(null);
    }
}