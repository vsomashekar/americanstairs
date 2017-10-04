package com.american.stairs.model;

import java.util.Date;
import java.util.List;

import com.american.stairs.model.Link;

import java.util.ArrayList;
import java.util.Calendar;

public class User {
	private int UserId, RoleId;
	private String UserName, Email, Password;  
	private int IsActive;
	private Date Created;
    private List<Link> links = new ArrayList<>();
	
	public User(){
		
	}
	public User(String userName, String email,  String password, boolean isActive, int roleId){
		//this.UserId = userId;
		this.UserName = userName;
		this.Email = email;
		this.Password = password;
		if(isActive)
			this.IsActive = 1;
		else
			this.IsActive = 0;
		this.RoleId = roleId;
        this.Created = Calendar.getInstance().getTime();
	}
	
	public int getUserId() {
		return UserId;
	}

	public Date getCreated() {
		return Created;
	}
	public void setCreated(Date created) {
		Created = created;
	}
	public void setUserId(int userId) {
		this.UserId = userId;
	}
	public int getRoleId() {
		return RoleId;
	}
	public void setRoleId(int roleId) {
		this.RoleId = roleId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		this.Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		this.Password = password;
	}
	public int isActive() {
		return IsActive;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		this.IsActive = isActive;
	}
	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
}
