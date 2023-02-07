package net.derbyparty.jpp.pastperformanceparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.derbyparty.jpp.object.Angle;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.Stat;
import net.derbyparty.jpp.object.TrackBias;

public class PastPerformanceParser {
	
	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
    final static Pattern RACE_HEADER =
            Pattern.compile("^Ultimate PP's w\\/ QuickPlay Comments.+ Race (\\d+)");
    
    final static Pattern RACE_DESCRIPTION_LINE_BEFORE = 
    		Pattern.compile("^PARS:\\s");
    
    final static Pattern RACE_DESCRIPTION_LINE_AFTER = 
    		Pattern.compile("^Post Time:\\s");
    
    final static Pattern RUNNER_LINE =
    		Pattern.compile("(\\d+)?(pp(\\d+))?\\s([\\sa-zA-Z\\'\\.]+)\\s(\\((E|P|E\\/P|S|NA)\\s[0-8]+\\))(Own: ([a-zA-Z\\s]+))?");

    final static Pattern PRIME_POWER =
    		Pattern.compile("^Prime Power: ([0-9\\.]+)");
    
    final static Pattern SIRE_STATS =
    		Pattern.compile("^Sire Stats:\\s*AWD\\s*([\\d\\.]+)\\s*([\\d]+)\\%Mud\\s*(\\d+)MudSts\\s*(([\\d]+)\\%Turf\\s*)?(([\\d]+)\\%1stT\\s*)?(([\\d]+)\\%1st\\s*)?([\\d\\.]+)spi");	

    final static Pattern DAMS_SIRE_STATS =
    		Pattern.compile("^Dam\\'sSire:\\s*AWD\\s*([\\d\\.]+)\\s*([\\d]+)\\%Mud\\s*(\\d+)MudSts\\s*(([\\d]+)\\%Turf\\s*)?(([\\d]+)\\%1stT\\s*)?(([\\d]+)\\%1st\\s*)?([\\d\\.]+)spi");	

    final static Pattern DAM_STATS =
    		Pattern.compile("^Dam\\'s\\s*Stats:\\s*([a-zA-Z]+)\\s*(([\\d]+)\\%2yo)?\\s*(([\\d]+)trfW)?\\s*([\\d]+)str\\s*([\\d]+)w\\s*([\\d]+)sw\\s*([\\d\\.]+)?dpi");	

    final static Pattern JOCKEY_TYPE_STATS =
    		Pattern.compile("^\\+*JKYw\\/\\s*([EPSNA]+\s*types)\\s*([0-9]+)\\s*([0-9]+)\\%\\s*([0-9]+)\\%\\s*([\\-\\+0-9.]+)");	

    final static  Pattern JOCKEY_TRN_STATS =
    		Pattern.compile("^\\+*JKYw\\/\\s*(Trn\\s*L60)\\s*([0-9]+)\\s*([0-9]+)\\%\\s*([0-9]+)%\\s*([\\-\\+0-9.]+)");	
    
    final static Pattern ANGLE_LINE = 
    		Pattern.compile("([ñ|×]\\s)([a-zA-Z0-9\\%\\s\\':\\(\\)\\-\\/\\+\\.]+)");
    
    final static Pattern RANK_LIST = 
    		Pattern.compile("^(([\\d\\.NA]+)\\s+([a-zA-Z\\'\\s]+))");
    
    final static Pattern RANK_LIST_MEMBER = 
    		Pattern.compile("^([\\d\\.NA]+)\\s+([a-zA-Z\\'\\s]+)");
	
