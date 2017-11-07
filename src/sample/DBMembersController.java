package sample;


import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class DBMembersController {

    @FXML
    private MenuItem exitButton;
    @FXML
    private TableView<Map> dbTable;
    @FXML
    private ScrollPane adEdBase;
    @FXML
    private VBox adEdPanel;
    @FXML
    private OctIconView extArrow;
    @FXML
    private AnchorPane sidePanel;

    private int COLCOUNT;
    private String[] ColNames;
    private String[] colKeys = new String[32];
    private String fileName = "Prepod.csv";
    private File file = new File(fileName);

    public void EditMemberAction() {

    }

    public void AddMemberAction() {


    }

    public void extArrowAction() {
        if (extArrow.getGlyphName() == "TRIANGLE_RIGHT") {
            extArrow.setGlyphName("TRIANGLE_LEFT");
            AnchorPane.setRightAnchor(sidePanel,-350.0);
            AnchorPane.setRightAnchor(dbTable,15.0);



        } else {

            extArrow.setGlyphName("TRIANGLE_RIGHT");
            AnchorPane.setRightAnchor(sidePanel,0.0);
            AnchorPane.setRightAnchor(dbTable,365.0);



        }


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
            dbTable.getColumns().add(new TableColumn<Map, Object>(ColNames[i]));
        }
        int forIndexOfCol = 0;
        for (TableColumn tc : dbTable.getColumns()) {
            tc.setCellValueFactory(new MapValueFactory(colKeys[forIndexOfCol]));
            forIndexOfCol++;
        }

        for (int i = 0; i < COLCOUNT; i++) {

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
