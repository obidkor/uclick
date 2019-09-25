package kr.co.uclick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.uclick.dto.Paging;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

//controller는 httprequest를 받아 view resovler 를 통해 httpresponse body로 리턴
@Controller // controller =>클라이언트 요청을 처리할 메서드 구현
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired // service 의존성 주입
	private UserService userService;

	@Autowired // service 의존성 주입
	private PhoneService phoneService;

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/0";
	}

	// @GetMapping : @RequestMapping(method = RequestMethod.GET) 의 축약형으로써, 애너테이션만 보고
	// 무슨 메소드 요청인지 바로 알아볼 수 있다
	@RequestMapping(value = "/{page}", method = { RequestMethod.GET, RequestMethod.POST }) // method로 get방식과 post방식으로 모두
																							// 파라미터를 받음
	public String userList(@PathVariable("page") Integer page, // pathvariable을 통한 get방식피라미터 +경로지정
			@RequestParam HashMap<String, String> map, // get방식으로 검색칸에서 오는 파라미터를 받음
			Model model) {
		if (map.get("search") == null) {
			map.put("search", "");// search의 값이 null인경우 사용자 전체리스트 출력함
		}
		if (page < 0) {
			page = 0;// 페이지 에러방지
		}
		// 페이징메서드 호출,계싼후 리턴
		Paging p = new Paging();
		p.setPagenow(page + 1).setCountList(5);
		Pageable pageable = PageRequest.of(page, p.countList);// Page에 넣을 pageable객체 생성

		if (map.get("search").equals("2")) {// 이름 검색일 경우
			p.setTotalCount(userService.userCountByName(map.get("value")).intValue());// 페이지 토탈 수를 이름 검색 사용자리스트로 카운트
			model.addAttribute("users", userService.findUserByName(map.get("value"), pageable));// 리스트를 model로 리턴
		} else if (map.get("search").equals("1")) {// 번호 검색일 경우
			p.setTotalCount((int) userService.findUserByNumber(map.get("value"), pageable).getTotalElements());// 페이지 토탈수를 번호검색사 사용자리스트로	카운트
			model.addAttribute("users", userService.findUserByNumber(map.get("value"), pageable));// 리스트를 model로 리턴
		} else if (map.get("search") == "") {// 전체 사용자리스트 일경우
			p.setTotalCount(userService.userCount().intValue());// 페이지 토탈 수를 전체 사용자리스트를 카운트
			model.addAttribute("users", userService.findAll(pageable));// 리스트를 model로 리턴
		}

		// 뽑혀야 할 전체 페이지버튼 수를 출력하기위한 model
		p.calcPage();// 페이지 계산
		model.addAttribute("startRange", p.startPage);
		model.addAttribute("endRange", p.endPage);
		model.addAttribute("endPage", p.totalPage);
		// 현재페이지
		model.addAttribute("page", p.pagenow);
		// 콤보박스value와 검색 input text의 value
		model.addAttribute("search", map.get("search"));
		model.addAttribute("value", map.get("value"));

		return "userList";
	}

	@GetMapping(value = "oneUser.html")
	public String userOneView(@RequestParam("id") String id, Model model) // 파라미터 user_id
	{
		Long uid = Long.parseLong(id);
		User user = userService.findById(uid);// user 객체 반환
		model.addAttribute("user", user);
		return "oneUser";
	}

	@GetMapping(value = "userNewForm.html")
	public String userNewForm(Model model) {
		return "userNewForm";
	}

	@PostMapping(value = "userEditForm.html")
	public String userEditForm(Long id, Model model) {
		model.addAttribute("thisUser", userService.findById(id));// 수정을 위해서는 userid가 필요함
		return "userEditForm";
	}

	@PostMapping(value = "userSave.html")
	public String userSave(User user, @RequestParam(name = "number", required = false) // number의 경우 있을 수도 없을수도 있기떼믄에 required=false
	List<String> numbers, Model model) {// 넘어올경우 List로 받음
		try {
			try {
				for (String number : numbers) {// 사용자 등록 + 전화기등록
					user.addPhone(new Phone(number));// number가 넘어올경우 collection에 addphone
				}
			} catch (NullPointerException e) {// 사용자 등록
				userService.save(user);// 저장
				return "redirect:/0";// 홈페이지로 리턴
			}
			userService.save(user);
		} catch (DataIntegrityViolationException e) {// 번호가 중복되는 경우 에러가 발생
			model.addAttribute("error", e);// 에러페이지로 리턴
			return "error";
		}
		return "redirect:/0";
	}

	@RequestMapping(value = "userDelete.html") // 사용자 삭제
	public String userDelete(Model model, @RequestParam HashMap<String, String> map) {
		String sid = map.get("id");
		long id = Long.parseLong(sid);
		userService.findById(id).getPhone().clear();// 사용자 삭제의 경우 child의 collection이 clear되어야함
		userService.delete(id);// 삭제
		return "redirect:/0";
	}

