package app.domain.store;

import app.Persistence;
import app.domain.model.Client;
import app.domain.model.Employee;
import app.domain.model.SpecialistDoctor;
import app.domain.model.TestType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeStore {
    private List<Employee> employeeList = new ArrayList<Employee>();

    /**
     * Constructor
     */
    public EmployeeStore() {
        try{
            employeeList = (List<Employee>) Persistence.readObjectFromFile("employees.bin");
        }catch (Exception e){
            //System.out.println("The EmployeeStore was not loaded :)");
        }
    }

    /**
     * This method creates a Specialist Doctor with all the parameters given
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @param doctorIndexNumber
     * @return
     */
    public Employee createNewSpecialistDoctor(String name, String role, String address, String phoneNumber, String email, int soc, String id, int doctorIndexNumber){
        SpecialistDoctor sp= new SpecialistDoctor(name, role, address, phoneNumber, email, soc, id, doctorIndexNumber);
        return sp;
    }

    /**
     * This method creates an Employee with all the parameters given
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @return
     */
    public Employee createNewEmployee(String name, String role, String address, String phoneNumber, String email, int soc, String id){
        Employee emp= new Employee(name, role, address, phoneNumber, email, soc, id);
        return emp;
    }

    /**
     * This method checks if an Employee already exists in memory or if it's null
     * @param Emp
     * @return
     */
    public boolean validateEmployee(Employee Emp) {
        int i = 0;
        for (Employee emp : employeeList){
            if (Emp.equals(employeeList.get(i))){
                throw new IllegalArgumentException("Employee " + emp.getName() + " already exists");
            }
            i++;
        }
        return !this.employeeList.contains(Emp);
    }

    /**
     * This method saves an Employee in the System
     * @param Emp
     */
    public void saveEmployee(Employee Emp) {
        validateEmployee(Emp);
        add(Emp);
    }

    /**
     * This method adds a new Employee to the list of employees
     * @param employee
     * @return
     */
    public boolean add(Employee employee){
        if (employee != null) {
            if(!employeeList.contains(employee)){
                employeeList.add(employee);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns a list with all the employees that exist
     * @return
     */
    public List<String> getEmployees() {
        List<String> employeesDescriptions = new ArrayList<>();
        for (Employee e : employeeList){
            employeesDescriptions.add(e.getName());
        }
        return employeesDescriptions;
    }

    /**
     * This method returns the number of employees that exist in System
     * @return
     */
    public int getEmployeeQuantity() {
        return getEmployees().size() + 1;
    }

    /**
     * This method returns a String that contains the Inicials of a Full name of an Employee
     * @param name
     * @return
     */
    public String getInitials(String name) {

        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Could not create id, because name field is empty");
        }
        String[] initialsArray = name.split(" ");
        String initials = "";
        for (int i = 0; i < initialsArray.length; i++) {
            initials = initials + String.valueOf(initialsArray[i].charAt(0)).toUpperCase();
        }
        return initials;
    }

    /**
     * This method returns the ID of an Employee
     * @param name
     * @return
     */
    public String getId(String name) {
        String inicitials = getInitials(name);
        int count = getEmployeeQuantity();
        String countStr = String.valueOf(count);
        while (String.valueOf(countStr).length() != 5) {
            countStr = "0" + countStr;
        }
        return inicitials + countStr;
    }


}
