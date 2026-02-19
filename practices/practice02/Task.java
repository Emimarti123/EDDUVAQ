package practices.practice02;

public class Task {

    private String title;
    private String details;
    private String dateTime;
    private boolean completed;

    public Task(String title, String details, String dateTime) {
        this.title = title;
        this.details = details;
        this.dateTime = dateTime;
        this.completed = false;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return title + " - " + dateTime;
    }
}
