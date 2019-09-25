package kr.co.uclick.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@SequenceGenerator(name = "USER_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "USER_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
// allocationSize가 1이면 올릴때 마다 시퀀스를 호출해서 적절한 숫자가 좋으나 initialValue가 1이면 1로 설정해야
// 오류가 안남
// 참고 : https://dololak.tistory.com/479
)
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class) // 양방향으로 매핑된 엔티티들을 파싱할때 Infinite recursion에러 방지
@Cache(region = "User", usage = CacheConcurrencyStrategy.READ_WRITE) // cache region설정, cache starategy_readwrite
public class User {
//cachecouncurrenycy strategy : read_write : data가 update될 필요가 있을 경우 사용. 내장된 cacheprovider가 트랜잭션 locking을 보장 하지 않음
// nonstrict_read_write : data가 update될 필요가 있을 경우 사용 그 경우가 readwrite 보다 적을 때 사용됨. 트랜잭션 관리가 비교적 엄격하지 않음.	

	@Id // 키값 설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, // 사용할 전략을 시퀀스로 선택
			generator = "USER_SEQ_GEN" // 식별자 생성기를 설정해놓은 USER_SEQ_GEN으로 설정
	)
	private Long id;

	@Column(nullable = false, length = 20) // 컬럼 속성 결정
	private String name;
	
	// timstamp로 데이터가 생성될때 자동으로 날짜 등록
	@Column(name = "enroll_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp enrollDate;

	// fetch type lazy--> 지연로딩 --> 유저 엔티티 시행시 getPhone실행시 phone Collection을 로딩하지 못함(no session)-->hibernate.initialize
	// eager--> 즉시로딩 --> getphone실행시 phone collection 즉시 로딩 --> 성능저하
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cache(region = "User.phone", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Collection<Phone> phone;
	
	// For convenience, to take advantage of the entity state transitions and the
	// dirty checking mechanism,
	// many developers choose to map the child entities as a collection in the
	// parent object, and, for this purpose, JPA offers the @OneToMany annotation.
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<Phone> getPhone() {
		return phone;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(Collection<Phone> phone) {
		this.phone.clear();// 컬렉션에 적용된 캐시를 갱신하기 위해 클리어후
		this.phone.addAll(phone);// 매개변수로 들어간 collection을 다시 넣어준다.
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name) {
		this.name = name;
	}

	public void addPhone(Phone p) {
		if (phone == null) {
			phone = new ArrayList<Phone>();
		}
		phone.add(p);// 양방향 맵핑이 되어있기 때문에 collection에 더해주는 것으로 phone 엔티티를 추가할 수 잇음
		p.setUser(this);// 전화기 엔티티에 이 사용자를 매핑해준다.
	}

	public Timestamp getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Timestamp enrollDate) {
		this.enrollDate = enrollDate;
	}

	public void removePhone(Phone p) {
		if (phone == null) {
			phone = new ArrayList<Phone>();
		}
		phone.removeIf(ph -> ph.getSeq() == p.getSeq());
		// 람다식 : foreach seq가 같은 phone객체를 삭제
		p.setUser(null);// 전화기 엔티티에 사용자 매핑 삭제
	}

	@Override
	public String toString() {
		String result = "[" + id + "] " + name;
		for (Phone p : phone) {
			result += "\n" + p.toString();// 디버깅용
		}
		return result;
	}

	// onetomany참고 :
	// https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
}
