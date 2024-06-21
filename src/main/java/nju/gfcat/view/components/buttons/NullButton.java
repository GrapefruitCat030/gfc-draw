package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class NullButton {
    private JButton m_button;

    public NullButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("NULL");
        m_button.addActionListener(e -> {
            drawPanel.setState(DrawPanel.State.IDLE);
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    } 
}
