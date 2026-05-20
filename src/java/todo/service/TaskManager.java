package todo.service;

import todo.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public boolean addTask(String title) {

        if(title == null || title.trim().isEmpty()) {
            return false;
        }


        for(Task t : tasks) {
            if(t.getTitle().equalsIgnoreCase(title)) {
                return false;
            }
        }

        tasks.add(new Task(title));
        return true;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void markCompleted(Task task) {
        task.setCompleted(true);
    }

    public void editTask(Task task, String newTitle) {
        task.setTitle(newTitle);
    }

    public List<Task> searchTasks(String keyword) {

        List<Task> results = new ArrayList<>();

        for(Task task : tasks) {
            if(task.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(task);
            }
        }

        return results;
    }
}
