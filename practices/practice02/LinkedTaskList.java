package practices.practice02;

import javax.swing.DefaultListModel;

public class LinkedTaskList {

    private Node head;

    public LinkedTaskList() {
        head = null;
    }

    public void addFirst(Task task) {
        Node newNode = new Node(task);
        newNode.next = head;
        head = newNode;
    }

    public void addLast(Task task) {

        Node newNode = new Node(task);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public boolean deleteByTitle(String title) {

        if (head == null) {
            return false;
        }

        if (head.data.toString().toLowerCase().contains(title.toLowerCase())) {
            head = head.next;
            return true;
        }

        Node current = head;

        while (current.next != null) {

            if (current.next.data.toString().toLowerCase().contains(title.toLowerCase())) {
                current.next = current.next.next;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public boolean moveToCompleted(String title, LinkedTaskList completedList) {

        if (head == null) {
            return false;
        }

        if (head.data.toString().toLowerCase().contains(title.toLowerCase())) {

            Task completedTask = head.data;
            completedTask.setCompleted(true);

            head = head.next;
            completedList.addLast(completedTask);
            return true;
        }

        Node current = head;

        while (current.next != null) {

            if (current.next.data.toString().toLowerCase().contains(title.toLowerCase())) {

                Task completedTask = current.next.data;
                completedTask.setCompleted(true);

                current.next = current.next.next;
                completedList.addLast(completedTask);
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public void fillListModel(DefaultListModel<String> model) {

        model.clear();

        Node current = head;

        while (current != null) {
            model.addElement(current.data.toString());
            current = current.next;
        }
    }

    public void searchInList(String text, DefaultListModel<String> model) {

        model.clear();

        Node current = head;

        while (current != null) {

            if (current.data.toString().toLowerCase().contains(text.toLowerCase())) {
                model.addElement(current.data.toString());
            }

            current = current.next;
        }
    }

    public Task getTaskByDisplay(String displayText) {

        Node current = head;

        while (current != null) {

            if (current.data.toString().equals(displayText)) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }
}
