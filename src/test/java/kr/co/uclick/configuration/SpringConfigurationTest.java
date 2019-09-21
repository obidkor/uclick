package kr.co.uclick.configuration;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.util.typedef.internal.U;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.QPhone;
import kr.co.uclick.entity.QUser;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.PhoneRepository;
import kr.co.uclick.repository.UserRepository;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.SampleService;
import kr.co.uclick.service.UserService;

@RunWith(SpringRunner.class)//JUnit 프레임워크의 테스트 실행 방법을 확장할 때 사용하는 애노테이션 / 테스트가 사용할 애플리케이션 컨텍스트를 만들과 관리하는 작업을 진행 
@ContextConfiguration(classes = SpringConfiguration.class)//자동으로 만들어줄 애플리케이션 컨텍스트의 설정파일위치를 지정한 것
public class SpringConfigurationTest {

	@Autowired
	SampleService sampleService;
	
	@Autowired
	UserRepository userRepository;
	private final QUser u = QUser.user;
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	PhoneService phoneService;
	
	@Autowired
	PhoneRepository phoneRepository;
	private final QPhone p = QPhone.phone;
	

	
	@Test//테스트메소드임을 지정
    public void test1() {
		User u = userService.findById(100L);
		u.addPhone(new Phone("010102929"));
		
        }

}
//	참고 : http://www.nextree.co.kr/p11104/
//	https://jdm.kr/blog/141
//
//	delete 하는법!
//	@Test//테스트메소드임을 지정
//	public void test() {	
//		
//		userService.findById(16L).get().getPhone().clear();
//		userService.delete(16L);
	
//	}
//}
