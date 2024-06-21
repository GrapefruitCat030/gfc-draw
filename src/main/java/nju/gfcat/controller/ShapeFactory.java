package nju.gfcat.controller;

import java.awt.Color;

import nju.gfcat.model.shapes.AbstractShape;
import nju.gfcat.model.shapes.Circle;
import nju.gfcat.model.shapes.CompositeShape;
import nju.gfcat.model.shapes.Ellipse;
import nju.gfcat.model.shapes.Line;
import nju.gfcat.model.shapes.Rectangle;
import nju.gfcat.model.shapes.Text;
import nju.gfcat.model.shapes.Triangle;

public class ShapeFactory {
    public static AbstractShape createShape(String shapeType, 
                                            Color color, String text,
                                            int x1, int y1, int x2, int y2,
                                            int width, int height, AbstractShape[] combine_shapes) {
        if (shapeType == null) {
            return null;
        }
        
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2); 

        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("TRIANGLE")) {
            return new Triangle(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle(color, x, y, width);
        } else if (shapeType.equalsIgnoreCase("LINE")) {
            return new Line(color, x1, y1, x2, y2); 
        } else if (shapeType.equalsIgnoreCase("ELLIPSE")) {
            return new Ellipse(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("TEXT")) {
            return new Text(text, color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("COMPOSITE")) {
            // ATTENTION: combine_shapes is not null, 
            // and the x, y, width, height are determined by the combine_shapes
            return new CompositeShape(color, combine_shapes);
        }
        return null;
    }
}