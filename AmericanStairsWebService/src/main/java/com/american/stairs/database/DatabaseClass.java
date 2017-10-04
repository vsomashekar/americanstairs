package com.american.stairs.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.american.stairs.model.User;

public class DatabaseClass {
	public Session session;
	
	private Map<Integer, User> users = new HashMap<Integer, User>();

	//Database class
	public DatabaseClass(){   
		System.out.println("In the Database Class");
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
	}
	
	public  Map<Integer, User> getAllUsers() {
	       List<User> usersList = new ArrayList<User>();
	       try {
	           usersList = session.createQuery("from User").getResultList();

	       } catch (RuntimeException e) {
	           e.printStackTrace();
	       } finally {
	           session.flush();
	       }
	       for (User u : usersList)
	       {
	    	      users.put(u.getUserId(), u);
	       }
        return users;
			
	}		
	
	public User getUserById(int userid) {
        User user = null;
 
        try {
            String queryString = "from User where id = :id";
            Query<User> query = session.createQuery(queryString);
            query.setParameter("id", userid);
            user = (User) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
        }
        return user;
    }
    
    public User getUserByLoginCredentials(String email, String password) {
        User user = null;
 
        try {
            String queryString = "from User where Email = :id and Password = :pwd";
            Query<User> query = session.createQuery(queryString);
            //query.setString("id", email);
            //query.setString("pwd", password);
            query.setParameter("id",  email);
            query.setParameter("pwd",  password);
            user = (User) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            //session.close();
        }
        return user;
    }
    
    public User updateUser(User user) {

    	try{
            session.update(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } 
    	return user;
    }
    public User addUser(User user) {
 
        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
            }
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User deleteUser(int userid) {

    	User user = null;
        try {
           // session.beginTransaction();
           // user = (User) session.load(User.class, new Integer(userid));
            user = getUserById(userid);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return user; // return only if successful
    }
    
    public UUID authenticate(String email, String password){
    	UUID uuid = null;
    	User user = getUserByLoginCredentials(email, password);
    	if(user == null)
    		System.out.println("User is not authenticated");
    	else
    	{
    		System.out.println("Authentication successful !! Returning the generated uuid");
    		uuid = UUID.randomUUID();
    	}
    	return uuid;
    }
	


}
