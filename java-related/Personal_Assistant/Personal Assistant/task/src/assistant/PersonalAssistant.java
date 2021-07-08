package assistant;

import java.util.Scanner;

public class PersonalAssistant {

    private Scanner scanner;
    private String name;
    private int age;

    public PersonalAssistant(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        // write your code here
        PersonalAssistant personalAssistant = new PersonalAssistant(new Scanner(System.in));
        personalAssistant.hello();
        personalAssistant.count();
        personalAssistant.question1();
    }

    public void hello() {
        System.out.println("Hello! My name is Aid.");
        System.out.println("I was created in 2018.");
        System.out.println("Please, remind me your name.");
        name = this.scanner.next();
        System.out.printf("What a great name you have, %s!\n", this.name);
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");
        int t3 = this.scanner.nextInt();
        int t5 = this.scanner.nextInt();
        int t7 = this.scanner.nextInt();
        this.age = (t3 * 70 + t5 * 21 + t7 * 15) % 105;
        System.out.printf("Your age is %d: that's a good time to start programming!\n", this.age);
    }

    public void count() {

        System.out.println("Now I will prove to you that I can count to any number you want.");
        int n = this.scanner.nextInt();

        for (int i = 0; i <= n; i++) {
            System.out.printf("%d!\n", i);
        }
    }


    public void question1() {
        System.out.println("Let's test your programming knowledge.");
        System.out.println("Why do we use methods?");
        System.out.println("1. To repeat a statement multiple times.");
        System.out.println("2. To decompose a program into several small subroutines.");
        System.out.println("3. To determine the execution time of a program.");
        System.out.println("4. To interrupt the execution of a program.");

        int answer = 0;

        while (true) {
            answer = this.scanner.nextInt();
            if (answer == 2) {
                System.out.println("Congratulations, have a nice day!");
                break;
            } else {
                System.out.println("Please, try again.");
            }
        }
    }

}