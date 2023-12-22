import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class StudentManager {
    //has an arrayList of the student names. upon being called, it will import all names to that list from the fileStudentNameList
    public static void startup() throws FileNotFoundException {
        //Step 1: Check studentNameList for existing students
        ArrayList<String> nameList = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new FileInputStream("studentNameList.txt"))) {
            while (fileReader.hasNextLine()) {
                nameList.add(fileReader.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (NullPointerException e) {
            System.out.println("No classes found");
        }
        //Step 2: show menu for adding, loading, editing, and deleting a student
            //menu thingie
        printProfileMenu();
        //Step 3: get user input for choices
        getProfileChoice(nameList);
    }
    //create profile method
        //take string for student name, ret nothing (the name is gotten in the menu handler thing)
        //creates file using student name 
        //adds student name to studentNameList
        //ends with prompting for user to input another option
    public static void printProfileMenu() {
        System.out.println("1. Create new profile");
        System.out.println("2. Load profile");
    }
    public static void getProfileChoice(ArrayList<String> nameList) throws FileNotFoundException {
        //e
        System.out.print("Choose an option (choose -1 to show menu): ");
        Scanner userInput = new Scanner(System.in);
        int userChoice = 0;
        boolean done = false;
        while (!done) {
            try {
                userChoice = userInput.nextInt();
                profileChoiceHandler(nameList, userChoice);
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a number: ");
                userInput.nextLine();
            }
        }
    }
    public static void profileChoiceHandler(ArrayList<String> nameList, int userChoice) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        if (userChoice == 1) {
            //get name
            System.out.print("Eneter a name: ");
            String studentName = scnr.nextLine();
            Student createdStudent = createProfile(nameList, studentName);
            Main.printMainMenu(createdStudent);
            Main.getUserChoice(createdStudent);
        }
        else if (userChoice == 2) {
            System.out.print("Eneter a name: ");
            String studentName = scnr.nextLine();
            Student loadedStudent = loadProfile(nameList, studentName);
            loadProfile(nameList, studentName);
            Main.printMainMenu(loadedStudent);
            Main.getUserChoice(loadedStudent);
        }
    }
    public static Student createProfile(ArrayList<String> nameList, String studentName) throws FileNotFoundException {
        Student currentStudent = new Student(studentName);
        currentStudent.updateCourseFile();//creates and updates course file 
        nameList.add(studentName);
        printNamesToFile(nameList);
        return currentStudent;
        //ask to do something else
    }
    public static void printNamesToFile(ArrayList<String> nameList) throws FileNotFoundException {
        PrintWriter nameListWriter = new PrintWriter("studentNameList.txt");
        for (int i = 0; i < nameList.size(); i++) {
            nameListWriter.println(nameList.get(i));
        }
        nameListWriter.close();
     }
    //load profile method
    public static Student loadProfile(ArrayList<String> nameList, String studentName) {
        //check if name exists
        if (!nameList.contains(studentName)) {
            //cry about it (make sure to return to menu and not let programme continue)
        }
        //if yes...
        //import courses from corresponding file
        Student loadedStudent = new Student(studentName);
        try {
            String fileName = loadedStudent.createFileName(loadedStudent.getStudentName());
            System.out.println(fileName);
            loadedStudent.importCoursesFromFile(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (NullPointerException e) {
            System.out.println(loadedStudent.getCourseListFileName() + " caused a nullpointer excelptiodn");
        }
        return loadedStudent;
        //print main menu
        //get user choice
    }
    //edit profile method (changes the name of the student and the file)
    //delete profile method (deletes the student from the file and deletes their corresponding file) aka replace the file data with all students besdies deleted one

}