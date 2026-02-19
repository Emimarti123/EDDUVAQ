package practices.practice02;

import javax.swing.*;
import java.awt.*;

public class VisualPart extends JFrame {

    private JTextField titleField;
    private JTextArea detailsArea;
    private JTextField dateField;

    private DefaultListModel<String> pendingModel;
    private DefaultListModel<String> completedModel;

    private JList<String> pendingList;
    private JList<String> completedList;

    private JButton addNormalTaskButton;
    private JButton addUrgentTaskButton;
    private JButton searchButton;
    private JButton showAllButton;
    private JButton completeButton;
    private JButton deleteButton;
    private JButton showDetailsButton;

    private LinkedTaskList pendingTasks;
    private LinkedTaskList completedTasks;

    public VisualPart() {

        setTitle("Second Practice - To Do List");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        pendingTasks = new LinkedTaskList();
        completedTasks = new LinkedTaskList();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2));

        JLabel titleLabel = new JLabel("Task Title");
        titleField = new JTextField();

        JLabel detailsLabel = new JLabel("Task Details");
        detailsArea = new JTextArea();

        JLabel dateLabel = new JLabel("Date / Time");
        dateField = new JTextField();

        JScrollPane detailsScroll = new JScrollPane(detailsArea);

        topPanel.add(titleLabel);
        topPanel.add(titleField);
        topPanel.add(detailsLabel);
        topPanel.add(detailsScroll);
        topPanel.add(dateLabel);
        topPanel.add(dateField);

        addNormalTaskButton = new JButton("Add Normal Task");
        addUrgentTaskButton = new JButton("Add Urgent Task");

        topPanel.add(addNormalTaskButton);
        topPanel.add(addUrgentTaskButton);

        searchButton = new JButton("Search Task");
        showAllButton = new JButton("Show All Tasks");

        topPanel.add(searchButton);
        topPanel.add(showAllButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        pendingModel = new DefaultListModel<>();
        pendingList = new JList<>(pendingModel);

        completedModel = new DefaultListModel<>();
        completedList = new JList<>(completedModel);

        JPanel pendingPanel = new JPanel(new BorderLayout());
        pendingPanel.add(new JLabel("Pending Tasks"), BorderLayout.NORTH);
        pendingPanel.add(new JScrollPane(pendingList), BorderLayout.CENTER);

        JPanel completedPanel = new JPanel(new BorderLayout());
        completedPanel.add(new JLabel("Completed Tasks"), BorderLayout.NORTH);
        completedPanel.add(new JScrollPane(completedList), BorderLayout.CENTER);

        centerPanel.add(pendingPanel);
        centerPanel.add(completedPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        completeButton = new JButton("Mark as Completed");
        deleteButton = new JButton("Delete Task");
        showDetailsButton = new JButton("Show Task Details");

        bottomPanel.add(completeButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(showDetailsButton);

        add(bottomPanel, BorderLayout.SOUTH);

        addNormalTaskButton.addActionListener(e -> {
            String t = titleField.getText();
            String d = detailsArea.getText();
            String dt = dateField.getText();

            if (!t.isEmpty()) {
                Task task = new Task(t, d, dt);
                pendingTasks.addLast(task);
                pendingTasks.fillListModel(pendingModel);
                clearFields();
            }
        });

        addUrgentTaskButton.addActionListener(e -> {
            String t = titleField.getText();
            String d = detailsArea.getText();
            String dt = dateField.getText();

            if (!t.isEmpty()) {
                Task task = new Task(t, d, dt);
                pendingTasks.addFirst(task);
                pendingTasks.fillListModel(pendingModel);
                clearFields();
            }
        });

        deleteButton.addActionListener(e -> {
            String sel = pendingList.getSelectedValue();
            if (sel != null) {
                pendingTasks.deleteByTitle(sel);
                pendingTasks.fillListModel(pendingModel);
            }
        });

        completeButton.addActionListener(e -> {
            String sel = pendingList.getSelectedValue();
            if (sel != null) {
                pendingTasks.moveToCompleted(sel, completedTasks);
                pendingTasks.fillListModel(pendingModel);
                completedTasks.fillListModel(completedModel);
            }
        });

        searchButton.addActionListener(e -> {
            String text = JOptionPane.showInputDialog(this, "Enter text to search:");
            if (text != null && !text.trim().isEmpty()) {
                pendingTasks.searchInList(text, pendingModel);
            }
        });

        showAllButton.addActionListener(e -> {
            pendingTasks.fillListModel(pendingModel);
        });

        showDetailsButton.addActionListener(e -> {

            String sel = pendingList.getSelectedValue();

            if (sel != null) {
                Task t = pendingTasks.getTaskByDisplay(sel);
                if (t != null) {
                    JOptionPane.showMessageDialog(this,
                            "Title: " + t.getTitle() +
                                    "\nDetails: " + t.getDetails() +
                                    "\nDate/Time: " + t.getDateTime());
                }
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        titleField.setText("");
        detailsArea.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        new VisualPart();
    }
}
