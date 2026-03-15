import java.util.ArrayList;

public class Subject {
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