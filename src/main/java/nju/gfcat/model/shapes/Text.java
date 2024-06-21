package nju.gfcat.model.shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.google.gson.JsonObject;

public class Text extends AbstractShape {
    private String text;
    private Font font;

    public Text(String text, Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height, "TEXT");
        this.text = text;
        this.font = new Font("Arial", Font.PLAIN, Math.min(width, height));
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }    


    @Override
    public void draw(Graphics g) {
        g.setColor(m_color);
        g.setFont(font);
        g.drawString(text, m_x + 5, m_y + 10); // draw the text
        // g.drawRect(m_x, m_y, m_width, m_height); // draw a rectangle around the text
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
    public Text clone() {
        Text cloned = (Text) super.clone();
        cloned.text = text;
        cloned.font = font;
        return cloned;
    }

    @Override
    public void resize(int dw, int dh) {
        m_width += dw;
        m_height += dh;
        font.deriveFont(Math.min(m_width, m_height));
    }

    @Override
    protected void addSubclassSpecificAttributes(JsonObject jsonObj) {
        jsonObj.addProperty("text", text);
        jsonObj.addProperty("font", font.toString());
    }

}