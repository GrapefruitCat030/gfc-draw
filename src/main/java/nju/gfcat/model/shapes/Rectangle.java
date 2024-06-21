package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Graphics;

import com.google.gson.JsonObject;

public class Rectangle extends AbstractShape {
    public Rectangle(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height, "RECTANGLE");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        g.drawRect(m_x, m_y, m_width, m_height);
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= m_x && x <= m_x + m_width && y >= m_y && y <= m_y + m_height;
    }

    @Override
    public void move(int dx, int dy) {
        m_x += dx;
        m_y += dy;
    }   
   
    @Override
    public void resize(int dw, int dh) {
        m_width += dw;
        m_height += dh;
    }
    
    @Override
    public Rectangle clone() {
        Rectangle cloned = (Rectangle) super.clone();
        return cloned;
    } 

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
    }

}
