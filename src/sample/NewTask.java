package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class NewTask implements Initializable{

    @FXML
    private TextField titl;

    @FXML
    private ChoiceBox<String> prio;

    @FXML
    private DatePicker data;

    @FXML
    private TextArea text;

    @FXML
    private Button addButton;


    public Task returnCreatedTask()
    {
        String title = titl.getText();
        String pr = prio.getValue();
        String desc = text.getText();
        String dat = data.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Task tsk = new Task(title,pr,desc,dat);
        return tsk;
    }


    @FXML
    public void addTask(ActionEvent event) {

        Stage stage = (Stage) addButton.getScene().getWindow();

        try
        {
            stage.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        prio.getItems().setAll("Low","Medium","High");
        prio.setMinWidth(50);
        prio.getSelectionModel().selectFirst();

    }
}
