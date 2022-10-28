package inventory.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import inventory.model.type.*;

@Entity
public class Item {
	
	@Id
    private Integer itemID ;
    private String name;
    private Integer quantity;
    private Float unitPrice;
    private ItemType type;
    
    private Item() {

    }

    public Item(Integer itemID, String name, Integer quantity, Float unitPrice, ItemType type){
    	this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.type = type;
    }

    public Integer getItemID() { return itemID; }

    public String getName() { return name; }

    public Integer getQuantity() { return quantity; }

    public Float getUnitPrice() { return unitPrice; }

    public ItemType getType() { return type; }

    public String toString() { return this.itemID + ": " + this.name + ", " + this.quantity + ", " + this.unitPrice + ", " + this.type ; }
}
