import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class Todo implements Serializable
{
    String title;
    String description;
    LocalDate startDate;
    LocalDate dueDate;
    boolean todoDone = false;

    public Todo(String title, String description, LocalDate startDate)
    {
        this(title, description, startDate, null);
    }

    public Todo(String title, String description, LocalDate startDate, LocalDate dueDate)
    {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public String getTitle()
    {
        return this.title;
    }
    public String getDescription()
    {
        return this.description;
    }
    @Override
    public String toString()
    {
        return title + "\n" + description + "\n" + todoDone;
    }
    public void setTitle(String text) {
        this.title = text;
    }
    public void setDone(boolean state)
    {
        todoDone = state;
    }
    public boolean getDone()
    {
        return todoDone;
    }
}
