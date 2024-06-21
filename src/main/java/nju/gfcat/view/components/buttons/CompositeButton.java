package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.DrawPanel;

public class CompositeButton {
    private JButton m_button;

    public CompositeButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("Composite");
        m_button.addActionListener(e -> {
            drawPanel.setComposite();
            drawPanel.repaint();
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
