package com.mountain.springdemo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mountain") 
public class Mountain {
	
	@Id
	@Column(name = "id") 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "m_date")
	private Timestamp m_date;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "preview")
	private String preview;

	public Integer getId() {
		return id;
	}
	
	
	public Mountain() {

	}


	public Mountain(Integer id, String name, Timestamp m_date, String type, String location, String description,
			String preview) {
		super();
		this.id = id;
		this.name = name;
		this.m_date = m_date;
		this.type = type;
		this.location = location;
		this.description = description;
		this.preview = preview;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getM_date() {
		return m_date;
	}

	public void setM_date(Timestamp m_date) {
		this.m_date = m_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}


	@Override
	public String toString() {
		return "Mountain [id=" + id + ", name=" + name + ", m_date=" + m_date + ", type=" + type + ", location="
				+ location + ", description=" + description + ", preview=" + preview + "]";
	}
	
	
	
	
}
