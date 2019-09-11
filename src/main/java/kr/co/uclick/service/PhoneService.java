package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

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
	
	
	@Transactional
	public void delete(Phone p) {
		phoneRepository.delete(p);
	}
	
	@Transactional(readOnly=true)
	public List<Phone> findAll(){
		return phoneRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Phone> findPhoneByNumber(String number) {
		return phoneRepository.findByNumberContaining(number);
	}
	
	public void save(Phone phone) {
		phoneRepository.save(phone);
	}

	public Phone findById(int phoneSeq) {
		return phoneRepository.findBySeq(phoneSeq);
	}

	
}