	public static void extractPastPerformance(List<Race> races, File PPFile) throws Exception {    
	    
		try {
			PDDocument doc = PDDocument.load(PPFile);  
			PDFTextStripper stripper = new PDFTextStripper();
			//stripper.setSortByPosition(true);
			String text = stripper.getText(doc);
			String lines[] = text.split("\\r?\\n");
			doc.close();
			
			Race thisRace = null;;
			Horse horse = null;
			
			for (int i = 0; i < lines.length; i++) {
				Matcher firstRaceMatcher = RACE_HEADER.matcher(lines[i]);
				if (firstRaceMatcher.find()) {
					for (Race race: races) {
						if (race.getRaceNumber() == Integer.parseInt(firstRaceMatcher.group(1).trim())) {
							thisRace = race;
							break;
						}
					}
					
				}
				if (thisRace != null) break;
			}
			
			List<String> rankStrings = new ArrayList<String>();
					
			for (int i = 0; i < lines.length; i++) {			
				Matcher raceMatcher = RACE_HEADER.matcher(lines[i]);
				if (raceMatcher.find() && Integer.parseInt(raceMatcher.group(1).trim()) != thisRace.getRaceNumber()) {
					for (Race race: races) {
						if (race.getRaceNumber() == Integer.parseInt(raceMatcher.group(1).trim())) {
							List<String> currentStrings = rankStrings.subList(thisRace.getHorses().size() * 2, thisRace.getHorses().size() * 3);							
							List<String> last3Strings = rankStrings.subList(thisRace.getHorses().size() * 3, thisRace.getHorses().size() * 4);
							
							for (String current : currentStrings) {
								Matcher matcher = RANK_LIST_MEMBER.matcher(current);
								matcher.find();
								if (!matcher.group(1).equals("NA")) {
									thisRace.getHorseWithName(matcher.group(2)).setBrisCurrentClass(Float.parseFloat(matcher.group(1)));
								}
							}
							
							for (String last3 : last3Strings) {
								Matcher matcher = RANK_LIST_MEMBER.matcher(last3);
								matcher.find();
								if (!matcher.group(1).equals("NA")) {
									thisRace.getHorseWithName(matcher.group(2)).setBrisAvgLast3Class(Float.parseFloat(matcher.group(1)));
								}
							}							
							
							rankStrings = new ArrayList<String>();
							
							thisRace = race;
						}
					}
				}			
				
				Matcher raceDescriptionLineBeforeMatcher = RACE_DESCRIPTION_LINE_BEFORE.matcher(lines[i]);
				if (raceDescriptionLineBeforeMatcher.find()) {
					i++;
					String description = lines[i];
					i++;
					Matcher raceDescriptionLineAfterMatcher = RACE_DESCRIPTION_LINE_AFTER.matcher(lines[i]);
					while (!raceDescriptionLineAfterMatcher.find()) {
						description += " " + lines[i];
						i++;
						raceDescriptionLineAfterMatcher = RACE_DESCRIPTION_LINE_AFTER.matcher(lines[i]);
					}
					thisRace.setDescription(description);
				}
					
				Matcher runnerMatcher = RUNNER_LINE.matcher(lines[i]);
				if (runnerMatcher.find()) {
					horse = thisRace.getHorseWithName(runnerMatcher.group(4));
				}
				
				Matcher primePowerMatcher = PRIME_POWER.matcher(lines[i]);
				if (horse != null && primePowerMatcher.find()) horse.setPrimePower(Float.parseFloat(primePowerMatcher.group(1)));
								
				Matcher sireStatsMatcher = SIRE_STATS.matcher(lines[i]);
				if (horse != null && sireStatsMatcher.find()) {
					if (sireStatsMatcher.group(1) != null) horse.setSireAWD(Float.parseFloat(sireStatsMatcher.group(1)));
					if (sireStatsMatcher.group(2) != null) horse.setSireMudPercent(Integer.parseInt(sireStatsMatcher.group(2)));
					if (sireStatsMatcher.group(3) != null) horse.setSireMudStarts(Integer.parseInt(sireStatsMatcher.group(3)));
					if (sireStatsMatcher.group(5) != null) horse.setSireTurfPercent(Integer.parseInt(sireStatsMatcher.group(5)));
					if (sireStatsMatcher.group(7) != null) horse.setSireFirstTurfPercent(Integer.parseInt(sireStatsMatcher.group(7)));
					if (sireStatsMatcher.group(9) != null) horse.setSireFirstPercent(Integer.parseInt(sireStatsMatcher.group(9)));
					if (sireStatsMatcher.group(10) != null) horse.setSireSPI(Float.parseFloat(sireStatsMatcher.group(10)));
				}
					
				Matcher damsSireStatsMatcher = DAMS_SIRE_STATS.matcher(lines[i]);
				if (horse != null && damsSireStatsMatcher.find()) {
					if (damsSireStatsMatcher.group(1) != null) horse.setDamSireAWD(Float.parseFloat(damsSireStatsMatcher.group(1)));
					if (damsSireStatsMatcher.group(2) != null) horse.setDamSireMudPercent(Integer.parseInt(damsSireStatsMatcher.group(2)));
					if (damsSireStatsMatcher.group(3) != null) horse.setDamSireMudStarts(Integer.parseInt(damsSireStatsMatcher.group(3)));
					if (damsSireStatsMatcher.group(5) != null) horse.setDamSireTurfPercent(Integer.parseInt(damsSireStatsMatcher.group(5)));
					if (damsSireStatsMatcher.group(7) != null) horse.setDamSireFirstTurfPercent(Integer.parseInt(damsSireStatsMatcher.group(7)));
					if (damsSireStatsMatcher.group(9) != null) horse.setDamSireFirstPercent(Integer.parseInt(damsSireStatsMatcher.group(9)));
					if (damsSireStatsMatcher.group(10) != null) horse.setDamSireSPI(Float.parseFloat(damsSireStatsMatcher.group(10)));
				}				

				Matcher damStatsMatcher = DAM_STATS.matcher(lines[i]);
				if (horse != null && damStatsMatcher.find()) {
					if (damStatsMatcher.group(1) != null) horse.setDamDescription(damStatsMatcher.group(1));
					if (damStatsMatcher.group(3) != null) horse.setDamTwoYearOldPercent(Integer.parseInt(damStatsMatcher.group(3)));
					if (damStatsMatcher.group(5) != null) horse.setDamTurfWinners(Integer.parseInt(damStatsMatcher.group(5)));
					if (damStatsMatcher.group(6) != null) horse.setDamStarters(Integer.parseInt(damStatsMatcher.group(6)));
					if (damStatsMatcher.group(7) != null) horse.setDamWinners(Integer.parseInt(damStatsMatcher.group(7)));
					if (damStatsMatcher.group(8) != null) horse.setDamStakesWinners(Integer.parseInt(damStatsMatcher.group(8)));
					if (damStatsMatcher.group(9) != null) horse.setDamDPI(Float.parseFloat(damStatsMatcher.group(9)));
				}		
				
				Matcher jkyTypeStatsMatcher = JOCKEY_TYPE_STATS.matcher(lines[i]);
				if (horse != null && jkyTypeStatsMatcher.find()) {
					Stat stat = Stat.builder()
						.withCategory(jkyTypeStatsMatcher.group(1))
						.withStarts(Integer.parseInt(jkyTypeStatsMatcher.group(2)))
						.withWinPercent(Float.parseFloat(jkyTypeStatsMatcher.group(3)))
						.withITMPercent(Float.parseFloat(jkyTypeStatsMatcher.group(4)))
						.withROI(Float.parseFloat(jkyTypeStatsMatcher.group(5)))
						.build();
					Boolean exists = false;
					for (Stat existingStat : horse.getJockey().getStats()) {
						if (stat.getCategory().equals(existingStat.getCategory())) exists = true;
					}
					if (!exists) horse.getJockey().getStats().add(stat);
				}
				
				Matcher jkyTrainerStatsMatcher = JOCKEY_TRN_STATS.matcher(lines[i]);
				if (horse != null && jkyTrainerStatsMatcher.find()) {
					Stat stat = Stat.builder()
							.withCategory(jkyTrainerStatsMatcher.group(1))
							.withStarts(Integer.parseInt(jkyTrainerStatsMatcher.group(2)))
							.withWinPercent(Float.parseFloat(jkyTrainerStatsMatcher.group(3)))
							.withITMPercent(Float.parseFloat(jkyTrainerStatsMatcher.group(4)))
							.withROI(Float.parseFloat(jkyTrainerStatsMatcher.group(5)))
							.build();
					Boolean exists = false;
					for (Stat existingStat : horse.getJockey().getStats()) {
						if (stat.getCategory().equals(existingStat.getCategory())) exists = true;
					}
					if (!exists) horse.getJockey().getStats().add(stat);
					
				}
				
				Matcher angleLineMatcher = ANGLE_LINE.matcher(lines[i]);
				if (horse != null) {
					while (angleLineMatcher.find()) {
						List<Angle> angles = horse.getAngles();
						if (angles == null) angles = new ArrayList<Angle>();
						List<Angle> newAngles = new ArrayList<Angle>();
						for (Angle angle : angles) {
							if (!angle.getText().equals("Augmented")) newAngles.add(angle);
						}						
						angles.add(
							Angle.builder()
							.withType(angleLineMatcher.group(1).charAt(0) == '×' ? "-" : "+")
							.withText(angleLineMatcher.group(2))
							.withSource("Augmented")
							.build() 
						);
						horse.setAngles(angles);
					}
				}
				
				if (lines[i].trim().equals("* MEET Totals *") || lines[i].trim().equals("* Week Totals *")) {
					horse = null;
					Matcher line1 = Pattern.compile(".*Speed Bias:\\s*([\\d]+)\\% WnrAvgBL").matcher(lines[i+1]);
					Matcher line2 = Pattern.compile("\\# Races:\\s*([\\d]+)\\s*([0-9\\s\\-\\/]+)\\s*1stCall:\\s*([\\d\\.]+)").matcher(lines[i+2]);
					Matcher line3 = Pattern.compile("\\%Wire:\\s*([\\d]+)\\% 2ndCall:\\s*([\\d\\.]+)").matcher(lines[i+3]);
					Matcher line7 = Pattern.compile("\\%Races Won\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%\\s*)?(([\\d]+)\\%)?").matcher(lines[i+8]);
					Matcher line8 = Pattern.compile("\\%Races Won\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%\\s*)?(([\\d]+)\\%)?").matcher(lines[i+8]);
					Matcher line11 = Pattern.compile("Avg Win \\%\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%\\s*)?(([\\d]+)\\%)?").matcher(lines[i+11]);
					Matcher line12 = Pattern.compile("Avg Win \\%\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%)?\\s*(([\\d]+)\\%\\s*)?(([\\d]+)\\%)?").matcher(lines[i+12]);
					
					if (line1.find() && line2.find() && line3.find()) {
						
						TrackBias trackBias = TrackBias.builder()
							.withNumberOfRaces(Integer.parseInt(line2.group(1)))
							.withDates(line2.group(2))
							.withWinnerABLFirstCall(Float.parseFloat(line2.group(3)))
							.withSpeedBias(Integer.parseInt(line1.group(1)))
							.withWirePercent(Integer.parseInt(line3.group(1)))
							.withWinnerABLSecondCall(Float.parseFloat(line3.group(2)))
							.build();
						
							if (line7.find()) {
								trackBias.setPercentEWon((line7.group(2) == null) ? 0 : Integer.parseInt(line7.group(2)));
								trackBias.setPercentEPWon((line7.group(4) == null) ? 0 : Integer.parseInt(line7.group(4)));
								trackBias.setPercentPWon((line7.group(6) == null) ? 0 : Integer.parseInt(line7.group(6)));
								trackBias.setPercentSWon((line7.group(8) == null) ? 0 : Integer.parseInt(line7.group(8)));	
							} else if (line8.find()) {
								trackBias.setPercentEWon((line8.group(2) == null) ? 0 : Integer.parseInt(line8.group(2)));
								trackBias.setPercentEPWon((line8.group(4) == null) ? 0 : Integer.parseInt(line8.group(4)));
								trackBias.setPercentPWon((line8.group(6) == null) ? 0 : Integer.parseInt(line8.group(6)));
								trackBias.setPercentSWon((line8.group(8) == null) ? 0 : Integer.parseInt(line8.group(8)));	
							}

							if (line11.find()) {
								 trackBias.setPercentRailWon((line11.group(2) == null) ? 0 : Integer.parseInt(line11.group(2)));
								 trackBias.setPercent123Won((line11.group(4) == null) ? 0 : Integer.parseInt(line11.group(4)));
								 trackBias.setPercent4567Won((line11.group(6) == null) ? 0 : Integer.parseInt(line11.group(6)));
								 trackBias.setPercent8PlusWon((line11.group(8) == null) ? 0 : Integer.parseInt(line11.group(8)));
							} else if (line12.find()) {
								 trackBias.setPercentRailWon((line12.group(2) == null) ? 0 : Integer.parseInt(line12.group(2)));
								 trackBias.setPercent123Won((line12.group(4) == null) ? 0 : Integer.parseInt(line12.group(4)));
								 trackBias.setPercent4567Won((line12.group(6) == null) ? 0 : Integer.parseInt(line12.group(6)));
								 trackBias.setPercent8PlusWon((line12.group(8) == null) ? 0 : Integer.parseInt(line12.group(8)));								
							}

						if (lines[i].trim().equals("* MEET Totals *")) {
							thisRace.setMeetTrackBias(trackBias);
						} else {
							thisRace.setWeekTrackBias(trackBias);
						}
					}
					
					i += 12;
				}
				Matcher rankListMatcher = RANK_LIST.matcher(lines[i]);

				if (rankListMatcher.find()) {
					if (rankListMatcher.group(0).equals(lines[i])) {
						rankStrings.add(lines[i]);
					}
				}

				
			}
			
		} catch (Exception e) {
			throw e;
		}
	} 
	
