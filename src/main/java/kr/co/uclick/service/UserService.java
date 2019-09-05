package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void delete(User entity) {
		userRepository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<User> findUserByName(String name) {
		return userRepository.findByNameLike("%"+name+"%");
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void findById(Long id) {
		userRepository.findById(id);
	}
	
}
