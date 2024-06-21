package nju.gfcat.view;

import javax.swing.*;

import nju.gfcat.controller.ThemeController;
import nju.gfcat.view.components.bar.TopMenuBar;
import nju.gfcat.view.components.buttons.ClearButton;
import nju.gfcat.view.components.buttons.CompositeButton;
import nju.gfcat.view.components.buttons.CopyButton;
import nju.gfcat.view.components.buttons.DeleteButton;
import nju.gfcat.view.components.buttons.DragButton;
import nju.gfcat.view.components.buttons.NullButton;
import nju.gfcat.view.components.buttons.ResizeButton;
import nju.gfcat.view.components.buttons.SelectButton;
import nju.gfcat.view.components.buttons.ShapeButton;
import nju.gfcat.view.components.buttons.TextButton;
import nju.gfcat.view.components.buttons.UndoButton;
import nju.gfcat.view.components.panel.CommandHistory;
import nju.gfcat.view.components.panel.DrawPanel;
import nju.gfcat.view.components.panel.FontPanel;
import nju.gfcat.view.components.panel.ShapeButtonPanel;
import nju.gfcat.view.components.panel.UtilButtonPanel;

import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel m_drawPanel;

    private JPanel              m_leftPanel;    
    private FontPanel           m_fontPanel;
    private ShapeButtonPanel    m_shapeButtonPanel;
    private UtilButtonPanel     m_utilButtonPanel;

    private TopMenuBar m_menuBar;    

    private JScrollPane m_undoScrollPane;    

    @SuppressWarnings("unused")
    public MainWindow() {
        // 设置窗口标题和大小
        setTitle("Drawing App");
        setSize(800, 600);

        // 创建DrawPanel对象
        m_drawPanel = new DrawPanel();
        add(m_drawPanel, BorderLayout.CENTER);

        // 创建字体面板
        m_fontPanel = new FontPanel(m_drawPanel);
        
        // 创建按钮面板
        m_shapeButtonPanel = new ShapeButtonPanel();

        // 创建按钮并添加到按钮面板
        String[] shapes = {"RECTANGLE", "CIRCLE", "ELLIPSE", "TRIANGLE", "LINE"}; 
        for (String shape : shapes) {
            ShapeButton shapeButton = new ShapeButton(m_drawPanel, shape);
            m_shapeButtonPanel.add(shapeButton.getButton());
        }
        TextButton      textButton   = new TextButton(m_drawPanel, m_shapeButtonPanel);

        
        // 工具按钮面板 
        m_utilButtonPanel = new UtilButtonPanel();

        SelectButton    selectButton = new SelectButton(m_drawPanel, m_utilButtonPanel);
        CopyButton      copyButton   = new CopyButton(m_drawPanel, m_utilButtonPanel);
        DragButton      dragButton   = new DragButton(m_drawPanel, m_utilButtonPanel);
        ResizeButton    resizeButton = new ResizeButton(m_drawPanel, m_utilButtonPanel);
        NullButton      nullButton   = new NullButton(m_drawPanel, m_utilButtonPanel);
        DeleteButton    deleteButton = new DeleteButton(m_drawPanel, m_utilButtonPanel);
        ClearButton     clearButton  = new ClearButton(m_drawPanel, m_utilButtonPanel);
        UndoButton      undoButton   = new UndoButton(m_drawPanel, m_utilButtonPanel);
        CompositeButton compositeButton = new CompositeButton(m_drawPanel, m_utilButtonPanel);
        
        
        // 创建左侧面板
        m_leftPanel = new JPanel();
        m_leftPanel.setPreferredSize(new Dimension(100, m_leftPanel.getPreferredSize().height));
        
        // 创建GridBagLayout和GridBagConstraints
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        // 设置GridBagConstraints的填充方式为BOTH，这样组件会在水平和垂直方向上都填充其显示区域
        gbc.fill = GridBagConstraints.BOTH;

        // 设置GridBagConstraints的权重为1.0，这样当窗口大小改变时，组件会获取额外的显示空间
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        m_leftPanel.setLayout(gridBagLayout);
       
        // 添加组件到面板，并使用GridBagConstraints
        gbc.gridy = 0;
        m_leftPanel.add(m_fontPanel, gbc);
        gbc.gridy = 1;
        m_leftPanel.add(m_shapeButtonPanel, gbc);
        gbc.gridy = 2;
        m_leftPanel.add(m_utilButtonPanel, gbc);
        
        add(m_leftPanel, BorderLayout.WEST);
        
        // 创建撤销面板
        m_undoScrollPane = CommandHistory.getInstance().getUndoScrollPane();
        add(m_undoScrollPane, BorderLayout.EAST);       

        // 创建菜单栏
        m_menuBar = new TopMenuBar();
        ThemeController themeController = new ThemeController();
        m_menuBar.setThemeController(themeController);
        m_menuBar.setDrawPanel(m_drawPanel);
        setJMenuBar(m_menuBar);

        // 注册观察者
        themeController.addThemeObserver(m_menuBar);
        themeController.addThemeObserver(m_fontPanel);
        themeController.addThemeObserver(m_drawPanel);
        themeController.addThemeObserver(CommandHistory.getInstance());
        themeController.addThemeObserver(m_shapeButtonPanel);
        themeController.addThemeObserver(m_utilButtonPanel);

        // 设置窗口关闭行为
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}