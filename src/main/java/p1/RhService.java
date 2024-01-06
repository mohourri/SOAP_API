package p1;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface RhService {
	 @WebMethod
	    String addEmployee(Employee employee);

	    @WebMethod
	    List<Employee> getemployees();

	    @WebMethod
	    List<Employee> searchEmployees(String keyword);

	    @WebMethod
	    String updateEmployee(Employee employee);

	    @WebMethod
	    String deleteEmployee(int employeeId);

	    @WebMethod(operationName = "getEmployeeById")
	    Employee getEmployeeById(int employeeId);
	}
