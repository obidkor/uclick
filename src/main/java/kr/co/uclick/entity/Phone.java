package kr.co.uclick.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="phone",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"number"})
		})//number에 unique key 지정
@Cache(region = "Phone",usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phone {

//	private static final long serialVersionUID = -21L;
	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int seq;

	@Column // 컬럼 속성 결정
	private String number;

	//일대다 양방향 매핑은 child가 parent를 알기 때문에 불필요한 오버헤드가 발생하지 않는다. 1:N에서 N이 적을때는 양방향이 안좋을 수도 있다.
	@ManyToOne
	@JoinColumn(name = "user_id")//join table을 통해 
	private User user;
	//일대다 양방향 매핑은 도메인 로직 상에서 parent를 몰라도 되는 child에게 굳이 parent를 강제로 알게 만드는 것이 단점인데, 
	//이 단점은 parent에 대한 public getter 메서드를 만들지 않거나 또는 극단적으로 아예 parent에 대한 getter 메서드를 만들지 않는 방식으로 보완
	
	@Column(name="enroll_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp enrollDate;

	public Phone() {

	}
	
	public Phone(String number) {
		this.number=number;
	}

	public Timestamp getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Timestamp enrollDate) {
		this.enrollDate = enrollDate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User getUser() {
		return user;//그러나 getUser는 비지니스 로직상 필요하다.
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		String result = "[phone_" + seq + "] " + number; //디버깅용
		return result;
	}
}