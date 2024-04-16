package net.derbyparty.jpp.factors;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.Angle;
import net.derbyparty.jpp.object.Entry;
import net.derbyparty.jpp.object.PastPerformance;
import net.derbyparty.jpp.object.Race;
import net.derbyparty.jpp.object.RaceType;
import net.derbyparty.jpp.object.Stat;
import net.derbyparty.jpp.object.Workout;

public class Angles {

	static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);

	
    public Angles() {

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
				if (workout.getDateOfWorkout().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(days)
						.isAfter(race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
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
				|| (Math.abs(pp.getFurlongs()) < 8 && pp.getFinishBeatenLengthsOnly() <= 2) 
				|| (Math.abs(pp.getFurlongs()) >= 8 && pp.getFinishBeatenLengthsOnly() <= 3));
	}
	
	public static Boolean OKRace(PastPerformance pp) {
		return (pp.getFinishPosition().equals("1") || pp.getFinishPosition().equals("2") || pp.getFinishPosition().equals("3")
				|| (Math.abs(pp.getFurlongs()) < 8 && pp.getFinishBeatenLengthsOnly() <= 9) 
				|| (Math.abs(pp.getFurlongs()) >= 8 && pp.getFinishBeatenLengthsOnly() <= 8));
	}
	
	public static Boolean isPaired(int Speed1, int Speed2) {
		return (Speed1 > 60 && Speed2 > 60 && Math.abs(Speed2 - Speed1) < 3);
	}
	
	public static Boolean fastTurfFinish(PastPerformance pp) {
		
		float finish = 0;
		float target = 0;
		
		if (Math.abs(Math.abs(pp.getFurlongs())) == 8 
			|| (Math.abs(pp.getDistance()) >= 1800 && Math.abs(pp.getDistance()) <= 1830)
			|| Math.abs(Math.abs(pp.getFurlongs())) == 10
			|| Math.abs(Math.abs(pp.getFurlongs())) == 12) {
			finish = pp.getSplit3();
			target = 23.5f;
		} else if (Math.abs(Math.abs(pp.getFurlongs())) == 8.5) {
			finish = pp.getSplit3();
			target = 29.5f;
		} else if (Math.abs(Math.abs(pp.getFurlongs())) == 9
				|| Math.abs(Math.abs(pp.getFurlongs())) == 9.5
				|| Math.abs(Math.abs(pp.getFurlongs())) == 11) {
			finish = pp.getSplit2() + pp.getSplit3();
			target = 35.5f;
		}
		if (pp.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)) target += 0.4f;
		return (finish < target);
	}
	
	public static Boolean firstTimeAtDistanceType(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if ((race.getFurlongs() < 8 && Math.abs(pp.getFurlongs()) < 8) || (race.getFurlongs() >= 8 && Math.abs(pp.getFurlongs()) >= 8)){				
				return false;
			}	
		}
		return true;
	}
	
	public static Boolean lastYearStakesWinner(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() .getYear() 
					== race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() - 1 
				&& (pp.getRaceType().equals(RaceType.NON_GRADED)
					|| pp.getRaceType().equals(RaceType.GRADE_1)
					|| pp.getRaceType().equals(RaceType.GRADE_2)
					|| pp.getRaceType().equals(RaceType.GRADE_3))
				&& pp.getFinishPosition().equals("1")) return true;	
		}
		return false;
	}
	
	public static Boolean goodTrainer1stTimeStat(Race race, Entry entry) {
		for (Stat stat : entry.getTrainer().getTrainerStats()) {
			if (stat.getCategory().equals("1st time str") && stat.getWinPercent() >= 11) return true;
		}
		return false;
	}
	
	public static Boolean goodDebutTurfStat(Race race, Entry entry) {
		for (Stat stat : entry.getTrainer().getTrainerStats()) {
			if (stat.getCategory().equals("Debut Turf") && stat.getWinPercent() >= 11) return true;
		}
		return false;
	}
	
	public static Boolean hasBeenInForAClaim(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getClaimingPrice() > 0) return true;
		}
		return false;
	}
	
	public static int racesCloseToPar(Race race, Entry entry) {
		int racesCloseToPar = 0;
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getBRISSpeedRating() >= race.getParSpeed() - (Math.abs(pp.getFurlongs()) < 8 ? 2 : 3)
					 && pp.getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(270)
					 .isAfter(race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
				racesCloseToPar++;
			}
		}
		return racesCloseToPar;
	}
	
	public static Boolean hasAllowanceWin(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if ((pp.getRaceType().equals(RaceType.ALLOWANCE)
					|| pp.getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
					|| pp.getRaceType().equals(RaceType.STARTER_ALLOWANCE))
				&& pp.getFinishPosition().equals("1")) return true;
		}
		return false;
	}
	
	public static Boolean hasClaimingWinOrClose(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getRaceType().equals(RaceType.CLAIMING)
				&& (pp.getFinishPosition().equals("1") 
					|| pp.getFinishBeatenLengthsOnly() < 3)) return true;
		}	
		return false;		
	}
	
	public static Boolean impressiveMaidenWinner(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& pp.getBRISSpeedRating() > pp.getSpeedPar() + (Math.abs(pp.getFurlongs()) < 8 ? 3 : 2)) return true;
		}
		return false;
	}
	
	public static Boolean hasNotLostAllowance(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if ((pp.getRaceType().equals(RaceType.ALLOWANCE)
					|| pp.getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING))
				&& !pp.getFinishPosition().equals("1")) return false;
		}
		return true;
	}
	
	public static Boolean racedNonAllowance(Race race, Entry entry) {
		for (PastPerformance pp : entry.getPastPerformances()) {
			if (pp.getRaceType() != RaceType.MAIDEN_SPECIAL_WEIGHT
				|| pp.getRaceType() != RaceType.ALLOWANCE
				|| pp.getRaceType() != RaceType.ALLOWANCE_OPTIONAL_CLAIMING) return true;
		}
		return false;
	}
	
	
