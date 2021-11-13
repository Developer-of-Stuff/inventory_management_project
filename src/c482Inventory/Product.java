package c482Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product which may have an associated parts list.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor method.
     *
     * @param id the product ID
     * @param name the product name
     * @param price the product price
     * @param stock inventory count
     * @param min minimum allowable count in inventory
     * @param max maximum allowable count in inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Retrieves the product ID for its associated product.
     *
     * @return product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product ID for its associated product.
     *
     * @param id product ID to be associated with product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name for the associated product.
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the associated product.
     *
     * @param name name to be associated with product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the price for the associated product.
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price for the associated product.
     *
     * @param price price to be associated with product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the inventory count for the associated product.
     *
     * @return product inventory count
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the inventory count for the associated product.
     *
     * @param stock inventory count to be associated with product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Retrieves the minimum for the associated product.
     *
     * @return product minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum for the associated product.
     *
     * @param min minimum to be associated with product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Retrieves the maximum for the associated product.
     *
     * @return product maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum for the associated product.
     *
     * @param max maximum to be associated with product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associatedParts ObservableList.
     *
     * @param part part to be added to associatedParts
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes a part from the associatedParts ObservableList.
     *
     * @param selectedAssociatedPart part to be removed from the Product's associatedParts
     * @return boolean concerning whether the part existed in associatedParts
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Retrieves the associatedParts ObservableList.
     *
     * @return the associatedParts ObservableList
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
