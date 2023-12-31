import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GPACalcGUI extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            calcGUI.setVisible(true);
            printProfileMenuGUI();
            getProfileChoiceGUI(nameList);
        });
    }
    public static GPACalcGUI calcGUI = new GPACalcGUI();
    public static ArrayList<String> nameList = new ArrayList<>();
    public static String directoryPath = System.getProperty("user.dir") + "/Save Files/";
    private JTextField inputField;
    private JTextArea outputArea;
    private int caseID = 0;

    public GPACalcGUI() {
        setTitle("Terminal GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        inputField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        //profile manager
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (caseID == 0) {
                    try {
                        processProfileCommand(inputField.getText());
                    }
                    catch (FileNotFoundException w) {

                    }
                }
                else if (caseID == 1) {
                    processChoiceCommand(inputField.getText());
                }
                inputField.setText(""); // Clear the input field after processing
            }
        });
        //student menu

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
    }
    public static void printProfileMenuGUI() {
        calcGUI.printToGui("---------Profile Manager---------");
        calcGUI.printToGui("0. Exit programme");
        calcGUI.printToGui("1. Create profile");
        calcGUI.printToGui("2. Load profile");
        calcGUI.printToGui("3. Edit profile name");
        calcGUI.printToGui("4. Delete profile");
    }
    public static void printMainMenuGUI(Student curerentStudent) {
        calcGUI.printToGui("---------GPA Calculator---------");
        calcGUI.printToGui("0. Exit programme");
        calcGUI.printToGui("1. Add course");
        calcGUI.printToGui("2. Remove course");
        calcGUI.printToGui("3. Edit course");
        calcGUI.printToGui("4. Show course list");
        calcGUI.printToGui("5. Import courses from file");
        calcGUI.printToGui("6. Export courses to file");
        calcGUI.printToGui("7. Calculate GPA");
        calcGUI.printToGui("8. Input notes");
        calcGUI.printToGui("9. Clear data");
        calcGUI.printToGui("10. Return to profile manager");
    }
    public void processProfileCommand(String command) throws FileNotFoundException {
        // Redirect the output to the GUI
        // Process different types of commands
        if (!command.equals("")) {
            if (command.equalsIgnoreCase("1")) {
                calcGUI.printToGui("1");
                printToGui("Create user profile command called.");
                profileChoiceHandlerGUI(nameList, 1);
                caseID = 1;
            }
            else if (command.equalsIgnoreCase("2")) {
                calcGUI.printToGui("2");
                printToGui("Load user profile called.");
                //call handler
                caseID = 1;
            }
            else if (command.equalsIgnoreCase("3")) {
                calcGUI.printToGui("3");
                printToGui("Edit profile name command called.");
                //call handler
                caseID = 0;
            }
            else if (command.equalsIgnoreCase("4")) {
                calcGUI.printToGui("4");
                printToGui("Delete profile command called.");
                //call handler
                caseID = 0;
            }
            else if (command.equalsIgnoreCase("-1")) {
                calcGUI.printToGui("-1");
                printProfileMenuGUI();
            }
            else {
                printToGui("Unknown command: " + command);
            }
        }
    }
    public void processChoiceCommand(String command) {
        Student currentStudent = new Student(this.getName());
        printMainMenuGUI(currentStudent);
        if (command.equalsIgnoreCase("1")) {
            printToGui("Add course command called");
            //call add course method
        }
        else if (command.equalsIgnoreCase("2")) {
            printToGui("Remove course command called.");
            //call remove course method
        }
        else if (command.equalsIgnoreCase("3")) {
            printToGui("Edit course command called.");
            //call edit course method
        }
        else if (command.equalsIgnoreCase("4")) {
            printToGui("Print course list command called.");
            //call print course list method
        }
        else if (command.equalsIgnoreCase("5")) {
            printToGui("Import courses from file command called.");
            //call import courses from file method
        }
        else if (command.equalsIgnoreCase("6")) {
            printToGui("Export courses to file command called.");
            //call export courses to file method
        }
        else if (command.equalsIgnoreCase("7")) {
            printToGui("Calculate GPA command called.");
            //call calculate gpa method
        }
        else if (command.equalsIgnoreCase("8")) {
            printToGui("Show input notes command called.");
            //call show input notes method
        }
        else if (command.equalsIgnoreCase("9")) {
            printToGui("Clear data command called.");
            //call clear data method
        }
        else if (command.equalsIgnoreCase("10")) {
            printToGui("Return to profile manager command called.");
            //call return to profile manager method
            caseID = 0;
        }
        else if (command.equalsIgnoreCase("-1")) {
            printMainMenuGUI(currentStudent);
        }
        else {
            printToGui("Unknown command: " + command);
        }
    }
    public static void getProfileChoiceGUI(ArrayList<String> nameList) {
        calcGUI.printToGUINoln("Choose an option (choose -1 to show menu): ");
        try {
            calcGUI.processProfileCommand(calcGUI.getGUIText());
        }
        catch (FileNotFoundException w) {

        }
        calcGUI.setGUIText("");
    }
    public static void getUserChoiceGUI(Student currentStudent) {
        calcGUI.printToGUINoln("Choose an option (choose -1 to show menu): ");
        calcGUI.processChoiceCommand(calcGUI.getGUIText());
        calcGUI.setGUIText("");
    }
    public static void profileChoiceHandlerGUI(ArrayList<String> nameList, int userChoice) throws FileNotFoundException {
        if (userChoice == 1) {
            if (!calcGUI.getGUIText().equals("")) {
                calcGUI.setGUIText("");
                calcGUI.printToGUINoln("Enter a name: ");
                String profileName = calcGUI.getGUIText();
                calcGUI.printToGui(profileName);
                if (!profileName.equals("")) {
                    Student currentStudent = MainCalc.createProfile(nameList, profileName);
                    printMainMenuGUI(currentStudent);
                    getUserChoiceGUI(currentStudent);
                }
            }
        }
        //create profile
            //get profile name from user (checking arraylist for dupes)
            //call create profile method
        //load profile
            //get profile name from user (checking arraylist for match)
            //call load profile method
        //edit profile
            //get profile name to edit from user
            //call edit profile method
        //delete profile
            //get profile name to delete from user
            //call delete profile
    }
    public static void choiceHandlerGUI(Student currentStudent, int userChoice) {
        //e
    }
    public void printToGui(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    public void printToGUINoln(String message) {
        outputArea.append(message);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    public String getGUIText() {
        return inputField.getText();
    }
    public void setGUIText(String message) {
        inputField.setText(message);
    }
}
