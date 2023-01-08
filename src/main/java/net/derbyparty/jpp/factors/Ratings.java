package net.derbyparty.jpp.factors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.Race;

public class Ratings {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

	public static float calcARating (Race race, Horse horse) {
		
		return 
			horse.getClassRating() / 120 * 45
			+
			horse.getSpeedRating() / 120 * 45
			+ 
			horse.getARatingForm() / 35 * 5
			+ 
			horse.getARatingConnections() * 2 * 5;
	}
	
	public static float calcARatingClass (Race race, Horse horse) {
		
		float factor = 1;

		factor += -1 * (float) horse.getClassShift() / 500;
		factor += -1 * (float) horse.getPurseShift() / 1000000;

	
		return horse.getClassRating() * factor;
	}
	
	public static List<Horse> identifyPaceAdvantage (Race race) throws Exception {
		
		List <Horse> advantagedHorses = new ArrayList<Horse>();
		
		try {
			float E2AvgSorted[] = new float[race.getUnscratchedHorses().size()];
			float SpeedRatingSorted[] = new float[race.getUnscratchedHorses().size()];
			float LatePaceAvgSorted[] = new float[race.getUnscratchedHorses().size()];
			
			for (int i = 0; i < race.getUnscratchedHorses().size(); i ++) {
				E2AvgSorted[i] = race.getUnscratchedHorses().get(i).getE2Avg();
				SpeedRatingSorted[i] = race.getUnscratchedHorses().get(i).getSpeedRating();
			}
			
			Arrays.sort(E2AvgSorted);
			Arrays.sort(SpeedRatingSorted);	
			Arrays.sort(LatePaceAvgSorted);
			
			switch (race.getPaceScenario()) {
				
				case FAST_EARLY_PACE:			
					for (Horse horse : race.getUnscratchedHorses()) {
						if (horse.getRunStyle().equals("E") && horse.getSpeedPoints() >= 6 && 
								(horse.getE2Avg() - 4 > E2AvgSorted[race.getUnscratchedHorses().size()-2] 
										|| horse.getSpeedRating() - 4 > SpeedRatingSorted[race.getUnscratchedHorses().size()-2])) {
							advantagedHorses.add(horse);
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Horse horse : race.getUnscratchedHorses()) {
							if ((horse.getRunStyle().equals("E/P") || horse.getRunStyle().equals("P"))
									&& horse.getSpeedRating() > SpeedRatingSorted[race.getUnscratchedHorses().size()-4]) {
								advantagedHorses.add(horse);
							}
						}						
					}
					
					break;
				case LONE_EARLY_PACE:					
					for (Horse horse : race.getUnscratchedHorses()) {
						if ((horse.getRunStyle().equals("E") || horse.getRunStyle().equals("E/P")) && horse.getSpeedPoints() >= 4 && 
								(horse.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedHorses().size()-2] 
										|| horse.getSpeedRating() - 2 > SpeedRatingSorted[race.getUnscratchedHorses().size()-2])) {
							advantagedHorses.add(horse);
						}
					}
					
					break;
				case HONEST_PACE:
					for (Horse horse : race.getUnscratchedHorses()) {
						if (horse.getRunStyle().equals("E/P") && 
								(horse.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedHorses().size()-2] 
										|| horse.getSpeedRating() - 2 > SpeedRatingSorted[race.getUnscratchedHorses().size()-2])) {
							advantagedHorses.add(horse);
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Horse horse : race.getUnscratchedHorses()) {
							if ((horse.getRunStyle().equals("E/P"))
									&& horse.getSpeedRating() > SpeedRatingSorted[race.getUnscratchedHorses().size()-4]) {
								advantagedHorses.add(horse);
							}
						}						
					}
					
					break;
				case SLOW_PACE:
					for (Horse horse : race.getUnscratchedHorses()) {
						if (horse.getRunStyle().equals("P") && 
								(horse.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedHorses().size()-2] )) {
							advantagedHorses.add(horse);
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Horse horse : race.getUnscratchedHorses()) {
							if ((horse.getRunStyle().equals("P") || horse.getRunStyle().equals("S"))
									&& horse.getLatePaceAvg() > LatePaceAvgSorted[race.getUnscratchedHorses().size()-4]) {
								advantagedHorses.add(horse);
							}
						}						
					}
					
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return advantagedHorses;
	}
	
	public static float calcARatingForm (Race race, Horse horse) {
		
		return horse.getBasicFitness() + horse.getFormPoints() + horse.getFurlongDays() * 5;
	}
	
	public static float calcARatingConnections (Race race, Horse horse) {
		
		float rating = 0;
		
		if (horse.getTrainer().getCurrentMeetStarts() > 10 && horse.getTrainer().getCurrentYearStarts() > 10) {
			rating += (((float) horse.getTrainer().getCurrentYearWins() / horse.getTrainer().getCurrentYearStarts() 
					+ (float) horse.getTrainer().getCurrentMeetWins() / horse.getTrainer().getCurrentMeetStarts()) 
					/ 2);
		} else if (horse.getTrainer().getCurrentYearStarts() > 10) {		
			rating += (float) horse.getTrainer().getCurrentYearWins() / horse.getTrainer().getCurrentYearStarts();
		}
		
		if (horse.getJockey().getCurrentMeetStarts() > 10 && horse.getJockey().getCurrentYearStarts() > 10) {
			rating += (((float) horse.getJockey().getCurrentYearWins() / horse.getJockey().getCurrentYearStarts() 
					+ (float) horse.getJockey().getCurrentMeetWins() / horse.getJockey().getCurrentMeetStarts()) 
					/ 2);
		} else if (horse.getJockey().getCurrentYearStarts() > 10) {		
			rating += (float) horse.getJockey().getCurrentYearWins() / horse.getJockey().getCurrentYearStarts();
		}
		return rating;
	}
}
