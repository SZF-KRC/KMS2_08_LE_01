package manager.interfaces;

import manager.model.Employee;
import java.util.List;

public interface IEmployeeService {
    void addEmployee(Employee employee);
    void updateEmployee(int employeeId, Employee updatedEmployee);
    void deleteEmployee(int employeeId);
    List<Employee> getAllEmployees();
}
