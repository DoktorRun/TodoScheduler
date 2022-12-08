import com.sun.nio.sctp.SctpMultiChannel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UI extends Application {
    Stage primaryStage;
    Scene mainScene;
    Scene creationScene;
    ObservableList<Todo> todoList;

    public void init()
    {
        todoList = loadTodo();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e->saveTodo(todoList));
        primaryStage.setTitle("To-Do's");

        mainScene = initMainScene();
        creationScene = initCreationScene();

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public Scene initMainScene()
    {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25,25,25,25));

        Button todoCreateButton = new Button();
        todoCreateButton.setText("+");
        todoCreateButton.setOnAction(e-> primaryStage.setScene(creationScene));
        root.add(todoCreateButton,1,0,2,1);

        ListView<Todo> todoListView = new ListView<Todo>(todoList);
        todoListView.setCellFactory(listView -> new TodoCell());
        root.add(todoListView,0,0);
        return new Scene(root, 1080, 720);
    }
    //todo Main Scene / Creation Scene in eigene Klassen auslagern
    private Scene initCreationScene()
    {
        GridPane creationScreen = new GridPane();
        creationScreen.setAlignment(Pos.CENTER);
        creationScreen.setHgap(10);
        creationScreen.setVgap(10);
        creationScreen.setPadding(new Insets(25,25,25,25));

        Text sceneTitle = new Text("Todo Creation: ");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label todoTitle = new Label("Todo title: ");
        TextField todoTitleValue = new TextField();
        Label todoDescription = new Label("Todo Description");
        TextField todoDescriptionValue = new TextField();

        todoTitleValue.setOnAction(e -> todoDescriptionValue.requestFocus());
        todoDescriptionValue.setOnAction(e -> extractTodoData(todoTitleValue, todoDescriptionValue));
        //todo Verschiedene Sets an Methoden erkennen und demnach auslagern wie oben genannt

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        creationScreen.add(sceneTitle,0,0,2,1);
        creationScreen.add(todoTitle,0,1);
        creationScreen.add(todoTitleValue,1,1);
        creationScreen.add(todoDescriptionValue, 1,2);
        creationScreen.add(todoDescription,0,2);
        creationScreen.add(backButton,0,3);

        GridPane smallCal = new SmallCalender();
        creationScreen.add(smallCal,0,4);

        return new Scene(creationScreen, 1080, 720);
        //todo CRUD = Delete operations
        //todo serialisation
    }

    private void extractTodoData(TextField todoTitleValue, TextField todoDescriptionValue) {
        String title = todoTitleValue.getText();
        String description = todoDescriptionValue.getText();
        LocalDate startDate = LocalDate.now();
        Todo todo = new Todo(title, description, startDate);
        todoList.add(todo);
        System.out.println(todo);
    }

    private void saveTodo(ObservableList list)
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

    private ObservableList loadTodo()
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
}