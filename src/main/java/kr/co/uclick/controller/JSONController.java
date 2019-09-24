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


//보통 Controller는 view에 리턴 / RestController는 Restful방식으로 return(controller+responsebody)
//controller는 httpreponse를 viewresolver를 통해 text/html타입으로  리턴
//restcontroller는 httpresponse body에 직접 쓰여짐 / MessageConverter를 통해  @RestController는 객체(VO,DTO)를 반환하기만 하면, 객체데이터는 application/json 형식의 HTTP ResponseBody에 직접 작성
@RestController
public class JSONController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhoneService phoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(JSONController.class);

	//메서드 마다 @Responsebody를 안붙여도된다.
	@RequestMapping("sendList")//전체 사용자 리스트 반환 api
	public List<User> sendList() {
	    return userService.findAll();
	}

	
	@RequestMapping("sendMap")//전체 사용자 리스트 -> 맵으로 반환 api
	public Map<Integer, User> sendMap() {
	    Map<Integer, User> map = new HashMap<>();//리턴될 map

	    for (int i = 0; i < userService.userCount(); i++) {
	        List<User> list = userService.findAll();
	    	map.put(i, list.get(i));//id값과 같이 넣음
	    }
	    return map;//리턴
	}
	
	//Cross-Origin Resource Sharing 표준 : 허가된 출저 집합임을 알리는 http헤더를 추가함
//	@CrossOrigin(origins = "http://serveraddress:port")//CORPS 승인
	@GetMapping("sendNameLike") //이름 자동완성을 위한 api
	public Map<Integer, User> sendNameLike(String name) {
	    Map<Integer, User> map = new HashMap<>();//반환될 map
	    try {
		    for (int i = 0; i < 5; i++) {//출력 리스트가 너무길면 안되니깐 5개로 제한 안됨
		        List<User> list = userService.findUserByNameForRest(name);
		    	map.put(i, list.get(i));
		    }
	    }catch(IndexOutOfBoundsException e) {//find 결과가 5보다 작으면 outofbound가 뜸 
	    	for (int i = 0; i < userService.findUserByNameForRest(name).size(); i++) {//size만큼 넣음
		        List<User> list = userService.findUserByNameForRest(name);
		    	map.put(i, list.get(i));
		    }
	    }catch(Exception e) {
	    	logger.debug(e.toString());//버그 로그
	    }
	    return map;
	}
	
	
	@GetMapping("sendNumberLike")//번호 자동완성을 위한 api
	public Map<Integer, Phone> sendNumberLike(String number) {
	    Map<Integer, Phone> map = new HashMap<>();//반환될 map
	    try {
		    for (int i = 0; i < 5; i++) {//출력 리스트가 너무길면 안되니깐 5개로 제한 안됨
		        List<Phone> list = phoneService.findPhoneByNumberForRest(number);
		        map.put(i, list.get(i));
		    }
	    }catch(IndexOutOfBoundsException e) {//find 결과가 5보다 작으면 outofbound가 뜸
		    for (int i = 0; i < phoneService.findPhoneByNumberForRest(number).size(); i++) {
		        List<Phone> list = phoneService.findPhoneByNumberForRest(number);
		        map.put(i, list.get(i));
		    }
	    }catch(Exception e) {
	    	logger.debug(e.toString());
	    }
	    return map;
	}
	
}
