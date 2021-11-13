package c482Inventory;

/**
 * Subclass of part which is sourced from another company.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor method.
     *
     * @param id the inventory ID
     * @param name part name
     * @param price part price
     * @param stock inventory count
     * @param min minimum allowable count in inventory
     * @param max maximum allowable count in inventory
     * @param companyName name of company the part was sourced from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the companyName for its associated Outsourced part.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the companyName for its associated Outsourced part.
     *
     * @param companyName company name to be associated with part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
