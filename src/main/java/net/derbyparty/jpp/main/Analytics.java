package net.derbyparty.jpp.main;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.derbyparty.jpp.chart.ProcessChart;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.factors.Angles;
import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.Angle;
import net.derbyparty.jpp.object.Card;
import net.derbyparty.jpp.object.Entry;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceDate;
import net.derbyparty.jpp.object.SexRestrictionType;
import net.derbyparty.jpp.object.Track;

public class Analytics {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
//	final static String saveDir = "/Users/ahonaker/Google Drive/pp/jpp/saveDir/";
//	final static String anglesFile = "/Users/ahonaker/Google Drive/pp/jpp/angles.json";
//	final static String raceTimesFile = "/Users/ahonaker/Google Drive/pp/jpp/raceTimes.csv";
//	final static String rawTimesFile = "/Users/ahonaker/Google Drive/pp/jpp/rawTimes.csv";
//	final static String statsFile = "/Users/ahonaker/Google Drive/pp/jpp/stats.csv";
//	final static String raceStatsFile = "/Users/ahonaker/Google Drive/pp/jpp/raceStats.csv";
//	final static String angleStatsFile = "/Users/ahonaker/Google Drive/pp/jpp/angleStats.csv";
//	final static String combo2StatsFile = "/Users/ahonaker/Google Drive/pp/jpp/combo2Stats.csv";
//	final static String combo3StatsFile = "/Users/ahonaker/Google Drive/pp/jpp/combo3Stats.csv";
//	final static String anglesUpateFile = "/Users/ahonaker/Google Drive/pp/jpp/anglesUpdate.csv";
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);

	
	public static ArrayNode generateRawTimes() throws Exception {
		
		try {
			ArrayNode times = mapper.createArrayNode();
			List<Track> tracks = Main.getTracksList();
			for (Track track : tracks) {
				for (RaceDate raceDate : track.getRaceDates()) {
					if (raceDate.getHasChartFlag()) {
						List<RaceResult> raceResults = ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
						for (RaceResult raceResult : raceResults) {
							System.out.println(raceResult.getTrack().getCode() + raceResult.getRaceDate().toString() + " " +raceResult.getRaceNumber());
							if (raceResult.getFractionals().size() > 0) {
								float furlongs = (float) raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3f / 220f;
								ObjectNode race = mapper.createObjectNode();
								race.put("track", raceResult.getTrack().getCode());
								race.put("date", raceResult.getRaceDate().toString());
								race.put("raceNumber", raceResult.getRaceNumber());
								race.put("raceType", raceResult.getRaceConditions().getRaceTypeNameBlackTypeBreed().getRaceType().toString());
								race.put("raceClassification", raceResult.getRaceConditions().getRaceClassification());
								race.put("furlongs", furlongs);
								race.put("distance", raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3);
								race.put("surface", raceResult.getDistanceSurfaceTrackRecord().getSurface());
								race.put("condition", raceResult.getDistanceSurfaceTrackRecord().getTrackCondition());
								race.put("finalTime", (Double.valueOf(raceResult.getFractionals().get(raceResult.getFractionals().size()-1).getMillis()))/1000);
								race.put("claimingRangeMin", raceResult.getRaceConditions().getClaimingPriceRange() == null ? null : raceResult.getRaceConditions().getClaimingPriceRange().getMin());
								race.put("claimingRangeMax", raceResult.getRaceConditions().getClaimingPriceRange() == null ? null : raceResult.getRaceConditions().getClaimingPriceRange().getMax());
								race.put("purse", raceResult.getPurse().getValue());
								race.put("filliesAndMares", raceResult.getRaceConditions().getSexRestrictionType().equals(SexRestrictionType.FILLIES) || raceResult.getRaceConditions().getSexRestrictionType().equals(SexRestrictionType.FILLIES_AND_MARES) ? "F" : "");
								race.put("age", 
										(raceResult.getRaceConditions().getAgeRestrictionType().equals(AgeRestrictionType.TWO_YEAR_OLDS) && raceResult.getRaceConditions().getAgeRestrictionRangeType().equals(AgeRestrictionRangeType.THAT_AGE_ONLY) )
										? "2" :
											(raceResult.getRaceConditions().getAgeRestrictionType().equals(AgeRestrictionType.THREE_YEAR_OLDS) && raceResult.getRaceConditions().getAgeRestrictionRangeType().equals(AgeRestrictionRangeType.THAT_AGE_ONLY))
											? "3" : "");
								times.add(race);
							}
						}
					}
				}
			}
			System.out.println("Done generating raw times.");
			return times;
			
		} catch (Exception e) {
			throw e;
		}	
	}
	
