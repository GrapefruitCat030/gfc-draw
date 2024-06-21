package nju.gfcat.view.components.bar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import nju.gfcat.controller.ThemeController;
import nju.gfcat.controller.ThemeObserver;
import nju.gfcat.view.components.panel.DrawPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;


public class TopMenuBar extends JMenuBar implements ThemeObserver{
    private ThemeController m_themeController;
    private DrawPanel m_drawPanel;

    public TopMenuBar() {
        super();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        this.add(fileMenu);
    
        loadItem.addActionListener(e -> {
            load_file();
        }); 

        saveItem.addActionListener(e -> {
            save_file();
        }); 

        
        exitItem.addActionListener(e -> {
            
        });

        JMenu themeMenu = new JMenu("Theme"); 
        String[] skins = {"Light", "Dark"};
        for (String skin : skins) {
            JMenuItem skinItem = new JMenuItem(skin);
            skinItem.addActionListener(e -> m_themeController.setTheme(skin));
            themeMenu.add(skinItem);
        }
        this.add(themeMenu);
    }
 
    public void setThemeController(ThemeController themeController) {
        m_themeController = themeController;
    } 
    public void setDrawPanel(DrawPanel drawPanel) {
        m_drawPanel = drawPanel;
    }
    
    private void load_file() {
        m_drawPanel.loadShapes();
    }  
   
    private void save_file() {
        m_drawPanel.saveShapes(); 
    } 
  
    @Override
    public void updateTheme(String theme) {
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