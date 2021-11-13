package c482Inventory.addPart;

import c482Inventory.InHouse;
import c482Inventory.Inventory;
import c482Inventory.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Handles all events within the AddPart window.
 */
public class AddPartController {

    @FXML private HBox addPartPane;
    @FXML private Label errorMessage;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label machineIdCompanyNameLabel;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineIdCompanyNameField;

    private boolean inHouse = true;
    private String newPartName;
    private int newPartInv;
    private double newPartPrice;
    private int newPartMax;
    private int newPartMin;
    private int newPartMachineId;
    private String newPartCompanyName;

    /**
     * Determines whether the part being added is an InHouse part or an Outsourced Part based on the radio buttons in the window.
     *
     * @param e ActionEvent produced when one of the radio buttons is acted upon
     */
    public void radioButtonListener(ActionEvent e) {

        if (e.getSource().equals(inHouseButton)) {
            machineIdCompanyNameLabel.setText("Machine ID");
            inHouse = true;
        }

        else if (e.getSource().equals(outsourcedButton)) {
            machineIdCompanyNameLabel.setText("Company Name");
            inHouse = false;
        }

    }

    /**
     * Ensures fields are filled out &amp; populated with valid data. Displays error messages when appropriate.
     *
     * @return boolean concerning whether part to be saved is valid
     */
    public boolean validateForm() {

        errorMessage.setDisable(true);
        errorMessage.setText("");

        if (
                !nameField.getText().isEmpty() &&
                !invField.getText().isEmpty() &&
                !priceField.getText().isEmpty() &&
                !maxField.getText().isEmpty() &&
                !minField.getText().isEmpty() &&
                !machineIdCompanyNameField.getText().isEmpty()
        ) {
            try {

                newPartName = nameField.getText();
                newPartInv = Integer.parseInt(invField.getText());
                newPartPrice = Double.parseDouble(priceField.getText());
                newPartMax = Integer.parseInt(maxField.getText());
                newPartMin = Integer.parseInt(minField.getText());

                if (inHouse) {
                    newPartMachineId = Integer.parseInt(machineIdCompanyNameField.getText());
                }

                else {
                    newPartCompanyName = machineIdCompanyNameField.getText();
                }

                if (newPartMin > newPartMax) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Min is higher than max.");
                    return false;
                }

                if (newPartInv > newPartMax || newPartInv < newPartMin) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Inventory count is lower than the min or higher than the max.");
                    return false;
                }

                return true;

            } catch (NumberFormatException n) {
                errorMessage.setDisable(false);
                errorMessage.setText("One or more fields have invalid input.");
                return false;
            }
        }

        else {
            errorMessage.setDisable(false);
            errorMessage.setText("One or more fields are null.");
            return false;
        }
    }

    /**
     * Checks part validity, generates a new part ID, adds the part to the Inventory, and then closes the AddPart window.
     *
     * @return boolean concerning whether the part was added or not
     */
    public boolean addPartFormHandler() {

        if (!validateForm()) {
            return false;
        }

        int newPartId = 0;

        while (Inventory.lookupPart(newPartId) != null) {
            newPartId++;
        }

        if (inHouse) {
            Inventory.addPart(new InHouse(newPartId, newPartName, newPartPrice, newPartInv, newPartMin, newPartMax, newPartMachineId));
        }

        else {
            Inventory.addPart(new Outsourced(newPartId, newPartName, newPartPrice, newPartInv, newPartMin, newPartMax, newPartCompanyName));
        }

        closeWindowHandler();
        return true;

    }

    /**
     * Closes the AddPart window.
     */
    public void closeWindowHandler() {
        Stage stage = (Stage) addPartPane.getScene().getWindow();
        stage.close();
    }

}
