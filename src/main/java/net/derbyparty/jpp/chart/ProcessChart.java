package net.derbyparty.jpp.chart;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.derbyparty.jpp.chartparser.ChartParser;
import net.derbyparty.jpp.chartparser.charts.pdf.RaceResult;
import net.derbyparty.jpp.chartparser.charts.pdf.Starter;
import net.derbyparty.jpp.object.HorseToWatch;
import net.derbyparty.jpp.object.PotentialKeyRace;
import net.derbyparty.jpp.object.PotentialKeyRaceHorse;
import net.derbyparty.jpp.object.RaceDate;
import net.derbyparty.jpp.object.RaceNote;
import net.derbyparty.jpp.object.Track;

public class ProcessChart {
	
	private static ChartParser chartParser = ChartParser.create();
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	final static String keyRacesFile = "/Users/ahonaker/Google Drive/pp/jpp/keyRaces.json";
	final static String raceDatesFile =  "/Users/ahonaker/Google Drive/pp/jpp/raceDates.json";
	final static String horsesToWatchDir = "/Users/ahonaker/Google Drive/pp/jpp/horsesToWatch/";

	public static List<PotentialKeyRace> getKeyRacesList() throws Exception {
		
		try {
			
			List<PotentialKeyRace> potentialKeyRaces = new ArrayList<PotentialKeyRace>(Arrays.asList(mapper.readValue(Paths.get(keyRacesFile).toFile(), PotentialKeyRace[].class)));
			List<PotentialKeyRace> keyRaces = new ArrayList<PotentialKeyRace>();
			for (PotentialKeyRace potentialKeyRace : potentialKeyRaces) {
				if (potentialKeyRace.getHorses().size() > 1) keyRaces.add(potentialKeyRace);
			}
			
			return keyRaces;
			
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

	public static List<RaceResult> process(File chart) throws Exception {
		
		try {
			List<PotentialKeyRace> keyRaces = new ArrayList<PotentialKeyRace>(getKeyRacesList());
			List<Track> raceDates = new ArrayList<Track>(Arrays.asList(mapper.readValue(Paths.get(raceDatesFile).toFile(), Track[].class)));
			
			List<RaceResult> results = chartParser.parse(chart);
			
			for (RaceResult result : results) {
				for (Starter starter : result.getStarters()) {
					
					RaceNote raceNote = RaceNote.builder()
						.withTrack(result.getTrack().getCode())
						.withRaceDate(result.getRaceDate())
						.withRaceNumber(result.getRaceNumber())
						.withPosition(starter.getFinishPosition() != null ? starter.getFinishPosition() : 0)
						.withBeatenLengths(starter.getFinishPosition() != null && starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
								? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths().floatValue() 
								: 0)
						.withFootnote(starter.getFootnote())
						.build();
					File horseToWatchFile = new File(horsesToWatchDir + starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "") + ".json");
					if (horseToWatchFile.exists()) {
						HorseToWatch horseToWatch = mapper.readValue(Paths.get(horsesToWatchDir + starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "") + ".json").toFile(), HorseToWatch.class);
						Boolean noteFound = false;
						for (RaceNote existingRaceNote : horseToWatch.getRaceNotes()) {
							if (existingRaceNote.getRaceDate().equals(result.getRaceDate())
								&& existingRaceNote.getTrack().equals(result.getTrack().getCode())
								&& existingRaceNote.getRaceNumber() == result.getRaceNumber()) {
									noteFound = true;
									existingRaceNote.setFootnote(starter.getFootnote());
									horseToWatch.save();
							}
						}
						if (!noteFound) {
							horseToWatch.getRaceNotes().add(raceNote);
							horseToWatch.save();
						}
					} else {
						List<RaceNote> raceNotes = new ArrayList<RaceNote>();
						raceNotes.add(raceNote);
						HorseToWatch horseToWatch = HorseToWatch.builder()
							.withName(starter.getHorse().getName())
							.withRaceNotes(raceNotes)
							.build();
						horseToWatch.save();
					}
					
					if (starter.getFinishPosition() != null && (starter.getFinishPosition() == 1 || starter.getFinishPosition() == 2 ||
						(starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").isPresent() 
								&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
								&& starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() < 2))) {
						
						Boolean raceFound = false;
						for (PotentialKeyRace keyRace : keyRaces) {
							if (starter.getLastRaced() != null &&  starter.getLastRaced().getLastRacePerformance() != null
								&& starter.getLastRaced().getLastRacePerformance().getTrack().getCode().equals(keyRace.getTrack())
								&& starter.getLastRaced().getRaceDate().equals(keyRace.getRaceDate())
								&& starter.getLastRaced().getLastRacePerformance().getRaceNumber() == keyRace.getRaceNumber()) {
								
								raceFound = true;
								Boolean horseFound = false;
								for (PotentialKeyRaceHorse keyRaceHorse : keyRace.getHorses()) {
									if (starter.getHorse().getName().equals(keyRaceHorse.getName())) horseFound = true;
								}	
								if (!horseFound) {
									keyRace.getHorses().add(PotentialKeyRaceHorse.builder()
										.withName(starter.getHorse().getName())
										.withPosition(starter.getFinishPosition())
										.withBeatenLengths(
												starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
												? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() 
												: 0)										
										.withTrack(result.getTrack().getCode())
										.withRaceDate(result.getRaceDate())
										.withRaceNumber(result.getRaceNumber())
										.build()			
									);
										
									
								}
							}
						}	
						if (!raceFound && starter.getLastRaced() != null && starter.getLastRaced().getLastRacePerformance() != null
								&& starter.getLastRaced().getLastRacePerformance().getRaceNumber() != null) {
							List<PotentialKeyRaceHorse> newKeyRaceHorses = new ArrayList<PotentialKeyRaceHorse>();
							newKeyRaceHorses.add(PotentialKeyRaceHorse.builder()
								.withName(starter.getHorse().getName())
								.withPosition(starter.getFinishPosition())
								.withBeatenLengths(
										starter.getFinishPosition() > 1 && starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind() != null
										? starter.getPointOfCall("Fin").get().getRelativePosition().getTotalLengthsBehind().getLengths() 
										: 0)
								.withTrack(result.getTrack().getCode())
								.withRaceDate(result.getRaceDate())
								.withRaceNumber(result.getRaceNumber())
								.build()			
							);						
							PotentialKeyRace newKeyRace = PotentialKeyRace.builder()
								.withTrack(starter.getLastRaced().getLastRacePerformance().getTrack().getCode())
								.withRaceDate(starter.getLastRaced().getRaceDate())
								.withRaceNumber(starter.getLastRaced().getLastRacePerformance().getRaceNumber())
								.withHorses(newKeyRaceHorses)
								.build();				
							keyRaces.add(newKeyRace);
						}
					}
				
				}
			}
			
			mapper.writeValue(Paths.get(keyRacesFile).toFile(), keyRaces);
			
			Boolean trackFound = false;
			for (Track track : raceDates) {
				if (track.getCode().equals(results.get(0).getTrack().getCode())) {
					trackFound = true;
					Boolean raceDateFound = false;
					for (RaceDate raceDate : track.getRaceDates()) {
						if (raceDate.getRaceDate().equals(results.get(0).getRaceDate())) {
							raceDateFound = true;
							raceDate.setHasChartFlag(true);
						}
					}
					if (!raceDateFound) {
						track.getRaceDates().add(RaceDate.builder()
							.withRaceDate(results.get(0).getRaceDate())
							.withReviewedFlag(false)
							.withHasChartFlag(true)
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
			
			mapper.writeValue(Paths.get(raceDatesFile).toFile(), raceDates);
			
			return results;
			
		} catch (Exception e) {
			throw e;
		
		}
		
	}

	public static void parseDirectory () throws Exception {
		
		try {
			String sourceDir = "/Users/ahonaker/Google Drive/pp/jpp/newcharts/";
			String targetDir = "/Users/ahonaker/Google Drive/pp/jpp/parsedcharts/";
	        Files.list(new File(sourceDir).toPath())
            .forEach(path -> {
                File file = new File(path.toString());
                try {
                	//System.out.println(path);
					if (path.toString().contains(".pdf")) {
						List<RaceResult> results = process(file);
						String fullFileName = targetDir + results.get(0).getTrack().getCode() + results.get(0).getRaceDate().format(DateTimeFormatter.ofPattern("MMddYYYY")) + "USA.pdf";
						Files.move(path, path.resolveSibling(fullFileName),
					            StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
		} catch (Exception e) {
			throw e;
		
		}
		
	}
	
	public static String getCharts() throws Exception {
		
		return mapper.writeValueAsString(getChartsArray());
		
	}
	
	public static List<Track> getChartsArray () throws Exception {
		
		return new ArrayList<Track>(Arrays.asList(mapper.readValue(Paths.get(raceDatesFile).toFile(), Track[].class)));

	}
	
	public static List<RaceResult> addHorsesToWatchToChart(List<RaceResult> chart) throws Exception {
		
		try {

			for (RaceResult race : chart) {
				for (Starter starter : race.getStarters()) {
					File horseToWatchFile = new File(horsesToWatchDir + starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "") + ".json");
					if (horseToWatchFile.exists()) {
						HorseToWatch horse = mapper.readValue(Path.of(horsesToWatchDir + starter.getHorse().getName().replaceAll("\\s\\(.+\\)", "") + ".json").toFile(), HorseToWatch.class);
						if (starter.getHorse().getName().equals(horse.getName())) {
							starter.setHorseFlag(horse.getFlag());
							for (RaceNote raceNote : horse.getRaceNotes()) {
								if (race.getTrack().getCode().equals(raceNote.getTrack()) && race.getRaceDate().equals(raceNote.getRaceDate())) {
									starter.setNote(raceNote.getComment());
									starter.setRaceFlag(raceNote.getFlag());
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
					for (Starter starter : race.getStarters()) {
						if (starter.getLastRaced() != null && starter.getLastRaced().getLastRacePerformance().getTrack().getCode().equals(keyRace.getTrack())
							&& starter.getLastRaced().getRaceDate().equals(keyRace.getRaceDate())
							&& starter.getLastRaced().getLastRacePerformance().getRaceNumber() == keyRace.getRaceNumber()) starter.setLastRacedKeyRace(keyRace);
					}
				}
			}
			return chart;
			
		} catch (Exception e) {
			throw e;
			
		
		}
	}
	
	public static String getChart(String track, LocalDate date) throws Exception {
		
		String targetDir = "/Users/ahonaker/Google Drive/pp/jpp/parsedcharts/";
		
		try {
			File file = new File(targetDir + track + date.format(DateTimeFormatter.ofPattern("MMddYYYY")) + "USA.pdf");
			return mapper.writeValueAsString(addKeyRacesToChart(addHorsesToWatchToChart(chartParser.parse(file))));
			
		} catch (Exception e) {
			throw e;
		
		}
		
	}
	
}
