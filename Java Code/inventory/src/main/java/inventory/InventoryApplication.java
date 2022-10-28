package inventory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import org.hibernate.type.descriptor.java.LocalDateJavaDescriptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import inventory.gui.ViewOrders;
import inventory.gui.ManageItem;
import inventory.model.*;
import inventory.model.type.ItemType;
import inventory.repo.*;

@SpringBootApplication
public abstract class InventoryApplication implements InitializingBean{
	
	
	@Autowired
	private ItemRepo items;
	
	@Autowired
	private SupplierRepo suppliers;
	
	@Autowired
	private PeOrderRepo peOrders;
	
	@Autowired
	private NpOrderRepo npOrders;
	
	@Autowired
	private NpOrderLineRepo npOrderLines;
	
    private void setupItems() {

        System.out.println("Setting up inventory items");

        Item item;

        item = new Item(0, "Traditional pizza base", 10, 2F, ItemType.pe) ;
        items.save(item) ;

        item = new Item(1, "Wholemeal pizza base", 15, 3F, ItemType.pe) ;
        items.save(item) ;

        item = new Item(2, "Gluten free pizza base", 12, 3F, ItemType.pe) ;
        items.save(item) ;

        item = new Item(3, "Chicken", 5, 5F, ItemType.pe) ;
        items.save(item) ;

        item = new Item(4, "Receipt paper", 40, 5F, ItemType.np) ;
        items.save(item) ;

        item = new Item(5, "Vinyl gloves", 30, 5F, ItemType.np) ;
        items.save(item) ;

        item = new Item(6, "Napkin", 150, 5F, ItemType.np) ;
        items.save(item) ;

        item = new Item(7, "Paper cup", 50, 5F, ItemType.np) ;
        items.save(item) ;

        System.out.println("Finished setting up inventory items");
    }

    private void printItems(){

        System.out.println("There are " + items.count() + " inventory items: ");

        for (Item item: items.findAll()){
            System.out.println(" " + item);
        }
    }
    
    private void setSuppliers() {

        System.out.println("Setting up suppliers") ;

        Supplier supplier ;

        supplier = new Supplier(0, "Wholesale Foods", "1AB 3KL");
        suppliers.save(supplier);

        supplier = new Supplier(1, "Restaurant Supplies", "O6R 1JK");
        suppliers.save(supplier);

        System.out.println("Finished setting up suppliers") ;
    }
    
    private void printSuppliers(){

        System.out.println("There are " + suppliers.count() + " suppliers: ");

        for (Supplier supplier: suppliers.findAll()){
            System.out.println(" " + supplier);
        }
    }
	
    private void setupPeOrders(){

        PeOrder peOrder;

        File orderFile = new File("PeOrder.txt");
        File reducedOrder = new File("PeReducedOrder.txt");
        
        LocalDate date = LocalDate.now();
        
        try {
        	if(date.getMonthValue() == 0 || date.getMonthValue() == 10 || date.getMonthValue() == 11){
        		System.out.println("Today is " + date + " , setting up reduced perishable order for summer holiday");
        		peOrder = new PeOrder(0, date, reducedOrder);
        		peOrders.save(peOrder);
        	}
        	else System.out.println("Today is " + date + ", setting up normal perishable order");
        		peOrder =new PeOrder(0, date, orderFile);
        		peOrders.save(peOrder);
        }catch(Exception e){
        	System.out.println("Invalid date" + e);
        }

        System.out.println("Finished setting up today's perishable order") ;

    }
    
    private void printPeOrders() {

        for (PeOrder periOrder: peOrders.findAll()){
            System.out.println(periOrder.getDate());
            System.out.println("Supplier for perishable order is " + suppliers.findById(periOrder.getSupplierID()));
            System.out.println("Today's perishable order: ");
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(periOrder.getOrderFile()));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    // read next line
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupNpOrders(){
    	
        System.out.println("Setting up today's non-perishable order") ;

        NpOrder npOrder;

        LocalDate date = LocalDate.now();

        npOrder = new NpOrder(1, date) ;
        npOrders.save(npOrder) ;

        System.out.println("Finished setting up today's non-perishable order") ;
    }
    
    private void setupNpOrderLines(){
    	
        System.out.println("Setting up today's non-perishable order lines") ;

        NpOrderLine npOrderLine;
        
        LocalDate date = LocalDate.now();
        
        NpOrder npOrder = npOrders.findByDate(date);
        
        for (Item item : items.findAll()) {
            if (item.getType() == ItemType.np) {
                if (item.getQuantity() < 50) {
                    npOrderLine = new NpOrderLine(npOrder.getOrderID(), item.getItemID(), item.getName(), 100);
                    npOrderLines.save(npOrderLine);
                }
            }
        }
        
        System.out.println("Finished setting up today's non-perishable order lines") ;
    }
    
    private void printNpOrders(){

        for (NpOrder npOrder: npOrders.findAll()) {
        	
        	System.out.println("Order ID: " + npOrder.getOrderID());
        	
        	System.out.println("Date: " + npOrder.getDate());

        	System.out.println("Supplier: " + suppliers.findById(npOrder.getSupplierID()));

        	System.out.println("Item: " + npOrderLines.findByOrderID(npOrder.getOrderID()));
        }
    }
    
    
    
    
    
    
    
    
public static void main(String args[]) {
    	
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(InventoryApplication.class);
        builder.headless(false).run(args);

    }

    @Override
    public void afterPropertiesSet() throws Exception {

//        setupItems();
//        printItems();
//        setSuppliers();
//        printSuppliers();
//        
//        setupPeOrders();
//        printPeOrders();
//        
//        setupNpOrders();
//        setupNpOrderLines();
//        printNpOrders();
        
//        ViewOrders gui2 = new ViewOrders(this.peOrders, this.npOrders, this.npOrderLines);
//        gui2.pack();
//        gui2.setVisible(true);
      
        ManageItem gui = new ManageItem(this.items);
        gui.pack();
        gui.setVisible(true);


    }
    
}
