package net.derbyparty.jpp.factors;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.Horse;
import net.derbyparty.jpp.object.PaceScenarioType;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceType;
import net.derbyparty.jpp.object.Stat;
import net.derbyparty.jpp.object.Workout;

public class Factors {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	public static float CalcFraction (float fraction, float length) {
		if (fraction == 0) return 0;
		return  (float) (fraction + (length) * 0.2);
	}
	
	public static float CalcE1Avg (List<PastPerformance> pps) throws Exception {
		
		try {
			
			int total = 0;
			int count = 0;
			
			for (PastPerformance pp : pps) {
				int pace = (pp.getFurlongs() < 8) ? pp.getPaceFigure2F() : pp.getPaceFigure4F();
				if (pace > 0) {
					total += pace;
					count++;
				}
			}
			if (count == 0) return 0;
			return (float) total / count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcRaceE1Avg (List<Horse> horses) throws Exception {
		
		try {
			float max = 0;
			for (Horse horse : horses) {
				if (horse.getE1Avg() != 0) max = (max < horse.getE1Avg()) ? horse.getE1Avg() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcE2Avg (List<PastPerformance> pps) throws Exception {
		
		try {
			int total = 0;
			int count = 0;

			for (PastPerformance pp : pps) {
				float pace = (pp.getFurlongs() < 8) ? pp.getPaceFigure4F() : pp.getPaceFigure6F();
				if (pace > 0) {
					total += pace;
					count++;
				}

			}
			if (count ==0) return 0;
			return (float) total / count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcRaceE2Avg (List<Horse> horses) throws Exception {
		
		try {
			float max = 0;
			for (Horse horse : horses) {
				if (horse.getE2Avg() != 0) max = (max < horse.getE2Avg()) ? horse.getE2Avg() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static int CalcMaxE2 (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int max = 0;
			for (PastPerformance pp : pps) {
				if (pp.getFurlongs() < 8) {
					max = (max < pp.getPaceFigure4F()) ? pp.getPaceFigure4F() : max;
				} else {
					max = (max < pp.getPaceFigure6F()) ? pp.getPaceFigure6F() : max;
				}
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static int CalcRaceMaxE2 (List<Horse> horses) throws Exception {
		
		try {
			int max = 0;
			for (Horse horse : horses) {
				max = (max < horse.getMaxE2()) ? horse.getMaxE2() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcLatePaceAvg (List<PastPerformance> pps) throws Exception {
		
		try {
			int total = 0;
			int count = 0;

			for (PastPerformance pp : pps) {
				if (pp.getPaceFigureLate() > 0) {
					total += pp.getPaceFigureLate();
					count++;
				}
			}
			if (count == 0) return 0;
			return (float) total / count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcAverageAdjustedSpeedRating (List<PastPerformance> pps) throws Exception {
		
		try {
			int total = 0;
			int count = 0;
			
			for (PastPerformance pp : pps) {
				if (pp.getAdjustedSpeedRating() > 0) {
					total += pp.getAdjustedSpeedRating();
					count++;
				}
			}
			if (count == 0) return 0;
			return (float) total / count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcRaceAverageAdjustedSpeedRating (List<Horse> horses) throws Exception {
		
		try {
			float max = 0;
			for (Horse horse : horses) {
				if (horse.getAvgAdjustedSpeedRating() != 0) max = (max < horse.getAvgAdjustedSpeedRating()) ? horse.getAvgAdjustedSpeedRating() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static ObjectNode CalcClosingRatio (List<PastPerformance> pps) throws Exception {
		
		ObjectNode result = mapper.createObjectNode();
		
		try {
			if (pps.size() == 0) return result;
			
			int E1Count = 0;
			int E2Count = 0;
			int StretchCount = 0;
			int FinishCount = 0;
			
			float E1Sum = 0;
			float E2Sum = 0;
			float StretchSum = 0;
			float FinishSum = 0;
			
			for (PastPerformance pp : pps) {
				
				try {
					E1Sum += ((float) pp.getNumberOfEntrants() - Integer.parseInt(pp.getFirstCallPosition()) + 1)/pp.getNumberOfEntrants(); 
					E1Count++;
				} catch (NumberFormatException e) {
					
				}
				try {
					E2Sum += ((float) pp.getNumberOfEntrants() - Integer.parseInt(pp.getSecondCallPosition()) + 1)/pp.getNumberOfEntrants(); 
					E2Count++;
				} catch (NumberFormatException e) {
					
				}				
				try {
					StretchSum += ((float) pp.getNumberOfEntrants() - Integer.parseInt(pp.getStretchPosition()) + 1)/pp.getNumberOfEntrants();
					StretchCount++;
				} catch (NumberFormatException e) {
					
				}
				try {
					FinishSum += ((float)pp.getNumberOfEntrants() - Integer.parseInt(pp.getFinishPosition()) + 1)/pp.getNumberOfEntrants();
					FinishCount++;
				} catch (NumberFormatException e) {
					
				}
				
				
			}
			
			result.put("EarlyPosition", ((Float) E1Sum/E1Count + E2Sum/E2Count));
			result.put("LatePosition", ((Float) StretchSum/StretchCount + FinishSum/FinishCount));
			result.put("ClosingRatio", ((Float) StretchSum/StretchCount + FinishSum/FinishCount) / (E1Sum/E1Count + E2Sum/E2Count));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
		return result;
	}
	
	public static int CalcLatePaceBestLast3 (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int max = 0;
			int count = 0;
			for (PastPerformance pp : pps) {
				if (count < 3) {
					if (pp.getPaceFigureLate() > 0) {
						count++;
						max = (max < pp.getPaceFigureLate()) ? pp.getPaceFigureLate() : max;
					}
				}					
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static int CalcRaceLatePaceBestLast3 (List<Horse> horses) throws Exception {
		
		try {
			int max = 0;
			for (Horse horse : horses) {
				max = (max < horse.getLatePaceBestLast3()) ? horse.getLatePaceBestLast3() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static float CalcPaceAdjustedLate (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;			
			return pps.get(0).getPaceFigureLate() - (2 * pps.get(0).getSecondCallBeatenLengthsLeader());
						
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static int CalcLatePaceLast (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			return pps.get(0).getPaceFigureLate();
						
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static PaceScenarioType DeterminePaceScenario (List<Horse> horses) throws Exception {
		
		try {
			int ECount = 0, EPCount = 0; 
			
			for (Horse horse : horses) {
				if (horse.getRunStyle().equals("E")) ECount++;
				if (horse.getRunStyle().equals("E/P")) EPCount++;
			}
			
			if (ECount > 1) return PaceScenarioType.FAST_EARLY_PACE;
			if ((ECount == 1 && EPCount == 0) || (ECount == 0 && EPCount == 1)) return PaceScenarioType.LONE_EARLY_PACE;
			if ((ECount + EPCount) > 1) return PaceScenarioType.HONEST_PACE;
			return PaceScenarioType.SLOW_PACE;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcClassRating (List<PastPerformance> pps, Race race) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int total = 0, weight = 0;
			for (PastPerformance pp : pps) {
				int thisWeight = 0;
				if (pp.getTurfFlag().equals(race.getTurfFlag())) thisWeight += 2;
				if (pp.getFurlongs() == race.getFurlongs()) {
					 thisWeight +=  2;
				} else if ((pp.getFurlongs() < 8 && race.getFurlongs() < 8) || (pp.getFurlongs() >= 8 && race.getFurlongs() >= 8)) {
					thisWeight +=1;
				}
				if (race.getDate().minusDays(45).isBefore(pp.getRaceDate())) {
					thisWeight += 3;
				} else if ((race.getDate().minusDays(90).isBefore(pp.getRaceDate()))) {
					thisWeight += 2;
				} else if ((race.getDate().minusDays(180).isBefore(pp.getRaceDate()))) {
					thisWeight += 1;
				}
				if (thisWeight > 0 && pp.getSpeedPar() > 0) {
					total += pp.getSpeedPar() * thisWeight;
					weight += thisWeight;
				}
			}
			return (weight == 0) ? 0 : (float) total / weight;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcAverageCompetitiveLevel (List<PastPerformance> pps, Race race) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int total = 0, weight = 0;
			for (PastPerformance pp : pps) {
				int thisWeight = 0;
				if (pp.getFinishPosition().equals("1")|| pp.getFinishPosition().equals("2") || pp.getFinishPosition().equals("3") 
						|| (pp.getFinishBeatenLengthsOnly() <= 3 && NumberUtils.isCreatable(pp.getFinishPosition()))) {
					if (pp.getTurfFlag().equals(race.getTurfFlag())) thisWeight += 2;
					if (pp.getFurlongs() == race.getFurlongs()) {
						 thisWeight +=  2;
					} else if ((pp.getFurlongs() < 8 && race.getFurlongs() < 8) || (pp.getFurlongs() >= 8 && race.getFurlongs() >= 8)) {
						thisWeight +=1;
					}
					if (race.getDate().minusDays(45).isBefore(pp.getRaceDate())) {
						thisWeight += 3;
					} else if ((race.getDate().minusDays(90).isBefore(pp.getRaceDate()))) {
						thisWeight += 2;
					} else if ((race.getDate().minusDays(180).isBefore(pp.getRaceDate()))) {
						thisWeight += 1;
					}
					if (thisWeight > 0 && pp.getSpeedPar() > 0) {
						total += pp.getSpeedPar() * thisWeight;
						weight += thisWeight;
					}
				}
			}
			return (weight == 0) ? 0 : (float) total / weight;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcRaceStrength (PastPerformance pp) throws Exception {
		
		try {
			if (pp.getBRISSpeedRating() == 0) return 0;
			return (float) (pp.getBRISSpeedRating() + (pp.getFurlongs() < 8 ? 1.5 : 1) * pp.getFinishBeatenLengthsOnly());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcLastRaceStrength (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			return pps.get(0).getRaceStrength();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcClassShift (List<PastPerformance> pps, Race race) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;	
			if (race.getParSpeed() == 0 || pps.get(0).getSpeedPar() == 0) return 0;
			return race.getParSpeed() - pps.get(0).getSpeedPar(); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}	
	
	public static int CalcPurseShift (List<PastPerformance> pps, Race race) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;	
			return race.getPurse() - pps.get(0).getPurse(); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}	
	
	public static float CalcSpeedRating (List<PastPerformance> pps, Race race) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int total = 0, weight = 0;
			for (PastPerformance pp : pps) {
				int thisWeight = 0;
				if (pp.getSurface().equals(race.getSurface())) thisWeight += 2;
				if (pp.getFurlongs() == race.getFurlongs()) {
					 thisWeight +=  2;
				} else if ((pp.getFurlongs() < 8 && race.getFurlongs() < 8) || (pp.getFurlongs() >= 8 && race.getFurlongs() >= 8)) {
					thisWeight +=1;
				}
				if (race.getDate().minusDays(45).isBefore(pp.getRaceDate())) {
					thisWeight += 3;
				} else if ((race.getDate().minusDays(90).isBefore(pp.getRaceDate()))) {
					thisWeight += 2;
				} else if ((race.getDate().minusDays(180).isBefore(pp.getRaceDate()))) {
					thisWeight += 1;
				}
				if (thisWeight > 0 && pp.getBRISSpeedRating() > 0) {
					total += pp.getBRISSpeedRating() * thisWeight;
					weight += thisWeight;
				}
			}
			return (weight == 0) ? 0 : (float) total / weight;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcRaceMaxSpeedRating (List<Horse> horses) throws Exception {
		
		try {
			float max = 0;
			for (Horse horse : horses) {
				max = (max < horse.getSpeedRating()) ? horse.getSpeedRating() : max;
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	public static int CalcRaceMaxSpeed (List<Horse> horses) throws Exception {
		
		try {
			int max = 0;
			for (Horse horse : horses) {
				for (PastPerformance pp : horse.getUnignoredPastPerformances()) {
					max = (max < pp.getBRISSpeedRating()) ? pp.getBRISSpeedRating() : max;
				}
			}
			return max;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
			
	}
	
	private static int CalcBonusWorkoutPoints (Workout workout) {
		if (workout.getTimeOfWorkout() < workout.getFurlongs() * 12) {
			return 2;
		} else if (workout.getTimeOfWorkout() < (workout.getFurlongs() * 12) + 1) {
			return 1;
		}
		return 0;
		
	}
	
	private static int CalcWorkoutRacePoints (Workout workout, PastPerformance race) {
		int diff = (int) Math.abs(workout.getDateOfWorkout().until(race.getRaceDate(), ChronoUnit.DAYS) - 7);
		return 6 - (diff > 6 ? 6 : diff);		
	}
	
	private static int CalcRaceWorkoutPoints (PastPerformance race, Workout workout) {
		int diff = (int) Math.abs(race.getRaceDate().until(workout.getDateOfWorkout(), ChronoUnit.DAYS) - 7);
		return 6 - (diff > 6 ? 6 : diff);		
	}
	
	private static int CalcWorkoutWorkoutPoints (Workout workout1, Workout workout2) {
		int diff = (int) Math.abs(workout1.getDateOfWorkout().until(workout2.getDateOfWorkout(), ChronoUnit.DAYS) - 7);
		return 6 - (diff > 6 ? 6 : diff);		
	}
	
	public static int CalcBasicFitness (List<PastPerformance> pps, List<Workout> workouts) throws Exception {
		
		try {
			if (workouts.size() == 0) return 0;
			PastPerformance race = null;
			int points = 0;
			
			Boolean noRaces = pps.size() < 1;	
			if (!noRaces) {
				race = pps.get(0);
			}
			
			int workoutsSince = 0;
			
			if (!noRaces) {
				for (int i = 0; i < (workouts.size() > 3 ? 3 : workouts.size()); i++) {
					if (race.getRaceDate().isBefore(workouts.get(i).getDateOfWorkout())) workoutsSince++;
				}
			} else workoutsSince = (workouts.size() > 3 ? 3 :0);
			
			switch (workoutsSince) {
			case 0:
				if (workouts.size() > 0 && !noRaces) {
					points += CalcWorkoutRacePoints(workouts.get(0), race);
					points += CalcBonusWorkoutPoints(workouts.get(0));
				}
				if (workouts.size() > 1) {
					points += CalcWorkoutWorkoutPoints(workouts.get(1), workouts.get(0));
					points += CalcBonusWorkoutPoints(workouts.get(1));
				}
				if (workouts.size() > 2) {
					points += CalcWorkoutWorkoutPoints(workouts.get(2), workouts.get(1));
					points += CalcBonusWorkoutPoints(workouts.get(2));
				}
				break;
				
			case 1:
				points += CalcBonusWorkoutPoints(workouts.get(0));
				
				if (!noRaces) {
					points += CalcRaceWorkoutPoints(race, workouts.get(0));	
				}
				
				if (workouts.size() > 1 && !noRaces) {
					points += CalcWorkoutRacePoints(workouts.get(1), race);
					points += CalcBonusWorkoutPoints(workouts.get(1));
				} 
				if (workouts.size() > 1 && noRaces) {
					points += CalcWorkoutWorkoutPoints(workouts.get(1), workouts.get(2));
					points += CalcBonusWorkoutPoints(workouts.get(1));
				}
				
				if (workouts.size() > 2) {
					points += CalcWorkoutWorkoutPoints(workouts.get(2), workouts.get(1));
					points += CalcBonusWorkoutPoints(workouts.get(2));
				}				
				break;
			
			case 2:
				points += CalcWorkoutWorkoutPoints(workouts.get(1), workouts.get(0));
				points += CalcBonusWorkoutPoints(workouts.get(0));
				points += CalcBonusWorkoutPoints(workouts.get(1));
				
				if (!noRaces) {
					points += CalcRaceWorkoutPoints(race, workouts.get(1));
				}
				break;
			case 3:
				points += CalcWorkoutWorkoutPoints(workouts.get(1), workouts.get(0));
				points += CalcBonusWorkoutPoints(workouts.get(0));
				points += CalcWorkoutWorkoutPoints(workouts.get(2), workouts.get(1));
				points += CalcBonusWorkoutPoints(workouts.get(1));
				if (workouts.size() >= 4) points += CalcWorkoutWorkoutPoints(workouts.get(3), workouts.get(2));
				points += CalcBonusWorkoutPoints(workouts.get(2));		
				
				if (!noRaces && workouts.size() >= 4) {
					points += CalcRaceWorkoutPoints(race, workouts.get(3));
				}
				break;
			}
			
			return points;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}	
	
	public static float CalcLast5AverageSpeed (int p, List<PastPerformance> pps) throws Exception {
		
		try {
			int total = 0;
			int count = 0;
			for (int i = p; i < (pps.size() - p > 5 ? p + 5 : pps.size()); i++) {
				if (pps.get(i).getBRISSpeedRating() > 0) {
					total += pps.get(i).getBRISSpeedRating();
					count++;
				}
			}
			return (float) total / count;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static int CalcFormPoints (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() < 2) return 0;
			if (pps.size() < 3) {
				if (pps.get(0).getLast5AverageSpeed() > pps.get(1).getLast5AverageSpeed()) {
					return 1;
				}
				return 0;
			}
			float r0 = pps.get(0).getLast5AverageSpeed();
			float r1 = pps.get(1).getLast5AverageSpeed();
			float r2 = pps.get(2).getLast5AverageSpeed();
			
			if (r0 > r1 && r1 >= r2) return 5;
			if (r0 > r1 && r1 < r2 && r0 >= r2) return 3;
			if (r0 > r1 && r1 < r2 && r0 < r2) return 2;
			if (r0 < r1 && r1 >= r2 && r0 >= r2) return 1;
			
			if (r0 < r1 && r1 <= r2) return -2;
			return 0;			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static float CalcFurlongDays (List<PastPerformance> pps, List<Workout> workouts, Race race) throws Exception {
		
		try {
			float furlongs = 0;
			
			for (PastPerformance pp : pps) {
				if (race.getDate().minusDays(30).isBefore(pp.getRaceDate())) furlongs += pp.getFurlongs();
			}
			for (Workout workout : workouts) {
				if (race.getDate().minusDays(30).isBefore(workout.getDateOfWorkout())) furlongs += workout.getFurlongs();
			}
			
			return furlongs / 30;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
	}

	public static float CalcTurnTime (List<PastPerformance> pps) throws Exception {
		
		try {
			if (pps.size() == 0) return 0;
			int total = 0;
			int count = 0;
			for (PastPerformance pp : pps) {
				if (pp.getE2() > 0) {
					total +=  pp.getE2() - pp.getE1();
					count++;
				}
			}
			if (count == 0) return 0;
			return (float) total / count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
	}
	
	public static String DetermineRaceShape (PastPerformance pp) throws Exception {
		String shape = "";
		try {
			if (pp.getE1() == 0) return "";
			if (pp.getRaceShapeSecondCall() > 4) {
				shape += "F";
			} else if (pp.getRaceShapeSecondCall() >= -4) {
				shape += "A";
			} else shape += "S";
			
			if (pp.getRaceStrength() > pp.getSpeedPar() + 4) {
				shape += "F";
			} else if (pp.getRaceStrength() >= pp.getSpeedPar() - 4) {
				shape += "A";
			} else shape += "S";
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;				
		}
		
		return shape;
	}
	
	public static int CalcTotalSpeedPoints(List<Horse> horses) throws Exception {
		int total = 0;
		try {
			
			for (Horse horse : horses) {
				total += horse.getSpeedPoints();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;				
		}
		return total;
	}
	
	public static List<String> GenerateHandicappingNotes (Race race) throws Exception {
		List<String> notes = new ArrayList<String>();
		
		try {
			switch (race.getRaceType()) {
			case OPTIONAL_CLAIMING:		
			case ALLOWANCE_OPTIONAL_CLAIMING:
				notes.add("Favor better horses on their way to winning stakes over consistent claiming winners");
			case ALLOWANCE:
				notes.add("Class supersedes speed.  Be alert for horses exiting good performances in better races.");
				if (race.getClassification().contains("n1")) {
					notes.add("Focus on: Younger horses; lightly raced horses; horse that have lost no more than six similar races; horses that have run impressively in stakes;" +
						"European imports that have won a non-maiden race in the native land; Maiden grads that have exceeded maiden par, provided the pace of the maiden race was faster than normal");
				}
				if (race.getClassification().contains("n1x")) {
					notes.add("Can be a deceptively strong race in which multiple-claiming winners deserve preference over horses that have lost a few similar allowance races.  "
						+ "Multiple-claiming winners have one thing their rivals do not: considerable experience defeating winners.");
				}
				if (race.getClassification().contains("n2")) {
					notes.add("Focus on: Horses that have won an open or graded stakes; Horses that have not lost more than four NW2 Allowances; " +
						"European imports that have impressed in group stakes; Horses that have recorded a triple-digit speed figure; " +
						"Imports of South America, Australia, New Zealand, South Africa and Dubai that have won 2 or more Group 1 races " +
						"Horses that have not lost claiming races below $50,000.");
					notes.add("Many winners of NW2 races will arrive with speed figures somewhat shy of par");
				}	
				if (race.getClassification().contains("n2l")) {
					notes.add("May be easy prey for a fast recent maiden grad, or the horse that has performed well in limited starts against similar or better allowance rivals, or a horse that ran credibly"
						+ "in a recent open claiming race against multiple winners.");
				}
				if (race.getClassification().contains("n2x")) {
					notes.add("May be won by high-priced claiming winners, but they will have no special edge over recent allowance winners who seem to have stakes potential and/or the lightly raced horses in"
							+ "the field that have one or two good races at this level.");
				}	
				if (race.getClassification().contains("n3x") || race.getClassification().contains("n$x")) {
					notes.add("Rarely won by claiming horses, unless we are talking about the top level of claiming, e.g. ~$100k.  Most top-of-the-line allowance races will be won by proven stakes horses, or by "
							+ "multiple-allowance winners who have already completed well at this level.");
				}	
				notes.add("If shippers from minor tracks were fast, versatile and consistent at home and performed well enough at a major track at first asking, they may be worth a bet. " +
					"After 2 losses where performance was impressive, third time may be the charm.");
				break;
			case CLAIMING:
				notes.add("Speed dominates. Even horses rising multiple levels to be considered if speed figures are near par.");
				notes.add("Early speed most frequently will dominate if a) uncontesed or b) accompanied by a significant drop in class.");
				if (race.getClassification().contains("n2l") || race.getClassification().contains("n3l")) {
					notes.add("Expect most horses to be off-pace runners and late comers with poor early speed relative to par.");
					notes.add("If race rates below par, as it will often do, early speed frequently is the trump.");
				}
				break;
			case GRADE_1:
			case GRADE_2:
				if (race.getAgeRestriction() == AgeRestrictionType.FOUR_YEAR_OLDS && race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP) {
					notes.add("More than 12 races, prefer winners of two or more Grade 1/Grade 2 events");
					notes.add("Less than 12 races, has won a single Grade 1/Grade 2 stakes, demand the horses also have matched par for the Grade 1/Grade 2 levels.");
				}
				if (race.getAgeRestriction() == AgeRestrictionType.THREE_YEAR_OLDS && race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP) {				
					notes.add("3 Year Olds and lightly raced 4 Year Olds, accept winners of a single Grade 1/Grade 2 event.");
				}
				if (race.getAgeRestriction() == AgeRestrictionType.THREE_YEAR_OLDS && race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_ONLY) {
					if (race.getDate().getMonth().getValue() <= 5) {				
						notes.add("Dominated by horses with Triple Crown potential");
					} else {
						notes.add("3 Year Old Grade 1/2 Stakes are won  by: 1) Horses who previously competed in Triple Crown races and preps, 2) Tpo 2-year olds of the previous "
								+ "year who were forced to miss the Triple Crown season, 3) Developing summer stars who have won a few allowance races and/or low-level stakes "
								+ "while earning above-average speed figures and/or style points.");
					}
				}
				notes.add("Authentic contenders should match par and possess pace figures within two lengths of par.");
				notes.add("Preferred combinations are abovepar pace figures in combination with a par or better speed figure - outstanding Grade 1/Grade 2 closes excepted.");
				break;
			case GRADE_3:
				if (race.getAgeRestriction() == AgeRestrictionType.FOUR_YEAR_OLDS && race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP) {
					notes.add("Horses will have varying credentials, from Grade 1 and Grade 2 drop-downs to improving allowance types.");
				}
				break;
			case MAIDEN_CLAIMING:
				notes.add("All horses typically will have speed figures below par, so not a reliable factor.");
				notes.add("The most likely winner will be the horse dropping in class from the races for straight maidens.");
				notes.add("Likeliest horsees in descending order: 1) Maiden droppers with early speed 2) Maiden droppers that can take the lead at E2 3)" +
					"Maiden droppers with one dull line and high odds 4) Maiden Claiming dropping from significantly higher level and having a pace advantage " +
					"5) Maiden-claiming horses with second start 6)First starts if all other experienced horses are slow");
				break;
			case MAIDEN_OPTIONAL_CLAIMING:
				break;
			case MAIDEN_SPECIAL_WEIGHT:
				break;
			case NON_GRADED:
				break;
			case OPTIONAL_CLAIMING_STAKES:
				break;
			case STARTER_ALLOWANCE:
				notes.add("More susceptible to class evaluation than speed handicapping");
				notes.add("Likely winner will be a horse dropping down from a much higher claiming level, especially if that horse won handily or witha lofty speed figure.");
				break;
			case STARTER_HANDICAP:
				break;
			default:
				break;
			
			}
			
		if (race.getTrackCondition().equals("wf")) notes.add("Prefer early speed; likes the wet; competitive to superior wet-fast figures; and attractive odds.");
		if (race.getFurlongs() >= 12) notes.add("Marathons are prey for a) horses that have won impressively at marathon distances in the past and b) lone front-runners that can set sluggish factors.  Inexperienced horses should possess a pedigree that smacks of endurance.");
		if (race.getTurfFlag() && race.getFurlongs() <= 5.5) notes.add("Turf Sprint best bets have a) won two or more similar races (one if a Three Year Old and b) equaled or exceeded today's par.");
		if (race.getAgeRestriction() == AgeRestrictionType.TWO_YEAR_OLDS) notes.add("Favor speed in juvenile races, as measured by speed figures. Class is irrelevant.");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
		
		return notes;
	}
		
}


