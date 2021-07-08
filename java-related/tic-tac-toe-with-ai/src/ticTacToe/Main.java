package ticTacToe;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TicGame game = new TicGame(new String[9]);
        game.run();
    }
}

class TicGame {
    static Scanner scanner = new Scanner(System.in);
    private String[] board;
    private boolean xFirst = true;
    private boolean move = false;

    public TicGame(String[] board) {
        this.board = board;
    }

    public void run() {
        this.draw();
        this.move();
    }

    private void move() {
        this.move = true;

        while (move) {
            System.out.println("Enter the coordinates:");

            String[] input = scanner.nextLine().split("\\s+");

            try {

                if (input.length < 2) {
                    throw new NumberFormatException();
                }

                int x = Integer.parseInt(input[0]);
                int y = Integer.parseInt(input[1]);

                if (x > 3 || y > 3 || x < 1 || y < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                int indexOfStep = findCoordinateInStepsArray(x, y);

                if (board[indexOfStep] != null) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                board[indexOfStep] = xFirst ? "X" : "O";

                draw();

                if (check() == null) {
                    this.move = true;
                    this.xFirst = !this.xFirst;
                } else {
                    this.move = false;
                    System.out.println(check());
                }

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        }

    }

    /**
     * check which win
     * print the win message or null
     *
     * @return
     */
    private String check() {
        boolean xWin = false;
        boolean oWin = false;

        int[][] possibles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        for (int[] d : possibles) {
            int a = d[0], b = d[1], c = d[2];

            if (board[a] != null && board[a].equals(board[b]) && board[a].equals(board[c])) {
                if (board[a].equals("X")) {
                    xWin = true;
                }

                if (board[a].equals("O")) {
                    oWin = true;
                }
            }
        }

        if (xWin) {
            return "X wins";
        } else if (oWin) {
            return "O wins";
        }

        return null;
    }

    /**
     * draw steps into board
     */
    public void draw() {
        System.out.println("---------");

        for (int i = 0; i < board.length; i++) {
            if (i == 0 || i == 3 || i == 6) {
                System.out.print("| ");
            }

            System.out.print(board[i] == null ? "  " : board[i] + " ");

            if (i == 2 || i == 5 || i == 8) {
                System.out.println("|");
            }
        }

        System.out.println("---------");
    }

    /**
     * put the user input O or X into board array
     *
     * @param x
     * @param y
     * @return
     */
    public static int findCoordinateInStepsArray(int x, int y) {
        // 1 1 -> 0 2  x-1 3-1
        // 1 3 -> 0 0  x-1 3-3

        x = x - 1;
        y = 3 - y;

        // 0,0 -> 0
        // 1,0 -> 1
        // 2,0 -> 2
        // 0,1 -> 3
        // 1,1 -> 4
        // 2,1 -> 5
        // 0,2 -> 6
        // 1,2 -> 7
        // 2,2 -> 8

        String flag = x + "" + y;
        int rs = 0;
        switch (flag) {
            case "00":
                rs = 0;
                break;
            case "10":
                rs = 1;
                break;
            case "20":
                rs = 2;
                break;
            case "01":
                rs = 3;
                break;
            case "11":
                rs = 4;
                break;
            case "21":
                rs = 5;
                break;
            case "02":
                rs = 6;
                break;
            case "12":
                rs = 7;
                break;
            case "22":
                rs = 8;
                break;
        }

        return rs;
    }

}
