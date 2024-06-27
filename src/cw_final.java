
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;      //importing necessary libraries
import java.io.IOException;
import java.io.File;

public class cw_final {

    static int burgers = 50;
    static char[] cashier_1_status = {'X', 'X'};
    static char[] cashier_2_status = {'X', 'X', 'X'};
    static char[] cashier_3_status = {'X', 'X', 'X', 'X', 'X'};
    static String[] customer_dataQ1 = new String[2];
    static String[] customer_dataQ2 = new String[3];
    static String[] customer_dataQ3 = new String[5];
    static int que1_remains = 0;
    static int que2_remains = 0;                //Declaring variables and arrays needed
    static int que3_remains = 0;
    static boolean queue_full;
    static int nullCount = 0;

    public static void main(String[] args) {
        boolean exit = false;           //Initialzed to false until user enters 999 which sets this to true
        String menu_op = " ";
        do {
            try {
                System.out.println("""
                                                           
                        100 or VFQ: View all Queues.
                        101 or VEQ: View all Empty Queues.
                        102 or ACQ: Add customer to a Queue.
                        103 or RCQ: Remove a customer from a Queue.
                        104 or PCQ: Remove a served customer.
                        105 or VCS: View Customers Sorted in alphabetical order
                        106 or SPD: Store Program Data into file.
                        107 or LPD: Load Program Data from file.
                        108 or STK: View Remaining burgers Stock.
                        109 or AFS: Add burgers to Stock.
                        999 or EXT: Exit the Program.
                        """);

                System.out.print("Enter your option: ");
                Scanner input = new Scanner(System.in);
                menu_op = input.nextLine();
                System.out.println(" ");
                switch (menu_op) {
                    case "100", "VFQ" -> allQueues();
                    case "101", "VEQ" -> {
                        boolean empty1 = false;
                        boolean empty2 = false;
                        boolean empty3 = false;
                        System.out.print("Available queus are: ");
                        emptyQueues(customer_dataQ1, empty1, 1);
                        emptyQueues(customer_dataQ2, empty2, 2);
                        emptyQueues(customer_dataQ3, empty3, 3);
                        System.out.println("\n");
                    }
                    case "102", "ACQ" -> addCustomer();
                    case "103", "RCQ" -> removeCustomer(menu_op);
                    case "104", "PCQ" -> removeCustomer(menu_op);
                    case "105", "VCS" -> customernames_order();
                    case "106", "SPD" -> storeData();                                           //each case represents inputs entered by the user and matched case runs the relevant methods
                    case "107", "LPD" -> displayData();
                    case "108", "STK" -> {
                        System.out.println(Arrays.toString(customer_dataQ1));
                        System.out.println(Arrays.toString(customer_dataQ2));
                        System.out.println(Arrays.toString(customer_dataQ3));
                        System.out.println("que1 remains: "+que1_remains);
                        System.out.println("que2 remains: "+que2_remains);
                        System.out.println("que3 remains: "+que3_remains);
                        System.out.println("There are " + burgers + " remaining burgers in the stock!\n");
                    }
                    case "109", "AFS" -> addBurgers();
                    case "999", "EXT" -> exit = true;
                    default -> System.out.println("Wrong menu input!!\n");
                }
                queue_full = false;
            } catch (Exception error) {
                System.out.println("Error in menu method");
            }

        } while (!exit);
    }

    public static void emptyQueues(String[] customer_Data, boolean empty, int num) throws IOException {
        for (int i = 0; i < customer_Data.length; i++) {
            if (customer_Data[i] == null) {                                   //Checks if any of the queue has an empty position and if yes, breaks the loop
                empty = true;                                                 //then it prints the current checked queue number indicating the queue has an empty spot
                break;
            }
        }
        if (empty) {
            System.out.print(num + " ");
        }
    }

