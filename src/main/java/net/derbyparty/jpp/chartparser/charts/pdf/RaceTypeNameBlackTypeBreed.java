package net.derbyparty.jpp.chartparser.charts.pdf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;
import net.derbyparty.jpp.object.RaceType;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses and stores the race type (e.g. "STAKES"), name (e.g. "Kentucky Derby"), grade (e.g. 1),
 * black type (e.g. "Grade 1"), and for what {@link Breed}
 */
@JsonPropertyOrder({"breed"})
public class RaceTypeNameBlackTypeBreed {

    static final Pattern RACE_TYPE_NAME_GRADE_BREED =
            Pattern.compile("([A-Z ]+)( (.+) (Grade (\\d))| (.+ S\\.)( (.+))?| (.+))? - " +
                    "(Thoroughbred|Quarter Horse|Arabian|Mixed)");
        
    public String type;
    public RaceType raceType;
    public String name;
    public Integer grade;
    public String blackType;
    public Breed breed;

    public RaceTypeNameBlackTypeBreed(String type, RaceType raceType, Breed breed) {
        this(type, raceType, null, breed);
    }

    public RaceTypeNameBlackTypeBreed(String type, RaceType raceType, String name, Breed breed) {
        this(type, raceType, name, null, breed);
    }

    public RaceTypeNameBlackTypeBreed(String type, RaceType raceType, String name, String blackType, Breed breed) {
        this(type, raceType, name, null, blackType, breed);
    }

    @JsonCreator
    public RaceTypeNameBlackTypeBreed(String type, RaceType raceType, String name, Integer grade, String blackType,
            Breed breed) {
        this.type = type;
        this.raceType = raceType;
        this.name = name;
        this.grade = grade;
        this.blackType = blackType;
        this.breed = breed;
    }

    public static RaceTypeNameBlackTypeBreed parse(List<List<ChartCharacter>> lines)
            throws Breed.NoMatchingBreedException, RaceTypeNameOrBreedNotIdentifiable {
    	
    	for (int i = 0; i < lines.size(); i++) {
    		List<ChartCharacter> line = lines.get(i);
            String rawText = Chart.convertToText(line);
            Optional<RaceTypeNameBlackTypeBreed> raceTypeNameGradeBreed =
                    parseRaceTypeNameBlackTypeBreed(rawText);
            if (raceTypeNameGradeBreed.isPresent()) {
                return raceTypeNameGradeBreed.get();
            }
    	}
    	for (int i = 0; i < lines.size(); i++) {
            
            if (i < lines.size()-1) {
            	String rawText = Chart.convertToText(lines.get(i)) + " " + Chart.convertToText(lines.get(i+1));
            	Optional<RaceTypeNameBlackTypeBreed> raceTypeNameGradeBreed =
	                    parseRaceTypeNameBlackTypeBreed(rawText);
	            if (raceTypeNameGradeBreed.isPresent()) {
	                return raceTypeNameGradeBreed.get();
	            }
            }
            
        }
        throw new RaceTypeNameOrBreedNotIdentifiable("Unable to identify a valid race type, name " +
                "and/or breed");
    }

