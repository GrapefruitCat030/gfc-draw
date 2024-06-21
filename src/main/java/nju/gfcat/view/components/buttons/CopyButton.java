package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class CopyButton {
    private JButton m_button;

    public CopyButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("COPY");
        m_button.addActionListener(e -> {
            drawPanel.setState(DrawPanel.State.COPYING_SHAPE);
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
