import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class Student {
    String directoryPathMac = "/workspaces/GPACalc/Save Files/";
    private String studentName = "";
    private int numCourses = 0;
    private double numCredits = 0.0;
    private String courseListFileName = null;
    private ArrayList<Course> courseList = null;
    
    public Student(String name) {
        this.studentName = name;
        this.courseList = new ArrayList<Course>();
    }
    public String getStudentName() {
        return this.studentName;
    }
    public void setStudentName(String name) {
        this.studentName = name;
    }
    public String getCourseListFileName() {
        return this.courseListFileName;
    }
    public void setFileName(String fileName) {
        //just sets it
        this.courseListFileName = fileName;
    }
    public String createFileName(String studentName) {
        //takes student name and converts it to the conventional file name
        String fileName = studentName;
        fileName = studentName.replaceAll(" ", "");
        fileName = fileName + "_saveFile.txt";
        this.courseListFileName = fileName;
        return this.courseListFileName;
    }
    public void updateCourseFile() throws FileNotFoundException {
        //creates file and prints data to file
        String fileName = this.createFileName(studentName);
        //also creates if it doesn't exist
        printCoursesToFile(fileName);
    }
    public ArrayList<Course> getStudentCourses() {
        return this.courseList;
    }
    public void addCourse() {
        double creditCount = 0;
        double courseGrade = 0.00;
        Scanner courseAdder = new Scanner(System.in);
        System.out.print("Enter the name of the course to add: ");
        String courseName = courseAdder.next();
        if (courseExists(courseName)) {
            System.out.println("Course already exists!");
            System.out.println();
            return;
        }
        boolean done = false;
        while (!done) {
            try {
                System.out.print("Enter the number of credits this course is worth: ");
                creditCount = courseAdder.nextDouble();
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a number");
                courseAdder.nextLine();
            }
        }
        done = false;
        while (!done) {
            try {
                System.out.print("Enter your grade in the course: ");
                courseGrade = courseAdder.nextDouble();
                done = true;
            }
            catch (InputMismatchException e) {
                System.out.print("Enter a number");
                courseAdder.nextLine();
            }
        }
        Course courseToAdd = new Course(courseName, creditCount, courseGrade);
        courseToAdd.printCourseInfo();
        this.courseList.add(courseToAdd);
        this.numCourses++;
        this.numCredits = this.numCredits + creditCount;
        System.out.println(courseToAdd.getCourseName() + " added to course list!");
        System.out.println();
    }
    public void addCourse(String courseName, double courseCredits, double courseGrade) {
        Course courseToAdd = new Course(courseName, courseCredits, courseGrade);
        courseToAdd.printCourseInfo();
        this.courseList.add(courseToAdd);
        System.out.println(courseToAdd.getCourseName() + " added to course list!");
        this.numCourses++;
        this.numCredits = this.numCredits + courseCredits;
        System.out.println();
    }
    public void removeCourse() {
        this.printCourseList();
        Scanner courseRemover = new Scanner(System.in);
        System.out.print("Type a course name to remove: ");
        String choice = courseRemover.nextLine();
        if (this.courseExists(choice)) {
            Course courseToRemove = getCourseByName(choice);
            courseList.remove(courseToRemove);
            System.out.println(courseToRemove.getCourseName() + " removed from list!");
            this.numCourses--;
            System.out.println();
        }
    }
    public void editCourse() {
        this.printCourseList();
        Scanner courseEditor = new Scanner(System.in);
        System.out.print("Choose a course to edit by name: ");
        String choice = courseEditor.next();
        if (this.courseExists(choice)) {
            Course courseToEdit = this.getCourseByName(choice);
            System.out.print("Enter a name: ");
            String newName = courseEditor.next();
            courseToEdit.setCourseName(newName);
            System.out.print("Enter number of credits: ");
            double newCredits = courseEditor.nextDouble();
            courseToEdit.setCourseCredits(newCredits);
            System.out.print("Enter a grade: ");
            double newGrade = courseEditor.nextDouble();
            courseToEdit.setCourseGrade(newGrade);
            courseToEdit.printCourseInfo();
        }
        else {
            System.out.println("Course does not exist");
        }
    }
    public void printCourseList() {
        int itemNumber = 1;
        System.out.println("---------" + this.studentName + "'s Course List---------");
        if (this.courseList.size() == 0) {
            System.out.println("No courses added!");
        }
        System.out.println("Number of courses: " + this.numCourses);
        System.out.println("Number of credits: " + this.numCredits);
        for (int i = 0; i < this.courseList.size(); i++) {
            System.out.println("Item number " + itemNumber);
            this.courseList.get(i).printCourseInfo();
            itemNumber++;
        }
        System.out.println();
    }
    public int getCourseNumber() {
        return this.numCourses;
    }
    public Course getCourseByName(String name) {
        boolean found = false;
        int currentIndex = 0;
        Course currentCourse = null;
        if (courseExists(name) == true) {
            while (found == false && currentIndex < this.courseList.size()) {
                currentCourse = this.courseList.get(currentIndex);
                if (currentCourse.getCourseName().equals(name)) {
                    found = true;
                }
                else {
                    currentIndex++;
                }
            }
        }
        return currentCourse;
    }
    public boolean courseExists(String name) {
        boolean found = false;
        int currentIndex = 0;
        while (found == false && currentIndex < this.courseList.size()) {
            Course currentCourse = this.courseList.get(currentIndex);
            if (currentCourse.getCourseName().equals(name)) {
                found = true;
            }
            currentIndex++;
        }
        return found;
    }
    public void printCoursesToFile(String fileName) throws FileNotFoundException {
        File saveFile = new File(directoryPathMac + fileName);
        try {
            saveFile.createNewFile();
        }
        catch (IOException e) {
            System.out.println("File might already exist, all is fine.");
        }
        PrintWriter fileWriter = new PrintWriter(saveFile);
        for (int i = 0; i < this.courseList.size(); i++) {
            Course currentCourse = this.courseList.get(i);
            fileWriter.print(currentCourse.getCourseName() + " ");
            fileWriter.print(currentCourse.getCourseCredits() + " ");
            fileWriter.println(currentCourse.getCourseGrade());
        }
        fileWriter.close();
        System.out.println("Courses added to file!");
    }
    public void importCoursesFromFile(String fileName) throws FileNotFoundException {
        try (Scanner fileReader = new Scanner(new FileInputStream(directoryPathMac + fileName))) {
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                try (Scanner lineReader = new Scanner(currentLine)){
                    String courseName = lineReader.next();
                    double credits = lineReader.nextDouble();
                    double grade = lineReader.nextDouble();
                    this.addCourse(courseName, credits, grade);
                }
                catch (InputMismatchException e) {
                    System.out.println("Failed to add a course! (Input mismatch)");
                    System.out.println();
                }
                catch (NoSuchElementException e) {
                    System.out.println("Failed to add a course! (Missing element)");
                    System.out.println();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    public double calculateGPA() {
        double GPA = 0.0;
        double earnedCredits = this.getWeightedGrade();
        double possibleCredits = this.getTotalCreditHours();
        GPA = earnedCredits / possibleCredits;
        if (GPA > 4.00) {
            GPA = 4.00;
        }
        return GPA;
    }
    public String judgment(double GPA) {
        String judgment = null;
        if (GPA >= 4.00) {
            judgment = "Acceptable, you can come home to dinner.";
        }
        else if (GPA >= 3.00) {
            judgment = "You can come home but no dinner.";
        }
        else if (GPA >= 2.00) {
            judgment = "Don't come home.";
        }
        else if (GPA >= 1.00) {
            judgment = "You have no home.";
        }
        else {
            judgment = "We never gave birth to you.";
        }
        return judgment;
    }
    public double getTotalCreditHours() {
        double totalHours = 0;
        for (int i = 0; i < this.courseList.size(); i++) {
            Course currentCourse = this.courseList.get(i);
            totalHours = totalHours + currentCourse.getCourseCredits();
        }
        return totalHours;
    }
    public double getWeightedGrade() {
        double totalWeightedGrade = 0.0;
        for (int i = 0; i < this.courseList.size(); i++) {
            Course currentCourse = this.courseList.get(i);
            double currentCourseWeightGrade = currentCourse.getCourseCredits() * currentCourse.getCourseGrade();
            totalWeightedGrade = totalWeightedGrade + currentCourseWeightGrade;
        }
        return totalWeightedGrade;
    }
    public void clearData() {
        this.courseList.clear();
        this.numCourses = 0;
        this.numCredits = 0;
    }
}
