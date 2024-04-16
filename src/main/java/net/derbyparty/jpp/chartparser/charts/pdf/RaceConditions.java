package net.derbyparty.jpp.chartparser.charts.pdf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import net.derbyparty.jpp.chartparser.exceptions.ChartParserException;
import net.derbyparty.jpp.object.AgeRestrictionRangeType;
import net.derbyparty.jpp.object.AgeRestrictionType;
import net.derbyparty.jpp.object.SexRestrictionType;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.codecs.pojo.annotations.BsonIgnore;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Locale.US;
import static net.derbyparty.jpp.chartparser.charts.pdf.DistanceSurfaceTrackRecord.DIST_SURF_RECORD_PATTERN;
import static net.derbyparty.jpp.chartparser.charts.pdf.RaceTypeNameBlackTypeBreed.RACE_TYPE_NAME_GRADE_BREED;

/**
 * Parses and stores the textual description of the race conditions and, if applicable, the minimum
 * and maximum claiming prices that can be availed of
 */
@JsonPropertyOrder({"raceTypeNameBlackTypeBreed", "text", "claimingPriceRange"})
public class RaceConditions {

	public String text;
    @JsonInclude(NON_NULL)
    public ClaimingPriceRange claimingPriceRange;
    @JsonProperty("raceTypeNameBlackTypeBreed") // required for property order but unwrapped
    @JsonUnwrapped
    public RaceTypeNameBlackTypeBreed raceTypeNameBlackTypeBreed;
    public String raceClassification;
    public SexRestrictionType sexRestrictionType;
    public AgeRestrictionType ageRestrictionType;
    public AgeRestrictionRangeType ageRestrictionRangeType;
    @BsonIgnore
    public String raceCategory;

    public RaceConditions(String text,
            ClaimingPriceRange claimingPriceRange,
            String raceClassification,
            SexRestrictionType sexRestrictionType,
            AgeRestrictionType ageRestrictionType,
            AgeRestrictionRangeType ageRestrictionRangeType) {
        this.text = text;
        this.claimingPriceRange = claimingPriceRange;
        this.raceClassification = raceClassification;
        this.sexRestrictionType = sexRestrictionType;
        this.ageRestrictionType = ageRestrictionType;
        this.ageRestrictionRangeType = ageRestrictionRangeType;
    }

    @JsonCreator
    private RaceConditions(String text, ClaimingPriceRange claimingPriceRange, String raceClassification,
    		SexRestrictionType sexRestrictionType,
            AgeRestrictionType ageRestrictionType,
            AgeRestrictionRangeType ageRestrictionRangeType,
            RaceTypeNameBlackTypeBreed raceTypeNameBlackTypeBreed) {
        this(text, claimingPriceRange, raceClassification, sexRestrictionType, ageRestrictionType, ageRestrictionRangeType);
        this.raceTypeNameBlackTypeBreed = raceTypeNameBlackTypeBreed;
    }

    // handles multi-line
    public static RaceConditions parse(List<List<ChartCharacter>> lines)
            throws ChartParserException {
        boolean found = false;
        StringBuilder raceConditionsBuilder = new StringBuilder();
        String prefix = "";
        for (List<ChartCharacter> line : lines) {
            String text = Chart.convertToText(line);
            if (found) {
                Matcher matcher = DIST_SURF_RECORD_PATTERN.matcher(text);
                if (matcher.find() && DistanceSurfaceTrackRecord.isValidDistanceText(text)) {
                    break;
                } else {
                    // prefix a space at the start of each line (except for the first)
                    raceConditionsBuilder.append(prefix).append(text);
                    prefix = " ";
                }
            }
            Matcher matcher = RACE_TYPE_NAME_GRADE_BREED.matcher(text);
            if (matcher.find()) {
                found = true;
            }
        }
        String raceConditions = raceConditionsBuilder.toString();
        ClaimingPriceRange claimingPriceRange = ClaimingPriceRange.parse(raceConditions);
        String raceClassification = parseRaceClassification(raceConditions);
        SexRestrictionType sexRestrictionType = parseSexRestrictionType(raceConditions);
        AgeRestrictionType ageRestrictionType = parseAgeRestrictionType(raceConditions);
        AgeRestrictionRangeType ageRestrictionRangeType = parseAgeRestrictionRangeType(raceConditions);

        return new RaceConditions(raceConditions, claimingPriceRange, raceClassification, sexRestrictionType, ageRestrictionType, ageRestrictionRangeType);
    }

    public String getText() {
        return text;
    }

    public ClaimingPriceRange getClaimingPriceRange() {
        return claimingPriceRange;
    }

    public void setText(String text) {
		this.text = text;
	}

	public void setClaimingPriceRange(ClaimingPriceRange claimingPriceRange) {
		this.claimingPriceRange = claimingPriceRange;
	}

	public void setRaceClassification(String raceClassification) {
		this.raceClassification = raceClassification;
	}

