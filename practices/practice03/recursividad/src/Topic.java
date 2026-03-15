import java.util.ArrayList;

public class Topic {
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

  public void setID(int iD) {
    ID = iD;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Topic> geTopics() {
    return this.topics;
  }

  public void addTopic(Topic top) {
    this.topics.add(top);
  }
}