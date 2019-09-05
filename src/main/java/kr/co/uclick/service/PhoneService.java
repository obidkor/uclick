package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.repository.PhoneRepository;

@Service
@Transactional
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;
	
	public void delete(Phone entity) {
		phoneRepository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public List<Phone> findAll(){
		return phoneRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Phone> findPhoneByNumber(String number) {
		return phoneRepository.findByNumber(number);
	}
	
	public void save(Phone phone) {
		phoneRepository.save(phone);
	}

	public void findById(int phoneSeq) {
		phoneRepository.findBySeq(phoneSeq);
	}
	
}
