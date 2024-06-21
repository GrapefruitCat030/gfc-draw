package nju.gfcat.controller;

import nju.gfcat.model.shapes.AbstractShape;

public class MoveShapeCommand implements AbstractCommand {
    private int m_beginX;
    private int m_beginY;

    private int m_dx;
    private int m_dy;
    private AbstractShape m_shape;

    public MoveShapeCommand(AbstractShape shape, int beginX, int beginY) {
        m_shape = shape;
        m_beginX = beginX;
        m_beginY = beginY;
        System.out.println("MoveShapeCommand created. the shape is " + shape.toString());
    }

    public void setPostion(int dx, int dy) {
        m_dx = dx;
        m_dy = dy;
    } 

    @Override
    public void execute() {
        m_shape.move(m_dx, m_dy);
    } 

    @Override
    public void undo() {
        m_shape.move(m_beginX - m_shape.getX(), m_beginY - m_shape.getY());
    } 
}
