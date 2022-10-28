package inventory.model;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PeOrder {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer orderID;
    private Integer supplierID;
    private LocalDate date;
    private File orderFile;
    
    private PeOrder() {

    }

    public PeOrder(Integer supplierID, LocalDate date, File orderFile){
        this.supplierID = supplierID;
        this.date = date;
        this.orderFile = orderFile;
    }

    public Integer getOrderID() { return orderID; }

    public Integer getSupplierID() { return supplierID; }

    public LocalDate getDate() { return date; }

    public File getOrderFile() { return orderFile; }

    public String toString() { return this.orderID + ": " + this.supplierID + ", " + this.date + ", " + this.orderFile ; }


}
