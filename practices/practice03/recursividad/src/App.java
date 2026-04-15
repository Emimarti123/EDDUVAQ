import java.util.Scanner;
import java.util.ArrayList;

public class App {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Subject> mySubjects = new ArrayList<Subject>();

    public static void main(String[] args) throws Exception {
        while (true) {
            showMainMenu();

            int option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    addSubject();
                    break;
                case 2:
                    listSubjects();
                    break;
                case 3:
                    addSubjectTopic();
                    break;
                case 4:
                    listAllTopics();
                    break;
                case 5:
                    System.out.println("\nProgram finished.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    break;
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("\n==================================");
        System.out.println("         SUBJECT ORGANIZER        ");
        System.out.println("==================================");
        System.out.println("1. Add Subject");
        System.out.println("2. List Subjects");
        System.out.println("3. Add Topic or Subtopic");
        System.out.println("4. Show Full Syllabus");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    public static void addSubject() {
        System.out.println("\n----------- ADD SUBJECT -----------");

        System.out.print("Enter subject code: ");
        String code = scan.nextLine();

        System.out.print("Enter subject name: ");
        String name = scan.nextLine();

        mySubjects.add(new Subject(code, name));

        System.out.println("\nSubject added successfully.");
        System.out.println("Saved subject: " + code + " - " + name);
    }

    public static void listSubjects() {
        System.out.println("\n----------- SUBJECT LIST -----------");

        if (mySubjects.size() == 0) {
            System.out.println("There are no subjects registered yet.");
            return;
        }

        int counter = 1;
        for (Subject subj : mySubjects) {
            System.out.println(counter + ". " + subj.getCode() + " - " + subj.getName());
            counter++;
        }
    }

    public static void addSubjectTopic() {
        System.out.println("\n-------- ADD TOPIC / SUBTOPIC --------");

        if (mySubjects.size() == 0) {
            System.out.println("There are no subjects yet.");
            System.out.println("You need to add a subject first.");
            return;
        }

        listSubjects();

        System.out.print("\nWrite the subject code: ");
        String code = scan.nextLine();

        Subject selected = findSubjectByCode(code);

        if (selected == null) {
            System.out.println("Subject not found.");
            return;
        }

        System.out.println("\nSelected subject: " + selected.getName());

        if (selected.getTopics().size() == 0) {
            System.out.println("This subject has no topics yet.");
            System.out.println("The first topic will be added as a main topic.");

            createMainTopic(selected);
            return;
        }

        System.out.println("\nWhat do you want to do?");
        System.out.println("1. Add main topic");
        System.out.println("2. Add subtopic");
        System.out.println("3. Cancel");
        System.out.print("Choose an option: ");

        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1:
                createMainTopic(selected);
                break;
            case 2:
                createSubtopic(selected);
                break;
            case 3:
                System.out.println("Operation cancelled.");
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public static Subject findSubjectByCode(String code) {
        for (Subject sub : mySubjects) {
            if (sub.getCode().equalsIgnoreCase(code)) {
                return sub;
            }
        }
        return null;
    }

    public static void createMainTopic(Subject selected) {
        System.out.println("\n----------- NEW MAIN TOPIC -----------");

        System.out.print("Enter topic ID: ");
        int topicID = scan.nextInt();
        scan.nextLine();

        System.out.print("Enter topic name: ");
        String topicName = scan.nextLine();

        selected.addTopic(new Topic(topicID, topicName));

        System.out.println("\nMain topic added successfully.");
        System.out.println("Added topic: " + topicName);
    }

    public static void createSubtopic(Subject selected) {
        System.out.println("\n----------- CURRENT TOPICS -----------");
        listTopics(selected.getTopics(), "");

        System.out.print("\nWrite the number of the parent topic: ");
        String selectedTopicNumber = scan.nextLine();

        boolean added = addTopic(selected.getTopics(), "", selectedTopicNumber);

        if (added) {
            System.out.println("\nSubtopic added successfully.");
        } else {
            System.out.println("\nTopic number not found.");
        }
    }

    public static void listAllTopics() {
        System.out.println("\n----------- FULL SYLLABUS -----------");

        if (mySubjects.size() == 0) {
            System.out.println("There are no subjects registered yet.");
            return;
        }

        for (Subject sub : mySubjects) {
            System.out.println("\n==================================");
            System.out.println("Subject: " + sub.getCode() + " - " + sub.getName());
            System.out.println("==================================");

            if (sub.getTopics().size() == 0) {
                System.out.println("No topics yet.");
            } else {
                listTopics(sub.getTopics(), "");
            }
        }
    }

    public static void listTopics(ArrayList<Topic> topics, String topicNumber) {
        int subTopicNumber = 0;

        for (Topic top : topics) {
            subTopicNumber++;

            String newTopicNumber = topicNumber + subTopicNumber + ".";
            System.out.println(newTopicNumber + " " + top.getName() + " (ID: " + top.getID() + ")");

            if (top.getTopics().size() > 0) {
                listTopics(top.getTopics(), newTopicNumber);
            }
        }
    }

    public static boolean addTopic(ArrayList<Topic> topics, String topicNumber, String selectedTopicNumber) {
        int subTopicNumber = 0;

        for (Topic top : topics) {
            subTopicNumber++;

            String newTopicNumber = topicNumber + subTopicNumber + ".";

            if (newTopicNumber.equals(selectedTopicNumber)) {
                System.out.print("Enter subtopic ID: ");
                int topicID = scan.nextInt();
                scan.nextLine();

                System.out.print("Enter subtopic name: ");
                String topicName = scan.nextLine();

                top.addTopic(new Topic(topicID, topicName));
                return true;
            }

            if (top.getTopics().size() > 0) {
                boolean found = addTopic(top.getTopics(), newTopicNumber, selectedTopicNumber);

                if (found) {
                    return true;
                }
            }
        }

        return false;
    }
}

class Subject {
    private String code;
    private String name;
    private ArrayList<Topic> topics;

    public Subject(String code, String name) {
        this.code = code;
        this.name = name;
        this.topics = new ArrayList<Topic>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Topic> getTopics() {
        return this.topics;
    }

    public void addTopic(Topic top) {
        this.topics.add(top);
    }
}

class Topic {
    private int ID;
    private String name;
    private ArrayList<Topic> topics;

    public Topic(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.topics = new ArrayList<Topic>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Topic> getTopics() {
        return this.topics;
    }

    public void addTopic(Topic top) {
        this.topics.add(top);
    }
}