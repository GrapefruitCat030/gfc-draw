package nju.gfcat.view.components.buttons;
import javax.swing.*;

import nju.gfcat.view.components.panel.DrawPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeButton {
    private JButton m_button;

    public ShapeButton(DrawPanel drawPanel, String shapeType) {
        m_button = new JButton(shapeType);
        m_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setCurrShapeType(shapeType);
                drawPanel.setState(DrawPanel.State.DRAWING_SHAPE);
            }
        });
    }

    public JButton getButton() {
        return m_button;
    }
}