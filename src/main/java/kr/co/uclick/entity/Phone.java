package kr.co.uclick.entity;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name="Phone")
@Table(name="phone")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phone {

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int seq;

	@Column(nullable = false, length = 20) // 컬럼 속성 결정
	private String number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Phone(String number) {
		this.number=number;
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
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		String result = "[phone_" + seq + "] " + number;
		return result;
	}
}