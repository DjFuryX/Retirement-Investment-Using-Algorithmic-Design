import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class RetirementInvestment {

    static Scanner scanner = new Scanner(System.in); // for user input
    static final String CYN = "\u001B[36m";// define colour Cyan for console print
    static final String RST = "\u001B[37m";// define colour White for console print

    public static void main(String[] args) throws Exception {

        double balance = 0;
        double rate = 0;
        double years = 0;

        int option = mainMenu();
        while (option != 0) {// Start while loop for main menu
            switch (option) {// case structure is used to determine option selected
                case 1:
                    // Calculate Fixed Investment with fixed rate
                    balance = getBalance();
                    rate = getRate();
                    years = getYear();
                    fixedInvestmentBalance(balance, rate, years);
                    fixedInvestmentContribution(balance, rate, years);
                    break;
                case 2:
                     // Calculate Fixed Investment with varaible rates
                    balance = getBalance();
                    years = getYear();
                    LinkedList<Double> rateList = new LinkedList<>();
                   
                    for(int x=1;x<=years;x++){
                        System.out.print(CYN +"Year: "+ x +RST);
                        rateList.add(getRate());
                        System.err.println();
                    }
                    System.out.print("\033[H\033[2J"); // clears console screen
                    System.out.println(
                            "\n\t\t +-------------------------+ Calculate Fixed Investment with varaible rates +-------------------------+");
                    
                    variableInvestor(balance, rateList);
                    
                    break;
                case 3:
                    // Calculate How long Retirement Funds will last
                    break;
                case 4:
                    // Calculate maximum sustainable annual withdrawal for specified years
                    balance = getBalance();
                    rate = getRate();
                    years = getYear();
                    System.out.print("\033[H\033[2J"); // clears console screen
                    System.out.println(
                            "\n\t\t +-------------------------+ Maximum Sustainable Annual Withdrawal +-------------------------+");

                    // get maximum expense
                    double totalAmount = maximumExpensed(balance, rate, years);

                    System.out.println(CYN + "Customer Balance: $" + RST + String.format("%.2f", balance));
                    System.out.println(CYN + "Customer interest rate: " + RST + rate);
                    System.out.println(CYN + "Total Years: " + RST + years);
                    // output maximum expense
                    System.out.println("For $" + CYN + String.format("%.2f", balance) + RST + " to last " + CYN + years
                            + RST + " years at "
                            + CYN + rate + RST + " interest rate a maximum of $" + CYN
                            + String.format("%.2f", totalAmount) + RST
                            + " has to expended annually\n\n");
                    break;
                default:// if an invalid number is entered
                    System.err.println("Please Select a valid input");
                    break;
            }// end switch case
            System.out.println(CYN + "Press any key to continue" + RST);
            scanner.nextLine();// the program pauses until the user enters a key
            option = mainMenu();// get user option
        }
        System.out.println("\n\nGoodbye\nClosing application.......");// closing message
        scanner.close();
    }

    public static void fixedInvestmentBalance(double principal, double rate, double years) {
        System.out.println("Function 1 ");
        double balance = principal;
        System.out.println("Year\t|\tBalance");

        for (int x = 1; x <= years; x++) {
            balance = balance * (rate + 1);
            System.out.println(x + 1 + "\t\t" + String.format("%.2f", balance));
        }
    }

    public static void fixedInvestmentContribution(double principal, double rate, double years) {
        System.out.println("Fucntion 2 ");
        double balance = 0;
        System.out.println("Year\t|\tBalance");
        for (int i = 0; i < years; i++) {
            balance = ((balance + principal) * (rate + 1));
            System.out.println(i + 1 + "\t\t" + String.format("%.2f", balance));
        }

    }


    public static double variableInvestor(double principal,LinkedList<Double> rateList) {
    
    double balance = principal;

    System.out.println(CYN+"Year | Rate   | Balance"+RST);
    System.out.println("---------------------------");

    // continuously looping through the year rate    
    for (int i = 0; i < rateList.size(); i++) {
        double rate = rateList.get(i);
        
        // applying the interest for the current year balance 
        balance = balance * (1.0 + rate);
        
        // print the year's number rate and balance
        System.out.printf("%4d | %5.2f%% | $%.2f%n", 
                         i + 1, rate * 100.0, balance);
    }
    
    return balance;
}

    public static double maximumExpensed(double balance, double rate, double years) {

        double low = 0;
        double mid = 0;
        double oldMid =0;
        double high = balance;
        double tolerance = .00001; // accuracy for expense
        double current_balance = 0;
        while ((high - low) > tolerance) {
            oldMid =mid;
            mid = (low + high) / 2; // get midpoint expense for current iteration
            if (oldMid == mid) return mid; // reached the limit of the computer preciseness
            current_balance = balance; // set balance to original value

            // simulate compound growth - withdrawals for each year
            for (int x = 1; x <= years; x++) {
                current_balance = current_balance * (1 + rate);
                current_balance = current_balance - mid;
            }
            if (current_balance > 0) {// Withdrawal too small so can increase it
                low = mid;
            } else { // Withdrawal too large so decrease
                high = mid;
            }
        }
        return low; // best estimate of maximum sustainable annual withdrawal
    }

    public static int mainMenu() {
        LocalDateTime myDateObj = LocalDateTime.now(); // create date object
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy"); // format date
        String formattedDate = myDateObj.format(myFormatObj); // get date as a string
        System.out.print("\033[H\033[2J"); // clears console screen
        System.out.println(formattedDate); // print date
        // print menu
        System.out.println(
                "\n\t\t +-------------------------+ AofA Financial Services Limited +-------------------------+");
        System.out.println("\t\t | " + CYN + "1." + RST
                + "    Calculate Fixed Investment with fixed rate                                   |");
        System.out.println("\t\t | " + CYN + "2." + RST
                + "    Calculate Fixed Investment with varaible rates                               |");
        System.out.println("\t\t | " + CYN + "3." + RST
                + "    Calculate How long Retirement Funds will last                                |");
        System.out.println("\t\t | " + CYN + "4." + RST
                + "    Calculate maximum sustainable annual withdrawal for specified years          |");
        System.out.println("\t\t | " + CYN + "0." + RST
                + "    EXIT                                                                         |");
        System.out.println(
                "\t\t +-------------------------------------------------------------------------------------+");
        System.out.print("\nPlease select with the " + CYN + "digits" + RST + " on the left:  ");// prompts for user
                                                                                                   // option

        int option = -1;
        try {
            option = scanner.nextInt(); // store option as int
            scanner.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();

        }
        return option;
    };

    // used to get user balance or expense
    static double getBalance() {
        double money = 0;
        while (true) {

            System.out.print("\nPlease Enter Customer" + CYN + " Balance " + RST);
            try {
                money = scanner.nextDouble(); // store balance as a double
                scanner.nextLine();
                if (money <= 0) {
                    System.out.println("Balance must be greater than zero");
                } else {
                    return money;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer

            }
        }
    }

    static double getExpense() {
        double money = 0;
        while (true) {

            System.out.print("\nPlease Enter Customer" + CYN + " Expense " + RST + "per year ");
            try {
                money = scanner.nextDouble(); // store expense as a double
                scanner.nextLine();
                if (money < 0) {
                    System.out.println("expense must be greater than zero");
                } else {
                    return money;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer

            }
        }
    }

    static double getYear() {
        double year = 0;
        while (true) {

            System.out.print("\nPlease Enter total " + CYN + "years " + RST);
            try {
                year = scanner.nextDouble(); // store year as a double
                scanner.nextLine();
                if (year < 0) {
                    System.out.println("year must be greater than zero");
                } else {
                    return year;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer

            }
        }
    }

    static double getRate() {
        double rate = 0;
        while (true) {
            System.out.println("\nPlease Enter Customer" + CYN + " interest rate" + RST + " as a decimal");
            try {
                rate = scanner.nextDouble(); // store rate as a double
                scanner.nextLine();
                return rate;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer

            }
        }
    }

}
