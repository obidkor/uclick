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
import kr.co.uclick.repository.PhoneRepository;

@Service
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;
	private QPhone p = QPhone.phone;
	private static final Logger logger = LoggerFactory.getLogger(PhoneService.class);
	
	//querydsl
	@Transactional(readOnly = true)
	public Page<Phone> findPhoneByNumber(String number,Pageable pageable) {
		logger.debug("findPhoneByNumber() : {}, {}", number, "");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like("%"+number+"%");
		Page<Phone> list = phoneRepository.findAll(predicate,pageable);
		for(int i=0; i<list.getSize();i++) {
			Hibernate.initialize(list.getContent().get(i).getUser().getPhone());
		}
		return list;
	}

	
	@Transactional(readOnly=true)
	public Page<Phone> findAll(Pageable pageable){
		logger.debug("findAll() : {}, {}", "", "");
		Predicate predicate = null;
		return phoneRepository.findAll(predicate,pageable);
	}
	
	
	public Phone findById(int phoneSeq) {
		logger.debug("findById() : {}, {}", phoneSeq, "");
		Predicate predicate = p.seq.eq(phoneSeq);
		return phoneRepository.findOne(predicate).get();
	}
	
	public Long phoneCount() {
		logger.debug("phoneCount() : {}, {}","","");
		Predicate predicate = null;
		return phoneRepository.count(predicate);
	}
	
	public Long phoneCountByNumber(String number) {//distinct때문에 사용불가
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
