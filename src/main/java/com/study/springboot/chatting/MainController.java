package com.study.springboot.chatting;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Controller
public class MainController
{
	@RequestMapping("/guest/index")
	public String guest()
	{
		return "/guest/index";
	}
	@RequestMapping("/seller/index")
	public String seller()
	{
		return "/seller/index";
	}
	@RequestMapping("/buyer/index")
	public String buyer()
	{
		return "/buyer/index";
	}
	
	@RequestMapping("/admin/admin")
	public String admin()
	{
		return "/admin/admin";
	}
		
	@Bean
	public ServerEndpointExporter serverEndpointExporter()
	{
		return new ServerEndpointExporter();
	}
}
