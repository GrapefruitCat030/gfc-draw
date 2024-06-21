package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Graphics;

import com.google.gson.JsonObject;

public class Ellipse extends AbstractShape {
    public Ellipse(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height, "ELLIPSE");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        g.drawOval(m_x, m_y, m_width, m_height);
    }

    @Override
    public boolean contains(int x, int y) {
        int radiusX = m_width / 2;
        int radiusY = m_height / 2;
        int centerX = m_x + radiusX;
        int centerY = m_y + radiusY;
        return Math.pow(x - centerX, 2) / Math.pow(radiusX, 2) + Math.pow(y - centerY, 2) / Math.pow(radiusY, 2) <= 1;
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
    public Ellipse clone() {
        Ellipse cloned = (Ellipse) super.clone();
        return cloned;
    }

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
    }
}
