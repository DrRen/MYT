package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBMembersController {

    Stage stage;

    @FXML
    public TableView<Map> dbTable;

    private int COLCOUNT;
    private String[] ColNames;
    public String[] colKeys = new String[32];
    private String fileName = "Prepod.csv";
    private File file = new File(fileName);

    public void EditMemberAction() throws IOException {
        AddEditMemberController addEditMemberController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditMember.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage addStage = new Stage();

        addStage.setScene(scene);
        addStage.show();

        addEditMemberController = loader.getController();
        addEditMemberController.stage = addStage;
        addEditMemberController.columns = dbTable.getColumns();
        addEditMemberController.dbmemberscontroller = this;
        addEditMemberController.state = true;
        addEditMemberController.item = dbTable.getSelectionModel().getSelectedItem();
        addEditMemberController.itemIndex = dbTable.getSelectionModel().getSelectedIndex();
        addEditMemberController.render();
    }

    public void AddMemberAction() throws IOException {
        AddEditMemberController addEditMemberController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditMember.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage addStage = new Stage();

        addStage.setScene(scene);
        addStage.show();

        addEditMemberController = loader.getController();
        addEditMemberController.stage = addStage;
        addEditMemberController.columns = dbTable.getColumns();
        addEditMemberController.dbmemberscontroller = this;
        addEditMemberController.state = false;
        addEditMemberController.render();
    }


    public void exitAction() {
        Stage thisStage = (Stage) dbTable.getScene().getWindow();
        thisStage.close();
    }

    public void DelNewMemberAction() {
        ObservableList<Map> membersSelected, allMembers;
        allMembers = dbTable.getItems();
        membersSelected = dbTable.getSelectionModel().getSelectedItems();

        membersSelected.forEach(allMembers::remove);
    }

    public void MenuSave() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        for (TableColumn tc : dbTable.getColumns()) {
            if (dbTable.getColumns().indexOf(tc) == COLCOUNT - 1)
                out.append(tc.getText() + "\n");
            else out.append(tc.getText() + ";");
        }
        for (int i = 0; i < dbTable.getItems().size(); i++) {
            Map m = dbTable.getItems().get(i);
            for (int j = 0; j < COLCOUNT; j++) {
                if (j != COLCOUNT - 1) {
                    out.append(m.get(j + "") + ";");
                } else {
                    out.append(m.get(j + "") + "\n");
                }
            }
        }
        out.close();
    }

    @FXML
    void initialize() {

        try {
            Scanner sc = new Scanner(file);
            String colName = sc.nextLine();
            String[] valColName = colName.split(";");
            COLCOUNT = valColName.length;
            ColNames = valColName;
            for (int i = 0; i < COLCOUNT; i++) {
                colKeys[i] = "" + i;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < COLCOUNT; i++) {
            dbTable.getColumns().add(new TableColumn<>(ColNames[i]));
        }
        int forIndexOfCol = 0;
        for (TableColumn tc : dbTable.getColumns()) {
            tc.setCellValueFactory(new MapValueFactory(colKeys[forIndexOfCol]));
            forIndexOfCol++;
        }

        dbTable.setItems(generateDataInMap());

    }


    private ObservableList<Map> generateDataInMap() {
        ObservableList<Map> allData = FXCollections.observableArrayList();


        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextLine()) {
                Map<String, String> dataRow = new HashMap<>();
                String data = sc.nextLine();
                String[] values = data.split(";");
                for (int i = 0; i < COLCOUNT; i++) {
                    dataRow.put(colKeys[i], values[i]);

                }
                allData.add(dataRow);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return allData;
    }

}
