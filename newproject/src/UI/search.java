package UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.Font;

public class search extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    search frame = new search();
                    frame.setVisible(true);
            }
        });
    }

    public search() {
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(64, 128, 128));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JComboBox comboBox = new JComboBox();   //영,한 선택할 combobox
        comboBox.setBounds(24, 30, 63, 31);
        contentPane.add(comboBox);
        
        textField = new JTextField();           //검색할 단어 입력받을 textfield
        textField.setBounds(94, 31, 182, 30);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JButton btn_search = new JButton("검색");   //검색버튼
        btn_search.setFont(new Font("HY엽서M", Font.BOLD, 12));
        btn_search.setBackground(new Color(245, 249, 208));
        btn_search.setBounds(279, 30, 72, 31);
        contentPane.add(btn_search);
        
        ScrollPane scrollPane = new ScrollPane();   //단어 목록 출력하는 scrollpane
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setEnabled(false);
        scrollPane.setForeground(new Color(0, 0, 0));
        scrollPane.setBounds(24, 71, 327, 266);
        contentPane.add(scrollPane);
    }
}
