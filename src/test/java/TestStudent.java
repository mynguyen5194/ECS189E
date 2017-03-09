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
 * Created by MyNguyen on 3/8/17.
 */
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();

        this.admin.createClass("ECS15", 2017, "Devanbu", 2);
    }

    // Test registerForClass
    @Test
    public void testRegisterForClass() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        assertTrue(this.student.isRegisteredFor("Kim", "ECS15", 2017));
    }

    @Test
    public void testRegisterForClass_WithEmptyStudentName() {
        this.student.registerForClass("", "ECS15", 2017);
        assertFalse(this.student.isRegisteredFor("", "ECS15", 2017));
    }

    @Test
    public void testRegisterForClass_WithNullStudentName() {
        this.student.registerForClass(null, "ECS15", 2017);
        assertFalse(this.student.isRegisteredFor(null, "ECS15", 2017));
    }

    @Test
    public void testRegisterForClass_NonExistingClass() {
        this.student.registerForClass("Kim", "ECS20", 2017);
        assertTrue(this.student.isRegisteredFor("Kim", "ECS20", 2017));
    }

    @Test
    public void testRegisterForClass_WithinCapacity() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        assertTrue(this.student.isRegisteredFor("Kim", "ECS15", 2017));
    }

    @Test
    public void testRegisterForClass_OverCapacity() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.student.registerForClass("My", "ECS15", 2017);
        this.student.registerForClass("Danny", "ECS15", 2017);
        assertFalse(this.student.isRegisteredFor("Danny", "ECS15", 2017));
    }

    // Test dropClass
    @Test
    public void testDropClass() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.student.dropClass("Kim", "ECS15", 2017);
        assertFalse(this.student.isRegisteredFor("Kim", "ECS15", 2017));
    }

    @Test
    public void testDropClass_NonExistingClass() {
        this.student.dropClass("Kim", "ECS50", 2017);
        assertFalse(this.student.isRegisteredFor("Kim", "ECS50", 2017));
    }

    @Test
    public void testDropClass_WithoutRegister() {
        this.student.dropClass("Kim", "ECS15", 2017);
        assertFalse(this.student.isRegisteredFor("Kim", "ECS15", 2017));
    }

    @Test
    public void testDropClass_InThePast() {
        this.student.dropClass("Kim", "ECS15", 2016);
        assertFalse(this.student.isRegisteredFor("Kim", "ECS15", 2016));
    }

    // Test submitHomework
    @Test
    public void testSubmitHomework() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab1", "Docker");
        this.student.submitHomework("Kim", "Lab1", "abc", "ECS15", 2017);
        assertTrue(this.student.hasSubmitted("Kim", "Lab1", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_NonExistingHomework() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.student.submitHomework("Kim", "Lab2", "abc", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "Lab2", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_DifferentHomework() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "Lab1", "abc", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "Lab1", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_WithoutRegister() {
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "Lab2", "abc", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "Lab2", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_WithoutStudentName() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("", "Lab2", "abc", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("", "Lab2", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_WithoutHomeworkName() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "", "abc", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_WithoutAnswer() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "Lab2", "", "ECS15", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "Lab2", "ECS15", 2017));
    }

    @Test
    public void testSubmitHomework_WithoutClassName() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "Lab2", "abc", "", 2017);
        assertFalse(this.student.hasSubmitted("Kim", "Lab2", "", 2017));
    }

    @Test
    public void testSubmitHomework_InThePast() {
        this.student.registerForClass("Kim", "ECS15", 2017);
        this.instructor.addHomework("Devanbu", "ECS15", 2017, "Lab2", "Docker");
        this.student.submitHomework("Kim", "Lab2", "abc", "Lab2", 2016);
        assertFalse(this.student.hasSubmitted("Kim", "Lab2", "Lab2", 2016));
    }


}
