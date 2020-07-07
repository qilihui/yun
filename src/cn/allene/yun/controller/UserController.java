package cn.allene.yun.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.allene.yun.pojo.User;
import cn.allene.yun.service.FileService;
import cn.allene.yun.service.UserService;
import cn.allene.yun.utils.GithubUtils;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, User user) {
		User exsitUser = userService.findUser(user);
		if (exsitUser != null) {
			HttpSession session = request.getSession();
			//这段代码放到你的登录请求中，获取用户输入的校验码并进行比较
			String verifyCode = request.getParameter("verifyCode").toLowerCase();
			String sessionVerifyCode = (String) session.getAttribute("verifyCodeValue");
			if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
				request.setAttribute("msg", "验证码错误");
				System.out.println("验证码错误");
				return "login";
			}
			session.setAttribute(User.NAMESPACE, exsitUser.getUsername());
			session.setAttribute("totalSize", exsitUser.getTotalSize());
			return "redirect:/index.action";
		} else {
			request.setAttribute("msg", "用户名或密码错误");
			return "login";
		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/regist",method=RequestMethod.GET)
	public String toRegist(){
		return "regist";
	}
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public String regist(HttpServletRequest request, User user){
		HttpSession session = request.getSession();
		//获取用户输入的校验码并进行比较
		String verifyCode = request.getParameter("verifyCode").toLowerCase();
		String sessionVerifyCode = (String) session.getAttribute("verifyCodeValue");
		if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
			request.setAttribute("msg", "验证码错误");
			System.out.println("验证码错误");
			return "regist";
		}
		if(user.getUsername() == null || user.getPassword() == null || user.getUsername() == ""
				|| user.getPassword() == ""){
			return "regist";
		} else {
			boolean isSuccess = userService.addUser(user);
			if (isSuccess) {
				fileService.addNewNameSpace(request, user.getUsername());
				return "login";
			} else {
				request.setAttribute("msg", "注册失败");
				return "regist";
			}
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/user/login.action";
	}

	/**
	 * 登录-移动端
	 * 
	 * @param req
	 * @param rep
	 * @throws Exception
	 */
	@RequestMapping("/loginForApp")
	public void getjson(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		System.out.println("安卓端访问中..............");

		PrintWriter writer = rep.getWriter();
		JSONObject object = new JSONObject();
		User exsitUser = userService.findUser(user);
		if (exsitUser != null) {
			HttpSession session = req.getSession();
			session.setAttribute(User.NAMESPACE, exsitUser.getUsername());
			session.setAttribute("totalSize", exsitUser.getTotalSize());
			// object.put("result", exsitUser);
			object.put("ret", "1000");
			object.put("msg", "登录成功");
			object.put("data", exsitUser);
		} else {
			// object.put("result", "fail");
			object.put("ret", "1001");
			object.put("msg", "登录失败");
		}
		writer.println(object.toString());
		writer.flush();
		writer.close();
	}

	/**
	 * 注册-移动端
	 * 
	 * @param req
	 * @param rep
	 * @throws Exception
	 */
	@RequestMapping("/registForApp")
	public void registForApp(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("安卓端注册中..............");
		PrintWriter writer = rep.getWriter();
		JSONObject object = new JSONObject();

		if (username == null || password == null) {
			// object.put("result", "error");//填写有误
			object.put("ret", "1003");
			object.put("msg", "填写有误");
		} else {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			User isRegUser = userService.findUser(username);
			if (isRegUser == null) {
				boolean isSuccess = userService.addUser(user);
				if (isSuccess) {
					// 根据名字创建文件目录
					fileService.addNewNameSpace(req, user.getUsername());
					user.setPassword(password);
					User exsitUser = userService.findUser(user);
					HttpSession session = req.getSession();
					session.setAttribute(User.NAMESPACE, exsitUser.getUsername());//
					session.setAttribute("totalSize", exsitUser.getTotalSize());
					// object.put("result", exsitUser);
					object.put("ret", "1000");
					object.put("msg", "注册成功");
					object.put("data", exsitUser);
				} else {
					// object.put("result", "fail");//注册失败
					object.put("ret", "1001");
					object.put("msg", "注册失败");
				}
			} else {
				// object.put("result", "isExist");//已存在该用户
				object.put("ret", "1002");
				object.put("msg", "用户已存在");
			}
		}
		writer.println(object.toString());
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "/authorization_code", method = RequestMethod.GET)
	public String authorization_code(String code, Model model, HttpServletRequest request) {
		System.out.println(code);
		Map json = null;
		try {
			json = GithubUtils.getJson(code);
		} catch (Exception e) {
			model.addAttribute("err", e.getMessage());
			return "error";
		}
		String name = "githubUser_" + (String) json.get("login");
		Integer githubId = Integer.valueOf(json.get("id").toString());
		User exsitUser = userService.findUser(name, githubId);
		if (exsitUser == null) {
			exsitUser = new User();
			exsitUser.setUsername(name);
			exsitUser.setPassword(name);
			exsitUser.setGithubId(githubId);
			boolean isSuccess = userService.addUser(exsitUser);
			if (isSuccess) {
				fileService.addNewNameSpace(request, exsitUser.getUsername());
			} else {
				model.addAttribute("err", "注册失败");
				return "error";
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute(User.NAMESPACE, exsitUser.getUsername());
		session.setAttribute("totalSize", exsitUser.getTotalSize());
		return "redirect:/index.action";
	}
	
	/* 获取校验码 */
    @RequestMapping("/getVerifyCode")
    public void generate(HttpServletResponse response, HttpSession session) {
    	System.out.println("验证码");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String verifyCodeValue = drawImg(output);
        // 将校验码保存到session中
        session.setAttribute("verifyCodeValue", verifyCodeValue);
        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /* 绘制验证码 */
    private String drawImg(ByteArrayOutputStream output) {
        String code = "";
        // 随机产生4个字符
        for (int i = 0; i < 4; i++) {
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        // 调用Graphics2D绘画验证码
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code.toLowerCase();
    }

    /* 获取随机参数 */
    private char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
    
    @RequestMapping("/person")
    public String person(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession();
    	String username = (String) session.getAttribute(User.NAMESPACE);
//		String countSize = userService.getCountSize(username);
//		request.setAttribute("countSize", countSize);
    	User user = userService.findUser(username);
    	model.addAttribute("user", user);
    	return "person";
    }
    
    @RequestMapping("/updateUser")
    public String updateUser(User user) {
    	userService.updateUser(user);
    	return "index";
    }
    
    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request) {
       	HttpSession session = request.getSession();
    	String username = (String) session.getAttribute(User.NAMESPACE);
    	userService.deleteUser(username);
    	return "login";
    }
    
    @RequestMapping("upperUser")
    public String upperUser(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	String username = (String) session.getAttribute(User.NAMESPACE);
    	userService.upperUser(username);
    	return "redirect:/index.action";
    }
}
