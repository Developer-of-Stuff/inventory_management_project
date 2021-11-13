package c482Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains ObservableLists containing all Parts &amp; Products, as well as methods to add, lookup, update, and delete items from those lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the allParts ObservableList.
     * <br><br>
     * RUNTIME ERROR
     * <br>
     * Was getting a NullPointerException. allParts was not initialized. This was remedied by assigning it to an ObservableArrayList from FXCollections.
     *
     * @param newPart part to be added to the inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the allProducts ObservableList.
     * <br><br>
     * RUNTIME ERROR
     * <br>
     * Was getting a NullPointerException. allProducts was not initialized. This was remedied by assigning it to an ObservableArrayList from FXCollections.
     *
     * @param newProduct product to be added to the inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Retrieves a part from the allParts ObservableList based on its part ID, and returns null if it cannot be found.
     *
     * @param partId the part ID associated with the requested part
     * @return part with the partId from the argument, otherwise null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Retrieves a product from the allProducts ObservableList based on its product ID, and returns null if it cannot be found.
     *
     * @param productId the product ID associated with the requested product
     * @return product with the productId from the argument, otherwise null
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieves a part from the allParts ObservableList based on its name, and returns null if it cannot be found.
     *
     * @param partName the name associated with the requested part
     * @return part that matches the part name from the argument, otherwise null
     */
    public static Part lookupPart(String partName) {
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                return part;
            }
        }
        return null;
    }

    /**
     * Retrieves a product from the allProducts ObservableList based on its name, and returns null if it cannot be found.
     *
     * @param productName the name associated with the requested product
     * @return product that matches the product name from the argument, otherwise null
     */
    public static Product lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Replaces a part at a specified index in the allParts ObservableList with a part provided as an argument.
     *
     * @param index the specified index in the allParts ObservableList array
     * @param selectedPart the replacement part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Replaces a product at a specified index in the allProducts ObservableList with a product provided as an argument.
     *
     * @param index the specified index in the allProducts ObservableList array
     * @param newProduct the replacement product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes a specified part from the allParts ObservableList.
     *
     * @param selectedPart the specified part in the allParts ObservableList array
     * @return boolean concerning whether part was successfully removed
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Removes a specified products from the allProducts ObservableList.
     *
     * @param selectedProduct the specified product in the allProducts ObservableList array
     * @return boolean concerning whether product was successfully removed
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Returns the allParts ObservableList.
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns the allProducts ObservableList.
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
