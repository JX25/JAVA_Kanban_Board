package sample;

import javafx.collections.ObservableList;
import sample.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListsData implements Serializable {

    private ArrayList<Task> toDoData;
    private ArrayList<Task> inProgressData;
    private ArrayList<Task> finishedData;

    ListsData(List<Task> todo, List<Task> inprogress, List<Task> finished)
    {
        toDoData = new ArrayList<>(todo);
        inProgressData = new ArrayList<>(inprogress);
        finishedData = new ArrayList<>(finished);
    }

    public ArrayList<Task> returnToDo()
    {
        return this.toDoData;
    }
    public ArrayList<Task> returnInProgress()
    {
        return this.inProgressData;
    }
    public ArrayList<Task> returnFinished()
    {
        return this.finishedData;
    }



}
