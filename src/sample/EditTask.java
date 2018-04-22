package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditTask implements Initializable{

        @FXML
        private TextField titl;
        @FXML private ChoiceBox<String> prio;
        @FXML private DatePicker data;
        @FXML private TextArea text;
        @FXML private Button acceptButton;
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public void editTask(Task tsk)
    {
        tsk.setName(titl.getText());
        LocalDate local = data.getValue();
        tsk.setExpDate( local.format(formatter));
        tsk.setPriority(prio.getValue());
        tsk.setDescription(text.getText());
    }

    public void setValues(Task tsk)
    {
        titl.setText(tsk.toString());
        prio.getSelectionModel().select(tsk.getPriority());
        text.setText(tsk.getDescription());
        LocalDate local = LocalDate.parse(tsk.getDate(), formatter);
        data.setValue(local);
    }

    @FXML
    void acceptChanges(ActionEvent event) {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
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
    }
}
