package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            userService.saveUser("a", "b", (byte) 1);
            System.out.printf("User с именем – %s добавлен в базу данных\n", "a");
        }
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // Создание таблицы User(ов)
        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        // Очистка таблицы User(ов)
        // Удаление таблицы
    }
}
