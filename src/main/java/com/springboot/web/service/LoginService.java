package com.springboot.web.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {
	public boolean validateUser(String userid,String password) {
		return userid.equalsIgnoreCase("myca")
				&& password.equalsIgnoreCase("zzz");
		
	}
}
