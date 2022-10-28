package inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NpOrderLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer orderID;
    private Integer itemID;
    private String name;
    private Integer quantity;
    
    private NpOrderLine() {

    }
    
    public NpOrderLine(Integer orderID, Integer itemID, String name, Integer quantity){
        this.orderID = orderID;
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
    }
    
    public Integer getId() { return id; }

    public Integer getOrderID() {
        return orderID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() { return quantity; }

    public String toString() {
        return this.id + ": " + this.orderID + ", " + this.itemID + ", " + this.name + ", " + this.quantity;
    }

}
