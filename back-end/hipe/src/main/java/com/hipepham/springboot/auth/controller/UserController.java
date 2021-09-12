package com.hipepham.springboot.auth.controller;

import com.hipepham.springboot.auth.JwtTokenProvider;
import com.hipepham.springboot.auth.constants.AuthMessageConstant;
import com.hipepham.springboot.auth.form.UserForm;
import com.hipepham.springboot.auth.service.SecurityService;
import com.hipepham.springboot.auth.service.UserDetailsService;
import com.hipepham.springboot.auth.service.UserService;
import com.hipepham.springboot.common.base.controller.BaseController;
import com.hipepham.springboot.common.response.utils.ResponseUtils;
import com.hipepham.springboot.common.response.vo.Message;
import com.hipepham.springboot.common.response.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@Log4j2
@RequestMapping("/authorization")
public class UserController extends BaseController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsService userDetailsService;

    @GetMapping("/registration")
    public ResponseEntity registration(@RequestParam String username,
                                       @RequestParam String password) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<Message> messages = new ArrayList<>();
        try {
            UserForm userForm =new UserForm(username,password);
//            securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

//			Map<String, Object> mapResult = new HashMap<>();
//			mapResult.put("token", createToken(userForm));
//			mapResult.put("username", userForm.getUsername());
//			mapResult.put("password", userForm.getPassword());

//			responseMessage.setData(mapResult);
////
//            Message message = new Message(Constants.ERROR,
//                    super.getMessageUtils().getMessage("auth.invalid-crowd-token"));
//            messages.add(message);
			responseMessage.setMessage(messages);
			return ResponseUtils.buildResponseMessage(true, responseMessage);
		} catch (Exception e) {
			return ResponseUtils.buildResponseMessage(false, responseMessage, HttpStatus.BAD_REQUEST);
		}
	}

//    @GetMapping("/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error", "Your username and password is invalid.");
//
//        if (logout != null)
//            model.addAttribute("message", "You have been logged out successfully.");
//
//        return "login";
//    }

	@PostMapping("/login")
	public ResponseEntity authenticateUser(@RequestBody UserForm loginRequest) {
		ResponseMessage responseMessage = new ResponseMessage();
		List<Message> messages = new ArrayList<>();

		try {
			// Xác thực từ username và password.
//			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//					loginRequest.getUsername(), loginRequest.getPassword());
//			Authentication authentication = authenticationManager.authenticate(token);
//
//			// Nếu không xảy ra exception tức là thông tin hợp lệ
//			// Set thông tin authentication vào Security Context
//			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userdetail = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			if(!loginRequest.getPassword().equals(userdetail.getPassword())) {
				throw new Exception("Invalid password!");
			}
			// Trả về jwt cho người dùng.
			String jwt = tokenProvider.generateToken(userdetail);
			Map<String, Object> mapResult = new HashMap<>();
			mapResult.put("token", jwt);
			mapResult.put("username", loginRequest.getUsername());
//			mapResult.put("password", loginRequest.getPassword());
			Message message = new Message();
			message.setCode(AuthMessageConstant.AUTH_MESSAGE_SUCCESS);
			message.setMsg("Login success!");
			messages.add(message);

			responseMessage.setMessage(messages);
			responseMessage.setData(mapResult);
			return ResponseUtils.buildResponseMessage(true, responseMessage);
		} catch (Exception e) {
			Message message = new Message();
			message.setCode(AuthMessageConstant.AUTH_MESSAGE_FAIL);
			message.setMsg("Login fail. Invalid username or password!");
			messages.add(message);

			responseMessage.setMessage(messages);
			responseMessage.setData(null);
			return ResponseUtils.buildResponseMessage(false, responseMessage, HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping("/create")
//	public ResponseEntity create(@RequestBody UserCreateForm form) {
//		ResponseMessage responseMessage = new ResponseMessage();
//		try{
//			userService.create(form, "hieppv");
//			responseMessage.setSuccess(true);
//		} catch (Exception e) {
//			log.error(e);
//			responseMessage.setSuccess(false);
//			return ResponseUtils.buildResponseMessage(false, responseMessage);
//		}
//		return ResponseUtils.buildResponseMessage(true, responseMessage);
//	}



}
