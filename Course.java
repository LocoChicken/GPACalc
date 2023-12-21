public class Course {
    public final double A_Plus = 4.33;
    public final double A = 4.00;
    public final double A_Minus = 3.67;
    public final double B_Plus = 3.33;
    public final double B = 3.00;
    public final double B_Minus = 2.67;
    public final double C_Plus = 2.33;
    public final double C = 2.00;
    public final double D = 1.00;
    public final double E = 0.00;


    private String courseName = "";
    private double numCredits = 0;
    private double courseGrade = 0;

    public Course(String name, double credits, double grade) {
        this.courseName = name;
        this.numCredits = credits;
        this.courseGrade = grade;
    }

    public void printCourseInfo() {
        System.out.println("Course name: " + this.getCourseName());
        System.out.println("Course credit count: " + this.getCourseCredits());
        System.out.println("Course grade: " + this.getCourseGrade());
    }
    public String getCourseName() {
        return this.courseName;
    }
    public double getCourseCredits() {
        return this.numCredits;
    }
    public double getCourseGrade() {
        return this.courseGrade;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setCourseCredits(double courseCredits) {
        this.numCredits = courseCredits;
    }
    public void setCourseGrade(double courseGrade) {
        this.courseGrade = courseGrade;
    }
}
