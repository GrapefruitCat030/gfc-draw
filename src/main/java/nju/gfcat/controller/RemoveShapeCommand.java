package nju.gfcat.controller;

import nju.gfcat.model.shapes.AbstractShape;

import java.util.ArrayList;
import java.util.List;

public class RemoveShapeCommand implements AbstractCommand {
    private List<AbstractShape> total_shapes;
    private List<AbstractShape> selected_shapes;
    private List<AbstractShape> removed_shapes;

    public RemoveShapeCommand(List<AbstractShape> total_shapes, List<AbstractShape> selected_shapes) {
        this.total_shapes = total_shapes;
        this.selected_shapes = selected_shapes;
        this.removed_shapes = new ArrayList<>(); 
    }

    @Override
    public void execute() {
        for (AbstractShape shape : selected_shapes) {
            removed_shapes.add(shape);
            total_shapes.remove(shape);
        }
    }

    @Override
    public void undo() {
        for (AbstractShape shape : removed_shapes) {
            total_shapes.add(shape);
        }
    }
}
