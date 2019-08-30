package com.mountain.springdemo.service;

import java.util.List;

import com.mountain.springdemo.entity.Mountain;

public interface MountainService {

	/**
	 * search cars according to keyword in model and make.
	 * @param keyword for search
	 * @return list of car that match the keyword
	 */
	List<Mountain> search(String keyword);
	
	
	void addMountain(Mountain theMountain);
	
	void updateMountain(Mountain theMountain);
	
	void deleteMountain(Mountain theMountain);
}
