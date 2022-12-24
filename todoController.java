import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableArrayList;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class todoController {

    ObservableList<Todo> todoList;
    UI ui;

    public todoController(UI ui)
    {
        this.ui = ui;
        loadTodo();
    }

    public ObservableList<Todo> getTodoList()
    {
        return todoList;
    }

    public void saveTodo()
    {
        try (FileOutputStream fileOut = new FileOutputStream("./todos.txt");
             ObjectOutputStream out = new ObjectOutputStream(fileOut))

        {
            out.writeObject(new ArrayList<Todo>(todoList));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTodo()
    {
        List<Todo> todos = null;
        //todo Versionskonvertierung für neuere Versionen
        try (FileInputStream fileIn = new FileInputStream("./todos.txt");
             ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            todos = (List) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(todos == null) todos = new ArrayList<Todo>();
        todoList = observableArrayList(todos);
        todoList.addListener((ListChangeListener<? super Todo>) e->ui.updateView());
    }

    public void deleteTodo(Todo todo)
    {
        if(todo == null) return;
        todoList.remove(todo);
    }

    public void addTodo(Todo todo)
    {
        if(todo == null) return;
        todoList.add(todo);
    }
}
