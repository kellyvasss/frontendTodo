package kelly.frontendTodo.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import kelly.frontendTodo.api.TodoAPI;
import kelly.frontendTodo.data.CreateTodoDTO;
import kelly.frontendTodo.data.Todo;
import kelly.frontendTodo.data.UpdateTodoDTO;
import lombok.Data;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Route("")
@Data
public class MainView extends VerticalLayout {

    private TextField description;
    private TextField filterText = new TextField();
    private Grid<Todo> todoGrid = new Grid<>(Todo.class, false);
    private Todo todo;

    public MainView() {
        configureGrid();
        configureFilterText();
        add(
                new Button("Lägg till Todo", click -> openAddTodoDialog()),
                filterText, todoGrid
        );
    }
    private void configureFilterText() {
        filterText.setPlaceholder("Sök efter beskrivning...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid(filterText.getValue()));
    }

    private void updateGrid(String value) {

        try {
            todoGrid.setItems(TodoAPI.getTodods(value));
        } catch (IOException | ParseException e) {
            System.out.println(e);
        }
    }

    private void openAddTodoDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Lägg till Todo");
        description = new TextField("Vad ska göras:");
        dialog.add(description);
        Button save = createSaveBtnForAddTodo(dialog, description);
        Button cancel = new Button("Avbryt", click -> dialog.close());
        dialog.getFooter().add(save, cancel);
        dialog.open();
    }

    private Button createSaveBtnForAddTodo(Dialog dialog, TextField description) {
        Button save = new Button("Spara", click -> {
            CreateTodoDTO dto = new CreateTodoDTO(description.getValue());
            try {
                TodoAPI.createTodo(dto);
                updateGrid(filterText.getValue());
            } catch (IOException | org.apache.hc.core5.http.ParseException e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });
        return save;
    }

    private void configureGrid() {
        todoGrid.addColumn(Todo::getDescription).setHeader("Beskrivning");
        todoGrid.addColumn(Todo::getDone).setHeader("Utförd").setSortable(true);
        todoGrid.asSingleSelect().addValueChangeListener(marked -> {
            if (marked.getValue() != null) {
                todo = marked.getValue();
                System.out.println(marked.getValue().getDescription() + " " + todo.getDescription());
                if (!todo.getDone()) {
                    openMarkDoneDialog();
                }
            }

        });
        todoGrid.addComponentColumn(todo1 -> {
            Icon delete = new Icon(VaadinIcon.TRASH);
            delete.addClickListener(click -> {
                deleteTodo(todo1.getId());
            });
            return delete;
        }).setWidth("150px").setFlexGrow(0);
        updateGrid(filterText.getValue());
    }

    private void deleteTodo(Long id) {
        try {
            TodoAPI.deleteTodo(id);
            updateGrid(filterText.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openMarkDoneDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Markera som utförd?");
        Button save = createSaveBtnForMarkDone(dialog);
        Button cancel = new Button("Nej", click -> dialog.close());
        dialog.getFooter().add(save, cancel);
        dialog.open();
    }

    private Button createSaveBtnForMarkDone(Dialog dialog) {
        Button save = new Button("Ja", click -> {
            UpdateTodoDTO dto = new UpdateTodoDTO(todo.getId(), todo.getDescription());
            try {
                TodoAPI.updateTodo(dto);
                updateGrid(filterText.getValue());
            } catch (IOException | org.apache.hc.core5.http.ParseException e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });
        updateGrid(filterText.getValue());
        return save;
    }
}
