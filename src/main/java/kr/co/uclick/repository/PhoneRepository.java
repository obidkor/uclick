package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository  extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>{
	
	public List<Phone> findByNumber(String number);
	public Phone findBySeq(int seq);

}
