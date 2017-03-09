import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by MyNguyen on 3/7/17.
 */
public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();

        this.admin.createClass("ECS15", 2017, "Devanbu", 50);
    }

    // Test addHomework
    @Test
    public void testAddHomework() {
        instructor.addHomework("Devanbu", "ECS15", 2017, "Lab4", "Cache");
        assertTrue(instructor.homeworkExists("ECS15", 2017, "Lab4"));
    }

    @Test
    public void testAddHomework_ByDifferentInstructor() {
        instructor.addHomework("Olson", "ECS15", 2017, "Lab2", "Multi-threading");
        assertEquals("Olson", admin.getClassInstructor("ECS15", 2017));
    }

    @Test
    public void testAddHomework_ToDifferentClassName() {
        instructor.addHomework("Devanbu", "ECS12", 2017, "Lab3", "Inheritance");
        assertTrue(instructor.homeworkExists("ECS12", 2017, "Lab3"));
    }

    @Test
    public void testAddHomework_WithEmptyHomeworkName() {
        instructor.addHomework("Devanbu", "ECS12", 2017, "", "cout");
        assertTrue(instructor.homeworkExists("ECS12", 2017, ""));
    }

    // Test asignGrade
    @Test
    public void testAsignGrade() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab1", "Stack");
        this.student.submitHomework("Kim", "Lab1", "abc", "ECS15", 2017);
        this.instructor.assignGrade("Devanbu", "ECS15", 2017, "Lab1", "Kim", 100);
        assertEquals(new Integer(100), this.instructor.getGrade("ECS15", 2017, "Lab1", "Kim"));
    }

    @Test
    public void testAssignGrade_InstructorDoesnotTeachClass() {
        this.instructor.assignGrade("Olson", "ECS15", 2017, "Lab1", "Kim", 90);
        assertFalse(this.admin.getClassInstructor("ECS15", 2017).equals("Olson"));
    }

    @Test
    public void testAssignGrade_HomeworkIsnotAdded() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.student.submitHomework("Kim", "Lab1", "abc", "ECS15", 2017);
        this.instructor.assignGrade("Devanbu", "ECS15", 2017, "Lab1", "Kim", 100);
        assertFalse(this.instructor.homeworkExists("ECS15", 2017, "Lab1"));
    }

    @Test
    public void testAssignGrade_StudentDidnotSubmitHomework() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab1", "Stack");
        this.instructor.assignGrade("Devanbu", "ECS15", 2017, "Lab1", "Kim", 100);
        assertFalse(this.student.hasSubmitted("Kim", "Lab1", "ECS15", 2017));
    }

    @Test
    public void testAssignGrade_NegativeGrade() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab1", "Stack");
        this.student.submitHomework("Kim", "Lab1", "abc", "ECS15", 2017);
        this.instructor.assignGrade("Devanbu", "ECS15", 2017, "Lab1", "Kim", -10);
        System.out.println("**** " + this.instructor.getGrade("ECS15", 2017, "Lab1", "Kim"));
        assertFalse(this.instructor.getGrade("ECS15", 2017, "Lab1", "Kim") >= 0);
    }
}
