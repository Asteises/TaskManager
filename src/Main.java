public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        /*

        Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
        Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
        Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус
        эпика рассчитался по статусам подзадач.
        И, наконец, попробуйте удалить одну из задач и один из эпиков.

         */

        Task task1 = new Task("Заголовок task1", "Текст task1");
        Task task2 = new Task("Заголовок task2", "Текст task2");
        manager.newTask(task1);
        manager.newTask(task2);

        Epic epic1 = new Epic("Заголовок epic1", "Текст epic1");
        Epic epic2 = new Epic("Заголовок epic2", "Текст epic2");
        manager.newEpic(epic1);
        manager.newEpic(epic2);

        Subtask subtask1 = new Subtask("Заголовок subtask1", "Текст subtask1");
        Subtask subtask2 = new Subtask("Заголовок subtask2", "Текст subtask2");
        Subtask subtask3 = new Subtask("Заголовок subtask3", "Текст subtask3");

        manager.newSubtask(epic1.getId(), subtask1);
        manager.newSubtask(epic1.getId(), subtask2);
        manager.newSubtask(epic2.getId(), subtask3);

        /*

        Фраза «информация приходит вместе с информацией по задаче» означает, что не существует отдельного метода,
        который занимался бы только обновлением статуса задачи. Вместо этого статус задачи обновляется вместе с полным
        обновлением задачи.

        Из описания задачи видно, что эпик не управляет своим статусом самостоятельно. Это значит:
        1. Пользователь не должен иметь возможности поменять статус эпика самостоятельно.
        2. Когда меняется статус любой подзадачи в эпике, вам необходимо проверить, что статус эпика изменится
        соответствующим образом. При этом изменение статуса эпика может и не произойти, если в нём, к примеру, всё ещё
        есть незакрытые задачи.

         */
        System.out.println("========================");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println("========================");

        subtask1.setStatus("DONE");
        subtask2.setStatus("DONE");
        subtask3.setStatus("IN_PROGRESS");
        task1.setStatus("IN_PROGRESS");

        manager.updateTask(task1);
        manager.updateSubtask(epic1.getId(), subtask1);
        manager.updateSubtask(epic1.getId(), subtask2);
        manager.updateSubtask(epic2.getId(), subtask3);

        System.out.println(manager.getEpicById(epic1.getId()));
        System.out.println(manager.getEpicById(epic2.getId()));
        System.out.println("========================");

        manager.printAllTasks(manager.getAllTasks());
        manager.printAllEpicsAndSubtasks(manager.getAllEpics());
        System.out.println("========================");

        manager.deleteTask(1);
        manager.deleteEpic(4);
        manager.deleteSubtask(5);
        System.out.println("========================");

        manager.printAllTasks(manager.getAllTasks());
        manager.printAllEpicsAndSubtasks(manager.getAllEpics());

//        Menu menu = new Menu();
//        menu.showMenu();
    }
}