//	public static void generateRawTimesCSV() throws Exception {
//		
//		try {
//			JsonNode node = mapper.valueToTree(generateRawTimes());
//			
//			Builder csvSchemaBuilder = CsvSchema.builder();
//			JsonNode firstObject = node.elements().next();
//			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//			
//			CsvMapper csvMapper = new CsvMapper();
//			csvMapper.writerFor(JsonNode.class)
//			  .with(csvSchema)
//			  .writeValue(new File(rawTimesFile), node);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
	
//	public static List<RaceTime> generateRaceTimes() throws Exception {
//		
//		try {
//    		Pattern pattern = Pattern.compile("([A-Z]+)(\\d{2})(\\d{2})(\\d{4})");
//			List<RaceTime> raceTimes = new ArrayList<RaceTime>();
//			
//			Files.list(new File(saveDir).toPath())
//            .forEach(path -> {
//            	if (path.toString().contains(".json")) {
//            		Matcher matcher =  pattern.matcher(path.toString());
//            		if (matcher.find()) {  
//            			try {
//            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + matcher.group(1) + LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))).format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
//            				for (Race race : card) {
//            					for (Entry entry : race.getEntries()) {
//            						for (PastPerformance pp : entry.getPastPerformances()) {
//            							RaceTime raceTime = RaceTime.builder()
//            								.withTrack(pp.getTrackCode())
//            								.withRaceDate(pp.getRaceDate())
//            								.withRaceNumber(pp.getRaceNumber())
//            								.withRaceType(pp.getRaceType())
//            								.withRaceClassification(pp.getRaceClassification())
//            								.withDistance(pp.getDistance())
//            								.withSurface(pp.getSurface())
//            								.withAllWeatherFlag(pp.getAllWeatherSurfaceFlag())
//            								.withTrackCondition(pp.getTrackCondition())
//            								.withFraction1(pp.getFraction1())
//            								.withFraction2(pp.getFraction2())
//            								.withFraction3(pp.getFraction3())
//            								.withFinalTime(pp.getFinalTime())
//            								.withSpeedPar(pp.getSpeedPar())
//            								.withRaceShapeFirstCall(pp.getRaceShapeFirstCall())
//            								.withRaceShapeSecondCall(pp.getRaceShapeSecondCall())
//            								.build();
//            							if (!raceTimes.contains(raceTime)) raceTimes.add(raceTime);
//            						}
//            					}
//            				}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//            			
//            		}
//            	}
//            });
//			
//			Files.list(new File(saveDir).toPath())
//            .forEach(path -> {
//            	if (path.toString().contains(".json")) {
//            		Matcher matcher =  pattern.matcher(path.toString());
//            		if (matcher.find()) {  
//            			try {
//            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + matcher.group(1) + LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))).format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
//            				for (Race race : card) {
//            					for (Entry entry : race.getEntries()) {
//            						for (PastPerformance pp : entry.getPastPerformances()) {
//            							if (pp.getFinishBeatenLengthsOnly() == 0) {
//            								for (RaceTime raceTime : raceTimes) {
//            									if (raceTime.getTrack().equals(race.getTrack())
//            										&& raceTime.getRaceDate().equals(race.getDate())
//            										&& raceTime.getRaceNumber() == race.getRaceNumber()) {
//            										raceTime.setWinnerPar(pp.getBRISSpeedRating());
//            										raceTime.setPaceFigure2F(pp.getPaceFigure2F());
//            										raceTime.setPaceFigure4F(pp.getPaceFigure4F());
//            										raceTime.setPaceFigure6F(pp.getPaceFigure6F());
//            										raceTime.setPaceFigure8F(pp.getPaceFigure8F());
//            										raceTime.setPaceFigure10F(pp.getPaceFigure10F());
//            										raceTime.setPaceFigureLate(pp.getPaceFigureLate());
//            										break;
//            									}
//            								}
//            							}
//            						}
//            					}
//            				}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//            			
//            		}
//            	}
//            });
//			
//			List<Track> tracks = Main.getTracksList();
//			for (Track track : tracks) {
//				for (RaceDate raceDate : track.getRaceDates()) {
//					if (raceDate.getHasChartFlag()) {
//						List<RaceResult> raceResults = ProcessChart.getChart(track.getCode(), raceDate.getRaceDate());
//						for (RaceResult raceResult : raceResults) {
//							System.out.println(raceResult.getTrack().getCode() + raceResult.getRaceDate().toString() + " " +raceResult.getRaceNumber());
//							Boolean found = false;
//							for (RaceTime raceTime : raceTimes) {
//								if (raceTime.getTrack().equals(raceResult.getTrack().getCode())
//									&& raceTime.getRaceDate().equals(raceResult.getRaceDate())
//									&& raceTime.getRaceNumber() == raceResult.getRaceNumber()) {
//									found = true;
//								}
//							}
//							if (!found && raceResult.getFractionals().size() > 0) {
//								float furlongs = (float) raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3f / 220f;
//								RaceTime raceTime = RaceTime.builder()
//    								.withTrack(raceResult.getTrack().getCode())
//    								.withRaceDate(raceResult.getRaceDate())
//    								.withRaceNumber(raceResult.getRaceNumber())
//    								.withRaceType(raceResult.getRaceConditions().getRaceTypeNameBlackTypeBreed().getRaceType())
//    								.withRaceClassification(raceResult.getRaceConditions().getRaceClassification())
//    								.withDistance(raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3)
//    								.withSurface(raceResult.getDistanceSurfaceTrackRecord().getSurface().equals("Turf") ? SurfaceType.TURF : SurfaceType.DIRT)
//    								.withAllWeatherFlag(raceResult.getDistanceSurfaceTrackRecord().getSurface().equals("All Weather Track") ? "A" : "")
//    								.withTrackCondition(raceResult.getDistanceSurfaceTrackRecord().getTrackCondition())
//    								.withFraction1( 
//    										(furlongs <= 9 ? raceResult.getFractionals().get(0).getMillis() : raceResult.getFractionals().get(1).getMillis())/1000
//    									)
//    								.withFraction2(
//    										(furlongs <= 9 ? raceResult.getFractionals().get(1).getMillis() : 
//    											furlongs <= 12 ? raceResult.getFractionals().get(2).getMillis() :
//    												furlongs == 13 ? raceResult.getFractionals().get(3).getMillis() : 
//    													furlongs <= 15 ? raceResult.getFractionals().get(4).getMillis() :
//    														raceResult.getFractionals().get(5).getMillis()) / 1000
//    									)
//    								.withFraction3(
//    										(furlongs <= 5 ? 0 : 
//    											furlongs <= 9 ? raceResult.getFractionals().get(2).getMillis() :
//    												furlongs == 11 ? raceResult.getFractionals().get(3).getMillis() : 
//    													furlongs <= 13 ? raceResult.getFractionals().get(4).getMillis() :
//    														furlongs == 14 ? raceResult.getFractionals().get(5).getMillis() :
//    															raceResult.getFractionals().get(6).getMillis()) / 1000
//    									)
//    								.withFinalTime((raceResult.getFractionals().get(raceResult.getFractionals().size()-1).getMillis())/1000)
//									.build();
//								raceTimes.add(raceTime);
//							}
//						}
//					}
//				}
//			}
//			
//			return raceTimes;
//			//mapper.writeValue(Paths.get(raceTimesFile).toFile(), raceTimes);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//		
//	}
	
