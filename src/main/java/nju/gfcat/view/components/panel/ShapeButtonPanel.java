package nju.gfcat.view.components.panel;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import nju.gfcat.controller.ThemeObserver;

public class ShapeButtonPanel extends JPanel implements ThemeObserver{

    public ShapeButtonPanel() {
       this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       this.setBorder(new TitledBorder("Shapes"));
        
    }

    @Override
    public void updateTheme(String theme) {
        ((TitledBorder) this.getBorder()).setTitleColor(theme.equals("Light") ? Color.BLACK : Color.WHITE); 
        set_theme(this, theme);
    }    

    private void set_theme(Container container, String theme) {
        for(Component component : container.getComponents()) {
            if (theme.equals("Light")) {
                container.setBackground(Color.WHITE);
                setFontColor(component, Color.BLACK);
            } else if (theme.equals("Dark")) {
                container.setBackground(Color.DARK_GRAY);
                setFontColor(component, Color.WHITE);
            }
            
            if(component instanceof JButton) {
                ((JButton) component).setBackground(theme.equals("Light") ? Color.WHITE : Color.DARK_GRAY);
                ((JButton) component).setForeground(theme.equals("Light") ? Color.BLACK : Color.WHITE);
                // ((JButton) component).setBorderPainted(false);
                ((JButton) component).setFocusPainted(false);
                ((JButton) component).setContentAreaFilled(false);
                ((JButton) component).setOpaque(true);
            }    
        }
    }
    
    private void setFontColor(Component component, Color color) {
        component.setForeground(color);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontColor(child, color);
            }
        }
    }

}
