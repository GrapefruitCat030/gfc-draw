package nju.gfcat.controller;

import java.awt.Color;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import nju.gfcat.model.shapes.AbstractShape;
import nju.gfcat.model.shapes.Circle;
import nju.gfcat.model.shapes.CompositeShape;
import nju.gfcat.model.shapes.Ellipse;
import nju.gfcat.model.shapes.Line;
import nju.gfcat.model.shapes.Rectangle;
import nju.gfcat.model.shapes.Text;
import nju.gfcat.model.shapes.Triangle;

public class JsonFactory {
    public static AbstractShape createShape(String shapeType, JsonObject jsonObj) {
        if (shapeType == null) {
            return null;
        }

        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x = jsonObj.get("x").getAsInt();
            int y = jsonObj.get("y").getAsInt();
            int width = jsonObj.get("width").getAsInt();
            int height = jsonObj.get("height").getAsInt();
            return new Rectangle(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("TRIANGLE")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x = jsonObj.get("x").getAsInt();
            int y = jsonObj.get("y").getAsInt();
            int width = jsonObj.get("width").getAsInt();
            int height = jsonObj.get("height").getAsInt();
            return new Triangle(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x = jsonObj.get("x").getAsInt();
            int y = jsonObj.get("y").getAsInt();
            int width = jsonObj.get("width").getAsInt();
            return new Circle(color, x, y, width);
        } else if (shapeType.equalsIgnoreCase("LINE")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x1 = jsonObj.get("x1").getAsInt();
            int y1 = jsonObj.get("y1").getAsInt();
            int x2 = jsonObj.get("x2").getAsInt();
            int y2 = jsonObj.get("y2").getAsInt();
            return new Line(color, x1, y1, x2, y2); 
        } else if (shapeType.equalsIgnoreCase("ELLIPSE")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x = jsonObj.get("x").getAsInt();
            int y = jsonObj.get("y").getAsInt();
            int width = jsonObj.get("width").getAsInt();
            int height = jsonObj.get("height").getAsInt();
            return new Ellipse(color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("TEXT")) {
            Color color = Color.decode(jsonObj.get("color").getAsString());
            int x = jsonObj.get("x").getAsInt();
            int y = jsonObj.get("y").getAsInt();
            int width = jsonObj.get("width").getAsInt();
            int height = jsonObj.get("height").getAsInt();
            String text = jsonObj.get("text").getAsString();
            return new Text(text, color, x, y, width, height);
        } else if (shapeType.equalsIgnoreCase("COMPOSITE")) {
            JsonArray shapesJsonArray = jsonObj.get("shapes").getAsJsonArray();
            AbstractShape[] combine_shapes = new AbstractShape[shapesJsonArray.size()];
            for (int i = 0; i < shapesJsonArray.size(); i++) {
                JsonObject shapeJsonObj = shapesJsonArray.get(i).getAsJsonObject();
                String type = shapeJsonObj.get("type").getAsString();
                combine_shapes[i] = createShape(type, shapeJsonObj);
            }
            Color color = Color.decode(jsonObj.get("color").getAsString());
            CompositeShape compositeShape = new CompositeShape(color, combine_shapes);
            return compositeShape;
        }

        return null;
    }
}