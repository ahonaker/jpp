package net.derbyparty.jpp.loader;


import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.text.WordUtils;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.derbyparty.jpp.factors.Factors;
import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.EquipmentChangeType;
import net.derbyparty.jpp.object.Entry;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.Jockey;
import net.derbyparty.jpp.object.MedicationType;
import net.derbyparty.jpp.object.MultiRaceWager;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.SurfaceType;
import net.derbyparty.jpp.object.Trainer;
import net.derbyparty.jpp.object.Stat;
import net.derbyparty.jpp.object.Workout;
import net.derbyparty.jpp.object.WorkoutTrackType;
import net.derbyparty.jpp.object.RaceType;
import net.derbyparty.jpp.object.SexRestrictionType;

public class Loader {
	
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	static ChromeDriver driver;
	
	static String mainHandle;
	
	public static boolean defaultOddsProvider;
	public static boolean initializing = false;
	
	static String user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36";
	
	static ChromeOptions options = new ChromeOptions()
		//.addArguments("--headless=new")
		.addArguments("--no-sandbox")
		.addArguments("--window-size=1920,1080")
		.addArguments("--disable-dev-shm-usage")
		.addArguments("--remote-allow-origins=*")
		.addArguments("user-agent=" + user_agent);	
		
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);

	
	public static String externalGet(String urlString) throws Exception {

		String response;

		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
			response = sb.toString();

			conn.disconnect();

		} catch (UnknownHostException e) {
			return "";
		} catch (Exception e) {
			throw e;
		}
	
		return response;
	}
	
	public static ObjectNode getDRF (String track_code, Date date) throws Exception  {
		
		ObjectNode node;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy");
			
			String url = "http://pro.drf.com/entries/entryDetails/id/" + 
				track_code + 
				"/country/USA/date/" +
				sdf.format(date);
			//System.out.println(url);
			node = mapper.readValue(externalGet(url), ObjectNode.class);
			
		} catch (Exception e) {
			throw e;
		}
		
		return node;
		
	}
	
	public static ObjectNode getDRFResults (String track_code, Date date) throws Exception  {
		
		ObjectNode node;
		try {			
			SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy");
			
			String url = "http://pro.drf.com/results/resultDetails/id/" + 
					track_code + 
					"/country/USA/date/" +
				sdf.format(date);
			//System.out.println(url);
			node = mapper.readValue(externalGet(url), ObjectNode.class);
			
		} catch (Exception e) {
			throw e;
		}
		
		return node;
		
	}
	
    public static MultiRaceWager matchWager (String wager, int index, int raceNum) throws Exception {
    	MultiRaceWager mrw = null;
    	try {
    		Pattern pattern = Pattern.compile("(\\$?\\.?\\d+)?(\\D+)?(\\$+\\.?\\d+)?\\s?(PICK|BET|JERSEY SHORE|ULTIMATE|RAINBOW|GRAND|SINGLE|DERBY CITY)\\s(6 JACKPOT|\\d|THREE|FOUR|FIVE|SIX|SLAM)\\s?(\\((\\$?\\.?\\d+)(\\sMIN|\sMINIMUM)?\\))?(\\((RACES )?(\\d+(-\\d+)+))?");
            Matcher matcher = pattern.matcher(wager);
            if (matcher.find() && !wager.matches(".*LEG\\s(2|3|4|5|6).*")) {
            	
            	String min = (matcher.group(1) != null) ? matcher.group(1) : ((matcher.group(7) != null) ? matcher.group(7) : ".50") ;
            	if (matcher.group(2) != null && matcher.group(2).contains("CENT")) min = "." + min;
            	
            	Integer numRaces = 0;
            	switch (matcher.group(5)) {
	            	case "THREE" : numRaces = 3; break;
	            	case "FOUR" : numRaces = 4; break;
	            	case "FIVE" : numRaces = 5; break;
	            	case "SIX" : numRaces = 6; break;
	            	case "SLAM" : numRaces = 4; break;
	            	case "6 JACKPOT" : numRaces = 6; break;
	            	default: numRaces = Integer.parseInt(matcher.group(5));
            	}
            	
            	String races = "";
            	if (matcher.group(11) == null || matcher.group(11).split("-").length != numRaces) {
            		for (int i = raceNum; i < raceNum + numRaces; i++) {
            			if (races != "") races += "-";
            			races += i;
            		}
            	} else {
            		races = matcher.group(11);
            	}
            	
            	mrw = MultiRaceWager.builder()
            		.withName(
            			((matcher.group(1) != null) ? matcher.group(1) : "") + " " + 
            			((matcher.group(2) != null) ? matcher.group(2) : "") + " " + 
            			matcher.group(4) + " " + 
            			matcher.group(5))
            		.withIndex(index)
            		.withMin(Float.parseFloat(min.replaceAll("\\$", "")))
            		.withFirstRace(matcher.group(11) == null ? raceNum : Integer.parseInt(matcher.group(11).split("-")[0]))
            		.withNumRaces(numRaces)
            		.withRaces(races)
            		.build();
            }
    		  		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(wager);
		}
		return mrw;
    	
    }
	
	public static List<MultiRaceWager> buildMultiRaceWagers (String[] values, Integer raceNum) throws Exception {	
				
		List<MultiRaceWager> multiRaceWagers = new ArrayList<MultiRaceWager>();
		
		try {
    		Integer index = 0;
    		
    		String allStrings = "";
    		String rep = (values[239].contains(";") || values[239].contains("/")) ? "/" : "";
    		
    		for (int i = 239; i <= 243; i++) {
    			Pattern pCode = Pattern.compile("\\d+(-\\d+)+|\\d+(;\\s?\\d+)+");
	    		Matcher mCode = pCode.matcher(values[i]);
	    		StringBuffer sb = new StringBuffer();
	    		while (mCode.find()) {
	    		    mCode.appendReplacement(sb,  mCode.group().replace("-", "#").replace("; ", "#").replace(";", "#"));
	    		}
	    		mCode.appendTail(sb);
	    		allStrings += sb + rep;
    		}
    		
    		allStrings = allStrings
    			.replaceAll("\\/MARE", " & MARE")	
    			.replaceAll("\\/BELMONT", " & BELMONT")
    			.replaceAll("\\/MET", " & MET")
    			.replaceAll("\\/GOLD", " & GOLD")
    			.replaceAll("\\/CUP", " & CUP")
    			.replaceAll("\\/OLD", " & OLD")
    			.replaceAll("\\/DERBY", " & DERBY")
    			.replaceAll("\\/OAKS", " & OAKS")
    			.replaceAll("\\/CLASSIC", " & CLASSIC")        			    			
    			.replaceAll("; FRI", " & FRI")
    			.replaceAll("; SAT", " & SAT")
    			.replaceAll("; SUN", " & SUN")
    			.replaceAll("((HI)|(HIGH))(\\/|-)5", "HI5")
    			.replaceAll("(\\/|-)6", " 6")
    			.replaceAll("BLACK-EYED SUSAN-PREAKNESS", "BLACK EYED SUSAN PREAKNESS")	
    			.replaceAll("0 \\/ PICK", "0 PICK")
    			.replaceAll("-DAY", " DAY")
    			.replaceAll("-CENT", "CENT")
    			.replaceAll("; ", "/").replaceAll(";", "/")
    			.replaceAll("-", "/");
    		
    		for (String wager : allStrings.split("/")) {
    			MultiRaceWager mrw = matchWager(wager.replaceAll("#", "-"),index,raceNum);
    			if (mrw != null) {
    				multiRaceWagers.add(mrw);
        			index++;
    			}
            }
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return multiRaceWagers;
	}
	
	public static List<Race> get (String filename) throws Exception {
		
		List<Race> races = new ArrayList<Race>();
		
		Map<String, SurfaceType> surfaceTypeMap = new HashMap<String, SurfaceType>();
		surfaceTypeMap.put("D", SurfaceType.DIRT);
		surfaceTypeMap.put("T", SurfaceType.TURF);
		surfaceTypeMap.put("d", SurfaceType.INNER_DIRT);
		surfaceTypeMap.put("t", SurfaceType.INNER_TURF);
		surfaceTypeMap.put("s", SurfaceType.STEEPLECHASE);
		surfaceTypeMap.put("h", SurfaceType.HUNT);
		
		Map<String, RaceType> raceTypeMap = new HashMap<String, RaceType>();
		raceTypeMap.put("G1", RaceType.GRADE_1);
		raceTypeMap.put("G2", RaceType.GRADE_2);
		raceTypeMap.put("G3", RaceType.GRADE_3);
		raceTypeMap.put("N", RaceType.NON_GRADED);
		raceTypeMap.put("A", RaceType.ALLOWANCE);
		raceTypeMap.put("R", RaceType.STARTER_ALLOWANCE);
		raceTypeMap.put("T", RaceType.STARTER_HANDICAP);
		raceTypeMap.put("C", RaceType.CLAIMING);
		raceTypeMap.put("CO", RaceType.OPTIONAL_CLAIMING);
		raceTypeMap.put("S", RaceType.MAIDEN_SPECIAL_WEIGHT);
		raceTypeMap.put("M", RaceType.MAIDEN_CLAIMING);
		raceTypeMap.put("AO", RaceType.ALLOWANCE_OPTIONAL_CLAIMING);
		raceTypeMap.put("MO", RaceType.MAIDEN_OPTIONAL_CLAIMING);
		raceTypeMap.put("NO", RaceType.OPTIONAL_CLAIMING_STAKES);		
		
		Map<String, AgeRestrictionType> ageRestrictionTypeMap = new HashMap<String, AgeRestrictionType>();
		ageRestrictionTypeMap.put("A", AgeRestrictionType.TWO_YEAR_OLDS);
		ageRestrictionTypeMap.put("B", AgeRestrictionType.THREE_YEAR_OLDS);
		ageRestrictionTypeMap.put("C", AgeRestrictionType.FOUR_YEAR_OLDS);
		ageRestrictionTypeMap.put("D", AgeRestrictionType.FIVE_YEAR_OLDS);
		ageRestrictionTypeMap.put("E", AgeRestrictionType.THREE_AND_FOUR_YEAR_OLDS);
		ageRestrictionTypeMap.put("F", AgeRestrictionType.FOUR_AND_FIVE_YEAR_OLDS);
		ageRestrictionTypeMap.put("G", AgeRestrictionType.THREE_FOUR_AND_FIVE_YEAR_OLDS);
		ageRestrictionTypeMap.put("H", AgeRestrictionType.ALL_AGES);
		
		Map<String, AgeRestrictionRangeType> ageRestrictionRangeTypeMap = new HashMap<String, AgeRestrictionRangeType>();
		ageRestrictionRangeTypeMap.put("O", AgeRestrictionRangeType.THAT_AGE_ONLY);
		ageRestrictionRangeTypeMap.put("U", AgeRestrictionRangeType.THAT_AGE_AND_UP);
		
		Map<String, SexRestrictionType> sexRestrictionTypeMap = new HashMap<String, SexRestrictionType>();
		sexRestrictionTypeMap.put("N", SexRestrictionType.NO_SEX_RESTRICTIONS);
		sexRestrictionTypeMap.put("M", SexRestrictionType.FILLIES_AND_MARES);
		sexRestrictionTypeMap.put("C", SexRestrictionType.COLTS_AND_GELDINGS);
		sexRestrictionTypeMap.put("F", SexRestrictionType.FILLIES);
		
		Map<Integer, MedicationType> medicationTypeMap = new HashMap<Integer, MedicationType>();
		medicationTypeMap.put(0, MedicationType.NONE);
		medicationTypeMap.put(1, MedicationType.LASIX);
		medicationTypeMap.put(2, MedicationType.BUTE);
		medicationTypeMap.put(3, MedicationType.BUTE_LASIX);
		medicationTypeMap.put(4, MedicationType.LASIX_FIRST);
		medicationTypeMap.put(5, MedicationType.BUTE_LASIX_FIRST);
		medicationTypeMap.put(9, MedicationType.UNAVAILABLE);
		
		Map<Integer, EquipmentChangeType> equipmentChangeTypeMap = new HashMap<Integer, EquipmentChangeType>();
		equipmentChangeTypeMap.put(0, EquipmentChangeType.NO_CHANGE);
		equipmentChangeTypeMap.put(1, EquipmentChangeType.BLINKERS_ON);
		equipmentChangeTypeMap.put(2, EquipmentChangeType.BLINKERS_OFF);
		equipmentChangeTypeMap.put(9, EquipmentChangeType.UNAVAILABLE);
		
		Map<String, WorkoutTrackType> workoutTrackTypeMap = new HashMap<String, WorkoutTrackType>();
		workoutTrackTypeMap.put("MT", WorkoutTrackType.MAIN_DIRT);
		workoutTrackTypeMap.put("IM", WorkoutTrackType.INNER_DIRT);
		workoutTrackTypeMap.put("TT", WorkoutTrackType.TRAINING_TRACK);
		workoutTrackTypeMap.put("T", WorkoutTrackType.MAIN_TURF);
		workoutTrackTypeMap.put("IT", WorkoutTrackType.INNER_TURF);
		workoutTrackTypeMap.put("WC", WorkoutTrackType.WOOD_CHIP);
		workoutTrackTypeMap.put("HC", WorkoutTrackType.HILLSIDE_COURSE);
		workoutTrackTypeMap.put("IN", WorkoutTrackType.INNER_TURF_TRAINING_TRACK);
		workoutTrackTypeMap.put("TR", WorkoutTrackType.TRAINING_RACE);
		
		Map<String, Integer> twoTurnBreakMap = new HashMap<String, Integer>();
		// 1 3/16 - 6270
		// 1 1/8 - 5940
		// 1 1/16 - 5610
		// 1 70 - 5490
		// 1 - 5280
		// 7 1/2  - 4950
		// 7 - 4620
		twoTurnBreakMap.put("AQU", 5940);
		twoTurnBreakMap.put("BAQ", 5940);		
		twoTurnBreakMap.put("BEL", 6270);
		twoTurnBreakMap.put("CT", 4620);
		twoTurnBreakMap.put("CD", 5610);
		twoTurnBreakMap.put("DED", 4620);
		twoTurnBreakMap.put("GP", 5610);
		twoTurnBreakMap.put("HAW", 5490);
		twoTurnBreakMap.put("KEE", 5610);
		twoTurnBreakMap.put("MNR", 4620);
		twoTurnBreakMap.put("WO", 5490);
		
		Map<String, Integer> twoTurnTurfBreakMap = new HashMap<String, Integer>();
		// 1 3/16 - 6270
		// 1 1/8 - 5940
		// 1 1/16 - 5610
		// 1 70 - 5490
		// 1 - 5280
		// 7 1/2  - 4950
		// 7 - 4620
		twoTurnTurfBreakMap.put("BEL", 5610);
		twoTurnTurfBreakMap.put("CBY", 4950);
		twoTurnTurfBreakMap.put("DEL", 4950);
		twoTurnTurfBreakMap.put("DMR", 4950);
		twoTurnTurfBreakMap.put("FG", 4950);
		twoTurnTurfBreakMap.put("GP", 4950);
		twoTurnTurfBreakMap.put("IND", 4950);
		twoTurnTurfBreakMap.put("LS", 4950);
		twoTurnTurfBreakMap.put("LAD", 4950);
		twoTurnTurfBreakMap.put("MNR", 4950);
		twoTurnTurfBreakMap.put("PRX", 4950);
		twoTurnTurfBreakMap.put("WO", 6270);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		try {
						
			RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
			CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new FileReader(filename))
			                .withCSVParser(rfc4180Parser);
			
			
			int raceNum = 0;
			Race race = Race.builder().build();
			List<Entry> entries = new ArrayList<Entry>();
			
			try (CSVReader csvReader = csvReaderBuilder.build()) {
			    String[] values = null;
			    while ((values = csvReader.readNext()) != null) {
			    	
			    	if (raceNum != Integer.parseInt(values[2].trim())) {

			    		if (raceNum != 0) {
			    			race.setEntries(entries);
			    			races.add(race);
			    			entries = new ArrayList<Entry>();
			    		}
				    				    		
		    		    StringBuilder wagerTypes = new StringBuilder();
		    		    if (!values[239].isBlank()) {
			    		    wagerTypes.append((values[239] + "; " + values[240] + "; " +values[241] + "; " +values[242]
									+ "; " +values[243] + "; " +values[244] + "; " +values[245] + "; " +values[246]
									+ "; " +values[247] + "; " +values[248]).trim());
			    		    while (wagerTypes.length() > 0 && wagerTypes.substring(wagerTypes.length() - 2).equals(" ;")) {
			    		        wagerTypes.setLength(wagerTypes.length() - 2);
			    		    }
				    		wagerTypes.setLength(wagerTypes.length() - 1);
		    		    }
		    		    
		    		    List<MultiRaceWager> multiRaceWagers =  buildMultiRaceWagers(values, Integer.parseInt(values[2].trim()));
			    		
				    	race = Race.builder()
				    			.withTrack(values[0].trim())
				    			.withDate(formatter.parse(values[1].trim()))
				    			.withPostTimes(values[1373].trim())
				    			.withRaceNumber(Integer.parseInt(values[2].trim()))
				    			.withDistance(Integer.parseInt(values[5].trim()))
				    			.withSurface(surfaceTypeMap.get(values[6].trim()))
				    			.withRaceType(raceTypeMap.get(values[8].trim()))
				    			.withAgeRestriction(ageRestrictionTypeMap.get(values[9].trim().substring(0, 1)))
				    			.withAgeRestrictionRange(ageRestrictionRangeTypeMap.get(values[9].trim().substring(1, 2)))
								.withSexRestriction(sexRestrictionTypeMap.get(values[9].trim().substring(2, 3)))
								.withClassification(values[10].trim())
								.withPurse(Integer.parseInt(values[11]))
								.withClaimingPrice(values[12].trim().isBlank() ? 0 : Integer.parseInt(values[12].trim()))
								.withTrackRecord(Float.parseFloat(values[14].trim()))
								.withRaceConditions(values[15].trim())
								.withLasixList(values[16].trim())
								.withButeList(values[17].trim())
								.withCoupledList(values[18].trim())
								.withMutuelList(values[19].trim())
								.withSimulcastTrackCode(values[20].trim())
								.withSimulcastTrackRaceNumber(Integer.parseInt(values[21].trim()))
								.withAllWeatherSurfaceFlag(values[24].trim())
								.withWagerTypes(wagerTypes.toString())
								.withMultiRaceWagers(multiRaceWagers)
								.withParPace2F(values[213].isBlank() ? 0 : Integer.parseInt(values[213].trim()))
								.withParPace4F(values[214].isBlank() ? 0 :Integer.parseInt(values[214].trim()))
								.withParPace6F(values[215].isBlank() ? 0 :Integer.parseInt(values[215].trim()))
								.withParSpeed(values[216].isBlank() ? 0 :Integer.parseInt(values[216].trim()))
								.withParLatePace(values[217].isBlank() ? 0 :Integer.parseInt(values[217].trim()))
								.withOffTheTurfFlag(false)
				    			.build();
				    	if (race.getSurface() == SurfaceType.TURF || race.getSurface() == SurfaceType.INNER_TURF) {
				    		race.setTrackCondition("fm");
				    		race.setTurfFlag(true);
				    	} else {
				    		race.setTrackCondition("ft");
				    		race.setTurfFlag(false);
				    	}
				    	raceNum = race.getRaceNumber();
				    	
			    	} else {
			    		
			    	}
			    	
					MongoCollection<Horse> horsesCollection = database.getCollection("horses", Horse.class);
					//Bson horseQuery = eq("name", values[44].trim().replaceAll("\\s\\(.+\\)", ""));
					
					Pattern regex = Pattern.compile(values[44].trim().replaceAll("\\s\\(.+\\)", ""), Pattern.CASE_INSENSITIVE);
					Bson filter = Filters.eq("name", regex);
					
					Horse horse = horsesCollection.find(filter).first();
			    	    	
			    	List<PastPerformance> pps = new ArrayList<PastPerformance>();
			    	Boolean over90Flagged = false, over365Flagged = false;
			    	
			    	
			    	for (int i = 0; i < 10; i++ ) {
			    		if (!values[255+i].isBlank()) {
			    			PastPerformance pp = PastPerformance.builder()
			    					.withRaceDate(formatter.parse(values[255+i].trim()))
			    					.withDaysSinceLastRace(values[265+i].isEmpty() ? 0 : Integer.parseInt(values[265+i].trim()))
			    					.withTrackCode(values[275+i].trim())
			    					.withBRISTrackCode(values[285+i].trim())
			    					.withRaceNumber(Integer.parseInt(values[295+i].trim()))
			    					.withTrackCondition(values[305+i].trim().toLowerCase())
			    					.withDistance(Integer.parseInt(values[315+i].trim()))
			    					.withOneTurn(Integer.parseInt(values[315+i].trim()) * 3 < 
			    							((surfaceTypeMap.get(values[325+i].trim()) == SurfaceType.TURF) 
			    								? twoTurnTurfBreakMap.getOrDefault(values[275+i].trim(),5280)
			    								: twoTurnBreakMap.getOrDefault(values[275+i].trim(),5280))
			    							)
			    					.withSurface(surfaceTypeMap.get(values[325+i].trim()))
			    					.withOffTheTurfFlag(values[1253+i].trim().equals("X"))
			    					.withAllWeatherSurfaceFlag(values[1402+i].trim())
			    					.withChuteIndicator(values[335+i].trim())
			    					.withNumberOfEntrants(Integer.parseInt(values[345+i].trim()))
			    					.withPostPosition(Integer.parseInt(values[355+i].trim()))
			    					.withEquipment(values[365+i].trim())
			    					.withMedication(medicationTypeMap.get(Integer.parseInt(values[385+i].trim())))
			    					.withTripComment(values[395+i].trim())
			    					.withWinnersName(WordUtils.capitalizeFully(values[405+i].trim()))
			    					.withPlaceName(WordUtils.capitalizeFully(values[415+i].trim()))
			    					.withShowName(WordUtils.capitalizeFully(values[425+i].trim()))
			    					.withWinnersWeight(Integer.parseInt(values[435+i].trim()))
			    					.withPlaceWeight(Integer.parseInt(values[445+i].trim()))
			    					.withShowWeight(Integer.parseInt(values[455+i].trim()))
			    					.withWinnersMargin(values[465+i].isBlank() ? 0 : Float.parseFloat(values[465+i].trim()))
			    					.withPlaceMargin(values[475+i].isBlank() ? 0 : Float.parseFloat(values[475+i].trim()))
			    					.withShowMargin(values[485+i].isBlank() ? 0 : Float.parseFloat(values[485+i].trim()))
			    					.withExtraCommentLine(values[495+i].trim())
			    					.withWeight(Integer.parseInt(values[505+i].trim()))
			    					.withOdds(Float.parseFloat(values[515+i].trim()))
			    					.withEntryFlag(values[525+i].trim())
			    					.withRaceClassification(values[535+i].trim())
			    					.withClaimingPrice(values[545+i].isBlank() ? 0 : Integer.parseInt(values[545+i].trim()))
			    					.withPurse(Integer.parseInt(values[555+i].trim()))
			    					.withStartCallPosition(values[565+i].trim())
			    					.withFirstCallPosition(values[575+i].trim())
			    					.withSecondCallPosition(values[585+i].trim())
			    					.withGateCallPosition(values[595+i].trim())
			    					.withStretchPosition(values[605+i].trim())
			    					.withFinishPosition(values[615+i].trim())
			    					.withMoneyPosition(values[625+i].trim())
			    					.withFirstCallBeatenLengthsLeader(values[655+i].isEmpty() ? 0 : Float.parseFloat(values[655+i].trim()))
			    					.withFirstCallBeatenLengthsOnly(values[665+i].isEmpty() ? 0 : Float.parseFloat(values[665+i].trim()))
			    					.withSecondCallBeatenLengthsLeader(values[675+i].isEmpty() ? 0 : Float.parseFloat(values[675+i].trim()))
			    					.withSecondCallBeatenLengthsOnly(values[685+i].isEmpty() ? 0 : Float.parseFloat(values[685+i].trim()))
			    					.withRaceShapeFirstCall(values[695+i].isBlank() ? 0 : Float.parseFloat(values[695+i].trim()))
			    					.withStretchBeatenLengthsLeader(values[715+i].isEmpty() ? 0 : Float.parseFloat(values[715+i].trim()))
			    					.withStretchBeatenLengthsOnly(values[725+i].isEmpty() ? 0 : Float.parseFloat(values[725+i].trim()))
			    					.withFinishBeatenLengthsLeader(values[735+i].isEmpty() ? 0 : Float.parseFloat(values[735+i].trim()))
			    					.withFinishBeatenLengthsOnly(values[745+i].isEmpty() ? 0 : Float.parseFloat(values[745+i].trim()))
			    					.withRaceShapeSecondCall(values[755+i].isBlank() ? 0 : Float.parseFloat(values[755+i].trim()))
			    					.withPaceFigure2F(values[765+i].isBlank() ? 0 : Integer.parseInt(values[765+i].trim()))
			    					.withPaceFigure4F(values[775+i].isBlank() ? 0 : Integer.parseInt(values[775+i].trim()))
			    					.withPaceFigure6F(values[785+i].isBlank() ? 0 : Integer.parseInt(values[785+i].trim()))
			    					.withPaceFigure8F(values[795+i].isBlank() ? 0 : Integer.parseInt(values[795+i].trim()))
			    					.withPaceFigure10F(values[805+i].isBlank() ? 0 : Integer.parseInt(values[805+i].trim()))
			    					.withPaceFigureLate(values[815+i].isBlank() ? 0 : Integer.parseInt(values[815+i].trim()))
			    					.withRaceRating(values[825+i].isBlank() ? 0 : Float.parseFloat(values[825+i].trim()))	   
			    					.withClassRating(values[835+i].isBlank() ? 0 : Float.parseFloat(values[835+i].trim()))	  
			    					.withBRISSpeedRating(values[845+i].isBlank() ? 0 : Integer.parseInt(values[845+i].trim()))	    					
			    					.withSpeedRating(values[855+i].isBlank() ? 0 : Integer.parseInt(values[855+i].trim()))	    					
			    					.withTrackVariant(values[865+i].isBlank() ? 0 : Integer.parseInt(values[865+i].trim()))	    					
			    					.withFraction2F(values[875+i].isBlank() ? 0 : Float.parseFloat(values[875+i].trim()))
			    					.withFraction4F(values[885+i].isBlank() ? 0 : Float.parseFloat(values[885+i].trim()))
			    					.withFraction6F(values[895+i].isBlank() ? 0 : Float.parseFloat(values[895+i].trim()))
			    					.withFraction8F(values[905+i].isBlank() ? 0 : Float.parseFloat(values[905+i].trim()))
			    					.withFraction10F(values[915+i].isBlank() ? 0 : Float.parseFloat(values[915+i].trim()))
			    					.withFraction12F(values[925+i].isBlank() ? 0 : Float.parseFloat(values[925+i].trim()))
			    					.withFraction14F(values[935+i].isBlank() ? 0 : Float.parseFloat(values[935+i].trim()))
			    					.withFraction16F(values[945+i].isBlank() ? 0 : Float.parseFloat(values[945+i].trim()))
			    					.withFraction1(values[985+i].isBlank() ? 0 : Float.parseFloat(values[985+i].trim()))
			    					.withFraction2(values[995+i].isBlank() ? 0 : Float.parseFloat(values[995+i].trim()))
			    					.withFraction3(values[1005+i].isBlank() ? 0 : Float.parseFloat(values[1005+i].trim()))
			    					.withFinalTime( Float.parseFloat(values[1035+i].trim()))
			    					.withClaimedCode(values[1045+i].trim())
			    					.withTrainer(WordUtils.capitalizeFully(values[1055+i].trim()))
			    					.withJockey(WordUtils.capitalizeFully(values[1065+i].trim()))
			    					.withApprenticeWeightAllowed(values[1075+i].isBlank() ? 0 : Integer.parseInt(values[1075+i].trim()))
			    					.withRaceType(raceTypeMap.get(values[1085+i].trim()))
			    					.withAgeRestriction(ageRestrictionTypeMap.get(values[1095+i].trim().substring(0, 1)))
			    					.withAgeRestrictionRange(ageRestrictionRangeTypeMap.get(values[1095+i].trim().substring(1, 2)))
			    					.withSexRestriction(sexRestrictionTypeMap.get(values[1095+i].trim().substring(2, 3)))
			    					.withStatebredFlag(values[1105+i].trim())
			    					.withRestrictedQualifierFlag(values[1115+i].trim())
			    					.withFavoriteFlag(Integer.parseInt(values[1125+i].trim()))
			    					.withFrontBandagesFlag(Integer.parseInt(values[1135+i].trim()))
			    					.withSpeedPar(values[1166+i].isBlank() ? 0 : Integer.parseInt(values[1166+i].trim()))
			    					.withBarShoeFlag(values[1181+i].trim())
			    					.withCompanyLineCodes(values[1191+i].trim())
			    					.withLowClaimingPriceOfRace(values[1201+i].isBlank() ? 0 : Integer.parseInt(values[1201+i].trim()))
			    					.withHighClaimingPriceOfRace(values[1211+i].isBlank() ? 0 : Integer.parseInt(values[1211+i].trim()))
			    					.withExtendedStartComment(values[1382+i].trim())
			    					.withTrainerChangeDate(values[1267+i].trim())
			    					.withTrainerChangeStarts(values[1277+i].isBlank() ? 0 : Integer.parseInt(values[1277+i].trim()))
			    					.withTrainerChangeWins(values[1287+i].isBlank() ? 0 : Integer.parseInt(values[1287+i].trim()))
			    					.withTrainerChangePlaces(values[1297+i].isBlank() ? 0 : Integer.parseInt(values[1297+i].trim()))
			    					.withTrainerChangeShows(values[1307+i].isBlank() ? 0 : Integer.parseInt(values[1307+i].trim()))
			    					.withTrainerChangeROI(values[1317+i].isBlank() ? 0 : Float.parseFloat(values[1317+i].trim()))
			    					.withIgnore(false)
			    					.withName(WordUtils.capitalizeFully(values[44].trim()))
			    					.withThisRaceNumber(raceNum)
			    					.build();
			    			
			    			pp.addHorse();
			    			pp.addKeyRace();
			    			pp.addSpeedRatings();
			    			
			    			if (!over90Flagged && 
			    				pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
			    					.plusDays(90).isBefore(pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
			    			) {
			    				over90Flagged = true;
			    				pp.setOver90Days(true);
			    			} else {
			    				pp.setOver90Days(false);
			    			}
			    			
			    			if (!over365Flagged && pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
			    					.plusDays(365).isBefore(pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
			    				over365Flagged = true;
			    				pp.setOver365Days(true);
			    			} else {
			    				pp.setOver365Days(false);
			    			}
			    			
					    	if (pp.getSurface() == SurfaceType.TURF || race.getSurface() == SurfaceType.INNER_TURF) {					    		
					    		pp.setTurfFlag(true);
					    	} else {
					    		pp.setTurfFlag(false);
					    	}			
					    	
					    	pp.setCalcFraction1(pp.getFraction1()  == 0 ? 0 : Factors.CalcFraction(pp.getFraction1(), pp.getFirstCallBeatenLengthsOnly()));
					    	pp.setCalcFraction2(pp.getFraction2()  == 0 ? 0 : Factors.CalcFraction(pp.getFraction2(), pp.getSecondCallBeatenLengthsOnly()));
					    	pp.setCalcFraction3(pp.getFraction3()  == 0 ? 0 : Factors.CalcFraction(pp.getFraction3(), pp.getStretchBeatenLengthsOnly()));
					    	pp.setCalcFinalTime(pp.getFinalTime()  == 0 ? 0 : Factors.CalcFraction(pp.getFinalTime(), pp.getFinishBeatenLengthsOnly()));
					    	
					    	pp.setSplit1(pp.getCalcFraction2() - pp.getCalcFraction1());
					    	pp.setSplit2(pp.getFraction3()  == 0 ? 0 : pp.getCalcFraction3() - pp.getCalcFraction2());
					    	pp.setSplit3(pp.getFraction3()  == 0 ? pp.getCalcFinalTime() - pp.getCalcFraction2() : pp.getCalcFinalTime() - pp.getCalcFraction3());
					    	    	
					    	if (horse != null) {
					    		for (int j = 0; j < horse.getRaceNotes().size(); j++) {
					    			if (horse.getRaceNotes().get(j).getTrack().equals(pp.getTrackCode())
					    				&& horse.getRaceNotes().get(j).getRaceDate().equals(pp.getRaceDate())
					    				&& horse.getRaceNotes().get(j).getRaceNumber() == pp.getRaceNumber()) {
					    					pp.setComment(horse.getRaceNotes().get(j).getComment());
					    					pp.setFlag(horse.getRaceNotes().get(j).getFlag());
					    					pp.setFootnote(horse.getRaceNotes().get(j).getFootnote());
					    			}
					    		}
					    	}
					    	pp.addKeyRace();
			    			pps.add(pp);
			    		}
			    		
			    	}
			    	
			    	List<Workout> workouts = new ArrayList<Workout>();
			    	
			    	for (int i = 0; i < 12; i++ ) {
			    		if (!values[101+i].isBlank()) {
			    			Workout workout = Workout.builder()
			    					.withDateOfWorkout(formatter.parse(values[101+i].trim()))
			    					.withTimeOfWorkout(Float.parseFloat(values[113+i].trim()))
			    					.withTrackOfWorkout(values[125+i].trim())
			    					.withDistanceOfWorkout(Integer.parseInt(values[137+i].trim()))
			    					.withTrackCondition(values[149+i].trim())
			    					.withDescription(values[161+i].trim())
			    					.withWorkoutTrackIndicator(values[173+i].isEmpty() ? WorkoutTrackType.UNKNOWN : workoutTrackTypeMap.get(values[173+i].trim()))
			    					.withNumberOfWorks(Integer.parseInt(values[185+i].trim()))
			    					.withRank(Integer.parseInt(values[197+i].trim()))
			    					.build();
			    			workouts.add(workout);
			    		}
			    		
			    	}
			    	
			    	List<Stat> trainerStats = new ArrayList<Stat>();

			    	for (int i = 0; i < 6; i++ ) {
			    		if (!values[1336+i*5].isBlank()) {
				    		Stat stat = Stat.builder()
				    				.withCategory(values[1336+i*5].trim())
				    				.withStarts(Integer.parseInt(values[1337+i*5].trim()))
				    				.withWinPercent(Float.parseFloat(values[1338+i*5]))
				    				.withITMPercent(Float.parseFloat(values[1339+i*5]))
				    				.withROI(Float.parseFloat(values[1340+i*5]))
				    				.build();
				    		trainerStats.add(stat);
			    		}
			    	}   	
			    			    	
			    	Trainer trainer = Trainer.builder()
			    			.withName(WordUtils.capitalizeFully(values[27].trim()))
			    			.withCurrentMeetStarts(values[28].isBlank() ? 0 :Integer.parseInt(values[28].trim()))
			    			.withCurrentMeetWins(values[29].isBlank() ? 0 :Integer.parseInt(values[29].trim()))
			    			.withCurrentMeetPlaces(values[30].isBlank() ? 0 :Integer.parseInt(values[30].trim()))
			    			.withCurrentMeetShows(values[31].isBlank() ? 0 :Integer.parseInt(values[31].trim()))
			    			.withCurrentYearStarts(values[1146].isBlank() ? 0 :Integer.parseInt(values[1146].trim()))
			    			.withCurrentYearWins(values[1147].isBlank() ? 0 :Integer.parseInt(values[1147].trim()))
			    			.withCurrentYearPlaces(values[1148].isBlank() ? 0 :Integer.parseInt(values[1148].trim()))
			    			.withCurrentYearShows(values[1149].isBlank() ? 0 :Integer.parseInt(values[1149].trim()))
			    			.withCurrentYearROI(values[1150].isBlank() ? 0 :Float.parseFloat(values[1150].trim()))
			    			.withPreviousYearStarts(values[1151].isBlank() ? 0 :Integer.parseInt(values[1151].trim()))
			    			.withPreviousYearWins(values[1152].isBlank() ? 0 :Integer.parseInt(values[1152].trim()))
			    			.withPreviousYearPlaces(values[1153].isBlank() ? 0 :Integer.parseInt(values[1153].trim()))
			    			.withPreviousYearShows(values[1154].isBlank() ? 0 :Integer.parseInt(values[1154].trim()))
			    			.withPreviousYearROI(values[1155].isBlank() ? 0 :Float.parseFloat(values[1155].trim()))
			    			.withTrainerStats(trainerStats)
			    			.build();
			    	
			    	List<Stat> jockeyStats = new ArrayList<Stat>();

			    	for (int i = 0; i < 1; i++ ) {
			    		if (!values[1366+i*5].isBlank()) {
				    		Stat stat = Stat.builder()
				    				.withCategory(values[1366+i*5].trim())
				    				.withStarts(values[1367+i*5].isBlank() ? 0 : Integer.parseInt(values[1367+i*5].trim()))
				    				.withWins(values[1368+i*5].isBlank() ? 0 : Integer.parseInt(values[1368+i*5]))
				    				.withPlaces(values[1369+i*5].isBlank() ? 0 : Integer.parseInt(values[1369+i*5]))
				    				.withShows(values[1370+i*5].isBlank() ? 0 : Integer.parseInt(values[1370+i*5]))
				    				.withROI(values[1371+i*5].isBlank() ? 0 : Float.parseFloat(values[1371+i*5]))
				    				.withEarnings(values[1372+i*5].isBlank() ? 0 : Integer.parseInt(values[1372+i*5]))
				    				.build();
				    		jockeyStats.add(stat);
			    		}
			    	}   	
			    			    	
			    	Jockey jockey = Jockey.builder()
			    			.withName(values[32].trim())
			    			.withCurrentMeetStarts(values[34].isBlank() ? 0 :Integer.parseInt(values[34].trim()))
			    			.withCurrentMeetWins(values[35].isBlank() ? 0 :Integer.parseInt(values[35].trim()))
			    			.withCurrentMeetPlaces(values[36].isBlank() ? 0 :Integer.parseInt(values[36].trim()))
			    			.withCurrentMeetShows(values[37].isBlank() ? 0 :Integer.parseInt(values[37].trim()))
			    			.withCurrentYearStarts(values[1156].isBlank() ? 0 :Integer.parseInt(values[1156].trim()))
			    			.withCurrentYearWins(values[1157].isBlank() ? 0 :Integer.parseInt(values[1157].trim()))
			    			.withCurrentYearPlaces(values[1158].isBlank() ? 0 :Integer.parseInt(values[1158].trim()))
			    			.withCurrentYearShows(values[1159].isBlank() ? 0 :Integer.parseInt(values[1159].trim()))
			    			.withCurrentYearROI(values[1160].isBlank() ? 0 :Float.parseFloat(values[1160].trim()))
			    			.withPreviousYearStarts(values[1161].isBlank() ? 0 :Integer.parseInt(values[1161].trim()))
			    			.withPreviousYearWins(values[1162].isBlank() ? 0 :Integer.parseInt(values[1162].trim()))
			    			.withPreviousYearPlaces(values[1163].isBlank() ? 0 :Integer.parseInt(values[1163].trim()))
			    			.withPreviousYearShows(values[1164].isBlank() ? 0 :Integer.parseInt(values[1164].trim()))
			    			.withPreviousYearROI(values[1165].isBlank() ? 0 :Float.parseFloat(values[1165].trim()))
			    			.withJockeyStats(jockeyStats)
			    			.build();			    	
			    	
			    	Entry entry = Entry.builder()
			    			.withRaceNumber(race.getRaceNumber())
			    			.withOwner(WordUtils.capitalizeFully(values[38].trim()))
			    			.withOwnerSilks(WordUtils.capitalizeFully(values[39].trim()))
			    			.withMTOFlag(values[40].trim())
			    			.withClaimingPrice(values[13].isEmpty() ? 0 : Integer.parseInt(values[13].trim()))
			    			.withProgramNumber(values[42].trim())
			    			.withPostPosition(Integer.parseInt(values[3].trim()))
			    			.withEntry(values[4].trim())
			    			.withMLOdds(values[43].isEmpty() ? 0 : Float.parseFloat(values[43].trim()))
			    			.withName(WordUtils.capitalizeFully(values[44].trim()))
			    			.withFlag((horse != null) ? horse.getFlag() : null)
			    			.withComment((horse != null) ? horse.getComment() : null)
			    			.withYearOfBirth(values[45].isBlank() ? 5 : Integer.parseInt(values[45].trim()))
			    			.withFoalingMonth(new DateFormatSymbols().getShortMonths()[Integer.parseInt(values[46])-1] )
			    			.withSex(values[48].trim())
			    			.withColor(WordUtils.capitalizeFully(values[49].trim()))
			    			.withWeight(values[50].isEmpty() ? 0 :  Integer.parseInt(values[50].trim()))
			    			.withApprenticeWeightAllowed(values[33].isEmpty() ? 0 : Integer.parseInt(values[33].trim()))
			    			.withSire(WordUtils.capitalizeFully(values[51].trim()))
			    			.withSiresSire(WordUtils.capitalizeFully(values[52].trim()))
			    			.withDam(WordUtils.capitalizeFully(values[53].trim()))
			    			.withDamsSire(WordUtils.capitalizeFully(values[54].trim()))
			    			.withBreeder(WordUtils.capitalizeFully(values[55].trim()))
			    			.withSireStudFee(values[1176].isEmpty() ? 0 : Float.parseFloat(values[1176].trim()))
			    			.withAuctionPrice(values[1221].isEmpty() ? 0 : Float.parseFloat(values[1221].trim()))
			    			.withAuctionLocationDate(values[1222].trim())
			    			.withPedigreeRatingDirt(values[1263].trim())
			    			.withPedigreeRatingMud(values[1264].trim())
			    			.withPedigreeRatingTurf(values[1265].trim())
			    			.withPedigreeRatingDist(values[1266].trim())
			    			.withStateCountry(values[56].trim())
			    			.withProgramPostPosition(values[57].isBlank() ? 0 : Integer.parseInt(values[57].trim()))
			    			.withMedication(values[61].isBlank() ? MedicationType.UNAVAILABLE :  medicationTypeMap.get(Integer.parseInt(values[61].trim())))
			    			.withEquipment(values[63].isBlank() ? EquipmentChangeType.UNAVAILABLE : equipmentChangeTypeMap.get(Integer.parseInt(values[63].trim())))
			    			.withLifetimeStarts(values[96].isBlank() ? 0 : Integer.parseInt(values[96].trim()))
			    			.withLifetimeWins(values[97].isBlank() ? 0 : Integer.parseInt(values[97].trim()))
			    			.withLifetimePlaces(values[98].isBlank() ? 0 : Integer.parseInt(values[98].trim()))
			    			.withLifetimeShows(values[99].isBlank() ? 0 : Integer.parseInt(values[99].trim()))
			    			.withLifetimeEarnings(values[100].isBlank() ? 0 : Float.parseFloat(values[100].trim()))
			    			.withLifetimeBestSpeed(values[1327].isBlank() ? 0 : Integer.parseInt(values[1327].trim()))
			    			.withDistanceStarts(values[64].isBlank() ? 0 : Integer.parseInt(values[64].trim()))
			    			.withDistanceWins(values[65].isBlank() ? 0 : Integer.parseInt(values[65].trim()))
			    			.withDistancePlaces(values[66].isBlank() ? 0 : Integer.parseInt(values[66].trim()))
			    			.withDistanceShows(values[67].isBlank() ? 0 : Integer.parseInt(values[67].trim()))
			    			.withDistanceEarnings(values[68].isBlank() ? 0 : Float.parseFloat(values[68].trim()))	
			    			.withDistanceBestSpeed(values[1180].isBlank() ? 0 : Integer.parseInt(values[1180].trim()))
			    			.withCurrentTrack(race.getTrack())
			    			.withTrackStarts(values[69].isBlank() ? 0 : Integer.parseInt(values[69].trim()))
			    			.withTrackWins(values[70].isBlank() ? 0 : Integer.parseInt(values[70].trim()))
			    			.withTrackPlaces(values[71].isBlank() ? 0 : Integer.parseInt(values[71].trim()))
			    			.withTrackShows(values[72].isBlank() ? 0 : Integer.parseInt(values[72].trim()))
			    			.withTrackEarnings(values[73].isBlank() ? 0 : Float.parseFloat(values[73].trim()))
			    			.withTrackBestSpeed(values[1330].isBlank() ? 0 : Integer.parseInt(values[1330].trim()))
			    			.withDirtStarts(values[1331].isBlank() ? 0 : Integer.parseInt(values[1331].trim()))
			    			.withDirtWins(values[1332].isBlank() ? 0 : Integer.parseInt(values[1332].trim()))
			    			.withDirtPlaces(values[1333].isBlank() ? 0 : Integer.parseInt(values[1333].trim()))
			    			.withDirtShows(values[1334].isBlank() ? 0 : Integer.parseInt(values[1334].trim()))
			    			.withDirtEarnings(values[1335].isBlank() ? 0 : Float.parseFloat(values[1335].trim()))
			    			.withDirtBestSpeed(values[1177].isBlank() ? 0 : Integer.parseInt(values[1177].trim()))
			    			.withTurfStarts(values[74].isBlank() ? 0 : Integer.parseInt(values[74].trim()))
			    			.withTurfWins(values[75].isBlank() ? 0 : Integer.parseInt(values[75].trim()))
			    			.withTurfPlaces(values[76].isBlank() ? 0 : Integer.parseInt(values[76].trim()))
			    			.withTurfShows(values[77].isBlank() ? 0 : Integer.parseInt(values[77].trim()))
			    			.withTurfEarnings(values[78].isBlank() ? 0 : Float.parseFloat(values[78].trim()))
			    			.withTurfBestSpeed(values[1178].isBlank() ? 0 : Integer.parseInt(values[1178].trim()))
			    			.withWetStarts(values[79].isBlank() ? 0 : Integer.parseInt(values[79].trim()))
			    			.withWetWins(values[80].isBlank() ? 0 : Integer.parseInt(values[80].trim()))
			    			.withWetPlaces(values[81].isBlank() ? 0 : Integer.parseInt(values[81].trim()))
			    			.withWetShows(values[82].isBlank() ? 0 : Integer.parseInt(values[82].trim()))
			    			.withWetEarnings(values[83].isBlank() ? 0 : Float.parseFloat(values[83].trim()))
			    			.withWetBestSpeed(values[1179].isBlank() ? 0 : Integer.parseInt(values[1179].trim()))
			    			.withAllWeatherStarts(values[230].isBlank() ? 0 : Integer.parseInt(values[230].trim()))
			    			.withAllWeatherWins(values[231].isBlank() ? 0 : Integer.parseInt(values[231].trim()))
			    			.withAllWeatherPlaces(values[232].isBlank() ? 0 : Integer.parseInt(values[232].trim()))
			    			.withAllWeatherShows(values[233].isBlank() ? 0 : Integer.parseInt(values[233].trim()))
			    			.withAllWeatherEarnings(values[234].isBlank() ? 0 : Float.parseFloat(values[234].trim()))
			    			.withAllWeatherBestSpeed(values[235].isBlank() ? 0 : Integer.parseInt(values[235].trim()))
			    			.withCurrentYear(values[84].isBlank() ? 0 : Integer.parseInt(values[84].trim()))
			    			.withCurrentYearStarts(values[85].isBlank() ? 0 : Integer.parseInt(values[85].trim()))
			    			.withCurrentYearWins(values[86].isBlank() ? 0 : Integer.parseInt(values[86].trim()))
			    			.withCurrentYearPlaces(values[87].isBlank() ? 0 : Integer.parseInt(values[87].trim()))
			    			.withCurrentYearShows(values[88].isBlank() ? 0 : Integer.parseInt(values[88].trim()))
			    			.withCurrentYearEarnings(values[89].isBlank() ? 0 : Float.parseFloat(values[89].trim()))
			    			.withPreviousYear(values[90].isBlank() ? 0 : Integer.parseInt(values[90].trim()))
			    			.withPreviousYearStarts(values[91].isBlank() ? 0 : Integer.parseInt(values[91].trim()))
			    			.withPreviousYearWins(values[92].isBlank() ? 0 : Integer.parseInt(values[92].trim()))
			    			.withPreviousYearPlaces(values[93].isBlank() ? 0 : Integer.parseInt(values[93].trim()))
			    			.withPreviousYearShows(values[94].isBlank() ? 0 : Integer.parseInt(values[94].trim()))
			    			.withPreviousYearEarnings(values[95].isBlank() ? 0 : Float.parseFloat(values[95].trim()))
			    			.withMostRecentYearBestSpeed(values[1328].isBlank() ? 0 : Integer.parseInt(values[1328].trim()))
			    			.withSecondMostRecentYearBestSpeed(values[1329].isBlank() ? 0 : Integer.parseInt(values[1329].trim()))
			    			.withRunStyle(values[209].trim())
							.withSpeedPoints(values[210].isBlank() ? 0 : Integer.parseInt(values[210].trim()))
							.withDaysSinceLastRace(values[223].isBlank() ? 0 : Integer.parseInt(values[223].trim()))
							.withPrimePowerRating(values[250].isEmpty() ? 0 : Float.parseFloat(values[250].trim()))
							.withAverageClassRatingLastThree(values[223].isBlank() ? 0 : Float.parseFloat(values[223].trim()))
							.withTrainer(trainer)
							.withJockey(jockey)
							.withWorkouts(workouts)
							.withPastPerformances(pps)
							.withScratchedFlag(false)
							.with_showDetails(false)
							.withSelection("")
							.withPick(false)
							.withFinishPosition(99)
							.withWinPayout(0)
							.withPlacePayout(0)
							.withShowPayout(0)
			    			.build();
			    	
			    	entries.add(entry);
				  		    				    	
			    }
			    
    			race.setEntries(entries);
    			races.add(race);
			    
			}

		    
 		} catch (Exception e) {
			e.printStackTrace();
			
			
 		} 
		
		return races;
		
		
		
	}
	
    public static void download(String fileURL, String localFilename) throws Exception {

    	try {
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.prompt_for_download", false);	
			prefs.put("download.default_directory", localFilename);
			options.setExperimentalOption("prefs", prefs);
			
			java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
			
			ChromeDriverService chromeDriverService = new ChromeDriverService.Builder().build(); 
			chromeDriverService.sendOutputTo(NullOutputStream.NULL_OUTPUT_STREAM);
			
			WebDriverManager.chromedriver().setup();
			ChromeDriver driver = new ChromeDriver(chromeDriverService, options);
			driver.get(fileURL);
			Thread.sleep(1000);
			
			System.out.println(driver.getPageSource());
    		
 		} catch (Exception e) {
			e.printStackTrace();
			
			
 		} 
     
    
	}
    
	
}
