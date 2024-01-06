package p1;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "p1.RhService")
public class RhServiceImpl implements RhService {


    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hrdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    
    @WebMethod(operationName = "addEmployee")
    public String addEmployee(@WebParam(name = "employee") Employee employee) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            if (employee != null) {
                String sql = "INSERT INTO employees (nom, prenom, poste, salaire) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, employee.getNom());
                    preparedStatement.setString(2, employee.getPrenom());
                    preparedStatement.setString(3, employee.getPoste());
                    preparedStatement.setDouble(4, employee.getSalaire());
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return "Employee ajouter a la base de donnees avec succes";
                    } else {
                        return "erreur de ajouter employee a la base de donne.";
                    }
                }
            } else {
                return "Employee object est null.";
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
            return "une erreur au temps d'ajouter employer a la base de donnees";
        }
    }
    @WebMethod(operationName = "getEmployeeById")
    public Employee getEmployeeById(@WebParam(name = "employeeId") int employeeId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM employees WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setNom(resultSet.getString("nom"));
                        employee.setPrenom(resultSet.getString("prenom"));
                        employee.setPoste(resultSet.getString("poste"));
                        employee.setSalaire(resultSet.getDouble("salaire"));
                        return employee;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed (e.g., log, return a default value, or throw a custom exception)
            return null;
        }
    }
    

    @WebMethod(operationName = "getemployees")
    public List<Employee> getemployees() {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM employees";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setNom(resultSet.getString("nom"));
                        employee.setPrenom(resultSet.getString("prenom"));
                        employee.setPoste(resultSet.getString("poste"));
                        employee.setSalaire(resultSet.getDouble("salaire"));
                        result.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



	@WebMethod(operationName="searchEmployees")
	    public List<Employee> searchEmployees(@WebParam(name="keyword")String keyword) {
	        List<Employee> result = new ArrayList<>();
	        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	            String sql = "SELECT * FROM employees WHERE nom LIKE ? OR prenom LIKE ? OR poste LIKE ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, "%" + keyword + "%");
	                preparedStatement.setString(2, "%" + keyword + "%");
	                preparedStatement.setString(3, "%" + keyword + "%");
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    while (resultSet.next()) {
	                        Employee employee = new Employee();
	                        employee.setId(resultSet.getInt("id"));
	                        employee.setNom(resultSet.getString("nom"));
	                        employee.setPrenom(resultSet.getString("prenom"));
	                        employee.setPoste(resultSet.getString("poste"));
	                        employee.setSalaire(resultSet.getDouble("salaire"));
	                        result.add(employee);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	
	@WebMethod(operationName = "updateEmployee")
	public String updateEmployee(@WebParam(name = "employee") Employee employee) {
	    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        String sql = "UPDATE employees SET nom = ?, prenom = ?, poste = ?, salaire = ? WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, employee.getNom());
	            preparedStatement.setString(2, employee.getPrenom());
	            preparedStatement.setString(3, employee.getPoste());
	            preparedStatement.setDouble(4, employee.getSalaire());
	            preparedStatement.setInt(5, employee.getId());
	
	            int rowsAffected = preparedStatement.executeUpdate();
	
	            if (rowsAffected > 0) {
	                return "Employé mis à jour avec succès.";
	            } else {
	                return "Aucun employé trouvé avec l'ID fourni. La mise à jour a échoué.";
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Une erreur s'est produite lors de la mise à jour de l'employé.";
	    }
	}
	
	
	@WebMethod(operationName = "deleteEmployee")
	public String deleteEmployee(@WebParam(name = "employeeId") int employeeId) {
	    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        String sql = "DELETE FROM employees WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, employeeId);
	
	            int rowsAffected = preparedStatement.executeUpdate();
	
	            if (rowsAffected > 0) {
	                return "Employé supprimé avec succès.";
	            } else {
	                return "Aucun employé trouvé avec l'ID fourni. La suppression a échoué.";
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Une erreur s'est produite lors de la suppression de l'employé.";
	    }
	
	}
}   
