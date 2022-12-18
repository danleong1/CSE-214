package hw1;

import java.util.Scanner;

/**
 * The <code>PlannerManager</code> class implements a menu-based application which
 * performs operations on an empty <code>Planner</code> object.
 * @author Daniel Leong
 *      114430807
 *      Recitation 02
 **/
public class PlannerManager {
    public static void main(String[] args){
        Planner planner = new Planner();
        Planner planner_clone = new Planner();
        Scanner input = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Enter Selection: ");
            String letter = input.nextLine();
            System.out.println("");
            if (letter.equalsIgnoreCase("A")) {
                System.out.print("Enter course name: ");
                String name = input.nextLine();
                System.out.print("Enter department: ");
                String department = input.nextLine();
                System.out.print("Enter course code: ");
                String code_str = input.nextLine();
                int code = Integer.parseInt(code_str);
                System.out.print("Enter course section: ");
                String section_str = input.nextLine();
                byte section = Byte.parseByte(section_str);
                System.out.print("Enter instructor: ");
                String instructor = input.nextLine();
                System.out.print("Enter position: ");
                String position_str = input.nextLine();
                int position = Integer.parseInt(position_str);
                Course newCourse = new Course(name, department, instructor, code, section);
                try {
                    newCourse.setCode(code);
                    newCourse.setSection(section);
                    planner.addCourse(newCourse, position);
                    System.out.println(newCourse.getDepartment() + " " + newCourse.getCode() + ".0" + newCourse.getSection() + " successfully added to planner.\n");
                } catch (FullPlannerException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (letter.equalsIgnoreCase("G")) {
                System.out.print("Enter position: ");
                String position_str = input.nextLine();
                int position = Integer.parseInt(position_str);
                //for(int i=0; i<planner.getPlanner().length; i++) {

                //}
                try {
                    System.out.println(String.format("%4s%-26s%-12s%-10s%-10s%-26s", "No.", "Course Name", "Department",
                            "Code", "Section", "Instructor"));
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println(String.valueOf(position) + "   " + planner.getCourse(position));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (letter.equalsIgnoreCase("r")) {
                System.out.print("Enter position: ");
                String position_str = input.nextLine();
                int position = Integer.parseInt(position_str);
                try {
                    planner.removeCourse(position);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (letter.equalsIgnoreCase("P")) {
                planner.printAllCourses();
            }
            else if (letter.equalsIgnoreCase("f")) {
                System.out.print("Enter department: ");
                String department = input.nextLine();
                planner.filter(planner, department);
            }
            else if (letter.equalsIgnoreCase("L")) {
                System.out.print("Enter course name: ");
                String name = input.nextLine();
                System.out.print("Enter department: ");
                String department = input.nextLine();
                System.out.print("Enter course code: ");
                String code_str = input.nextLine();
                int code = Integer.parseInt(code_str);
                System.out.print("Enter course section: ");
                String section_str = input.nextLine();
                byte section = Byte.parseByte(section_str);
                System.out.print("Enter instructor: ");
                String instructor = input.nextLine();
                Course c = new Course(name, department, instructor, code, section);
                if (planner.exists(c))
                    System.out.println(department + " " + code + ".0" + section + " is found in the planner at position "
                            + planner.findPosition(c) + ".\n");
                else
                    System.out.println("Course not found in the planner.\n");
                }
            else if (letter.equalsIgnoreCase("s")) {
                System.out.println("There are " + planner.size() + " courses in the planner.\n");
            }
            else if (letter.equalsIgnoreCase("b")) {
                planner_clone = (Planner) planner.clone();
                System.out.println("Created a backup of the current planner.\n");
            }
            else if (letter.equalsIgnoreCase("pb")) {
                planner_clone.printAllCourses();
            }
            else if (letter.equalsIgnoreCase("rb")) {
                Planner planner_clone_clone = (Planner) planner_clone.clone();
                planner = planner_clone_clone;
                System.out.println("Planner successfully reverted to the backup copy.\n");
            }
            else if (letter.equalsIgnoreCase("Q")) {
                System.out.println("Program terminating successfully...");
                break;
            }
        }
    }

    /**
     * A method which prints the menu of operations
     */
    public static void printMenu() {
        System.out.println("(A) Add Course\n" +
                "(G) Get Course\n" +
                "(R) Remove Course\n" +
                "(P) Print Courses in Planner\n" +
                "(F) Filter by Department Code\n" +
                "(L) Look For Course\n" +
                "(S) Size\n" +
                "(B) Backup\n" +
                "(PB) Print Courses in Backup\n" +
                "(RB) Revert to Backup\n" +
                "(Q) Quit");
    }
}