/////////////phone 서비스

	@RequestMapping(value = "phoneList.html/{page}", method = { RequestMethod.GET, RequestMethod.POST }) // get,pot로 동시에 받음
	public String phoneList(@PathVariable Integer page, // 현재 페이지
			@RequestParam HashMap<String, String> map, // 검색어 value
			Model model) {
		if (page == null || page < 0) {
			page = 0;// 페이지 초기화 에러방지
		}

		// 페이징 처리
		Paging p = new Paging();
		p.setPagenow(page + 1).setCountList(5);
		Pageable pageable = PageRequest.of(page, p.countList);

		if (map.get("value") == null) {// 전화기 전체리스트 검색
			p.setTotalCount(phoneService.phoneCount().intValue());// 페이지 카운트
			model.addAttribute("phones", phoneService.findAll(pageable));// 전화기 전체리스트 리턴
		} else {// 번호검색 전화기 리스트 검색
			p.setTotalCount(phoneService.phoneCountByNumber(map.get("value")).intValue());// 페이지 카운트
			model.addAttribute("phones", phoneService.findPhoneByNumber(map.get("value"), pageable));// 전화기 검색리스트 리턴
		}

		// 페이지버튼 구현
		p.calcPage();// 페이지 계산
		model.addAttribute("startRange", p.startPage);
		model.addAttribute("endRange", p.endPage);
		model.addAttribute("endPage", p.totalPage);
		// 현재페이지
		model.addAttribute("page", p.pagenow);
		// 리스트
		model.addAttribute("value", map.get("value"));

		return "phoneList";
	}

	@GetMapping(value = "phoneNewForm.html")
	public String phoneNewForm(@RequestParam("id") String id, Model model) {
		Long uid = Long.parseLong(id);
		model.addAttribute("user", userService.findById(uid));// collection에 addphone하기위해 user를 전달
		return "phoneNewForm";
	}

	@PostMapping(value = "phoneSave.html")
	public String phoneSave(Long id, // user_id
			@RequestParam(name = "number") List<String> numbers, Model model) {// number가 리스트로 넘어옴

		User user = null;
		logger.debug("numbers : " + numbers.size());
		try {
			user = userService.findById(id);// phone을 넣을 user객체 세팅
			try {
				for (String number : numbers) {
					user.addPhone(new Phone(number));// addphone
				}
			} catch (NullPointerException e) {
				return "redirect:oneUser.html?id=" + user.getId();// 등록하지 않을경우 뒤로가기
			}
			userService.save(user);// save
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("error", e);// 번호가 중복일 경우 에러처리
			return "error";
		}
		return "redirect:oneUser.html?id=" + user.getId();
	}

	@PostMapping(value = "phoneEditForm.html")
	public String phoneEditForm(int seq, Model model) {
		model.addAttribute("thisPhone", phoneService.findById(seq));// update는 phone의 seq가 필요함
		return "phoneEditForm";
	}

	@PostMapping(value = "phoneUpdate.html")
	public String phonePupdate(Phone phone, Model model) {// 파라미터로 수정된 phone 엔티티
		try {
			String number = phone.getNumber();// number를 세팅
			phone = phoneService.findById(phone.getSeq());// 수정한 phone을 세팅
			phone.setNumber(number);// 수정된 number을 set
			phoneService.save(phone);// save
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("error", e);// 번호가 중복될 경우
			return "error";
		}
		return "redirect:oneUser.html?id=" + phoneService.findById(phone.getSeq()).getUser().getId();
	}

	@RequestMapping(value = "phoneDelete.html") // 전하기 삭제
	public String phoneDelete(Model model, @RequestParam HashMap<String, String> map) {
		String pid = map.get("seq");
		String uid = map.get("uid");
		int seq = Integer.parseInt(pid);
		Long id = Long.parseLong(uid);

		User u = userService.findById(id);// 전화기가 수정될 user세팅
		Phone p = phoneService.findById(seq);// 수정할 phone세팅
		u.removePhone(p);// collection에서 같은 phone삭제
		// children.remove(child)는 사실 child 자체를 삭제하라는 게 아니라 child가 parent의 children의 하나로
		// 존재하는 관계를 remove 하라는 것이다.
		// 따라서 child 자체를 delete 하는 게 아니라 parent_id에 null 값을 넣는 update를 실행하는 게 정확히 맞다.
		userService.save(u);// 캐시갱신용(collection쪽에서 갱신이 일어나야 캐시도 갱신됨)
		phoneService.delete(p);// null로변한 DB안의 데이터 삭제
		return "redirect:oneUser.html?id=" + u.getId();

	}

	// 멀티서치
	@GetMapping(value = "multiList.html")
	public String multiView(@RequestParam HashMap<String, String> map, Model model) {
		String[] list = map.get("value").split(",");// input으로 받은 value를 쉼표를 기준으로 split

		if (map.get("search").equals("1")) {// 번호검색일 경우
			List<Phone> phones = new ArrayList<Phone>();
			for (int i = 0; i < list.length; i++) {// list길이만큼
				phones.add(phoneService.findPhoneByNumber2(list[i].trim()));// 빈칸은 짤라서 리스트 출력
			}
			model.addAttribute("phones", phones);
		} else if (map.get("search").equals("2")) {// 이름검색일 경우
			List<User> users = new ArrayList<User>();
			for (int i = 0; i < list.length; i++) {
				users.add(userService.findUserByName2(list[i].trim()));// 빈칸은 짤라서 리스트 검색
			}
			model.addAttribute("users", users);
		}
		return "multiList";
	}

}
