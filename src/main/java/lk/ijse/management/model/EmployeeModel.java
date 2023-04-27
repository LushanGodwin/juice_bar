package lk.ijse.management.model;

import lk.ijse.management.dto.Customer;
import lk.ijse.management.dto.Employee;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static boolean add(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee(employee_id,employee_name,employee_address,employee_contact) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmployeeAddress(),employee.getEmployeeContact());
    }

    public static boolean remove(String employeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return CrudUtil.execute(sql,employeeId);
    }

    public static Employee get(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,employeeId);
        if(resultSet.next()){
            return new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        } return null;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET employee_name = ?,employee_address = ?,employee_contact = ? WHERE employee_id =?";
        return CrudUtil.execute(sql,employee.getEmployeeName(),employee.getEmployeeAddress(),employee.getEmployeeContact(),employee.getEmployeeId());
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Employee> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }return data;
    }
}
