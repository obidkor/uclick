package kr.co.uclick.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uclick.dto.Paging;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.SampleService;
import kr.co.uclick.service.UserService;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired//service autowire
	private UserService userService;
	
	@Autowired
	private PhoneService phoneService;
	
	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/0";
	}
	
	//@GetMapping : @RequestMapping(method = RequestMethod.GET) 의 축약형으로써, 애너테이션만 보고 무슨 메소드 요청인지 바로 알아볼 수 있다
	@RequestMapping(value = "/{page}" ,method = {RequestMethod.GET, RequestMethod.POST})
	public String userList(@PathVariable("page") Integer page, 
							@RequestParam HashMap<String,String> map, 
							Model model) {
		if(page==null||page<0) {
	    	page=0;
	    }
		Paging p = new Paging();
		p.setPagenow(page+1).setCountList(5);
		
		Pageable pageable = PageRequest.of(page, p.countList);
		
		//뽑혀야 할 전체 페이지버튼 수
	    try {
		if(map.get("search").equals("2")) {
	    	p.setTotalCount(userService.userCountByName(map.get("value")).intValue());
	    }else if(map.get("search").equals("1")) {
	    	p.setTotalCount(userService.findUserByNumber(map.get("value"), pageable).getTotalPages());
	    }
	    }catch(NullPointerException e) {
	    	p.setTotalCount(userService.userCount().intValue());
	    }
	    p.calcPage();
		
		model.addAttribute("startRange", p.startPage);
		model.addAttribute("endRange", p.endPage);
		model.addAttribute("page", p.pagenow);
	      
		try {
		if(map.get("search").equals("2")) {	
			model.addAttribute("search",map.get("search"));
			model.addAttribute("users", userService.findUserByName(map.get("value"),pageable));
		}else if(map.get("search").equals("1")) {
			model.addAttribute("search",map.get("search"));
			model.addAttribute("users", userService.findUserByNumber(map.get("value"), pageable));
		}
		}catch(NullPointerException e) {
			model.addAttribute("users", userService.findAll(pageable));
		}
		return "userList";
	}
	
	@GetMapping(value = "oneUser.html")
	public String userOneView(@RequestParam("id") String id,Model model) 
	{
		Long uid = Long.parseLong(id);
		User user = userService.findById(uid);
		model.addAttribute("user", user);
		return "oneUser";	
	}
	
	@GetMapping(value = "userNewForm.html")
	public String userNewForm(Model model) {
		return "userNewForm";
	}

	@PostMapping(value = "userEditForm.html")
	public String userEditForm(Long id, Model model) {
		model.addAttribute("thisUser",userService.findById(id));
		return "userEditForm";
	}
	
	@PostMapping(value = "userSave.html")
	public String userSave(User user,@RequestParam(name="number",required=false) List<String> numbers, Model model) {
		try {
			try {
				for(String number:numbers) {
					user.addPhone(new Phone(number));
				}
			}catch(NullPointerException e) {
				return "redirect:/0";
			}
			userService.save(user);
		}catch(DataIntegrityViolationException e) {
			model.addAttribute("error",e);
			return "error";
		}
		return "redirect:/0";
	}

	@RequestMapping(value = "userDelete.html")// 사용자 삭제
	public String userDelete(Model model,@RequestParam HashMap<String,String> map) {
		String sid = map.get("id");
		long id = Long.parseLong(sid);
		userService.findById(id).getPhone().clear();
		userService.delete(id);
		return "redirect:/0";
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping(value="phoneList.html/{page}")
	public String phoneList(@PathVariable Integer page,Model model) {
		if(page==null||page<0) {
	    	page=0;
	    }
		Paging p = new Paging();
		p.setPagenow(page+1).setCountList(5);
		p.setTotalCount(phoneService.phoneCount().intValue());
		p.calcPage();
		
		model.addAttribute("startRange", p.startPage);
		model.addAttribute("endRange", p.endPage);
		model.addAttribute("page", p.pagenow);
		
		
		Pageable pageable = PageRequest.of(page, p.countList);
		model.addAttribute("phones",phoneService.findAll(pageable));
		return "phoneList";
	}
	
	@GetMapping(value = "phoneNewForm.html")
	public String phoneNewForm(@RequestParam("id") String id,Model model) {
		Long uid = Long.parseLong(id);
		model.addAttribute("user", userService.findById(uid));
		return "phoneNewForm";
	}
	
	@PostMapping(value = "phoneSave.html")
	public String phoneSave(Long id,String number,Model model) {	
		
		User user = null;
		try {
			user = userService.findById(id);
			user.addPhone(new Phone(number));
			userService.save(user);
		}catch(DataIntegrityViolationException e) {
			model.addAttribute("error",e);
			return "error";
		}
		return "redirect:oneUser.html?id="+user.getId();
	}
	
	@PostMapping(value = "phoneEditForm.html")
	public String phoneEditForm(int seq, Model model) {
		model.addAttribute("thisPhone",phoneService.findById(seq));
		return "phoneEditForm";
	}
	
	@PostMapping(value = "phoneUpdate.html")
	public String phonePupdate(Phone phone, Model model) {
		try {
		String number = phone.getNumber();
		phone = phoneService.findById(phone.getSeq());
		phone.setNumber(number);
		phoneService.save(phone);
		}catch(DataIntegrityViolationException e) {
			model.addAttribute("error",e);
			return "error";
		}
		return "redirect:oneUser.html?id="+phoneService.findById(phone.getSeq()).getUser().getId();
	}
	
	@RequestMapping(value = "phoneDelete.html")// 사용자 삭제
	public String phoneDelete(Model model,@RequestParam HashMap<String,String> map) {
		String pid = map.get("seq");
		String uid = map.get("uid");
		int seq = Integer.parseInt(pid);
		Long id = Long.parseLong(uid);

		
		User u = userService.findById(id);
		Phone p = phoneService.findById(seq);//이게문제
		u.removePhone(p);
//		userService.save(u);//캐시갱신용(collection쪽에서 갱신이 일어나야 캐시도 갱신됨)
		phoneService.delete(p);
		return "redirect:oneUser.html?id="+u.getId();
		
	}
	
}
