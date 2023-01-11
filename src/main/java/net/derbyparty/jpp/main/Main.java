package net.derbyparty.jpp.main;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.derbyparty.jpp.chart.ProcessChart;
import net.derbyparty.jpp.chartparser.ChartParser;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.factors.Factors;
import net.derbyparty.jpp.factors.Ratings;
import net.derbyparty.jpp.loader.Loader;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.HorseToWatch;
import net.derbyparty.jpp.object.MultiRaceWager;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.PotentialKeyRace;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceNote;
import net.derbyparty.jpp.object.Track;
import net.derbyparty.jpp.pastperformanceparser.PastPerformanceParser;

public class Main {

	static List<Race> races;
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	static String distanceOption = "all";
	static String surfaceOption = "all";
	static String conditionOption = "all";
	
	final static String dir = "/Users/ahonaker/Google Drive/pp/jpp/";
	final static String horsesToWatchFile = "/Users/ahonaker/Google Drive/pp/jpp/horsesToWatch.json";
	final static String tracksFile = "/Users/ahonaker/Google Drive/pp/jpp/tracks.json";

	public static void updateOptions(String dOption, String sOption, String cOption) throws Exception {
		distanceOption = dOption;
		surfaceOption = sOption;
		conditionOption = cOption;
	}
	
	public static String load(String filename) throws Exception {
		
		try {
			races = Loader.get(dir + filename);
			getChanges();
		} catch (Exception e) {
			throw e;
		}
		
		return getAll();
	}
	
