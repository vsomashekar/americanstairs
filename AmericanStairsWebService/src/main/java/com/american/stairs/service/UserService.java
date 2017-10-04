package com.american.stairs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.american.stairs.database.DatabaseClass;
import com.american.stairs.exception.DataNotFoundException;
//import com.american.stairs.model.Message;
import com.american.stairs.model.User;

public class UserService {

	public Map<Long, User> users ;
    public DatabaseClass dbClass;
	public UserService()
	{
		System.out.println("Instantite the Database Class");
		dbClass = new DatabaseClass();
	}
	
	public List<User> getAllUsers(){
		 Map<Integer, User> users = dbClass.getAllUsers();
		 return new ArrayList<User>(users.values());
	}; 
	
	public List<User> getAllUsersByYear(int year){
		List<User> messagesByYear = new ArrayList<User>();
		for(User eachMessage: users.values())
		{
			Calendar  msgYear = Calendar.getInstance();
			msgYear.setTime(eachMessage.getCreated());
			if (msgYear.get(Calendar.YEAR) == year)
				messagesByYear.add(eachMessage);		
		}
		return messagesByYear;
	}; 
	
	public List<User> getAllUsersPagination(int start, int size){ 
		ArrayList<User> list = new ArrayList<User>(users.values());
		if(start+size > list.size()) return new ArrayList<User>();
		
		return list.subList(start, start+size);
	}; 
	
	// All Messages methods to get message info, get One message, all messages, add, update and delete a message
		public User getUser(Integer id){
			User user = dbClass.getUserById(id);
			if (user == null)
				throw new DataNotFoundException("User not found for ID " + id);
			else
				return user;
				
			//System.out.println(msg.getMessage());
		}
		public User addUser(User user){
			dbClass.addUser(user);
			return user;
		}
		public User updateUser(User user){
			if(user.getUserId() <= 0)
			{
				System.out.println("user id is null");
				return null;
			}
			System.out.println("Call Update user");
			if(dbClass != null)
				dbClass.updateUser(user);
			else
				System.out.println("dbClass is not available");
			return user;
			/*if (user.getUserId()) != null)
			{
				//System.out.println("Update successful");
				return user;
			}
			else
				return null;*/
		}
		public User deleteUser(Integer userId){
			 User user = dbClass.deleteUser(userId);
			 return user;
		}
}
