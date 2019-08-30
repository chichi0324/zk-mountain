package com.mountain.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mountain.springdemo.dao.MountainDAO;
import com.mountain.springdemo.entity.Mountain;

@Service("MountainService")
public class MountainServiceImpl implements MountainService {
	
	@Autowired
	private MountainDAO mountainDAO;


	@Override
	public List<Mountain> search(String keyword) {
		
		List<Mountain> result=null;
		if(keyword==null || "".equals(keyword.trim())){
			result =mountainDAO.getAllMountains();
		}else{
			result =mountainDAO.getMountains(keyword);
		}

		return result;
	}

	@Override
	public void addMountain(Mountain theMountain) {
		mountainDAO.saveOrUpdateMountain(theMountain);
	}

	@Override
	public void updateMountain(Mountain theMountain) {
		mountainDAO.saveOrUpdateMountain(theMountain);
	}

	@Override
	public void deleteMountain(Mountain theMountain) {
		mountainDAO.deleteMountain(theMountain);
	}
}
