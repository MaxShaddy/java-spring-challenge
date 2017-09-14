package org.sgolub.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	
	@RequestMapping({"/", "/index", "/main"})
	public String index(){
	
		return "default";
	}
	
}
