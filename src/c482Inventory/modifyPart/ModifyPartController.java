package c482Inventory.modifyPart;

import c482Inventory.InHouse;
import c482Inventory.Inventory;
import c482Inventory.Outsourced;
import c482Inventory.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Handles all events within the ModifyPart window.
 */
public class ModifyPartController {

    @FXML private HBox modifyPartPane;
    @FXML private Label errorMessage;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label machineIdCompanyNameLabel;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineIdCompanyNameField;

    private boolean inHouse = true;
    private int partId;
    private String newPartName;
    private int newPartInv;
    private double newPartPrice;
    private int newPartMax;
    private int newPartMin;
    private int newPartMachineId;
    private String newPartCompanyName;

    /**
     * Loads the text fields &amp; selects the appropriate radio button using data from the Part argument.
     *
     * @param part part to be loaded and modified
     */
    public void loadModifyPartFields(Part part) {

        partId = part.getId();
        idField.setText(partId + "");
        nameField.setText(part.getName());
        invField.setText(part.getStock() + "");
        priceField.setText(part.getPrice() + "");
        maxField.setText(part.getMax() + "");
        minField.setText(part.getMin() + "");

        if (part.getClass().equals(InHouse.class)) {
            machineIdCompanyNameField.setText(((InHouse) part).getMachineId() + "");
            inHouseButton.setSelected(true);
        }

        else {
            machineIdCompanyNameLabel.setText("Company Name");
            machineIdCompanyNameField.setText(((Outsourced) part).getCompanyName());
            outsourcedButton.setSelected(true);
            inHouse = false;
        }

    }

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

                if (newPartInv > newPartMax || newPartInv < newPartMin) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Inventory count is lower than the min or higher than the max.");
                    return false;
                }

                System.out.println("Successful validation!");
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
     * Checks part validity, converts InHouse parts to Outsourced parts &amp; vice versa if necessary, updates the part, and then closes the ModifyPart window.
     *
     * @return boolean concerning whether the part was updated or not
     */
    public boolean modifyPartFormHandler() {

        if (!validateForm()) {
            return false;
        }

        Part initSelectedPart = Inventory.lookupPart(partId);

        if (inHouse) {
            if (initSelectedPart.getClass().equals(Outsourced.class)) {
                initSelectedPart = new InHouse(
                        initSelectedPart.getId(),
                        initSelectedPart.getName(),
                        initSelectedPart.getPrice(),
                        initSelectedPart.getStock(),
                        initSelectedPart.getMin(),
                        initSelectedPart.getMax(),
                        0);
            }

            InHouse finSelectedPart = (InHouse) initSelectedPart;

            finSelectedPart.setName(newPartName);
            finSelectedPart.setPrice(newPartPrice);
            finSelectedPart.setStock(newPartInv);
            finSelectedPart.setMin(newPartMin);
            finSelectedPart.setMax(newPartMax);
            finSelectedPart.setMachineId(newPartMachineId);
            Inventory.updatePart(partId, finSelectedPart);
        }

        else {
            if (initSelectedPart.getClass().equals(InHouse.class)) {
                initSelectedPart = new Outsourced(
                        initSelectedPart.getId(),
                        initSelectedPart.getName(),
                        initSelectedPart.getPrice(),
                        initSelectedPart.getStock(),
                        initSelectedPart.getMin(),
                        initSelectedPart.getMax(),
                        "");
            }

            Outsourced finSelectedPart = (Outsourced) initSelectedPart;

            finSelectedPart.setName(newPartName);
            finSelectedPart.setPrice(newPartPrice);
            finSelectedPart.setStock(newPartInv);
            finSelectedPart.setMin(newPartMin);
            finSelectedPart.setMax(newPartMax);
            finSelectedPart.setCompanyName(newPartCompanyName);
            Inventory.updatePart(partId, finSelectedPart);
        }

        closeWindowHandler();
        return true;

    }

    /**
     * Closes the ModifyPart window.
     */
    public void closeWindowHandler() {
        Stage stage = (Stage) modifyPartPane.getScene().getWindow();
        stage.close();
    }

}
