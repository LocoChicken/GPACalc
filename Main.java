import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static ArrayList<String> nameList = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {
        //Step 1: Check studentNameList for existing students
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
    public static void printProfileMenu() {
        System.out.println("---------Profile Manager---------");
        System.out.println("0. Exit programme");
        System.out.println("1. Create new profile");
        System.out.println("2. Load profile");
        System.out.println("3. Edit profile name");
        System.out.println("4. Delete profile");
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
                System.out.print("Enter a number: ");
                userInput.nextLine();
            }
        }
    }
    public static void profileChoiceHandler(ArrayList<String> nameList, int userChoice) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        if (userChoice == 1) {
            //get name
            System.out.print("Enter a name: ");
            String studentName = scnr.nextLine();
            Student createdStudent = createProfile(nameList, studentName);
            printMainMenu(createdStudent);
            getUserChoice(createdStudent);
        }
        else if (userChoice == 2) {
            System.out.print("Enter a name: ");
            String studentName = scnr.nextLine();
            Student loadedStudent = loadProfile(nameList, studentName);
            loadProfile(nameList, studentName);
            printMainMenu(loadedStudent);
            getUserChoice(loadedStudent);
        }
        else if (userChoice == 3) {
            //Edit profile
            System.out.print("Choose a name to change: ");
            String nameToChange = scnr.nextLine();
            //check if the name exists
            if (!nameList.contains(nameToChange)) {
                System.out.println("Name does not exist.");
                swapToProfileManager();
            }
            //if it does...
            //change it in the arraylist
            //overwrite the file with new arraylist
            System.out.print("Enter a new name: ");
            String newName = scnr.nextLine();
            int indexOfTarget = nameList.indexOf(nameToChange);
            nameList.set(indexOfTarget, newName);
            printNamesToFile(nameList);
            //then find the file of nameToChange
            //then change that file name
            String oldFileName = generateFileName(nameToChange);
            File oldFile = new File(oldFileName);
            String newFileName = generateFileName(newName);
            File newFile = new File(newFileName);
            oldFile.renameTo(newFile);
            swapToProfileManager();
        }
        else if (userChoice == 4) {
            //delete profile
            System.out.print("Choose a name to delete: ");
            String nameToDelete = scnr.nextLine();
            //check if the name exists
            if (!nameList.contains(nameToDelete)) {
                System.out.println("Name does not exist.");
                swapToProfileManager();
            }
            //if it does...
            //deleting and overwritting namelist in the file
            int indexOfTarget = nameList.indexOf(nameToDelete);
            nameList.remove(indexOfTarget);
            printNamesToFile(nameList);
            //deleting file
            String fileNameToDelete = generateFileName(nameToDelete);
            File fileToDelete = new File(fileNameToDelete);
            fileToDelete.delete();
            swapToProfileManager();
        }
        else if (userChoice == 0) {
            System.out.println("Bai bai");
        }
        else if (userChoice == -1) {
            swapToProfileManager();
        }
        else {
            getProfileChoice(nameList);
        }
    }
    public static String generateFileName(String studentName) {
        //takes student name and converts it to the conventional file name
        String fileName = studentName;
        fileName = studentName.replaceAll(" ", "");
        fileName = fileName + "_saveFile.txt";
        return fileName;
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
    public static Student loadProfile(ArrayList<String> nameList, String studentName) throws FileNotFoundException {
        //check if name exists
        if (!nameList.contains(studentName)) {
            swapToProfileManager();            
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
    public static void printMainMenu(Student currentStudent) throws FileNotFoundException {
        System.out.println("---------GPA Calculator---------");
        System.out.println("0. Exit programme");
        System.out.println("1. Add course");
        System.out.println("2. Remove course");
        System.out.println("3. Edit course");
        System.out.println("4. Show course list");
        System.out.println("5. Import courses from file");
        System.out.println("6. Export courses to file");
        System.out.println("7. Calculate GPA");
        System.out.println("8. Input notes");
        System.out.println("9. Clear data");
        System.out.println("10. Return to profile manager");
        System.out.println();
    }
    public static void getUserChoice(Student currentStudent) throws FileNotFoundException {
        System.out.print("Choose an option (choose -1 to show menu): ");
        Scanner userInput = new Scanner(System.in);
        int userChoice = 0;
        boolean done = false;
        while (!done) {
            try {
                userChoice = userInput.nextInt();
                choiceHandler(currentStudent, userChoice);
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.print("Enter a number: ");
                userInput.nextLine();
            }
        }
    }
    public static void swapToProfileManager() throws FileNotFoundException {
        printProfileMenu();
        getProfileChoice(nameList);
    }
    public static void choiceHandler(Student currentStudent, int userChoice) throws FileNotFoundException {
        if (userChoice == 1)  {
            currentStudent.addCourse();
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == -1) {
            printMainMenu(currentStudent);
            getUserChoice(currentStudent);
        }
        else if (userChoice == 2) {
            currentStudent.removeCourse();
            getUserChoice(currentStudent);
            System.out.println("Choose a course to remove: ");
            System.out.println();
        }
        else if (userChoice == 3) {
            currentStudent.editCourse();
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 4) {
            currentStudent.printCourseList();
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 5) {
            Scanner scnr = new Scanner(System.in);
            System.out.print("Enter file name: ");
            String fileName = scnr.next();
            currentStudent.importCoursesFromFile(fileName);
            getUserChoice(currentStudent);
            System.out.println();

        }
        else if (userChoice == 6) {
            Scanner scnr = new Scanner(System.in);
            System.out.print("Enter file name: ");
            String fileName = scnr.next();
            currentStudent.printCoursesToFile(fileName);
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 7) {
            double GPA = currentStudent.calculateGPA();
            String judgment = currentStudent.judgment(GPA);
            System.out.println("---------GPA & Judgment---------");
            System.out.printf("GPA: %.2f\n", GPA);
            System.out.println(judgment);
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 8) {
            System.out.println("---------Input Notes---------");
            System.out.println("For class names, make them one word, usually with a dash (-). Example: FSE-100");
            System.out.println("For credit hours, input units.");
            System.out.println("For grade, refer to your syllabus for your professor's grading scale. Then use the following values for each letter grade input.");
            System.out.println("An A+ is 4.33");
            System.out.println("An A is 4.00");
            System.out.println("An A- is 3.67");
            System.out.println("A B+ is 3.33");
            System.out.println("A B is 3.00");
            System.out.println("A B- is 2.67");
            System.out.println("A C+ is 2.33");
            System.out.println("A C is 2.00");
            System.out.println("A D is 1.00");
            System.out.println("An F is 0.00");
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 9) {
            System.out.println("Data cleared!");
            currentStudent.clearData();
            getUserChoice(currentStudent);
            System.out.println();
        }
        else if (userChoice == 10) {
            //swap profiles/go back to profile manager
            currentStudent.updateCourseFile();
            currentStudent.clearData();
            //swap
            swapToProfileManager();
        }
        else if (userChoice == 0) {
            currentStudent.updateCourseFile();
            System.out.println("Exited programme!");
        }
        else {
            getUserChoice(currentStudent);
        }
    }
}
