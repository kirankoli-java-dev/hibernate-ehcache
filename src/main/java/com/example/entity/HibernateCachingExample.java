package com.example.entity;

import com.example.entity.Employee;

import static utils.HibernateUtils.getSf;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateCachingExample {
    public static void main(String[] args) {
 
   	
        try(SessionFactory sessionFactory=getSf())
		{
			System.out.println("Hibernated booted.....");
	        // Open a session
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	
	        // Create and persist a new Employee
	        Employee employee = new Employee(1, "John Doe");
	        session.save(employee);
	        session.getTransaction().commit();
//	
	        // Begin a new transaction
	        session.beginTransaction();
	
	        // Fetch the employee entity (First level cache)
	        Employee emp1 = session.get(Employee.class, 1);
	        System.out.println("First Query: " + emp1);
	
	        // Fetch the employee entity again (Should hit the second level cache)
	        Employee emp2 = session.get(Employee.class, 1);
	        System.out.println("Second Query: " + emp2);
	
	        session.getTransaction().commit();
	        session.close();
	
	        // Open another session to demonstrate Level 2 cache
	        Session session2 = sessionFactory.openSession();
	        session2.beginTransaction();
	
	        // Fetch the employee entity (Should hit the second level cache)
	        Employee emp3 = session2.get(Employee.class, 1);
	        System.out.println("first Query (new session): " + emp3);
	
	        session2.getTransaction().commit();
	        session2.close();
	        
	        Session session3 = sessionFactory.openSession();
	        session3.beginTransaction();
	        
	        Employee emp4 = session3.get(Employee.class, 2);
	        System.out.println("second Query (new session): " + emp4);
	
	        session3.getTransaction().commit();
	        session3.close();
	
	        
	        Session session4 = sessionFactory.openSession();
	        session4.beginTransaction();
	        
	        Employee emp5 = session4.get(Employee.class, 2);
	        System.out.println("second Query (new session): " + emp4);
	
	        session4.getTransaction().commit();
	        session4.close();
	        
	        
	        // Close SessionFactory
	        sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


//

    }
}

