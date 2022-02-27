package service;

import java.util.Scanner;

public class Menu {
    private String[] menu;

    public Menu() {
        menu = new String[] {"Получение списка всех задач",
                "Удаление всех задач"};
    }

    public void showMenu() {
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + " " + menu[i]);
        }
    }

    public int setMenuNumber() {
        Scanner sc = new Scanner(System.in);
        int menuNumber;
        while (true) {
            menuNumber = sc.nextInt();
            if (menuNumber > 0) {
                return menuNumber;
            }
        }
    }

    public void openMenuByMenuNumber(int menuNumber) {
        showMenu();
        switch (menuNumber) {
            case 1:
        }
    }
}