    static Optional<RaceTypeNameBlackTypeBreed> parseRaceTypeNameBlackTypeBreed(String text)
            throws Breed.NoMatchingBreedException {
        Matcher matcher = RACE_TYPE_NAME_GRADE_BREED.matcher(text);
     
        if (matcher.find()) {
            String breedText = matcher.group(10);
            Breed breed = Breed.forChartValue(breedText);
            String type = matcher.group(1);
            if (type == null) {
            	System.out.println("WARNING: " + "A race type was not found from text: " + text);
            }

            Integer grade = null;
            String blackType = null;
            String gradeText = matcher.group(5);
            if (gradeText != null) {
                grade = Integer.parseInt(gradeText);
                blackType = matcher.group(4);
            }

            String name = null;
            if (grade != null) {
                name = matcher.group(3);
            } else {
                String raceNameText = matcher.group(6);
                if (raceNameText != null) {
                    name = raceNameText;
                } else {
                    raceNameText = matcher.group(9);
                    if (raceNameText != null) {
                        name = raceNameText;
                    }
                }
                String blackTypeText = matcher.group(8);
                if (blackTypeText != null) {
                    blackType = blackTypeText;
                }
            }

            // correct when race name starts with capital letters
            if (type.toUpperCase().startsWith("STAKES")) {
                String[] split = type.split("\\s", 2); // split on the first space
                type = split[0];
                if (split.length > 1 && split[1] != null) {
                    name = split[1] + " " + name;
                }
            }
            
            RaceType raceType = null;
            if (type.equals("STAKES") || type.equals("STARTER STAKES")) {
            	if (grade == null) {
            		raceType = RaceType.NON_GRADED;
            	} else {
	            	if (grade == 1) raceType = RaceType.GRADE_1;
	            	if (grade == 2) raceType = RaceType.GRADE_2;
	            	if (grade == 3) raceType = RaceType.GRADE_3;
            	}
            	
            }
            if (type.equals("ALLOWANCE") || type.equals("HANDICAP")) raceType = RaceType.ALLOWANCE;
            if (type.equals("STARTER ALLOWANCE")) raceType = RaceType.STARTER_ALLOWANCE;
            if (type.equals("STARTER HANDICAP")) raceType = RaceType.STARTER_HANDICAP;
            if (type.equals("CLAIMING") || type.equals("WAIVER CLAIMING")) raceType = RaceType.CLAIMING;
            if (type.equals("OPTIONAL_CLAIMING") || type.equals("STARTER OPTIONAL CLAIMING")) raceType = RaceType.OPTIONAL_CLAIMING;
            if (type.equals("MAIDEN SPECIAL WEIGHT")) raceType = RaceType.MAIDEN_SPECIAL_WEIGHT;
            if (type.equals("MAIDEN CLAIMING") || type.equals("WAIVER MAIDEN CLAIMING")) raceType = RaceType.MAIDEN_CLAIMING;
            if (type.equals("ALLOWANCE OPTIONAL CLAIMING")) raceType = RaceType.ALLOWANCE_OPTIONAL_CLAIMING;
            if (type.equals("MAIDEN OPTIONAL CLAIMING")) raceType = RaceType.MAIDEN_OPTIONAL_CLAIMING;
            if (type.equals("OPTIONAL CLAIMING STAKES")) raceType = RaceType.OPTIONAL_CLAIMING_STAKES;
            
            if (raceType == null) {
            	System.out.println("WARNING: " + "A race type was not mapped from type : " + type);
            }

            return Optional.of(new RaceTypeNameBlackTypeBreed(type, raceType, name, grade, blackType, breed));
        }
        return Optional.empty();
    }

    public String getType() {
        return type;
    }
    
    public RaceType getRaceType() {
        return raceType;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getBlackType() {
        return blackType;
    }

    public Breed getBreed() {
        return breed;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RaceTypeNameBlackTypeBreed [type=").append(type).append(", raceType=").append(raceType)
				.append(", name=").append(name).append(", grade=").append(grade).append(", blackType=")
				.append(blackType).append(", breed=").append(breed).append("]");
		return builder.toString();
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RaceTypeNameBlackTypeBreed other = (RaceTypeNameBlackTypeBreed) obj;
		if (blackType == null) {
			if (other.blackType != null)
				return false;
		} else if (!blackType.equals(other.blackType))
			return false;
		if (breed != other.breed)
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (raceType != other.raceType)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blackType == null) ? 0 : blackType.hashCode());
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((raceType == null) ? 0 : raceType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

    public static class RaceTypeNameOrBreedNotIdentifiable extends ChartParserException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RaceTypeNameOrBreedNotIdentifiable(String message) {
            super(message);
        }
    }

	public RaceTypeNameBlackTypeBreed() {
		super();
		
		// TODO Auto-generated constructor stub
	}
    
}