//	public static void initializeCatalog() throws Exception {
//		try {
//			angles = Arrays.asList(mapper.readValue(Paths.get(anglesFile).toFile(), Angle[].class));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;			
//		}	
//	}
//	
//	public static void convertAngles() throws Exception {
//		try {
//			angles = Arrays.asList(mapper.readValue(Paths.get(anglesFile).toFile(), Angle[].class));
//			for (Angle angle : angles) {
//				angle.save();
//			}
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();			
//		}	
//	}
	
	public static List<Angle> generateAngles(Race race, Entry entry) throws Exception {
		
		//TODO: Add days since last race on angles that need it, such as peaking form, bounces, etc.
		
		try {
//			initializeCatalog();
			//System.out.println(angles.size() + " angles to evaluate for " + horse.getName());
			
			MongoCollection<Angle> collection = database.getCollection("angles", Angle.class);
			FindIterable<Angle> iterable = collection.find();
			
			Angles anglesObj = new Angles();
			List<Angle> retAngles = new ArrayList<Angle>();
			iterable.forEach(angle -> {
//			for (Angle angle : angles) {	
				try {
					Method method = Angles.class.getDeclaredMethod(angle.getMethod(), Race.class, Entry.class);
					//System.out.println(angle.getName() + ": " + (Boolean) method.invoke(anglesObj, race, horse));
					if ((Boolean) method.invoke(anglesObj, race, entry)) {
						//System.out.println(race.getRaceNumber() + " " + horse.getName() + " " + angle.getName() + " " + angle.getMethod());
						retAngles.add(angle);
					}
				} catch (Exception e) {
					
				}
			});
			//System.out.println(horse.getName() + ": " + mapper.writeValueAsString(retAngles));
			return retAngles;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}	
	}	
	
	public static List<List<Angle>> getAngleCombos(int n) throws Exception {
		
		try {
			List<Angle> angles = new ArrayList<Angle>();
			MongoCollection<Angle> collection = database.getCollection("angles", Angle.class);
			FindIterable<Angle> iterable = collection.find();
			iterable.into(angles);
			
			Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(angles.size(), n);
			List<List<Angle>> combos = new ArrayList<List<Angle>>();
			while (iterator.hasNext()) {
				int[] combination = iterator.next();
				List<Angle> angleCombo = new ArrayList<Angle>();
				for (int i = 0; i < n; i++) {
					angleCombo.add(angles.get(combination[i]));
				}
				combos.add(angleCombo);
			}
			return combos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}
	
	public static Boolean angle_0001(Race race, Entry entry) throws Exception {	
		//Most Recent Year Best Speed is equal or better than Max Speed Rating in the Race.	
		try {
			return (entry.getMostRecentYearBestSpeed() >= race.getMaxSpeed() && race.getMaxSpeed() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0002(Race race, Entry entry) throws Exception {	
		//Horse has paired top speed figures; probable bounce.	
		try {
			return (entry.getAge() >= 4 
				&& entry.getPastPerformances().size() > 8
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating(), entry.getPastPerformances().get(1).getBRISSpeedRating())
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating(), entry.getLifetimeBestSpeed()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0003(Race race, Entry entry) throws Exception {	
		//Horse has paired below lifetime top; anticipate move forward.
		try {
			return (entry.getAge() >= 4 
				&& entry.getPastPerformances().size() > 8
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating(), entry.getPastPerformances().get(1).getBRISSpeedRating())
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating() - 5, entry.getLifetimeBestSpeed()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0004(Race race, Entry entry) throws Exception {	
		//3YO has paired figures - expect a forward move.
		try {
			return (entry.getAge() == 3
				&& entry.getPastPerformances().size() > 2
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating(), entry.getPastPerformances().get(1).getBRISSpeedRating()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0005(Race race, Entry entry) throws Exception {	
		//3YO with paired figures followed by a decline in form.
		try {
			return (entry.getAge() == 3
				&& entry.getPastPerformances().size() > 2
				&& isPaired(entry.getPastPerformances().get(0).getBRISSpeedRating(), entry.getPastPerformances().get(1).getBRISSpeedRating())
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() < entry.getPastPerformances().get(1).getBRISSpeedRating() - 2);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0006(Race race, Entry entry) throws Exception {	
		//Potential layoff bounce - lengthy absence followed by overexertion.
		try {
			return (entry.getAge() >= 4
				&& entry.getPastPerformances().size() > 2
				&& entry.getPastPerformances().get(0).getDaysSinceLastRace() > 150
				&& entry.getDaysSinceLastRace() < 42
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() > entry.getLifetimeBestSpeed() - 3
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() > entry.getPastPerformances().get(0).getSpeedPar() + 5);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0007(Race race, Entry entry) throws Exception {	
		//Horse exiting Turf route where it failed to run within a length of the leader at the first three calls.
		try {
			return (entry.getPastPerformances().size() > 0
				&& entry.getDaysSinceLastRace() < 180
				&& entry.getPastPerformances().get(0).getTurfFlag() 
				&& !entry.getPastPerformances().get(0).getOffTheTurfFlag()
				&& race.getFurlongs() < 8 
				&& Math.abs(entry.getPastPerformances().get(0).getFurlongs()) >= 8 
				&& entry.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() > 1 
				&& entry.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() > 1
				&& entry.getPastPerformances().get(0).getStretchBeatenLengthsOnly() > 1 
				&& entry.getPastPerformances().get(0).getFinishPosition() != "1");			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0008(Race race, Entry entry) throws Exception {	
		//Well bred maiden winner can move ahead in class and beat turf winners if final fraction less than :12 per furlong.
		try {
			return (race.getTurfFlag() 
				&& !race.getOffTheTurfFlag()
				&& entry.getPastPerformances().size() > 0
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& Math.abs(entry.getPastPerformances().get(0).getFurlongs()) >= 8
				&& entry.getPastPerformances().get(0).getFinishPosition().equals("1") 
				&& entry.getPedigreeRatingTurf().charAt(0) != '?'
				&& !entry.getPedigreeRatingTurf().contains("?") 
				&& !entry.getPedigreeRatingTurf().contains("*") 
				&& Integer.parseInt(entry.getPedigreeRatingTurf()) > 105
				&& fastTurfFinish(entry.getPastPerformances().get(0)));			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0009(Race race, Entry entry) throws Exception {	
		//Horse possesses 30 percent or more of a race's early speed.
		try {
			return ((float) entry.getSpeedPoints() / race.getTotalSpeedPoints() >= 0.30);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0010(Race race, Entry entry) throws Exception {	
		//Exiting Key Race (An OK or better FF Race)
		try {
			return (entry.getPastPerformances().size() > 0
				&& entry.getDaysSinceLastRace() < 180
				&& entry.getPastPerformances().get(0).getRaceShape().equals("FF") 
				&& OKRace(entry.getPastPerformances().get(0)));			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0011(Race race, Entry entry) throws Exception {	
		//Late Running sprinters may not be dependable in routes.
		try {
			return (entry.getPastPerformances().size() > 0 
				&& race.getFurlongs() >= 8 
				&& firstTimeAtDistanceType(race, entry)
				&& (entry.getRunStyle().equals("P") 
					|| entry.getRunStyle().equals("S") 
					|| (entry.getE2Avg() > 0 
						&& entry.getLatePaceAvg() > entry.getE2Avg())
					)
				);			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0012(Race race, Entry entry) throws Exception {	
		//Front runners with significantly higher pace figures than speed figures may not be dependable in routes.
		try {
			return (entry.getPastPerformances().size() > 0 
				&& race.getFurlongs() >= 8 
				&& firstTimeAtDistanceType(race, entry)
				&& (entry.getRunStyle().equals("E") 
					|| entry.getRunStyle().equals("E/P")) 
				&& entry.getE2Avg() >=  entry.getSpeedRating() + 2);			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}	
	
	public static Boolean angle_0013(Race race, Entry entry) throws Exception {	
		//Horses stretching out win their fair share, even when matched against route horses that delivered good results last time.
		try {
			return (entry.getPastPerformances().size() > 1
				&& race.getFurlongs() >= 8 
				&& firstTimeAtDistanceType(race, entry)
				&& !angle_0011(race, entry)
				&& !angle_0012(race, entry));			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0014(Race race, Entry entry) throws Exception {	
		//Showing improving form.
		try {
			return (entry.getFormPoints() == 5
				&& entry.getDaysSinceLastRace() < 180);			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0015(Race race, Entry entry) throws Exception {	
		//Showing declining form.
		try {
			return (entry.getFormPoints() < 0
				&& entry.getDaysSinceLastRace() < 180);			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	
	public static Boolean angle_0016(Race race, Entry entry) throws Exception {	
		//Less than two four furlong workouts for claiming horse away 60 to 90 days.
		try {
			int workouts = 0;
			for (Workout workout : entry.getWorkouts()) {
				if (workout.getDateOfWorkout().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(22)
						.isAfter(race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && workout.getFurlongs() >= 4) workouts++;
			}
			
			return (entry.getClaimingPrice() > 0
				&& entry.getDaysSinceLastRace() >= 60 
				&& entry.getDaysSinceLastRace() <= 90
				&& workouts < 2);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0017(Race race, Entry entry) throws Exception {	
		//No five furlong workouts in last 14 days for horse away 6 months.
		try {
			int workouts = 0;
			for (Workout workout : entry.getWorkouts()) {
				if (workout.getDateOfWorkout().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(15)
						.isAfter(race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && workout.getFurlongs() >= 5) workouts++;
			}
			
			return (entry.getDaysSinceLastRace() > 180 
				&& workouts == 0 
				&& entry.getBasicFitness() < 15);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0018(Race race, Entry entry) throws Exception {	
		//Sharp Workout in last 90 days.
		try {			
			return (SharpWorkouts(entry.getWorkouts(),race) > 0);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0019(Race race, Entry entry) throws Exception {	
		//2YO with regular workouts and at least two sharp workouts at four or five furlongs, one of which occurred out of the gate.
		try {		
			int sharpGateWorkout = 0;
			int fourOrFiveFurlongSharpWorkouts = 0;
			for (Workout workout : entry.getWorkouts()) {
				if (isSharp(workout) && workout.getFurlongs() >= 4 && workout.getFurlongs() <= 5) {
					fourOrFiveFurlongSharpWorkouts++;
					if (workout.getDescription().length() >= 2 && workout.getDescription().charAt(1) == 'g') sharpGateWorkout++;
				}
			}
			return (race.getAgeRestriction() == AgeRestrictionType.TWO_YEAR_OLDS 
				&& entry.getBasicFitness() >= 15
				&& sharpGateWorkout >=1 
				&& fourOrFiveFurlongSharpWorkouts > 2);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0020(Race race, Entry entry) throws Exception {	
		//Potential Performance Bounce - last speed figure well above average.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() > entry.getSpeedRating() + 5 
				&& entry.getDaysSinceLastRace() <= 120);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0021(Race race, Entry entry) throws Exception {	
		//Potential Bounce-Back Race.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(1).getBRISSpeedRating() > entry.getSpeedRating() + 5
				&& entry.getPastPerformances().get(1).getDaysSinceLastRace() >= 120
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() < entry.getSpeedRating() - 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0022(Race race, Entry entry) throws Exception {	
		//Peaking Form - much-improved race followed by improved workout followed by return to races within 2 to 3 weeks.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() - 5 > entry.getPastPerformances().get(1).getBRISSpeedRating()
				&& entry.getWorkouts().size() >= 2
				&& entry.getWorkouts().get(0).getDateOfWorkout().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.isAfter(entry.getPastPerformances().get(0).getRaceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
				&& entry.getWorkouts().get(0).getTimeOfWorkout() / entry.getWorkouts().get(0).getFurlongs() <
					entry.getWorkouts().get(1).getTimeOfWorkout() / entry.getWorkouts().get(1).getFurlongs()
				&& entry.getDaysSinceLastRace() <= 21
				&& entry.getDaysSinceLastRace() >= 14);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0023(Race race, Entry entry) throws Exception {	
		//3rd start off of layoff with much improved 2nd start (especially sprints and better horses).
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(1).getDaysSinceLastRace() > 90 
				&& entry.getPastPerformances().get(0).getDaysSinceLastRace() < 45
				&& entry.getDaysSinceLastRace() < 45 
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() > entry.getPastPerformances().get(1).getBRISSpeedRating() + 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0024(Race race, Entry entry) throws Exception {	
		//4th start off of layoff with peaking form.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(2).getDaysSinceLastRace() > 90 
				&& entry.getPastPerformances().get(1).getDaysSinceLastRace() < 45
				&& entry.getPastPerformances().get(0).getDaysSinceLastRace() < 45
				&& entry.getDaysSinceLastRace() < 45 
				&& entry.getFormPoints() == 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0025(Race race, Entry entry) throws Exception {	
		//Off-pace horse that ran close to the lead horse at every fraction call - potential top form.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getDaysSinceLastRace() < 180
				&& !entry.getRunStyle().equals("E")  
				&& !entry.getRunStyle().equals("NA")
				&& entry.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() <= 3
				&& entry.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() <= 3 
				&& entry.getPastPerformances().get(0).getStretchBeatenLengthsOnly() <= 3 
				&& entry.getPastPerformances().get(0).getFinishBeatenLengthsOnly() <= 4.5 
				&& entry.getPastPerformances().get(0).getRaceShapeSecondCall() >= 0);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0026(Race race, Entry entry) throws Exception {	
		//Second off Claim with a much-improved last race.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(1).getClaimedCode().equals("c") 
				&& !entry.getPastPerformances().get(0).getClaimedCode().equals("c")
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() > entry.getSpeedRating() + 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0027(Race race, Entry entry) throws Exception {	
		//Second off Claim with a disappointing or dull last race.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(1).getClaimedCode().equals("c") 
				&& !entry.getPastPerformances().get(0).getClaimedCode().equals("c")
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() < entry.getSpeedRating() - 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0028(Race race, Entry entry) throws Exception {	
		//Unexpectedly dull performance followed by multiple level drop in class.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() < entry.getSpeedRating() - 5 
				&& entry.getPurseShift() <= -10000
				&& !entry.getPastPerformances().get(0).getFinishPosition().equals("1") 
				&& !entry.getPastPerformances().get(0).getFinishPosition().equals("2")
				&& !entry.getPastPerformances().get(0).getFinishPosition().equals("3"));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0029(Race race, Entry entry) throws Exception {	
		//Peaking Form - close up at every call in last race and won by 3 or more lengths.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getDaysSinceLastRace() < 180	
				&& entry.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly() < 2.75 
				&& entry.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() < 2.75
				&& entry.getPastPerformances().get(0).getStretchBeatenLengthsOnly() < 2.75 
				&& entry.getPastPerformances().get(0).getFinishPosition().equals("1") 
				&& entry.getPastPerformances().get(0).getWinnersMargin() >= 3);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0030(Race race, Entry entry) throws Exception {	
		//Peaking Form - improved early speed and back within 21 days.
		try {		
			return (entry.getPastPerformances().size() >= 5
				&& entry.getPastPerformances().get(0).getE2() > entry.getE2Avg() + 5 
				&& entry.getDaysSinceLastRace() <= 21);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0031(Race race, Entry entry) throws Exception {	
		//4UP Stakes winner last year showing decline in the first starts of season
		try {			
			return (entry.getAge() >= 4 
				&& race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() <= 4
				&& lastYearStakesWinner(race, entry)
				&& entry.getMostRecentYearBestSpeed() < entry.getSecondMostRecentYearBestSpeed() - 5 
				&& entry.getCurrentYearStarts() > 2) ;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0032(Race race, Entry entry) throws Exception {	
		//Three Year old Stakes winner last year not improved as Four Year Old
		try {			
			return (entry.getAge() == 4 
				&& lastYearStakesWinner(race, entry)
				&& !angle_0031(race, entry)
				&& entry.getMostRecentYearBestSpeed() < entry.getSecondMostRecentYearBestSpeed() + 2 
				&& entry.getCurrentYearStarts() > 2) ;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0033(Race race, Entry entry) throws Exception {	
		//Has not run a good Turf Sprint race within a half furlong of this distance.
		try {		
			Boolean goodTurfRaceWithinHalfFurlong = false;
			for (PastPerformance pp : entry.getPastPerformances()) {
				if (race.getFurlongs() < 8 
					&& pp.getTurfFlag() 
					&& !pp.getOffTheTurfFlag()
					&& GoodRace(pp) 
					&& Math.abs(Math.abs(pp.getFurlongs())) - race.getFurlongs() <= 0.5) goodTurfRaceWithinHalfFurlong = true;
			}
		
			return (entry.getAge() >= 4 
				&& entry.getPastPerformances().size() > 4
				&& race.getTurfFlag()
				&& race.getOffTheTurfFlag()
				&& race.getFurlongs() < 8
				&& !goodTurfRaceWithinHalfFurlong) ;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0034(Race race, Entry entry) throws Exception {	
		//4UP, experienced horses, lifetime top, expect a decline
		try {		
			return (entry.getAge() >= 4 
				&& entry.getPastPerformances().size() > 4
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() == entry.getLifetimeBestSpeed());
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0035(Race race, Entry entry) throws Exception {	
		//Jockey has win percent less than 11% on turf mounts - NO PLAY.
		try {		
			return (race.getTurfFlag() 
				&& !race.getOffTheTurfFlag()
				&& entry.getJockey().getStats().get(0).getCategory().equals("Turf") 
				&& entry.getJockey().getStats().get(0).getStarts() > 0 
				&& (float) entry.getJockey().getStats().get(0).getWins() / entry.getJockey().getStats().get(0).getStarts() < 0.11);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0036(Race race, Entry entry) throws Exception {	
		//NO PLAY on horses aged seven and older.
		try {		
			return (entry.getAge() >= 7);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0037(Race race, Entry entry) throws Exception {	
		//First Time Start with Trainer 1st time stat < 11%, less than 2 sharp workouts AND Sire 1st Time Win % < 11 - NO PLAY.
		try {		
			return (entry.getPastPerformances().size() == 0
				&& (!goodTrainer1stTimeStat(race, entry) && !goodDebutTurfStat(race,entry))
				&& SharpWorkouts(entry.getWorkouts(), race) < 2
				&& (race.getTurfFlag() ? entry.getSireFirstTurfPercent() < 11 : entry.getSireFirstPercent() < 11));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0038(Race race, Entry entry) throws Exception {	
		//First Time Start with Trainer 1st time stat < 11% AND less than 2 sharp workouts - NO PLAY.
		try {		
			return (!angle_0037(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& (!goodTrainer1stTimeStat(race, entry) && !goodDebutTurfStat(race,entry))
				&& SharpWorkouts(entry.getWorkouts(), race) < 2);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0039(Race race, Entry entry) throws Exception {	
		//First Time Start with Trainer 1st time stat < 11% AND Sire 1st Time Win % < 11 - NO PLAY.
		try {		
			return (!angle_0037(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& (!goodTrainer1stTimeStat(race, entry) && !goodDebutTurfStat(race,entry))
				&& (race.getTurfFlag() ? entry.getSireFirstTurfPercent() < 11 : entry.getSireFirstPercent() < 11));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0040(Race race, Entry entry) throws Exception {	
		//First Time Start with less than 2 sharp workouts AND Sire 1st Time Win % < 11 - NO PLAY.
		try {		
			return (!angle_0037(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& SharpWorkouts(entry.getWorkouts(), race) < 2
				&& (race.getTurfFlag() ? entry.getSireFirstTurfPercent() < 11 : entry.getSireFirstPercent() < 11));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0041(Race race, Entry entry) throws Exception {	
		//First Time Start with Trainer 1st time start < 11% - NO PLAY Favorites/Low Price.
		try {		
			return (!angle_0037(race,entry)
				&& !angle_0038(race,entry)
				&& !angle_0039(race,entry)
				&& !angle_0040(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& SharpWorkouts(entry.getWorkouts(), race) < 2
				&& (!goodTrainer1stTimeStat(race, entry) && !goodDebutTurfStat(race,entry)));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0042(Race race, Entry entry) throws Exception {	
		//First Time Start with less than 2 sharp workouts - NO PLAY Favorites/Low Price.
		try {		
			return (!angle_0037(race,entry)
				&& !angle_0038(race,entry)
				&& !angle_0039(race,entry)
				&& !angle_0040(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& SharpWorkouts(entry.getWorkouts(), race) < 2);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0043(Race race, Entry entry) throws Exception {	
		//First Time Start with Sire 1st Time Win % < 11 - NO PLAY Favorites/Low Price.
		try {		
			return (!angle_0037(race,entry)
				&& !angle_0038(race,entry)
				&& !angle_0039(race,entry)
				&& !angle_0040(race,entry)
				&& entry.getPastPerformances().size() == 0
				&& (race.getTurfFlag() ? entry.getSireFirstTurfPercent() < 11 : entry.getSireFirstPercent() < 11));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	
	public static Boolean angle_0044(Race race, Entry entry) throws Exception {	
		//Last Race Speed equal to or better than that race's Par (Maiden).
		try {		
			return (entry.getPastPerformances().size() > 0
				&& (race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT))
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() >= entry.getPastPerformances().get(0).getSpeedPar());
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0045(Race race, Entry entry) throws Exception {	
		//Second Start projected to par with normal improvement.
		try {		
			return (entry.getPastPerformances().size() == 1 
				&& entry.getPastPerformances().get(0).getBRISSpeedRating() + (Math.abs(entry.getPastPerformances().get(0).getFurlongs()) < 8 ? .3 : .2) * 5 >= race.getParSpeed());
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0046(Race race, Entry entry) throws Exception {	
		//Maiden to Maiden Claiming
		try {		
			return ((race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING))
				&& entry.getClaimingPrice() > 0
				&& entry.getPastPerformances().size() > 0
				&& !hasBeenInForAClaim(race, entry)
				&& (entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0047(Race race, Entry entry) throws Exception {	
		//Maiden to Maiden Claiming Two Races Back
		try {		
			Boolean hasBeenInForClaim3orMoreBack = false;
			for (int i=2; i < entry.getPastPerformances().size(); i++) {
				if (entry.getPastPerformances().get(i).getClaimingPrice() > 0) hasBeenInForClaim3orMoreBack = true;
			}
			return ((race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING))
				&& entry.getClaimingPrice() > 0
				&& entry.getPastPerformances().size() > 1 
				&& !hasBeenInForClaim3orMoreBack
				&& entry.getPastPerformances().get(0).getClaimingPrice() > 0
				&& (entry.getPastPerformances().get(1).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
					|| (entry.getPastPerformances().get(1).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING))
				&& entry.getPastPerformances().get(1).getClaimingPrice() == 0));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0048(Race race, Entry entry) throws Exception {	
		//Maiden to Maiden Claiming Three Races Back
		try {		
			Boolean hasBeenInForClaim4orMoreBack = false;
			for (int i=3; i < entry.getPastPerformances().size(); i++) {
				hasBeenInForClaim4orMoreBack = true;
			}
			return ((race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING))
				&& entry.getClaimingPrice() > 0
				&& entry.getPastPerformances().size() > 2 
				&& !hasBeenInForClaim4orMoreBack
				&& entry.getPastPerformances().get(0).getClaimingPrice() > 0
				&& entry.getPastPerformances().get(1).getClaimingPrice() > 0
				&& (entry.getPastPerformances().get(2).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
					|| (entry.getPastPerformances().get(2).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING))
				&& entry.getPastPerformances().get(2).getClaimingPrice() == 0));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0049(Race race, Entry entry) throws Exception {	
		//MCl 50k+ to MCl 40k-.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& race.getPurse() <= 40000 
				&& entry.getPastPerformances().get(0).getPurse() >= 50000);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0050(Race race, Entry entry) throws Exception {	
		//Maiden Claiming drop greater than two levels.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& entry.getPastPerformances().get(0).getPurse() - race.getPurse() > 10000);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0051(Race race, Entry entry) throws Exception {	
		//First Time Starter in Maiden Claiming Race - likely NO PLAY.
		try {		
			return (entry.getPastPerformances().size() == 0
				&& race.getRaceType().equals(RaceType.MAIDEN_CLAIMING));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0052(Race race, Entry entry) throws Exception {	
		//Has 2 or more races close to or better than this race's Par.
		try {		
			return (racesCloseToPar(race, entry) > 2
				&& race.getRaceType().equals(RaceType.CLAIMING));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0053(Race race, Entry entry) throws Exception {	
		//Good Race last out with justifiable class shift.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.CLAIMING)				
				&& GoodRace(entry.getPastPerformances().get(0)) 
				&& entry.getPurseShift() >= 10000 
				&& entry.getClassShift() >= 0);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0054(Race race, Entry entry) throws Exception {	
		//Good Race last out yet dropping in class.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.CLAIMING)
				&& GoodRace(entry.getPastPerformances().get(0)) 
				&& entry.getPurseShift() < 0
				&& entry.getClassShift() < 0);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0055(Race race, Entry entry) throws Exception {	
		//Rising multiple levels but has equaled or exceeded Par at this level.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)	
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.CLAIMING)
				&& entry.getClassShift() > 10000 
				&& ((race.getTurfFlag() 
						&& !race.getOffTheTurfFlag() 
						&& entry.getTurfBestSpeed() >= race.getParSpeed())
					|| ((!race.getTurfFlag() 
						|| (race.getOffTheTurfFlag() && !race.getOntoAllWeatherFlag()))
						&& !race.getAllWeatherSurfaceFlag().equals("A")
						&& entry.getDirtBestSpeed() >= race.getParSpeed())
					|| ((race.getAllWeatherSurfaceFlag().equals("A") || race.getOntoAllWeatherFlag())
						&& entry.getAllWeatherBestSpeed() >= race.getParSpeed())));
			
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0056(Race race, Entry entry) throws Exception {	
		//3 year old claimers may be played in the Fall if best on fundamentals.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& entry.getAge() == 3
				&& race.getAgeRestriction() == AgeRestrictionType.THREE_YEAR_OLDS 
				&& race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP
				&& ((race.getClassification().contains("n2l") 
					|| race.getClassification().contains("n3l")) 
				&& race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() > 7));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0057(Race race, Entry entry) throws Exception {	
		//3 year old claimers should not prevail against older.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& entry.getAge() == 3
				&& race.getAgeRestriction() == AgeRestrictionType.THREE_YEAR_OLDS 
				&& race.getAgeRestrictionRange() == AgeRestrictionRangeType.THAT_AGE_AND_UP
				&& ((race.getClassification().contains("n2l") 
					|| race.getClassification().contains("n3l")) 
				&& race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() <= 7));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0058(Race race, Entry entry) throws Exception {	
		//Three year old drop-downs from allowance are best played January through June.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& (entry.getPastPerformances().get(0).getRaceType().equals(RaceType.ALLOWANCE)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.STARTER_ALLOWANCE))
				&& entry.getAge() == 3 && race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() <= 6);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0059(Race race, Entry entry) throws Exception {	
		//Three year old drop-downs from allowance are best NOT played July through December.
		//TODO: validate this angle from the books
		try {		
			return (entry.getPastPerformances().size() > 0
					&& race.getRaceType().equals(RaceType.CLAIMING)
					&& (entry.getPastPerformances().get(0).getRaceType().equals(RaceType.ALLOWANCE)
						|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
						|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.STARTER_ALLOWANCE))
					&& entry.getAge() == 3 && race.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() >= 7 );
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0060(Race race, Entry entry) throws Exception {	
		//Previous Allowance winner has won or finished close in a Claiming race.
		try {		
			return (race.getRaceType().equals(RaceType.CLAIMING)
				&& hasAllowanceWin(race, entry)
				&& hasClaimingWinOrClose(race, entry));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0061(Race race, Entry entry) throws Exception {	
		//Maiden Claiming grads are never acceptable in higher priced open claiming races.
		try {		
			return (entry.getPastPerformances().size() > 0
				&& race.getRaceType().equals(RaceType.CLAIMING)
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& race.getPurse() > entry.getPastPerformances().get(0).getPurse());
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0062(Race race, Entry entry) throws Exception {	
		//Three year old moving up from claiming - NO PLAY.
		try {		
			return ((race.getRaceType().equals(RaceType.ALLOWANCE)
					|| race.getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.OPTIONAL_CLAIMING_STAKES))
				&& entry.getPastPerformances().size() > 0
				&& entry.getAge() == 3 
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.CLAIMING));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0063(Race race, Entry entry) throws Exception {	
		//Non-claiming three year old has lost two races in a row, well-beaten in each.
		try {		
			return ((race.getRaceType().equals(RaceType.ALLOWANCE)
					|| race.getRaceType().equals(RaceType.ALLOWANCE_OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.OPTIONAL_CLAIMING_STAKES))
				&& entry.getClaimingPrice() == 0
				&& entry.getPastPerformances().size() > 0
				&& entry.getAge() == 3 
				&& entry.getPastPerformances().get(0).getFinishBeatenLengthsOnly() > 5
				&& entry.getPastPerformances().get(1).getFinishBeatenLengthsOnly() > 5);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0064(Race race, Entry entry) throws Exception {	
		//Maiden Claiming grads are never acceptable in allowance or stakes races.
		try {		
			return (!race.getRaceType().equals(RaceType.CLAIMING)
				&& !race.getRaceType().equals(RaceType.OPTIONAL_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
 				&& entry.getPastPerformances().size() > 0
				&& entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0065(Race race, Entry entry) throws Exception {	
		//No horse can rise in class from NW1 or NW2 to Grade 1 - NO PLAY.
		try {		
			return (race.getRaceType().equals(RaceType.GRADE_1)
				&& entry.getPastPerformances().size() > 0
				&& (entry.getPastPerformances().get(0).getRaceClassification().contains("n1")
						|| entry.getPastPerformances().get(0).getRaceClassification().contains("n2")));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0066(Race race, Entry entry) throws Exception {	
		//4 years old and up and having as many as 15 races in a G1/G2 race
		try {		
			return (race.getRaceType().equals(RaceType.GRADE_1)
				&& entry.getAge() >= 4 
				&& entry.getLifetimeStarts() >= 15 && 
				(entry.getPastPerformances().get(0).getRaceType().equals(RaceType.GRADE_3 )
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.NON_GRADED))
				&& entry.getPastPerformances().get(0).getPurse() < 150000);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0067(Race race, Entry entry) throws Exception {	
		//No horse can rise in class from NW1 to Grade 2 - NO PLAY.
		try {		
			return (race.getRaceType().equals(RaceType.GRADE_2)
					&& entry.getPastPerformances().size() > 0
					&& entry.getPastPerformances().get(0).getRaceClassification().contains("n1"));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0068(Race race, Entry entry) throws Exception {	
		//One of the Likeliest winners (Grade 1 or 2 winner).
		try {		
			return (race.getRaceType().equals(RaceType.GRADE_3)
				&& entry.getPastPerformances().size() > 0
				&& (entry.getPastPerformances().get(0).getRaceType().equals(RaceType.GRADE_2)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.GRADE_1))
				&& entry.getPastPerformances().get(0).getFinishPosition().equals("1"));
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0069(Race race, Entry entry) throws Exception {	
		//Previous NW1 or NW2 winner with speed rating at or better than par and not a slow pace; can accept lower class and speed.
		try {	
			if (!race.getRaceType().equals(RaceType.GRADE_3)) return false;
			for (PastPerformance pp : entry.getPastPerformances()) {
				if ((pp.getRaceClassification().contains("n1") || pp.getRaceClassification().contains("n2")) 
					&& pp.getFinishPosition().equals("1") && pp.getBRISSpeedRating() >= pp.getSpeedPar()
					&& pp.getRaceShapeSecondCall() > 0) return true;
			}
			return false;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0070(Race race, Entry entry) throws Exception {	
		//Previous NW3 or NW4 winner; can accept lower class and speed.
		try {	
			if (!race.getRaceType().equals(RaceType.GRADE_3)) return false;
			for (PastPerformance pp : entry.getPastPerformances()) {
				if ((pp.getRaceClassification().contains("n3") || pp.getRaceClassification().contains("n4")) 
						&& pp.getFinishPosition().equals("1")) return true;
			}
			return false;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0071(Race race, Entry entry) throws Exception {	
		//Impressive Maiden winner that has not lost an allowance; can accept lower class and speed.
		try {	
			return (race.getRaceType().equals(RaceType.GRADE_3)
				&& impressiveMaidenWinner(race, entry)
				&& hasNotLostAllowance(race, entry)
				&& !racedNonAllowance(race, entry));		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0072(Race race, Entry entry) throws Exception {	
		//Away 365+ days
		try {	
			return (entry.getDaysSinceLastRace() >= 365);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0073(Race race, Entry entry) throws Exception {	
		//Away 180-365 days
		try {
			return (entry.getDaysSinceLastRace() > 0
				&& entry.getDaysSinceLastRace() >= 180
				&& entry.getDaysSinceLastRace() < 365);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0074(Race race, Entry entry) throws Exception {	
		//Away 90-180 days
		try {	
			return (entry.getDaysSinceLastRace() > 0
				&& entry.getDaysSinceLastRace() >= 90
				&& entry.getDaysSinceLastRace() < 180);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0075(Race race, Entry entry) throws Exception {	
		//Failed as Favorite last out.
		try {
			
			return (entry.getPastPerformances().size() > 0
				&& entry.getPastPerformances().get(0).getFavoriteFlag() > 0
				&& !entry.getPastPerformances().get(0).getFinishPosition().equals("1"));		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0076(Race race, Entry entry) throws Exception {	
		//Max ARating
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getARating() > max) max = runner.getARating();
			}
			return (max > 0
				&& entry.getARating()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0077(Race race, Entry entry) throws Exception {	
		//Max Prime Power
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getPrimePower() > max) max = runner.getPrimePower();
			}
			return (max > 0
				&& entry.getPrimePower()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0078(Race race, Entry entry) throws Exception {	
		//Max Speed Rating
		try {	
			return (race.getMaxSpeedRating() > 0
				&& entry.getSpeedRating() == race.getMaxSpeedRating());		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0079(Race race, Entry entry) throws Exception {	
		//Max Speed 
		try {	
			if (race.getMaxSpeed() == 0) return false;
			for (PastPerformance pp : entry.getPastPerformances()) {
				if (pp.getBRISSpeedRating() == race.getMaxSpeed()) return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}		
	}
	
	public static Boolean angle_0080(Race race, Entry entry) throws Exception {	
		//Max Class Rating
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getClassRating() > max) max = runner.getClassRating();
			}
			return (max > 0
				&& entry.getClassRating()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0081(Race race, Entry entry) throws Exception {	
		//Max BRIS Average Class Last 3
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getBrisAvgLast3Class() > max) max = runner.getBrisAvgLast3Class();
			}
			return (max > 0
				&& entry.getBrisAvgLast3Class()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0082(Race race, Entry entry) throws Exception {	
		//Max BRIS Current Class
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getBrisCurrentClass() > max) max = runner.getBrisCurrentClass();
			}
			return (max > 0
				&& entry.getBrisCurrentClass()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0083(Race race, Entry entry) throws Exception {	
		//Max ACL
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getAverageCompetitiveLevel() > max) max = runner.getAverageCompetitiveLevel();
			}
			return (max > 0
				&& entry.getAverageCompetitiveLevel()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0084(Race race, Entry entry) throws Exception {	
		//Max ARating Form
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getARatingForm() > max) max = runner.getARatingForm();
			}
			return (max > 0
				&& entry.getARatingForm()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0085(Race race, Entry entry) throws Exception {	
		//Max ARating Connections
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getARatingConnections() > max) max = runner.getARatingConnections();
			}
			return (max > 0
				&& entry.getARatingConnections()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0086(Race race, Entry entry) throws Exception {	
		//Max Combined Pace Rating
		try {	
			float max = 0f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getCombinedPaceAvg() > max) max = runner.getCombinedPaceAvg();
			}
			return (max > 0
				&& entry.getCombinedPaceAvg()  == max);		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0087(Race race, Entry entry) throws Exception {	
		//Pace Advantage
		try {	
			return (race.getAdvantagedEntries().contains(entry.getName()));		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0088(Race race, Entry entry) throws Exception {	
		//ML Favorite
		try {	
			
			float min = 99f;
			for (Entry runner : race.getUnscratchedEntries()) {
				if (runner.getMLOdds() < min) min = runner.getCombinedPaceAvg();
			}
			return (entry.getMLOdds()  == min);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	/*
	public static Boolean angle_0089(Race race, Horse horse) throws Exception {	
		//Max ARating and Pace Advantage
		try {	

			return (angle_0076(race, horse) && angle_0087(race, horse));	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0090(Race race, Horse horse) throws Exception {	
		//Max ARating and ML Favorite
		try {	

			return (angle_0076(race, horse) && angle_0088(race, horse));	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	*/
	public static Boolean angle_0091(Race race, Entry entry) throws Exception {	
		//Winner Moving Up
		try {
			return (entry.getPastPerformances().size() > 2
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
				&& !race.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& !race.getRaceType().equals(RaceType.GRADE_1)
				&& !race.getRaceType().equals(RaceType.GRADE_2)
				&& !race.getRaceType().equals(RaceType.GRADE_3)
				&& race.getPurse() > entry.getPastPerformances().get(0).getPurse()
				&& race.getParSpeed() > entry.getPastPerformances().get(0).getSpeedPar()
				&& entry.getPastPerformances().get(0).getFinishPosition().equals("1"));	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0092(Race race, Entry entry) throws Exception {	
		//Pace Setter Dropping Down
		try {	

			return (entry.getPastPerformances().size() > 2
				&& entry.getLifetimeWins() > 1
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& entry.getPastPerformances().get(0).getFirstCallPosition() == "1"
				&& entry.getPastPerformances().get(0).getSecondCallPosition() == "1"
				&& entry.getPastPerformances().get(0).getFinishPosition() != "1"
				&& entry.getPastPerformances().get(0).getFinishBeatenLengthsOnly() < 10
				&& race.getPurse() < entry.getPastPerformances().get(0).getPurse()
				&& race.getParSpeed() < entry.getPastPerformances().get(0).getSpeedPar());	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0093(Race race, Entry entry) throws Exception {	
		//Wide Runner Dropping Down
		try {	
			Pattern pattern = Pattern.compile("(([6789])w)+");
			if (entry.getPastPerformances().size() <= 1) return false;
			
			Matcher matcher = pattern.matcher(entry.getPastPerformances().get(0).getExtendedStartComment());
			return (matcher.find() 
				&& entry.getPastPerformances().size() > 2
				&& entry.getLifetimeWins() > 1
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
				&& !entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING)
				&& entry.getPastPerformances().get(0).getFinishPosition() != "1"
				&& entry.getPastPerformances().get(0).getFinishBeatenLengthsOnly() <= entry.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly()
				&& entry.getPastPerformances().get(0).getSecondCallBeatenLengthsOnly() <= entry.getPastPerformances().get(0).getFirstCallBeatenLengthsOnly()
				&& race.getPurse() < entry.getPastPerformances().get(0).getPurse()
				&& race.getParSpeed() <entry.getPastPerformances().get(0).getSpeedPar());	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0094(Race race, Entry entry) throws Exception {	
		//First Time Gelding
		try {	

			return (entry.getSex().equals("g"));	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0095(Race race, Entry entry) throws Exception {	
		//Maiden Moving Up
		try {
			return (entry.getPastPerformances().size() > 0
				&& (entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
					|| entry.getPastPerformances().get(0).getRaceType().equals(RaceType.MAIDEN_CLAIMING))
				&& (race.getRaceType().equals(RaceType.MAIDEN_CLAIMING) 
					|| race.getRaceType().equals(RaceType.MAIDEN_OPTIONAL_CLAIMING)
					|| race.getRaceType().equals(RaceType.MAIDEN_SPECIAL_WEIGHT))
				&& race.getPurse() > entry.getPastPerformances().get(0).getPurse()
				&& race.getParSpeed() > entry.getPastPerformances().get(0).getSpeedPar());	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0096(Race race, Entry entry) throws Exception {	
		//Only Turf Experience in Race
		try {
			if (!race.getTurfFlag()) return false;
			if (race.getOffTheTurfFlag()) return false;
			int hasTurfExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getTurfStarts() > 0) hasTurfExperience++;
			}
			
			return (entry.getTurfStarts() > 0 && hasTurfExperience == 1);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0097(Race race, Entry entry) throws Exception {	
		//One of two with Turf Experience in Race
		try {
			if (!race.getTurfFlag()) return false;
			if (race.getOffTheTurfFlag()) return false;
			int hasTurfExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getTurfStarts() > 0) hasTurfExperience++;
			}
			
			return (entry.getTurfStarts() > 0 && hasTurfExperience == 2);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0098(Race race, Entry entry) throws Exception {	
		//Only AW Experience in Race
		try {
			if (!race.getAllWeatherSurfaceFlag().equals("A") && !race.getOntoAllWeatherFlag()) return false;
			
			int hasAWExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getAllWeatherStarts() > 0) hasAWExperience++;
			}
			
			return (entry.getAllWeatherStarts() > 0 && hasAWExperience == 1);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0099(Race race, Entry entry) throws Exception {	
		//One of two with AW Experience in Race
		try {
			if (!race.getAllWeatherSurfaceFlag().equals("A") && !race.getOntoAllWeatherFlag()) return false;
			int hasAWExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getAllWeatherStarts() > 0) hasAWExperience++;
			}
			
			return (entry.getAllWeatherStarts() > 0 && hasAWExperience == 2);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0100(Race race, Entry entry) throws Exception {	
		//Only Fast Dirt Experience in Race
		try {
			if (race.getAllWeatherSurfaceFlag().equals("A") || race.getOntoAllWeatherFlag() || 
				(race.getTurfFlag() && !race.getOffTheTurfFlag())) return false;
			if (!race.getTrackCondition().equals("ft")) return false;
			int hasFastDirtExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getDirtStarts() > 0) hasFastDirtExperience++;
			}
			
			return (entry.getDirtStarts() > 0 && hasFastDirtExperience == 1);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0101(Race race, Entry entry) throws Exception {	
		//One of two with Fast Dirt Experience in Race
		try {
			if (race.getAllWeatherSurfaceFlag().equals("A") || race.getOntoAllWeatherFlag() || 
					(race.getTurfFlag() && !race.getOffTheTurfFlag())) return false;
				if (!race.getTrackCondition().equals("ft")) return false;
				int hasFastDirtExperience = 0;
				for (Entry h : race.getUnscratchedEntries()) {
					if (h.getDirtStarts() > 0) hasFastDirtExperience++;
				}
				
				return (entry.getDirtStarts() > 0 && hasFastDirtExperience == 1);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0102(Race race, Entry entry) throws Exception {	
		//Only Wet Dirt Experience in Race
		try {
			if (race.getAllWeatherSurfaceFlag().equals("A") || race.getOntoAllWeatherFlag() || 
				(race.getTurfFlag() && !race.getOffTheTurfFlag())) return false;
			if (race.getTrackCondition().equals("ft")) return false;
			int hasWetDirtExperience = 0;
			for (Entry h : race.getUnscratchedEntries()) {
				if (h.getWetStarts() > 0) hasWetDirtExperience++;
			}
			
			return (entry.getWetStarts() > 0 && hasWetDirtExperience == 1);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
	
	public static Boolean angle_0103(Race race, Entry entry) throws Exception {	
		//One of two with Wet Track Dirt Experience in Race
		try {
			if (race.getAllWeatherSurfaceFlag().equals("A") || race.getOntoAllWeatherFlag() || 
					(race.getTurfFlag() && !race.getOffTheTurfFlag())) return false;
				if (race.getTrackCondition().equals("ft")) return false;
				int hasWetDirtExperience = 0;
				for (Entry h : race.getUnscratchedEntries()) {
					if (h.getWetStarts() > 0) hasWetDirtExperience++;
				}
				
				return (entry.getWetStarts() > 0 && hasWetDirtExperience == 2);	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}			
	}
}
