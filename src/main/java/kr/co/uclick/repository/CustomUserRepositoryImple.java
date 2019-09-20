package kr.co.uclick.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import kr.co.uclick.entity.QUser;
import kr.co.uclick.entity.User;

public class CustomUserRepositoryImple extends QuerydslRepositorySupport implements CustomUserRepository {

	private QUser u = QUser.user;
	public CustomUserRepositoryImple() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	
}
