package com.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController extends BaseController{
	
	public static final String URL = "/user/";
	
//	@Autowired
//	private CatModelMapper catDao;
	
	@Override
	protected String getViewPath(String path) {
		return URL + path;
	}
	
	@RequestMapping
	public String index(ModelMap model) {
	    return URL;
	}

}
