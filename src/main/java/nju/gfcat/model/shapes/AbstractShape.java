package nju.gfcat.model.shapes;

import java.awt.*;

import com.google.gson.JsonObject;


public abstract class AbstractShape implements Cloneable {
    protected Color m_color;  // 形状的颜色
    protected int m_x;        // 形状的左上角x坐标
    protected int m_y;        // 形状的左上角y坐标
    protected int m_width;    // 形状的宽度
    protected int m_height;   // 形状的高度
    protected String m_type;  // 形状的类型

    public AbstractShape(Color color, int x, int y, int width, int height, String type) { 
        m_color = color; 
        m_x = x;
        m_y = y;
        m_width = width;
        m_height = height;
        m_type = type;
    }
    
    public abstract void draw(Graphics g);
    public abstract boolean contains(int x, int y);
    public abstract void move(int dx, int dy);
    public abstract void resize(int dw, int dh); 
    
    @Override
    public AbstractShape clone() {
        try {
            return (AbstractShape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // 由于实现了Cloneable，这个异常不应该发生
        }
    }
    
    public void     setColor(Color color)   { m_color = color; }
    public void     setX(int x)             { m_x = x; }
    public void     setY(int y)             { m_y = y; }
    public void     setWidth(int width)     { m_width = width; }
    public void     setHeight(int height)   { m_height = height; }
    
    public Color    getColor()              { return m_color; }
    public int      getX()                  { return m_x; }
    public int      getY()                  { return m_y; }
    public int      getWidth()              { return m_width; }
    public int      getHeight()             { return m_height; }
    public String   getType()               { return m_type; }
    public String   getText()               { return null;}

    public JsonObject toJson() {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("color", m_color.getRGB()); 
        jsonObj.addProperty("x", m_x);
        jsonObj.addProperty("y", m_y);
        jsonObj.addProperty("width", m_width);
        jsonObj.addProperty("height", m_height);
        jsonObj.addProperty("type", m_type);
        
        // 添加额外的逻辑来处理子类特有的字段
        addSubclassSpecificAttributes(jsonObj);
        
        return jsonObj;
    }

    // 添加额外的逻辑来处理子类特有的字段
    protected abstract void addSubclassSpecificAttributes(JsonObject jsonObj);
}
