package kr.co.uclick.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.uclick.entity.Phone;

public interface CustomPhoneRepository {

	public Page<Phone> findDistinctUserByNumber(String number, Pageable pageable);
	
}
