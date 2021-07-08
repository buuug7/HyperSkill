package coffeeMachine;


import java.util.Scanner;

class CoffeeMachine {

    private Scanner scanner;

    private int water;
    private int milk;
    private int coffeeBean;
    private int disposableCups;
    private int money;

    // espresso
    int espressoPerCupOfWater = 250;
    int espressoPerCupOfCoffeeBean = 16;
    int espressoPerCupOfPrice = 4;

    // latte
    int lattePerCupOfWater = 350;
    int lattePerCupOfMilk = 75;
    int lattePerCupOfCoffeeBean = 20;
    int lattePerCupOfPrice = 7;

    // cappuccino
    int cappuccinoPerCupOfWater = 200;
    int cappuccinoPerCupOfMilk = 100;
    int cappuccinoPerCupOfCoffeeBean = 12;
    int cappuccinoPerCupOfPrice = 6;

    enum Operation {
        BUY,
        FILL,
        TAKE,
        REMAINING,
        EXIT
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.run();
    }

    private CoffeeMachine() {
        this.scanner = new Scanner(System.in);
        this.water = 400;
        this.milk = 540;
        this.coffeeBean = 120;
        this.disposableCups = 9;
        this.money = 550;
    }


    public void run() {
        boolean runFlag = true;

        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.next().toUpperCase();

            Operation action = Operation.valueOf(input);

            switch (action) {
                case BUY:
                    buy();
                    break;
                case FILL:
                    fill();
                    break;
                case TAKE:
                    take();
                    break;
                case REMAINING:
                    showCurrentState();
                    break;
                case EXIT:
                    runFlag = false;
                    break;
            }

        } while (runFlag);

    }

    private void fill() {
        System.out.println("Write how many ml of water do you want to add: ");
        water += scanner.nextInt();

        System.out.println("Write how many ml of milk do you want to add:");
        milk += scanner.nextInt();

        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffeeBean += scanner.nextInt();

        System.out.println("Write how many disposable cups of coffee do you want to add:");
        disposableCups += scanner.nextInt();
    }

    private void take() {
        System.out.printf("I gave you %d\n", money);
        money = 0;
    }

    private void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String kindOfCoffee = scanner.next();

        switch (kindOfCoffee) {
            case "1":
                buyEspresso();
                break;
            case "2":
                buyLatte();
                break;
            case "3":
                buyCappuccino();
                break;
            case "back":
                return;
        }
    }

    private void buyCappuccino() {
        if (cappuccinoPerCupOfWater > water) {
            printNotEnoughWater();
            return;
        }

        if (cappuccinoPerCupOfMilk > milk) {
            printNotEnoughMilk();
            return;
        }

        if (cappuccinoPerCupOfCoffeeBean > coffeeBean) {
            printNotEnoughCoffeeBean();
            return;
        }

        if (disposableCups < 0) {
            printNotEnoughDisposableCups();
            return;
        }

        water -= cappuccinoPerCupOfWater;
        milk -= cappuccinoPerCupOfMilk;
        coffeeBean -= cappuccinoPerCupOfCoffeeBean;
        disposableCups -= 1;
        money += cappuccinoPerCupOfPrice;

        printEnoughResource();
    }

    void printNotEnoughWater() {
        System.out.println("Sorry, not enough water!");
    }

    void printNotEnoughMilk() {
        System.out.println("Sorry, not enough milk!");
    }

    void printNotEnoughCoffeeBean() {
        System.out.println("Sorry, not enough coffee beans!");
    }

    void printNotEnoughDisposableCups() {
        System.out.println("Sorry, not enough disposable cups!");
    }

    void printEnoughResource() {
        System.out.println("I have enough resources, making you a coffee!");
    }

    private void buyLatte() {

        if (lattePerCupOfWater > water) {
            printNotEnoughWater();
            return;
        }

        if (lattePerCupOfMilk > milk) {
            printNotEnoughMilk();
            return;
        }

        if (lattePerCupOfCoffeeBean > coffeeBean) {
            printNotEnoughCoffeeBean();
            return;
        }

        if (disposableCups < 0) {
            printNotEnoughDisposableCups();
            return;
        }

        water -= lattePerCupOfWater;
        milk -= lattePerCupOfMilk;
        coffeeBean -= lattePerCupOfCoffeeBean;
        disposableCups -= 1;
        money += lattePerCupOfPrice;

        printEnoughResource();
    }

    private void buyEspresso() {

        if (espressoPerCupOfWater > water) {
            printNotEnoughWater();
            return;
        }

        if (espressoPerCupOfCoffeeBean > coffeeBean) {
            printNotEnoughCoffeeBean();
            return;
        }

        if (disposableCups < 0) {
            printNotEnoughDisposableCups();
            return;
        }

        water -= espressoPerCupOfWater;
        coffeeBean -= espressoPerCupOfCoffeeBean;
        disposableCups -= 1;
        money += espressoPerCupOfPrice;

        printEnoughResource();
    }

    void showCurrentState() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", water);
        System.out.printf("%d of milk\n", milk);
        System.out.printf("%d of coffee beans\n", coffeeBean);
        System.out.printf("%d of disposable cups\n", disposableCups);
        System.out.printf("%d of money\n", money);
    }

}
