package net.derbyparty.jpp.chart;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;

import net.derbyparty.jpp.chartparser.ChartParser;
import net.derbyparty.jpp.chartparser.charts.pdf.DistanceSurfaceTrackRecord;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.main.Main;
import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.PotentialKeyRace;
import net.derbyparty.jpp.object.PotentialKeyRaceHorse;
import net.derbyparty.jpp.object.RaceCategory;
import net.derbyparty.jpp.object.RaceDate;
import net.derbyparty.jpp.object.RaceNote;
import net.derbyparty.jpp.object.RaceType;
import net.derbyparty.jpp.object.SurfaceType;
import net.derbyparty.jpp.object.Track;

public class ProcessChart {
	
	private static ChartParser chartParser = ChartParser.create();
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	final static String keyRacesFile = "/Users/ahonaker/Google Drive/pp/jpp/keyRaces.json";
	final static String raceDatesFile =  "/Users/ahonaker/Google Drive/pp/jpp/raceDates.json";
	final static String chartDownloadDir = "/Users/ahonaker/Google Drive/pp/jpp/newcharts/";
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);
	
	public static List<PotentialKeyRace> getKeyRacesList() throws Exception {
		
		try {
			List<PotentialKeyRace> races = new ArrayList<PotentialKeyRace>();
			
			MongoCollection<PotentialKeyRace> collection = database.getCollection("tracks", PotentialKeyRace.class);
			FindIterable<PotentialKeyRace> iterable = collection.find();
			iterable.into(races);
			
			return races;
			
		} catch (Exception e) {
			throw e;
		}

	}		
	
	public static String getKeyRaces() throws Exception {
			
		try {
			
			return mapper.writeValueAsString(getKeyRacesList());
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static int calculateRawSpeedFigure (Starter starter, Track track, DistanceSurfaceTrackRecord distSurf) throws Exception {
		
		try {
			Document query = new Document("time", new Document("$lte", starter.getFinishFractional().getMillis() / 1000.0d))
		    .append("time2", new Document("$gt", starter.getFinishFractional().getMillis() / 1000.0d))
		    .append("turns", 
		    		(distSurf.getRaceDistance().getValue() >= 
		    			(distSurf.getSurface().equals("Dirt") ? track.getTwoTurnBreak() : track.getTwoTurnTurfBreak()))
		    		? 2 : 1)
		    .append("distance",  distSurf.getRaceDistance().getValue() / 5280.0d);
			
			MongoCollection<Document> collection = database.getCollection("speedRatingChart");
			return collection.find(query).first().getInteger("rawSpeedRating");

		} catch (Exception e) {
			System.out.println( new Document("time", new Document("$lte", starter.getFinishFractional().getMillis() / 1000.0d))
				    .append("time2", new Document("$gt", starter.getFinishFractional().getMillis() / 1000.0d))
				    .append("turns", 
				    		(distSurf.getRaceDistance().getValue() >= 
				    			(distSurf.getSurface().equals("Dirt") ? track.getTwoTurnBreak() : track.getTwoTurnTurfBreak()))
				    		? 2 : 1)
				    .append("distance",  distSurf.getRaceDistance().getValue() / 5280.0d));
			//System.out.println("No Raw Speed Rating Calculated for " + distSurf.getRaceDistance().getText());
		}
		
		return 0;
	}
	
	public static RaceCategory getRaceCategory(RaceResult raceResult, Track track) throws Exception {
		
		RaceCategory result = new RaceCategory();
		
		try {			
			String surface = 
				(raceResult.getDistanceSurfaceTrackRecord().getSurface().toUpperCase().contains("TURF")) ? "Turf" :
					raceResult.getDistanceSurfaceTrackRecord().getSurface();
			Boolean isRoute =
				raceResult.getDistanceSurfaceTrackRecord().getRaceDistance().getValue() < 5280;
				
			for (RaceCategory raceCategory : track.getRaceCategories()) {				

				if (raceCategory.getCategory().equals(raceResult.getRaceConditions().getRaceCategory())
						&& raceCategory.getSurface().equals(surface)
						&& raceCategory.getIsRoute().equals(isRoute)
						&& (raceResult.getRaceConditions().getClaimingPriceRange() != null
							&& raceResult.getRaceConditions().getClaimingPriceRange().getMin() >= raceCategory.getClaimingMin()
							&& raceResult.getRaceConditions().getClaimingPriceRange().getMin() < raceCategory.getClaimingMax()
							|| (raceResult.getPurse().getValue() >= raceCategory.getPurseMin()
									&& raceResult.getPurse().getValue() < raceCategory.getPurseMax()))
					) {
					result = raceCategory;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public static int calculateAdjustedSpeedFigure (Starter starter, Track track, RaceResult raceResult, List<RaceResult> raceResults) throws Exception {
		
		try {
			String surface = 
				(raceResult.getDistanceSurfaceTrackRecord().getSurface().toUpperCase().contains("TURF")) ? "Turf" :
					raceResult.getDistanceSurfaceTrackRecord().getSurface();
			return starter.getRawSpeedRating() + calculateSpeedVariant(raceResults, surface); 			
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static void updateAdjustedSpeedFigures (String trackString, Date date) throws Exception {
		
		try {
			List<Track> tracks = Main.getTracksList();
			for (Track track : tracks) {
				if (track.getCode().equals(trackString)) {
					List<RaceResult> raceResults = ProcessChart.getChart(trackString, date);
					for (RaceResult raceResult : raceResults) {
						for (Starter starter : raceResult.getStarters()) {
							starter.setAdjustedSpeedRating(calculateAdjustedSpeedFigure(starter, track, raceResult, raceResults));
						}
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}
	
	public static int calculateSpeedVariant(List<RaceResult> raceResults, String surface) throws Exception {
		
		int variant = 0;
		
		try {
			int count = 0;
			int sumDiff = 0;
			List<Track> tracks = Main.getTracksList();
			for (Track track : tracks) {
				if (raceResults.size() == 0) {
					throw new Exception("No results found.");
				}
				if (track.getCode().equals(raceResults.get(0).getTrack().getCode())) {
					for (RaceResult raceResult : raceResults) {
						if (!raceResult.getCancellation().cancelled) {
							String surf = 
								(raceResult.getDistanceSurfaceTrackRecord().getSurface().toUpperCase().contains("TURF")) ? "Turf" :
									raceResult.getDistanceSurfaceTrackRecord().getSurface();
							if (surf.equals(surface) && raceResult.getWinners().get(0).getRawSpeedRating() > 0) {
								RaceCategory raceCategory = getRaceCategory(raceResult, track);
								if (raceCategory != null) {
									count++;
									sumDiff += raceResult.getWinners().get(0).getRawSpeedRating() - raceCategory.getParSpeedRating();
									//System.out.println("Race " + raceResult.getRaceNumber() + " " + (raceResult.getWinners().get(0).getRawSpeedRating() - raceCategory.getParSpeedRating()));
								}
								
							}
						}
					}

				}
			}
			variant = (count == 0) ? 0 : sumDiff / count;
			//System.out.println(sumDiff + " / " + count + " " + variant);
			
		} catch (Exception e) {
			throw e;
		}
		return variant;
	}
	
	public static void updateKeyRaces(List<RaceResult> results) throws Exception {
		for (RaceResult raceResult : results) {
			for (Starter starter : raceResult.getStarters()) {
				if (starter.getLastRaced() != null && starter.getLastRaced().getLastRacePerformance() != null && starter.getLastRaced().getLastRacePerformance().getRaceNumber() != null
						&& starter.getFinishPosition() != null  && (starter.getFinishPosition() == 1 || starter.getFinishPosition() == 2 ||
						(starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").isPresent() 
								&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
								&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() < 2))) {
						
						PotentialKeyRace keyRace = PotentialKeyRace.builder()
								.withTrack(starter.getLastRaced().getLastRacePerformance().getTrack().getCode())
								.withRaceDate(starter.getLastRaced().getRaceDate())
								.withRaceNumber(starter.getLastRaced().getLastRacePerformance().getRaceNumber())
								.build();
						
						MongoCollection<PotentialKeyRace> keyRacesCollection = database.getCollection("keyRaces", PotentialKeyRace.class);
						Bson raceQuery = and(
							eq("track", starter.getLastRaced().getLastRacePerformance().getTrack().getCode()),
							eq("raceDate",starter.getLastRaced().getRaceDate()),
							eq("raceNumber", starter.getLastRaced().getLastRacePerformance().getRaceNumber())
						);
						
						PotentialKeyRace keyRaceFound = keyRacesCollection.find(raceQuery).first();
						if (keyRaceFound != null) {
							Boolean horseFound = false;
							for (PotentialKeyRaceHorse keyRaceHorse : keyRaceFound.getHorses()) {
								if (starter.getHorse().getName().equals(keyRaceHorse.getName())) {
									horseFound = true;
								}

							}	
							if (!horseFound) {
								List<PotentialKeyRaceHorse> newKeyRaceHorses = new ArrayList<PotentialKeyRaceHorse>();
								PotentialKeyRaceHorse keyRaceHorse = PotentialKeyRaceHorse.builder()
										.withName(starter.getHorse().getName())
										.withPosition(starter.getFinishPosition())
										.withBeatenLengths(
												starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
												? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() 
												: 0)										
										.withTrack(raceResult.getTrack().getCode())
										.withRaceDate(raceResult.getRaceDate())
										.withRaceNumber(raceResult.getRaceNumber())
										.build();
								newKeyRaceHorses.add(keyRaceHorse);
								newKeyRaceHorses.addAll(keyRaceFound.getHorses());

								keyRaceFound.setHorses(newKeyRaceHorses);
								keyRaceFound.save();
							}
						} else {
							List<PotentialKeyRaceHorse> newKeyRaceHorses = new ArrayList<PotentialKeyRaceHorse>();
							newKeyRaceHorses.add(PotentialKeyRaceHorse.builder()
								.withName(starter.getHorse().getName())
								.withPosition(starter.getFinishPosition())
								.withBeatenLengths(
										starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
										? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() 
										: 0)
								.withTrack(raceResult.getTrack().getCode())
								.withRaceDate(raceResult.getRaceDate())
								.withRaceNumber(raceResult.getRaceNumber())
								.build()			
							);	
							keyRace.setHorses(newKeyRaceHorses);				
							keyRace.save();
						}
					}
			}
		}
	}

	public static List<RaceResult> process(File chart) throws Exception {
		
		try {
			List<Track> raceDates = Main.getTracksList();
			
			List<RaceResult> results = chartParser.parse(chart);
			if (results.size() == 0) throw new Exception("No chart found.");
			
			for (RaceResult result : results) {
				if (!result.getCancellation().isCancelled()) {
					
					for (Starter starter : result.getStarters() ) {
						for (Track track : raceDates) {
							if (track.getCode().equals(result.getTrack().getCode())) {
								try {
									int rating = calculateRawSpeedFigure(starter, track, result.getDistanceSurfaceTrackRecord());
									starter.setRawSpeedRating(rating);
								} catch (Exception e) {
									
								}
							}
						}
					}
					
					for (Starter starter : result.getStarters()) {
						RaceNote raceNote = RaceNote.builder()
							.withTrack(result.getTrack().getCode())
							.withRaceDate(result.getRaceDate())
							.withRaceNumber(result.getRaceNumber())
							.withType(result.getRaceConditions().getRaceTypeNameBlackTypeBreed().getType())
							.withRaceClassification(result.getRaceConditions().getRaceClassification())
							.withPurse(result.getPurse().getValue())
							.withClaimingPrice(starter.getClaim() == null ? 0 : starter.getClaim().getPrice())
							.withDistance(result.getDistanceSurfaceTrackRecord().getRaceDistance().getValue())
							.withExactDistance(result.getDistanceSurfaceTrackRecord().getRaceDistance().isExact())
							.withSurface(result.getDistanceSurfaceTrackRecord().getSurface())
							.withOffTurf(result.getDistanceSurfaceTrackRecord().isOffTurf())
							.withTrackCondition(result.getDistanceSurfaceTrackRecord().getTrackCondition())
							.withPosition(starter.getFinishPosition() != null ? starter.getFinishPosition() : 0)
							.withBeatenLengths(starter.getFinishPosition() != null && starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
									? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths().floatValue() 
									: 0)
							.withFootnote(starter.getFootnote())
							.build();
						
						MongoCollection<Horse> horsesCollection = database.getCollection("horses", Horse.class);
						Bson horseQuery = eq("name", starter.getHorse().getName().replaceAll("\\s\\(.+\\)", ""));
						
						Horse horse = horsesCollection.find(horseQuery).first();
						if (horse != null) {
							Boolean noteFound = false;
							Iterator<RaceNote> it = horse.getRaceNotes().iterator();
							while (it.hasNext()) {
								RaceNote existingRaceNote = it.next();
								if (existingRaceNote.getRaceDate().equals(result.getRaceDate())
									&& existingRaceNote.getTrack().equals(result.getTrack().getCode())
									&& existingRaceNote.getRaceNumber() == result.getRaceNumber()) {
										noteFound = true;
										existingRaceNote.setFootnote(starter.getFootnote());
								}
							}
							if (!noteFound) {
								horse.getRaceNotes().add(raceNote);
							}
							horse.save();
						} else {
							List<RaceNote> raceNotes = new ArrayList<RaceNote>();
							raceNotes.add(raceNote);
							horse = Horse.builder()
								.withName(starter.getHorse().getName())
								.withRaceNotes(raceNotes)
								.build();
							horse.save();
						}
					}
					
					ReplaceOptions opts = new ReplaceOptions().upsert(true);
					
					Document query = new Document()
							.append("track.code", result.getTrack().getCode())
							.append("raceDate", result.getRaceDate())
							.append("raceNumber", result.getRaceNumber() );	
					
					MongoCollection<RaceResult> collection = database.getCollection("raceResults", RaceResult.class);
					UpdateResult updateResult = collection.replaceOne(query, result, opts);
//					if (updateResult.getModifiedCount() == 1) {
//						System.out.println("raceResult "
//								+ result.getTrack().getCode() 
//								+ " - " + result.getRaceDate() 
//								+ " - Race " + result.getRaceNumber()
//								+ " updated.");
//					} else {
//						System.out.println("raceResult "
//								+ result.getTrack().getCode() 
//								+ " - " + result.getRaceDate() 
//								+ " - Race " + result.getRaceNumber()
//								+ " saved. (ID = " + updateResult.getUpsertedId());
//					}	
				}
			}
			
			updateKeyRaces(results);
			
			if (!results.get(0).getCancellation().cancelled) {
				updateAdjustedSpeedFigures(results.get(0).getTrack().getCode(), results.get(0).getRaceDate());
			}
			
			Boolean trackFound = false;
			for (Track track : raceDates) {
				if (track.getCode().equals(results.get(0).getTrack().getCode())) {
					trackFound = true;
					Boolean raceDateFound = false;
					for (RaceDate raceDate : track.getRaceDates()) {
						if (raceDate.getRaceDate().equals(results.get(0).getRaceDate())) {
							raceDateFound = true;
							raceDate.setHasChartFlag(true);
							raceDate.setSpeedVariantDirt(calculateSpeedVariant(results, "Dirt"));
							raceDate.setSpeedVariantTurf(calculateSpeedVariant(results, "Turf"));
							raceDate.setSpeedVariantAllWeather(calculateSpeedVariant(results, "All Weather Track"));
						}
					}
					if (!raceDateFound) {
						track.getRaceDates().add(RaceDate.builder()
							.withRaceDate(results.get(0).getRaceDate())
							.withReviewedFlag(false)
							.withHasChartFlag(true)
							.withSpeedVariantDirt(calculateSpeedVariant(results, "Dirt"))
							.withSpeedVariantTurf(calculateSpeedVariant(results, "Turf"))
							.withSpeedVariantAllWeather(calculateSpeedVariant(results, "All Weather Track"))
							.build());
					}
				}
			}
			if (!trackFound) {
				RaceDate raceDate = RaceDate.builder()
					.withRaceDate(results.get(0).getRaceDate())
					.withReviewedFlag(false)
					.withHasChartFlag(true)
					.build();
				List<RaceDate> firstRaceDate = new ArrayList<RaceDate>();
				firstRaceDate.add(raceDate);
				Track track = Track.builder()
					.withCode(results.get(0).getTrack().getCode())
					.withName(results.get(0).getTrack().getName())
					.withRaceDates(firstRaceDate)
					.build();
				raceDates.add(track);
			}
			
			Main.saveTracks(raceDates);
			
			return results;
			
		} catch (Exception e) {
			throw e;
		
		}
		
	}

	public static void parseDirectory () throws Exception {
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MMddYYYY");
			String sourceDir = "/Users/ahonaker/Google Drive/pp/jpp/newcharts/";
			String targetDir = "/Users/ahonaker/Google Drive/pp/jpp/parsedcharts/";
	        Files.list(new File(sourceDir).toPath())
            .forEach(path -> {
                File file = new File(path.toString());
                try {
//                	System.out.println(path);
					if (path.toString().contains(".pdf")) {
						List<RaceResult> results = process(file);
						String fullFileName = targetDir + results.get(0).getTrack().getCode() + formatter.format(results.get(0).getRaceDate()) + "USA.pdf";
						Files.move(path, path.resolveSibling(fullFileName),
					            StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (Exception e) {
					System.out.println(file.getName() + " - no chart found");
					e.printStackTrace();
				}
            });
		} catch (Exception e) {
			throw e;
		
		}
		
	}
	
	public static List<RaceResult> addHorsesToChart(List<RaceResult> chart) throws Exception {
		
		try {

			for (RaceResult race : chart) {
				if (!race.getCancellation().isCancelled()) {
					for (Starter starter : race.getStarters()) {
						MongoCollection<Horse> horsesCollection = database.getCollection("horses", Horse.class);
						Bson horseQuery = eq("name", starter.getHorse().getName().replaceAll("\\s\\(.+\\)", ""));
						
						Horse horse = horsesCollection.find(horseQuery).first();
						if (horse != null) {						
							if (starter.getHorse().getName().equals(horse.getName())) {
								starter.setHorseFlag(horse.getFlag());
								for (RaceNote raceNote : horse.getRaceNotes()) {
									if (race.getTrack().getCode().equals(raceNote.getTrack()) && race.getRaceDate().equals(raceNote.getRaceDate())) {
										starter.setNote(raceNote.getComment());
										starter.setRaceFlag(raceNote.getFlag());
									}
									if (raceNote.getRaceDate().after(race.getRaceDate())) {
										starter.setNextOutRaceNote(raceNote);
									}
								}
							}
						}
					}
				}
			}
			return chart;
			
		} catch (Exception e) {
			throw e;
			
		
		}
	}
	
	public static List<RaceResult> addKeyRacesToChart(List<RaceResult> chart) throws Exception {
		
		try {
			for (RaceResult race : chart) {
				for (PotentialKeyRace keyRace : ProcessChart.getKeyRacesList()) {
					if (race.getTrack().getCode().equals(keyRace.getTrack())
						&& race.getRaceDate().equals(keyRace.getRaceDate())
						&& race.getRaceNumber() == keyRace.getRaceNumber()) race.setKeyRace(keyRace);
					if (!race.getCancellation().isCancelled()) {
						for (Starter starter : race.getStarters()) {
							if (starter.getLastRaced() != null && starter.getLastRaced().getLastRacePerformance() != null && starter.getLastRaced().getLastRacePerformance().getTrack().getCode().equals(keyRace.getTrack())
								&& starter.getLastRaced().getRaceDate().equals(keyRace.getRaceDate())
								&& starter.getLastRaced().getLastRacePerformance().getRaceNumber() == keyRace.getRaceNumber()) starter.setLastRacedKeyRace(keyRace);
						}
					}
				}
			}
			return chart;
			
		} catch (Exception e) {
			throw e;
			
		
		}
	}
	
	public static List<RaceResult> getChart(String track, Date date) throws Exception {
		
		try {						
			List<RaceResult> raceResults = new ArrayList<>();
			Document query = new Document()
					.append("track.code", track)
					.append("raceDate", date);
			
			MongoCollection<RaceResult> collection = database.getCollection("raceResults", RaceResult.class);
			FindIterable<RaceResult> iterable = collection.find(query);
			iterable.into(raceResults);


			return raceResults;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		
		}
		
	}
	
	public static String getChartString(String track, Date date) throws Exception {
		
		try {
			System.out.println(track + date);
			return mapper.writeValueAsString(addHorsesToChart(getChart(track, date)));
			
		} catch (Exception e) {
			throw e;
		
		}
		
	}
	
	public static void generateKeyRaces() throws Exception {
		
		try {
			List<PotentialKeyRace> keyRaces = new ArrayList<PotentialKeyRace>();
			List<Track> tracks = Main.getTracksList();
			for (Track track : tracks) {
				for (RaceDate raceDate : track.getRaceDates()) {
					if (raceDate.getHasChartFlag()) {
						System.out.println("Updating Key Races for " + track.getCode() + " " + raceDate.getRaceDate().toString());
						for (RaceResult raceResult : getChart(track.getCode(), raceDate.getRaceDate())) {
							if (!raceResult.getCancellation().isCancelled()) {
								for (Starter starter : raceResult.getStarters()) {
									if (starter.getLastRaced() != null && starter.getLastRaced().getLastRacePerformance() != null && starter.getLastRaced().getLastRacePerformance().getRaceNumber() != null
										&& starter.getFinishPosition() != null 
										&& (starter.getFinishPosition() == 1 || starter.getFinishPosition() == 2 || starter.getOfficialPosition() == 1
										|| starter.getOfficialPosition() == 2 
										|| (starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").isPresent() 
												&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
												&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() < 2)
										)) {
										PotentialKeyRace keyRace = PotentialKeyRace.builder()
											.withTrack(starter.getLastRaced().getLastRacePerformance().getTrack().getCode())
											.withRaceDate(starter.getLastRaced().getRaceDate())
											.withRaceNumber(starter.getLastRaced().getLastRacePerformance().getRaceNumber())
											.build();
										PotentialKeyRaceHorse keyRaceHorse = PotentialKeyRaceHorse.builder()
											.withName(starter.getHorse().getName().replaceAll("\\s\\(.+\\)", ""))
											.withPosition(starter.getFinishPosition())
											.withBeatenLengths(starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
												? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() 
												: 0)
											.withTrack(track.getCode())
											.withRaceDate(raceDate.getRaceDate())
											.withRaceNumber(raceResult.getRaceNumber())
											.build();
										if (keyRaces.contains(keyRace)) {
											int ndx =  keyRaces.indexOf(keyRace);
											PotentialKeyRace existingKeyRace = keyRaces.get(ndx);
											existingKeyRace.getHorses().add(keyRaceHorse);
										} else {
											List<PotentialKeyRaceHorse> keyRaceHorses = new ArrayList<PotentialKeyRaceHorse>();
											keyRaceHorses.add(keyRaceHorse);
											keyRace.setHorses(keyRaceHorses);
											keyRaces.add(keyRace);
										}
									}
								}
							}
						}
					}
				}
			}
			mapper.writeValue(Paths.get(keyRacesFile).toFile(), keyRaces);
			System.out.println("Key Race Generation Complete.");
			
		} catch (Exception e) {
			throw e;
		
		}
		
	}
	
	
}