	public void setSexRestrictionType(SexRestrictionType sexRestrictionType) {
		this.sexRestrictionType = sexRestrictionType;
	}

	public void setAgeRestrictionType(AgeRestrictionType ageRestrictionType) {
		this.ageRestrictionType = ageRestrictionType;
	}

	public void setAgeRestrictionRangeType(AgeRestrictionRangeType ageRestrictionRangeType) {
		this.ageRestrictionRangeType = ageRestrictionRangeType;
	}

	public RaceTypeNameBlackTypeBreed getRaceTypeNameBlackTypeBreed() {
        return raceTypeNameBlackTypeBreed;
    }

    public void setRaceTypeNameBlackTypeBreed(RaceTypeNameBlackTypeBreed
            raceTypeNameBlackTypeBreed) {
        this.raceTypeNameBlackTypeBreed = raceTypeNameBlackTypeBreed;
    }
    
    public String getRaceClassification() {
		return raceClassification;
	}
    
    //@BsonIgnore
	public String getRaceCategory () {
		
		String raceCategory = "";

		raceCategory += this.getRaceTypeNameBlackTypeBreed().getRaceType().toString();
		
		if (this.getAgeRestrictionRangeType().equals(AgeRestrictionRangeType.THAT_AGE_ONLY)) {
			if (this.getAgeRestrictionType().equals(AgeRestrictionType.TWO_YEAR_OLDS))
				raceCategory += " 2YO";
			if (this.getAgeRestrictionType().equals(AgeRestrictionType.THREE_YEAR_OLDS))
				raceCategory += " 3YO";				
		}
		
		
		String raceClassification = this.getRaceClassification();
		if (raceClassification != null) 
			if (raceClassification.equals("NW1 X")) raceCategory += " NW1X";
				else if (raceClassification.equals("NW1 B")) raceCategory += " NW1X S";
				else if (raceClassification.equals("SNW1 X")) raceCategory += " NW1X S";
				else if (raceClassification.equals("NW2 L")) raceCategory += " NW2";
				else if (raceClassification.equals("NW2 L X")) raceCategory += " NW2";
				else if (raceClassification.equals("SNW2 L")) raceCategory += " NW2 S";
				else if (raceClassification.equals("NW2 X")) raceCategory += " NW2X";
				else if (raceClassification.equals("SNW2 X")) raceCategory += " NW2X S";
				else if (raceClassification.equals("NW3 L")) raceCategory += " NW3";
				else if (raceClassification.equals("NW3 L X")) raceCategory += " NW3";
				else if (raceClassification.equals("SNW3 L")) raceCategory += " NW3 S";
				else if (raceClassification.contains("S")) raceCategory += " S";
			
		return raceCategory;
	}

	public static String parseRaceClassification(String raceConditions) {
        
    	Pattern RACE_CLASSIFICATION_PATTERN =
                Pattern.compile("(\\(([A-Z0-9 $]+)\\))");
        
        Matcher classMatcher = RACE_CLASSIFICATION_PATTERN.matcher(raceConditions);

        if (classMatcher.find()) {
        	return classMatcher.group(2);
        } 
    	return null;
    }
	

	public SexRestrictionType getSexRestrictionType() {
		return sexRestrictionType;
	}

	public AgeRestrictionType getAgeRestrictionType() {
		return ageRestrictionType;
	}

	public AgeRestrictionRangeType getAgeRestrictionRangeType() {
		return ageRestrictionRangeType;
	}

	public static SexRestrictionType parseSexRestrictionType(String raceConditions) {
        
		if (raceConditions.contains("FILLIES AND MARES")) return SexRestrictionType.FILLIES_AND_MARES;
		if (raceConditions.contains("FILLIES")) return SexRestrictionType.FILLIES;
		if (raceConditions.contains("COLTS AND GELDINGS")) return SexRestrictionType.COLTS_AND_GELDINGS;
    	return SexRestrictionType.NO_SEX_RESTRICTIONS;
    }
	
	public static AgeRestrictionType parseAgeRestrictionType(String raceConditions) {
		
        
		if (raceConditions.contains("TWO YEAR OLDS") || raceConditions.contains("TWO YEARS OLD")) return AgeRestrictionType.TWO_YEAR_OLDS;
		if (raceConditions.contains("THREE YEAR OLDS") || raceConditions.contains("THREE YEARS OLD")) return AgeRestrictionType.THREE_YEAR_OLDS;
		if (raceConditions.contains("FOUR YEAR OLDS") || raceConditions.contains("FOUR YEARS OLD")) return AgeRestrictionType.FOUR_YEAR_OLDS;
		if (raceConditions.contains("FIVE YEAR OLDS") || raceConditions.contains("FIVE YEARS OLD")) return AgeRestrictionType.FIVE_YEAR_OLDS;
		if (raceConditions.contains("THREE AND FOUR YEAR OLDS")) return AgeRestrictionType.THREE_AND_FOUR_YEAR_OLDS;
		if (raceConditions.contains("FOUR AND FIVE YEAR OLDS")) return AgeRestrictionType.FOUR_AND_FIVE_YEAR_OLDS;
		if (raceConditions.contains("FOUR AND FIVE YEAR OLDS")) return AgeRestrictionType.FOUR_AND_FIVE_YEAR_OLDS;
		if (raceConditions.contains("THREE FOUR AND FIVE YEAR OLDS")) return AgeRestrictionType.THREE_FOUR_AND_FIVE_YEAR_OLDS;
    	return AgeRestrictionType.ALL_AGES;
    }
	
