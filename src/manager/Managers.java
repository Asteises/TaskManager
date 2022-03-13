package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public class Managers {

    public TaskManager getDefault() {
        return new TaskManager() {
            @Override
            public void saveEpic(Epic epic) {

            }

            @Override
            public void saveTask(Task task) {

            }

            @Override
            public void saveSubtask(Subtask subtask) {

            }

            @Override
            public List<Task> getAllTasks() {
                return null;
            }

            @Override
            public List<Epic> getAllEpics() {
                return null;
            }

            @Override
            public List<Subtask> getAllSubtasks() {
                return null;
            }

            @Override
            public void clearTasks() {

            }

            @Override
            public void clearEpics() {

            }

            @Override
            public void clearSubtasks() {

            }

            @Override
            public Task getTaskById(int taskId) {
                return null;
            }

            @Override
            public Epic getEpicById(int epicId) {
                return null;
            }

            @Override
            public Subtask getSubtaskById(int subtaskId) {
                return null;
            }

            @Override
            public void updateTask(Task task) {

            }

            @Override
            public void updateEpic(Epic epic) {

            }

            @Override
            public void updateSubtask(Subtask subtask) {

            }

            @Override
            public void deleteTask(int taskId) {

            }

            @Override
            public void deleteEpic(int epicId) {

            }

            @Override
            public void deleteSubtask(int subtaskId) {

            }

            @Override
            public List<Subtask> getAllSubtaskByEpic(int epicId) {
                return null;
            }

            @Override
            public void printAllTasks(List<Task> tasks) {

            }

            @Override
            public void printAllEpicsAndSubtasks(List<Epic> epics) {

            }

            @Override
            public List<Task> history() {
                return null;
            }
        };
    }
}
