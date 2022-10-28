package inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Supplier {

	@Id
	private Integer supplierID ;
    private String name ;
    private String address ;
    
    private Supplier() {

    }

    public Supplier(Integer supplierID, String name, String address){
    	this.supplierID = supplierID;
        this.name = name;
        this.address = address;
    }

    public Integer getSupplierID() { return supplierID; }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public String toString() { return this.supplierID + ": " + this.name + ", " + this.address ;}
	
	
}
