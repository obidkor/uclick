package kr.co.uclick.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository  
		extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>, CustomPhoneRepository{
	
	
	//querydsl
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "REFRESH"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "Phone")
            })	
	public Page<Phone> findAll(Predicate predicate,Pageable pageable);
	

	//jpa
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "Phone")
            })
	public Page<Phone> findDistinctUserByNumberContaining(String number,Pageable pageable);
	
	public Page<Phone> findDistinctUser_idByNumberLike(String number,Pageable pageable);
	
	
	public Phone findBySeq(int seq);	

}
