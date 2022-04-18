package manager;

import enums.Type;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private String backup;

    public FileBackedTasksManager(String backup) {
        String table = "id,type,name,status,description,epic\n\n\n";
        this.backup = backup;
        File file = new File(backup);
        try {
            if (file.length() == 0) {
                Writer fileWriter = new FileWriter(file);
                fileWriter.write(table);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(Task task) {
        return task.toString();
    }

    public Task fromString(String value) {
        try {
            Reader fileReader = new FileReader(value);
            int data = fileReader.read();
            while (data != -1) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /*
    id,type,name
    1,Task,sadas,New,\n
    2,EPIC,asdasd,DONE\n
    \n
    ,
    4,SUBTASK,cvcxv,DONE

    2,3
     */

    public void save(Task task) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(backup)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                    sb.append(line);
            }
            String[] lines = sb.toString().split("\n");
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                if (i == lines.length - 3) {
                    sb2.append(task.toString() + "\n");
                    sb2.append(lines[i]);
                } else {
                    sb2.append(lines[i] + "\n");
                }

            }
            Writer writer = new FileWriter(new File(backup));
            writer.write(sb2.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        super.saveSubtask(subtask);
        save(subtask);
    }

    @Override
    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
        save(epic);
    }

    @Override
    public void saveTask(Task task) {
        super.saveTask(task);
        save(task);
    }
}
