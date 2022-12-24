import javafx.application.Application;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class UI extends Application {
    Stage primaryStage;
    Scene mainScene;
    Scene creationScene;
    todoController todoCon;
    ChoiceBox filterChoiceBox;
    ListView<Todo> todoListView;

    public void init()
    {
        this.todoCon = new todoController(this);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e->todoCon.saveTodo());
        primaryStage.setTitle("To-Do's");

        mainScene = initMainScene();
        creationScene = initCreationScene();

        primaryStage.setScene(mainScene);
        primaryStage.show();
        updateView();
    }

    public Scene initMainScene()
    {
        BorderPane mainContainer = new BorderPane();

        ArrayList<String> filterItems = new ArrayList<String>();
        filterItems.add("None");
        filterItems.add("Deadline");
        filterItems.add("Done");
        filterItems.add("Open");
        filterChoiceBox = new ChoiceBox();
        filterChoiceBox.setValue("None");
        ObservableList<String> filterMethods = observableArrayList(filterItems);
        filterChoiceBox.setItems(filterMethods);
        mainContainer.setTop(filterChoiceBox);
        BorderPane.setAlignment(filterChoiceBox,Pos.CENTER);
        BorderPane.setMargin(filterChoiceBox,new Insets(15,0,0,15));

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25,25,25,25));

        Button todoCreateButton = new Button();
        todoCreateButton.setText("+");
        todoCreateButton.setOnAction(e-> primaryStage.setScene(creationScene));
        root.add(todoCreateButton,1,0,2,1);

        filterChoiceBox.setOnAction(e-> updateView());
        todoListView = new ListView<Todo>();
        todoListView.setCellFactory(listView -> new TodoCell(todoCon));
        todoListView.setMinSize(980,500);
        root.add(todoListView,0,0);
        mainContainer.setCenter(root);
        return new Scene(mainContainer, 1080, 720);
    }

    public void updateView()
    {
        String choice = (String) filterChoiceBox.getValue();
        List<Todo> filteredTodos;
        switch(choice)
        {
            case "Done": filteredTodos = todoCon.getTodoList().stream().
                    filter(todo -> todo.isDone()).collect(Collectors.toList()); break;
            case "Deadline": //todo implement deadline filter
            case "None":
            default: filteredTodos = todoCon.getTodoList();
        }

        todoListView.setItems(observableArrayList(filteredTodos));
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
    }

    private void extractTodoData(TextField todoTitleValue, TextField todoDescriptionValue) {
        String title = todoTitleValue.getText();
        String description = todoDescriptionValue.getText();
        LocalDate startDate = LocalDate.now();
        Todo todo = new Todo(title, description, startDate);
        todoCon.addTodo(todo);
        System.out.println(todo);
    }
}