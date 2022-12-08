import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TodoCell extends ListCell<Todo>
{
    Todo todo;
    public TodoCell()
    {
        todo = null;
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
        CheckBox doneCb = new CheckBox();
        doneCb.setSelected(todo.getDone());
        doneCb.setOnAction(e->
        {
            todo.setDone(doneCb.selectedProperty().get());
            System.out.println(todo);
        });
        setGraphic(doneCb);
        setText(todo.getTitle() + "\n" + todo.getDescription());
        setOnMouseClicked(e->{
            //edit the displayed To-Do
            if(e.getClickCount() == 2)
            {
                setText(null);
                TextField editingField = new TextField();
                editingField.setOnAction(f->{
                    todo.setTitle(editingField.getText());
                    setGraphic(doneCb);
                    setText(todo.getTitle() + "\n" + todo.getDescription());
                });
                setGraphic(editingField);
            }
        });
    }
};