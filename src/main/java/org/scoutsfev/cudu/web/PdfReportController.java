package org.scoutsfev.cudu.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class PdfReportController extends AbstractController{

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String output =
			ServletRequestUtils.getStringParameter(request, "output");
		
		//dummy data
		Map<String,String> revenueData = new HashMap<String,String>();
		revenueData.put("1/20/2010", "$101,000");
		revenueData.put("1/21/2010", "$201,000");
		revenueData.put("1/22/2010", "$301,000");
		revenueData.put("1/23/2010", "$401,000");
		revenueData.put("1/24/2010", "$501,000");
		
		if(output ==null || "".equals(output)){
			//return normal view
			return new ModelAndView("RevenueSummary","revenueData",revenueData);
			
		}else if("PDF".equals(output.toUpperCase())){
			//return excel view
			return new ModelAndView("PdfRevenueSummary","revenueData",revenueData);
			
		}else{
			//return normal view
			return new ModelAndView("RevenueSummary","revenueData",revenueData);
			
		}
		
	}
	
}