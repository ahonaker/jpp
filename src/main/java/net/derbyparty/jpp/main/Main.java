package net.derbyparty.jpp.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;

import net.derbyparty.jpp.chart.ProcessChart;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.factors.Angles;
import net.derbyparty.jpp.factors.Factors;
import net.derbyparty.jpp.factors.Ratings;
import net.derbyparty.jpp.loader.Loader;
import net.derbyparty.jpp.object.Angle;
import net.derbyparty.jpp.object.Card;
import net.derbyparty.jpp.object.Entry;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.MultiRaceWager;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceCategory;
import net.derbyparty.jpp.object.RaceDate;
import net.derbyparty.jpp.object.RaceNote;
import net.derbyparty.jpp.object.Track;
import net.derbyparty.jpp.pastperformanceparser.PastPerformanceParser;

public class Main {

	//static List<Race> races = new ArrayList<Race>();
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	static Card card = new Card();
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	static String distanceOption = "all";
	static String surfaceOption = "all";
	static String conditionOption = "all";
	
	final static String dir = "/Users/ahonaker/Google Drive/pp/jpp/";
	final static String saveDir = "/Users/ahonaker/Google Drive/pp/jpp/saveDir/";
	final static String horsesToWatchDir = "/Users/ahonaker/Google Drive/pp/jpp/horsesToWatch/";
	final static String tracksFile = "/Users/ahonaker/Google Drive/pp/jpp/tracks.json";
	final static String raceDatesFile = "/Users/ahonaker/Google Drive/pp/jpp/raceDates.json";
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().
		       conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION)).automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);
	
		
	public static void updateOptions(String dOption, String sOption, String cOption) throws Exception {
		distanceOption = dOption;
		surfaceOption = sOption;
		conditionOption = cOption;
	}
	
	public static String load(String filename) throws Exception {
		
		try {
			List<Race> races = new ArrayList<Race>();
			races = Loader.get(dir + filename);
			card.setTrack(races.get(0).getTrack());
			card.setDate(races.get(0).getDate());
			card.setRaces(races);
			getChanges();
		} catch (Exception e) {
			throw e;
		}
		
		return getAll();
	}
	
	public static void save() throws Exception {
		
		try {			
			card.save();
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static String retrieve(String track, Date date) throws Exception {
		
		try {
			card = Card.get(track, date);		
			return getAll();
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static String getSavedList() throws Exception {
		
		try {	        
			List<Document> saves = new ArrayList<>();
            Bson projection = Projections.fields(Projections.include("track", "date"), Projections.excludeId());
			MongoCollection<Document> collection = database.getCollection("cards");
			FindIterable<Document> iterable = collection.find().projection(projection);
			iterable.into(saves);
			System.out.println(mapper.writeValueAsString(saves));
			
            return mapper.writeValueAsString(saves);
            
    		} catch (Exception e) {
    			throw e;
    		}
	}
	
	public static int getNumRaces() throws Exception {
		
		return card.getRaces().size();
	}
		
	public static String getAll() throws Exception {
		
//		addKeyRacesToPP();
//		addHorsesToPP();
		return mapper.writeValueAsString(card.getRaces());
	}
	
	public static String get(int raceNumber) throws Exception {
		
//		addKeyRacesToPP();
//		addHorsesToPP();
		for (Race race : card.getRaces()) {
			if (race.getRaceNumber() == raceNumber) return mapper.writeValueAsString(race);
		}
 		throw new Exception("Race not found.");
	}
	
	public static String getSelectionSummary() throws Exception {
		
		ArrayNode rs = mapper.createArrayNode();
		for (Race race : card.getRaces()) {
			ObjectNode r = mapper.createObjectNode();
			r.put("raceNumber", race.getRaceNumber());
			r.put("postTimes", race.getPostTimes());
			r.put("track", race.getTrack());
			r.put("date", formatter.format(race.getDate()));
			ArrayNode hs = mapper.createArrayNode();
			for (Entry entry : race.getUnscratchedEntries()) {
				ObjectNode h = mapper.createObjectNode();
				h.put("programNumber", entry.getProgramNumber());
				h.put("pick", entry.getPick());
				h.put("selection", entry.getSelection());
				h.put("afv", entry.getARatingFairValue());
				h.put("bettingLine", entry.getBettingLine());
				h.put("mlodds", entry.getMLOdds());
				h.put("finishPosition", entry.getFinishPosition());
				hs.add(h);
			}
			r.set("horses", hs);
			rs.add(r);
		}
		return mapper.writeValueAsString(rs);
	}
	
	public static void getChanges () throws Exception {
		
		try {
		
			ObjectNode program = Loader.getDRF(card.getTrack(), card.getDate());
			for (JsonNode raceNode : program.get("races")) {
				for (Race race : card.getRaces()) {
					if (race.getRaceNumber() == raceNode.get("raceKey").get("raceNumber").asInt()) {				
						List<String> changes = new ArrayList<String>();
						for (JsonNode changeNode : raceNode.get("changes")) {	
							changes.add(changeNode.get("text").asText());
							if (changeNode.get("type").asText().equals("S")) {
								for (Entry entry : race.getEntries()) {
									if (entry.getName().equals(changeNode.get("text").asText().replace(" scratched", ""))) {
										entry.setScratchedFlag(true);
									}
								}
							}
						}
						race.setChanges(changes);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		
		} 
	}
	
	public static void getResults () throws Exception {
		
		try {
		
			ObjectNode program = Loader.getDRFResults(card.getTrack(), card.getDate());
			if (program.has("races")) {
				for (JsonNode raceNode : program.get("races")) {
					for (Race race : card.getRaces()) {
						if (race.getRaceNumber() == raceNode.get("raceKey").get("raceNumber").asInt()) {				
							
							for (JsonNode runnerNode : raceNode.get("runners")) {	
								for (Entry entry : race.getEntries()) {
									if (entry.getProgramNumber().equals(runnerNode.get("programNumber").asText().trim())) {
										entry.setWinPayout(runnerNode.get("winPayoff").floatValue());
										entry.setPlacePayout(runnerNode.get("placePayoff").floatValue());
										entry.setShowPayout(runnerNode.get("showPayoff").floatValue());
										if (runnerNode.get("winPayoff").floatValue() > 0) {
											entry.setFinishPosition(1);
										} else if (runnerNode.get("placePayoff").floatValue() > 0) {
											entry.setFinishPosition(2);
										} else if (runnerNode.get("showPayoff").floatValue() > 0) {
											entry.setFinishPosition(3);
										} else entry.setFinishPosition(0);
									}
								}
	
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		
		} 
	}
	
	public static String augment (File ppFile) throws Exception {
		
		try {
			PastPerformanceParser.extractPastPerformance(card.getRaces(), ppFile);
			calculate();
			
		} catch (Exception e) {
			e.printStackTrace();
		
		
		} 
		return getAll();
	}
	
	public static String addLateData (String filename) throws Exception {
		
		try {
			List<Race> updatedRaces =  Loader.get(dir + filename);
			for (Race race : card.getRaces()) {
				for (Race updatedRace : updatedRaces) {
					if (race.getRaceNumber() == updatedRace.getRaceNumber()) {
						race.setWagerTypes(updatedRace.getWagerTypes());
						race.setMultiRaceWagers(updatedRace.getMultiRaceWagers());
						for (Entry entry : race.getEntries()) {
							Boolean found = false;
							for (Entry updatedHorse : updatedRace.getEntries()) {
								if (entry.getName().equals(updatedHorse.getName())) {
									entry.setProgramNumber(updatedHorse.getProgramNumber());
									entry.setMLOdds(updatedHorse.getMLOdds());
									entry.setJockey(updatedHorse.getJockey());
									found = true;
								}
							}
							if (!found) toggleScratch(race.getRaceNumber(),entry.getName());
						}
					}
				}
					
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return getAll();
	}
	
	public static List<PastPerformance> filterPastPerformances (Race race, List<PastPerformance> pps) throws Exception {
		
		List<PastPerformance> results = new ArrayList<PastPerformance>();
		
		try {
			for (PastPerformance pp : pps) {
				Boolean include = true;
				
				if (distanceOption.equals("same") && race.getFurlongs() != pp.getFurlongs()) include = false;
				if (distanceOption.equals("similar") && (race.getFurlongs() < 8 && pp.getFurlongs() >= 8)) include = false;
				if (distanceOption.equals("similar") && (race.getFurlongs() >= 8 && pp.getFurlongs() < 8)) include = false;
				
				if (surfaceOption.equals("same")) {
						if (race.getTurfFlag() && pp.getTurfFlag() && race.getOffTheTurfFlag()) include = false;
						if (race.getTurfFlag() && !pp.getTurfFlag() && !race.getOffTheTurfFlag()) include = false;
						if (!race.getTurfFlag() && pp.getTurfFlag()) include = false;
						if (!race.getTurfFlag() && !race.getAllWeatherSurfaceFlag().equals("A") && pp.getAllWeatherSurfaceFlag().equals("A")) include = false;
						if (race.getAllWeatherSurfaceFlag().equals("A") && !pp.getAllWeatherSurfaceFlag().equals("A")) include = false;
						
				}
				if (surfaceOption.equals("off") && !pp.getOffTheTurfFlag()) include = false;

				
				if (conditionOption.equals("good") && !pp.getTrackCondition().equals("ft") && !pp.getTrackCondition().equals("fm")) {
					include = false;
				}
				else if (conditionOption.equals("off") && (pp.getTrackCondition().equals("ft") || pp.getTrackCondition().equals("fm"))) {
					include = false;
				}
				else if (!conditionOption.equals("all") && !pp.getTrackCondition().equals(race.getTrackCondition())) include = false;
				
				if (include && !pp.getIgnore()) results.add(pp);
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return results;
		
	}
	
	public static String calculate() throws Exception {
		
		try {
			List<Document> combos2 = Analytics.getComboAlerts(2);
			List<Document> combos3 = Analytics.getComboAlerts(3);
			for (Race race : card.getRaces()) {
							
				for (Entry entry : race.getUnscratchedEntries()) {
					
					List<PastPerformance> pps = filterPastPerformances(race, entry.getUnignoredPastPerformances());
					
					for (int p = 0; p < pps.size(); p++) {
					
						PastPerformance pp = pps.get(p);						
						pp.setRaceStrength(Factors.CalcRaceStrength(pp));
						pp.setLast5AverageSpeed(Factors.CalcLast5AverageSpeed(p, pps));
						pp.setRaceShape(Factors.DetermineRaceShape(pp));
					}
					
					entry.setE1Avg(Factors.CalcE1Avg(pps));
					entry.setE2Avg(Factors.CalcE2Avg(pps));
					entry.setMaxE2(Factors.CalcMaxE2(pps));
					entry.setLatePaceAvg(Factors.CalcLatePaceAvg(pps));
					entry.setAvgAdjustedSpeedRating(Factors.CalcAverageAdjustedSpeedRating(pps));
					
					ObjectNode closingRatio = Factors.CalcClosingRatio(pps);
					if (closingRatio.hasNonNull("EarlyPosition")) entry.setEarlyPosition(closingRatio.get("EarlyPosition").floatValue());
					if (closingRatio.hasNonNull("LatePosition")) entry.setLatePosition(closingRatio.get("LatePosition").floatValue());
					if (closingRatio.hasNonNull("ClosingRatio")) entry.setClosingRatio(closingRatio.get("ClosingRatio").floatValue());
					
					entry.setLatePaceBestLast3(Factors.CalcLatePaceBestLast3(pps));
					entry.setPaceAdjustedLate(Factors.CalcPaceAdjustedLate(pps));
					entry.setLatePaceLast(Factors.CalcLatePaceLast(pps));
					
					entry.setClassRating((Factors.CalcClassRating(pps, race)));
					entry.setAverageCompetitiveLevel((Factors.CalcAverageCompetitiveLevel(pps, race)));
					entry.setLastRaceStrength(Factors.CalcLastRaceStrength(pps));
					
					entry.setClassShift(Factors.CalcClassShift(pps, race));
					entry.setPurseShift(Factors.CalcPurseShift(pps, race));
					
					entry.setSpeedRating(Factors.CalcSpeedRating(pps, race));
					
					entry.setBasicFitness(Factors.CalcBasicFitness(pps, entry.getWorkouts()));
					entry.setFormPoints(Factors.CalcFormPoints(pps));
					entry.setFurlongDays(Factors.CalcFurlongDays(pps, entry.getWorkouts(), race));
					entry.setTurnTime(Factors.CalcTurnTime(pps));
					
					entry.setARatingClass(Ratings.calcARatingClass(race, entry));
					entry.setARatingForm(Ratings.calcARatingForm(race, entry));
					entry.setARatingConnections(Ratings.calcARatingConnections(race, entry));
					entry.setARating(Ratings.calcARating(race, entry));
					
				}
				
				race.setPaceScenario(Factors.DeterminePaceScenario(race.getUnscratchedEntries()));
				race.setE1Avg(Factors.CalcRaceE1Avg(race.getUnscratchedEntries()));
				race.setE2Avg(Factors.CalcRaceE2Avg(race.getUnscratchedEntries()));
				race.setMaxE2(Factors.CalcRaceMaxE2(race.getUnscratchedEntries()));
				race.setLatePaceBestLast3(Factors.CalcRaceLatePaceBestLast3(race.getUnscratchedEntries()));
				race.setMaxSpeedRating(Factors.CalcRaceMaxSpeedRating(race.getUnscratchedEntries()));
				race.setMaxSpeed(Factors.CalcRaceMaxSpeed(race.getUnscratchedEntries()));
				race.setAverageAdjustedSpeed(Factors.CalcRaceAverageAdjustedSpeedRating(race.getUnscratchedEntries()));
				race.setTotalSpeedPoints(Factors.CalcTotalSpeedPoints(race.getUnscratchedEntries()));
				
				race.setAdvantagedEntries(Ratings.identifyPaceAdvantage(race));
				race.setHandicappingNotes(Factors.GenerateHandicappingNotes(race));
				
				for (Entry entry : race.getUnscratchedEntries()) {
					
					List<Angle> angles = entry.getAngles();
					List<Angle> newAngles = new ArrayList<Angle>();
					for (Angle angle : angles) {
						if (angle.getSource().equals("Augmented")) newAngles.add(angle);
					}
					for (Angle angle : Angles.generateAngles(race, entry)) {
						newAngles.add(angle);
					}
					entry.setAngles(newAngles);
					
					String comboAlert = "";
					
					for (Document combo : combos2) {
						int matches = 0;
						for (Angle angle : newAngles ) {
							if (angle.getName() != null 
									&& 
									(combo.get("_id", Document.class).getString("angle0").equals(angle.getName())
										|| combo.get("_id", Document.class).getString("angle1").equals(angle.getName())
									)
								)
								matches++;
						}
						if (matches == 2) {
							if (comboAlert != "") comboAlert += "; ";
							comboAlert += combo.get("_id", Document.class).getString("angles") + " (" +
								combo.getInteger("winners") + "/" +
								combo.getInteger("total") + " $" +
								String.format("%.2f", combo.getDouble("ROI")) + ")";
						}
					}
					for (Document combo : combos3) {
						int matches = 0;
						for (Angle angle : newAngles ) {
							if (angle.getName() != null 
									&& 
									(combo.get("_id", Document.class).getString("angle0").equals(angle.getName())
										|| combo.get("_id", Document.class).getString("angle1").equals(angle.getName())
										|| combo.get("_id", Document.class).getString("angle2").equals(angle.getName())
									)
								)
								matches++;						}
						
						if (matches == 3) {
							if (comboAlert != "") comboAlert += "; ";
							comboAlert += combo.get("_id", Document.class).getString("angles") + " (" +
								combo.getInteger("winners") + "/" +
								combo.getInteger("total") + " $" +
								String.format("%.2f", combo.getDouble("ROI")) + ")";
						}
					}
					entry.setComboAlert(comboAlert);
				}
				

			}
			
			setARatingFairValue();
			
		} catch (Exception e) {
			throw e;
		}
		
		return "";
	}
	
	public static void setARatingFairValue () throws Exception {
		
		try {
			
			for (Race race : card.getRaces()) {
				float maxARating = 0;
				for (Entry entry : race.getUnscratchedEntries()) {
					if (entry.getARating() > maxARating && entry.getClassRating() > 0) maxARating = entry.getARating();
				}
				float total = 0;
				for (Entry entry : race.getUnscratchedEntries()) {
					if (entry.getClassRating() > 0) total += (1 / (maxARating - entry.getARating() + 1));
				}
				for (Entry entry : race.getUnscratchedEntries()) {
					if (entry.getClassRating() > 0) {
						entry.setARatingFairValue(1 / 
							((1 / (maxARating - entry.getARating() + 1)) / total)
							- 1);
					} else {
						entry.setARatingFairValue(0);
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleScratch(int raceNumber, String name) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							entry.setScratchedFlag(!entry.getScratchedFlag());
							if (entry.getScratchedFlag()) entry.setSelection("X");
							if (entry.getScratchedFlag()) entry.setPick(false);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void togglePick(int raceNumber, String name) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							entry.setPick(!entry.getPick());
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleShowDetail(int raceNumber, String name) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							if (entry.get_showDetails() == null) entry.set_showDetails(false);
							entry.set_showDetails(!entry.get_showDetails());
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}	
	
	public static void toggleIgnored(int raceNumber, String name, Date raceDate) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							for (PastPerformance pp : entry.getPastPerformances()) {
								if (pp.getRaceDate().equals(raceDate)) {
									pp.setIgnore(!pp.getIgnore());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleOffTheTurf(int raceNumber) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					race.setOffTheTurfFlag(!race.getOffTheTurfFlag());
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleOntoAllWeather(int raceNumber) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					race.setOntoAllWeatherFlag(!race.getOntoAllWeatherFlag());
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void setTrackCondition(int raceNumber, String condition) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					race.setTrackCondition(condition);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void setRaceNote(int raceNumber, String note) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					race.setNote(note);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void setEntryNote(int raceNumber, String name, String note) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							entry.setNote(note);
						}
					}
				}
			}			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String generatePickString (List<List<String>> picks) throws Exception {
		StringJoiner outerJoiner = new StringJoiner("/");
		
		try {
			for (int i = 0; i < picks.size(); i++) {
				StringJoiner innerJoiner = new StringJoiner(",");
				for (int j = 0; j < picks.get(i).size(); j++) {
					innerJoiner.add(picks.get(i).get(j));
				}
				outerJoiner.add(innerJoiner.toString());
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return outerJoiner.toString();
	}
	
	public static void setBettingLine(int raceNumber, String name, float bettingLine) throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							entry.setBettingLine(bettingLine);
						}
					}
				}
			}			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String setSelection(int raceNumber, String name, String selection) throws Exception {
		
		ArrayNode results = mapper.createArrayNode();
		
		try {
			for (Race race : card.getRaces()) {
				if (race.getRaceNumber() == raceNumber) {
					for (Entry entry : race.getEntries()) {
						if (entry.getName().equals(name)) {
							entry.setSelection(selection);
						}
					}
				}
			}
			
			for (Race race : card.getRaces()) {
				ArrayNode wagerNodes = mapper.createArrayNode();
				
				for (MultiRaceWager wager : race.getMultiRaceWagers()) {
					
					List<List<String>> aPicks =  new ArrayList<List<String>>();
					List<List<String>> abPicks =  new ArrayList<List<String>>();
					List<List<String>> bPicks =  new ArrayList<List<String>>();
					List<List<String>> cPicks =  new ArrayList<List<String>>();
					
					for (int i = race.getRaceNumber()-1; i <= race.getRaceNumber()-1 + wager.getNumRaces()-1; i++) {
					
						List<String> aHorses = new ArrayList<String>();
						List<String> abHorses = new ArrayList<String>();
						List<String> bHorses = new ArrayList<String>();
						List<String> cHorses = new ArrayList<String>();
						
						for (Entry entry : card.getRaces().get(i).getUnscratchedEntries()) {
							String s = entry.getProgramNumber().isEmpty() ? String.valueOf(entry.getPostPosition()) : entry.getProgramNumber();
							if (entry.getSelection() != null) {
								if (entry.getSelection().equals("A")) {
									if (!aHorses.contains(s)) aHorses.add(s);
									if (!abHorses.contains(s)) abHorses.add(s);
									
								}
								if (entry.getSelection().equals("B")) {
									if (!abHorses.contains(s)) abHorses.add(s);
									if (!bHorses.contains(s)) bHorses.add(s);
								}
								if (entry.getSelection().equals("C")) {
									if (!cHorses.contains(s)) cHorses.add(s);
								}
							}
						}
						
						aPicks.add(aHorses);
						abPicks.add(abHorses);
						bPicks.add(bHorses);
						cPicks.add(cHorses);
						
					}
					
					//  All As
					wager.setTmA(generatePickString(aPicks));
					float aCost = aPicks.get(0).size();
					for (int j = 1; j < aPicks.size(); j++) {
						aCost *= aPicks.get(j).size();
					}
					wager.setTmACost(aCost * wager.getMin());
					
					// All As and All Bs
					wager.setTmAB(generatePickString(abPicks));
					float abCost = abPicks.get(0).size();
					for (int j = 1; j < abPicks.size(); j++) {
						abCost *= abPicks.get(j).size();
					}
					wager.setTmABCost(abCost * wager.getMin());
					
					// All As and One B		
					if (aPicks.size() == wager.getNumRaces() && bPicks.size() == wager.getNumRaces()) {
						List<String> b1 = new ArrayList<String>();
						float b1Cost = 0;
										
						for (int i = 0; i < wager.getNumRaces(); i++) {
							List<List<String>> b1combo = new ArrayList<List<String>>();
							for (int j = 0; j < wager.getNumRaces(); j++) {
								if (i==j) {
									b1combo.add(bPicks.get(j));
								} else {
									b1combo.add(aPicks.get(j));
								}
							}
							b1.add(generatePickString(b1combo));
							float b1ComboCost = b1combo.get(0).size();
							for (int k = 1; k < b1combo.size(); k++) {
								b1ComboCost *= b1combo.get(k).size();
							}
							b1Cost += b1ComboCost;
						}
						
						
						wager.setTmB1(b1);
						wager.setTmB1Cost(b1Cost * wager.getMin());
						
						// All As and Two Bs
						List<String> b2 = new ArrayList<String>();
						float b2Cost = 0;
						
						
						for (int i = 0; i < wager.getNumRaces(); i++) {			
							for (int j = i+1; j < wager.getNumRaces(); j++) {
								List<List<String>> b2combo = new ArrayList<List<String>>();
								for(int k = 0; k < wager.getNumRaces(); k++) {
									if (i != j) {
										if (i==k || j==k) {
											b2combo.add(bPicks.get(k));
										} else {
											b2combo.add(aPicks.get(k));
										}	
									}
								}
								b2.add(generatePickString(b2combo));
								float b2ComboCost = b2combo.get(0).size();
								for (int m = 1; m < b2combo.size(); m++) {
									b2ComboCost *= b2combo.get(m).size();
								}
								b2Cost += b2ComboCost;
							}
						}
							
						wager.setTmB2(b2);
						wager.setTmB2Cost(b2Cost * wager.getMin());
					}
					
					// All As and One C
					if (aPicks.size() == wager.getNumRaces() && cPicks.size() == wager.getNumRaces()) {
						List<String> c1 = new ArrayList<String>();
						float c1Cost = 0;
										
						for (int i = 0; i < wager.getNumRaces(); i++) {
							List<List<String>> c1combo = new ArrayList<List<String>>();
							for (int j = 0; j < wager.getNumRaces(); j++) {
								if (i==j) {
									c1combo.add(cPicks.get(j));
								} else {
									c1combo.add(aPicks.get(j));
								}
							}
							c1.add(generatePickString(c1combo));
							float c1ComboCost = c1combo.get(0).size();
							for (int k = 1; k < c1combo.size(); k++) {
								c1ComboCost *= c1combo.get(k).size();
							}
							c1Cost += c1ComboCost;
						}
						
						wager.setTmC1(c1);	
						wager.setTmC1Cost(c1Cost * wager.getMin());
					}
					
					ObjectNode wagerNode = mapper.valueToTree(wager);
					wagerNodes.add(wagerNode);
				}	
					
				results.add(wagerNodes);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return mapper.writeValueAsString(results);
	}
	
	public static void convertCards() throws Exception {
		try {
			Files.list(new File(saveDir).toPath())
            .forEach(path -> {
                File file = new File(path.toString());
                try {                	
                	System.out.println(path);
                	if (path.toString().contains(".json")) {
                		JsonNode card = mapper.readValue(file, JsonNode.class);
                		ArrayNode newCard = mapper.readValue(
                		mapper.writeValueAsString(card)
                			.replace("advantagedHorses", "advantagedEntries")
                			.replace("unscratchedHorsesCount", "unscratchedEntriesCount")
                			.replace("\"horses\":", "\"entries\":"), ArrayNode.class);
                		
                		List<Race> races = new ArrayList<Race>();
                		for (JsonNode race : newCard) {
                			ObjectNode raceNode = (ObjectNode) new ObjectMapper().readTree(mapper.writeValueAsString(race));
                			raceNode.put("date", 
										LocalDate.of(
											raceNode.get("date").get(0).asInt(),
											raceNode.get("date").get(1).asInt(),
											raceNode.get("date").get(2).asInt())
										  .atStartOfDay()
									      .atZone(ZoneId.systemDefault())
									      .toInstant()
									      .toEpochMilli()
									      
                					);
                			ArrayNode horses = mapper.createArrayNode();
                			for (JsonNode horse : race.get("entries")) {
                				ArrayNode pps = mapper.createArrayNode();
                				ObjectNode horseNode = (ObjectNode) new ObjectMapper().readTree(mapper.writeValueAsString(horse));
                 				for (JsonNode pp : horseNode.get("pastPerformances")) {
                        			ObjectNode ppNode = (ObjectNode) new ObjectMapper().readTree(mapper.writeValueAsString(pp));
                        			
                        			long millis = LocalDate.of(
											ppNode.get("raceDate").get(0).asInt(),
											ppNode.get("raceDate").get(1).asInt(),
											ppNode.get("raceDate").get(2).asInt())
										  .atStartOfDay()
									      .atZone(ZoneId.systemDefault())
									      .toInstant()
									      .toEpochMilli();
                        			
                        			ppNode.remove("raceDate");
                        			ppNode.put("raceDate", millis);
                        			
                        			ppNode.remove("keyRace");
                        			
                        			pps.add(ppNode);
                				}		
                				horseNode.set("pastPerformances", pps);
                				
                				ArrayNode works = mapper.createArrayNode();
                				for (JsonNode work : horseNode.get("workouts")) {
                        			ObjectNode workNode = (ObjectNode) new ObjectMapper().readTree(mapper.writeValueAsString(work));
                        			
                        			Long millis = LocalDate.of(
											workNode.get("dateOfWorkout").get(0).asInt(),
											workNode.get("dateOfWorkout").get(1).asInt(),
											workNode.get("dateOfWorkout").get(2).asInt())
										  .atStartOfDay()
									      .atZone(ZoneId.systemDefault())
									      .toInstant()
									      .toEpochMilli();
                        			workNode.remove("dateOfWorkout");
                        			workNode.put("dateOfWorkout", millis);
                        			
                        			works.add(workNode);
                				}
                				
                				horseNode.set("workouts", works);
                				horses.add(horseNode);
                			}
                			
                			raceNode.set("entries", horses);
                			
                			races.add(mapper.readValue(mapper.writeValueAsString(raceNode), Race.class));
                		}
                		Card convertedCard = Card.builder()
                			.withTrack(races.get(0).getTrack())
                			.withDate(races.get(0).getDate())
                			.withRaces(races)
                			.build();
                		convertedCard.save();
                	}
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
           
		} catch (Exception e) {
			
		}
	}
	
	public static void convertNotes() throws Exception {
		
		try {
			Files.list(new File(horsesToWatchDir).toPath())
            .forEach(path -> {
                File file = new File(path.toString());
                try {
                	System.out.println(path);
					if (path.toString().contains(".json")) {
						ObjectNode obj = mapper.readValue(file, ObjectNode.class);
						Horse horse = Horse.builder()
							.withName(obj.get("name").asText())
							.withFlag((!obj.get("flag").isNull()) ? obj.get("flag").asText() : null)
							.withComment((!obj.get("comment").isNull()) ? obj.get("comment").asText() : null)
							.build();
						JsonNode notes = mapper.readTree(mapper.writeValueAsString(obj.get("raceNotes")));
						List<RaceNote> raceNotes = new ArrayList<RaceNote>();
						for (JsonNode jsonNote : notes) {
							RaceNote raceNote = RaceNote.builder()
								.withTrack(jsonNote.get("track").asText())
								.withRaceDate(java.util.Date.from(
										LocalDate.of(
											jsonNote.get("raceDate").get(0).asInt(),
											jsonNote.get("raceDate").get(1).asInt(),
											jsonNote.get("raceDate").get(2).asInt())
										  .atStartOfDay()
									      .atZone(ZoneId.systemDefault())
									      .toInstant()))
								.withRaceNumber(jsonNote.get("raceNumber").asInt())
								.withType(jsonNote.get("type").asText())
								.withRaceClassification(jsonNote.get("raceClassification").asText())
								.withPurse(jsonNote.get("purse").asInt())
								.withClaimingPrice(jsonNote.get("claimingPrice").asInt())
								.withDistance(jsonNote.get("distance").asInt())
								.withExactDistance(jsonNote.get("exactDistance").asBoolean())
								.withSurface(jsonNote.get("surface").asText())
								.withOffTurf(jsonNote.get("offTurf").asBoolean())
								.withTrackCondition(jsonNote.get("trackCondition").asText())
								.withPosition(jsonNote.get("position").asInt())
								.withBeatenLengths(Float.valueOf(jsonNote.get("beatenLengths").asText()))
								.withComment((!jsonNote.get("comment").isNull()) ? jsonNote.get("comment").asText() : null)
								.withFootnote(jsonNote.get("footnote").asText())
								.withFlag((!jsonNote.get("flag").isNull()) ? jsonNote.get("flag").asText() : null)
								.build();
							raceNotes.add(raceNote);
						}
						horse.setRaceNotes(raceNotes);
						horse.save();					
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
            
		} catch (Exception e) {
			
		}
	}
	
	public static void saveNotes(JsonNode data) throws Exception {
		
		try {
			System.out.println(mapper.writeValueAsString(data));
			for (JsonNode race : data) {
				System.out.println(race.get("raceDate").asLong());
				Date raceDate = new Date(race.get("raceDate").asLong());
				System.out.println(raceDate.toString());
				for (JsonNode starter : race.get("starters")) {
					MongoCollection<Horse> horsesCollection = database.getCollection("horses", Horse.class);
					Bson horseQuery = eq("name", starter.get("name").asText().replaceAll("\\s\\(.+\\)", ""));

					Horse horse = horsesCollection.find(horseQuery).first();
					if (horse != null) {
						if (!starter.get("horseFlag").isNull()) horse.setFlag(starter.get("horseFlag").asText());
						Boolean raceFound = false;
						for (RaceNote raceNote : horse.getRaceNotes()) {
							
							if (raceNote.getTrack().equals(race.get("track").asText()) 
								&& raceDate.equals(raceNote.getRaceDate())
								&& raceNote.getRaceNumber() == race.get("raceNumber").asInt()) {
									raceFound = true;
									raceNote.setComment(!starter.get("note").isNull() ? starter.get("note").asText() : "");
									raceNote.setFlag(!starter.get("raceFlag").isNull() ? starter.get("raceFlag").asText() : "");
							}
						}
						if (!raceFound) {
							
							throw new Exception("Race not found.");
						}
						horse.save();
					} else {
						throw new Exception("Horse not found.");
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static List<Track> getTracksList() throws Exception {
		try {
			List<Track> tracks = new ArrayList<>();
			
			MongoCollection<Track> collection = database.getCollection("tracks", Track.class);
			FindIterable<Track> iterable = collection.find();
			iterable.into(tracks);

			return tracks;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void saveTracks(List<Track> tracks) throws Exception {
		try {
			for (Track track : tracks) {
				track.save();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String getTracks() throws Exception {
		try {
			return mapper.writeValueAsString(getTracksList());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void generateMissingChartLinks() throws Exception {
		
		try {
			String fileName = "/Users/ahonaker/Google Drive/pp/jpp/chartsToGet.txt";
			SimpleDateFormat fmtA = new SimpleDateFormat("MM/dd/YYYY");
			SimpleDateFormat fmtB = new SimpleDateFormat("MMddYY");
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			for (Track track : getTracksList()) {
				for (RaceDate raceDate : track.getRaceDates()) {
					LocalDate date = raceDate.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (!raceDate.getHasChartFlag() && date.isBefore(LocalDate.now())) {							 
						if (raceDate.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(
							LocalDate.now().minusDays(7))) {
							writer.append("https://www.equibase.com/static/chart/pdf/"
								+ track.getCode()
								+ fmtB.format(raceDate.getRaceDate())
								+ "USA.pdf");
						} else {					
							writer.append("https://www.equibase.com/premium/eqbPDFChartPlus.cfm?RACE=A&BorP=P&TID=" 
									+ track.getCode()
									+ "&CTRY=USA&DT=" 
									+ fmtA.format(raceDate.getRaceDate())
									+ "&DAY=D&STYLE=EQB");
						}					
						writer.newLine();
					}
				}
			}
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void convertRaceDates() throws Exception  {
		try {
			
			List<JsonNode> jsonTracks = new ArrayList<JsonNode>(Arrays.asList(mapper.readValue(Paths.get(raceDatesFile).toFile(), JsonNode[].class)));
			
			for (JsonNode jsonTrack : jsonTracks) {
				
				Track track = Track.builder()
					.withCode(jsonTrack.get("code").toString().replace("\"",""))
					.withName(jsonTrack.get("name").toString().replace("\"",""))
					.build();
				
				List<RaceDate> dates = new ArrayList<RaceDate>();
				for (JsonNode jsonDate : jsonTrack.get("raceDates")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
					System.out.println("" +
								jsonDate.get("raceDate").get(0).asInt() + "-"
								+ jsonDate.get("raceDate").get(1).asInt() + "-"
								+ jsonDate.get("raceDate").get(2).asInt());
					RaceDate raceDate = RaceDate.builder()
						.withHasChartFlag(jsonDate.get("hasChartFlag").asBoolean())
						.withReviewedFlag(jsonDate.get("reviewedFlag").asBoolean())
						.withRaceDate(
							sdf.parse("" +
								jsonDate.get("raceDate").get(0).asInt() + "-"
								+ jsonDate.get("raceDate").get(1).asInt() + "-"
								+ jsonDate.get("raceDate").get(2).asInt()
									)
						)
						.build();
					dates.add(raceDate);
				}
				
				track.setRaceDates(dates);	
				track.save();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String getHorses() throws Exception {
		
		try {
	        
			List<Document> horses = new ArrayList<>();
			
			MongoCollection<Document> collection = database.getCollection("horses");
			Bson projection = Projections.fields(Projections.include("name", "flag"), Projections.excludeId());
			FindIterable<Document> iterable = collection.find().projection(projection);
			iterable.into(horses);
	     		
            return mapper.writeValueAsString(horses);
            
    		} catch (Exception e) {
    			throw e;
    		}
	}
	
	public static String getHorse(String name) throws Exception {
		try {			
			MongoCollection<Horse> collection = database.getCollection("horses", Horse.class);
			Bson query = eq("name", name);
			
			Horse horse = collection.find(query).first();
			
			List<PastPerformance> pps = new ArrayList<PastPerformance>();
			Document ppQuery = new Document()
					.append("name", horse.getName());
			MongoCollection<PastPerformance> ppCollection = database.getCollection("pastPerformances", PastPerformance.class);
			FindIterable<PastPerformance> iterable = ppCollection.find(ppQuery);
			iterable.into(pps);
			horse.setPastPerformances(pps);
			return mapper.writeValueAsString(horse);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void saveHorse(Horse horse) throws Exception {
		try {
			horse.save();
		} catch (Exception e) {
			throw e;
		}
	}
			
	
	public static void markChartsReviewed() throws Exception {
		try {
			JsonNode horses = mapper.readTree(Path.of("/Users/ahonaker/Google Drive/pp/jpp/horsesToWatch.json").toFile());
			List<Track> raceDates = new ArrayList<Track>(Arrays.asList(mapper.readValue(Paths.get(raceDatesFile).toFile(), Track[].class)));
			for (JsonNode horse : horses) {
				for (JsonNode raceNoteHorse : horse.get("raceNotes")) {
					for (Track track : raceDates) {
						if (track.getCode().equals(raceNoteHorse.get("track").asText()))
						for (RaceDate raceDate : track.getRaceDates()) {
							if (raceDate.getRaceDate().equals(formatter.parse(raceNoteHorse.get("raceDate").toString()))) {
								raceDate.setReviewedFlag(true);
							}
						}
					}
				}

			}
			mapper.writeValue(Paths.get(raceDatesFile).toFile(), raceDates);
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleChartReviewed(String trackToMark, Date date) throws Exception {
		
		try {
			List<Track> raceDates = new ArrayList<Track>(Arrays.asList(mapper.readValue(Paths.get(raceDatesFile).toFile(), Track[].class)));
			for (Track track : raceDates) {
				if (track.getCode().equals(trackToMark))
					for (RaceDate raceDate: track.getRaceDates()) {
						if (raceDate.getRaceDate().equals(date)) raceDate.setReviewedFlag(!raceDate.getReviewedFlag());
					}
			}
			mapper.writeValue(Paths.get(raceDatesFile).toFile(), raceDates);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public static void retrieveCalculateAndSaveAll() throws Exception {
		try {
			System.out.println("retrieveCalculateAndSaveAll started.");
			
			List<Document> saves = new ArrayList<>();
            Bson projection = Projections.fields(Projections.include("track", "date"), Projections.excludeId());
			MongoCollection<Document> collection = database.getCollection("cards");
			FindIterable<Document> iterable = collection.find().projection(projection);
			iterable.into(saves);

			for (Document save : saves) {
				card = Card.get(save.getString("track"), save.getDate("date"));
				calculate();
				save();
			}
					
		} catch (Exception e) {
			throw e;
		}
		
		System.out.println("retrieveCalculateAndSaveAll finished.");
	}
	
//	 //Script to update hasChartFlags based on presence of results
//	public static void script() throws Exception {
//		
//		try {
//			List<Track> tracks = getTracksList();
//			
//			for (Track track : tracks) {
//				for (RaceDate raceDate : track.getRaceDates()) {
//					List<RaceResult> raceResults =  ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
//					if (raceResults.size() == 0 && raceDate.getHasChartFlag()) {
//						raceDate.setHasChartFlag(false);
//						raceDate.setReviewedFlag(false);
//						System.out.println(track.getCode() + " " + raceDate.getRaceDate());
//					}
//				}
//				track.save();
//			}
//			
//			System.out.println("Done");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	// Script to reset race Categories for each track
	public static void createRaceCategories() throws Exception {
		
		try {			
			List<Track> tracks = getTracksList();
			for (Track track : tracks) {
				track.setRaceCategories(new ArrayList<RaceCategory>());
				for (RaceDate raceDate : track.getRaceDates()) {
					if (raceDate.getHasChartFlag()) {
						System.out.println(track.getCode() + " " + raceDate.getRaceDate());
						List<RaceResult> raceResults = ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
						for (RaceResult raceResult : raceResults) {
							if (raceResult.getWinners().get(0).getRawSpeedRating() > 0) {
								Boolean found = false;
								String surface = 
									(raceResult.getDistanceSurfaceTrackRecord().getSurface().toUpperCase().contains("TURF")) ? "Turf" :
										raceResult.getDistanceSurfaceTrackRecord().getSurface();
								Boolean isRoute =
									raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() < 5280;
								if (track.getRaceCategories() != null)
									for (RaceCategory rc : track.getRaceCategories()) {			
										if (rc.getCategory().equals(raceResult.getRaceConditions().getRaceCategory())
												&& rc.getSurface().equals(surface)
												&& rc.getIsRoute().equals(isRoute)
												&& (raceResult.getRaceConditions().getClaimingPriceRange() != null
													&& raceResult.getRaceConditions().getClaimingPriceRange().getMin() >= rc.getClaimingMin()
													&& raceResult.getRaceConditions().getClaimingPriceRange().getMin() < rc.getClaimingMax()
													|| (raceResult.getPurse().getValue() >= rc.getPurseMin()
															&& raceResult.getPurse().getValue() < rc.getPurseMax()))
											) {
											rc.setRaceCount(rc.getRaceCount() + 1);
											found = true;
										}
									}
								if (!found) {
									RaceCategory raceCategory = RaceCategory.builder()
											.withRaceCategory(raceResult.getRaceConditions().getRaceCategory())
											.withSurface(surface)
											.withIsRoute(isRoute)
											.withRaceCount(1)
											.build();
									
									if (raceResult.getRaceConditions().getClaimingPriceRange() != null) {
										Document query = new Document("claimingMin", new Document("$lte", raceResult.getRaceConditions().getClaimingPriceRange().getMin()))
											.append("claimingMax", new Document("$gt", raceResult.getRaceConditions().getClaimingPriceRange().getMin()));
										try {
										MongoCollection<Document> collection = database.getCollection("claimingRaceLevels");
										Document level = collection.find(query).first();
										raceCategory.setClaimingMin(level.getInteger("claimingMin"));
										raceCategory.setClaimingMax(level.getInteger("claimingMax"));
										} catch (Exception e) {
											System.out.println(query);
											throw(e);
										}
										
									} else {
										Document query = new Document("purseMin", new Document("$lte", raceResult.getPurse().getValue()))
											    .append("purseMax", new Document("$gt", raceResult.getPurse().getValue()));
	
										MongoCollection<Document> collection = database.getCollection("nonClaimingRaceLevels");
										Document level = collection.find(query).first();
										raceCategory.setPurseMin(level.getInteger("purseMin"));
										raceCategory.setPurseMax(level.getInteger("purseMax"));
									}
									
									List<RaceCategory> categories = 
										(track.getRaceCategories() == null) ?
											new ArrayList<RaceCategory>() :
											track.getRaceCategories();
									
									categories.add(raceCategory);
									track.setRaceCategories(categories);
								}
							}
						}
					}
				}
				track.save();
			}
			
			
			System.out.println("Done.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePars() {
		
		try {
			List<Track> tracks = getTracksList();
			for (Track track : tracks) {
				for (RaceCategory raceCategory : track.getRaceCategories()) {
					System.out.println(track.getCode() + " " + raceCategory.getCategory());
					List<RaceResult> raceResults = new ArrayList<RaceResult>();
					
					Document query = new Document("track.code", track.getCode());
					if (raceCategory.getClaimingMax() > 0) {
						query.append("raceConditions.claimingPriceRange.min", new Document("$gte", raceCategory.getClaimingMin())
						    .append("$lt", raceCategory.getClaimingMax()))
							.append("raceConditions.raceTypeNameBlackTypeBreed.raceType", raceCategory.getCategory().split(" ")[0]);	
					} else {
						query.append("purse.value", new Document("$gte", raceCategory.getPurseMin())
						.append("$lt", raceCategory.getPurseMax()))
						.append("raceConditions.raceTypeNameBlackTypeBreed.raceType", raceCategory.getCategory().split(" ")[0]);						
					}
					
					if (raceCategory.getSurface().equals("Turf"))
						 query.append("distanceSurfaceTrackRecord.surface", Pattern.compile("turf(?i)"));
					else query.append("distanceSurfaceTrackRecord.surface", raceCategory.getSurface());
					
					if (raceCategory.getIsRoute()) 
						query.append("distanceSurfaceTrackRecord.raceDistance.value", new Document("$lt", 5280));
					else query.append("distanceSurfaceTrackRecord.raceDistance.value", new Document("$gte", 5280));
					
					MongoCollection<RaceResult> collection = database.getCollection("raceResults", RaceResult.class);
					FindIterable<RaceResult> iterable = collection.find(query);
					iterable.into(raceResults);
					if (raceResults.size() == 0) {
						System.out.println(query.toString());
						//throw(new Exception("No result found."));
					}
					if (raceResults.size() > 0) {
						int count = 0;
						int total = 0;
						int min = 999;
						int max = 0;
						for (RaceResult raceResult : raceResults) {
							if (raceResult.getRaceConditions().getRaceCategory().equals(raceCategory.getCategory()) 
									&& raceResult.getWinners().get(0).getRawSpeedRating() > 0) {
								count++;
								total += raceResult.getWinners().get(0).getRawSpeedRating();
								if (raceResult.getWinners().get(0).getRawSpeedRating() > max) max = raceResult.getWinners().get(0).getRawSpeedRating();
								if (raceResult.getWinners().get(0).getRawSpeedRating() < min) min = raceResult.getWinners().get(0).getRawSpeedRating();
							}
						}
						raceCategory.setRaceCount(count);
						raceCategory.setMaxSpeedRating(max);
						raceCategory.setMinSpeedRating(min);
						raceCategory.setParSpeedRating((int) Math.ceil((double)total / count));
					}
				}
				track.save();
			}
			
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateSpeedVariants() throws Exception {
			
		try {
			System.out.println("Updating Variants");
			List<Track> tracks = getTracksList();
			
			for (Track track : tracks) {
				for (RaceDate raceDate : track.getRaceDates()) {
					if (raceDate.getHasChartFlag()) {
						System.out.println(track.getCode() + " " + raceDate.getRaceDate().toString());
						List<RaceResult> raceResults =  ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
						raceDate.setSpeedVariantDirt(ProcessChart.calculateSpeedVariant(raceResults, "Dirt"));
						raceDate.setSpeedVariantTurf(ProcessChart.calculateSpeedVariant(raceResults, "Turf"));
						raceDate.setSpeedVariantAllWeather(ProcessChart.calculateSpeedVariant(raceResults, "All Weather Track"));
					}
				}
				track.save();
			}
						
			System.out.println("Done");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateKeyRaces() throws Exception {
		
		try {
			System.out.println("Updating Key Races");
			List<Track> tracks = getTracksList();
			
			for (Track track : tracks) {
				for (RaceDate raceDate : track.getRaceDates()) {
					if (raceDate.getHasChartFlag()) {
						System.out.println(track.getCode() + " " + raceDate.getRaceDate().toString());
						List<RaceResult> raceResults =  ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
						ProcessChart.updateKeyRaces(raceResults);
					}
				}
				track.save();
			}
						
			System.out.println("Done");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String updatePPs () throws Exception {
		
		try {
			for (Race race : card.getRaces()) {
				for (Entry entry : race.getEntries()) {
					for (PastPerformance pp : entry.getPastPerformances()) {
						System.out.println(pp.getTrackCode() + " " + pp.getName() + " " + pp.getRaceDateString());
						pp.addHorse();
						pp.addKeyRace();
						pp.addSpeedRatings();
						pp.save();
					}
				}
			};		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapper.writeValueAsString(card);
	}
	
	public static void script () throws Exception {
		
		try {
			
			MongoCollection<PastPerformance> ppcollection = database.getCollection("pastPerformances", PastPerformance.class);
			FindIterable<PastPerformance> iterable = ppcollection.find();
			iterable.forEach(pp -> {
				System.out.println(pp.getTrackCode() + " " + pp.getName() + " " + pp.getRaceDateString());
				pp.addHorse();
				pp.addKeyRace();
				pp.save();
			});		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
