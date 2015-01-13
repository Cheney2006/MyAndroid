package com.keertech.myandroid.bean;

import com.yftools.db.annotation.Foreign;
import com.yftools.db.annotation.Table;

import java.util.Date;

@Table(name="t_user")
public class User {

	private int id;
	private String username;
	private String name;
	private Date birthday;
	@Foreign(column="departmentId",foreign="id",foreignAutoCreate=true)
	private Department department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	

}
