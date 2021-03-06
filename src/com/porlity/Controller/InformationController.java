package com.porlity.Controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.porlity.Service.InformationService;
import com.porlity.entity.information;

@Controller
public class InformationController {
	 @EJB(mappedName = "ejb:/Portlity//InformationServiceBean!com.porlity.Service.InformationService")
	 InformationService informationSer;

	 @RequestMapping("/studentHomepage")
	 public ModelAndView StudentHome(HttpServletRequest request){
		 ModelAndView mv = new ModelAndView("studentHomepage.jsp");
		 List<information> listinformation;
		 try{
			 listinformation = informationSer.getAll();
			 mv.addObject("listInformation",listinformation);
		 }catch (Exception e) {
			// TODO: handle exception
		}
		 return mv;
	 }
	 @RequestMapping("/saveInformation")
	 @ResponseBody
	 public String saveInformation(@ModelAttribute("information") information information, BindingResult result,
				HttpServletRequest request) throws ParseException,UnsupportedEncodingException {
		 request.setCharacterEncoding("UTF-8");
		 String htmlBody = request.getParameter("htmlValue");
		 String InformationId = String.valueOf(information.getInformationId());
		 
		 try{
			 if(InformationId != null){
				 information.setBody(htmlBody);
				 informationSer.update(information);
				 System.out.println("update Body");
				 System.out.println(htmlBody);
				 return "success";
			 }else{
				 information.setBody(htmlBody);
				 informationSer.insert(information);
				 return "success";
			 }
		 }catch (Exception e) {
			// TODO: handle exception
		}
					return null;	
		 
	 }
	 
	 @RequestMapping("/adminListinformation")
	 public ModelAndView listInformation(HttpServletRequest request){
		 ModelAndView mv = new ModelAndView("adminList.jsp");
		 List<information> listinformation;
		 try{
			 listinformation = informationSer.getAll();
			 mv.addObject("adminListinformation",listinformation);
		 }catch (Exception e) {
			// TODO: handle exception
		}
		 return mv;
	 }
	 
	 @RequestMapping("/deleteInformation")
	 public String delete(HttpServletRequest request){
		 informationSer.delete(Long.valueOf(request.getParameter("id")));
		 return "redirect:adminListinformation.do";
	 }
	 
}
