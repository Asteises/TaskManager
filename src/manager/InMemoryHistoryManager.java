package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> last;
    private final Map<Integer, Node<Task>> historyMap;
    private final int SIZE_HISTORY = 10;
    private int size = 0;

    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    public void deleteAllTasksFromHistory(Map<Integer, Task> taskMap) {
        for (Task task : taskMap.values()) {
            if (historyMap.get(task.getId()) != null) {
                remove(task.getId());
            }
        }
    }

    public void deleteAllEpicsFromHistory(Map<Integer, Epic> epicMap) {
        for (Epic epicTask : epicMap.values()) {
            if (epicMap.get(epicTask.getId()) != null) {
                for (Subtask subtask : epicTask.getSubtaskMap().values()) {
                    remove(subtask.getId());
                }
                remove(epicTask.getId());
            }
        }
    }

    public void deleteAllSubtasksFromHistory(Map<Integer, Epic> epicMap) {
        for (Epic epicTask : epicMap.values()) {
            for (Subtask subtask : epicTask.getSubtaskMap().values()) {
                if (historyMap.get(subtask.getId()) != null) {
                    remove(subtask.getId());
                }
            }
        }
    }

    @Override
    public void add(Task task) {
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(historyMap.get(id));
        historyMap.remove(id);
    }

    public void removeNode(Node<Task> node) {
        Node<Task> next = node.next;
        Node<Task> prev = node.prev;
        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
        }
        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
        }
        node.setNext(null);
        node.setPrev(null);
        node.setData(null);
        size--;
    }

    public void linkLast(Task task) {
        if (historyMap.get(task.getId()) != null) {
            remove(task.getId());
        }
        if (SIZE_HISTORY <= size) {
            remove(head.getData().getId());
        }
        Node<Task> temp = last;
        Node<Task> newNode = new Node<Task>(task, null, temp);
        last = newNode;
        if (head == null) {
            head = newNode;
        } else {
            temp.setNext(newNode);
        }
        size++;
        historyMap.put(task.getId(), newNode);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> temp = head;
        while (temp != null) {
            tasks.add(temp.getData());
            temp = temp.getNext();
        }
        return tasks;
    }

}
