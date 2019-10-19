package com.weather.Temper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataServices {

	@Autowired
	private detailRepository repo;

	public void addRecord(String location, String responseDetail) {
		try {
			repo.insertdetails(location, responseDetail);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
