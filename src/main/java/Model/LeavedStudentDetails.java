// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model for a student who has left the hostel (in-memory leaved list).
 */
public class LeavedStudentDetails {

    private StringProperty id;
    private StringProperty name;
    private StringProperty prnNo;
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty address;
    private StringProperty guardName;
    private StringProperty guardTel;
    private StringProperty hostelBlock;
    private StringProperty date;

    public LeavedStudentDetails(String id, String name, String prnNo,
                                String email, String phoneNumber, String address,
                                String guardName, String guardTel,
                                String hostelBlock, String date) {
        this.id          = new SimpleStringProperty(id);
        this.name        = new SimpleStringProperty(name);
        this.prnNo       = new SimpleStringProperty(prnNo);
        this.email       = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address     = new SimpleStringProperty(address);
        this.guardName   = new SimpleStringProperty(guardName);
        this.guardTel    = new SimpleStringProperty(guardTel);
        this.hostelBlock = new SimpleStringProperty(hostelBlock);
        this.date        = new SimpleStringProperty(date);
    }

    // â”€â”€ Getters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public String getId()          { return id.get(); }
    public String getName()        { return name.get(); }
    public String getPrnNo()       { return prnNo.get(); }
    public String getEmail()       { return email.get(); }
    public String getPhoneNumber() { return phoneNumber.get(); }
    public String getAddress()     { return address.get(); }
    public String getGuardName()   { return guardName.get(); }
    public String getGuardTel()    { return guardTel.get(); }
    public String getHostelBlock() { return hostelBlock.get(); }
    public String getDate()        { return date.get(); }

    // â”€â”€ Setters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void setId(String v)          { id.set(v); }
    public void setName(String v)        { name.set(v); }
    public void setPrnNo(String v)       { prnNo.set(v); }
    public void setEmail(String v)       { email.set(v); }
    public void setPhoneNumber(String v) { phoneNumber.set(v); }
    public void setAddress(String v)     { address.set(v); }
    public void setGuardName(String v)   { guardName.set(v); }
    public void setGuardTel(String v)    { guardTel.set(v); }
    public void setHostelBlock(String v) { hostelBlock.set(v); }
    public void setDate(String v)        { date.set(v); }

    // â”€â”€ Properties â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public StringProperty idProperty()          { return id; }
    public StringProperty nameProperty()        { return name; }
    public StringProperty prnNoProperty()       { return prnNo; }
    public StringProperty emailProperty()       { return email; }
    public StringProperty phoneNumberProperty() { return phoneNumber; }
    public StringProperty addressProperty()     { return address; }
    public StringProperty guardNameProperty()   { return guardName; }
    public StringProperty guardTelProperty()    { return guardTel; }
    public StringProperty hostelBlockProperty() { return hostelBlock; }
    public StringProperty DateProperty()        { return date; }
}
