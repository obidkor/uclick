package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository  
		extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>,CustomSampleRepository{
	
	
	//querydsl
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public Iterable<Phone> findAll(Predicate predicate);
	
	
	
	//jpa
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public List<Phone> findByNumberContaining(String number);
	
	
	public Phone findBySeq(int seq);	

}