    public static void addCustomer() {
        try {
            String customer;
            System.out.print("Enter Queue: ");
            Scanner input = new Scanner(System.in);             //gets name from the operator and adds it to the relevant queue
            int queue = input.nextInt();
            input.nextLine();
            customerQueue(queue);
            if (!queue_full && queue <= 3 && queue >= 1) {        /* avoid asking name once the queue is full */
                System.out.print("Enter Name: ");
                customer = input.nextLine();
                System.out.println(customer + " added to the queue " + queue + " successfully\n");

                switch (queue) {
                    case 1 -> customer_dataQ1[que1_remains - 1] = customer;
                    case 2 -> customer_dataQ2[que2_remains - 1] = customer;         //selects the queue to add customer
                    case 3 -> customer_dataQ3[que3_remains - 1] = customer;
                }
            } else if(queue > 3 || queue < 1) System.out.println("Wrong queue number!!, Please enter 1,2 or 3.");
        } catch (Exception error) {
            System.out.println("Wrong input!! Queue should be a number ranging from 1 to 3.\n");

        }
    }

    public static void customerQueue(int queue) {

        try {
            switch (queue) {                                    //this method updates the queue status arrays which displays when option 100 is selected
                case 1 -> {
                    cashier_1_status[que1_remains] = 'O';   /* que1_remains --> initially 0 and then added 1 each time a customer is added to a queue */
                    que1_remains++;
                }
                case 2 -> {
                    cashier_2_status[que2_remains] = 'O';
                    que2_remains++;
                }
                case 3 -> {
                    cashier_3_status[que3_remains] = 'O';
                    que3_remains++;
                }
            }

        } catch (Exception error) {
            System.out.println("Queue is full\n");
            queue_full = true;
        }

    }

    public static void allQueues() {
        System.out.println("    ******************");                   //Prints cashiers and the customer status in each queue in order
        System.out.println("       * Cashiers *");
        System.out.println("    ******************");
        for (int j = 0; j < 2; j++) {
            System.out.println("    "+cashier_1_status[j] + "       " + cashier_2_status[j] + "       " + cashier_3_status[j] + " ");
        }
        for (int j = 2; j < 3; j++) {
            System.out.println("            " + cashier_2_status[j] + "       " + cashier_3_status[j] + " ");
        }
        for (int j = 3; j < 5; j++) {
            System.out.println("                    " + cashier_3_status[j] + " ");
        }
        System.out.println(" ");
        System.out.println("X – Not Occupied  O – Occupied");
    }

    public static void removeCustomer(String menu_op) {
        try {
            Scanner input = new Scanner(System.in);

            if ("103".equals(menu_op) || "RCQ".equals(menu_op)) {
                System.out.print("Enter the queue number (1, 2, or 3): ");              //checks if a customer needs to be served or just removed
                int unserved_queue = input.nextInt();                                   //menu options regarding removing customers are given as a parameter(103 or 104)
                //according to the parameter each method related to the menu option will be called

                switch (unserved_queue) {
                    case 1 -> unservedCustomers(customer_dataQ1, cashier_1_status, que1_remains, unserved_queue);
                    case 2 -> unservedCustomers(customer_dataQ2, cashier_2_status, que2_remains, unserved_queue);
                    case 3 -> unservedCustomers(customer_dataQ3, cashier_3_status, que3_remains, unserved_queue);
                    default -> System.out.println("Wrong queue number!!, Please enter 1,2 or 3.\n");
                }
            } else if (("104".equals(menu_op) || "PCQ".equals(menu_op)) && burgers>=5) {            //checks the menu option as well as burger stock, if stock is low customer will not be served
                System.out.print("Enter the served queue number (1, 2, or 3): ");
                int served_queue = input.nextInt();

                switch (served_queue) {
                    case 1 -> servedCustomer(customer_dataQ1, cashier_1_status, que1_remains, served_queue);
                    case 2 -> servedCustomer(customer_dataQ2, cashier_2_status, que2_remains, served_queue);
                    case 3 -> servedCustomer(customer_dataQ3, cashier_3_status, que3_remains, served_queue);
                    default -> System.out.println("Wrong queue number!!, Please enter 1,2 or 3.\n");
                }
            }else{
                System.out.println("Burger stock is running below 5 burgers!!, Please add burgers to the stock in order to serve.");
            }

        } catch (Exception remove_customers1) {
            System.out.println("Wrong input!!, Queue should be a number ranging from 1 to 3.\n");
        }
    }

