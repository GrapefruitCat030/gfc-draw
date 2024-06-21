package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class TextButton {
    JButton m_button;

    public TextButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("TEXT");
        m_button.addActionListener(e -> {
            drawPanel.setCurrShapeType("TEXT");
            drawPanel.setState(DrawPanel.State.TEXTING_SHAPE);
        });
        buttonPanel.add(m_button);
    }

}
