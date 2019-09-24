package kr.co.uclick.repository;


import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository  
		extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>, CustomPhoneRepository{
	
	//querydsl을 통해 jpa를 사용했을때 메서드이름이 길어지는 것을 막을 수 있다.
	//querydsl
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "Phone")
            })	
	public Page<Phone> findAll(Predicate predicate,Pageable pageable);
	//findAll에 predicate를 사용해 범용적으로 사용이 가능하다.

	//jpa
	public Phone findBySeq(int seq);	

}
	