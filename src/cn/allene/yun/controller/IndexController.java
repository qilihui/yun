package cn.allene.yun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.allene.yun.pojo.User;
import cn.allene.yun.service.FileService;
import cn.allene.yun.service.UserService;
import cn.allene.yun.utils.UserUtils;

@Controller
public class IndexController {
	@Autowired
	private UserService userService;
	/**
	 * 主页面页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		String username = UserUtils.getUsername(request);
		User user = userService.findUser(username);
		//String countSize = userService.getCountSize(username);
		request.setAttribute("countSize", user.getCountSize());
		request.setAttribute("totalSize", user.getTotalSize());
		return "index";
	}
}