	public static String extractText (File PPFile) throws Exception {
		
		try {
			PDDocument doc = PDDocument.load(PPFile);  
			PDFTextStripper stripper = new PDFTextStripper();
			//stripper.setSortByPosition(true);
			String text = stripper.getText(doc);
			String lines[] = text.split("\\r?\\n");
			for (int i = 0; i < lines.length; i++) {
				if (RACE_HEADER.matcher(lines[i]).find()) lines[i] += " <-- RACE_HEADER";
				if (RACE_DESCRIPTION_LINE_BEFORE.matcher(lines[i]).find()) lines[i] += " <-- RACE_DESCRIPTION_LINE_BEFORE";
				if (RACE_DESCRIPTION_LINE_AFTER.matcher(lines[i]).find()) lines[i] += " <-- RACE_DESCRIPTION_LINE_AFTER";
				if (RUNNER_LINE.matcher(lines[i]).find()) lines[i] += " <-- RUNNER_LINE";
				if (PRIME_POWER.matcher(lines[i]).find()) lines[i] += " <-- PRIME_POWER";
				if (SIRE_STATS.matcher(lines[i]).find()) lines[i] += " <-- SIRE_STATS";
				if (DAMS_SIRE_STATS.matcher(lines[i]).find()) lines[i] += " <-- DAMS_SIRE_STATS";
				if (DAM_STATS.matcher(lines[i]).find()) lines[i] += " <-- DAM_STATS";
				if (JOCKEY_TYPE_STATS.matcher(lines[i]).find()) lines[i] += " <-- JOCKEY_TYPE_STATS";
				if (JOCKEY_TRN_STATS.matcher(lines[i]).find()) lines[i] += " <-- JOCKEY_TRN_STATS";
				if (ANGLE_LINE.matcher(lines[i]).find()) lines[i] += " <-- ANGLE_LINE";
				Matcher rankListMatcher = RANK_LIST.matcher(lines[i]);
				if (rankListMatcher.find()) {
					if (rankListMatcher.group(0).equals(lines[i])) lines[i] += " <-- RANK_LIST";
					
				}
				//if (!lines[i].contains("<--")) System.out.println(lines[i]);
				
				
			}
			doc.close();
			return mapper.writeValueAsString(lines);
			
		} catch (Exception e) {
			throw e;
		}
	}

}
