package com.weather.Temper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TempControler {

	@Autowired
	private ConstantComponent constant;
	
	@Autowired
	private DataServices dataService;
	
	@RequestMapping("/")
	public String welcome(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "loc")
	public ModelAndView getAllInfo(@RequestParam(value = "location") String location) {
		
		ModelAndView mav = new ModelAndView();
	
		try {
			
			String api = constant.getApi();
			String key = constant.getKey();

			String url = api + "APPID=" + key + "&q=" + location;
            System.out.println(url);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, HttpMethod.POST, JsonNode.class);
			String responseDetail = responseEntity.getBody().toString();
			

			// Implementation test for JSON
			ObjectMapper mapper = new ObjectMapper();
			JsonNode weather = mapper.readValue(responseEntity.getBody().toString(), JsonNode.class);
			String mausam = weather.get("base").asText();
			System.out.println("Weather: "+weather);
			System.out.println("Mausam: "+mausam);
			//Test end
			
			
			// Enter the record in database:
			dataService.addRecord(location,responseDetail.substring(0, 200));
			
			// Render to view:
			mav.setViewName("res");
			mav.addObject("result", responseDetail);
			return mav;

		} catch (Exception e) {
			mav.setViewName("res");
			mav.addObject("result","City not Found, " + e.getMessage());
		}
		return mav;
	}
}
