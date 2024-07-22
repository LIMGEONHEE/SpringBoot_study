package com.itwill.springboot2.contronller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	@GetMapping("path")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
}
