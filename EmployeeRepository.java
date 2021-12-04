package com.example.project.capstone.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.capstone.entity.Employee;

@Repository
@Transactional
public class EmployeeRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	EntityManager em;
	//@Autowired
	//EmailService emailService;
	
	/**
	 * Retrieve Employee By EmployeeId
	 * 
	 * @param id
	 * @return Employee
	 */
	public Employee findById(Long id) {
		return em.find(Employee.class, id);
	}
	
	/**
	 * Update Employee Mobile number By employeeId
	 * 
	 * @param id
	 * @param phonenumber
	 * @return Employee
	 */
	public Employee updatePhoneNumberById(long id,String phonenumber) {
		Employee employee1=findById(id);
		if(employee1!=null) {
			Query query = em.createNativeQuery("UPDATE employee SET phone_number=? where employee_id = ? ",Employee.class);
			query.setParameter(1,phonenumber);
			query.setParameter(1,id);
			if(query.executeUpdate()>0);
			return findById(id);
		}
		else
			return null;
	}
	
	/**
	 * Change Employee Password By EmployeeId
	 * 
	 * @param id
	 * @param password
	 * @return Employee
	 */
	public Employee updatePasswordById(long id,String password) {
		Employee employee1=findById(id);
		if(employee1!=null) {
			Query query = em.createNativeQuery("UPDATE employee SET password=? where employee_id = ? ",Employee.class);
			query.setParameter(1,password);
			query.setParameter(2,id);
			if(query.executeUpdate()>0);
			return findById(id);
		}
		else
			return null;
	}
	
	/**
	 * Update employee details like name,email_id,phone_number,type,managerId By EmployeeId
	 * 
	 * @param employee
	 * @return Employee
	 */
	public Employee updateById(Employee employee) {
		Employee employee1=findById(employee.getEmployeeId());
		Employee email=findByEmail(employee.getEmailId());
		if(employee1!=null&&email==null) {
			Query query = em.createNativeQuery("UPDATE employee SET name=?,email_id = ?,phone_number=?,type=?,manager_id=? where employee_id = ? ",Employee.class);
			query.setParameter(1,employee.getName());
			query.setParameter(2,employee.getEmailId());
			query.setParameter(3,employee.getPhoneNumber());
			query.setParameter(4,employee.getType());
			query.setParameter(5,employee.getManagerId());
			query.setParameter(6,employee.getEmployeeId());
			if(query.executeUpdate()>0);
			return findById(employee.getEmployeeId());
		}
		else
			return null;
	}
	
	/**
	 * Retrieve Employee By email_id
	 * 
	 * @param email
	 * @return EMployee
	 */
	public Employee findByEmail(String email) {
		Query query = em.createNativeQuery("Select * from employee where email_id LIKE ? LIMIT 1",Employee.class);
		query.setParameter(1, email);
		Employee employee=null;
		try {
		employee= (Employee) query.getSingleResult();
		}
		catch (NoResultException nre){}
		return employee;
	}
	
	/**
	 *  Save Employee into database
	 *  
	 * @param employee
	 * @return Employee
	 */
	public Employee save(Employee employee) {
		Employee employee1=findByEmail(employee.getEmailId());
		if(employee1==null) {
			employee.generatePassword();
			em.persist(employee);
			logger.info("employee registered->{}",employee);
			String message="URL:<a href='http://localhost:4200/login'>click here</a>"
					+"\nLogin credentials:\n"+
					"username:"+employee.getEmailId()+"\npassword:"+employee.getPassword();
			//emailService.sendMail(employee.getEmailId(), "Login Credentials",message);
		}
		else {
			logger.error("employee already registered->{}",employee1);
			return null;
		}
		return employee;
	}
	
	/**
	 * Retrieve employees by type employee or manager
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> findAll(){
		Query query = em.createNativeQuery("Select * from employee where type LIKE 'Employee' or type LIKE 'Manager'",Employee.class);
		return query.getResultList();
	}
	
	
	public List<Employee> findEmployeeByManagerId(long id){
		Query query = em.createNativeQuery("Select * from employee where manager_id=?",Employee.class);
		query.setParameter(1,id);
		return query.getResultList();
	}
	
	
	public void deleteById(Long id) {
		Employee employee = findById(id);
		em.remove(employee);
	}
	
	public Employee findByEmailAndPassword(String email,String password) {
		Query query = em.createNativeQuery("Select * from employee where email_id LIKE ? and password LIKE ? COLLATE latin1_general_cs LIMIT 1",Employee.class);
		query.setParameter(1, email);
		query.setParameter(2, password);
		Employee employee=null;
		try {
		employee= (Employee) query.getSingleResult();
		}
		catch (NoResultException nre){}
		return employee;
	}

	public List<Employee> findByType() {
		Query query = em.createNativeQuery("Select * from employee where type LIKE 'Employee'",Employee.class);
		List<Employee> employee=null;
		try {
		employee=query.getResultList();
		}
		catch (NoResultException nre){}
		return employee;
		}
	
	public List<Employee> findManagers(){
		Query query = em.createNativeQuery("Select * from employee where type LIKE 'Manager'",Employee.class);
		List<Employee> employee=null;
		try {
		employee=query.getResultList();
		}
		catch (NoResultException nre){}
		return employee;
		}
	
}
