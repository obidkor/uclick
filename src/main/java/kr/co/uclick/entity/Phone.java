package kr.co.uclick.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone {
	
	@Id
	@Column(name="seq")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int seq;
	
	@Column(name="member_id")
	private Long memberId;
	
	@Column(nullable=false,length=20)//컬럼 속성 결정
	private String number;
		
	public Phone() {
		
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number =number;
	}
	
	@Override
	public String toString() {
		String result = "[phone_"+seq+"] " + number;
		return result;
	}
}