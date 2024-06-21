package nju.gfcat.controller;

import nju.gfcat.model.shapes.AbstractShape;
import nju.gfcat.model.shapes.CompositeShape;
import nju.gfcat.view.components.panel.DrawPanel;

import java.awt.Color;

public class CompositeCommand implements AbstractCommand {
    private DrawPanel   m_drawPanel;
    private AbstractShape[] m_selected_shapes;

    private AbstractShape m_shape; 

    public CompositeCommand(DrawPanel drawPanel, AbstractShape[] shapes) {
        m_drawPanel = drawPanel;
        m_selected_shapes = shapes;
        m_shape = ShapeFactory.createShape("COMPOSITE", Color.BLACK, "", 0, 0, 0, 0, 0, 0, m_selected_shapes);
    }
    
    @Override
    public void execute() {
        System.out.println("Executing CompositeCommand");
        m_drawPanel.addShape(m_shape);
    }

    @Override
    public void undo() {
        System.out.println("Undoing CompositeCommand");
        
        AbstractShape[] shapes = ((CompositeShape)m_shape).getShapes();
        
        for(AbstractShape s : shapes) {
            m_drawPanel.addShape(s);
        }  
            
        m_drawPanel.removeShape(m_shape);
    } 

}

