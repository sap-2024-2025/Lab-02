package clean.infrastructure;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class MyGUIView extends JFrame {

    private JButton updateButton;
    private JTextField  valueField;
    private MyGUIController controller;

    public MyGUIView(MyGUIController controller) {
        super(".:: Count View ::.");
    	setSize(250, 60);
        
        this.controller = controller;

        valueField = new JTextField(3);
        valueField.setEditable(false);
        
        updateButton = new JButton("update");
    	
        JPanel panel = new JPanel();
        var layout = new BoxLayout(panel, BoxLayout.X_AXIS);  
        panel.setLayout(layout);       
        panel.add(new JLabel("Count value: "));
        panel.add(valueField);
        panel.add(updateButton);

        getContentPane().add(panel);    
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	controller.notifyUpdateRequest();
            }
        });
    }
    
    public void reset() {
    	controller.notifyQueryRequest();
    }
    
    public void updateValue(int value) {
    	SwingUtilities.invokeLater(() -> {
    		valueField.setText(""+value);
    	});
    }
    
    public void display() {
    	SwingUtilities.invokeLater(() -> {
    		this.setVisible(true);
    	});
    }

}
