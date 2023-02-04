package net.derbyparty.jpp.factors;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
						|| pp.getFinishBeatenLengthsOnly() <= 3) {
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
			return (float) (pp.getBRISSpeedRating() + (pp.getFurlongs() < 8 ? 0.3 : 0.2) * pp.getFinishBeatenLengthsOnly());
			
			
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
				points += CalcWorkoutWorkoutPoints(workouts.get(3), workouts.get(2));
				points += CalcBonusWorkoutPoints(workouts.get(2));		
				
				if (!noRaces) {
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
	
	public static Boolean isSharp (Workout workout) throws Exception {
		try {
			return (workout.getFurlongs() >= 4 && workout.getFurlongs() <= 4 &&  Math.abs(workout.getTimeOfWorkout())/workout.getFurlongs() < 12) 
				|| (workout.getFurlongs() == 6 && Math.abs(workout.getTimeOfWorkout()) < 73)
				|| (workout.getFurlongs() == 8 && Math.abs(workout.getTimeOfWorkout()) < 87);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
	}
	
	public static int SharpWorkouts(List<Workout> workouts, Race race) throws Exception {
		return SharpWorkouts(workouts, race, 90);
	}
	
	public static int SharpWorkouts(List<Workout> workouts, Race race, int days) throws Exception {
		int count = 0;
		try {
			for (Workout workout : workouts) {
				if (workout.getDateOfWorkout().plusDays(days).isAfter(race.getDate())) {
					if (isSharp(workout)) count++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
		return count;
	}
	
	public static Boolean GoodRace(PastPerformance pp) {
		return (pp.getFinishPosition().equals("1") || pp.getFinishPosition().equals("2") || pp.getFinishPosition().equals("3")
				|| (pp.getFurlongs() < 8 && pp.getFinishBeatenLengthsOnly() <= 2) 
				|| (pp.getFurlongs() >= 8 && pp.getFinishBeatenLengthsOnly() <= 3));
	}
	
	public static Boolean OKRace(PastPerformance pp) {
		return (pp.getFinishPosition().equals("1") || pp.getFinishPosition().equals("2") || pp.getFinishPosition().equals("3")
				|| (pp.getFurlongs() < 8 && pp.getFinishBeatenLengthsOnly() <= 9) 
				|| (pp.getFurlongs() >= 8 && pp.getFinishBeatenLengthsOnly() <= 8));
	}
	
	public static Boolean isPaired(int Speed1, int Speed2) {
		return (Speed1 > 60 && Speed2 > 60 && Math.abs(Speed2 - Speed1) < 3);
	}
	
	public static List<String> GenerateAngles (Race race, Horse horse) throws Exception {
		List<String> angles = new ArrayList<String>();
		
		try {
			if (horse.getMostRecentYearBestSpeed() >= race.getMaxSpeed()) angles.add("+Most Recent Year Best Speed is equal or better than Max Speed Rating in the Race.");
						
			if (horse.getPastPerformances().size() > 2) {
				if (horse.getAge() >= 4 && horse.getPastPerformances().size() > 5 && isPaired(horse.getPastPerformances().get(0).getBRISSpeedRating(), horse.getPastPerformances().get(1).getBRISSpeedRating())
					&& isPaired(horse.getPastPerformances().get(0).getBRISSpeedRating(), horse.getLifetimeBestSpeed()))
						angles.add("-Horse has paired top speed figures; probable bounce.");
				if (horse.getAge() >= 4 && isPaired(horse.getPastPerformances().get(0).getBRISSpeedRating(), horse.getPastPerformances().get(1).getBRISSpeedRating())
					&& isPaired(horse.getPastPerformances().get(0).getBRISSpeedRating()-5, horse.getLifetimeBestSpeed()))
						angles.add("+Horse has paired below lifetime top; anticipate move forward.");	
				if (horse.getAge() == 3 && isPaired(horse.getPastPerformances().get(0).getBRISSpeedRating(), horse.getPastPerformances().get(1).getBRISSpeedRating()))
					angles.add("+3YO has paired figures - expect a forward move.");
				if (horse.getAge() == 3 && isPaired(horse.getPastPerformances().get(1).getBRISSpeedRating(), horse.getPastPerformances().get(2).getBRISSpeedRating())
					&& horse.getPastPerformances().get(0).getBRISSpeedRating() < horse.getPastPerformances().get(1).getBRISSpeedRating() - 2)
						angles.add("-3YO with paired figures followed by a decline in form is negative; expect a return to positive form equal only to paired figure but not improved.");
				if (horse.getAge() >= 4 && horse.getPastPerformances().get(0).getDaysSinceLastRace() > 150 && horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getLifetimeBestSpeed() - 3
					&& horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getPastPerformances().get(0).getSpeedPar() + 5
					&& horse.getDaysSinceLastRace() < 42)
						angles.add("-Potential layoff bounce - lengthy absence followed by overexertion");
				if (horse.getPastPerformances().get(0).getTurfFlag() && race.getFurlongs() < 8 && horse.getPastPerformances().get(0).getFurlongs() >= 8 
					&& horse.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() > 1 && horse.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() > 1
					&& horse.getPastPerformances().get(0).getStretchBeatenLengthsOnly() > 1 && horse.getPastPerformances().get(0).getFinishPosition() != "1")
					angles.add("-Horse exiting route where it failed to run within a length of the leader at the first three calls.");
				if (race.getTurfFlag() && horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_SPECIAL_WEIGHT 
					&& horse.getPastPerformances().get(0).getFinishPosition().equals("1") && horse.getPedigreeRatingTurf().charAt(0) != '?'
					&& !horse.getPedigreeRatingTurf().contains("?") && Integer.parseInt(horse.getPedigreeRatingTurf()) > 105) 
						angles.add("+Well bred maiden winner can move ahead in class and beat turf winners if final fraction less than :12 per furlong.");
				if ((float) horse.getSpeedPoints() / race.getTotalSpeedPoints() >= 0.30) 
					angles.add("+Horse possesses 30 percent or more of a race's early speed.");
				if (horse.getPastPerformances().get(0).getRaceShape().equals("FF") && OKRace(horse.getPastPerformances().get(0))) 
					angles.add("+Exiting Key Race (An OK or better FF Race)");
			}
			
			Boolean firstTimeAtDistanceType = true;
		
			for (PastPerformance pp : horse.getPastPerformances()) {
				if ((race.getFurlongs() < 8 && pp.getFurlongs() < 8) || (race.getFurlongs() >= 8 && pp.getFurlongs() >= 8)){
					firstTimeAtDistanceType = false;
				}	
			}
			
			if (horse.getPastPerformances().size() > 0 && race.getFurlongs() >= 8 && firstTimeAtDistanceType) {
				if (horse.getRunStyle().equals("P") || horse.getRunStyle().equals("S") || 
					(horse.getE2Avg() > 0 && horse.getLatePaceAvg() > horse.getE2Avg())) {
						angles.add("-Late Running sprinters may not be dependable in routes.");
				} else if ((horse.getRunStyle().equals("E") || horse.getRunStyle().equals("E/P")) && horse.getE2Avg() >=  horse.getSpeedRating() + 2) {
					angles.add("-Front runners with signficantly higher pace figures than speed figures may not be dependable in routes.");
				} else {
					angles.add("+Horses stretching out win their fair share, even when matched against route horses that delivered good results last time.");
				}
			}
			
			if (horse.getFormPoints() == 5) angles.add("+Showing improving form.");
			if (horse.getFormPoints() < 0) angles.add("-Showing declining form.");
			
			int fourFurlongWorkouts = 0;
			int fiveFurlongWorkouts = 0;
			for (Workout workout : horse.getWorkouts()) {
				if (workout.getDateOfWorkout().plusDays(22).isAfter(race.getDate()) && workout.getFurlongs() >= 4) fourFurlongWorkouts++;
				if (workout.getDateOfWorkout().plusDays(15).isAfter(race.getDate()) && workout.getFurlongs() >= 5) fiveFurlongWorkouts++;
			}
			
			if (race.getRaceType() == RaceType.CLAIMING && horse.getDaysSinceLastRace() >= 60 && horse.getDaysSinceLastRace() <=90
				&& fourFurlongWorkouts < 2) angles.add("-Less than two four furlong workouts for claiming horse away 60 to 90 days");
			
			if (horse.getDaysSinceLastRace() > 180 && fiveFurlongWorkouts == 0 & horse.getBasicFitness() < 15)
				angles.add("-No five furlong workouts in last 14 days for horse away 6 months.");
			
			if (SharpWorkouts(horse.getWorkouts(),race) > 0) angles.add("+Sharp Workout in last 90 days");
			
			if (race.getAgeRestriction() == AgeRestrictionType.TWO_YEAR_OLDS && horse.getBasicFitness() >= 15) {
				int sharpGateWorkout = 0;
				int fourOrFiveFurlongSharpWorkouts = 0;
				for (Workout workout : horse.getWorkouts()) {
					if (isSharp(workout) && workout.getFurlongs() >= 4 && workout.getFurlongs() <= 5) {
						fourOrFiveFurlongSharpWorkouts++;
						if (workout.getDescription().length() >= 2 && workout.getDescription().charAt(1) == 'g') sharpGateWorkout++;
					}
				}
				if (sharpGateWorkout >=1 && fourOrFiveFurlongSharpWorkouts > 2)
				angles.add("+2YO with regular workouts and at least two sharp workouts at four or five furlongs, one of which occurred out of the gate.");
			}
					
						
			if (horse.getPastPerformances().size() >= 5) {
				if (horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getSpeedRating() + 5 
					&& horse.getDaysSinceLastRace() <= 120) 
						angles.add("-Potential Performance Bounce - last speed figure well above average.");
				if (horse.getPastPerformances().get(1).getBRISSpeedRating() > horse.getSpeedRating() + 5
					&& horse.getPastPerformances().get(1).getDaysSinceLastRace() >= 120
					&& horse.getPastPerformances().get(0).getBRISSpeedRating() < horse.getSpeedRating() - 5)
						angles.add("+Potential Bounce-Back Race.");
				if (horse.getPastPerformances().get(0).getBRISSpeedRating() - 5 > horse.getPastPerformances().get(1).getBRISSpeedRating()
					&& horse.getWorkouts().get(0).getDateOfWorkout().isAfter(horse.getPastPerformances().get(0).getRaceDate())
					&& horse.getWorkouts().get(0).getTimeOfWorkout() / horse.getWorkouts().get(0).getFurlongs() <
					horse.getWorkouts().get(1).getTimeOfWorkout() / horse.getWorkouts().get(1).getFurlongs()
					&& horse.getDaysSinceLastRace() <= 21 )
						angles.add("+Peaking Form - much-improved race followed by much-improved workout followed by return to races within 2 to 3 weeks.");
				if (horse.getPastPerformances().get(1).getDaysSinceLastRace() > 90 && horse.getPastPerformances().get(0).getDaysSinceLastRace() < 45
					&& horse.getDaysSinceLastRace() < 45 && horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getPastPerformances().get(1).getBRISSpeedRating() + 5)
						angles.add("+3rd start off of layoff with much improved 2nd start (especially sprints and better horses).");
				if (horse.getPastPerformances().get(2).getDaysSinceLastRace() > 90 && horse.getPastPerformances().get(0).getDaysSinceLastRace() < 45
					&& horse.getDaysSinceLastRace() < 45 && horse.getPastPerformances().get(1).getBRISSpeedRating() > horse.getPastPerformances().get(2).getBRISSpeedRating() + 5
					&& horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getPastPerformances().get(1).getBRISSpeedRating() + 5)
						angles.add("+4th start off of layoff with peaking form.");	
				if (!horse.getRunStyle().equals("E")  && !horse.getRunStyle().equals("NA")&& horse.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() <= 3
					&& horse.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() <= 3 && horse.getPastPerformances().get(0).getStretchBeatenLengthsOnly() <= 3 
					&& horse.getPastPerformances().get(0).getFinishBeatenLengthsOnly() <= 4.5 && horse.getPastPerformances().get(0).getRaceShapeSecondCall() >= 0) 
						angles.add("+Off-pace horse that ran close to the lead horse at every fraction call - potential top form.");

				if (horse.getPastPerformances().get(1).getClaimedCode().equals("c") && horse.getPastPerformances().get(0).getBRISSpeedRating() > horse.getSpeedRating() + 5 
					&& !horse.getPastPerformances().get(0).getClaimedCode().equals("c"))  
					angles.add("+Second off Claim with a much-improved last race.");
				
				if (horse.getPastPerformances().get(1).getClaimedCode().equals("c") && horse.getPastPerformances().get(0).getBRISSpeedRating() < horse.getSpeedRating() - 5 
						&& !horse.getPastPerformances().get(0).getClaimedCode().equals("c")) 
					angles.add("-Second off Claim with a disappointing or dull last race.");
				
				if (horse.getPastPerformances().get(0).getBRISSpeedRating() < horse.getSpeedRating() - 5 && horse.getPurseShift() <= -10000
					&& !horse.getPastPerformances().get(0).getFinishPosition().equals("1") && !horse.getPastPerformances().get(0).getFinishPosition().equals("2")
					&& !horse.getPastPerformances().get(0).getFinishPosition().equals("3"))
						angles.add("-Unexpectedly dull performance followed by multiple level drop in class.");	
				
				if (horse.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() < 2.75 && horse.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() < 2.75
					&& horse.getPastPerformances().get(0).getStretchBeatenLengthsOnly() < 2.75 && horse.getPastPerformances().get(0).getFinishPosition().equals("1") 
					&& horse.getPastPerformances().get(0).getWinnersMargin() >= 3 &&  horse.getPastPerformances().size() > 4)
						angles.add("+Peaking Form - close up at every call in last race and won by 3 or more lengths.");
				
				if (horse.getPastPerformances().get(0).getE2() > horse.getE2Avg() + 5 && horse.getDaysSinceLastRace() <= 21 && horse.getPastPerformances().size() > 4)
					angles.add("+Peaking Form - improved early speed and back within 21 days.");
			}
			
			if (horse.getAge() >= 4 && race.getDate().getMonthValue() <= 4) {
				Boolean lastYearStakesWinner = false;
				for (PastPerformance pp : horse.getPastPerformances()) {
					if (pp.getRaceDate().getYear() == race.getDate().getYear() - 1 && (pp.getRaceType() == RaceType.NON_GRADED 
						|| pp.getRaceType() == RaceType.GRADE_1 || pp.getRaceType() == RaceType.GRADE_2 || pp.getRaceType() == RaceType.GRADE_3)
						&& pp.getFinishPosition().equals("1")) lastYearStakesWinner = true;	
				}
				if (lastYearStakesWinner && horse.getMostRecentYearBestSpeed() < horse.getSecondMostRecentYearBestSpeed() - 5 
					&& horse.getCurrentYearStarts() > 2) 
						angles.add("-4UP Stakes winner last year showing decline in the first starts of season");	

			}
			if (horse.getAge() == 3) {
				Boolean lastYearStakesWinner = false;
				for (PastPerformance pp : horse.getPastPerformances()) {
					if (pp.getRaceDate().getYear() == race.getDate().getYear() - 1 && (pp.getRaceType() == RaceType.NON_GRADED 
						|| pp.getRaceType() == RaceType.GRADE_1 || pp.getRaceType() == RaceType.GRADE_2 || pp.getRaceType() == RaceType.GRADE_3)
						&& pp.getFinishPosition().equals("1")) lastYearStakesWinner = true;					
				}	
				if (horse.getAge() == 4 && lastYearStakesWinner && horse.getMostRecentYearBestSpeed() < horse.getSecondMostRecentYearBestSpeed() + 2 
					&& horse.getCurrentYearStarts() > 2)
						angles.add("-Three Year old Stakes winner last year not improved as Four Year Old");
			}
			if (horse.getAge() >= 4 && horse.getPastPerformances().size() > 4) {
				if (race.getTurfFlag() && race.getFurlongs() < 8) {
					Boolean goodTurfRaceWithinHalfFurlong = false;
					for (PastPerformance pp : horse.getPastPerformances()) {
						if (race.getFurlongs() < 8 && pp.getTurfFlag() && GoodRace(pp) && Math.abs(pp.getFurlongs()) - race.getFurlongs() <= 0.5) goodTurfRaceWithinHalfFurlong = true;
					}
					if (!goodTurfRaceWithinHalfFurlong) angles.add("-Has not run a good Turf Sprint race within a half furlong of this distance.");
				}
				if (horse.getPastPerformances().get(0).getBRISSpeedRating() == horse.getLifetimeBestSpeed()) angles.add("-4UP, experienced horses, lifetime top, expect a decline");
		
			}
			if (race.getTurfFlag() && horse.getJockey().getStats().get(0).getCategory().equals("Turf") &&
				horse.getJockey().getStats().get(0).getStarts() > 0 && (float) horse.getJockey().getStats().get(0).getWins() / 
				horse.getJockey().getStats().get(0).getStarts() < 0.11) 
					angles.add("-Jockey has win percent less than 11% on turf mounts - NO PLAY");

			if (horse.getAge() >= 7) angles.add("-NO PLAY on horses aged seven and older.");
			
			switch (race.getRaceType()) {
			
			case MAIDEN_SPECIAL_WEIGHT:
			case MAIDEN_OPTIONAL_CLAIMING:
				
				if (horse.getPastPerformances().size() == 0) {
					Boolean trainerStatFirstStarter = false;
					Boolean lowFirstTimeWinPercent = (race.getTurfFlag() ? horse.getSireFirstTurfPercent() < 11 : horse.getSireFirstPercent() < 11);
					for (Stat stat : horse.getTrainer().getTrainerStats()) {
						if (stat.getCategory().equals("1st time str") && stat.getWinPercent() >= 11) trainerStatFirstStarter = true;
					}
					if (!trainerStatFirstStarter && SharpWorkouts(horse.getWorkouts(), race) < 2 && lowFirstTimeWinPercent) {
						angles.add("-First Time Start with Trainer 1st time stat < 11%, less than 2 sharp workouts AND Sire 1st Time Win % < 11 - NO PLAY");
					} else if (!trainerStatFirstStarter && SharpWorkouts(horse.getWorkouts(), race) < 2) {
						angles.add("-First Time Start with Trainer 1st time stat < 11% AND less than 2 sharp workouts - NO PLAY");
					} else if (!trainerStatFirstStarter && lowFirstTimeWinPercent) {
						angles.add("-First Time Start with Trainer 1st time stat < 11% AND Sire 1st Time Win % < 11 - NO PLAY");
					} else if (SharpWorkouts(horse.getWorkouts(), race) < 2 && lowFirstTimeWinPercent) {
						angles.add("-First Time Start with less than 2 sharp workouts AND Sire 1st Time Win % < 11 - NO PLAY");
					} else if (!trainerStatFirstStarter) {
						angles.add("?First Time Start with Trainer 1st time stat < 11% - NO PLAY Favorites/Low Price (Only high price + no 2nd starters projected to par + no experienced starters at par)");
					} else if (SharpWorkouts(horse.getWorkouts(), race) < 2) {
						angles.add("?First Time Start with less than 2 sharp workouts - NO PLAY Favorites/Low Price (Only high price + no 2nd starters projected to par + no experienced starters at par)");
					} else if (lowFirstTimeWinPercent) {
						angles.add("?First Time Start with Sire 1st Time Win % < 11 - NO PLAY Favorites/Low Price (Only high price + no 2nd starters projected to par + no experienced starters at par)");
					}
					
				} else {
					if (horse.getPastPerformances().get(0).getBRISSpeedRating() >= horse.getPastPerformances().get(0).getSpeedPar())
						angles.add("+Last Race Speed equal to or better than that race's Par (Maiden)");
					if (horse.getPastPerformances().size() == 1 
							&& horse.getPastPerformances().get(0).getBRISSpeedRating() + (horse.getPastPerformances().get(0).getFurlongs() < 8 ? .3 : .2) * 5 >= 
							race.getParSpeed()) 
						angles.add("+Second Starter projected to par with normal improvement");
				}
				
				break;
				
			case MAIDEN_CLAIMING:
				if (horse.getPastPerformances().size() > 0) {
					Boolean hasBeenInForClaim = false;
					for (PastPerformance pp : horse.getPastPerformances()) {
						if (pp.getClaimingPrice() > 0) hasBeenInForClaim = true;
					}
					if (!hasBeenInForClaim && (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_SPECIAL_WEIGHT 
						|| horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_OPTIONAL_CLAIMING)) {
							angles.add("+Maiden to Maiden Claiming");
					} else if (horse.getPastPerformances().size() > 1 && (horse.getPastPerformances().get(1).getRaceType() == RaceType.MAIDEN_SPECIAL_WEIGHT 
						|| (horse.getPastPerformances().get(1).getRaceType() == RaceType.MAIDEN_OPTIONAL_CLAIMING
						&& horse.getPastPerformances().get(1).getClaimingPrice() == 0))) {
							Boolean hasBeenInForClaim3orMoreBack = false;
							for (int i=2; i < horse.getPastPerformances().size(); i++) {
								hasBeenInForClaim3orMoreBack = true;
							}
							if (!hasBeenInForClaim3orMoreBack) angles.add("+Maiden to Maiden Claiming Two Races Back");
					} else {
						if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING && 
								race.getPurse() <= 40000 && horse.getPastPerformances().get(0).getPurse() >= 50000) {
									angles.add("+MCl 50k+ to MCl 40k-");
						} else if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING && 
								horse.getPastPerformances().get(0).getPurse() - race .getPurse() > 10000) {
									angles.add("+Maiden Claiming drop greater than two levels ($" + (horse.getPastPerformances().get(0).getPurse() - race .getPurse()) + ")");
						}
					}
				} else {
					angles.add("-First Time Starter in Maiden Claiming Race - likely NO PLAY");
				}
				break;
			case CLAIMING:
				int racesCloseToPar = 0;
				int count = 0;
				for (PastPerformance pp : horse.getPastPerformances()) {
					count++;
					if (count <= 8 && pp.getBRISSpeedRating() >= race.getParSpeed() - (pp.getFurlongs() < 8 ? 2 : 3)
							 && pp.getRaceDate().plusDays(270).isAfter(race.getDate())) {
						racesCloseToPar++;
					}
				}
				if (racesCloseToPar >= 2) angles.add("+Has 2 or more races close to or better than this race's Par");
				
				if (GoodRace(horse.getPastPerformances().get(0)) && horse.getPastPerformances().get(0).getRaceType() == RaceType.CLAIMING 
					&& horse.getPurseShift() <= 10000 && horse.getClassShift() >= 0) angles.add("+Good Race last out with justifiable class shift");

				if (GoodRace(horse.getPastPerformances().get(0)) && horse.getPastPerformances().get(0).getRaceType() == RaceType.CLAIMING 
					&& horse.getPurseShift() < 0) angles.add("?Good Race last out yet dropping in class");
				 
				if ( horse.getPastPerformances().get(0).getRaceType() == RaceType.CLAIMING  && horse.getClassShift() > 10000 
					&& ((race.getTurfFlag() && horse.getTurfBestSpeed() >= race.getParSpeed())
						|| (!race.getTurfFlag() && horse.getDirtBestSpeed() >= race.getParSpeed()))
						) angles.add("+Rising multiple levels but has equaled or exceeded Par at this level");
				 
				
				if (horse.getAge() == 3 && race.getAgeRestriction() == AgeRestrictionType.THREE_YEAR_OLDS 
					&& race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP)  {
						if ((race.getClassification().contains("n2l") || race.getClassification().contains("n3l")) 
							&& race.getDate().getMonthValue() > 7)  {
							angles.add("?3 year old claimers may be played in the Fall if best on fundamentals");
						} else {
							angles.add("-3 year old claimers should not prevail against older.");
						}
				}
				
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.ALLOWANCE
						|| horse.getPastPerformances().get(0).getRaceType() == RaceType.ALLOWANCE_OPTIONAL_CLAIMING
						|| horse.getPastPerformances().get(0).getRaceType() == RaceType.STARTER_ALLOWANCE) {
					if (horse.getAge() == 3 && race.getDate().getMonthValue() <= 6)
						angles.add("+Three year olds drop-downs from allowance are best play January through June.");
				} 
				
				Boolean hasAllowanceWin = false;
				for (PastPerformance pp : horse.getPastPerformances()) {
					if ((pp.getRaceType() == RaceType.ALLOWANCE || pp.getRaceType() == RaceType.ALLOWANCE_OPTIONAL_CLAIMING || pp.getRaceType() == RaceType.STARTER_ALLOWANCE)
						&& pp.getFinishPosition().equals("1")) {
						for (PastPerformance pp2 : horse.getPastPerformances()) {
							if (pp2.getRaceType() == RaceType.CLAIMING && (pp.getFinishPosition().equals("") || pp.getFinishBeatenLengthsOnly() < 3))
								hasAllowanceWin = true;
						}
					}
				}
				if (hasAllowanceWin) angles.add("+Previous Allowance winner has won or finished close in a Claiming race.");
				
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING && race.getPurse() > horse.getPastPerformances().get(0).getPurse()) 
					angles.add("-Maiden Claiming grads are never acceptable in higher priced open claiming races.");
				
				break;				
			case ALLOWANCE:
			case ALLOWANCE_OPTIONAL_CLAIMING:
			case OPTIONAL_CLAIMING:
			case OPTIONAL_CLAIMING_STAKES:			
				if (horse.getAge() == 3 && horse.getPastPerformances().get(0).getRaceType() == RaceType.CLAIMING)
					angles.add("-Three year old moving up from claiming - NO PLAY");
				if (horse.getAge() == 3 && horse.getPastPerformances().get(0).getFinishBeatenLengthsOnly() > 5 && horse.getPastPerformances().get(1).getFinishBeatenLengthsOnly() > 5) 
					angles.add("-Non-claiming three year old has lost two races in a row, well-beaten in each");
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING 
					&& horse.getPastPerformances().get(0).getClaimingPrice() == 0) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				break;

			case GRADE_1:
				if (horse.getPastPerformances().get(0).getRaceClassification().contains("n1")) angles.add("-No horse can rise in class from NW1 to Grade 1 - NO PLAY");
				if (horse.getPastPerformances().get(0).getRaceClassification().contains("n2")) angles.add("-No horse can rise in class from NW2 to Grade 1 - NO PLAY");
				if (horse.getAge() >= 4 && horse.getLifetimeStarts() >= 15 && 
					(horse.getPastPerformances().get(0).getRaceType() == RaceType.GRADE_3 || 
					horse.getPastPerformances().get(0).getRaceType() == RaceType.NON_GRADED && horse.getPastPerformances().get(0).getPurse() < 150000)
					) angles.add("?No horse 4 years old and up and having as many as 15 races can be accepted in a G1/G2 race as favorite or low price " +
						" when moving up from G3 or open stakes having purses below $150,000");
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				break;
			case GRADE_2:
				if (horse.getPastPerformances().get(0).getRaceClassification().contains("n1")) angles.add("-No horse can rise in class from NW1 to Grade 2 - NO PLAY");
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				break;
			case GRADE_3:
				if ((horse.getPastPerformances().get(0).getRaceType() == RaceType.GRADE_2 || horse.getPastPerformances().get(0).getRaceType() == RaceType.GRADE_1)
						&& horse.getPastPerformances().get(0).getFinishPosition().equals("1"))
							angles.add("+Likeliest winner is a Grade 1 or 2 winner");
				
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				
				Boolean nw1Ornw2Winner = false;
				Boolean nw3Ornw4Winner = false;
				Boolean impressiveMaidenWinner = false;
				Boolean haveNotLostAllowance = true;
				Boolean racedNonAllowance = false;
				
				for (PastPerformance pp : horse.getPastPerformances()) {
					if ((pp.getRaceClassification().contains("n1") || pp.getRaceClassification().contains("n2")) 
						&& pp.getFinishPosition().equals("1") && pp.getBRISSpeedRating() >= pp.getSpeedPar()
						&& pp.getRaceShapeSecondCall() > 0) nw1Ornw2Winner = true;
					if ((pp.getRaceClassification().contains("n3") || pp.getRaceClassification().contains("n4")) 
							&& pp.getFinishPosition().equals("1")) nw3Ornw4Winner = true;
					if (pp.getRaceType() == RaceType.MAIDEN_SPECIAL_WEIGHT && pp.getBRISSpeedRating() > pp.getSpeedPar() + 
						(pp.getFurlongs() < 8 ? 3 : 2))
							impressiveMaidenWinner = true;
					if ((pp.getRaceType() == RaceType.ALLOWANCE || pp.getRaceType() == RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
						&& !pp.getFinishPosition().equals("1")) haveNotLostAllowance = false;
					if (pp.getRaceType() != RaceType.MAIDEN_SPECIAL_WEIGHT|| pp.getRaceType() != RaceType.ALLOWANCE
						|| pp.getRaceType() != RaceType.ALLOWANCE_OPTIONAL_CLAIMING) racedNonAllowance = true;
						
				}
				if (nw1Ornw2Winner) angles.add("+Previous NW1 or NW2 winner with speed rating at or better than par and not a slow pace; can accept lower class and speed.");
				if (nw3Ornw4Winner) angles.add("+Previous NW3 or NW4 winner; can accept lower class and speed.");
				if (impressiveMaidenWinner && haveNotLostAllowance && !racedNonAllowance)
					angles.add("+Impressive Maiden winner that has not lost an allowance; can accept lower class and speed.");
				break;
			case NON_GRADED:
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				break;
			case STARTER_ALLOWANCE:
				if (horse.getPastPerformances().get(0).getRaceType() == RaceType.MAIDEN_CLAIMING) angles.add("-Maiden Claiming grads are never acceptable in allowance or stakes races.");
				break;
			case STARTER_HANDICAP:
				break;
			default:
				break;
				
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
		
		return angles;
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


