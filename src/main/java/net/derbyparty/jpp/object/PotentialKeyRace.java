package net.derbyparty.jpp.object;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;

import java.util.Collections;

public class PotentialKeyRace implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);
	
	private Date raceDate;
	private String track;
	private int raceNumber;
	
	private List<PotentialKeyRaceHorse> horses;

	@Generated("SparkTools")
	private PotentialKeyRace(Builder builder) {
		this.raceDate = builder.raceDate;
		this.track = builder.track;
		this.raceNumber = builder.raceNumber;
		this.horses = builder.horses;
	}

	public Date getRaceDate() {
		return raceDate;
	}

	public void setRaceDate(Date raceDate) {
		this.raceDate = raceDate;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public int getRaceNumber() {
		return raceNumber;
	}

	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}

	public List<PotentialKeyRaceHorse> getHorses() {
		return horses;
	}

	public void setHorses(List<PotentialKeyRaceHorse> horses) {
		this.horses = horses;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raceDate == null) ? 0 : raceDate.hashCode());
		result = prime * result + raceNumber;
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PotentialKeyRace other = (PotentialKeyRace) obj;
		if (raceDate == null) {
			if (other.raceDate != null)
				return false;
		} else if (!raceDate.equals(other.raceDate))
			return false;
		if (raceNumber != other.raceNumber)
			return false;
		if (track == null) {
			if (other.track != null)
				return false;
		} else if (!track.equals(other.track))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PotentialKeyRace [raceDate=").append(raceDate).append(", track=").append(track)
				.append(", raceNumber=").append(raceNumber).append(", horses=").append(horses).append("]");
		return builder.toString();
	}

	public PotentialKeyRace(Date raceDate, String track, int raceNumber, List<PotentialKeyRaceHorse> horses) {
		super();
		this.raceDate = raceDate;
		this.track = track;
		this.raceNumber = raceNumber;
		this.horses = horses;
	}
	

	public PotentialKeyRace() {

	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private Date raceDate;
		private String track;
		private int raceNumber;
		private List<PotentialKeyRaceHorse> horses = Collections.emptyList();

		private Builder() {
		}

		public Builder withRaceDate(Date raceDate) {
			this.raceDate = raceDate;
			return this;
		}

		public Builder withTrack(String track) {
			this.track = track;
			return this;
		}

		public Builder withRaceNumber(int raceNumber) {
			this.raceNumber = raceNumber;
			return this;
		}

		public Builder withHorses(List<PotentialKeyRaceHorse> horses) {
			this.horses = horses;
			return this;
		}

		public PotentialKeyRace build() {
			return new PotentialKeyRace(this);
		}
	}
	
	public void save() throws Exception {
		
		ReplaceOptions opts = new ReplaceOptions().upsert(true);
		
		Document query = new Document()
				.append("track", track)
				.append("raceDate", raceDate)
				.append("raceNumber", raceNumber);			
		
		MongoCollection<PotentialKeyRace> collection = database.getCollection("keyRaces", PotentialKeyRace.class);
		UpdateResult result = collection.replaceOne(query, this, opts);
		if (result.getModifiedCount() == 1) {
//			System.out.println("potentialKeyRace " 
//					+ this.getTrack() + ":" 
//					+ this.getRaceDate() 
//					+ " - Race " + 
//					this.getRaceNumber() 
//					+ " updated.");
		} else {
//			System.out.println("potentialKeyRace " 
//					+ this.getTrack() + ":" 
//					+ this.getRaceDate() 
//					+ " - Race " 
//					+ this.getRaceNumber() 
//					+ " inserted. (ID = " + result.getUpsertedId());
		}

	}
		
	public void delete() throws Exception {
		
		Document query = new Document()
				.append("track", track)
				.append("raceDate", raceDate)
				.append("raceNumber", raceNumber);
		
		MongoCollection<PotentialKeyRace> collection = database.getCollection("keyRaces", PotentialKeyRace.class);
		collection.findOneAndDelete(query);
	}


}
