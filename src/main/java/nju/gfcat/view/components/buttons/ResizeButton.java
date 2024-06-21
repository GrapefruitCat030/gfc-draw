package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class ResizeButton {
    private JButton m_button;

    public ResizeButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("Resize");
        m_button.addActionListener(e -> {
            drawPanel.setState(DrawPanel.State.RESIZING_SHAPE);
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