    public static void unservedCustomers(String[] customer_data, char[] cashier_status, int queue_remains, int unserved_queue) {
        try {
            if (customer_data[0] == null) {
                System.out.println("No customers to remove,current queue is empty!!\n");                                                        //Method regarding removing customers who aren't served
            } else {                                                                                                                            //queue array,a cashier status array, a count for each queue that adds up when a customer is added,
                Scanner input = new Scanner(System.in);                                                                                         //and an integer which have the queue number of that unserved customer are provided as parameters
                System.out.print("Enter the position of the customer in the queue ranging from 0-" + (customer_data.length - 1) + ": ");
                int customer_index = input.nextInt();

                if (customer_index < 0 || customer_index >= customer_data.length) {
                    System.out.println("Invalid customer position!!, Position of the customer should be in range.\n");
                } else if (customer_data[customer_index] == null) {
                    System.out.println("No customer to remove!!, Entered customer position is empty.\n");
                } else {
                    System.out.println("Customer in position " + customer_index + "(" + customer_data[customer_index] + ")" + " has been removed successfully!\n");

                    for (int i = customer_index; i < customer_data.length - 1; i++) {
                        if (customer_data[i + 1] == null) {
                            break;
                        } else {
                            customer_data[i] = customer_data[i + 1];            //checks and removes the customer at the index that operator enters which need to be removed
                            customer_index++;                                   //then the customer objects after that are assigned to its before index, like when someone is going out of a queue
                        }
                    }
                    switch (unserved_queue) {
                        case 1 -> que1_remains -= 1;
                        case 2 -> que2_remains -= 1;                                     //According to the queue the count relating to that queue is removed by 1
                        case 3 -> que3_remains -= 1;
                    }
                    queue_remains--;
                    customer_data[customer_index] = null;
                    customer_data[customer_data.length - 1] = null;                 //100 options are updated and the last element of the queue referenced here is set to null
                    cashier_status[queue_remains] = 'X';
                }
            }
        } catch (Exception err) {
            System.out.println("Invalid input!!, Customer position should be a number in the provided range.");
        }
    }

    public static void servedCustomer(String[] customer_data, char[] cashier_status, int queue_remains, int served_queue) {
        int removeCount = 1;
        try {
            if (customer_data[0] == null) {
                System.out.println("No customers to remove, queue is empty!!\n");                               //queue object,a cashier status array, a count for each queue that adds up when a customer is added,
            } else if (customer_data[0] != null) {                                                              //an integer referencing the customer queue are added as parameters
                switch (served_queue) {                                                                         //This method is regarding the removal of served customers
                    case 1 -> que1_remains -= 1;                                                                //updates the burger sales for each queue and removes the customer and the waiting queue functionalities are same as in unserved option
                    case 2 -> que2_remains -= 1;
                    case 3 -> que3_remains -= 1;
                }
                System.out.println("Customer " + customer_data[0] + " has been removed successfully!\n");
                customerRemover(customer_data, cashier_status, queue_remains, removeCount);
                burgers -= 5;
            }
            if (burgers == 10) {
                System.out.println("********************************");
                System.out.println("The burger stock has reached down to 10 burgers\n");                //warnings regarding the burger stock
                System.out.println("********************************");
            } else if (burgers < 15 && burgers > 10) {
                System.out.println("Warning!! burger stock will reach below 10 burgers once one more customer is served!");
                System.out.println("Currently there are " + burgers + " burgers in the stock!");
            }
        } catch (Exception error) {
            System.out.println("Please enter a queue number\n");
        }
    }

