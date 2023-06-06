package UI;

import javax.management.loading.PrivateClassLoader;
import newproject.CurrentUser;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import newproject.MysqlConnection;

public class FriendNoteDialog extends JDialog {
    private MysqlConnection connection = new MysqlConnection();
    private String userId;
    private String friend;
    private String note;

    public FriendNoteDialog(String userId, String friend, String note) {
        this.userId = userId;
        this.friend = friend;
        this.note = note;

        setTitle(friend + "'s note: " + note);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        String[] columnNames = {"단어", "뜻"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(new Color(255, 255, 255));
        table.setFillsViewportHeight(true);

        // Fetch word list from the note
        HashMap<String, String> words = connection.wordList(note);
        for (Map.Entry<String, String> entry : words.entrySet()) {
            String word = entry.getKey();
            String meaning = entry.getValue();
            model.addRow(new Object[]{word, meaning});
        }
        if (words.isEmpty()) {
            System.out.println("No words fetched from the friend's note.");
        }
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(0xa0c040));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        tablePanel.add(table);

        JButton btnAddFavorite = new JButton("즐겨찾기에 추가");
        c.add(btnAddFavorite, BorderLayout.SOUTH);
        btnAddFavorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {  // 행이 선택되었는지 확인
                    String word = (String) model.getValueAt(selectedRow, 0);  //단어
                    String meaning = (String) model.getValueAt(selectedRow, 1);  //뜻

                    connection.bookMarkInsert(userId, word, meaning);
                    JOptionPane.showMessageDialog(null, "즐겨찾기에 추가되었습니다.");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        c.add(scrollPane, BorderLayout.CENTER);
    }
}
