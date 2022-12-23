import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class todoController {

    ObservableList<Todo> todoList;

    public todoController()
    {
        todoList = loadTodo();
    }

    public ObservableList<Todo> getTodoList()
    {
        return todoList;
    }

    public void saveTodo(ObservableList list)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("./todos.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new ArrayList(list));
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList loadTodo()
    {
        try {
            FileInputStream fileIn = new FileInputStream("./todos.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List todos = (List) in.readObject();
            ObservableList todoData = FXCollections.observableArrayList(todos);
            in.close();
            fileIn.close();
            return todoData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList();
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