    public static void customerRemover(String[] customer_data, char[] cashier_status, int queue_remains, int removeCount) {
        do {
            if (removeCount == 0) break;
            queue_remains --;
            removeCount--;
            for (int i = 1; i < customer_data.length; i++) {            //queue array,a cashier status array, a count for each queue that adds up when a customer is added,
                customer_data[i - 1] = customer_data[i];                // a count for the no of customers to be removed are given as parameters
            }                                                           //this removes customer according to given queue and the remove count and updates information related to it

            customer_data[customer_data.length - 1] = null;
            cashier_status[queue_remains] = 'X';
        } while (removeCount > 0);
    }

    public static void customernames_order(){
        nullCal(customer_dataQ1);
        nullCal(customer_dataQ2);
        nullCal(customer_dataQ3);

        String[] allCustomerData = new String[10-nullCount];            //Initializing an array to get all customer names and calling functions that does the adding functionality
        int index = 0;                                                  //Empty spots are omitted with the use of nullcal method

        index = allNames(customer_dataQ1, allCustomerData, index);
        index = allNames(customer_dataQ2, allCustomerData, index);
        allNames(customer_dataQ3, allCustomerData, index);

        nameSort(allCustomerData);
        nullCount = 0;
    }

    public static void nullCal(String[] customerData){
        for (int i = 0; i < customerData.length; i++){
            if(customerData[i] == null){                    //customer arrays are given to check how many null elements are there in that queue(empty spots)
                nullCount++;
            }
        }
    }

    public static int allNames(String[] customerData, String[] allData, int index) {
        for(int i = 0; i<customerData.length; i++){
            if(customerData[i] != null){
                allData[index] = customerData[i];                           //customer array, array that stores all customer name, and index for that array are provided as parameters
                index++;                                                    //This method adds all names except nulls to the customerData array
            }
        }
        return index;
    }

