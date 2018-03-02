package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddEditMemberController {

    @FXML
    private Button doneBtn;

    @FXML
    private VBox parent;
    public ObservableList<TableColumn<Map, ?>> columns;
    public Stage stage;
    public DBMembersController dbmemberscontroller;
    public boolean state;
    public Map item;
    public int itemIndex;
    private boolean changed = false;


    @FXML
    void initialize() {

    }

    public void render() {
        for (int i = 0; i < columns.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editCell.fxml"));
                loader.setController(this);
                parent.getChildren().add(i, loader.load());
                HBox hbox = (HBox) parent.getChildren().get(i);
                Label label = (Label) hbox.getChildren().get(0);
                TextField textField = (TextField) hbox.getChildren().get(1);
                label.setText(columns.get(i).getText() + ":");
                if (state) {
                    textField.setText((String) item.get(i + ""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void done() {
        if (changed) {
            Map<String, String> dataRow = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                HBox hbox = (HBox) parent.getChildren().get(i);
                TextField textField = (TextField) hbox.getChildren().get(1);
                dataRow.put(dbmemberscontroller.colKeys[i], textField.getText());
            }
            if (state) {
                dbmemberscontroller.dbTable.getItems().set(itemIndex, dataRow);
            } else {
                dbmemberscontroller.dbTable.getItems().add(dataRow);
            }
            changed = false;
            doneBtn.setText("Готово");
        } else {
            stage.close();
        }


    }

    public void change(KeyEvent event) {
        changed = true;
        doneBtn.setText("Применить");
    }

}
