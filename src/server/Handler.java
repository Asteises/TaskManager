package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.FileBackedTasksManager;
import manager.Managers;
import model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

class Handler implements HttpHandler {
    private final String path = "backup.csv";
    private final Managers managers = new Managers();
    private FileBackedTasksManager fileBackedTasksManager = managers.getDefaultFileBacked(Path.of(path));

    public Handler() {
        Task task1 = new Task("Заголовок task1", "Текст task1", 60, LocalDateTime.now());
        Task task2 = new Task("Заголовок task2", "Текст task2", 100, LocalDateTime.now().plusMinutes(60));
        fileBackedTasksManager.saveTask(task1);
        fileBackedTasksManager.saveTask(task2);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("GET".equals(exchange.getRequestMethod())) {
            handleGet(exchange);
        }

        if ("POST".equals(exchange.getRequestMethod())) {
            handlePost(exchange);
        }

        if ("DELETE".equals(exchange.getRequestMethod())) {
            handleDelete(exchange);
        }
    }

    public void handleGet(HttpExchange exchange) throws IOException {
        String text = "";
        byte[] resp;
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String path = exchange.getRequestURI().toString();
        if (path.contains("tasks/task")) {
            if (path.equals("/tasks/task")) {
                text = fileBackedTasksManager.getAllTasks().toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
                exchange.close();
            } else if (path.contains("tasks/task/?id=")) {
                String id = exchange.getRequestURI().getPath().substring("/tasks/task/?id=".length());
                text = fileBackedTasksManager.getTaskById(id).toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            } else if (path.equals("/tasks/history")) {
                text = fileBackedTasksManager.getHistory();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            }
        } else if (path.contains("tasks/subtask")) {
            if (path.equals("/tasks/subtask")) {
                text = fileBackedTasksManager.getAllSubtasks().toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            } else if (path.contains("tasks/subtask/?id=")) {
                String id = exchange.getRequestURI().getPath().substring("/tasks/subtask/?id=".length());
                text = fileBackedTasksManager.getSubtaskById(id).toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            } else if (path.contains("tasks/subtask/epic/?id=")) {
                String id = exchange.getRequestURI().getPath().substring("/tasks/subtask/epic/?id=".length());
                text = fileBackedTasksManager.getAllSubtaskByEpic(id).toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            }
        } else if (path.contains("tasks/epic")) {
            if (path.equals("/tasks/epic")) {
                text = fileBackedTasksManager.getAllEpics().toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            } else if (path.contains("tasks/epic/?id=")) {
                String id = exchange.getRequestURI().getPath().substring("/tasks/epic/?id=".length());
                text = fileBackedTasksManager.getEpicById(id).toString();
                resp = text.getBytes(UTF_8);
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            }
        }
    }


    public void handlePost(HttpExchange exchange) throws IOException {
        String object = new String(exchange.getRequestBody().readAllBytes(), UTF_8);
        //JsonElement jsonElement = JsonParser.parseString(object);
        Gson g = new Gson();
//        String str = g.toJson(fileBackedTasksManager.getAllTasks().get(1));
//        Task task = g.fromJson(object, Task.class);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Task newTask = g.fromJson(bufferedReader.readLine(), Task.class);

            fileBackedTasksManager.saveTask(newTask);
        } catch (IOException e) {

        }

        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }

    public void handleDelete(HttpExchange exchange) throws IOException {

    }
}
