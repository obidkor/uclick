package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.QUser;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.CustomSampleRepositoryImpl;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private final QUser u = QUser.user;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	//querydsl 사용 
	@Transactional(readOnly=true)
	public Page<User> findAll(Pageable pageable){//Allview 리스트용
		logger.debug("findAll(Pageable pageable) : {}, {}", pageable.getPageSize(), pageable.first());
		Predicate predicate = null; 
		return userRepository.findAll(predicate,pageable);
		 
	}
	
	@Transactional(readOnly = true)//이름 like검색용
	public Page<User> findUserByName(String name,Pageable pageable) {
		logger.debug("findUserByName() : {}, {}", pageable.getPageSize(), name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");
		Page<User> list = userRepository.findAll(predicate, pageable);
		return list;
	}
	
	@Transactional(readOnly = true)//멀티서치용
	public User findUserByName2(String name) {
		logger.debug("findUserByName2() : {}, {}","" , name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like(name);
		User u = userRepository.findOne(predicate).get();
		Hibernate.initialize(u.getPhone());
		return u;
	}
	
	@Transactional(readOnly = true)//restcontroller용
	public List<User> findUserByNameForRest(String name) {
		logger.debug("findUserByName2() : {}, {}","" , name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");
		List<User> u = (List<User>) userRepository.findAll(predicate);
		for(User user:u) {
			Hibernate.initialize(user.getPhone());
		}
		return u;
	}
	

	@Transactional(readOnly = true)//번호로 사용자 검색용
	public Page<User> findUserByNumber(String number,Pageable pageable) {
		logger.debug("findUserByNumber() : {}, {}", pageable.getPageSize(), number);
		if(number==null) {
			number="";
		}
		return userRepository.findDistinctIdByPhoneNumberContaining(number, pageable);
	}
	
	@Transactional(readOnly = true)
	public User findById(Long id) {//id로 찾기
		logger.debug("findById() : {}, {}", id, 1);
		Predicate predicate = u.id.eq(id);
		User user = userRepository.findOne(predicate).get();
		Hibernate.initialize(user.getPhone());
		return user;
	}
	
	public Long userCount() {//사용자 전체리스트 페이지네이션용
		logger.debug("userCount() : {}, {}","","");
		Predicate predicate = null;
		return userRepository.count(predicate);
	}
	
	public Long userCountByName(String name) {//사용자 이름 검색 페이지네이션용
		logger.debug("userCountByName() : {}, {}",name,"");
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");
		return userRepository.count(predicate);
	}
	
	public void delete(Long id) {
		logger.debug("delete() : {}, {}",id,"");
		userRepository.deleteById(id);
	}
	
	
	public void save(User user) {
		logger.debug("save() : {}, {}",user.getName(),"");
		userRepository.save(user);
	}
	
	
	@Transactional(readOnly=true)
	public List<User> findAll(){//restcontroller 테스트용
		logger.debug("findAll() : {}, {}","","");
		List<User> list = userRepository.findAll();
		for(User u : list) Hibernate.initialize(u.getPhone());
		return list;
	}

}
