package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class DeleteButton {
    private JButton m_button;

    public DeleteButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("DELETE");
        m_button.addActionListener(e -> {
            drawPanel.deleteSelectedShapes();
            drawPanel.repaint();
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
