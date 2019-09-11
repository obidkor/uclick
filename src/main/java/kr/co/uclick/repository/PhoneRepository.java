package kr.co.uclick.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository  extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>{
	@Cacheable("phone")
	public List<Phone> findByNumberContaining(String number);
	
	
	public Phone findBySeq(int seq);	

}
