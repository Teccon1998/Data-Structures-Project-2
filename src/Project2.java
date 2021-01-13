import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Arrays;
import java.io.*;

public class Project2 
{
    
    public static void main(String[] args) throws IOException
    {
        //creating all the objects I need for the program
        ArrayList<Person> phonebook = new ArrayList<Person>();
        Scanner keyboard = new Scanner(System.in);
        PrintWriter text = new PrintWriter("addresses.txt");


        //menu container and while look keeping the menu static until exit is called.
        int choice = showMenu(keyboard);
        while(choice != 7)
        {
            switch(choice)
            {
            case 1:
                phonebook.add(addContact(keyboard));
                choice = showMenu(keyboard);
                break;
            case 2:
                phonebook = delContact(keyboard, phonebook);
                choice = showMenu(keyboard);
                break;
            case 3:
            //these arrays are to make the array resort every time the function is called. 
            //Its also converting formatted strings into long arrays
                String tempArr[] = new String[phonebook.size()];
                long tempArr2[] = new long[phonebook.size()];
                for(int i = 0; i <phonebook.size(); i++)
                {
                    tempArr[i] = phonebook.get(i).getPhoneNum();
                    tempArr2[i] = getPhoneNumWithoutDashes(tempArr[i]);
                }
                Arrays.sort(tempArr2);
                phonebook = modContact(keyboard, phonebook,tempArr2);
                choice = showMenu(keyboard);
                break;
            case 4:
                printContact(keyboard, phonebook);
                choice = showMenu(keyboard);
                break;
            case 5:
                printAll(phonebook);
                choice = showMenu(keyboard);
                break;
            case 6:
                writeToFile(phonebook,text);
                choice = showMenu(keyboard);
                break;
            }
        }
        //closed objects
        keyboard.close();
        text.close();
    }
//menu method to display and request a choice from the user.
    public static int showMenu(Scanner keyboard)
    {
        int menuchoice;

        System.out.println("--------- Main Menu --------");
        System.out.println("1.Add a person.");
        System.out.println("2.Delete a person.");
        System.out.println("3.Modify a person.");
        System.out.println("4.Print one person.");
        System.out.println("5.Print all people in console.");
        System.out.println("6.Write all object's to text file.");
        System.out.println("7.Exit");
        System.out.println();

        menuchoice = keyboard.nextInt();

/* This line is stupid and I hate it.
keyboard.nextInt() records numerical inputs as integers,
but when you press enter to confirm it it records \n 
so next time I open for input the memory is holding \n
so it automatically cancels the input. Therefore I have to
open the keyboard again and basically store it
to nothing so the \n or ENTER button can be cleared so next 
time I want to use my scanner object I can actually place input in.
*/
        keyboard.nextLine(); // <---- This one right here.
        System.out.println("You have chosen Menu Option > " + menuchoice);
        System.out.println();
        return menuchoice;
    }

//adds a contact by creating a new object and populating the data members by
//mutator functions.
    public static Person addContact(Scanner keyboard)
    {
        Person aperson = new Person();

            System.out.print("What is the person's first name:");
        aperson.setFirstName((keyboard.nextLine()).toLowerCase());
            System.out.print("What is the person's last name:");
        aperson.setLastName(keyboard.nextLine().toLowerCase());
            System.out.print("What is the person's Address:");
        aperson.setAddress(keyboard.nextLine());
            System.out.print("What state do they live in:");
        aperson.setState(keyboard.nextLine().toUpperCase());
            System.out.print("What is their zip-code:");
        aperson.setZip(keyboard.nextInt());
        
        //this line just clears the \n when you input an int.
        keyboard.nextLine();
        //ignore it.

        System.out.print("What is their phone number (###-###-####):");
        aperson.setPhoneNum(formatPhoneNumber(keyboard.nextLine(), keyboard));
        System.out.println();
        System.out.println("Success!");
        System.out.println();
        return aperson;
    }
    //This deletes a contact and takes the formating off so it can be converted to a long type variable.
    public static ArrayList<Person> delContact(Scanner keyboard, ArrayList<Person> phonebook)
    {
        System.out.print("Enter the phone number of the person you want to remove:");
        String pn = keyboard.nextLine();
        formatPhoneNumber(pn, keyboard);
        for(int i = 0; i < phonebook.size(); i++)
        {
            if(phonebook.get(i).getPhoneNum().equals(pn));
            {
                phonebook.remove(i);
                System.out.println("Contact deleted!");
                System.out.println();
            }
            if(i>phonebook.size())
            {
                System.out.println("Cannot find phone number.");
            }
        }
        return phonebook;
    }
    //this was the hardest function aand taakes in a temporary array of type long to use a binary search method of 
    //sorted phone numbers to find phone numbers faster than incrementally searching.
    public static ArrayList<Person> modContact(Scanner keyboard, ArrayList<Person> phonebook,long[] tempArr2) 
    {   
        System.out.println("Please give the phone number of the person you want to be modified: ");
        String pn = keyboard.nextLine();
        pn = formatPhoneNumber(pn, keyboard);
        long formPn = getPhoneNumWithoutDashes(pn);
        sortObjects(phonebook);
        int i = binarySearch(tempArr2, formPn, tempArr2.length, (tempArr2.length)-(tempArr2.length));
            System.out.println("Contact found. What would you like to change?");
            System.out.println();
            System.out.println("1.Change First name.");
            System.out.println("2.Change Last name.");
            System.out.println("3.Change Address.");
            System.out.println("4.Change State.");
            System.out.println("5.Change ZIP code.");
            System.out.println("6.Exit");
            System.out.println();
            int choice = keyboard.nextInt();

            //This line just clears the \n
            keyboard.nextLine();
            //ignore it.

            //this switch statement just takes modified answers and uses mutator functions to change the member variables
            switch(choice)
            {
                case 1:
                    System.out.println("What would you like to change their first name to:");
                    phonebook.get(i).setFirstName(keyboard.nextLine());
                    System.out.println("First name changed!");
                    break;
                case 2:
                    System.out.println("What would you like to change their last name to:");
                    phonebook.get(i).setLastName(keyboard.nextLine());
                    System.out.println("Last name changed!");
                    break;
                case 3:
                    System.out.println("What would you like to change their address to:");
                    phonebook.get(i).setAddress(keyboard.nextLine());
                    System.out.println("Address changed!");
                    break;
                case 4:
                    System.out.println("What would you like to change their state to:");
                    phonebook.get(i).setState(keyboard.nextLine());
                    System.out.println("State changed!");
                    break;
                case 5:
                    System.out.println("What would you like to change their ZIP code to:");
                    phonebook.get(i).setZip(keyboard.nextInt());
                    keyboard.nextLine();
                    System.out.println("ZIP code changed!");
                    break;
                case 6:
                    System.out.println("What would you like to change their first name to:");
                    phonebook.get(i).setFirstName(keyboard.nextLine());
                    System.out.println("Phone number changed!");
                    break;
            }
            System.out.println("--------------------------------------");
        return phonebook;
    }
    //This format function just makes all the string's consistent amongst all the phone number data members.
    public static String formatPhoneNumber(String phoneNumber, Scanner keyboard)
    {
        if((phoneNumber.charAt(3) != '-') && (phoneNumber.charAt(7) != '-'));
            {
                phoneNumber = phoneNumber.substring(0,3) + '-' + phoneNumber.substring(3,6) + '-' + phoneNumber.substring(6);
            }
        return phoneNumber;
    }
    //This function just takes the formatted phone number and removed the dashes and parse's it to long so it can be
    //read by a compare function imported.
    public static long getPhoneNumWithoutDashes(String phoneMod)
        {
            phoneMod = phoneMod.substring(0,3) + phoneMod.substring(4,7) + phoneMod.substring(8);
            long longPhoneNum = Long.valueOf(phoneMod); 

            return longPhoneNum;
        }
    //This function takes in the phonebook and uses the collections library and compareTo() member funciton of the library.
    //one the phonebook is passed in it can be sorted.
    public static ArrayList<Person> sortObjects(ArrayList<Person> phonebook)
    {
        Collections.sort(phonebook);
        return phonebook;
    }
    //This binary search algorithm recurses on itself by saying if its found the middle and the middle
    //is the correct value being searched for return that value, but if its lower than the returned value then it runs
    //the function again but sets the middle value from previous as the high, and if its lower then its sets the previous middle as the low.
    public static int binarySearch(long[] sortedArray, long find, int high, int low)
    {
        int middle = (high + low) / 2;

        if(high < low)
        {
            return -1;
        }
        if(find == sortedArray[middle])
        {
            return middle;
        }
        else if(find < sortedArray[middle])
        {
            return binarySearch(sortedArray, find, middle -1, low);
        }
        else
        {
            return binarySearch(sortedArray, find, middle +1, low);
        }
    }
    //This function just asks for the contact you want to find and prints all their information formatted
    public static void printContact(Scanner keyboard, ArrayList<Person> phonebook)
    {   
        System.out.println("Write the phone number of the contact you want to find.");
        System.out.println();
        String phoneNumber = keyboard.nextLine();


        for(int i = 0; i < phonebook.size(); i++)
        {
            if(phonebook.get(i).getPhoneNum().equals(phoneNumber))
            {
                System.out.println("Name: " + phonebook.get(i).getFirstName() + " "+ phonebook.get(i).getLastName());
                System.out.println("Address: " + phonebook.get(i).getAddress());
                System.out.println("State: " + phonebook.get(i).getState());
                System.out.println("ZIP: " + phonebook.get(i).getZip());
            }
            if(i > phonebook.size())
            {
                System.out.println("Cannot find Contact.");
                System.out.println();
            }

            
        }
    }
    //This function does the same as the previous function but has no conditions to be printed. Therefore all stored data is printed.
    public static void printAll(ArrayList<Person> phonebook)
    {
        for(int i = 0; i < phonebook.size(); i++)
        {
            System.out.println("--------------------");
            System.out.println("Name: " + phonebook.get(i).getFirstName() + " " + phonebook.get(i).getLastName());
            System.out.println("Address: " + phonebook.get(i).getAddress());
            System.out.println("State: " + phonebook.get(i).getState());
            System.out.println("ZIP: " + phonebook.get(i).getZip());
            System.out.println();
        }
    }
    //This function writes all the data again but now with a printwriter object passed in so it can be 
    //pasted in a text file known as addresses.txt
    public static void writeToFile(ArrayList<Person> phonebook, PrintWriter text)
    {
        for(int i = 0; i < phonebook.size();i++)
        {
            text.println("--------------------");
            text.println("Name: " + phonebook.get(i).getFirstName() + phonebook.get(i).getLastName());
            text.println("Address: " + phonebook.get(i).getAddress());
            text.println("State: " + phonebook.get(i).getState());
            text.println("ZIP: " + phonebook.get(i).getZip());
            text.println();
        }
    }

}
//Data was pre entered to reduce size of recording.