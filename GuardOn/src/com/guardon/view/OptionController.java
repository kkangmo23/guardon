package com.guardon.view;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.option.OptionService;
import com.guardon.option.domain.Option;

@Controller
@RequestMapping("/*")
@SessionAttributes("option")
public class OptionController {

	@Inject
	@Named("optionService")
	OptionService optionService;

	@RequestMapping("option.do")
	public String option(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String loginFailedCount = null, otpTimeLimit = null, pwdLength = null, pwdComplexity = null, autoLogout = null, approvedTimeLimit = null;

		try {
			// autoLogout=String.valueOf(optionService.getAutoLogout());
			loginFailedCount = String.valueOf(optionService
					.getLoginFailedCount());
			otpTimeLimit = String.valueOf(optionService.getOtpTimeLimit());
			pwdLength = String.valueOf(optionService.getPwdLength());
			pwdComplexity = optionService.getPwdComplexity();
			approvedTimeLimit = String.valueOf(optionService
					.getApprovedTimeLimit());

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "설정 정보를 불러오는데 실패했습니다.");
			return "errorPage";
		}
		// request.setAttribute("autoLogout", autoLogout);
		request.setAttribute("loginFailedCount", loginFailedCount);
		request.setAttribute("otpTimeLimit", otpTimeLimit);
		request.setAttribute("pwdLength", pwdLength);
		request.setAttribute("pwdComplexity", pwdComplexity);
		request.setAttribute("approvedTimeLimit", approvedTimeLimit);

		return "option";

	}

	@RequestMapping("updateOption.do")
	public String updateOption(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		String pwdComplexity;
		int pwdLength, otpTimeLimit, loginFailedCount, approvedTimeLimit;
		Option option = new Option();

		try {
			pwdLength = Integer.parseInt(request.getParameter("pwdLength"));
			otpTimeLimit = Integer.parseInt(request
					.getParameter("otpTimeLimit"));
			// loginFailedCount =
			// Integer.parseInt(request.getParameter("loginFailedCount"));
			pwdComplexity = request.getParameter("pwdComplexity");
			approvedTimeLimit = Integer.parseInt(request
					.getParameter("approvedTimeLimit"));

			option.setPwdLength(pwdLength);
			option.setOtpTimeLimit(otpTimeLimit);
			// option.setLoginFailedCount(loginFailedCount);
			option.setPwdComplexity(pwdComplexity);
			option.setApprovedTimeLimit(approvedTimeLimit);

			optionService.updateOption(option);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "설정 수정에 실패하였습니다.");
			return "adminInterPage";
		}
		request.setAttribute("message", "성공적으로 설정을 수정하였습니다.");
		return "adminInterPage";
	}

}
