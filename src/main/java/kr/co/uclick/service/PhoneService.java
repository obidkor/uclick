package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.QPhone;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.PhoneRepository;
import kr.co.uclick.repository.UserRepository;

@Service
public class PhoneService {

	//autowired는 스프링 빈요구사항과 매칭되는 bean을 찾아 선언된  bean간의 의존성을 자동 주입시킴
	@Autowired//없으면 bean설정을 xml파일에 달아야함
	private PhoneRepository phoneRepository;//service에 Repo의존성 주입
	
	//QueryDsl을 통해 jpa를 쉽게쓸수 있음 Q도메인 객체를 통해 작성
	private QPhone p = QPhone.phone;
	private static final Logger logger = LoggerFactory.getLogger(PhoneService.class);
	
	//querydsl
	@Transactional(readOnly = true)//리스트검색용
	public Page<Phone> findPhoneByNumber(String number,Pageable pageable) {
		logger.debug("findPhoneByNumber() : {}, {}", number, "");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like("%"+number+"%");
		Page<Phone> list = phoneRepository.findAll(predicate,pageable);
		return list;
	}
	
	@Transactional(readOnly = true)//멀티서치용
	public Phone findPhoneByNumber2(String number) {
		logger.debug("findPhoneByNumber() : {}, {}", number, "");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like(number);
		Phone p = phoneRepository.findOne(predicate).get();
		Hibernate.initialize(p.getUser().getPhone());
		return p;
	}
	
	@Transactional(readOnly = true)//RestController용
	public List<Phone> findPhoneByNumberForRest(String number) {
		logger.debug("findPhoneByNumberForRest() : {}, {}", number, "");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like("%"+number+"%");
		List<Phone> list = (List<Phone>) phoneRepository.findAll(predicate);
		for(int i=0; i<list.size();i++) {
			Hibernate.initialize(list.get(i).getUser().getPhone());
		}
		return list;
	}

	
	@Transactional(readOnly=true)//모든 전화기리스트 출력용
	public Page<Phone> findAll(Pageable pageable){
		logger.debug("findAll() : {}, {}", "", "");
		Predicate predicate = null;
		return phoneRepository.findAll(predicate,pageable);
	}
	
	
	public Phone findById(int phoneSeq) {//하나 출력용
		logger.debug("findById() : {}, {}", phoneSeq, "");
		Predicate predicate = p.seq.eq(phoneSeq);
		return phoneRepository.findOne(predicate).get();
	}
	
	public Long phoneCount() {//전체폰리스트 페이지네이션용
		logger.debug("phoneCount() : {}, {}","","");
		Predicate predicate = null;
		return phoneRepository.count(predicate);
	}
	
	public Long phoneCountByNumber(String number) {//전체 폰리스트->번호검색 페이지네이션용
		logger.debug("phoneCountByName() : {}, {}",number,"");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like("%"+number+"%");
		return phoneRepository.count(predicate);
	}
	
	//jpa
	public void delete(Phone p) {
		logger.debug("delete() : {}, {}", p.getNumber(), "");
		phoneRepository.delete(p);
	}
	

	
	public void save(Phone phone) {
		logger.debug("save() : {}, {}", phone.getNumber(), "");
		phoneRepository.save(phone);
	}

	




	
}
