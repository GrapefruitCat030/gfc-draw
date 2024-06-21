package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Graphics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CompositeShape extends AbstractShape {
    private AbstractShape[] m_shapes;

    public CompositeShape(Color color, AbstractShape[] shapes) {
        super(color, 0, 0, 0, 0, "COMPOSITE");
        m_shapes = shapes;

        // 需要计算出CompositeShape的x, y, width, height 
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (AbstractShape shape : m_shapes) {
            if (shape.getX() < minX) {
                minX = shape.getX();
            }
            if (shape.getY() < minY) {
                minY = shape.getY();
            }
            if (shape.getX() + shape.getWidth() > maxX) {
                maxX = shape.getX() + shape.getWidth();
            }
            if (shape.getY() + shape.getHeight() > maxY) {
                maxY = shape.getY() + shape.getHeight();
            }
        }

        m_x = minX;
        m_y = minY;
        m_width = maxX - minX;
        m_height = maxY - minY;
        m_color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        for (AbstractShape shape : m_shapes) {
            shape.draw(g);
        }
        // 绘制CompositeShape的边框
        // g.drawRect(m_x, m_y, m_width, m_height);
    }

    @Override
    public boolean contains(int x, int y) {
        for (AbstractShape shape : m_shapes) {
            if (shape.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(int dx, int dy) {
        m_x += dx;
        m_y += dy;
        for (AbstractShape shape : m_shapes) {
            shape.move(dx, dy);
        }
    }    

    @Override
    public void resize(int dw, int dh) {
        m_width += dw;
        m_height += dh;
        for (AbstractShape shape : m_shapes) {
            shape.resize(dw, dh);
        }
    }    

    @Override
    public CompositeShape clone() {
        AbstractShape[] newShapes = new AbstractShape[m_shapes.length];
        for (int i = 0; i < m_shapes.length; i++) {
            newShapes[i] = m_shapes[i].clone();
        }
        CompositeShape cloned = new CompositeShape(m_color, newShapes);
        return cloned;
    } 

    public AbstractShape[] getShapes() {
        return m_shapes;
    }    

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
        JsonArray shapesArray = new JsonArray();
        for (AbstractShape shape : m_shapes) {
            JsonObject shapeJson = shape.toJson(); // 假设每个shape都有一个toJson()方法返回JsonObject
            shapesArray.add(shapeJson);
        }
        jsonObj.add("shapes", shapesArray); // 将shapes列表作为一个字段加入到jsonObject中
    }    

}
