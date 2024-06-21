package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.google.gson.JsonObject;

public class Triangle extends AbstractShape {
    public Triangle(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height, "TRIANGLE");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        int[] xPoints = {m_x, m_x + m_width / 2, m_x + m_width};
        int[] yPoints = {m_y + m_height, m_y, m_y + m_height};
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean contains(int x, int y) {
        int[] xPoints = {m_x, m_x + m_width / 2, m_x + m_width};
        int[] yPoints = {m_y + m_height, m_y, m_y + m_height};
        Polygon p = new Polygon(xPoints, yPoints, 3);
        return p.contains(x, y);
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
    public Triangle clone() {
        Triangle cloned = (Triangle) super.clone();
        return cloned;
    }    

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
    }

}