    public static void nameSort(String[] customerData) {
        try {
            String[] array = new String[customerData.length];
            String temp;                                                //for the name sorting algorithm array with all customer data is given as a parameter

            for (int i = 0; i < customerData.length; i++)
                array[i] = customerData[i];

            System.out.println(Arrays.toString(array));

            int bottom = array.length - 2;
            boolean exchanged = true;
            while (exchanged) {
                exchanged = false;
                for (int i = 0; i <= bottom; i++) {
                    int min = 0;
                    int equalCount = 0;

                    if (array[i].length() > array[i + 1].length()) {        //checks the legth of the names
                        min = array[i + 1].length();
                    }else if (array[i].length() < array[i + 1].length()) {
                        min = array[i].length();
                    }

                    if (array[i].charAt(0) > array[i + 1].charAt(0)) {          //comparing first char of 2 names
                        temp = array[i];
                        array[i] = array[i + 1];
                        array[i + 1] = temp;
                        exchanged = true;


                    }else if (array[i].charAt(0) == array[i + 1].charAt(0)) {
                        String len;
                        if (array[i].length() == array[i + 1].length()) {
                            len = array[i];

                            for (int j = 0; j < len.length(); j++) {
                                if (array[i].charAt(j) > array[i + 1].charAt(j)) {
                                    temp = array[i];
                                    array[i] = array[i + 1];
                                    array[i + 1] = temp;
                                    exchanged = true;
                                }
                            }
                        } else if(array[i].length() != array[i + 1].length()){
                            for (int j = 0; j < min; j++) {
                                if (array[i].charAt(0) > array[i + 1].charAt(0)) {
                                    temp = array[i];
                                    array[i] = array[i + 1];
                                    array[i + 1] = temp;
                                    exchanged = true;
                                }else{
                                    equalCount++;
                                    if(equalCount==min){
                                        temp = array[i];
                                        array[i] = array[i + 1];
                                        array[i + 1] = temp;
                                        exchanged = true;
                                    }
                                }
                            }
                        }
                    }
                }
                bottom--;
            }
            System.out.println(Arrays.toString(array));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void storeData() throws IOException {
        FileWriter fileWriter = new FileWriter("dataSave.txt");             //Saves data to the text file(Executes the method that does it)

        storeDataloop(fileWriter, customer_dataQ1);
        storeDataloop(fileWriter, customer_dataQ2);
        storeDataloop(fileWriter, customer_dataQ3);

        fileWriter.write(burgers + "\n");
        fileWriter.close();
        System.out.println("Data has been saved successfully!\n");
    }

    public static void storeDataloop(FileWriter fileWriter, String[] customer_data) throws IOException {
        for (int i = 0; i < customer_data.length; i++) {
            fileWriter.write((customer_data[i]) + "\n");            //customer data is written to the text file
        }
    }

    public static void displayData(){

        try {
            File inputFile = new File("dataSave.txt");
            Scanner readFile = new Scanner(inputFile);      // Read and process each line of the file
            int lineCount = 0;

            customerRemover(customer_dataQ1,cashier_1_status,que1_remains,que1_remains);
            customerRemover(customer_dataQ2,cashier_2_status,que2_remains,que2_remains);         //Emptying data(executes customer remover method above) in queues before adding old data stored in the text file
            customerRemover(customer_dataQ3,cashier_3_status,que3_remains,que3_remains);
            que1_remains = 0;
            que2_remains = 0;
            que3_remains = 0;

            while (readFile.hasNextLine()) {
                String text = readFile.nextLine();

                if(lineCount >= 0 && lineCount < 2){
                    if(!("null".equals(text))){
                        cashier_1_status[que1_remains] = 'O';
                        customer_dataQ1[lineCount] = text;          //assigns each line data to customer names accordingly
                        que1_remains ++;                            //data from text file that refers queue 1 is added to queue 1 here
                    }else{
                        customer_dataQ1[lineCount] = null;
                    }

                }else if(lineCount > 1 && lineCount < 5){
                    if(!("null".equals(text))){
                        cashier_2_status[que2_remains] = 'O';
                        customer_dataQ2[lineCount-2] = text;         //data from text file that refers queue 2 is added to queue 2 here
                        que2_remains ++;
                    }else{
                        customer_dataQ2[lineCount-2] = null;
                    }

                }else if(lineCount > 4 && lineCount < 10){
                    if(!("null".equals(text))){
                        cashier_3_status[que3_remains] = 'O';
                        customer_dataQ3[lineCount-5] = text;        //data from text file that refers queue 3 is added to queue 3 here
                        que3_remains ++;
                    }else{
                        customer_dataQ3[lineCount-5] = null;
                    }

                }else{
                    burgers = Integer.parseInt(text);       //Burger stock data is updated here
                }
                lineCount++;
            }
            readFile.close();
            System.out.println("Data has been successfully loaded to the programme!!\n");

        } catch (Exception error) {
            System.out.println("Please try saving data into a file first and then try loading data.");
        }
    }

    public static void addBurgers() throws IOException {
        int stock;
        int new_stock;
        //All options related to adding burgers are here
        while (true) {
            if (burgers == 50) {
                System.out.println("Stock is full, please serve customers and try again..\n");      //burgers are permitted to be added only if the burger stock is not = to 50, here it checks if it's = 50 and if true, breaks
                break;

            } else if (burgers != 50) {
                stock = (50 - burgers);
                System.out.println("The maximum number of burgers you can add is: " + stock + "\n");

                while (true) {
                    try {
                        System.out.print("Enter the numbers of burgers you are going to add: ");
                        Scanner input = new Scanner(System.in);
                        new_stock = input.nextInt();

                        if (new_stock > stock) {
                            System.out.println("Cannot exceed the maximum number of burgers you can add!!\n");
                            new_stock -= new_stock;
                        }else if (new_stock <= stock) {
                            burgers += new_stock;
                            System.out.println("Successfully added " + new_stock + " burgers to the stock!\n");
                            break;
                        }
                    }catch (Exception add_burgers) {
                        System.out.println("Wrong input!!, Amount of burgers can only be a number.\n");
                    }
                }
                break;
            }
        }
    }
}
