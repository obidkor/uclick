package kr.co.uclick.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.User;

public interface UserRepository 
			extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>,CustomSampleRepository{
		
	
	//Querydsl
	//queryhint docs : https://vladmihalcea.com/jpa-hibernate-query-hints/
	//querycache 적용! customize the way a given query is executed by Hibernate// query를 하이버네이트를 통해서 쉽게 조작할 수 있다.
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),//Equivalent to setCacheable
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"), //Equivalent to setCacheMode 
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User") // Equivalent to setCacheRegion
            })
	public Page<User> findAll(Predicate predicate, Pageable pageable);
	//이외 가능한 설정 	CALLABLE,COMMENT,FETCH_SIZE,FLUSH_MODE,FOLLOW_ON_LOCKING,NATIVE_LOCKMODE,
	//PASS_DISTINCT_THROUGH,READ_ONLY,TIMEOUT_HIBERNATE,TIMEOUT_JPA
	//jpa
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public Page<User> findDistinctIdByPhoneNumberContaining(String number,Pageable pageable);
	//distint를 통해서 user id가 겹치는 user 중복체크
	

}