	public static void save(String filename) throws Exception {
		
		try {
			mapper.writeValue(Paths.get(dir + filename).toFile(), races);
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static String retrieve(String filename) throws Exception {
		
		try {
			races = Arrays.asList(mapper.readValue(Paths.get(dir + filename).toFile(), Race[].class));
			return getAll();
		} catch (Exception e) {
			throw e;
		}

	}
		
	public static String getAll() throws Exception {
		
		addKeyRacesToPP();
		addHorsesToWatchToPP();
		return mapper.writeValueAsString(races);
	}
	
	public static void addKeyRacesToPP() throws Exception {
		
		try {
			for (PotentialKeyRace keyRace : ProcessChart.getKeyRacesList()) {
				for (Race race : races) {
					for (Horse horse : race.getHorses()) {
						for (PastPerformance pp : horse.getPastPerformances()) {
							if (pp.getTrackCode().equals(keyRace.getTrack())
								&& pp.getRaceDate().equals(keyRace.getRaceDate())
								&& pp.getRaceNumber() == keyRace.getRaceNumber()) pp.setKeyRace(keyRace);
						}
					}
				}
			};
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void addHorsesToWatchToPP() throws Exception {
		
		try {
			for (HorseToWatch horseToWatch : getHorsesToWatchList()) {
				for (Race race : races) {
					for (Horse horse : race.getHorses()) {
						if (horse.getName().equals(horseToWatch.getName())) horse.setHorseToWatch(horseToWatch);
					}
				}
			};
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void getChanges () throws Exception {
		
		try {
		
			ObjectNode program = Loader.getDRF(races.get(0).getTrack(), races.get(0).getDate());
			for (JsonNode raceNode : program.get("races")) {
				for (Race race : races) {
					if (race.getRaceNumber() == raceNode.get("raceKey").get("raceNumber").asInt()) {				
						List<String> changes = new ArrayList<String>();
						for (JsonNode changeNode : raceNode.get("changes")) {	
							changes.add(changeNode.get("text").asText());
							if (changeNode.get("type").asText().equals("S")) {
								for (Horse horse : race.getHorses()) {
									if (horse.getName().equals(changeNode.get("text").asText().replace(" scratched", ""))) {
										horse.setScratchedFlag(true);
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
		
			ObjectNode program = Loader.getDRFResults(races.get(0).getTrack(), races.get(0).getDate());
			if (program.has("races")) {
				for (JsonNode raceNode : program.get("races")) {
					for (Race race : races) {
						if (race.getRaceNumber() == raceNode.get("raceKey").get("raceNumber").asInt()) {				
							
							for (JsonNode runnerNode : raceNode.get("runners")) {	
								for (Horse horse : race.getHorses()) {
									if (horse.getProgramNumber().equals(runnerNode.get("programNumber").asText().trim())) {
										horse.setWinPayout(runnerNode.get("winPayoff").floatValue());
										horse.setPlacePayout(runnerNode.get("placePayoff").floatValue());
										horse.setShowPayout(runnerNode.get("showPayoff").floatValue());
										if (runnerNode.get("winPayoff").floatValue() > 0) {
											horse.setFinishPosition(1);
										} else if (runnerNode.get("placePayoff").floatValue() > 0) {
											horse.setFinishPosition(2);
										} else if (runnerNode.get("showPayoff").floatValue() > 0) {
											horse.setFinishPosition(3);
										} else horse.setFinishPosition(0);
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
			PastPerformanceParser.extractPastPerformance(races, ppFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		
		
		} 
		return getAll();
	}
	
	public static String addProgramNumbers (String filename) throws Exception {
		
		try {
			List<Race> updatedRaces =  Loader.get(dir + filename);
			for (int i = 0; i < races.size(); i++) {
				for (int j = 0; j < races.get(i).getHorses().size(); j++) {
					races.get(i).getHorses().get(j).setProgramNumber(updatedRaces.get(i).getHorses().get(j).getProgramNumber());
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
				
				if (surfaceOption.equals("same") && race.getTurfFlag() != pp.getTurfFlag()) include = false;
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
			for (Race race : races) {
							
				for (Horse horse : race.getUnscratchedHorses()) {
					
					List<PastPerformance> pps = filterPastPerformances(race, horse.getUnignoredPastPerformances());
					
					for (int p = 0; p < pps.size(); p++) {
					
						PastPerformance pp = pps.get(p);						
						pp.setRaceStrength(Factors.CalcRaceStrength(pp));
						pp.setLast5AverageSpeed(Factors.CalcLast5AverageSpeed(p, pps));
						pp.setRaceShape(Factors.DetermineRaceShape(pp));
					}
					
					horse.setE1Avg(Factors.CalcE1Avg(pps));
					horse.setE2Avg(Factors.CalcE2Avg(pps));
					horse.setMaxE2(Factors.CalcMaxE2(pps));
					horse.setLatePaceAvg(Factors.CalcLatePaceAvg(pps));
					horse.setAvgAdjustedSpeedRating(Factors.CalcAverageAdjustedSpeedRating(pps));
					
					ObjectNode closingRatio = Factors.CalcClosingRatio(pps);
					if (closingRatio.hasNonNull("EarlyPosition")) horse.setEarlyPosition(closingRatio.get("EarlyPosition").floatValue());
					if (closingRatio.hasNonNull("LatePosition")) horse.setLatePosition(closingRatio.get("LatePosition").floatValue());
					if (closingRatio.hasNonNull("ClosingRatio")) horse.setClosingRatio(closingRatio.get("ClosingRatio").floatValue());
					
					horse.setLatePaceBestLast3(Factors.CalcLatePaceBestLast3(pps));
					horse.setPaceAdjustedLate(Factors.CalcPaceAdjustedLate(pps));
					horse.setLatePaceLast(Factors.CalcLatePaceLast(pps));
					
					horse.setClassRating((Factors.CalcClassRating(pps, race)));
					horse.setAverageCompetitiveLevel((Factors.CalcAverageCompetitiveLevel(pps, race)));
					horse.setLastRaceStrength(Factors.CalcLastRaceStrength(pps));
					
					horse.setClassShift(Factors.CalcClassShift(pps, race));
					horse.setPurseShift(Factors.CalcPurseShift(pps, race));
					
					horse.setSpeedRating(Factors.CalcSpeedRating(pps, race));
					
					horse.setBasicFitness(Factors.CalcBasicFitness(pps, horse.getWorkouts()));
					horse.setFormPoints(Factors.CalcFormPoints(pps));
					horse.setFurlongDays(Factors.CalcFurlongDays(pps, horse.getWorkouts(), race));
					horse.setTurnTime(Factors.CalcTurnTime(pps));
					
					horse.setARatingClass(Ratings.calcARatingClass(race, horse));
					horse.setARatingForm(Ratings.calcARatingForm(race, horse));
					horse.setARatingConnections(Ratings.calcARatingConnections(race, horse));
					horse.setARating(Ratings.calcARating(race, horse));
					
				}
				
				race.setPaceScenario(Factors.DeterminePaceScenario(race.getUnscratchedHorses()));
				race.setE1Avg(Factors.CalcRaceE1Avg(race.getUnscratchedHorses()));
				race.setE2Avg(Factors.CalcRaceE2Avg(race.getUnscratchedHorses()));
				race.setMaxE2(Factors.CalcRaceMaxE2(race.getUnscratchedHorses()));
				race.setLatePaceBestLast3(Factors.CalcRaceLatePaceBestLast3(race.getUnscratchedHorses()));
				race.setMaxSpeedRating(Factors.CalcRaceMaxSpeedRating(race.getUnscratchedHorses()));
				race.setMaxSpeed(Factors.CalcRaceMaxSpeed(race.getUnscratchedHorses()));
				race.setAverageAdjustedSpeed(Factors.CalcRaceAverageAdjustedSpeedRating(race.getUnscratchedHorses()));
				race.setTotalSpeedPoints(Factors.CalcTotalSpeedPoints(race.getUnscratchedHorses()));
				
				race.setAdvantagedHorses(Ratings.identifyPaceAdvantage(race));
				race.setHandicappingNotes(Factors.GenerateHandicappingNotes(race));
				
				for (Horse horse : race.getUnscratchedHorses()) {
					horse.setAngles(Factors.GenerateAngles(race, horse));	
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
			
			for (Race race : races) {
				float maxARating = 0;
				for (Horse horse : race.getUnscratchedHorses()) {
					if (horse.getARating() > maxARating && horse.getClassRating() > 0) maxARating = horse.getARating();
				}
				float total = 0;
				for (Horse horse : race.getUnscratchedHorses()) {
					if (horse.getClassRating() > 0) total += (1 / (maxARating - horse.getARating() + 1));
				}
				for (Horse horse : race.getUnscratchedHorses()) {
					if (horse.getClassRating() > 0) {
						horse.setARatingFairValue(1 / 
							((1 / (maxARating - horse.getARating() + 1)) / total)
							- 1);
					} else {
						horse.setARatingFairValue(0);
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleScratch(int raceNumber, String programNumber) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.setScratchedFlag(!horse.getScratchedFlag());
							if (horse.getScratchedFlag()) horse.setSelection("X");
							if (horse.getScratchedFlag()) horse.setPick(false);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void togglePick(int raceNumber, String programNumber) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.setPick(!horse.getPick());
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void toggleShowDetail(int raceNumber, String programNumber) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.set_showDetails(!horse.get_showDetails());
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}	
	
	public static void toggleIgnored(int raceNumber, String programNumber, LocalDate raceDate) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							for (PastPerformance pp : horse.getPastPerformances()) {
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
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					race.setOffTheTurfFlag(!race.getOffTheTurfFlag());
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void setTrackCondition(int raceNumber, String condition) throws Exception {
		
		try {
			for (Race race : races) {
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
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					race.setNote(note);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void setHorseNote(int raceNumber, String programNumber, String note) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.setNote(note);
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
	
	public static void setBettingLine(int raceNumber, String programNumber, float bettingLine) throws Exception {
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.setBettingLine(bettingLine);
						}
					}
				}
			}			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String setSelection(int raceNumber, String programNumber, String selection) throws Exception {
		
		ArrayNode results = mapper.createArrayNode();
		
		try {
			for (Race race : races) {
				if (race.getRaceNumber() == raceNumber) {
					for (Horse horse : race.getHorses()) {
						if (horse.getProgramNumber().equals(programNumber)) {
							horse.setSelection(selection);
							
						}
					}
				}
			}
			
			for (Race race : races) {
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
						
						for (Horse horse : races.get(i).getUnscratchedHorses()) {
							if (horse.getSelection().equals("A")) {
								if (!aHorses.contains(horse.getProgramNumber())) aHorses.add(horse.getProgramNumber());
								if (!abHorses.contains(horse.getProgramNumber())) abHorses.add(horse.getProgramNumber());
								
							}
							if (horse.getSelection().equals("B")) {
								if (!abHorses.contains(horse.getProgramNumber())) abHorses.add(horse.getProgramNumber());
								if (!bHorses.contains(horse.getProgramNumber())) bHorses.add(horse.getProgramNumber());
							}
							if (horse.getSelection().equals("C")) {
								if (!cHorses.contains(horse.getProgramNumber())) cHorses.add(horse.getProgramNumber());
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
											b2combo.add(bPicks.get(j));
										} else {
											b2combo.add(aPicks.get(j));
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
	
	public static List<HorseToWatch> getHorsesToWatchList() throws Exception {
		try {
			return new ArrayList<HorseToWatch>(Arrays.asList(mapper.readValue(Paths.get(horsesToWatchFile).toFile(), HorseToWatch[].class)));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void saveHorsesToWatch(List<HorseToWatch> horsesToWatch) throws Exception {
		try {
			mapper.writeValue(Paths.get(horsesToWatchFile).toFile(), horsesToWatch);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String getHorsesToWatch() throws Exception {
		try {
			return mapper.writeValueAsString(getHorsesToWatchList());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void addHorseToWatch(HorseToWatch horse) throws Exception {
		try {
			List<HorseToWatch> horsesToWatch = new ArrayList<HorseToWatch>(getHorsesToWatchList());
			horsesToWatch.add(horse);
			mapper.writeValue(Paths.get(horsesToWatchFile).toFile(), horsesToWatch);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void removeHorseToWatch(HorseToWatch horse) throws Exception {
		try {
			List<HorseToWatch> horsesToWatch = new ArrayList<HorseToWatch>(getHorsesToWatchList());
			horsesToWatch.remove(horse);
			mapper.writeValue(Paths.get(horsesToWatchFile).toFile(), horsesToWatch);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void updateHorseToWatch(HorseToWatch horse) throws Exception {
		try {
			List<HorseToWatch> horsesToWatch = new ArrayList<HorseToWatch>(getHorsesToWatchList());
			for (HorseToWatch horseToWatch : horsesToWatch) {
				if (horse.getName().equals(horseToWatch.getName())) {
					horsesToWatch.remove(horseToWatch);
					horsesToWatch.add(horse);
					break;
				}
			}
			mapper.writeValue(Paths.get(horsesToWatchFile).toFile(), horsesToWatch);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void saveNotes(JsonNode data) throws Exception {
		
		try {
			List<HorseToWatch> horsesToWatch = new ArrayList<HorseToWatch>(getHorsesToWatchList());
			for (JsonNode race : data) {
				LocalDate raceDate = LocalDate.of(race.get("raceDate").get(0).asInt(), race.get("raceDate").get(1).asInt(), race.get("raceDate").get(2).asInt());
				for (JsonNode starter : race.get("starters")) {
					Boolean horseFound = false;
					for (HorseToWatch horseToWatch : horsesToWatch) {
						if (horseToWatch.getName().equals(starter.get("name").asText())) {
							horseFound = true;
							if (!starter.get("horseFlag").isNull()) horseToWatch.setFlag(starter.get("horseFlag").asText());
							Boolean raceFound = false;
							for (RaceNote raceNote : horseToWatch.getRaceNotes()) {
								
								if (raceNote.getTrack().equals(race.get("track").asText()) 
									&& raceDate.equals(raceNote.getRaceDate())
									&& raceNote.getRaceNumber() == race.get("raceNumber").asInt()) {
										raceFound = true;
										raceNote.setComment(!starter.get("note").isNull() ? starter.get("note").asText() : "");
										raceNote.setFlag(!starter.get("raceFlag").isNull() ? starter.get("raceFlag").asText() : "");
								}
							}
							if (!raceFound) {
								List<RaceNote> raceNotes = new ArrayList<RaceNote>(horseToWatch.getRaceNotes());
								raceNotes.add(RaceNote.builder()
									.withTrack(race.get("track").asText())
									.withRaceDate(raceDate)
									.withRaceNumber(race.get("raceNumber").asInt())
									.withPosition(starter.get("position").asInt())
									.withBeatenLengths(starter.get("beatenLengths").floatValue())
									.withComment(!starter.get("note").isNull()? starter.get("note").asText() : "")
									.withFlag(!starter.get("raceFlag").isNull() ? starter.get("raceFlag").asText() : "")
									.build());
								horseToWatch.setRaceNotes(raceNotes);
							}
						}
					}
					if (!horseFound) {
						List<RaceNote> raceNotes = new ArrayList<RaceNote>();
						raceNotes.add(RaceNote.builder()
							.withTrack(race.get("track").asText())
							.withRaceDate(raceDate)
							.withRaceNumber(race.get("raceNumber").asInt())
							.withPosition(starter.get("position").asInt())
							.withBeatenLengths(starter.get("beatenLengths").floatValue())
							.withComment(!starter.get("note").isNull() ? starter.get("note").asText() : "")
							.withFlag(!starter.get("raceFlag").isNull() ? starter.get("raceFlag").asText() : "")
							.build());
						horsesToWatch.add(HorseToWatch.builder()
							.withName(starter.get("name").asText())
							.withFlag(!starter.get("horseFlag").isNull() ? starter.get("horseFlag").asText() : "")
							.withRaceNotes(raceNotes)
							.build());
					}
				}
			}
			mapper.writeValue(Paths.get(horsesToWatchFile).toFile(), horsesToWatch);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static List<Track> getTracksList() throws Exception {
		try {
			return new ArrayList<Track>(Arrays.asList(mapper.readValue(Paths.get(tracksFile).toFile(), Track[].class)));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void saveTracks(List<Track> tracks) throws Exception {
		try {
			mapper.writeValue(Paths.get(tracksFile).toFile(), tracks);
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
	
}
