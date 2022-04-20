package manager;

import enums.Status;
import enums.Type;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private String backup;

    public FileBackedTasksManager(String backup) {
        String table = "id,type,name,status,description,epic";
        this.backup = backup;
        try (Writer fileWriter = new FileWriter(backup)) {
            fileWriter.write(table);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager manager = new FileBackedTasksManager(file.getPath());
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            bf.readLine();
            StringBuilder sb = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String[] strings = sb.toString().split("\n");
            List<String> historyList = historyFromString(strings[strings.length - 1]);
            strings[strings.length - 1] = null;
            for (String value : strings) {
                if (value != null) {
                    Task task = fromString(value);
                    if (task.getType().equals(Type.TASK)) {
                        manager.saveTask(task);
                    } else if (task.getType().equals(Type.EPIC)) {
                        manager.saveEpic((Epic) task);
                    } else {
                        manager.saveSubtask((Subtask) task);
                    }
                    if (historyList.contains(task.getId())) {
                        manager.inMemoryHistoryManager.add(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return manager;
    }

    private static List<String> historyFromString(String string) {
        return Arrays.asList(string.split(","));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : super.history()) {
            sb.append(task.getId());
        }
        return String.valueOf(sb);
    }

    public String toString(Task task) {
        return task.toString();
    }

    public static Task fromString(String value) {
        String[] strings = value.split(",");
        if (strings[1].equals("TASK")) {
            Task task = new Task(strings[2], strings[4]);
            task.setId(strings[0]);
            task.setStatus(Status.valueOf(strings[3]));
            return task;
        } else if (strings[1].equals("EPIC")) {
            Epic epic = new Epic(strings[2], strings[4]);
            epic.setId(strings[0]);
            epic.setStatus(Status.valueOf(strings[3]));
            return epic;
        } else  {
            Subtask subtask = new Subtask(strings[5], strings[2], strings[4]);
            subtask.setId(strings[0]);
            subtask.setStatus(Status.valueOf(strings[3]));
            return subtask;
        }
    }

    public void save() {
        try (BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(backup), StandardCharsets.UTF_8))) {
            bf.write("id,type,name,status,description,epic");
            bf.newLine();
            for (Task task : super.getAllTasks()) {
                bf.write(task.toString());
                bf.newLine();
            }
            for (Epic epic : super.getAllEpics()) {
                bf.write(epic.toString());
                bf.newLine();
            }
            for (Subtask subtask : super.getAllSubtasks()) {
                bf.write(subtask.toString());
                bf.newLine();
            }
            bf.newLine();
            StringBuilder sb = new StringBuilder();
            if (!super.history().isEmpty()) {
                for (Task task : super.history()) {
                    sb.append(task.getId()).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                bf.write(sb.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void save(Task task) {
//        try (Reader fileReader = new FileReader(backup)) {
//            BufferedReader br = new BufferedReader(fileReader);
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line).append("\n");
//            }
//            System.out.println(sb.toString());
//           try (Writer writer = new FileWriter(new File(backup))) {
//               for (String s : sb.toString().split("\n")) {
//                   String[] strings = sb.toString().split("\n");
//                   for (int i = 0; i < strings.length; i++) {
//                       if (i == strings.length - 2) {
//                           writer.write(task.toString() + "\n\n" + strings[i]);
//                       } else {
//                           writer.write(strings[i]);
//                       }
//                   }
//               }
//           }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void saveSubtask(Subtask subtask) {
        super.saveSubtask(subtask);
        save();
    }

    @Override
    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
        save();
    }

    @Override
    public void saveTask(Task task) {
        super.saveTask(task);
        save();
    }

    @Override
    public Task getTaskById(String taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(String epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(String subtaskId) {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();
        return subtask;
    }

    @Override
    public void deleteTask(String taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteEpic(String epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteSubtask(String subtaskId) {
        super.deleteSubtask(subtaskId);
        save();
    }
}
