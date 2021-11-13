package c482Inventory;

/**
 * Subclass of part which is produced in-house.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor method.
     *
     * @param id the inventory ID
     * @param name part name
     * @param price part price
     * @param stock inventory count
     * @param min minimum allowable count in inventory
     * @param max maximum allowable count in inventory
     * @param machineId machine ID associated with part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns the machine ID for its associated in-house part.
     *
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machine ID for its associated in-house part.
     *
     * @param machineId machine ID to be associated with part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
