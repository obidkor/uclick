package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;

public interface SampleRepository
		extends JpaRepository<Sample, Long>, QuerydslPredicateExecutor<Sample>, CustomSampleRepository {
	// Dao라고 불리는 DB Layer 접근자입니다. 
	//JPA에선 Repository라고 부르며 인터페이스로 생성합니다. 
	//단순히 인터페이스를 생성후, JpaRepository<Entity클래스, PK타입>를 상속하면 기본적인 CRUD 메소드가 자동생성 됩니다. 
	//특별히 @Repository를 추가할 필요도 없습니다.

	public List<Sample> findSampleByNameLike(String name);
}
