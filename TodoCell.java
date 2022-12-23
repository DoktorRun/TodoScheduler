import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TodoCell extends ListCell<Todo>
{
    Todo todo;
    todoController todoCon;
    public TodoCell(todoController todoCon)
    {
        todo = null;
        this.todoCon = todoCon;
    }

    @Override
    public void updateItem(Todo todo, boolean empty)
    {
        super.updateItem(todo, empty);
        if (empty || todo == null)
        {
            setText(null);
            setGraphic(null);
            return;
        }
        buildCell(todo);
    }

    private void buildCell(Todo todo) {
        BorderPane container = new BorderPane();

        Label titleTodo = new Label(todo.getTitle());

        TextField descriptionTodo = new TextField();
        descriptionTodo.setDisable(true);
        descriptionTodo.setText(todo.description);
        descriptionTodo.setMaxSize(900,250);

        descriptionTodo.setOnAction(e->
        {
            todo.setDescription(descriptionTodo.getText());
            descriptionTodo.setDisable(true);
        });

        container.setOnMouseClicked(e->
        {
            if(e.getClickCount() == 2)
            {
                descriptionTodo.setDisable(!descriptionTodo.isDisable());
            }
            else if(e.isShiftDown() && e.getClickCount() == 1)
            {
                todoCon.deleteTodo(todo);
            }
        });

        CheckBox doneCb = new CheckBox();
        doneCb.setSelected(todo.getDone());
        doneCb.setOnAction(e -> todo.setDone(!todo.getDone()));

        container.setTop(titleTodo);
        container.setCenter(descriptionTodo);
        container.setRight(doneCb);
        container.setStyle("-fx-focus-color: transparent;");

        BorderPane.setAlignment(titleTodo,Pos.CENTER);
        BorderPane.setMargin(titleTodo, new Insets(6,0,6,0));

        BorderPane.setAlignment(doneCb, Pos.CENTER_LEFT);

        container.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.FULL)));
        setGraphic(container);
    }
};