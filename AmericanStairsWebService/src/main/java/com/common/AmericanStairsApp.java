package com.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.american.stairs.database.HibernateUtil;
import com.american.stairs.model.User;

public class AmericanStairsApp
{
	public Session session;
    public static void main( String[] args )
    {
    	    AmericanStairsApp app = new AmericanStairsApp();
    		System.out.println("Maven + Hibernate + MySQL + Restful");
    		
    		app.process();   		
    	   				
       // Authenticating the user first
       System.out.println("Aunthenticate the user");
       UUID uuid = app.authenticate("roopjay@hotmail.com", "testpwd1");
       if(uuid != null)
    	   System.out.println("UUID: " + uuid.toString());

    	// Get all Users
    	System.out.println("---------------- Getting all users -------------------------");
    	List<User> users = app.getAllUsers();
	    for (User user : users) {
           System.out.println("User: [Userid : " + user.getUserId() + ", Name : " + user.getUserName() + ", Email : " + user.getEmail() +
        		   ", Password : " + user.getPassword() +", Role : " + user.getRoleId() +", isActive : " + user.isActive() + 
        		   ", Created : " + user.getCreated() + " ]"  );
        }

/*	    System.out.println("---------------- Getting one user by ID -------------------------");
	    //Get a user by Id
	    User user = app.getUserById(1);
	    System.out.println("User: [Userid : " + user.getUserId() + ", Name : " + user.getUserName() + ", Email : " + user.getEmail() +
     		   ", Password : " + user.getPassword() +", Role : " + user.getRoleId() +", isActive : " + user.isActive() + 
     		   ", Created : " + user.getCreated() + " ]"  );
	    
	    System.out.println("---------------- Update the above fetched user -------------------------");
        //update user
        //User user = studentDao.getAllStudents().get(0);
        user.setUserName("Roopa Jayadev");
        app.updateUser(user);
        
        System.out.println("---------------- Add a new User -------------------------");
        //Add User
        User newUser = new User("Test User 3", "Test3@email.com", "Testpwd3", true, 3);
        app.addUser(newUser);
        
        System.out.println("---------------- Delete an existing User -------------------------");
        //Delete a User
        app.deleteUser(6);   */  
        
        HibernateUtil.shutdown();

    }
    
    public void process()
    {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		//return session;
    }
    
    public List<User> getAllUsers( ) {
        List<User> users = new ArrayList<User>();

	       try {
	           users = session.createQuery("from User").getResultList();
	       } catch (RuntimeException e) {
	           e.printStackTrace();
	       } finally {
	           session.flush();
	           //session.close();
	       }
        return users;
    }

    public User getUserById(int userid) {
        User user = null;
 
        try {
            String queryString = "from User where id = :id";
            Query<User> query = session.createQuery(queryString);
            query.setParameter("id", userid);
            //query.setInteger("id", userid);
            user = (User) query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            //session.close();
        }
        return user;
    }
    
    public User getUserByLoginCredentials(String email, String password) {
        User user = null;
 
        try {
            String queryString = "from User where Email = :id and Password = :pwd";
            Query<User> query = session.createQuery(queryString);
            //query.setInteger("id", userid);
            query.setString("id", email);
            query.setString("pwd", password);
            user = (User) query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            //session.close();
        }
        return user;
    }
    
    public void updateUser(User user) {

    	try{
            session.update(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            //session.close();
        }
    }
    public void addUser(User user) {
 
        try {
            session.save(user);
            System.out.println("Saved now commit");
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
            }
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            //session.close();
        }
    }

    public void deleteUser(int userid) {

        try {
            session.beginTransaction();
            User user = (User) session.load(User.class, new Integer(userid));
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            //session.close();
        }
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