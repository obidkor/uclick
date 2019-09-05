package kr.co.uclick.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity//hibernate entity 선언
public class Sample {

	@Id//키 필드라는 정보이다
	@TableGenerator(name = "sample")//sample이라는 table을 만듦
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sample")//@GeneratedValue는 주키의 값을 위한 자동 생성 전략을 명시하는데 사용한다. 
	//GeneratedValue 어노테이션에는 자동 생성 전략을 4개로 지정해 줄 수 있는데 지정해 주지 않으면 기본값은 auto임 
	//AUTO(default)	JPA 구현체가 자동으로 생성 전략을 결정한다.
	//IDENTITY	기본키 생성을 데이터베이스에 위임한다. 예를 들어 MySQL의 경우 AUTO_INCREMENT를 사용하여 기본키를 생성한다.
	//SEQUENCE	데이터베이스의 특별한 오브젝트 시퀀스를 사용하여 기본키를 생성한다.
	//TABLE	데이터베이스에 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성한다.
	
	private Long id;

	private String name;

	private int number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	//Entity 클래스 생성시 Querydsl에서 같은 패키지에 Q클래스를 자동생성한다
	//https://engkimbs.tistory.com/828
	//Querydsl을 사용하여 Hibernate에서는 만들기 힘든 쿼리 등을 작성가능 구현은 Repository에 적어준다.
	//사용법 : http://www.querydsl.com/static/querydsl/4.0.1/reference/ko-KR/html_single/#d0e285
	//사용법 : https://adrenal.tistory.com/23

}
