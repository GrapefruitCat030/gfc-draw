package nju.gfcat.view.components.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.MouseEvent;

import nju.gfcat.controller.AbstractCommand;
import nju.gfcat.model.shapes.AbstractShape;
import nju.gfcat.model.shapes.Text;
import nju.gfcat.controller.AddShapeCommand;
import nju.gfcat.controller.JsonFactory;
import nju.gfcat.controller.MoveShapeCommand;
import nju.gfcat.controller.RemoveShapeCommand;
import nju.gfcat.controller.ResizeCommand;
import nju.gfcat.controller.ShapeFactory;
import nju.gfcat.controller.ThemeObserver; 

public class DrawPanel extends JPanel implements ThemeObserver{
    private int m_xStart, m_yStart, m_xEnd, m_yEnd;
    private Color   m_color;
    private Font    m_font;
    private boolean m_drawing;

    private State  m_state;
    
    private String m_curr_shape_type;
    private AbstractShape m_curr_shape;

    private AbstractShape m_dragging_shape;
    private MoveShapeCommand m_move_shape_command;

    private AbstractShape m_resizing_shape;
    private ResizeCommand m_resize_command;

    private ArrayList<AbstractShape>    m_shapes;
    private ArrayList<AbstractShape>    m_selected_shapes;

    public enum State {
        IDLE, DRAWING_SHAPE, TEXTING_SHAPE, 
        SELECTING_SHAPE, COPYING_SHAPE, 
        MOVING_SHAPE, RESIZING_SHAPE
    }

