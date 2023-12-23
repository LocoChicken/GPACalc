import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class MainCalc {
    public static ArrayList<String> nameList = new ArrayList<>();
    public static String directoryPath = System.getProperty("user.dir") + "/Save Files/";
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner fileReader = new Scanner(new FileInputStream("studentNameList.txt"))) {
            while (fileReader.hasNextLine()) {
                nameList.add(fileReader.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File named studentNameList.txt not found.");
        }
        swapToProfileManager();
    }
    //menu printer methods
    public static void printProfileMenu() {
        System.out.println("---------Profile Manager---------");
        System.out.println("0. Exit programme");
        System.out.println("1. Create new profile");
        System.out.println("2. Load profile");
        System.out.println("3. Edit profile name");
        System.out.println("4. Delete profile");
    }
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
    //get choice from user methods
    public static void getProfileChoice(ArrayList<String> nameList) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Choose an option (choose -1 to show menu): ");
        int userChoice = 0;
        boolean done = false;
        while (!done) {
            try {
                userChoice = scnr.nextInt();
                profileChoiceHandler(nameList, userChoice);
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.print(userChoice + " is an invalid input, enter a number: ");
                scnr.nextLine();
            }
        }
    }
    public static void getUserChoice(Student currentStudent) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Choose an option (choose -1 to show menu): ");
        int userChoice = 0;
        boolean done = false;
        while (!done) {
            try {
                userChoice = scnr.nextInt();
                choiceHandler(currentStudent, userChoice);
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.print(userChoice + " is an invalid input, enter a number: ");
                scnr.nextLine();
            }
        }
    }
    //choice handler methods
    public static void profileChoiceHandler(ArrayList<String> nameList, int userChoice) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        if (userChoice == 1) {
            String studentName = "";
            do {
                System.out.print("Enter a name (type \"cancel\" to cancel): ");
                studentName = scnr.nextLine();
                if (nameList.contains(studentName)) {
                    System.out.println("Name already exists.");
                }
            }
            while(nameList.contains(studentName));
            if (!studentName.equals("cancel")) {
                Student createdStudent = createProfile(nameList, studentName);
                printMainMenu(createdStudent);
                getUserChoice(createdStudent);
            }
            else {
                getProfileChoice(nameList);
            }
        }
        else if (userChoice == 2) {
            printProfiles();
            String studentName = "";
            do {
                System.out.print("Enter a name (type \"cancel\" to cancel): ");
                studentName = scnr.nextLine();
                if (!studentName.equals("cancel")) {
                    System.out.println("Name not found.");
                }
            }
            while (!nameList.contains(studentName) && !studentName.equals("cancel"));
            if (!studentName.equals("cancel")) {
                Student loadedStudent = loadProfile(nameList, studentName);
                loadProfile(nameList, studentName);
                printMainMenu(loadedStudent);
                getUserChoice(loadedStudent);
            }
            else {
                getProfileChoice(nameList);
            }
        }
        else if (userChoice == 3) {
            String nameToChange = "";
            do {
                System.out.print("Choose a name to change (type \"cancel\" to cancel): ");
                nameToChange = scnr.nextLine();
                if (!nameToChange.equals("cancel")) {
                    System.out.println("Name not found.");
                }
            }
            while (!nameList.contains(nameToChange) && !nameToChange.equals("cancel"));
            if (!nameToChange.equals("cancel")) {
                System.out.print("Enter a new name: ");
                String newName = scnr.nextLine();
                int indexOfTarget = nameList.indexOf(nameToChange);
                nameList.set(indexOfTarget, newName);
                printNamesToFile(nameList);
                String oldFileName = generateFileName(nameToChange);
                File oldFile = new File(directoryPath + oldFileName);
                String newFileName = generateFileName(newName);
                File newFile = new File(directoryPath + newFileName);
                oldFile.renameTo(newFile);
            }
            getProfileChoice(nameList);
        }
        else if (userChoice == 4) {
            String nameToDelete = "";
            do {
                System.out.print("Choose a name to delete: ");
                nameToDelete = scnr.nextLine();
                if (!nameToDelete.equals("cancel")) {
                    System.out.println("Name not found.");
                }
            }
            while (!nameList.contains(nameToDelete) && !nameToDelete.equals("cancel"));
            if (!nameToDelete.equals("cancel")) {
                int indexOfTarget = nameList.indexOf(nameToDelete);
                nameList.remove(indexOfTarget);
                printNamesToFile(nameList);
                String fileNameToDelete = generateFileName(nameToDelete);
                File fileToDelete = new File(directoryPath + fileNameToDelete);
                fileToDelete.delete();
            }
            getProfileChoice(nameList);
        }
        else if (userChoice == 0) {
            System.out.println("Bai bai");
        }
        else if (userChoice == -1) {
            printProfileMenu();
            getProfileChoice(nameList);
        }
        else {
            System.out.println("Invalid input, input the number in front of the desired option.");
            getProfileChoice(nameList);
        }
    }
    public static void choiceHandler(Student currentStudent, int userChoice) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        if (userChoice == 1)  {
            currentStudent.addCourse();
            getUserChoice(currentStudent);
        }
        else if (userChoice == -1) {
            printMainMenu(currentStudent);
            getUserChoice(currentStudent);
        }
        else if (userChoice == 2) {
            currentStudent.removeCourse();
            getUserChoice(currentStudent);
        }
        else if (userChoice == 3) {
            currentStudent.editCourse();
            getUserChoice(currentStudent);
        }
        else if (userChoice == 4) {
            currentStudent.printCourseList();
            getUserChoice(currentStudent);
        }
        else if (userChoice == 5) {
            System.out.print("Enter file name: ");
            String fileName = scnr.next();
            currentStudent.importCoursesFromFile(fileName);
            getUserChoice(currentStudent);

        }
        else if (userChoice == 6) {
            System.out.print("Enter file name: ");
            String fileName = scnr.next();
            currentStudent.printCoursesToFile(fileName);
            getUserChoice(currentStudent);
        }
        else if (userChoice == 7) {
            double GPA = currentStudent.calculateGPA();
            String judgment = currentStudent.judgment(GPA);
            System.out.println("---------GPA & Judgment---------");
            System.out.printf("GPA: %.2f\n", GPA);
            System.out.println(judgment);
            getUserChoice(currentStudent);
        }
        else if (userChoice == 8) {
            System.out.println("---------Input Notes---------");
            System.out.println("For class names, make them one word, usually with a dash (-). Example: FSE-100");
            System.out.println("For credit hours, input units.");
            System.out.println("For grade, refer to your syllabus for your professor's grading scale. Then use the following values for each letter grade input.");
            System.out.println("Your grade in parenthesis (A+ for example) usually shows next to your numerical percentage grade in Canvas.");
            System.out.println("Enter the number associated with the letter grade below.");
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
        }
        else if (userChoice == 9) {
            System.out.println("Data cleared!");
            currentStudent.clearData();
            getUserChoice(currentStudent);
        }
        else if (userChoice == 10) {
            String fileName = generateFileName(currentStudent.getStudentName());
            currentStudent.printCoursesToFile(fileName);
            swapToProfileManager();
        }
        else if (userChoice == 0) {
            String fileName = generateFileName(currentStudent.getStudentName());
            currentStudent.printCoursesToFile(fileName);
            System.out.println("Bai bai!");
        }
        else {
            getUserChoice(currentStudent);
        }
    }
    //create and load profile methods
    public static Student createProfile(ArrayList<String> nameList, String studentName) throws FileNotFoundException {
        Student currentStudent = new Student(studentName);
        String fileName = generateFileName(currentStudent.getStudentName());
        currentStudent.printCoursesToFile(fileName);
        nameList.add(studentName);
        printNamesToFile(nameList);
        return currentStudent;
    }
    public static Student loadProfile(ArrayList<String> nameList, String studentName) throws FileNotFoundException {
        Student loadedStudent = new Student(studentName);
        String fileName = generateFileName(loadedStudent.getStudentName());
        try {
            loadedStudent.importCoursesFromFile(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println("File with name "+ fileName + " not found");
            //maybe create file in case of this issue? dunno how tho
        }
        return loadedStudent;
    }
    //print profiles (from studentNameList.txt)
    public static void printProfiles() {
        int counter = 1;
        System.out.println("---------Name List---------");
        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(counter + ". " + nameList.get(i));
            counter++;
        }
    }
    //method for generating what the corresponding file name would be based on a name (strings)
    public static String generateFileName(String studentName) {
        String fileName = studentName;
        fileName = studentName.replaceAll(" ", "");
        fileName = fileName + "_saveFile.txt";
        return fileName;
    }
    //prints names currently in the nameList ArrayList into the studentNamesList.txt file
    public static void printNamesToFile(ArrayList<String> nameList) throws FileNotFoundException {
        PrintWriter nameListWriter = new PrintWriter("studentNameList.txt");
        for (int i = 0; i < nameList.size(); i++) {
            nameListWriter.println(nameList.get(i));
        }
        nameListWriter.close();
    }
    //calls the printProfileMenu and getProfileChoice methods. FIX: causes a recursive call, which sometimes prevents the programme from being exited when the user wants it to
    public static void swapToProfileManager() throws FileNotFoundException {
        printProfileMenu();
        getProfileChoice(nameList);
    }
}
