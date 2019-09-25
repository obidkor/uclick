package kr.co.uclick.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.QUser;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@Service//service bean생성
@Transactional//트랜잭션 처리방법 ., PlatformTransactionManager를 사용하여 트랜잭션을 시작하고, 정상 여부에 따라 Commit 또는 Rollback
public class UserService {
	//@transactional이 추가되면 트랜잭션 기능이 적용된 프록시객체 생성
	@Autowired
	private UserRepository userRepository;
	private final QUser u = QUser.user;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	//querydsl 사용 
	//트랜잭션 처리가 완료된 다음에 다른 매서드가 실행되어야 한다.
	@Transactional(readOnly=true)
	public Page<User> findAll(Pageable pageable){//Allview 리스트용
		logger.debug("findAll(Pageable pageable) : {}, {}", pageable.getPageSize(), pageable.first());
		//querydsl은 jpa로 커버되지 않는 복잡한 쿼리를 사용할때 사용 / SQL을 Java로 Type-Safe하게 개발해 디버깅이 쉽다.
		Predicate predicate = null; //predicate는 검색조건 +정렬조건
		return userRepository.findAll(predicate,pageable); //페이지와 모든 리스트를 리턴
		 
	}
	//readonly는 성능최적화// 쓰기작업방지// 각메서드에 일일이 지정해줘야함.
	@Transactional(readOnly = true)//이름 like검색용
	public Page<User> findUserByName(String name,Pageable pageable) {
		logger.debug("findUserByName() : {}, {}", pageable.getPageSize(), name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");//검색조건(where)
		Page<User> list = userRepository.findAll(predicate, pageable);
		return list; //Containing으로 검색된 결과 리턴
	}
	
	@Transactional(readOnly = true)//멀티서치용
	public User findUserByName2(String name) {
		logger.debug("findUserByName2() : {}, {}","" , name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like(name);//정확히 검색되는 조검 검색/eq해도됨
		User u = null;
		try {	
		u = userRepository.findOne(predicate).get();
		}catch(NoSuchElementException e) {
			//do nothing
		}
		Hibernate.initialize(u.getPhone());//fetch type이 기본 LAZY이기 때문에 hibernate를 사용해 collection의 세션관리.
		return u;//정확히 검색된 하나를 리턴
	}
	
	@Transactional(readOnly = true)//restcontroller용
	public List<User> findUserByNameForRest(String name) {
		logger.debug("findUserByName2() : {}, {}","" , name);
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");//이름으로 검색되는 모든 사용자리스트 리턴
		List<User> u = (List<User>) userRepository.findAll(predicate);
		for(User user:u) {
			Hibernate.initialize(user.getPhone());//안의 컬렉션을 쓰기위해 session 생성
		}
		return u;//리턴
	}
	

	@Transactional(readOnly = true)//번호로 사용자 검색용
	public Page<User> findUserByNumber(String number,Pageable pageable) {
		logger.debug("findUserByNumber() : {}, {}", pageable.getPageSize(), number);
		if(number==null) {
			number="";
		}
		//id로 distinct된 사용자리스트 출력,리턴
		return userRepository.findDistinctIdByPhoneNumberContaining(number, pageable);
	}
	
	@Transactional(readOnly = true)
	public User findById(Long id) {//id로 찾기
		logger.debug("findById() : {}, {}", id, 1);
		Predicate predicate = u.id.eq(id);//아이디로 검색조건
		User user = userRepository.findOne(predicate).get();//하나를 리턴
		Hibernate.initialize(user.getPhone());// collection 세션스타트
		return user;
	}
	
	public Long userCount() {//사용자 전체리스트 페이지네이션용
		logger.debug("userCount() : {}, {}","","");
		Predicate predicate = null;
		return userRepository.count(predicate);//모든 사용자 리스트의 개수를 리턴
	}
	
	public Long userCountByName(String name) {//사용자 이름 검색 페이지네이션용
		logger.debug("userCountByName() : {}, {}",name,"");
		if(name==null) {
			name="";
		}
		Predicate predicate = u.name.like("%"+name+"%");//containing name으로 검색된 리스트의 개수를 리턴
		return userRepository.count(predicate);
	}
	
	public void delete(Long id) {//사용자 삭제(cascade.all)
		logger.debug("delete() : {}, {}",id,"");
		userRepository.deleteById(id);
	}
	
	
	public void save(User user) {//사용자 저장
		logger.debug("save() : {}, {}",user.getName(),"");
		userRepository.save(user);
	}
	
	
	@Transactional(readOnly=true)//
	public List<User> findAll(){//restcontroller 테스트용
		logger.debug("findAll() : {}, {}","","");
		List<User> list = userRepository.findAll();
		for(User u : list) Hibernate.initialize(u.getPhone());
		return list;
	}

}
