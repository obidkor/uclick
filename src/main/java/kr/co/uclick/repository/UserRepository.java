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
	
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "REFRESH"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public Page<User> findAll(Predicate predicate, Pageable pageable);
	
	
	
	
	//jpa
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public List<User> findByNameLike(String name);
	
	public Page<User> findDistinctIdByPhoneNumberContaining(String number,Pageable pageable);
	
	public Page<User> findAll(Pageable pageable); 
	public Page<User> findAllByOrderByIdDesc(Pageable pageable);
	
	@QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "User")
            })
	public Page<User> findByNameLike(String name,Pageable pageable);


	
	
	//query cacge : https://kwonnam.pe.kr/wiki/java/hibernate/cache
	//위의 어노테이션이안되면
	//@Cacheable("user")를 넣을 것  //@CacheEvict(value="user", allEntries = true)
	//	@QueryHints(value = {
    //@QueryHint(name = "org.hibernate.cacheRegion", value = "user-by-name")})

}
