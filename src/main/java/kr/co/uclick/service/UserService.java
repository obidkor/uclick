package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<User> findUserByName(String name,Pageable pageable) {
		return userRepository.findByNameLike("%"+name+"%", pageable);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User findById(Long id) {
		Optional<User> u = userRepository.findById(id);
		User user = u.get();
		Hibernate.initialize(user.getPhone());
		return user;
	}
	
	
	public Page<User> findAll(Pageable pageable){
		Page<User> page = userRepository.findAll(pageable);
		return page;
	}
	
	public Page<User> findAllbyOrderIdDesc(Pageable pageable){
		Page<User> page = userRepository.findAllByOrderByIdDesc(pageable);
		return page;
	}
	
	public Long userCount() {
		return userRepository.count();
		
	}
	

}
