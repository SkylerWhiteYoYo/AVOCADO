package UI;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import newproject.*;

public class Main extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    IdFunction idf = new IdFunction();
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    Main frame = new Main();
                    frame.setVisible(true);
            }});
    }
    
    public Main() {
        setSize(550,350);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 64, 64), 4));
        contentPane.setBackground(new Color(215, 236, 213));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 64, 0), 5));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(12, 10, 512, 293);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lb = new JLabel("A!VOCA.DO");
        lb.setBounds(115, 75, 408, 82);
        panel.add(lb);
        lb.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 50));
        
        JLabel lb_ = new JLabel("");
        lb_.setBounds(174, 30, 134, 151);
        panel.add(lb_);
        lb_.setIcon(new ImageIcon("src/image/avocado.png"));
        
        JLabel lb_id = new JLabel("ID");
        lb_id.setBounds(128, 180, 50, 40);
        panel.add(lb_id);
        lb_id.setFont(new Font("굴림", Font.BOLD, 25));
        
        textField = new JTextField();             //id입력받을 textfield
        textField.addActionListener(new MyActionListener());
        textField.setBounds(161, 190, 210, 21);
        panel.add(textField);
        textField.setColumns(10);
  
    }

    class MyActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		String id = textField.getText();
    		
    		if(id.equals(""))
    			JOptionPane.showMessageDialog(null,"다시 입력하세요", "Message",JOptionPane.ERROR_MESSAGE); 
    		
    		boolean hasid = idf.hasId(id);
    		
    		System.out.println(hasid);
    		
    		if(hasid == false) {
    				int result = JOptionPane.showConfirmDialog(null,"없는 아이디입니다 생성 하시겠습니까?", "아이디 생성",JOptionPane.YES_NO_OPTION);
    				if(result == JOptionPane.YES_OPTION) {
    					String nickname = JOptionPane.showInputDialog("nickname을 입력하세요.");
    					idf.userInsert(id, nickname);
    				}
		
    		}else JOptionPane.showMessageDialog(null,"접속 성공", "Message",JOptionPane.INFORMATION_MESSAGE);
    		
    		dispose();
    		
    		new Main2();
    	}
    }
}

