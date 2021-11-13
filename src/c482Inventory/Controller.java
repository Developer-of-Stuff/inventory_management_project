package c482Inventory;

import c482Inventory.modifyPart.ModifyPartController;
import c482Inventory.modifyProduct.ModifyProductController;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Optional;

/**
 * Responsible for handling all buttons as well as table display &amp; functionality on the main page.
 */
public class Controller {

    @FXML private HBox mainPane;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, Integer> productId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, String> productName;
    @FXML private TableColumn<Part, Integer> partInvLevel;
    @FXML private TableColumn<Part, Integer> productInvLevel;
    @FXML private TableColumn<Part, Double> partPricePerUnit;
    @FXML private TableColumn<Part, Double> productPricePerUnit;
    @FXML private TextField searchPartField;
    @FXML private TextField searchProductField;

    private FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts());
    private SortedList<Part> sortedPartList = new SortedList<>(filteredPartList);
    private FilteredList<Product> filteredProductList = new FilteredList<>(Inventory.getAllProducts());
    private SortedList<Product> sortedProductList = new SortedList<>(filteredProductList);

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    /**
     * Sets the TableViews and gives functionality to the search fields above the Part &amp; Product Tables.
     */
    public void initialize() {

        partTableView.setItems(sortedPartList);
        productTableView.setItems(sortedProductList);

        sortedPartList.comparatorProperty().bind(partTableView.comparatorProperty());
        sortedProductList.comparatorProperty().bind(productTableView.comparatorProperty());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        /*

        Source for the formatting of the price TableColumns:
        https://stackoverflow.com/questions/48733121/javafx-format-double-in-tablecolumn

         */

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

        productPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPricePerUnit.setCellFactory(tc -> new TableCell<>(){
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

        searchProductField.textProperty().addListener((observableValue, s, t1) -> {
            int searchId;
            try {
                searchId = Integer.parseInt(t1);
                filteredProductList.setPredicate(product -> product.getId() == searchId);
                productTableView.getSelectionModel().selectFirst();
            } catch (NumberFormatException n) {
                filteredProductList.setPredicate(part -> part.getName().toLowerCase().contains(t1.toLowerCase()));
            }
        });

    }

    /**
     * Handles the initialization &amp; display of the "Add Part" window.
     */
    public void addPartButtonHandler() {

        Stage addPartStage = new Stage();
        addPartStage.initOwner(mainPane.getScene().getWindow());
        addPartStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addPart/addPartForm.fxml"));

        try {
            Scene addPartScene = new Scene(fxmlLoader.load());
            addPartStage.setScene(addPartScene);
            addPartStage.showAndWait();

        } catch (IOException e) {
            System.out.println("Couldn't load scene.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the initialization &amp; display of the "Modify Part" window.
     * <br><br>
     * FUTURE ENHANCEMENT
     * <br>
     * Add the ability to double click on table rows to open the ModifyPart window for that particular part.
     */
    public void modifyPartButtonHandler() {

        Stage modifyPartStage = new Stage();
        modifyPartStage.initOwner(mainPane.getScene().getWindow());
        modifyPartStage.initModality(Modality.APPLICATION_MODAL);
        Part part = partTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("modifyPart/modifyPartForm.fxml"));

        if (part != null) {
            try {
                Scene modifyPartScene = new Scene(fxmlLoader.load());
                ModifyPartController controller = fxmlLoader.getController();
                controller.loadModifyPartFields(part);
                modifyPartStage.setScene(modifyPartScene);
                modifyPartStage.showAndWait();

            } catch (IOException e) {
                System.out.println("Couldn't load scene.");
                e.printStackTrace();
            }
        }

    }

    /**
     * Generates a dialog to confirm the deletion of a selected part.
     */
    public void deletePartButtonHandler() {

        Part part = partTableView.getSelectionModel().getSelectedItem();

        if (part != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Part Deletion Confirmation");
            dialog.setHeaderText("Are you sure you want to delete this part?");
            dialog.getDialogPane().setPadding(new Insets(15));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                Inventory.deletePart(part);
            }

        }

    }

    /**
     * Handles the initialization &amp; display of the "Add Product" window.
     */
    public void addProductButtonHandler() {
        Stage addProductStage = new Stage();
        addProductStage.initOwner(mainPane.getScene().getWindow());
        addProductStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addProduct/addProductForm.fxml"));

        try {
            Scene addProductScene = new Scene(fxmlLoader.load());
            addProductStage.setScene(addProductScene);
            addProductStage.showAndWait();

        } catch (IOException e) {
            System.out.println("Couldn't load scene.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the initialization &amp; display of the "Modify Product" window.
     * <br><br>
     * FUTURE ENHANCEMENT
     * <br>
     * Add the ability to double click on table rows to open the ModifyProduct window for that particular product.
     */
    public void modifyProductButtonHandler() {

        Stage modifyProductStage = new Stage();
        modifyProductStage.initOwner(mainPane.getScene().getWindow());
        modifyProductStage.initModality(Modality.APPLICATION_MODAL);
        Product product = productTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("modifyProduct/modifyProductForm.fxml"));

        if (product != null) {
            try {
                Scene modifyProductScene = new Scene(fxmlLoader.load());
                ModifyProductController controller = fxmlLoader.getController();
                controller.loadModifyProductFields(product);
                modifyProductStage.setScene(modifyProductScene);
                modifyProductStage.showAndWait();

            } catch (IOException e) {
                System.out.println("Couldn't load scene.");
                e.printStackTrace();
            }
        }

    }

    /**
     * Generates a dialog to confirm the deletion of a selected product.
     */
    public void deleteProductButtonHandler() {

        Product product = productTableView.getSelectionModel().getSelectedItem();

        if (product != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Product Deletion Confirmation");
            dialog.setHeaderText("Are you sure you want to delete this product?");
            dialog.getDialogPane().setPadding(new Insets(15));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                Inventory.deleteProduct(product);
            }
        }

    }

    /**
     * Exits the program.
     */
    public void exitButtonHandler() {
        System.exit(0);
    }

}
