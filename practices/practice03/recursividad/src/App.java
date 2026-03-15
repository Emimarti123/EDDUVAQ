import java.util.Scanner;
import java.util.ArrayList;

public class App {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Subject> mySubjects = new ArrayList<Subject>();

    public static void main(String[] args) throws Exception {        

        while (true) {
            System.out.println("\nOption:");
            System.out.println("1. Add Subject");
            System.out.println("2. List Subjects");
            System.out.println("3. Add Topic");
            System.out.println("4. List Topics");
            System.out.println("5. Exit");

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
                    for (Subject sub: mySubjects) {
                        System.out.println("======= " + sub.getName() + " =========");
                        listTopics(sub.getTopics(), "");
                    }
                    break;
                case 5:            
                    System.exit(0);        
                    break;            
                default:
                    break;
            }
        }

    }

   public static void addSubject() {
    System.out.println("\nEnter code:");
    String code = scan.nextLine();

    System.out.println("Enter name:");
    String name = scan.nextLine();

    mySubjects.add(new Subject(code, name));
}

    public static void listSubjects() {
        System.out.println("\nSubjects:");
        for (Subject subj: mySubjects) {
            System.out.println(subj.getCode() + " - " + subj.getName());
        }
    }

    public static void addSubjectTopic() {
        listSubjects();

        System.out.println("\nSelect subject");        
        String code = scan.next();
        scan.nextLine();

        Subject selected = null;

        for (Subject sub: mySubjects) {
            if (sub.getCode().equalsIgnoreCase(code)) {
                selected = sub;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Subject not found");
            addSubjectTopic();
            return;
        }

        selected.addTopic(new Topic(1234, "Testing 1"));
        selected.addTopic(new Topic(1235, "Testing 2"));
        selected.addTopic(new Topic(1236, "Testing 3"));
        selected.addTopic(new Topic(1237, "Testing 4"));


        Topic test = new Topic(1238, "Testing 5");
        Topic test1 = new Topic(1239, "Testing 5.1");
        Topic test2 = new Topic(1239, "Testing 5.1");
        Topic test11 = new Topic(1239, "Testing 5.1.1");
        Topic test12 = new Topic(1239, "Testing 5.1.1");

        test1.addTopic(test11);
        test1.addTopic(test12);

        test.addTopic(test1);
        test.addTopic(test2);

        selected.addTopic(test);

        System.out.println("Select main topic:\n");

        String topicNumber = "";
        listTopics(selected.getTopics(), topicNumber);

        String selectedTopicNumber = scan.next();
        scan.nextLine();
        
        addTopic(selected.getTopics(), topicNumber, selectedTopicNumber);
    }

    public static void listTopics(ArrayList<Topic> topics, String topicNumber) {

        int subTopicNumber = 0;
        for (Topic top: topics) {
            subTopicNumber++;
        
            String newTopicNumber = topicNumber + subTopicNumber + ".";
            System.out.println(newTopicNumber + " - " + top.getID() + " - " + top.getName());

            if (top.geTopics().size() > 0 ) {
                listTopics(top.geTopics(), newTopicNumber);
            }
        }
    }

    public static void addTopic(ArrayList<Topic> topics, String topicNumber, String selectedTopicNumber) {
        int subTopicNumber = 0;
        for (Topic top: topics) {
            subTopicNumber++;
        
            String newTopicNumber = topicNumber + subTopicNumber + ".";

            if (newTopicNumber.equals(selectedTopicNumber)) {
                System.out.println("Enter ID:");
                int topicID = scan.nextInt();
                scan.nextLine(); 

                System.out.println("Enter name:");
                String topicName = scan.nextLine();            

                top.addTopic(new Topic(topicID, topicName));
                return;
            }

            if (top.geTopics().size() > 0 ) {
                addTopic(top.geTopics(), topicNumber, selectedTopicNumber);
            }
        }
    }
}