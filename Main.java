import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //instaniate arrayList of Strings with the fileNames of the students
        Student bean = new Student("Cannibalistic Chicken");
        try {
            bean.importCoursesFromFile("saveFile.txt");
            printMainMenu(bean);
            getUserChoice(bean);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
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
                System.out.println("Enter a number: ");
                userInput.nextLine();
            }
        }
    }
    //get choice method
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
        else if (userChoice == 0) {
            currentStudent.printCoursesToFile("saveFile.txt");
            System.out.println("Exited programme!");
        }
        else {
            System.out.println("Enter an option: ");
            getUserChoice(currentStudent);
        }
    }
}
