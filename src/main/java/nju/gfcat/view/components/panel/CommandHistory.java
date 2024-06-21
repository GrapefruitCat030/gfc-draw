package nju.gfcat.view.components.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import nju.gfcat.controller.AbstractCommand;
import nju.gfcat.controller.ThemeObserver;

public class CommandHistory implements ThemeObserver{
    private static CommandHistory instance = null;
    private Stack<AbstractCommand> undoStack;

    // 可视化部分
    DefaultListModel<String> listModel;
    JList<String> undoList;
    JScrollPane undoScrollPane;


    private CommandHistory() {
        undoStack = new Stack<>();
        
        listModel = new DefaultListModel<>();
        undoList = new JList<>(listModel);
        undoScrollPane = new JScrollPane(undoList);
        undoScrollPane.setPreferredSize(new Dimension(200, 200)); // 设置宽度为300，高度为200
        // 添加标题
        undoScrollPane.setBorder(new TitledBorder("Command History"));
    }

    public static CommandHistory getInstance() {
        if (instance == null) {
            instance = new CommandHistory();
        }
        return instance;
    }

    public JScrollPane getUndoScrollPane() {
        return undoScrollPane;
    }    


    public void executeCommand(AbstractCommand command) {
        command.execute();
        undoStack.push(command);
        listModel.addElement(command.toString());
    }

    public void undoCommand() {
        if (!undoStack.isEmpty()) {
            AbstractCommand command = undoStack.pop();
            command.undo();
            // redoStack.push(command);
            listModel.removeElementAt(listModel.size() - 1);
        }
    }
    
    @Override
    public void updateTheme(String theme) {
        ((TitledBorder) undoScrollPane.getBorder()).setTitleColor(theme.equals("Light") ? Color.BLACK : Color.WHITE); 
        set_theme(undoScrollPane, theme);
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