	public static AgeRestrictionRangeType parseAgeRestrictionRangeType(String raceConditions) {
	
        
		if (raceConditions.contains("AND UPWARD")) return AgeRestrictionRangeType.THAT_AGE_AND_UP;
    	return AgeRestrictionRangeType.THAT_AGE_ONLY;
    }
	
    /**
     * Stores the range of the claiming prices that apply to a claiming race. Some claiming races
     * allow setting the claim within a particular range, others give weight allowances for lower
     * claim prices, while others use a single value (meaning the minimum and maximum are the same)
     */
    public static class ClaimingPriceRange {
        private static final Pattern CLAIMING_PRICE_PATTERN =
                Pattern.compile("Claiming Price: " +
                        "\\$([0-9]{1,3}(,[0-9]{3})*)( - \\$([0-9]{1,3}(,[0-9]{3})*))?$");

        private int min;
        private int max;

        public ClaimingPriceRange(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        static ClaimingPriceRange parse(String raceConditions) throws ChartParserException {
            Integer maxClaim = null, minClaim = null;
            Matcher matcher = CLAIMING_PRICE_PATTERN.matcher(raceConditions);

            if (matcher.find()) {
                String maxClaimAmount = matcher.group(1);
                if (maxClaimAmount != null) {
                    try {
                        maxClaim =
                                NumberFormat.getNumberInstance(US).parse(maxClaimAmount).intValue();
                    } catch (ParseException e) {
                        throw new ChartParserException(String.format("Unable to parse a max claim" +
                                " price value from text: %s", maxClaimAmount), e);
                    }
                }

                String minClaimAmount = matcher.group(4);
                if (minClaimAmount != null) {
                    try {
                        minClaim =
                                NumberFormat.getNumberInstance(US).parse(minClaimAmount).intValue();
                    } catch (ParseException e) {
                        throw new ChartParserException(String.format("Unable to parse a min claim" +
                                " price value from text: %s", minClaimAmount), e);
                    }
                } else if (maxClaim != null) {
                    minClaim = maxClaim;
                }

                // integrity check
                if (minClaim != null && maxClaim != null) {
                    if (minClaim > maxClaim) {
                        int holder = maxClaim;
                        minClaim = holder;
                        maxClaim = minClaim;
                    }
                }

                return new ClaimingPriceRange(minClaim, maxClaim);
            }

            return null;
        }

        public Integer getMin() {
            return min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMin(int min) {
			this.min = min;
		}

		public void setMax(int max) {
			this.max = max;
		}

		@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClaimingPriceRange that = (ClaimingPriceRange) o;

            if (min != that.min) return false;
            return max == that.max;
        }

        @Override
        public int hashCode() {
            int result = min;
            result = 31 * result + max;
            return result;
        }

        @Override
        public String toString() {
            return "ClaimingPriceRange{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }

		public ClaimingPriceRange() {
			super();
			
			// TODO Auto-generated constructor stub
		}
        
    }
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RaceConditions other = (RaceConditions) obj;
		if (claimingPriceRange == null) {
			if (other.claimingPriceRange != null)
				return false;
		} else if (!claimingPriceRange.equals(other.claimingPriceRange))
			return false;
		if (raceClassification == null) {
			if (other.raceClassification != null)
				return false;
		} else if (!raceClassification.equals(other.raceClassification))
			return false;
		if (raceTypeNameBlackTypeBreed == null) {
			if (other.raceTypeNameBlackTypeBreed != null)
				return false;
		} else if (!raceTypeNameBlackTypeBreed.equals(other.raceTypeNameBlackTypeBreed))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claimingPriceRange == null) ? 0 : claimingPriceRange.hashCode());
		result = prime * result + ((raceClassification == null) ? 0 : raceClassification.hashCode());
		result = prime * result + ((raceTypeNameBlackTypeBreed == null) ? 0 : raceTypeNameBlackTypeBreed.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RaceConditions [text=").append(text).append(", claimingPriceRange=").append(claimingPriceRange)
				.append(", raceTypeNameBlackTypeBreed=").append(raceTypeNameBlackTypeBreed)
				.append(", raceClassification=").append(raceClassification).append("]");
		return builder.toString();
	}

	public RaceConditions() {
		super();
		
		// TODO Auto-generated constructor stub
	}
    
    
}
