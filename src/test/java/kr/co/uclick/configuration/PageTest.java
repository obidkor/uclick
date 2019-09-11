package kr.co.uclick.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

@RunWith(SpringRunner.class)//JUnit 프레임워크의 테스트 실행 방법을 확장할 때 사용하는 애노테이션 / 테스트가 사용할 애플리케이션 컨텍스트를 만들과 관리하는 작업을 진행 
@ContextConfiguration(classes = SpringConfiguration.class)//자동으로 만들어줄 애플리케이션 컨텍스트의 설정파일위치를 지정한 것
public class PageTest {
	@Autowired
	UserService userService;
	
	@Test//테스트메소드임을 지정
	public void test() {	
		Page<User> page  = userService.findAllbyOrderIdDesc(PageRequest.of(0, 5));
		System.out.println(page);

	}
}
