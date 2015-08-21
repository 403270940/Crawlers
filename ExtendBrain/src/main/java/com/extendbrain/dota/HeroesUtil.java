package com.extendbrain.dota;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.extendbrain.dota.entity.Hero;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HeroesUtil {
	public static HashMap<Integer, String> heroMap;
	private static String heroesFileName = "src/main/java/conf/Heroes.xml";
	
	static{
		heroMap = new HashMap<Integer, String>();
		List<Hero> heroList = getHerosFormFile();
		if(heroList == null)updateHeroes();
		heroList = getHerosFormFile();
		for(Hero hero : heroList){
			heroMap.put(hero.getId(), hero.getLocalized_name());
		}
	}
	public static String getName(int id){
		return heroMap.get(id);
	}
	public static List<Hero> getHerosFormFile(){
		List<Hero> heroList = new ArrayList<Hero>();
		try {
			FileInputStream fis = new FileInputStream(heroesFileName);
			Document doc = Jsoup.parse(fis,"utf-8","",Parser.xmlParser());
			Element heroesElement = doc.select("heroes").first();
			Elements heroElements = heroesElement.select("hero");
			for(Element element : heroElements){
				int id = Integer.valueOf(element.select("id").first().text());
				String name = element.select("name").first().text();
				String localizedName = element.select("localized_name").first().text();
				Hero hero = new Hero(id,name,localizedName);
				heroList.add(hero);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return heroList;
		
	}
	
	public static List<Hero> getHeroesFromServer(){
		List<Hero> heroList = new ArrayList<Hero>();
		String result = Steam.getHeroes();
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonObject resultObject = jsonObject.getAsJsonObject("result");
		JsonArray heroArray = resultObject.getAsJsonArray("heroes");
		for(int i = 0; i < heroArray.size(); i ++){
			JsonObject heroObject = (JsonObject)heroArray.get(i);
			Hero hero = gson.fromJson(jsonObject, Hero.class);
			heroList.add(hero);
		}
		return heroList;
	}
	
	public static void saveListToXML(List<Hero> heroList){
		Document doc = new Document("heroes");
		Element element = doc.appendElement("heroes");
		
		for(Hero hero : heroList){
			Element heroElement = element.appendElement("hero");
			Element idElement = heroElement.appendElement("id");
			idElement.appendText(hero.getId()+"");
			Element nameElement = heroElement.appendElement("name");
			nameElement.appendText(hero.getName());
			Element localizedNameElement = heroElement.appendElement("localized_name");
			localizedNameElement.appendText(hero.getLocalized_name());
		}
		try {
			FileOutputStream fos = new FileOutputStream(heroesFileName);
			fos.write(doc.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateHeroes(){
		List<Hero> heroesList = getHeroesFromServer();
		saveListToXML(heroesList);
		
	}
	public static void main(String[] args) {
		updateHeroes();
	}
}
