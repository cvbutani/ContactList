package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.dataModel.Contact;

public class ContactPicker {
    @FXML
    private TextField firstnamePicker;

    @FXML
    private TextField lastnamePicker;

    @FXML
    private TextField numberPicker;

    @FXML
    private TextField notePicker;

    public Contact processResults() {
        String firstName = firstnamePicker.getText();
        String lastName = lastnamePicker.getText();
        String phoneNumber = numberPicker.getText();
        String notes = notePicker.getText();

        Contact contact = new Contact(firstName,lastName,phoneNumber,notes);
        return contact;
    }

    public void editController(Contact contact){
        firstnamePicker.setText(contact.getFirstName());
        lastnamePicker.setText(contact.getLastName());
        numberPicker.setText(contact.getPhoneNumber());
        notePicker.setText(contact.getNotes());
    }

    public void updateController(Contact contact){
        contact.setFirstName(firstnamePicker.getText());
        contact.setLastName(lastnamePicker.getText());
        contact.setPhoneNumber(numberPicker.getText());
        contact.setNotes(notePicker.getText());
    }

}
