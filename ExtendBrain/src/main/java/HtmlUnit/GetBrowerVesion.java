package HtmlUnit;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class GetBrowerVesion {
	
	
	public static List<BrowserVersion> getBrowserVersionsList(){
		List<BrowserVersion> bversionList = new ArrayList<BrowserVersion>();
		bversionList.add(BrowserVersion.CHROME);
		bversionList.add(BrowserVersion.FIREFOX_38);
		bversionList.add(BrowserVersion.INTERNET_EXPLORER_8);
		return bversionList;
	}
	
	
	public static BrowserVersion getRandomVersion(){
		List<BrowserVersion> bversionList = getBrowserVersionsList();
		int i = RandomUtils.nextInt(0, 2);
		return bversionList.get(i);
	}
}
