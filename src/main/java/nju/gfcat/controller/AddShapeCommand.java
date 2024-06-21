package nju.gfcat.controller;

import nju.gfcat.model.shapes.AbstractShape;
import nju.gfcat.view.components.panel.DrawPanel;

import java.awt.Color;

public class AddShapeCommand implements AbstractCommand {
    private DrawPanel   m_drawPanel;
    private AbstractShape m_shape;

    public AddShapeCommand(DrawPanel drawPanel, String shapeType, 
                            Color color, String text,
                            int x1, int y1, int x2, int y2,
                            int width, int height, AbstractShape[] shapes) {
        m_drawPanel = drawPanel;
        m_shape = ShapeFactory.createShape(shapeType, color, text, x1, y1, x2, y2, width, height, shapes);
    }

    public AddShapeCommand(DrawPanel drawPanel, AbstractShape shape) {
        m_drawPanel = drawPanel;
        m_shape = shape;
    }    

    @Override
    public void execute() {
        System.out.println("Executing AddShapeCommand");
        m_drawPanel.addShape(m_shape);
    }

    @Override
    public void undo() {
        System.out.println("Undoing AddShapeCommand");
        m_drawPanel.removeShape(m_shape);
    }

}
