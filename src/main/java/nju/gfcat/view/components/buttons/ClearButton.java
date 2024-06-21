package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class ClearButton {
    private JButton m_button;

    public ClearButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("CLEAR");
        m_button.addActionListener(e -> {
            drawPanel.clearShapes();
            drawPanel.repaint();
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