//	public static void generateRaceTimesCSV() throws Exception {
//		
//		try {
//			JsonNode node = mapper.valueToTree(generateRaceTimes());
//			
//			Builder csvSchemaBuilder = CsvSchema.builder();
//			JsonNode firstObject = node.elements().next();
//			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//			
//			CsvMapper csvMapper = new CsvMapper();
//			csvMapper.writerFor(JsonNode.class)
//			  .with(csvSchema)
//			  .writeValue(new File(raceTimesFile), node);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
	
	public static void generateAngleStats() throws Exception {
		
		try {
			System.out.println("Generating angle stats.");
			
			MongoCollection<Angle> collection = database.getCollection("angles", Angle.class);
			FindIterable<Angle> iterable = collection.find();
			
			iterable.forEach(angle -> {
				Document query = new Document()
						.append("angle", angle.getName());					
				
				MongoCollection<Document> angleStatCollection = database.getCollection("angleStats");
				FindIterable<Document> angleStatIterable = angleStatCollection.find(query);
				
				AtomicInteger total = new AtomicInteger(0);
				AtomicInteger winners =  new AtomicInteger(0);
				AtomicInteger ITM = new AtomicInteger(0);
				DoubleAdder payoff = new DoubleAdder();
				
				
				angleStatIterable.forEach(angleStat -> {
					total.getAndAdd(1);
					if (angleStat.getBoolean("winner")) {
						winners.getAndAdd(1);
						payoff.add(angleStat.getDouble("payoff"));
					}
					if (angleStat.getBoolean("ITM")) ITM.getAndAdd(1);
					
				});
				angle.setTotal(total.get());
				angle.setWinners(winners.get());
				angle.setWinPercent((float) winners.get()/total.get());
				angle.setItmPercent((float) ITM.get()/total.get());
				angle.setRoi((float) payoff.floatValue()/total.get());
				try {
					angle.save();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		
			System.out.println("Done generating angle stats.");

			
		} catch (Exception e) {
			throw e;
		}	
	}
	
	public static String getAngles() throws Exception {
		
		try {
			List<Angle> angles = new ArrayList<Angle>();
			MongoCollection<Angle> collection = database.getCollection("angles", Angle.class);
			FindIterable<Angle> iterable = collection.find();
			iterable.into(angles);
			return mapper.writeValueAsString(angles);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static ObjectNode createStat(RaceResult result, Starter starter) {
		
		ObjectNode stat = mapper.createObjectNode();
		stat.put("track",result.getTrack().getCode());
		stat.put("date", result.getRaceDate().toString());
		stat.put("raceNumber", result.getRaceNumber());
		stat.put("horse", starter.getHorse().getName());
		stat.put("trainer", starter.getTrainer().getName());
		stat.put("jockey", starter.getJockey().getName());
		stat.put("finishPosition", starter.getFinishPosition());
		stat.put("officialPosition", starter.getOfficialPosition());
		stat.put("winner", starter.getOfficialPosition() != null && starter.getOfficialPosition() == 1);
		stat.put("ITM", starter.getOfficialPosition() != null && starter.getOfficialPosition() <= 3);
		stat.put("odds", starter.getOdds());
		stat.put("win", (starter.getWinPlaceShowPayoff() == null || starter.getWinPlaceShowPayoff().getWin() == null) ? 0 : starter.getWinPlaceShowPayoff().getWin().getPayoff());
		return stat;
		
	}
	
	public static void generateStats() throws Exception {
		
		try {
			System.out.println("Generating rating stats.");
									
			AtomicInteger total = new AtomicInteger(0);
			AtomicInteger AMLGT4total =  new AtomicInteger(0);
			AtomicInteger AMLGT4 =  new AtomicInteger(0);
			AtomicInteger AOGT4total = new AtomicInteger(0);
			AtomicInteger AOGT4 = new AtomicInteger(0);
			AtomicInteger AOGT8total = new AtomicInteger(0);
			AtomicInteger AOGT8 = new AtomicInteger(0);
			AtomicInteger AOBT48total =  new AtomicInteger(0);
			AtomicInteger AOBT48 =  new AtomicInteger(0);
			AtomicInteger ARating = new AtomicInteger(0);
			AtomicInteger primePower = new AtomicInteger(0);
			AtomicInteger speedRating = new AtomicInteger(0);
			AtomicInteger classRating = new AtomicInteger(0);
			AtomicInteger brisAvgLast3Class = new AtomicInteger(0);
			AtomicInteger brisCurrentClass = new AtomicInteger(0);
			AtomicInteger ACL = new AtomicInteger(0);
			AtomicInteger ARatingForm = new AtomicInteger(0);
			AtomicInteger ARatingConnections = new AtomicInteger(0);
			AtomicInteger combinedPaceAvg = new AtomicInteger(0);
			
			DoubleAdder AMLGT4Payoff = new DoubleAdder();
			DoubleAdder AOGT4Payoff = new DoubleAdder();
			DoubleAdder AOGT8Payoff = new DoubleAdder();
			DoubleAdder AOBT48Payoff = new DoubleAdder();
			DoubleAdder ARatingPayoff = new DoubleAdder();
			DoubleAdder primePowerPayoff = new DoubleAdder();
			DoubleAdder speedRatingPayoff = new DoubleAdder();
			DoubleAdder classRatingPayoff = new DoubleAdder();
			DoubleAdder brisAvgLast3ClassPayoff = new DoubleAdder();
			DoubleAdder brisCurrentClassPayoff = new DoubleAdder();
			DoubleAdder ACLPayoff = new DoubleAdder();
			DoubleAdder ARatingFormPayoff = new DoubleAdder();
			DoubleAdder ARatingConnectionsPayoff = new DoubleAdder();
			DoubleAdder combinedPaceAvgPayoff = new DoubleAdder();
			
			MongoCollection<Card> cardCollection = database.getCollection("cards", Card.class);
			FindIterable<Card> cardIterable = cardCollection.find();
			cardIterable.forEach(card -> {
				for (Race race : card.getRaces()) {
					
					Document resultQuery = new Document()
						.append("raceNumber", race.getRaceNumber())
						.append("track.code", race.getTrack())
						.append("raceDate", race.getDate());					
					
					
					MongoCollection<Document> resultsCollection = database.getCollection("raceResults");
					Document raceResult = resultsCollection.find(resultQuery).first();
					
					if (raceResult != null) {
						
						total.getAndAdd(1);
						
						float maxARating = 0f;
    					float maxPrimePower = 0f;
    					float maxSpeedRating = 0f;
    					float maxClassRating = 0f;
    					float maxBrisAvgLast3Class = 0f;
    					float maxBrisCurrentClass = 0f;
    					float maxACL = 0f;
    					float maxARatingForm = 0f;
    					float maxARatingConnections = 0f;
    					float maxCombinedPaceAvg = 0f;
						
						for (Entry entry : race.getEntries()) {
							if (!entry.getScratchedFlag()) {
								if (entry.getARating() > maxARating) maxARating = entry.getARating();
	    						if (entry.getPrimePower() > maxPrimePower) maxPrimePower = entry.getPrimePower();
	    						if (entry.getSpeedRating() > maxSpeedRating) maxSpeedRating = entry.getSpeedRating();
	    						if (entry.getClassRating() > maxClassRating) maxClassRating = entry.getClassRating();
	    						if (entry.getBrisAvgLast3Class() > maxBrisAvgLast3Class) maxBrisAvgLast3Class = entry.getBrisAvgLast3Class();
	    						if (entry.getBrisCurrentClass() > maxBrisCurrentClass) maxBrisCurrentClass = entry.getBrisCurrentClass();
	    						if (entry.getAverageCompetitiveLevel() > maxACL) maxACL = entry.getAverageCompetitiveLevel();
	    						if (entry.getARatingForm() > maxARatingForm) maxARatingForm = entry.getARatingForm();
	    						if (entry.getARatingConnections() > maxARatingConnections) maxARatingConnections = entry.getARatingConnections();
	    						if (entry.getCombinedPaceAvg() > maxCombinedPaceAvg) maxCombinedPaceAvg = entry.getCombinedPaceAvg();
							}
						}
						
						for (Entry entry : race.getEntries()) {
							
							if (!entry.getScratchedFlag()) {
							
								Document starterQuery = new Document()
									.append("raceNumber", race.getRaceNumber())
									.append("track", race.getTrack())
									.append("date", race.getDate())
									.append("name", entry.getName())
									;
							
								MongoCollection<Document> startersCollection = database.getCollection("startersResults");
								Document starterResult = startersCollection.find(starterQuery).first();
								
								if (starterResult != null) {
									if (entry.getARating() == maxARating && maxARating > 0 && entry.getMLOdds() >= 4) {
										AMLGT4total.getAndAdd(1);
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 4.0D) {
										AOGT4total.getAndAdd(1);
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 8.0D) {
										AOGT8total.getAndAdd(1);
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 4.0D && starterResult.getDouble("odds") < 8.0D) {
										AOBT48total.getAndAdd(1);
									}
								}
								
								if (starterResult != null && starterResult.getBoolean("winner")) {
									if (entry.getARating() == maxARating && maxARating > 0) {
										ARating.getAndAdd(1);
										ARatingPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARating() == maxARating && maxARating > 0 && entry.getMLOdds() >= 4) {
										AMLGT4.getAndAdd(1);
										AMLGT4Payoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 4.0D) {
										AOGT4.getAndAdd(1);
										AOGT4Payoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 8.0D) {
										AOGT8.getAndAdd(1);
										AOGT8Payoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARating() == maxARating && maxARating > 0 && starterResult.getDouble("odds") >= 4.0D && starterResult.getDouble("odds") < 8.0D) {
										AOBT48.getAndAdd(1);
										AOBT48Payoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getPrimePower() == maxPrimePower && maxPrimePower > 0) {
										primePower.getAndAdd(1);
										primePowerPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getSpeedRating() == maxSpeedRating && maxSpeedRating > 0) {
										speedRating.getAndAdd(1);
										speedRatingPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getClassRating() == maxClassRating && maxClassRating > 0) {
										classRating.getAndAdd(1);
										classRatingPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getBrisAvgLast3Class() == maxBrisAvgLast3Class && maxBrisAvgLast3Class > 0) {
										brisAvgLast3Class.getAndAdd(1);
										brisAvgLast3ClassPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getBrisCurrentClass() == maxBrisCurrentClass && maxBrisCurrentClass > 0) {
										brisCurrentClass.getAndAdd(1);
										brisCurrentClassPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getAverageCompetitiveLevel() == maxACL && maxACL > 0) {
										ACL.getAndAdd(1);
										ACLPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARatingForm() == maxARatingForm && maxARatingForm > 0) {
										ARatingForm.getAndAdd(1);
										ARatingFormPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getARatingConnections() == maxARatingConnections && maxARatingConnections > 0) {
										ARatingConnections.getAndAdd(1);
										ARatingConnectionsPayoff.add(starterResult.getDouble("payoff"));
									}
									if (entry.getCombinedPaceAvg() == maxCombinedPaceAvg && maxCombinedPaceAvg > 0) {
										combinedPaceAvg.getAndAdd(1);
										combinedPaceAvgPayoff.add(starterResult.getDouble("payoff"));
									}
								}
							}
							
						}
					}
					
				}
			});

			Document stats = new Document()
				.append("date", new Date())
				.append("total", total)
				.append("ARating", ARating.get())
				.append("AMLGT4total", AMLGT4total.get())
				.append("AMLGT4", AMLGT4.get())
				.append("AOGT4total", AOGT4total.get())
				.append("AOGT4", AOGT4.get())
				.append("AOGT8total", AOGT8total.get())
				.append("AOGT8", AOGT8.get())
				.append("AOBT48total", AOBT48total.get())
				.append("AOBT48", AOBT48.get())
				.append("primePower", primePower.get())
				.append("speedRating", speedRating.get())
				.append("classRating", classRating.get())
				.append("brisAvgLast3Class", brisAvgLast3Class.get())
				.append("brisCurrentClass", brisCurrentClass.get())
				.append("ACL", ACL.get())
				.append("ARatingForm", ARatingForm.get())
				.append("ARatingConnections", ARatingConnections.get())
				.append("combinedPaceAvg", combinedPaceAvg.get())
				.append("ARatingPayoff", ARatingPayoff.doubleValue())
				.append("AMLGT4Payoff", AMLGT4Payoff.doubleValue())
				.append("AOGT4Payoff", AOGT4Payoff.doubleValue())
				.append("AOGT8Payoff", AOGT8Payoff.doubleValue())
				.append("AOBT48Payoff", AOBT48Payoff.doubleValue())
				.append("primePowerPayoff", primePowerPayoff.doubleValue())
				.append("speedRatingPayoff", speedRatingPayoff.doubleValue())
				.append("classRatingPayoff", classRatingPayoff.doubleValue())
				.append("brisAvgLast3ClassPayoff", brisAvgLast3ClassPayoff.doubleValue())
				.append("brisCurrentClassPayoff", brisCurrentClassPayoff.doubleValue())
				.append("ACLPayoff", ACLPayoff.doubleValue())
				.append("ARatingFormPayoff", ARatingFormPayoff.doubleValue())
				.append("ARatingConnectionsPayoff", ARatingConnectionsPayoff.doubleValue())
				.append("combinedPaceAvgPayoff", combinedPaceAvgPayoff.doubleValue());
			
			MongoCollection<Document> statCollection = database.getCollection("stats");
			statCollection.insertOne(stats);
			
			
			System.out.println("Rating Stat Generation Done; Races: " + total.get());
						
		} catch (Exception e) {
			throw e;
		}	
	}
	
	public static String getStats() throws Exception {
		
		try {
			MongoDatabase database = mongoClient.getDatabase("jpp");
			MongoCollection<Document> collection = database.getCollection("stats");
			AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$sort", 
			    new Document("date", -1L)), 
			    new Document("$addFields", 
			    new Document("ARatingPct", 
			    new Document("$divide", Arrays.asList("$ARating", "$total")))
			            .append("AMLGT4Pct", 
			    new Document("$divide", Arrays.asList("$AMLGT4", "$AMLGT4total")))
			            .append("AOGT4Pct", 
			    new Document("$divide", Arrays.asList("$AOGT4", "$AOGT4total")))
			            .append("AOGT8Pct", 
			    new Document("$divide", Arrays.asList("$AOGT8", "$AOGT8total")))
			            .append("AOBT48Pct", 
			    new Document("$divide", Arrays.asList("$AOBT48", "$AOBT48total")))
			            .append("primePowerPct", 
			    new Document("$divide", Arrays.asList("$primePower", "$total")))
			            .append("speedRatingPct", 
			    new Document("$divide", Arrays.asList("$speedRating", "$total")))
			            .append("classRatingPct", 
			    new Document("$divide", Arrays.asList("$classRating", "$total")))
			            .append("brisAvgLast3ClassPct", 
			    new Document("$divide", Arrays.asList("$brisAvgLast3Class", "$total")))
			            .append("brisCurrentClassPct", 
			    new Document("$divide", Arrays.asList("$brisCurrentClass", "$total")))
			            .append("ACLPct", 
			    new Document("$divide", Arrays.asList("$ACL", "$total")))
			            .append("ARatingFormPct", 
			    new Document("$divide", Arrays.asList("$ARatingForm", "$total")))
			            .append("ARatingConnectionsPct", 
			    new Document("$divide", Arrays.asList("$ARatingConnections", "$total")))
			            .append("combinedPaceAvgPct", 
			    new Document("$divide", Arrays.asList("$combinedPaceAvg", "$total")))
			            .append("ARatingROI", 
			    new Document("$divide", Arrays.asList("$ARatingPayoff", "$total")))
			            .append("AMLGT4ROI", 
			    new Document("$divide", Arrays.asList("$AMLGT4Payoff", "$AMLGT4total")))
			            .append("AOGT4ROI", 
			    new Document("$divide", Arrays.asList("$AOGT4Payoff", "$AOGT4total")))
			            .append("AOGT8ROI", 
			    new Document("$divide", Arrays.asList("$AOGT8Payoff", "$AOGT8total")))
			            .append("AOBT48ROI", 
			    new Document("$divide", Arrays.asList("$AOBT48Payoff", "$AOBT48total")))
			            .append("primePowerROI", 
			    new Document("$divide", Arrays.asList("$primePowerPayoff", "$total")))
			            .append("speedRatingROI", 
			    new Document("$divide", Arrays.asList("$speedRatingPayoff", "$total")))
			            .append("classRatingROI", 
			    new Document("$divide", Arrays.asList("$classRatingPayoff", "$total")))
			            .append("brisAvgLast3ClassROI", 
			    new Document("$divide", Arrays.asList("$brisAvgLast3ClassPayoff", "$total")))
			            .append("brisCurrentClassROI", 
			    new Document("$divide", Arrays.asList("$brisCurrentClassPayoff", "$total")))
			            .append("ACLROI", 
			    new Document("$divide", Arrays.asList("$ACLPayoff", "$total")))
			            .append("ARatingFormROI", 
			    new Document("$divide", Arrays.asList("$ARatingFormPayoff", "$total")))
			            .append("ARatingConnectionsROI", 
			    new Document("$divide", Arrays.asList("$ARatingConnectionsPayoff", "$total")))
			            .append("combinedPaceAvgROI", 
			    new Document("$divide", Arrays.asList("$combinedPaceAvgPayoff", "$total"))))));

			return mapper.writeValueAsString(result.first());
			
		} catch (Exception e) {
			throw e;
		}
	}
	
//	public static void generateStatsCSV() throws Exception {
//		
//		try {
//			JsonNode node = mapper.valueToTree(generateStats());
//			
//			Builder csvSchemaBuilder = CsvSchema.builder();
//			JsonNode firstObject = node.elements().next();
//			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//			
//			CsvMapper csvMapper = new CsvMapper();
//			csvMapper.writerFor(JsonNode.class)
//			  .with(csvSchema)
//			  .writeValue(new File(statsFile), node);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
	
//	public static ArrayNode generateRaceStats() throws Exception {
//		
//		try {
//			ArrayNode stats = mapper.createArrayNode();	
//			
//			for (Track track : ProcessChart.getChartsArray()) {
//				for (RaceDate raceDate : track.getRaceDates()) {
//					if (raceDate.getHasChartFlag()) {
//						for (RaceResult raceResult : ProcessChart.getChart(track.getCode(), raceDate.getRaceDate())) {
//							float furlongs = (float) raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3f / 220f;
//							ObjectNode stat = mapper.createObjectNode();
//							stat.put("track", track.getCode());
//							stat.put("raceDate", raceDate.getRaceDate().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")));
//							stat.put("raceNumber", raceResult.getRaceNumber());
//							stat.put("raceType", raceResult.getRaceConditions().getRaceTypeNameBlackTypeBreed().getRaceType().toString());
//							stat.put("purse", raceResult.getPurse().getValue());
//							stat.put("surface", raceResult.getDistanceSurfaceTrackRecord().getSurface());
//							stat.put("distance", raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() / 3);
//							stat.put("trackCondition", raceResult.getDistanceSurfaceTrackRecord().getTrackCondition());
//							stat.put("winnerLastTrack", raceResult.getWinners().get(0).getLastRaced() == null ? null : raceResult.getWinners().get(0).getLastRaced().getLastRacePerformance().getTrack().getCode());
//							stat.put("favoriteFlag", raceResult.getWinners().get(0).getFavorite());
//							stat.put("odds", raceResult.getWinners().get(0).getOdds());
//							stat.put("winPayout", raceResult.getWinners().get(0).getWinPlaceShowPayoff().getWin().getPayoff());
//							stat.put("winnner", raceResult.getWinners().get(0).getHorse().getName());
//							stat.put("trainer", raceResult.getWinners().get(0).getTrainer().getName());
//							stat.put("jockey", raceResult.getWinners().get(0).getJockey().getName());
//							stat.put("postPosition", raceResult.getWinners().get(0).getPostPosition());
//							TotalLengthsBehind firstCallBL = raceResult.getWinners().get(0).getPointOfCall(
//									furlongs < 5 ? "1/4" : 
//										furlongs == 5 ? "3/16" :
//											furlongs < 8 ? "1/4" : 
//												furlongs <= 14 ? "1/2" : "1m").get().getRelativePosition().getTotalLengthsBehind();
//							stat.put("firstCallBL", firstCallBL == null ? 0 : firstCallBL.getLengths());
//							Optional<PointOfCall> secondCall = raceResult.getWinners().get(0).getPointOfCall(
//									furlongs < 6 ? "3/8" : 
//										furlongs < 8 ? "1/2" :
//											furlongs < 10 ? "1m" : 
//												furlongs <= 12 ? "1m" : 
//													furlongs == 12 ? "11/4" :
//														furlongs == 13 ? "13/8" :
//															furlongs == 14 ? "11/2" :
//																furlongs == 15 ? "15/8" : "13/4");
//							if (secondCall.isPresent() && furlongs >= 5) {
//								TotalLengthsBehind secondCallBL = secondCall.get().getRelativePosition().getTotalLengthsBehind();
//								stat.put("secondCallBL", secondCallBL == null ? 0 : secondCallBL.getLengths());
//							} else {
//								stat.set("secondCallBL", null);
//							}	
//							stats.add(stat);
//						}
//					}
//				}
//			}
//			
//			return stats;
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//	
//	
//	public static void generateRaceStatsCSV() throws Exception {
//		
//		try {
//			JsonNode node = mapper.valueToTree(generateRaceStats());
//			
//			Builder csvSchemaBuilder = CsvSchema.builder();
//			JsonNode firstObject = node.elements().next();
//			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//			
//			CsvMapper csvMapper = new CsvMapper();
//			csvMapper.writerFor(JsonNode.class)
//			  .with(csvSchema)
//			  .writeValue(new File(raceStatsFile), node);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
//	
	
public static void generateComboStats(int n) throws Exception {
		
		try {
			System.out.println("Generating Combo (" + n + ") stats.");
    		List<List<Angle>> combos = Angles.getAngleCombos(n);	
    		
			MongoCollection<Document> statsCollection = database.getCollection("comboStats" + n);
			statsCollection.drop();
			
			MongoCollection<Card> cardCollection = database.getCollection("cards", Card.class);
			FindIterable<Card> cardIterable = cardCollection.find();
			cardIterable.forEach(card -> {
				for (Race race : card.getRaces()) {
					
					Document resultQuery = new Document()
							.append("raceNumber", race.getRaceNumber())
							.append("track.code", race.getTrack())
							.append("raceDate", race.getDate());					
						
						
					MongoCollection<Document> resultsCollection = database.getCollection("raceResults");
						Document raceResult = resultsCollection.find(resultQuery).first();
						
					if (raceResult != null) {
						
						for (Entry entry : race.getEntries()) {
							
							if (!entry.getScratchedFlag()) {
							
								Document starterQuery = new Document()
									.append("raceNumber", race.getRaceNumber())
									.append("track", race.getTrack())
									.append("date", race.getDate())
									.append("name", entry.getName())
									;
							
								MongoCollection<Document> startersCollection = database.getCollection("startersResults");
								Document starterResult = startersCollection.find(starterQuery).first();
								
								if (starterResult != null) {
    								for (List<Angle> combo : combos) {
    									Document stat = new Document();
    									Boolean match = true;
    									String comboName = "";
    									for (int j = 0; j < n; j++) {
    										if (!entry.getAngles().contains(combo.get(j))) {
    											match = false;
    											break;
    										}
    										stat.put("angle"+j, combo.get(j).getName());
    										comboName += combo.get(j).getName();
    										if (j < n-1) comboName += " & ";
    									}
    									if (match) {
    										stat.put("angles", comboName);
    										stat.put("track", race.getTrack());
    										stat.put("date", race.getDate().toInstant().toEpochMilli());
    										stat.put("raceNumber", race.getRaceNumber());
    										stat.put("horse", entry.getName());
    										stat.put("trainer", entry.getTrainer().getName());
    										stat.put("jockey", entry.getJockey().getName());
    										stat.put("finishPosition", starterResult.getInteger("finishPosition"));
    										stat.put("officialPosition", starterResult.getInteger("officialPosition"));
    										stat.put("winner", starterResult.getBoolean("winner"));
    										stat.put("ITM", starterResult.getBoolean("ITM"));
    										stat.put("odds", starterResult.getDouble("odds"));
    										stat.put("payoff", starterResult.getDouble("payoff"));
    										statsCollection.insertOne(stat);
    									}
    								}
								}
							}
						}
					}
            	}
            });			
			System.out.println("Done generating combos (" + n + ")");
			
		} catch (Exception e) {
			throw e;
		}	
	}

	public static String getComboStats(int n) throws Exception {
		
		try {
			List<Document> results = new ArrayList<Document>();
			MongoDatabase database = mongoClient.getDatabase("jpp");
			MongoCollection<Document> collection = database.getCollection("comboStats" + n + "Summary");
			collection.find().into(results);
			return mapper.writeValueAsString(results);
			
		} catch (Exception e) {
			throw e;
		}
	}

	public static List<Document> getComboAlerts(int n) throws Exception {
		
		try {
			List<Document> combos = new ArrayList<Document>();
			Document filter = new Document("$and", Arrays.asList(new Document("winPercent", 
			        new Document("$gt", .60d)), 
			        new Document("winners", 
			        new Document("$gte", 10L)), 
			        new Document("ROI", 
			        new Document("$gt", 2L))));

			MongoDatabase database = mongoClient.getDatabase("jpp");
			MongoCollection<Document> collection = database.getCollection("comboStats" + n + "Summary");
			FindIterable<Document> result = collection.find(filter);
			result.into(combos);
			return combos;

		} catch (Exception e) {
			throw e;
		}
	}
}
