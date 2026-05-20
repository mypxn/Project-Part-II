package todo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import todo.model.Task;
import todo.service.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class MainUIController {

    private TaskManager manager = new TaskManager();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private ListView<Task> taskList;

    @FXML
    private TextField taskInput;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {

        taskList.setItems(tasks);


        refreshList();
    }

    @FXML
    private void searchTask() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            refreshList();
            return;
        }

        tasks.clear();
        for (Task t : manager.getTasks()) {
            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                tasks.add(t);
            }
        }
    }

    @FXML
    private void addTask() {
        String title = taskInput.getText();
        if (title == null || title.trim().isEmpty()) {
            showMessage("Task cannot be empty");
            return;
        }

        boolean added = manager.addTask(title);
        if (!added) {
            showMessage("Task already exists or too long");
        } else {
            taskInput.setText("");
            refreshList();
        }
    }

    @FXML
    private void deleteTask() {
        Task selected = taskList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("Please select a task to delete");
            return;
        }
        manager.deleteTask(selected);
        refreshList();
    }

    @FXML
    private void completeTask() {
        Task selected = taskList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("Please select a task to complete");
            return;
        }
        manager.markCompleted(selected);
        refreshList();
    }

    @FXML
    private void editTask() {
        Task selected = taskList.getSelectionModel().getSelectedItem();
        String newTitle = taskInput.getText();

        if (selected == null) {
            showMessage("Please select a task to edit");
            return;
        }

        if (newTitle == null || newTitle.trim().isEmpty()) {
            showMessage("New title cannot be empty");
            return;
        }

        manager.editTask(selected, newTitle);
        taskInput.setText("");
        refreshList();
    }

    @FXML
    private void toggleDarkMode() {
        Scene scene = taskList.getScene();
        if (scene.getStylesheets().isEmpty()) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dark.css")).toExternalForm());
        } else {
            scene.getStylesheets().clear();
        }
    }

    private void refreshList() {
        tasks.clear();
        for (Task t : manager.getTasks()) {
            tasks.add(t);
        }
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}