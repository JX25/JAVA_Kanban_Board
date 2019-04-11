package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static sample.SetCellFactory.setCustomCellFactory;

public class Controller implements Initializable {

    private List<Task> toDo = new ArrayList<>();
    private List<Task> inProgress = new ArrayList<>();
    private List<Task> isFinished = new ArrayList<>();
    private static final DataFormat customFormat = new DataFormat("Task to move");
    @FXML private MenuItem exitKey;
    @FXML private MenuItem thisProject;
    @FXML private ListView<Task> toDoList;
    @FXML private ListView<Task> inProgressList;
    @FXML private ListView<Task> finishedList;
    @FXML private Button addNewTask;
    @FXML private MenuItem editKey;
    @FXML private MenuItem deleteKey;
    @FXML private MenuItem openButton;
    @FXML private MenuItem saveButton;
    @FXML private MenuItem importButton;
    @FXML private MenuItem exportButton;
    FileChooser fileChooser;
    private MouseEvent event;
    private Tooltip tooltip;

    @FXML
    void addTaskFinishedDetected(MouseEvent event) {
        //System.out.println("addTaskFinishedDetected");
        dragDetected(event, inProgressList);
    }
    @FXML
    void addTaskFinishedDone(DragEvent event) {
        //System.out.println("addTaskFinishedDone");
        dragDone(event, inProgressList);
    }
    @FXML
    void addTaskFinishedDropped(DragEvent event) {
        //System.out.println("addTaskFinishedDropped");
        dragDropped(event, finishedList);
    }
    @FXML
    void addTaskFinishedOver(DragEvent event) {
        //System.out.println("addTaskFinishedOver");
        dragOver(event, finishedList);
    }
    @FXML
    void infoAboutTaskToDo(MouseEvent event) {
        if (toDoList.getSelectionModel().getSelectedItem() != null) {
            String opis = toDoList.getSelectionModel().getSelectedItem().getInformation();
            infoAbout(opis);
        }
    }
    @FXML
    void infoAboutTaskInProgress(MouseEvent event) {
        if (inProgressList.getSelectionModel().getSelectedItem() != null) {
            String opis = inProgressList.getSelectionModel().getSelectedItem().getInformation();
            infoAbout(opis);
        }
    }
    @FXML
    void infoAboutTaskFinished(MouseEvent event) {
        /*if (finishedList.getSelectionModel().getSelectedItem() != null) {
            ObservableList selectedIndices = finishedList.getSelectionModel().getSelectedIndices();
            Task t = (Task) selectedIndices;
            String opis = t.getInformation();
            infoAbout(opis);
        }*/
    }
    void infoAbout(String opis) {
        /*if (opis != null) {
            tooltip.setText(opis);
            Node node = (Node) event.getSource();
            com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
            tooltip.show(node, robot.getMouseX() + 20, robot.getMouseY() + 20);
            tooltip.setConsumeAutoHidingEvents(true);
        }*/
    }
    @FXML
    void infoAboutTaskEnd(MouseEvent event) {
        tooltip.hide();
        tooltip.setText(null);

    }
    @FXML
    void removeTaskToDo(ActionEvent event) {
        removeSelectedTask(toDoList);
        setCustomCellFactory(toDoList);
    }
    @FXML
    void removeTaskInProgress(ActionEvent event) {
        removeSelectedTask(inProgressList);
        setCustomCellFactory(inProgressList);
    }
    @FXML
    void removeTaskFinished(ActionEvent event) {
        removeSelectedTask(finishedList);
        setCustomCellFactory(finishedList);
    }
    @FXML
    void openEditTaskMenuToDo(ActionEvent event) throws IOException {
        try {
            openEditTaskMenu(toDoList, toDo);

        } catch (Exception e) {
            errorComunicate(e);
        }
    }
    @FXML
    void openEditTaskMenuInProgress(ActionEvent event) throws IOException {
        try {
            openEditTaskMenu(inProgressList, inProgress);

        } catch (Exception e) {
            errorComunicate(e);
        }
    }
    @FXML
    void openEditTaskMenuFinished(ActionEvent event) throws IOException {
        try {
            openEditTaskMenu(finishedList, isFinished);

        } catch (Exception e) {
            errorComunicate(e);
        }
    }
    void errorComunicate(Exception e) {
        Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Cannot open edit task menu", ButtonType.OK);
        errorInfo.setTitle("Error Edit Task");
        errorInfo.setHeaderText("There was an error with editing task");
        errorInfo.show();
        e.printStackTrace();
    }
    void openEditTaskMenu(ListView<Task> listView, List<Task> list) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("fxml/EditTask.fxml")));
        Parent root;
        root = (Parent) fxmlLoader.load();
        EditTask controller = fxmlLoader.getController();
        controller.setValues(listView.getSelectionModel().getSelectedItem());
        Stage secondaryStage = new Stage();
        secondaryStage.getIcons().add(new Image("sample/fxml/css/kanban.png"));
        secondaryStage.setScene(new Scene(root));
        secondaryStage.setTitle("Edit existing task");
        secondaryStage.setResizable(false);
        secondaryStage.showAndWait();
        controller.editTask(listView.getSelectionModel().getSelectedItem());
        setCustomCellFactory(listView);
    }
    @FXML
    void addTaskInProgressDetected(MouseEvent event) {
        //System.out.println("addTaskInProgressDetected");
        dragDetected(event, toDoList);
    }
    @FXML
    void addTaskInProgressDone(DragEvent event) {
        //System.out.println("addTaskInProgressDone");
        dragDone(event, toDoList);
    }
    @FXML
    void addTaskInProgressDropped(DragEvent event) {
        //System.out.println("addTaskInProgressDropped");
        dragDropped(event, inProgressList);
        setCustomCellFactory(inProgressList);
    }
    @FXML
    void addTaskInProgressOver(DragEvent event) {
        //System.out.println("addTaskInProgressOver");
        dragOver(event, inProgressList);
    }
    private void dragDetected(MouseEvent event, ListView listView) {
        this.event = event;
        int count = listView.getSelectionModel().getSelectedIndices().size();
        if (count == 0) {
            event.consume();
            return;
        }
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.ANY);
        ArrayList<Task> tasks = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
        ClipboardContent content = new ClipboardContent();
        content.put(customFormat, tasks);
        dragboard.setContent(content);
        event.consume();
    }
    private void dragOver(DragEvent event, ListView target) {
        Dragboard dragboard = event.getDragboard();
        if (event.getGestureSource() != target && dragboard.hasContent(customFormat)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
    private void dragDropped(DragEvent event, ListView target) {
        boolean dragCompleated = false;
        event.acceptTransferModes(TransferMode.MOVE);
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(customFormat)) {
            ArrayList<Task> tasks = (ArrayList<Task>) dragboard.getContent(customFormat);
            ObservableList<Task> tasksOL = FXCollections.observableList(tasks);
            target.getItems().addAll(tasksOL);
            setCustomCellFactory(target);
            dragCompleated = true;
        }
        event.setDropCompleted(dragCompleated);
        event.consume();
    }
    private void dragDone(DragEvent event, ListView source) {
        TransferMode modeUsed = event.getTransferMode();
        if (modeUsed == TransferMode.MOVE) {
            removeSelectedTask(source);
        }
        setCustomCellFactory(source);

        event.consume();
    }
    @FXML
    public void createNewTask(ActionEvent ae) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("fxml/NewTask.fxml")));
            Parent root;
            root = (Parent) fxmlLoader.load();
            Stage secondaryStage = new Stage();
            secondaryStage.getIcons().add(new Image("sample/fxml/css/kanban.png"));
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setTitle("Add new task");
            secondaryStage.setResizable(false);
            secondaryStage.showAndWait();
            NewTask controller = fxmlLoader.getController();
            Task taskToAdd = controller.returnCreatedTask();
            createTask(taskToAdd);
        } catch (Exception e) {
            Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Cannot open new task menu", ButtonType.OK);
            errorInfo.setTitle("Error New Task");
            errorInfo.setHeaderText("There was an error with adding new task");
            errorInfo.show();
            e.printStackTrace();
        }
    }
    @FXML
    public void exitFromApp(ActionEvent ae) {
        System.exit(0);
    }
    @FXML
    public void infoAboutAuthor() {
        Alert authorInfo = new Alert(Alert.AlertType.INFORMATION, "Tablica Kanban Java Projekt 04", ButtonType.OK);
        authorInfo.setTitle("Information about author");
        authorInfo.setHeaderText("Program created by Jakub Wozniak JX25");
        authorInfo.show();
    }
    public void updateListViews() {
        ObservableList<Task> tsks = FXCollections.observableList(toDo);
        toDoList.setItems(tsks);
        setCustomCellFactory(toDoList);
        tsks = FXCollections.observableList(inProgress);
        inProgressList.setItems(tsks);
        setCustomCellFactory(inProgressList);
        tsks = FXCollections.observableList(isFinished);
        finishedList.setItems(tsks);
        setCustomCellFactory(finishedList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Task> toDo = new ArrayList<>();
        ArrayList<Task> inProgress = new ArrayList<>();
        ArrayList<Task> isFinished = new ArrayList<>();

        ObservableList<Task> toDoOL = FXCollections.observableList(toDo);
        ObservableList<Task> inProgressOL = FXCollections.observableList(inProgress);
        ObservableList<Task> isFinishedOL = FXCollections.observableList(isFinished);

        toDoList.getItems().addAll(toDoOL);
        setCustomCellFactory(toDoList);
        inProgressList.getItems().addAll(inProgressOL);
        setCustomCellFactory(inProgressList);
        finishedList.getItems().addAll(isFinishedOL);
        setCustomCellFactory(finishedList);
        tooltip = new Tooltip();
    }
    public void createTask(Task tsk) {
        toDo.add(tsk);
        ObservableList<Task> toDoOL = FXCollections.observableList(toDo);
        toDoList.setItems(toDoOL);
    }
    private void removeSelectedTask(ListView<Task> listView) {
        List<Task> selectedList = new ArrayList<>();
        for (Task tsk : listView.getSelectionModel().getSelectedItems()) {
            selectedList.add(tsk);
        }
        listView.getSelectionModel().clearSelection();
        listView.getItems().removeAll(selectedList);
    }
    @FXML
    public void exportCSV(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Save .csv file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".csv Files", "*.csv"));
        File fileToSave = fileChooser.showSaveDialog(null);
        if (fileToSave != null) {
            try {
                FileWriter fileWriter = null;
                String allTasks = new String();
                toDo = toDoList.getItems();
                inProgress = inProgressList.getItems();
                isFinished = finishedList.getItems();
                for (Task tsk : toDo) {
                    allTasks += "td;" + tsk.toString() + ";" + tsk.getPriority() + ";" + tsk.getDescription() + ";" + tsk.getDate() + '\n';
                }
                for (Task tsk : inProgress) {
                    allTasks += "ip;" + tsk.toString() + ";" + tsk.getPriority() + ";" + tsk.getDescription() + ";" + tsk.getDate() + '\n';
                }
                for (Task tsk : isFinished) {
                    allTasks += "if;" + tsk.toString() + ";" + tsk.getPriority() + ";" + tsk.getDescription() + ";" + tsk.getDate() + '\n';
                }
                fileWriter = new FileWriter(fileToSave);
                fileWriter.write(allTasks);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void importCSV(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open .csv file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".csv Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (selectedFile.toString().contains(".csv")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                    String lineBuffer;
                    toDo.clear();
                    inProgress.clear();
                    isFinished.clear();
                    try {
                        while ((lineBuffer = br.readLine()) != null) {
                            String[] task = lineBuffer.split(";");
                            if (task[0].equals("td")) toDo.add(new Task(task[1], task[2], task[3], task[4]));
                            else if (task[0].equals("ip")) inProgress.add(new Task(task[1], task[2], task[3], task[4]));
                            else if (task[0].equals("if")) isFinished.add(new Task(task[1], task[2], task[3], task[4]));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateListViews();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Chosen file is not .csv", ButtonType.OK);
                errorInfo.setTitle("This is not .csv file");
                errorInfo.show();
            }
        } else {
            Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Chosen file is empty #null", ButtonType.OK);
            errorInfo.setTitle("Import from .csv Error");
            errorInfo.show();
        }
    }
    @FXML
    public void openFile(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Kanban file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Kanban Files", "*.knb"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
                try {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    ListsData readLists = (ListsData) ois.readObject();
                    toDo.clear();
                    inProgress.clear();
                    isFinished.clear();
                    toDo = readLists.returnToDo();
                    inProgress = readLists.returnInProgress();
                    isFinished = readLists.returnFinished();
                    updateListViews();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Problem with opening file", ButtonType.OK);
                    errorInfo.setTitle("wrong extension");
                    errorInfo.show();
                }
            } else {
                Alert errorInfo = new Alert(Alert.AlertType.ERROR, "the file is null", ButtonType.OK);
                errorInfo.setTitle("error null file");
                errorInfo.show();
            }
    }
    @FXML
    public void saveFile(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Save Kanban file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Kanban Files", "*.knb"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                ListsData save = new ListsData(toDo, inProgress, isFinished);
                FileOutputStream fos = new FileOutputStream(selectedFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(save);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert errorInfo = new Alert(Alert.AlertType.ERROR, "Cannot save file", ButtonType.OK);
            errorInfo.setTitle("There was a problem with saving");
            errorInfo.show();
        }
    }
}