    public DrawPanel() {
        m_color = Color.BLACK;
        m_font = new Font("Arial", Font.PLAIN, 12);
        m_drawing = false;
        
        this.setBackground(Color.WHITE);
        this.setBorder(new TitledBorder("Draw Panel"));

        m_shapes = new ArrayList<>();
        m_selected_shapes = new ArrayList<>();
        m_state = State.IDLE;
        
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                m_xStart = e.getX();
                m_yStart = e.getY();
               
                System.out.println("Mouse pressed, x = " + m_xStart + ", y = " + m_yStart); 
                // System.out.println("State is " + m_state);
                
                switch (m_state) {
                    case IDLE:
                        printShapes();
                        break;
                    case DRAWING_SHAPE:
                        break;
                    case TEXTING_SHAPE:
                        break;
                    case SELECTING_SHAPE:
                        for(AbstractShape shape : m_shapes) {
                            if(shape.contains(m_xStart, m_yStart)) {
                                if(m_selected_shapes.contains(shape)) {
                                    m_selected_shapes.remove(shape);
                                } else {
                                    m_selected_shapes.add(shape);
                                    // System.out.println("Add shape to selected shapes.");
                                }
                                break;
                            }
                        }
                        break;
                    case COPYING_SHAPE:
                        for(AbstractShape shape : m_shapes) {
                            if(shape.contains(m_xStart, m_yStart)) {
                                
                                // AbstractCommand command = new AddShapeCommand(
                                //     DrawPanel.this, shape.getType(), shape.getColor(), shape.getText(),
                                //     shape.getX() + 10, shape.getY() + 10, shape.getX() + 10 + shape.getWidth(), shape.getY() + 10 + shape.getHeight(), 
                                //     shape.getWidth(), shape.getHeight(),
                                //     null
                                // );
                                AbstractCommand command = new AddShapeCommand(
                                    DrawPanel.this, shape.clone()
                                );
                                shape.move(10, 10);
                                CommandHistory.getInstance().executeCommand(command);
                                // command.execute();
                                break;
                            }
                        }
                        break;
                    case MOVING_SHAPE:
                        for(AbstractShape shape : m_shapes) {
                            if(shape.contains(m_xStart, m_yStart)) {
                                m_dragging_shape = shape;
                                m_move_shape_command = new MoveShapeCommand(
                                    m_dragging_shape, m_dragging_shape.getX(), m_dragging_shape.getY()
                                );
                                break;
                            }
                        }
                        break;
                    case RESIZING_SHAPE:
                        for(AbstractShape shape : m_shapes) {
                            if(shape.contains(m_xStart, m_yStart)) {
                                m_resizing_shape = shape;
                                // 判断鼠标点击的位置是哪个角
                                // 0: top-left, 1: top-right, 2: bottom-right, 3: bottom-left
                                int resizing_quadrant = -1; 
                                double dx = m_xStart - (m_resizing_shape.getX() + m_resizing_shape.getWidth() / 2.0);
                                double dy = m_yStart - (m_resizing_shape.getY() + m_resizing_shape.getHeight() / 2.0);

                                if(dx < 0 && dy < 0) {
                                    resizing_quadrant = 0; // 左上象限
                                } else if(dx >= 0 && dy < 0) {
                                    resizing_quadrant = 1; // 右上象限
                                } else if(dx >= 0 && dy >= 0) {
                                    resizing_quadrant = 2; // 右下象限
                                } else if(dx < 0 && dy >= 0) {
                                    resizing_quadrant = 3; // 左下象限
                                }
                                
                                m_resize_command = new ResizeCommand(
                                    m_resizing_shape, 
                                    m_resizing_shape.getWidth(), m_resizing_shape.getHeight(), 
                                    m_resizing_shape.getX(), m_resizing_shape.getY(),
                                    resizing_quadrant
                                );
                                break;
                            }
                        }
                        break;
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // System.out.println("Mouse released.");
                
                switch (m_state) {
                    case IDLE:
                        // Do nothing
                        break;
                    case DRAWING_SHAPE:
                        // Start drawing a new shape
                        if(m_drawing) {
                           
                            // TODO: 或者可以直接将 curr_shape 传给 AddShapeCommand ?  
                           
                            m_xEnd = e.getX();
                            m_yEnd = e.getY();
                            
                            AbstractCommand command = new AddShapeCommand(
                                DrawPanel.this, m_curr_shape_type, m_color, null,
                                m_xStart, m_yStart, m_xEnd, m_yEnd, 
                                Math.abs(m_xEnd - m_xStart), Math.abs(m_yEnd - m_yStart),
                                null // 由于是正在绘制的图形，所以不需要传入 combine_shapes
                            );
                            CommandHistory.getInstance().executeCommand(command);
                            // command.execute();  
                        }
                        m_drawing = false;
                        break;
                    case TEXTING_SHAPE:
                        String text = JOptionPane.showInputDialog("Enter the text:");
                        
                        if(text != null) {

                            // 计算 text 的宽度和高度
                            int text_width = text.length() * 10;
                            int text_height = 10;

                            AbstractShape text_shape = ShapeFactory.createShape(
                                "TEXT", m_color, text,
                                m_xStart, m_yStart, m_xStart + text_width, m_yStart + text_height,
                                text_width, text_height,
                                null
                            );                            
                            ((Text)text_shape).setFont(m_font);

                            AbstractCommand command = new AddShapeCommand(
                                DrawPanel.this, text_shape
                            );
                            CommandHistory.getInstance().executeCommand(command);
                            // command.execute();
                        }

                        break;
                    case SELECTING_SHAPE:
                        // Do nothing
                        break;
                    case COPYING_SHAPE:
                        // Do nothing
                        break;
                    case MOVING_SHAPE:
                        m_dragging_shape = null;
                        if(m_move_shape_command != null)
                            CommandHistory.getInstance().executeCommand(m_move_shape_command);
                        m_move_shape_command = null;
                        break;
                    case RESIZING_SHAPE:
                        m_resizing_shape = null;
                        if(m_resize_command != null)
                            CommandHistory.getInstance().executeCommand(m_resize_command);
                        m_resize_command = null;
                        break;
                    }
                repaint();
            }

        });
    
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                
                // System.out.println("Mouse dragged, x = " + e.getX() + ", y = " + e.getY());

                switch (m_state) {
                    case IDLE:
                        // Do nothing
                        break;
                    case DRAWING_SHAPE:
                        // Continue drawing the current shape
                        m_drawing = true;
                        if(m_drawing) {
                            m_xEnd = e.getX();
                            m_yEnd = e.getY();
                          
                            String t = null;
                            if(m_curr_shape != null) {
                                t = m_curr_shape.getText();
                            }  
                            
                            m_curr_shape = ShapeFactory.createShape(
                                m_curr_shape_type, m_color, t,
                                m_xStart, m_yStart, m_xEnd, m_yEnd,
                                Math.abs(m_xEnd - m_xStart), Math.abs(m_yEnd - m_yStart), 
                                null // 由于是正在绘制的图形，所以不需要传入 combine_shapes
                            );
                            
                            repaint();
                        }
                        break;
                    case TEXTING_SHAPE:
                        // Do nothing
                        break;
                    case SELECTING_SHAPE:
                        // Do nothing
                        break;
                    case COPYING_SHAPE:
                        // Do nothing
                        break;
                    case MOVING_SHAPE:
                        if(m_dragging_shape != null) {
                            m_move_shape_command.setPostion(
                                e.getX() - m_dragging_shape.getX(), 
                                e.getY() - m_dragging_shape.getY()
                            );
                            m_dragging_shape.move(
                                e.getX() - m_dragging_shape.getX(), 
                                e.getY() - m_dragging_shape.getY()
                            );
                            repaint();
                        }
                        break;
                    case RESIZING_SHAPE:
                        if(m_resizing_shape != null) {
                            int dw = e.getX() - m_xStart;
                            int dh = e.getY() - m_yStart;
                            m_resize_command.setResize(
                                dw, dh
                            );    
                            repaint();
                        }
                        break;
                    }
                }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (AbstractShape shape : m_shapes) {
            if(m_selected_shapes.contains(shape)) {
                g2.setColor(Color.RED);
            } else if(m_dragging_shape == shape) {
                g2.setColor(Color.BLUE);
            } else {
                g2.setColor(shape.getColor());
            }
            shape.draw(g2);
            g2.setColor(Color.BLACK);
        }
        
        if (m_state == State.DRAWING_SHAPE && m_drawing) {
            // Draw the current shape
            m_curr_shape.draw(g2);
        }
    }

    public void addShape(AbstractShape shape) {
        m_shapes.add(shape);
    }

    public void removeShape(AbstractShape shape) {
        m_shapes.remove(shape);
    }

    public void clearShapes() {
        m_shapes.clear();
    }    

    public void loadShapes() {
        // 从shapes.json文件中读取JsonArray
        try {
            JsonElement fileElement;
            // 检查文件是否存在
            File file = new File("shapes.json");
            if (file.exists() && !file.isDirectory()) {
                fileElement = JsonParser.parseReader(new FileReader(file));
            } else {
                System.out.println("文件不存在");
                return;
            }
            
            JsonArray jsonArray = fileElement.getAsJsonArray();

            // 清空当前的m_shapes列表
            m_shapes.clear();

            // 将JsonArray中的每个JsonObject转换为具体的shape对象并添加到m_shapes中
            for (JsonElement shapeElement : jsonArray) {
                // 根据 type 字段的值创建具体的shape对象
                JsonObject shapeJson = shapeElement.getAsJsonObject();
                String typeStr = shapeJson.get("type").getAsString();
                AbstractShape shape = JsonFactory.createShape(typeStr, shapeJson);
                System.out.println("Loaded shape: " + shape.toString());
                m_shapes.add(shape);
            }
        
            repaint();
            System.out.println("Successfully loaded shapes from file.");    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    


    public void saveShapes() {
        JsonArray shapesArray = new JsonArray();
        for(AbstractShape shape : m_shapes) {
            JsonObject shapeJson = shape.toJson(); // 假设每个shape都有一个toJson()方法返回JsonObject
            shapesArray.add(shapeJson);
        }
        
        try (FileWriter file = new FileWriter("shapes.json")) { // 尝试将JsonArray保存到shapes.json文件中
            file.write(shapesArray.toString());
            System.out.println("Successfully saved JSON to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrShapeType(String shapeType) {
        m_curr_shape_type = shapeType;
        System.out.println("Current shape type is " + m_curr_shape_type);
    } 

    public void setState(State state) {
        m_state = state;
    }

    public State getState() {
        return m_state;
    }

    public void setColor(Color color) {
        m_color = color;
    }    

    public Font getFont() {
        return m_font;
    } 
    public void setFont(Font font) {
        m_font = font;
    } 

    public void deleteSelectedShapes() {
        RemoveShapeCommand command = new RemoveShapeCommand(m_shapes, m_selected_shapes);
        CommandHistory.getInstance().executeCommand(command);
        m_selected_shapes.clear();
    }

    public void setComposite() {
        if(m_selected_shapes.size() > 1) {
            AbstractCommand command = new AddShapeCommand(
                this, "COMPOSITE", Color.BLACK, null,
                0, 0, 0, 0, 0, 0, m_selected_shapes.toArray(new AbstractShape[m_selected_shapes.size()])
            );
            CommandHistory.getInstance().executeCommand(command);
            deleteSelectedShapes();
            m_selected_shapes.clear();
        }
    }    

    // 打印所有的图形
    public void printShapes() {
        System.out.println("Printing all shapes:");
        for(AbstractShape shape : m_shapes) {
            System.out.println(shape.toString());
        }
        System.out.println("End of printing all shapes.");

    }

    @Override
    public void updateTheme(String theme) {
        if(theme.equals("Light")) {
            ((TitledBorder) this.getBorder()).setTitleColor(Color.BLACK);
            this.setBackground(Color.WHITE);
            this.setColor(Color.BLACK);
            for(AbstractShape shape : m_shapes) {
                shape.setColor(Color.BLACK);
            }
            repaint();
        } else if(theme.equals("Dark")) {
            ((TitledBorder) this.getBorder()).setTitleColor(Color.WHITE);
            this.setBackground(Color.DARK_GRAY);
            this.setColor(Color.WHITE);
            for(AbstractShape shape : m_shapes) {
                shape.setColor(Color.WHITE);
            }
            repaint();
        }
    }

}