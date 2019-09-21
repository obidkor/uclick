package kr.co.uclick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

@RestController
public class JSONController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhoneService phoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(JSONController.class);
	
	@RequestMapping("sendList")
	public List<User> sendList() {
	    return userService.findAll();
	}

	
	@RequestMapping("sendMap")
	public Map<Integer, User> sendMap() {
	    Map<Integer, User> map = new HashMap<>();

	    for (int i = 0; i < userService.userCount(); i++) {
	        List<User> list = userService.findAll();
	    	map.put(i, list.get(i));
	    }
	    return map;
	}
	
//	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("sendNameLike")
	public Map<Integer, User> sendNameLike(String name) {
	    Map<Integer, User> map = new HashMap<>();
	    int size = 5;
	    try {
	    for (int i = 0; i < size; i++) {
	        List<User> list = userService.findUserByName(name, PageRequest.of(0, size)).getContent();
	    	map.put(i, list.get(i));
	    }
	    }catch(Exception e) {
	    	logger.debug(e.toString());
	    }
	    return map;
	}
	
	
//	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("sendNumberLike")
	public Map<Integer, Phone> sendNumberLike(String number) {
	    Map<Integer, Phone> map = new HashMap<>();
	    int size = 5;
	    try {
	    for (int i = 0; i < size; i++) {
	        List<Phone> list = phoneService.findPhoneByNumber(number, PageRequest.of(0, size)).getContent();
	        map.put(i, list.get(i));
	    }
	    }catch(Exception e) {
	    	logger.debug(e.toString());
	    }
	    return map;
	}
	
}
