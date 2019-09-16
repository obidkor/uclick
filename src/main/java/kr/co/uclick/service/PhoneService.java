package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Phone> findPhoneByNumber(String number) {
		logger.debug("findPhoneByNumber() : {}, {}", number, "");
		if(number==null) {
			number="";
		}
		Predicate predicate = p.number.like("%"+number+"%");
		return (List<Phone>) phoneRepository.findAll(predicate);
	}
	
	@Transactional(readOnly=true)
	public List<Phone> findAll(){
		logger.debug("findAll() : {}, {}", "", "");
		Predicate predicate = null;
		return (List<Phone>) phoneRepository.findAll(predicate);
	}
	
	
	public Phone findById(int phoneSeq) {
		logger.debug("findById() : {}, {}", phoneSeq, "");
		Predicate predicate = p.seq.eq(phoneSeq);
		return phoneRepository.findOne(predicate).get();
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
