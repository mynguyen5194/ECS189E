import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    // Test createClass
    @Test
    public void testCreateClass() {
        this.admin.createClass("ECS1", 2017, "Devanbu", 15);
        assertTrue(this.admin.classExists("ECS1", 2017));
    }

    @Test
    public void testCreateClass_Duplicate() {
        this.admin.createClass("ECS1", 2017, "Devanbu", 15);
        this.admin.createClass("ECS1", 2017, "Olson", 30);

        assertFalse(admin.getClassInstructor("ECS1", 2017).equals("Devanbu"));
    }

    @Test
    public void testCreateClass_InvalidClassName() {
        this.admin.createClass("", 2017, "Devanbu", 30);
        assertFalse(admin.classExists("", 2017));
    }

    @Test
    public void testCreateClass_InstructorWithMoreThan2Courses() {
        this.admin.createClass("ECS189E", 2017, "Prof. Devanbu", 15);
        this.admin.createClass("ECS160", 2017, "Prof. Devanbu", 30);
        this.admin.createClass("ECS175", 2017, "Prof. Devanbu", 40);

        String instr = admin.getClassInstructor("ECS189E", 2017);
        int courseCount = 1;
        if (instr.equals(admin.getClassInstructor("ECS160", 2017))) {
            courseCount++;
        }
        if (instr.equals(admin.getClassInstructor("ECS175", 2017))) {
            courseCount++;
        }
        System.out.println("*** = " + courseCount);

        assertFalse(courseCount > 2);
    }

    @Test
    public void testCreateClass_InvalidInstructor() {
        this.admin.createClass("ECS1", 2017, "", 40);
        assertFalse(admin.getClassInstructor("ECS1", 2017).isEmpty());
    }

    @Test
    public void testCreateClass_InThePast() {
        this.admin.createClass("ECS180", 2016, "Prof. John", 30);
        assertFalse(this.admin.classExists("ECS180", 2016));
    }

    @Test
    public void testCreateClass_WithInvalidCapacity() {
        this.admin.createClass("ECS189E", 2017, "Prof. Devanbu", -3);
        assertFalse(this.admin.getClassCapacity("ECS189E", 2017) <= 0);
    }


    // Test changeCapacity
    @Test
    public void testChangeCapacity1() {
        admin.createClass("ECS122", 2017, "Prof. G", 30);
        admin.changeCapacity("ECS122", 2017, 20);

        assertTrue(admin.getClassCapacity("ECS122", 2017) == 20);
    }

    @Test
    public void testChangeCapacity2() {
        admin.createClass("ASA150", 2017, "Prof. V", 5);

        IStudent student1 = new Student();
        IStudent student2 = new Student();

        student1.registerForClass("A", "ASA150", 2017);
        student2.registerForClass("B", "ASA150", 2017);
        this.admin.changeCapacity("ASA150", 2017, 3);

        assertTrue(admin.getClassCapacity("ASA150", 2017) >= 3);
    }

    @Test
    public void testChangeCapacity_OfNotCreatedClass() {
        admin.changeCapacity("ECS1", 2017, 45);
        assertTrue(admin.classExists("ECS1", 2017));
    }

    // Test classExists
    @Test
    public void testClassExits_OfNonExistClass() {
        assertTrue(admin.classExists("ECS145", 2017));
    }

    // Test getClassInstructor
    @Test
    public void testGetClassInstructor_() {
        admin.createClass("ECS60", 2017, "Prof. MN", 40);
        assertTrue(admin.getClassInstructor("ECS60", 2017).equals("Prof. MN"));
    }

    // Test getClassCapacity
    @Test
    public void testGetClassCapacity_() {
        admin.createClass("ECS20", 2017, "AV", 50);
        assertTrue(admin.getClassCapacity("ECS20", 2017) == 50);
    }

}
