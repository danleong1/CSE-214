package hw1;

/**
 * The <code>Course</code> class implements <code>Course</code> objects
 * and makes their corresponding attributes accessible and able to be mutated.
 * @author Daniel Leong
 *       114430807
 *       Recitation 02
 */
public class Course {
    private String name, department, instructor;
    private int code;
    private byte section;

    /**
     * This is a constructor used to create a new Course object
     */
    public Course(){
    }

    /**
     * This is a constructor used to create a new Course object
     * @param name
     *      The name of the course
     * @param department
     *      The department of the course
     * @param instructor
     *      The instructor of the course
     * @param code
     *      The code of the course
     * @param section
     *      The section of the course
     */
    public Course(String name, String department, String instructor, int code, byte section){
        this.name = name;
        this.department = department;
        this.instructor = instructor;
        this.code = code;
        this.section = section;
    }

    /**
     * A method that returns the course name of the <code>Course</code>.
     * @return
     *     The course name of the <code>Course</code> invoked by the method.
     */
    public String getName(){
        return name;
    }
    /**
     * A method that returns the department of the <code>Course</code>.
     * @return
     *     The department of the <code>Course</code> invoked by the method.
     */
    public String getDepartment(){
        return department;
    }

    /**
     * A method that returns the instructor of the <code>Course</code>.
     * @return
     *     The instructor of the <code>Course</code> invoked by the method.
     */
    public String getInstructor(){
        return instructor;
    }

    /**
     * A method that returns the code of the <code>Course</code>.
     * @return
     *     The code of the <code>Course</code> invoked by the method.
     */
    public int getCode(){
        return code;
    }
    /**
     * A method that returns the section of the <code>Course</code>.
     * @return
     *     The section of the <code>Course</code> invoked by the method.
     */
    public byte getSection(){
        return section;
    }

    /**
     * A mutator method that changes the name of the course
     * @param name
     *      The name that the mutator changes the course's name to
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * A mutator method that changes the department of the course
     * @param department
     *      The department that the mutator changes the course's department to
     */
    public void setDepartment(String department){
        this.department = department;
    }

    /**
     * A mutator method that changes the instructor of the course
     * @param instructor
     *      The instructor that the mutator changes the course's instructor to
     */
    public void setInstructor(String instructor){
        this.instructor = instructor;
    }

    /**
     * A mutator method that changes the code of the course
     * @param code
     *      The code that the mutator changes the course's code to
     * @throws IllegalArgumentException
     *      When the inputted code is invalid
     */
    public void setCode(int code){
        if(code<100 || code>999)
            throw new IllegalArgumentException("Invalid code.\n");
        else
            this.code = code;
    }

    /**
     * A mutator method that changes the section of the course
     * @param section
     *      The section that the mutator changes the course's section to
     * @throws IllegalArgumentException
     *      When the inputted section is invalid
     */
    public void setSection(byte section) {
        if(section<1 || section>9)
            throw new IllegalArgumentException("Invalid section.\n");
        else
            this.section = section;
    }

    /**
     * A method that returns a copy of the <code>Course</code> where changes
     * will not affect the original and vice versa.
     * @return
     *      A deep clone of the <code>Course</code>.
     */
    public Object clone() {
        Course c = new Course(this.name, this.department, this.instructor, this.code, this.section);
        return c;
    }

    /**
     * A method that compares the attributes between two courses.
     * @param obj
     *      The Course object whose attributes are compared with the course which invokes this method.
     * @return
     *      true if the attributes of the two courses are the same and false otherwise
     */
    public boolean equals(Object obj) {
        if(obj instanceof Course){
            Course c = (Course) obj;
            return this.name.equals(c.name) && this.department.equals(c.department) && this.instructor.equals(c.instructor)
                            && this.code == c.code && this.section == c.section;
        }
        return false;
    }

    /**
     * A method that gets the String representation of this Course object
     * @return
     *      String representation of this Course object
     */
    public String toString() {
        return String.format("%-26s%-12s%-10s%-10s%-26s", name, department, code, section, instructor);
    }
}