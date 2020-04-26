package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static int[][] gameMass = new int[3][3];
    static Scanner scan = new Scanner(System.in);
    static int countElements = 0;
    static int playerNumberOne = 1;
    static int playerNumberTwo = 2;

    public static void main(String[] args) throws IOException, InterruptedException {
        String nameFirst = null;
        String nameSecond = null;
        nameFirst = getName(playerNumberOne);
        nameSecond = getName(playerNumberTwo);

        System.out.println("Играют " + nameFirst + " и " + nameSecond);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameMass[i][j] = 0;
            }
        }
        drawGame();

        boolean endGame = false;
        do {
            endGame = doStep(playerNumberOne, nameFirst);
            if (endGame) break;

            endGame = doStep(playerNumberTwo, nameSecond);
            if (endGame) break;

        } while (true);
    }

    public static boolean victoryPlayer(int playerNumber) {
        if ((gameMass[0][0] == playerNumber && gameMass[0][1] == playerNumber && gameMass[0][2] == playerNumber) ||
                (gameMass[1][0] == playerNumber && gameMass[1][1] == playerNumber && gameMass[1][2] == playerNumber) ||
                (gameMass[2][0] == playerNumber && gameMass[2][1] == playerNumber && gameMass[2][2] == playerNumber) ||
                (gameMass[0][0] == playerNumber && gameMass[1][0] == playerNumber && gameMass[2][0] == playerNumber) ||
                (gameMass[0][1] == playerNumber && gameMass[1][1] == playerNumber && gameMass[2][1] == playerNumber) ||
                (gameMass[0][2] == playerNumber && gameMass[1][2] == playerNumber && gameMass[2][2] == playerNumber) ||
                (gameMass[0][0] == playerNumber && gameMass[1][1] == playerNumber && gameMass[2][2] == playerNumber) ||
                (gameMass[2][0] == playerNumber && gameMass[1][1] == playerNumber && gameMass[0][2] == playerNumber)) {
            return true;
        } else return false;
    }

    public static void drawGame() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Чтобы сделать ход ведите координаты клетки  формате \"строка,столбец\"");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameMass[i][j] == 0) System.out.print(getSymbolPlayer(0));
                if (gameMass[i][j] == 1) System.out.print(getSymbolPlayer(1));
                if (gameMass[i][j] == 2) System.out.print(getSymbolPlayer(2));
                if (j != 2) System.out.print(" | ");
            }
            System.out.println("");
            if (i != 2) System.out.println("----------");
        }
    }

    public static Coordinate getPlayerInput() {
        Coordinate coordinate = new Coordinate();
        String input;
        do {
            do {
                input = scan.nextLine();
                if (input.length() < 3) System.out.println("Введите минимум 3 символа");
            } while (input.length() < 3);
            coordinate.coordinateX = Integer.parseInt(input.substring(0, 1));
            coordinate.coordinateY = Integer.parseInt(input.substring(2, 3));
            if (coordinate.coordinateX > 0 && coordinate.coordinateX < 4 && coordinate.coordinateY > 0 && coordinate.coordinateY < 4) {
                break;
            } else {
                System.out.println("Неверный формат, повторите ввод");
            }
        } while (true);
        return coordinate;
    }

    public static boolean doStep(int playerNumber, String namePlayer) throws IOException, InterruptedException {
        boolean endGame = false;
        boolean stepDone = false;
        do {
            System.out.println("Ходит игрок " + namePlayer + ". Ваш символ " + getSymbolPlayer(playerNumber));
            Coordinate coordinate = getPlayerInput();
            if (gameMass[coordinate.coordinateX - 1][coordinate.coordinateY - 1] == 0) {
                gameMass[coordinate.coordinateX - 1][coordinate.coordinateY - 1] = playerNumber;
                stepDone = true;
                countElements++;
            } else {
                System.out.println("Данная клетка уже занята, повторите ввод");
                stepDone = false;
            }
        } while (!stepDone);
        drawGame();

        if (victoryPlayer(playerNumber)) {
            System.out.println("Победа игрока " + namePlayer);
            endGame = true;
            return endGame;
        } else if (countElements == 9) {
            endGame = true;
            System.out.println("Ничья");
            return endGame;
        } else return false;
    }

    public static String getName(int playerNumber) {
        String name;
        do {
            System.out.println("Введите имя игрока № " + playerNumber);
            name = scan.nextLine();
            if (name.isEmpty()) {
                System.out.println("Вы не ввели имя, повторите ввод");
            } else break;
        } while (name.isEmpty());
        return name;
    }

    public static String getSymbolPlayer(int playerNumber) {
        if (playerNumber == 1) return "X";
        else if (playerNumber == 2) return "O";
        else return "*";
    }
}
