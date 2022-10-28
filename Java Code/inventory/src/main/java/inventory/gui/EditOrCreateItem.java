package inventory.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.ui.Model;

import inventory.model.Item;
import inventory.model.type.*;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class EditOrCreateItem extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private Item item;
	
	private JTextField txtItemID;
	private JTextField txtName;
	private JTextField txtUnitPrice;
	private JTextField txtQuantity;
	private JButton okButton;
	private JButton cancelButton;	
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditOrCreateItem dialog = new EditOrCreateItem(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditOrCreateItem(Item item) {
		
		this.item = item ;
		
		this.setModal(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Please provide the details of the item");

		JLabel lblItemID = new JLabel("ItemID");
		txtItemID = new JFormattedTextField(new DecimalFormat("####"));
		txtItemID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				okButton.setEnabled(inputComplete());
			}
		});
		txtItemID.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		
		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				okButton.setEnabled(inputComplete());
			}
		});
		txtName.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		
		txtQuantity = new JFormattedTextField(new DecimalFormat("#####"));
		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				okButton.setEnabled(inputComplete());
			}
		});
		txtQuantity.setColumns(10);
		
		
		JLabel lblUnitPrice = new JLabel("Unit Price");
		
		txtUnitPrice = new JFormattedTextField(new DecimalFormat("###.##"));
		txtUnitPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				okButton.setEnabled(inputComplete());
			}
		});
		txtUnitPrice.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		
		txtType = new JTextField();
		txtType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				okButton.setEnabled(inputComplete());
			}
		});
		txtType.setColumns(10);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblItemID)
							.addGap(36)
							.addComponent(txtItemID, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblName)
							.addGap(42)
							.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblQuantity)
							.addGap(24)
							.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUnitPrice)
								.addComponent(lblType))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtType, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addComponent(txtUnitPrice))))
					.addGap(129))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(6)
					.addComponent(lblNewLabel)
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblItemID))
						.addComponent(txtItemID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblName))
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblQuantity))
						.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUnitPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUnitPrice))
					.addGap(13)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(txtType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						createItem();
						close();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		if (item != null) {
			txtItemID.setText(item.getItemID().toString());
			txtName.setText(item.getName());
			txtQuantity.setText(item.getQuantity().toString());
			txtUnitPrice.setText(item.getUnitPrice().toString());
			txtType.setText(item.getType().toString());
			
			
		}
	}
	
	private boolean inputComplete() {
		
		if (txtItemID.getText().isEmpty() || !txtItemID.isValid())
			return false ;
		
		if (txtName.getText().isEmpty())
			return false ;
		
		if (txtQuantity.getText().isEmpty())
			return false ;
		
		if (txtUnitPrice.getText().isEmpty())
			return false ;
		
		if (txtType.getText().isEmpty())
			return false ;
		
		return true ;
	}

	
	private void createItem() {
	    this.item = new Item(
	        Integer.valueOf(txtItemID.getText()),
	        txtName.getText(),
	        Integer.valueOf(txtQuantity.getText()),
	        Float.valueOf(txtUnitPrice.getText()),
	        ItemType.valueOf(txtType.getText())
	    );
	}
	
	private void close() {
	    this.setVisible(false);
	    this.dispose();
	}
	
	public Item showDialog() {
	    setVisible(true);
	    return item;
	}
}
