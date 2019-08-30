package com.mountain.springdemo.dao;

import java.util.List;

import com.mountain.springdemo.entity.Mountain;

public interface MountainDAO {
	List<Mountain> getAllMountains();
	
	List<Mountain> getMountains(String keyword);

	void saveOrUpdateMountain(Mountain theMountain);
	
	void deleteMountain(Mountain theMountain);
}
