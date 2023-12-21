import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Student {
    private String studentName = "";
    private int numCourses = 0;
    private double numCredits = 0.0;
    private ArrayList<Course> courseList = null;
    
    public Student(String name) {
        this.studentName = name;
        this.courseList = new ArrayList<Course>();
    }

    public void addCourse() {
        //when called on a student object, this method should prompt for the course info
        //then it should add it to the student's courseList
        //create the course
        double creditCount = 0;
        double courseGrade = 0.00;
        Scanner courseAdder = new Scanner(System.in);
        System.out.print("Enter the name of the course: ");
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
        //show course list then...
        this.printCourseList();
        Scanner courseRemover = new Scanner(System.in);
        //could tell user to type name of course to remove or tell them to choose a number
        System.out.print("Type a course name from the list: ");
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
        //edit if it exists
        if (this.courseExists(choice)) {
            //edit course if it exists
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
        //maybe make into a list
        int itemNumber = 1;
        System.out.println("---------" + this.studentName + "'s Course List---------");
        if (this.courseList.size() == 0) {
            System.out.println("No courses added!");
        }
        System.out.println("Number of courses: " + this.numCourses);
        System.out.println("Number of credits: " + this.numCredits);
        for (int i = 0; i < this.courseList.size(); i++) {
            //call printCourseInfo on each element
            System.out.println("Item number " + itemNumber);
            this.courseList.get(i).printCourseInfo();
            itemNumber++;
        }
        System.out.println();
    }
    public int getCourseNumber() {
        return this.numCourses;
    }
    public Course getCourseByName(String name) {//only call this if course exists
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
        //check if file exists, if it does, override the contents
        //if it doesnt, create new file
        
        //shouldn't need to check if courseList is valid bc that is already checked
        //visit each arraylist element
            //add the values of the object
            //go to next line
        PrintWriter fileWriter = new PrintWriter(fileName);
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
        //check if file exists
        try (Scanner fileReader = new Scanner(new FileInputStream(fileName))) {
            while (fileReader.hasNextLine()) {
                //check each line individually for a string, double, double
                    //run addCourse if the line is valid
                    //else, move to next line
                //if file ends, exit 
                String currentLine = fileReader.nextLine();
                //Scanner lineReader = new Scanner(currentLine);//scanner to only read currentLine
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
        //check if file contents are valid
    }
    public double calculateGPA() {
        //earned credits/possible credits
        //earned credits = grade * credits
        //credits possible = sum of credits
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
        if (GPA >= 4.00) {//perfect
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
    public double getTotalCreditHours() {//returns total count of credit hours
        //iterate thru each element in courseList and get the sum of the credit hours fields
        double totalHours = 0;
        for (int i = 0; i < this.courseList.size(); i++) {
            Course currentCourse = this.courseList.get(i);
            totalHours = totalHours + currentCourse.getCourseCredits();
        }
        return totalHours;
    }
    public double getWeightedGrade() {//returns grade points * credit hours in each class
        //iterate thru each element and multiply the grade * credits. add this to the sum
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
    public void quickSortFML() {
        quicksortHelperFML(0, this.courseList.size() - 1);
    }
    private int partition(int startIndex, int endIndex) {
        //returns partition index
        Course pivotCourse = this.courseList.get(endIndex);
        String pivotValue = pivotCourse.getCourseName();
        int savedIndex = 0;
        for (int currentIndex = 0; currentIndex < endIndex; currentIndex++) {
            Course currentCourse = this.courseList.get(currentIndex);
            String currentName = currentCourse.getCourseName();
            if (currentName.compareTo(pivotValue) <= 0) {
                this.swap(currentIndex, savedIndex);
                savedIndex++;
            }
        }
        swap(endIndex, savedIndex);
        return savedIndex;
    }
    private void quicksortHelperFML(int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int partitionIndex = this.partition(startIndex, endIndex);

        this.quicksortHelperFML(startIndex, partitionIndex - 1);
        this.quicksortHelperFML(partitionIndex + 1, endIndex);
    }
    private void swap(int index1, int index2) {
        Course temp = this.courseList.get(index1);
        this.courseList.set(index1, this.courseList.get(index2));
        this.courseList.set(index2, temp);
    }
}
