package inventory.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import inventory.model.Item;
import inventory.repo.ItemRepo;
import inventory.gui.*;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class ManageItem extends JFrame {
	
	private ItemRepo items ;
	private ItemTableModel model ;
	
	private Item selectedItem ;

	private JPanel contentPane;
	private JTable tblItems;
	private JButton btnEdit;
	private JButton btnAdd;

	/**
	 * Create the frame.
	 */
	
	public ManageItem(ItemRepo items) {
		
		this.items = items;
		this.model = new ItemTableModel() ;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTitle = new JLabel("Inventory Items");
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnEdit = new JButton("Edit Item");
		btnEdit.setEnabled(false);
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editItem(selectedItem);
			}
		});
		
		btnAdd = new JButton("Add Item");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addItem();
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(174, Short.MAX_VALUE)
					.addComponent(btnEdit)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAdd)
					.addGap(26))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addGap(14)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEdit)
						.addComponent(btnAdd))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
		tblItems = new JTable(model);
		tblItems.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {

	        	int row = tblItems.getSelectedRow();
	        	
	        	if (row >= 0)
	        		selectedItem = model.getItemAtRow(row) ;
	        	else
	        		selectedItem = null ;
	        	
	        	btnEdit.setEnabled(selectedItem != null);
	        }
	    });
		scrollPane.setViewportView(tblItems);
		contentPane.setLayout(gl_contentPane);
	}

	private void addItem() {
		EditOrCreateItem dlg = new EditOrCreateItem(null) ;
	    Item createdItem = dlg.showDialog();

	    if (createdItem != null) {

	        items.save(createdItem) ;
	        model.fireTableDataChanged();
	        model.refreshData();
	    }
	}
	
	private void editItem(Item item) {
		EditOrCreateItem dlg = new EditOrCreateItem(item) ;
	    Item editedItem = dlg.showDialog();

	    if (editedItem != null) {

	        items.save(editedItem) ;
	        model.fireTableDataChanged();
	        model.refreshData();
	    }
	}
	
	private class ItemTableModel extends AbstractTableModel {
		
	    private String[] columnNames = {"Item ID", "Name", "Quantity", "Unit Price", "Type"} ;
	    
	    private List<Item> rows ;
	    
	    public ItemTableModel() {
	    	refreshData();
	    }

	    public void refreshData() {
	    	this.rows = new ArrayList<Item>();
	    	
	    	for (Item item: items.findAll()) {
	    		this.rows.add(item) ;
	    	}
	    }
	    
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return rows.size();
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	    	
	    	Item item = rows.get(row) ;
	    	
	    	switch (col) {
	    		case 0: return item.getItemID();
	    		case 1: return item.getName() ;
	    		case 2: return item.getQuantity() ;
	    		case 3: return item.getUnitPrice();
	    		case 4: return item.getType();
	    	}
	    	
	    	return null ;
	    	
	    }

	    public Class getColumnClass(int col) {
	    	switch (col) {
	    		case 0: return Integer.class;
	    		case 1: return String.class ;
	    		case 2: return Integer.class ;
	    		case 3: return Float.class;
	    		case 4: return Type.class;
	    	}
	    	
	    	return null ;
    	} 
	    
	    public Item getItemAtRow(int row) {
	    	return rows.get(row);
	    }
	}
	
	
}
