package hw1;

/**
 * The <code>Planner</code> class implements a list of <code>Course</code> objects
 * supporting methods which perform operations on such list.
 * @author Daniel Leong
 *      114430807
 *      Recitation 02
 **/
public class Planner {
    public static final int MAX_COURSES = 50;
    private Course[] courses;
    private int items_currently_in_List;

    /**
     * This is a constructor used to create an instance of Planner with no Course objects in it
     */
    public Planner() {
        courses = new Course[MAX_COURSES];
    }

    /**
     * A method that returns the number of courses in the planner.
     * @return
     *      The number of courses in the planner
     * <dt>Preconditions:</dt>
     *  <dd>This planner has been instatiated</dd>
     */
    public int size() {
        return items_currently_in_List;
    }

    /**
     * A method that adds a course to the planner at the desired position.
     * @param newCourse
     *         The course object that is going to be added to the planner
     * @param position
     *         The position at which the course object is added to the planner
     * @throws FullPlannerException
     *         Indicates that the planner cannot take any more courses.
     * @throws IllegalArgumentException
     *         When the inputted position is invalid or creates gaps in the planner.
     */
    public void addCourse(Course newCourse, int position) throws FullPlannerException {
        int p = position-1;
        if(p<0)
            throw new IllegalArgumentException("Error: Invalid position\n");
        if(p>size())
            throw new IllegalArgumentException("Error: No gaps between courses allowed in the planner\n");
        if(size() >= MAX_COURSES)
            throw new FullPlannerException("Error: There is no more room in the Planner to record an additional course\n");

        for(int i=size(); i>=p; i--) {
            if(size()==0) {
                courses[i] = newCourse;
                break;
            }
            else if(i==p)
                courses[i] = newCourse;
            else
                courses[i] = courses[i-1];
        }
        items_currently_in_List++;
    }

    /**
     * A method that adds a course to the end of the planner
     * @param newCourse
     *      The course object that is going to be added to the planner
     * @throws FullPlannerException
     *      Indicates when the planner cannot take any more courses.
     */
    public void addCourse(Course newCourse) throws FullPlannerException {   // check
        addCourse(newCourse, size() + 1);
    }

    /**
     * A method that removes a course at this position
     * @param position
     *      The position of the course that is to be removed.
     * @throws IllegalArgumentException
     *      When an invalid position is inputted or there is no course at the inputted position.
     */
    public void removeCourse(int position) {
        int p = position-1;
        if(p<0)
            throw new IllegalArgumentException("Invalid position\n");
        if(p>=size())
            throw new IllegalArgumentException("There is no course at position " + position + ".\n");
        Course save = courses[p];
        for(int i=p; i<size()-1; i++)
            courses[i] = courses[i+1];
        items_currently_in_List--;
        System.out.println(save.getDepartment() + " " + save.getCode() + ".0" +
                save.getSection() + " has been successfully removed from the planner.\n");
    }

    /**
     * A method that returns the course at the specified position
     * @param position
     *      position of the Course to retrieve
     * @return
     *      the course at the specified position of this Planner object
     * @throws IllegalArgumentException
     *      when an invalid position is specified or there is no course at the specified position
     */
    public Course getCourse(int position) {
        int p = position-1;
        if(p<0)
            throw new IllegalArgumentException("Invalid position\n");
        if(p>size()-1)
            throw new IllegalArgumentException("There is currently no course at position " + position + ".\n");
        return courses[p];   // should call toString
    }

    /**
     * A method that prints all the courses within the specified department
     * @param planner
     *      the ljst of courses which this method searches in
     * @param department
     *      the specified department to filter courses by
     */
    public static void filter(Planner planner, String department) {       // fix format to table
        System.out.println(String.format("%-6s%-22s%-12s%-9s%-10s%-16s", "No.", "Course Name", "Department", "Code", "Section", "Instructor"));
        System.out.println("-----------------------------------------------------------------------------------");
        for(int i=0; i<planner.size(); i++) {
            if(planner.courses[i].getDepartment().equals(department))
                System.out.println(String.format("%-6d%-23s%-12s%-9d%-10d%-16s", i+1, planner.courses[i].getName(),
                        planner.courses[i].getDepartment(), planner.courses[i].getCode(),
                        planner.courses[i].getSection(), planner.courses[i].getInstructor()));
        }
        System.out.print("\n");
    }

    /**
     * A method that checks whether a course is found within the planner
     * @param course
     *      The specified course which the method searches for
     * @return
     *      true if the course is found within the planner and false otherwise
     */
    public boolean exists(Course course) {
        for(int i=0; i<size(); i++) {
            if(courses[i].equals(course))
                return true;
        }
        return false;
    }

    /**
     * A method that compares the courses of the two planner objects
     * @param obj
     *      The planner object whose courses are compared with the planner which invokes this method.
     * @return
     *      true if the courses of the two planner objects are the same and false otherwise
     */
    public boolean equals(Object obj) {    // check
        if(obj instanceof Planner) {
            Planner p = (Planner) obj;
            for(int i=0; i<size(); i++) {
                if(!courses[i].equals(p.courses[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * A method which creates a copy of this Planner. Changes made to the copy will
     * not affect the original and vice versa
     * @return
     *      A deep clone of the planner object
     */
    public Object clone() {
        Planner planner_clone = new Planner();
        for(int i=0; i<size(); i++) {
            try {
                planner_clone.addCourse((Course) courses[i].clone());
            } catch (FullPlannerException e) {
                throw new RuntimeException(e);
            }
        }
        return planner_clone;
    }

    /**
     * A method which prints the planner (list of courses) in a neat table format
     */
    public void printAllCourses() {
        System.out.println("Planner: ");
        System.out.println(String.format("%-6s%-22s%-12s%-9s%-10s%-16s", "No.", "Course Name", "Department", "Code", "Section", "Instructor"));
        System.out.println("-----------------------------------------------------------------------------------");
        for(int i=0; i<size(); i++) {
            System.out.println(String.format("%-6d%-23s%-12s%-9d%-10d%-16s", i+1, courses[i].getName(), courses[i].getDepartment(), courses[i].getCode(), courses[i].getSection(), courses[i].getInstructor()) + "\n");
        }
    }

    /**
     * A method that gets the String representation of this Planner object
     * @return
     *      String representation of this Planner object
     */
    public String toString() {  // fix
        String output = String.format("%4s%-26s%-12s%-10s%-10s%-26s", "No.", "Course Name", "Department", "Code", "Section", "Instructor")
                + "\n -----------------------------------------------------------------------------------\n";
        for(int i=0; i<size(); i++) {
            output += String.format(String.valueOf(i+1) + courses[i]);
        }
        return output;
    }

    /**
     * A method which returns the specified course's position in the planner
     * @param c
     *      The specified course whose position needs to be found by the method
     * @return
     *      The specified course's position in the planner
     */
    public int findPosition(Course c) {
        for(int i=0; i<size(); i++) {
            if(c.equals(courses[i]))
                return i+1;
        }
        return -1;
    }
}
