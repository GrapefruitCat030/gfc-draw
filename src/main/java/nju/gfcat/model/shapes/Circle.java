package nju.gfcat.model.shapes;

import java.awt.*;

import com.google.gson.JsonObject;

public class Circle extends AbstractShape {
    public Circle(Color color, int x, int y, int diameter) {
        super(color, x, y, diameter, diameter, "CIRCLE");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        g.drawOval(m_x, m_y, m_width, m_height); // ATTENTION: m_height == m_width
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = m_width / 2;
        int centerX = m_x + radius;
        int centerY = m_y + radius;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }

    @Override
    public void move(int dx, int dy) {
        m_x += dx;
        m_y += dy;
    }    

    @Override
    public void resize(int dw, int dh) {
        int dd = Math.min(dw, dh);
        m_width += dd;
        m_height += dd;
    }

    @Override
    public Circle clone() {
        Circle cloned = (Circle) super.clone();
        return cloned;
    }

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
    }
}