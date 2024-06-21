package nju.gfcat.view.components.buttons;

import javax.swing.JButton;
import javax.swing.JPanel;

import nju.gfcat.view.components.panel.CommandHistory;
import nju.gfcat.view.components.panel.DrawPanel;

public class UndoButton {
    private JButton m_button;

    public UndoButton(DrawPanel drawPanel, JPanel buttonPanel) {
        m_button = new JButton("UNDO");
        m_button.addActionListener(e -> {
            CommandHistory.getInstance().undoCommand();
            drawPanel.repaint();
        });
        buttonPanel.add(m_button);
    }

    public JButton getButton() {
        return m_button;
    }
}
