package c482Inventory.addProduct;

import c482Inventory.Inventory;
import c482Inventory.Part;
import c482Inventory.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Optional;

/**
 * Handles all events within the AddProduct window.
 */
public class AddProductController {

    @FXML private HBox addProductPane;
    @FXML private Label errorMessage;

    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Part> componentTableView;
    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, Integer> componentId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, String> componentName;
    @FXML private TableColumn<Part, Integer> partInvLevel;
    @FXML private TableColumn<Part, Integer> componentInvLevel;
    @FXML private TableColumn<Part, Double> partPricePerUnit;
    @FXML private TableColumn<Part, Double> componentPricePerUnit;

    @FXML private TextField searchPartField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;

    private String newProductName;
    private int newProductInv;
    private double newProductPrice;
    private int newProductMax;
    private int newProductMin;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    private Product newProduct = new Product(0, "", 0, 0, 0, 0);

    private FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts());
    private SortedList<Part> sortedPartList = new SortedList<>(filteredPartList);
    private FilteredList<Part> filteredComponentList = new FilteredList<>(newProduct.getAllAssociatedParts());
    private SortedList<Part> sortedComponentList = new SortedList<>(filteredComponentList);

    /**
     * Sets the TableViews and gives functionality to the search field above the Part Table.
     */
    public void initialize() {

        partTableView.setItems(sortedPartList);
        componentTableView.setItems(sortedComponentList);

        sortedPartList.comparatorProperty().bind(partTableView.comparatorProperty());
        sortedComponentList.comparatorProperty().bind(componentTableView.comparatorProperty());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        componentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        componentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        componentInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPricePerUnit.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                }
                else {
                    setText(nf.format(price));
                }
            }
        });

        componentPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        componentPricePerUnit.setCellFactory(tc -> new TableCell<>(){
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                }
                else {
                    setText(nf.format(price));
                }
            }
        });

        searchPartField.textProperty().addListener((observableValue, s, t1) -> {
            int searchId;
            try {
                searchId = Integer.parseInt(t1);
                filteredPartList.setPredicate(part -> part.getId() == searchId);
                partTableView.getSelectionModel().selectFirst();
            } catch (NumberFormatException n) {
                filteredPartList.setPredicate(part -> part.getName().toLowerCase().contains(t1.toLowerCase()));
            }
        });

    }

    /**
     * Adds the selected part from the Parts table to the new Product's associatedParts ObservableList.
     */
    public void addComponentButtonHandler() {
        Part component = partTableView.getSelectionModel().getSelectedItem();

        if (component != null) {
            newProduct.addAssociatedPart(component);
        }
    }

    /**
     * Removes the selected part from the associatedParts ObservableList once confirmed via a dialog.
     */
    public void removeComponentButtonHandler() {
        Part component = componentTableView.getSelectionModel().getSelectedItem();

        if (component != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Part Removal Confirmation");
            dialog.setHeaderText("Are you sure you want to remove this part?");
            dialog.getDialogPane().setPadding(new Insets(15));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                newProduct.deleteAssociatedPart(component);
            }
        }
    }

    /**
     * Ensures fields are filled out &amp; populated with valid data. Displays error messages when appropriate.
     *
     * @return boolean concerning whether product to be saved is valid
     */
    public boolean validateForm() {

        errorMessage.setDisable(true);
        errorMessage.setText("");

        if (!nameField.getText().isEmpty() &&
                !invField.getText().isEmpty() &&
                !priceField.getText().isEmpty() &&
                !maxField.getText().isEmpty() &&
                !minField.getText().isEmpty()
        ) {
            try {

                newProductName = nameField.getText();
                newProductInv = Integer.parseInt(invField.getText());
                newProductPrice = Double.parseDouble(priceField.getText());
                newProductMax = Integer.parseInt(maxField.getText());
                newProductMin = Integer.parseInt(minField.getText());

                if (newProductMin > newProductMax) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Min is higher than max.");
                    return false;
                }

                if (newProductInv > newProductMax || newProductInv < newProductMin) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Inventory count is lower than the min or higher than the max.");
                    return false;
                }

                if (newProduct.getAllAssociatedParts().isEmpty()) {
                    errorMessage.setDisable(false);
                    errorMessage.setText("Product has no associated parts.");
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
     * Checks product validity, generates a new product ID, sets the new product's fields, adds the product to the Inventory, and then closes the AddProduct window.
     *
     * @return boolean concerning whether the product was added or not
     */
    public boolean addProductButtonHandler() {
        // Used lookupPart instead of lookupProduct

        if (!validateForm()) {
            return false;
        }

        int newProductId = 1000;

        while (Inventory.lookupProduct(newProductId) != null) {
            newProductId++;
        }

        newProduct.setId(newProductId);
        newProduct.setName(newProductName);
        newProduct.setPrice(newProductPrice);
        newProduct.setStock(newProductInv);
        newProduct.setMax(newProductMax);
        newProduct.setMin(newProductMin);

        Inventory.addProduct(newProduct);
        closeWindowHandler();
        return true;
    }

    /**
     * Closes the AddProduct window.
     */
    public void closeWindowHandler() {
        Stage stage = (Stage) addProductPane.getScene().getWindow();
        stage.close();
    }

}
