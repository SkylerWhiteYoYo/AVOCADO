package UI;


import javax.management.loading.PrivateClassLoader;
import newproject.CurrentUser;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import newproject.MysqlConnectionBookmark;

public class bookmark extends JFrame {

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    bookmark frame = new bookmark();
                    frame.setVisible(true);
            }
        });
    }

    public bookmark() {// 북마크 생성자
        setTitle("즐겨찾기");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        String userId = CurrentUser.getInstance().getUserId(); // 로그인 아이디 불러오기
        
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);//즐겨찾기 탭
        tabbedPane.setBackground(new Color(0xe0e080));
        JPanel panel1=new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(new Color(0xa0c040));
        tabbedPane.add("즐겨찾기",panel1);
        

        //즐겨찾기 탭 테이블
        String[] columnNamesFavorite = {"단어", "의미"}; 
        DefaultTableModel modelFavorite = new DefaultTableModel(columnNamesFavorite, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;//셀 수정 불가
        }
    };
    	
        JTable tableFavorite = new JTable(modelFavorite);
        tableFavorite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //셀 하나씩만 선택 가능
        tableFavorite.setBackground(new Color(255, 255, 255));
        tableFavorite.setFillsViewportHeight(true); // JTable이 스크롤 팬의 높이를 모두 채우도록 설정

    
        MysqlConnectionBookmark connectionFavorite = new MysqlConnectionBookmark();
       
        HashMap<String, String> bookmarkedWords = connectionFavorite.bookMarkList(userId);
        for (Map.Entry<String, String> entry : bookmarkedWords.entrySet()) {
            String word = entry.getKey();
            String meaning = entry.getValue();
            modelFavorite.addRow(new Object[]{word, meaning});
        }
        if (bookmarkedWords.isEmpty()) {
            System.out.println("즐겨찾기에서 단어를 못 가져왔어요");
        }
        
     // 테이블을 패널에 추가하고 스크롤 팬 설정
        JPanel tablePanelFavorite = new JPanel(new BorderLayout());
        tablePanelFavorite.setBackground(new Color(0xa0c040));
        tablePanelFavorite.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        tablePanelFavorite.add(tableFavorite);
        JScrollPane scrollPaneFavorite = new JScrollPane(tablePanelFavorite);        // 스크롤 부착
        panel1.add(scrollPaneFavorite, BorderLayout.CENTER);
        
        
     // 즐겨찾기 삭제 버튼 추가
        JButton btnDeleteFavorite = new JButton("즐겨찾기 삭제");
        panel1.add(btnDeleteFavorite, BorderLayout.SOUTH);
        btnDeleteFavorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableFavorite.getSelectedRow();
                if (selectedRow >= 0) {  // 행이 선택되었다면
                    String word = (String) modelFavorite.getValueAt(selectedRow, 0);  // "단어" 칼럼

                    MysqlConnectionBookmark connection = new MysqlConnectionBookmark();
                    connection.bookMarkDelete(userId, word);

                    // 즐겨찾기 테이블에서 항목 삭제
                    modelFavorite.removeRow(selectedRow);
                }
            }
        });

 
        
        // 기본 단어장탭
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBackground(new Color(0xa0c040));
        tabbedPane.add("단어목록", panel2);
        c.add(tabbedPane, BorderLayout.CENTER);

        String[] columnNames = {"단어", "의미"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableWordList = new JTable(model);
        tableWordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableWordList.setBackground(new Color(255, 255, 255));
        tableWordList.setFillsViewportHeight(true);
 
      
        MysqlConnectionBookmark connection = new MysqlConnectionBookmark();
        HashMap<String, String> words = connection.basicVocaList();
        for (Map.Entry<String, String> entry : words.entrySet()) {
            String word = entry.getKey();
            String meaning = entry.getValue();
            model.addRow(new Object[]{word, meaning});
        }
        if (words.isEmpty()) {
            System.out.println("즐겨찾기에서 단어를 못 가져왔어요");
        }
        
        JPanel tablePanel2 = new JPanel(new BorderLayout());
        tablePanel2.setBackground(new Color(0xa0c040));
        tablePanel2.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        tablePanel2.add(tableWordList);

        JButton btnAddFavorite = new JButton("즐겨찾기에 추가");
        panel2.add(btnAddFavorite, BorderLayout.SOUTH);
        btnAddFavorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableWordList.getSelectedRow();
                if (selectedRow >= 0) {  // 행이 선택되었는지 확인
                    String word = (String) model.getValueAt(selectedRow, 0);  // "단어" 칼럼
                    String meaning = (String) model.getValueAt(selectedRow, 1);  // "의미" 칼럼

                    MysqlConnectionBookmark connection = new MysqlConnectionBookmark();
                    connection.bookMarkInsert(userId, word, meaning);

                    // 즐겨찾기 테이블에 새로운 항목 추가
                    modelFavorite.addRow(new Object[]{word, meaning});
                }
            }
        });
        
        
        JScrollPane scrollPane2 = new JScrollPane(tablePanel2);
        panel2.add(scrollPane2, BorderLayout.CENTER);
        
        //즐겨찾기 탭 누를때 마다 다시 불러오기
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 0) {  // 즐겨찾기 탭이 선택된 경우
                    // 새로고침
                    modelFavorite.setRowCount(0);      
                    MysqlConnectionBookmark connectionFavorite = new MysqlConnectionBookmark();
                    HashMap<String, String> bookmarkedWords = connectionFavorite.bookMarkList(userId);
                    for (Map.Entry<String, String> entry : bookmarkedWords.entrySet()) {
                        String word = entry.getKey();
                        String meaning = entry.getValue();
                        modelFavorite.addRow(new Object[]{word, meaning});
                    }
                    if (bookmarkedWords.isEmpty()) {
                        System.out.println("즐겨찾기에서 단어를 못 가져왔어요");
                    }
                }
            }
        });
        
        
     // 세 번째 탭
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setBackground(new Color(0xa0c040));  // 색상
        tabbedPane.add("단어장 목록", panel3);  // 탭 이름

        String[] columnNames3 = {"nickname", "notename"};  // 칼럼 이름
        DefaultTableModel model3 = new DefaultTableModel(columnNames3, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table3 = new JTable(model3);
        table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table3.setBackground(new Color(255, 255, 255));
        table3.setFillsViewportHeight(true);



        JPanel tablePanel3 = new JPanel(new BorderLayout());
        tablePanel3.setBackground(new Color(0xa0c040));  // 색상
        tablePanel3.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        tablePanel3.add(table3);

        JButton btn3 = new JButton("단어장 접속");  // 버튼 
        //단어장 접속버튼 클릭시 창을 띄운다.
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table3.getSelectedRow();
                if (selectedRow >= 0) {  // 행이 선택되었는지 확인
                    String friend = (String) model3.getValueAt(selectedRow, 0);  // "nickname" 칼럼
                    String note = (String) model3.getValueAt(selectedRow, 1);  // "notename" 칼럼

                    // 단어장 보기 창을 생성 및 보여주기
                    FriendNoteDialog dialog = new FriendNoteDialog(userId, friend, note);
                    dialog.setVisible(true);
                }
            }
        });

        panel3.add(btn3, BorderLayout.SOUTH);
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 0) {  // "즐겨찾기" 탭이 선택된 경우
                    // ... (기존의 즐겨찾기 단어 목록을 불러오는 코드)
                } else if (tabbedPane.getSelectedIndex() == 2) {  // 단어장 목록 탭이 선택된 경우
                    model3.setRowCount(0);  // 모든 행 제거
                    
                    // 사용자의 노트 목록을 가져와서 모델에 추가
                    MysqlConnectionBookmark connectionUser = new MysqlConnectionBookmark();
                    String[] userNotes = connectionUser.noteList(userId);
                    for (String note : userNotes) {
                        model3.addRow(new Object[]{userId, note});
                    }

                    // 친구 목록 데이터를 불러와서 친구의 단어장 목록을 모델에 추가
                    MysqlConnectionBookmark connectionFriends = new MysqlConnectionBookmark();
                    String[] friends = connectionFriends.friendList(userId);
                    for (String friend : friends) {
                        String[] friendNotes = connectionFriends.noteList(friend);
                        for (String note : friendNotes) {
                            model3.addRow(new Object[]{friend, note});
                        }
                    }
                }
            }
        });
        // 버튼 액션 리스너

        JScrollPane scrollPane3 = new JScrollPane(tablePanel3);
        panel3.add(scrollPane3, BorderLayout.CENTER);
        

        

        c.add(tabbedPane);
        setSize(400, 400);
    }
}
