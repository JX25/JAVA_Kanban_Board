package sample;


import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class SetCellFactory extends ListView {

    public static void setCustomCellFactory(ListView listView)
    {
        listView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>()
    {
        @Override
        public ListCell<Task> call(ListView<Task> param)
        {
            return new ListCell<Task>(){
                @Override
                protected void updateItem(Task tsk, boolean empty)
                {
                    super.updateItem(tsk,empty);
                    if(tsk == null || empty )
                    {

                    }
                    else {
                        setText(tsk.toString());
                        if (tsk.getPriority() == "Low") getStyleClass().add("lowPriorityStyle");
                        else if (tsk.getPriority() == "Medium") getStyleClass().add("mediumPriorityStyle");
                        else if (tsk.getPriority() == "High") getStyleClass().add("highPriorityStyle");
                    }
                }
            };
        }
    });
    }
}
