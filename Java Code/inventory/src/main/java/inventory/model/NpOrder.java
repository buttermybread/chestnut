package inventory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NpOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer orderID;
    private Integer supplierID;
    private LocalDate date;
    
    private NpOrder() {
    	
    }
    
    public NpOrder(Integer supplierID, LocalDate date){
        this.supplierID = supplierID;
        this.date = date;
    }
    
    public Integer getOrderID() { return orderID; }

    public Integer getSupplierID() { return supplierID; }

    public LocalDate getDate() { return date; }

    public String toString() { return this.orderID + ": " + this.supplierID + ", " + this.date + ", " ; }
    
    
}
