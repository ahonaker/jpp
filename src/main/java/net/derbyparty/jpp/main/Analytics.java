package net.derbyparty.jpp.main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceTime;

public class Analytics {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	final static String saveDir = "/Users/ahonaker/Google Drive/pp/jpp/saveDir/";
	final static String raceTimesFile = "/Users/ahonaker/Google Drive/pp/jpp/raceTimes.csv";
	
	public static List<RaceTime> generateRaceTimes() throws Exception {
		
		try {
    		Pattern pattern = Pattern.compile("([A-Z]+)(\\d{2})(\\d{2})(\\d{4})");
			List<RaceTime> raceTimes = new ArrayList<RaceTime>();
			
			Files.list(new File(saveDir).toPath())
            .forEach(path -> {
            	if (path.toString().contains(".json")) {
            		Matcher matcher =  pattern.matcher(path.toString());
            		if (matcher.find()) {  
            			try {
            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + matcher.group(1) + LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))).format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
            				for (Race race : card) {
            					for (Horse horse : race.getHorses()) {
            						for (PastPerformance pp : horse.getPastPerformances()) {
            							RaceTime raceTime = RaceTime.builder()
            								.withTrack(pp.getTrackCode())
            								.withRaceDate(pp.getRaceDate())
            								.withRaceNumber(pp.getRaceNumber())
            								.withRaceType(pp.getRaceType())
            								.withRaceClassification(pp.getRaceClassification())
            								.withDistance(pp.getDistance())
            								.withSurface(pp.getSurface())
            								.withAllWeatherFlag(pp.getAllWeatherSurfaceFlag())
            								.withTrackCondition(pp.getTrackCondition())
            								.withFraction1(pp.getFraction1())
            								.withFraction2(pp.getFraction2())
            								.withFraction3(pp.getFraction3())
            								.withFinalTime(pp.getFinalTime())
            								.withSpeedPar(pp.getSpeedPar())
            								.withRaceShapeFirstCall(pp.getRaceShapeFirstCall())
            								.withRaceShapeSecondCall(pp.getRaceShapeSecondCall())
            								.build();
            							if (!raceTimes.contains(raceTime)) raceTimes.add(raceTime);
            						}
            					}
            				}
						} catch (Exception e) {
							e.printStackTrace();
						}
            			
            		}
            	}
            });
			
			Files.list(new File(saveDir).toPath())
            .forEach(path -> {
            	if (path.toString().contains(".json")) {
            		Matcher matcher =  pattern.matcher(path.toString());
            		if (matcher.find()) {  
            			try {
            				List<Race> card = Arrays.asList(mapper.readValue(Paths.get(saveDir + matcher.group(1) + LocalDate.of(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))).format(DateTimeFormatter.ofPattern("MMddYYYY")) + ".json").toFile(), Race[].class));
            				for (Race race : card) {
            					for (Horse horse : race.getHorses()) {
            						for (PastPerformance pp : horse.getPastPerformances()) {
            							if (pp.getFinishBeatenLengthsOnly() == 0) {
            								for (RaceTime raceTime : raceTimes) {
            									if (raceTime.getTrack().equals(race.getTrack())
            										&& raceTime.getRaceDate().equals(race.getDate())
            										&& raceTime.getRaceNumber() == race.getRaceNumber()) {
            										raceTime.setWinnerPar(pp.getBRISSpeedRating());
            										raceTime.setPaceFigure2F(pp.getPaceFigure2F());
            										raceTime.setPaceFigure4F(pp.getPaceFigure4F());
            										raceTime.setPaceFigure6F(pp.getPaceFigure6F());
            										raceTime.setPaceFigure8F(pp.getPaceFigure8F());
            										raceTime.setPaceFigure10F(pp.getPaceFigure10F());
            										raceTime.setPaceFigureLate(pp.getPaceFigureLate());
            										break;
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
            	}
            });
			
			return raceTimes;
			//mapper.writeValue(Paths.get(raceTimesFile).toFile(), raceTimes);
			
		} catch (Exception e) {
			throw e;
		}	
		
	}
	
	public static void generateRaceTimesCSV() throws Exception {
		
		try {
			JsonNode node = mapper.valueToTree(generateRaceTimes());
			
			Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = node.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
			
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class)
			  .with(csvSchema)
			  .writeValue(new File(raceTimesFile), node);
			
		} catch (Exception e) {
			throw e;
		}	
	}
	
	
}
