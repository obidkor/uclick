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
@Cache(region = "User", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User{
	
//	private static final long serialVersionUID = 1L;

	@Id // 키값 설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, // 사용할 전략을 시퀀스로 선택
			generator = "USER_SEQ_GEN" // 식별자 생성기를 설정해놓은 USER_SEQ_GEN으로 설정
	)
	private Long id;

	@Column(nullable = false, length = 20) // 컬럼 속성 결정
	private String name;

	@Column(name = "enroll_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp enrollDate;

//	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cache(region = "User.phone", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Collection<Phone> phone;
	
	// For convenience, to take advantage of the entity state transitions and the
	// dirty checking mechanism,
	// many developers choose to map the child entities as a collection in the
	// parent object, and, for this purpose, JPA offers the @OneToMany annotation.
	//http://wonwoo.ml/index.php/post/1002
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
	    this.phone.clear();
	    this.phone.addAll(phone);
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
		phone.add(p);
		p.setUser(this);
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
		p.setUser(null);
	}

	@Override
	public String toString() {
		String result = "[" + id + "] " + name;
		for (Phone p : phone) {
			result += "\n" + p.toString();
		}
		return result;
	}

	// onetomany참고 :
	// https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
}
