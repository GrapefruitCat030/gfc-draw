package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.google.gson.JsonObject;

public class Line extends AbstractShape {
    Line2D m_line;

    public Line(Color color, int x1, int y1, int x2, int y2) {
        // 与Rectangle类似，这里也是先计算出左上角和右下角的坐标
        super(color, x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1), "LINE");
        m_line = new Line2D.Double(x1, y1, x2, y2);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        ((Graphics2D) g).draw(m_line);
    }

    @Override
    public boolean contains(int x, int y) {
        return m_line.ptSegDist(x, y) < 5;
    } 

    @Override
    public void move(int dx, int dy) {
        m_line.setLine(m_line.getX1() + dx, m_line.getY1() + dy, m_line.getX2() + dx, m_line.getY2() + dy);
        m_x = (int) m_line.getX1();
        m_y = (int) m_line.getY1();
    }     

    @Override
    public void resize(int dw, int dh) {
        // do nothing
    }    

    @Override
    public Line clone() {
        Line cloned = (Line) super.clone();
        cloned.m_line = (Line2D) m_line.clone();
        return cloned;
    }

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
        jsonObj.addProperty("x2", m_line.getX2());
        jsonObj.addProperty("y2", m_line.getY2());
    }
}
