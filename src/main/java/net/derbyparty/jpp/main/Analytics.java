package net.derbyparty.jpp.main;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.derbyparty.jpp.chart.ProcessChart;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.Angle;
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
				System.out.println("Updating " + angle.getName());
				Document query = new Document()
						.append("angle", angle.getName());					
				
				MongoCollection<Document> angleStatCollection = database.getCollection("angleStats");
				FindIterable<Document> angleStatIterable = angleStatCollection.find(query);
				
				AtomicInteger total = new AtomicInteger(0);;
				AtomicInteger winners =  new AtomicInteger(0);;
				AtomicInteger ITM = new AtomicInteger(0);;
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
	
	public static ArrayNode generateStats() throws Exception {
		
		try {
			ArrayNode stats = mapper.createArrayNode();		
//    		Pattern pattern = Pattern.compile("([A-Z]+)(\\d{2})(\\d{2})(\\d{4})");
//    		
//    		List<Track> tracks = ProcessChart.getChartsArray();
//			
//			Files.list(new File(saveDir).toPath())
//            .forEach(path -> {
//            	if (path.toString().contains(".json")) {
//            		Matcher matcher =  pattern.matcher(path.toString());
//            		if (matcher.find()) {  
//            			try {
//            				LocalDate date = LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
//            				String track = matcher.group(1);
//            				Boolean hasChart = false;
//            				for (Track chartTrack : tracks) {
//            					if (chartTrack.getCode().equals(track)) {
//            						for (RaceDate raceDate : chartTrack.getRaceDates()) {
//            							if (raceDate.getRaceDate().equals(date)) {
//            								hasChart = raceDate.getHasChartFlag();
//            								break;
//            							}
//            						}
//            						if (hasChart) break;
//            					}
//            				}
//            				if (hasChart) {
//	            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + track + date.format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
//	            				List<RaceResult> results = ProcessChart.getChart(track, date);
//	            				for (int i = 0; i < card.size(); i++) {
//	            					float maxARating = 0f;
//	            					float maxPrimePower = 0f;
//	            					float maxSpeedRating = 0f;
//	            					float maxClassRating = 0f;
//	            					float maxBrisAvgLast3Class = 0f;
//	            					float maxBrisCurrentClass = 0f;
//	            					float maxACL = 0f;
//	            					float ARatingForm = 0f;
//	            					float maxARatingConnections = 0f;
//	            					float maxCombinedPaceAvg = 0f;
//	            					
//	            					for (Horse horse : card.get(i).getUnscratchedHorses()) {
//	            						if (horse.getARating() > maxARating) maxARating = horse.getARating();
//	            						if (horse.getPrimePower() > maxPrimePower) maxPrimePower = horse.getPrimePower();
//	            						if (horse.getSpeedRating() > maxSpeedRating) maxSpeedRating = horse.getSpeedRating();
//	            						if (horse.getClassRating() > maxClassRating) maxClassRating = horse.getClassRating();
//	            						if (horse.getBrisAvgLast3Class() > maxBrisAvgLast3Class) maxBrisAvgLast3Class = horse.getBrisAvgLast3Class();
//	            						if (horse.getBrisCurrentClass() > maxBrisCurrentClass) maxBrisCurrentClass = horse.getBrisCurrentClass();
//	            						if (horse.getAverageCompetitiveLevel() > maxACL) maxACL = horse.getAverageCompetitiveLevel();
//	            						if (horse.getARatingForm() > ARatingForm) ARatingForm = horse.getARatingForm();
//	            						if (horse.getARatingConnections() > maxARatingConnections) maxARatingConnections = horse.getARatingConnections();
//	            						if (horse.getCombinedPaceAvg() > maxCombinedPaceAvg) maxCombinedPaceAvg = horse.getCombinedPaceAvg();
//	            					}
//	            					for (Horse horse : card.get(i).getUnscratchedHorses()) {
//	            						for (Starter starter : results.get(i).getStarters()) {
//	            							if (starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "").toUpperCase().equals(horse.getName().toUpperCase())) {
//	            								if (horse.getARating() == maxARating && maxARating > 0 && horse.getMLOdds() >= 4) {
//		    										ObjectNode stat = createStat(results.get(i), starter);
//		    										stat.put("statName", "Max ARating & ML >= 4");
//		    										stats.add(stat);
//	            								}
//	            								if (horse.getARating() == maxARating && maxARating > 0 && starter.getOdds() >= 4) {
//		    										ObjectNode stat = createStat(results.get(i), starter);
//		    										stat.put("statName", "Max ARating & Odds >= 4");
//		    										stats.add(stat);
//	            								}  
//	            								if (horse.getARating() == maxARating && maxARating > 0 && starter.getOdds() >= 8) {
//		    										ObjectNode stat = createStat(results.get(i), starter);
//		    										stat.put("statName", "Max ARating & Odds >= 8");
//		    										stats.add(stat);
//	            								}              								
//	            							}
//	            						}
//	            					}
//	            				}
//            				}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//            		}
//            	}
//            });
//			
			System.out.println("Done generating stats");
			return stats;
			
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
//public static ArrayNode generateComboStats(int n) throws Exception {
//		
//		try {
//			ArrayNode stats = mapper.createArrayNode();		
//    		Pattern pattern = Pattern.compile("([A-Z]+)(\\d{2})(\\d{2})(\\d{4})");
//    		
//    		List<List<Angle>> combos = Angles.getAngleCombos(n);
//    		List<Track> tracks = ProcessChart.getChartsArray();  		
//			
//			Files.list(new File(saveDir).toPath())
//            .forEach(path -> {
//            	if (path.toString().contains(".json")) {
//            		Matcher matcher =  pattern.matcher(path.toString());
//            		if (matcher.find()) {  
//            			try {
//            				LocalDate date = LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
//            				String track = matcher.group(1);
//            				Boolean hasChart = false;
//            				for (Track chartTrack : tracks) {
//            					if (chartTrack.getCode().equals(track)) {
//            						for (RaceDate raceDate : chartTrack.getRaceDates()) {
//            							if (raceDate.getRaceDate().equals(date)) {
//            								hasChart = raceDate.getHasChartFlag();
//            								break;
//            							}
//            						}
//            						if (hasChart) break;
//            					}
//            				}
//            				if (hasChart) {
//            					System.out.println("Processing " + track + date.format(DateTimeFormatter.ofPattern("YYYYMMdd")));
//	            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + track + date.format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
//	            				List<RaceResult> results = ProcessChart.getChart(track, date);
//	            				for (int i = 0; i < card.size(); i++) {
//	            					for (Horse horse : card.get(i).getUnscratchedHorses()) {
//	            						for (Starter starter : results.get(i).getStarters()) {
//	            							if (starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "").toUpperCase().equals(horse.getName().toUpperCase())) {
//	            								for (List<Angle> combo : combos) {
//	            									ObjectNode stat = mapper.createObjectNode();
//	            									Boolean match = true;
//	            									String comboName = "";
//	            									for (int j = 0; j < n; j++) {
//	            										if (!horse.getAngles().contains(combo.get(j))) {
//	            											match = false;
//	            											break;
//	            										}
//	            										stat.put("angle"+j, combo.get(j).getName());
//	            										comboName += combo.get(j).getName();
//	            										if (j < n-1) comboName += " & ";
//	            									}
//	            									if (match) {
//	            										stat.put("angles", comboName);
//	            										stat.put("track", results.get(i).getTrack().getCode());
//	            										stat.put("date", results.get(i).getRaceDate().format(DateTimeFormatter.ofPattern(("MM/dd/YYYY"))));
//	            										stat.put("raceNumber", results.get(i).getRaceNumber());
//	            										stat.put("horse", starter.getHorse().getName());
//	            										stat.put("trainer", starter.getTrainer().getName());
//	            										stat.put("jockey", starter.getJockey().getName());
//	            										stat.put("finishPosition", starter.getFinishPosition());
//	            										stat.put("officialPosition", starter.getOfficialPosition());
//	            										stat.put("winner", starter.getOfficialPosition() != null && starter.getOfficialPosition() == 1);
//	            										stat.put("ITM", starter.getOfficialPosition() != null && starter.getOfficialPosition() <= 3);
//	            										stat.put("odds", starter.getOdds());
//	            										stat.put("win", (starter.getWinPlaceShowPayoff() == null || starter.getWinPlaceShowPayoff().getWin() == null) ? 0 : starter.getWinPlaceShowPayoff().getWin().getPayoff());
//	            										stats.add(stat);
//	            									}
//	            								}
//	            							}
//	            						}
//	            					}
//	            				}
//            				}
//						} catch (Exception e) {
//							//e.printStackTrace();
//						}
//            		}
//            	}
//            });
//			
//			System.out.println("Done generating combos (" + n + ")");
//			return stats;
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
//		
//	public static void generateComboStatsCSV(int n) throws Exception {
//		
//		try {
//			JsonNode node = mapper.valueToTree(generateComboStats(n));
//			
//			Builder csvSchemaBuilder = CsvSchema.builder();
//			JsonNode firstObject = node.elements().next();
//			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
//			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//			
//			CsvMapper csvMapper = new CsvMapper();
//			csvMapper.writerFor(JsonNode.class)
//			  .with(csvSchema)
//			  .writeValue(new File(n == 2 ? combo2StatsFile : combo3StatsFile), node);
//			
//		} catch (Exception e) {
//			throw e;
//		}	
//	}
//	
}
