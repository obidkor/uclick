package kr.co.uclick.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;



@Entity
@SequenceGenerator(
        name="USER_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="USER_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        //allocationSize가 1이면 올릴때 마다 시퀀스를 호출해서 적절한 숫자가 좋으나 initialValue가 1이면 1로 설정해야 오류가 안남
        //참고 : https://dololak.tistory.com/479
        )
public class User {

	@Id//키값 설정
	@GeneratedValue(
	            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
	            generator="USER_SEQ_GEN" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정        
	            )
	private Long id;
	
	@Column(nullable=false,length=20)//컬럼 속성 결정
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="member_id")
	private Collection<Phone> phone;

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
		this.phone = phone;
	}

	public User(Long id, String name, Collection<Phone> phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
	
	public User() {
		
	}


	@Override
	public String toString() {
		String result = "["+id+"] " + name;
		for( Phone p : phone ){
			result += "\n" + p.toString();
		}
		return result;
	}
	
	
	
}
