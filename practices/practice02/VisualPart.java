package practices.practice02;
import javax.swing.*;
import java.awt.*;
public class VisualPart extends JFrame {
    public VisualPart() {

        setTitle("Second Practice, To Do List");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2));
        
        JLabel titleLabel = new JLabel("Task Title");
        JTextField titleField = new JTextField();

        JLabel detailsLabel = new JLabel("Details");
        JTextArea detailsArea = new JTextArea();

        JLabel dateLabel = new JLabel("Date/Time");
        JTextField dateField = new JTextField();    
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        topPanel.add(titleLabel);
        topPanel.add(titleField);

        topPanel.add(detailsLabel);
        topPanel.add(detailsScroll);

        topPanel.add(dateLabel);
        topPanel.add(dateField);

        JButton addNormalTaskButton = new JButton("Add Normal Task");
        JButton addUrgentTaskButton = new JButton("Add Urgent Task");

        topPanel.add(addNormalTaskButton);
        topPanel.add(addUrgentTaskButton);

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField();

        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");

        topPanel.add(searchLabel);
        topPanel.add(searchField);

        topPanel.add(searchButton);
        topPanel.add(showAllButton);


        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2 ));

        JLabel pendingLabel = new JLabel("Pending Tasks");

        DefaultListModel<String> pendingModel = new DefaultListModel<>();
        JList<String> pendingList = new JList<>(pendingModel);

        JScrollPane pendingScroll = new JScrollPane(pendingList);

        JPanel pendingPanel = new JPanel();
        pendingPanel.setLayout(new BorderLayout());

        pendingPanel.add(pendingLabel, BorderLayout.NORTH);
        pendingPanel.add(pendingScroll, BorderLayout.CENTER);

        JPanel completedPanel = new JPanel();
        completedPanel.setLayout(new BorderLayout());

        JLabel completedLabel = new JLabel("Completed Tasks");

        DefaultListModel<String> completedModel = new DefaultListModel<>();
        JList<String> completedList = new JList<>(completedModel);

        JScrollPane completedScroll = new JScrollPane(completedList);

        completedPanel.add(completedLabel, BorderLayout.NORTH);
        completedPanel.add(completedScroll, BorderLayout.CENTER);

        centerPanel.add(pendingPanel);
        centerPanel.add(completedPanel); 
        
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton completeButton = new JButton("Complete");
        JButton deleteButton = new JButton("Delete");

        bottomPanel.add(completeButton);
        bottomPanel.add(deleteButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

    } 
        public static void main(String[] args) {

        VisualPart window = new VisualPart();
        window.setVisible(true);

    }}

