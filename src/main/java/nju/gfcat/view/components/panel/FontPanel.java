package nju.gfcat.view.components.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import nju.gfcat.controller.ThemeObserver;

public class FontPanel extends JPanel implements ThemeObserver{
    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> sizeComboBox;
    private DrawPanel m_drawPanel;

    public FontPanel(DrawPanel drawPanel) {
        this.setBorder(new TitledBorder("Font"));

        // 调整panel大小
        this.setSize(100, 100);        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        m_drawPanel = drawPanel;

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        // 设置 box 大小
        fontComboBox.setMaximumSize(fontComboBox.getPreferredSize());
        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFont = (String) fontComboBox.getSelectedItem();
                Font currentFont = m_drawPanel.getFont();
                m_drawPanel.setFont(new Font(selectedFont, currentFont.getStyle(), currentFont.getSize()));
            }
        });

        Integer[] sizes = {10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setMaximumSize(sizeComboBox.getPreferredSize());
        sizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedSize = (Integer) sizeComboBox.getSelectedItem();
                Font currentFont = m_drawPanel.getFont();
                m_drawPanel.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), selectedSize));
            }
        });


        add(fontComboBox);
        add(sizeComboBox);
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
                container.setForeground(Color.BLACK);
                setFontColor(component, Color.BLACK);
            } else if (theme.equals("Dark")) {
                container.setBackground(Color.DARK_GRAY);
                container.setForeground(Color.WHITE);
                setFontColor(component, Color.WHITE);
            }
        
            if(component instanceof Container) {
                set_theme((Container) component, theme);
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
