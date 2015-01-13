package com.keertech.myandroid.bean;

import com.yftools.db.annotation.Table;

@Table(name = "t_department")
public class Department {
	
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
