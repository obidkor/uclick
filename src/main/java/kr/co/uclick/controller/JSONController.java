package kr.co.uclick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import kr.co.uclick.entity.User;
import kr.co.uclick.service.UserService;

@RestController
public class JSONController {

	@Autowired
	UserService userService;
	
	@RequestMapping("sendList")
	public List<User> sendList() {
//			List<User> list = new ArrayList<User>();
//	    for (int i = 0; i < userService.userCount(); i++) {
//	        List<User> users = userService.findAll();
//	    	User u = new User();
//	        u.setId(users.get(i).getId());
//	        u.setName(users.get(i).getName());
//	        u.setEnrollDate(users.get(i).getEnrollDate());
//	        try {
//	        	u.setPhone(users.get(i).getPhone());//왜 널이 오는가???
//	        }catch(NullPointerException e) {
////	        	u.setPhone(null);
//	        }
//	    	list.add(u);
//	    }
	    return userService.findAll();
	}

	@RequestMapping("sendMap")
	public Map<Integer, User> sendMap() {
	    Map<Integer, User> map = new HashMap<>();

	    for (int i = 0; i < userService.userCount(); i++) {
	        List<User> list = userService.findAll();
//	    	User u = new User();
//	        u.setId(list.get(i).getId());
//	        u.setName(list.get(i).getName());
//	        u.setEnrollDate(list.get(i).getEnrollDate());
//	        try {
//		        u.setPhone(list.get(i).getPhone());
//		        }catch(NullPointerException e) {
////		        u.setPhone(null);
//		        }
	    	map.put(i, list.get(i));
	    }
	    return map;
	}
	
//	@GetMapping
//	public Page<User> getUsers(final Pageable pageable){
//		return null;
////		return userService.findAll(pageable).map(UserDto.)
//	}
}
