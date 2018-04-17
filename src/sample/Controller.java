package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sample.dataModel.Contact;
import sample.dataModel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<Contact> contactTable;

    private ContactData data;

    public void initialize() {
        data = new ContactData();
        data.loadContacts();
        contactTable.setItems(data.getContacts());
    }

    @FXML
    public void showContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("New Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddContact.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't Load Dialog.");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactPicker controller = fxmlLoader.getController();
            Contact newContact = controller.processResults();
            data.addContact(newContact);
            data.saveContacts();
        }

    }


    @FXML
    public void editContactDialog() {
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please Select any contact to Edit");
            alert.showAndWait();
            return;
        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainPane.getScene().getWindow());
            dialog.setTitle("Edit Contact");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddContact.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                System.out.println("Couldn't Load Dialog.");
                e.printStackTrace();
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            ContactPicker controller = fxmlLoader.getController();
            controller.editController(selectedContact);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.updateController(selectedContact);
                data.saveContacts();
            }
        }
    }

    @FXML
    public void deleteListedItem() {
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please Select any contact to Delete");
            alert.showAndWait();
            return;
        }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Contact Selected " + selectedContact.getFirstName() + " " + selectedContact.getLastName());
            alert.setHeaderText(null);
            alert.setContentText("Press OK to confirm");
            alert.showAndWait();

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                data.deleteContact(selectedContact);
                data.saveContacts();
            }
    }

    @FXML
    public void exitDialog(){
        Platform.exit();
    }
}

