package kr.co.uclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.service.SampleService;

@Controller//컨트롤러 선언
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired//service autowire
	private SampleService sampleService;

	@GetMapping(value = "list.html")//@GetMapping : @RequestMapping(method = RequestMethod.GET) 의 축약형으로써, 애너테이션만 보고 무슨 메소드 요청인지 바로 알아볼 수 있다
	public String list(Model model) {
		model.addAttribute("users", sampleService.findAll());
		return "list";
	}

	@GetMapping(value = "newForm.html")
	public String newForm(Model model) {
		return "newForm";
	}

	@GetMapping(value = "editForm.html")
	public String editForm(Long sampleId, Model model) {
		sampleService.findById(sampleId);
		return "editForm";
	}

	@PostMapping(value = "save.html")
	public String save(Sample sample, Model model) {
		return "redirect:list.html";
	}

	@DeleteMapping(value = "delete.html")
	public String delete(Long sampleId, Model model) {
		return "redirect:list.html";
	}

	@GetMapping(value = "sample.html")
	public String sample(String name, Sample sample, Model model) {

		logger.debug("sample name : {}", name);
		logger.debug("Sample.name : {}", sample.getName());

		model.addAttribute("samples", sampleService.findAll());
		model.addAttribute("sample", sample);
		model.addAttribute("findSampleByName", sampleService.findSampleByName(name));
		return "sample";
	}
}
