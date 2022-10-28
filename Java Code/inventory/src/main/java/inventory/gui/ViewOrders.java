package inventory.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import inventory.model.Item;
import inventory.model.NpOrder;
import inventory.model.NpOrderLine;
import inventory.model.PeOrder;
import inventory.repo.ItemRepo;
import inventory.repo.NpOrderLineRepo;
import inventory.repo.NpOrderRepo;
import inventory.repo.PeOrderRepo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.persistence.NonUniqueResultException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewOrders extends JFrame {
	
	private PeOrderRepo peOrders ;
	private PeOrderTableModel model1 ;
	private PeOrder selectedPeOrder ;
	
	private NpOrderRepo npOrders ;
	private NpOrderTableModel model2 ;
	private NpOrder selectedNpOrder ;
	
	private NpOrderLineRepo npOrderLines;

	private JPanel contentPane;
	private JTable tblPeOrders;
	private JTable tblNpOrders;

	/**
	 * Create the frame.
	 */
	
	public ViewOrders(PeOrderRepo peOrders, NpOrderRepo npOrders, NpOrderLineRepo npOrderLines) {
		
		this.peOrders = peOrders;
		this.model1 = new PeOrderTableModel() ;
		
		this.npOrders = npOrders;
		this.model2 = new NpOrderTableModel() ;
		
		this.npOrderLines = npOrderLines;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPe = new JScrollPane();
		
		JLabel lblTitle = new JLabel("All Orders");
		
		JLabel lblPe = new JLabel("Perishable Orders");
		
		JLabel lblNp = new JLabel("Non-Perishable Orders");
		
		JScrollPane scrollNp = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addComponent(lblPe)
						.addComponent(scrollPe, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
						.addComponent(lblNp)
						.addComponent(scrollNp, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPe, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNp)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollNp, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		tblPeOrders = new JTable(model1);
		tblPeOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {

	        	int row = tblPeOrders.getSelectedRow();
	        	
	        	if (row >= 0)
	        		selectedPeOrder = model1.getPeOrderAtRow(row) ;
	        	else
	        		selectedPeOrder = null ;
	        }
	    });
		scrollPe.setViewportView(tblPeOrders);
		tblPeOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable_ClickMouseClicked();
			}

			private void JTable_ClickMouseClicked() {
				// TODO Auto-generated method stub
				int index = tblPeOrders.getSelectedRow();
				TableModel pemodel = tblPeOrders.getModel();
				String fileName = pemodel.getValueAt(index, 3).toString();
				String orderDate = pemodel.getValueAt(index, 2).toString();
				
				Path file = FileSystems.getDefault().getPath("", fileName);
				
				List<String> lines = null;
				try {
					lines = Files.readAllLines(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String out = "";
				for (String line: lines) {
	                out+= "\n" + line;
				}
				
				JOptionPane.showMessageDialog(null, "Perishable Order for " + orderDate + "\nIndex     Name     Quantity" + out);
			}
		});
		
		tblNpOrders = new JTable(model2);
		tblNpOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {

	        	int row = tblNpOrders.getSelectedRow();
	        	
	        	if (row >= 0)
	        		selectedNpOrder = model2.getNpOrderAtRow(row) ;
	        	else
	        		selectedNpOrder = null ;

	        }
	    });
		scrollNp.setViewportView(tblNpOrders);
		tblNpOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable_ClickMouseClicked();
			}

			private void JTable_ClickMouseClicked() {
				// TODO Auto-generated method stub
				int index = tblNpOrders.getSelectedRow();
				TableModel npmodel = tblNpOrders.getModel();
				Integer orderID = Integer.parseInt(npmodel.getValueAt(index, 0).toString());
				String orderDate = npmodel.getValueAt(index, 2).toString();
				
				List<NpOrderLine> npOrderLine = npOrderLines.findByOrderID(orderID);
				
				String out2 = "";
				for (NpOrderLine orderLine: npOrderLine) {
	                out2+= "\n" + orderLine;
				}
				
				try {
					JOptionPane.showMessageDialog(null, "Non-Perishable Order for " + orderDate + "\nIndex OrderID ItemID Name Quantity" + out2);
				} catch (NonUniqueResultException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.setLayout(gl_contentPane);
	}

	private class PeOrderTableModel extends AbstractTableModel {
		
	    private String[] columnNames = {"Order ID", "Supplier ID", "Date", "File Name"} ;
	    
	    private List<PeOrder> rows ;
	    
	    public PeOrderTableModel() {
	    	refreshData();
	    }

	    public void refreshData() {
	    	this.rows = new ArrayList<PeOrder>();
	    	
	    	for (PeOrder peOrder: peOrders.findAll()) {
	    		this.rows.add(peOrder) ;
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
	    	
	    	PeOrder peOrder = rows.get(row) ;
	    	
	    	switch (col) {
	    		case 0: return peOrder.getOrderID();
	    		case 1: return peOrder.getSupplierID() ;
	    		case 2: return peOrder.getDate() ;
	    		case 3: return peOrder.getOrderFile();
	    	}
	    	
	    	return null ;
	    	
	    }

	    public Class getColumnClass(int col) {
	    	switch (col) {
	    		case 0: return Integer.class;
	    		case 1: return Integer.class ;
	    		case 2: return LocalDate.class ;
	    		case 3: return File.class;
	    	}
	    	
	    	return null ;
    	} 
	    
	    public PeOrder getPeOrderAtRow(int row) {
	    	return rows.get(row);
	    }
	}
	

	private class NpOrderTableModel extends AbstractTableModel {
		
	    private String[] columnNames = {"Order ID", "Supplier ID", "Date"} ;
	    
	    private List<NpOrder> rows ;
	    
	    public NpOrderTableModel() {
	    	refreshData();
	    }

	    public void refreshData() {
	    	this.rows = new ArrayList<NpOrder>();
	    	
	    	for (NpOrder npOrder: npOrders.findAll()) {
	    		this.rows.add(npOrder) ;
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
	    	
	    	NpOrder npOrder = rows.get(row) ;
	    	
	    	switch (col) {
	    		case 0: return npOrder.getOrderID();
	    		case 1: return npOrder.getSupplierID() ;
	    		case 2: return npOrder.getDate() ;
	    	}
	    	
	    	return null ;
	    	
	    }

	    public Class getColumnClass(int col) {
	    	switch (col) {
	    		case 0: return Integer.class;
	    		case 1: return Integer.class ;
	    		case 2: return LocalDate.class ;
	    	}
	    	
	    	return null ;
    	} 
	    
	    public NpOrder getNpOrderAtRow(int row) {
	    	return rows.get(row);
	    }
	}
	

}
