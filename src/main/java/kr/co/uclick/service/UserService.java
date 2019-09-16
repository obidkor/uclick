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
	public Page<User> findAll(Pageable pageable){
		logger.debug("findAll(Pageable pageable) : {}, {}", pageable.getPageSize(), pageable.first());
		Predicate predicate = null; 
		return userRepository.findAll(predicate,pageable);
		 
	}
	
	@Transactional(readOnly = true)
	public Page<User> findUserByName(String name,Pageable pageable) {
		logger.debug("findUserByName() : {}, {}", pageable.getPageSize(), name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");
		return userRepository.findAll(predicate, pageable);
	}
	@Transactional(readOnly = true)
	public User findById(Long id) {
		logger.debug("findById() : {}, {}", id, 1);
		Predicate predicate = u.id.eq(id);
		User user = userRepository.findOne(predicate).get();
		Hibernate.initialize(user.getPhone());
		return user;
	}
	
	public Long userCount() {
		logger.debug("userCount() : {}, {}","","");
		Predicate predicate = null;
		return userRepository.count(predicate);
	}
	
	public Long userCountByName(String name) {
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
	public List<User> findAll(){
		logger.debug("findAll() : {}, {}","","");
		return userRepository.findAll();
	}

}
