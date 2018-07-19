package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReportGenerator {
	
	private List jsonReader() throws IOException, ParseException{
		File f = new File("input/CV-V1-API Latency.json");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		List<JSONObject> reportJSONObjList = new ArrayList<JSONObject>();
		while((line = br.readLine())!=null){
			//System.out.println(line);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(line);
			reportJSONObjList.add(obj);
			//obj.keySet().forEach(action);
			
		}
		
		return reportJSONObjList;
	}
	
	
	private List createReportList(List<JSONObject> jsonObjList) throws Exception{
		
		List<Report> reportList = new ArrayList<Report>();
		
		//Setting Header
		Report reportHeader = setJSONtoObjValue(jsonObjList.get(0),true);
		
		//Setting value
		jsonObjList.forEach(jsonObj -> {
			Report report = null;
			try {
				report = setJSONtoObjValue(jsonObj,false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reportList.add(report);
		});
		
		
		for (Report report : reportList) {
			
			
		}
		
		
		return null;
	}
	

	

	private Report setJSONtoObjValue(JSONObject jsonReportObj, boolean isHeader) throws Exception{
		
		int counter = 1;
		Report report = new Report();
		for (Object key : jsonReportObj.keySet()) {
			try {
				if (isHeader){
					 Report.class.getMethod("setFieldHeader"+counter,String.class).invoke(report,key);
				}else{
					 Report.class.getMethod("setField"+counter,String.class).invoke(report,jsonReportObj.get(key));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter = counter + 1;
		}
		return report;
		
	}
	
	
	public void trigger() throws Exception{
		List jsonObjList = jsonReader();
		List reportList = createReportList(jsonObjList);
	}
	
	public static void main(String[] args) throws Exception {
		ReportGenerator rg = new ReportGenerator();
		rg.trigger();
	}

}
