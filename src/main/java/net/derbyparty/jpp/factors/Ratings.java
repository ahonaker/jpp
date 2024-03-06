package net.derbyparty.jpp.factors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.derbyparty.jpp.object.Entry;
import net.derbyparty.jpp.object.Race;

public class Ratings {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

	public static float calcARating (Race race, Entry entry) {
		
		return 
			entry.getClassRating() / 120 * 45
			+
			entry.getSpeedRating() / 120 * 45
			+ 
			entry.getARatingForm() / 35 * 5
			+ 
			entry.getARatingConnections() * 2 * 5;
	}
	
	public static float calcARatingClass (Race race, Entry entry) {
		
		float factor = 1;

		factor += -1 * (float) entry.getClassShift() / 500;
		factor += -1 * (float) entry.getPurseShift() / 1000000;

	
		return entry.getClassRating() * factor;
	}
	
	public static List<String> identifyPaceAdvantage (Race race) throws Exception {
		
		List <String> advantagedHorses = new ArrayList<String>();
		
		try {
			float E2AvgSorted[] = new float[race.getUnscratchedEntries().size()];
			float SpeedRatingSorted[] = new float[race.getUnscratchedEntries().size()];
			float LatePaceAvgSorted[] = new float[race.getUnscratchedEntries().size()];
			
			for (int i = 0; i < race.getUnscratchedEntries().size(); i ++) {
				E2AvgSorted[i] = race.getUnscratchedEntries().get(i).getE2Avg();
				SpeedRatingSorted[i] = race.getUnscratchedEntries().get(i).getSpeedRating();
			}
			
			Arrays.sort(E2AvgSorted);
			Arrays.sort(SpeedRatingSorted);	
			Arrays.sort(LatePaceAvgSorted);
			
			switch (race.getPaceScenario()) {
				
				case FAST_EARLY_PACE:			
					for (Entry entry : race.getUnscratchedEntries()) {
						if (entry.getRunStyle().equals("E") && entry.getSpeedPoints() >= 6 && 
								(entry.getE2Avg() - 4 > E2AvgSorted[race.getUnscratchedEntries().size()-2] 
										|| entry.getSpeedRating() - 4 > SpeedRatingSorted[race.getUnscratchedEntries().size()-2])) {
							advantagedHorses.add(entry.getName());
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Entry entry : race.getUnscratchedEntries()) {
							if ((entry.getRunStyle().equals("E/P") || entry.getRunStyle().equals("P"))
									&& entry.getSpeedRating() > SpeedRatingSorted[race.getUnscratchedEntries().size()-4]) {
								advantagedHorses.add(entry.getName());
							}
						}						
					}
					
					break;
				case LONE_EARLY_PACE:					
					for (Entry entry : race.getUnscratchedEntries()) {
						if ((entry.getRunStyle().equals("E") || entry.getRunStyle().equals("E/P")) && entry.getSpeedPoints() >= 4 && 
								(entry.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedEntries().size()-2] 
										|| entry.getSpeedRating() - 2 > SpeedRatingSorted[race.getUnscratchedEntries().size()-2])) {
							advantagedHorses.add(entry.getName());
						}
					}
					
					break;
				case HONEST_PACE:
					for (Entry entry : race.getUnscratchedEntries()) {
						if (entry.getRunStyle().equals("E/P") && 
								(entry.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedEntries().size()-2] 
										|| entry.getSpeedRating() - 2 > SpeedRatingSorted[race.getUnscratchedEntries().size()-2])) {
							advantagedHorses.add(entry.getName());
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Entry entry : race.getUnscratchedEntries()) {
							if ((entry.getRunStyle().equals("E/P"))
									&& entry.getSpeedRating() > SpeedRatingSorted[race.getUnscratchedEntries().size()-4]) {
								advantagedHorses.add(entry.getName());
							}
						}						
					}
					
					break;
				case SLOW_PACE:
					for (Entry entry : race.getUnscratchedEntries()) {
						if (entry.getRunStyle().equals("P") && 
								(entry.getE2Avg() - 2 > E2AvgSorted[race.getUnscratchedEntries().size()-2] )) {
							advantagedHorses.add(entry.getName());
						}
					}
					
					if (advantagedHorses.size() == 0) {
						for (Entry entry : race.getUnscratchedEntries()) {
							if ((entry.getRunStyle().equals("P") || entry.getRunStyle().equals("S"))
									&& entry.getLatePaceAvg() > LatePaceAvgSorted[race.getUnscratchedEntries().size()-4]) {
								advantagedHorses.add(entry.getName());
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
	
	public static float calcARatingForm (Race race, Entry entry) {
		
		return entry.getBasicFitness() + entry.getFormPoints() + entry.getFurlongDays() * 5;
	}
	
	public static float calcARatingConnections (Race race, Entry entry) {
		
		float rating = 0;
		
		if (entry.getTrainer().getCurrentMeetStarts() > 10 && entry.getTrainer().getCurrentYearStarts() > 10) {
			rating += (((float) entry.getTrainer().getCurrentYearWins() / entry.getTrainer().getCurrentYearStarts() 
					+ (float) entry.getTrainer().getCurrentMeetWins() / entry.getTrainer().getCurrentMeetStarts()) 
					/ 2);
		} else if (entry.getTrainer().getCurrentYearStarts() > 10) {		
			rating += (float) entry.getTrainer().getCurrentYearWins() / entry.getTrainer().getCurrentYearStarts();
		}
		
		if (entry.getJockey().getCurrentMeetStarts() > 10 && entry.getJockey().getCurrentYearStarts() > 10) {
			rating += (((float) entry.getJockey().getCurrentYearWins() / entry.getJockey().getCurrentYearStarts() 
					+ (float) entry.getJockey().getCurrentMeetWins() / entry.getJockey().getCurrentMeetStarts()) 
					/ 2);
		} else if (entry.getJockey().getCurrentYearStarts() > 10) {		
			rating += (float) entry.getJockey().getCurrentYearWins() / entry.getJockey().getCurrentYearStarts();
		}
		return rating;
	}
}
