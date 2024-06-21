package nju.gfcat.controller;

import nju.gfcat.model.shapes.AbstractShape;

public class ResizeCommand implements AbstractCommand {
    private int m_beginWidth;
    private int m_beginHeight;
    private int m_beginX;
    private int m_beginY;

    // 判断鼠标点击的位置是哪个角
    // 0: top-left, 1: top-right, 2: bottom-right, 3: bottom-left
    private int m_resizing_corner; 

    private int m_dw;
    private int m_dh;
    private int m_dx;
    private int m_dy;
    private AbstractShape m_shape;
    
    public ResizeCommand(AbstractShape shape, int beginWidth, int beginHeight, int beginX, int beginY, int resize_corner) {
        m_shape = shape;
        m_beginWidth = beginWidth;
        m_beginHeight = beginHeight;
        m_beginX = beginX;
        m_beginY = beginY;
        m_resizing_corner = resize_corner;
        System.out.println("ResizeCommand created. the shape is " + shape.toString());
    }

    public void setResize(int dw, int dh) {
        int old_mdw = m_dw;
        int old_mdh = m_dh;
        int old_mdx = m_dx;
        int old_mdy = m_dy;

        switch (m_resizing_corner) {
            case 0:
                m_dw = -dw;
                m_dh = -dh;
                m_dx = dw;
                m_dy = dh;
                break;
            case 1:
                m_dw = dw;
                m_dh = -dh;
                m_dx = 0;
                m_dy = dh;
                break;
            case 2:
                m_dw = dw;
                m_dh = dh;
                m_dx = 0;
                m_dy = 0;
                break;
            case 3:
                m_dw = -dw;
                m_dh = dh;
                m_dx = dw;
                m_dy = 0;
                break;
            default:
                break;
        }     
 
        m_shape.move(m_dx - old_mdx, m_dy - old_mdy); 
        m_shape.resize(m_dw - old_mdw, m_dh - old_mdh);
        System.out.println("dw = " + dw + "dh = " + dh + "cornet = " + m_resizing_corner);
    }

    @Override
    public void execute() {
        System.out.println("ResizeCommand execute"
            + " m_dw: " + m_dw
            + " m_dh: " + m_dh
            + " m_dx: " + m_dx
            + " m_dy: " + m_dy);
        // m_shape.resize(m_dw, m_dh);
        // m_shape.move(m_dx, m_dy);
   
        int old_mdw = m_dw;
        int old_mdh = m_dh;
        int old_mdx = m_dx;
        int old_mdy = m_dy;
        
        m_shape.move(m_dx - old_mdx, m_dy - old_mdy); 
        m_shape.resize(m_dw - old_mdw, m_dh - old_mdh);
    } 

    @Override
    public void undo() {
        m_shape.resize(m_beginWidth - m_shape.getWidth(), m_beginHeight - m_shape.getHeight());
        m_shape.move(m_beginX - m_shape.getX(), m_beginY - m_shape.getY());
    } 
}