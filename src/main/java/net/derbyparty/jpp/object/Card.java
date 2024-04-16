package net.derbyparty.jpp.object;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import javax.annotation.Generated;
import java.util.Collections;

public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().
		automatic(true).conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION)).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);

	
	private String track;
	private Date date;
	private List<Race> races;
	@Generated("SparkTools")
	private Card(Builder builder) {
		this.track = builder.track;
		this.date = builder.date;
		this.races = builder.races;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Race> getRaces() {
		return races;
	}
	public void setRaces(List<Race> races) {
		this.races = races;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((races == null) ? 0 : races.hashCode());
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
		Card other = (Card) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (races == null) {
			if (other.races != null)
				return false;
		} else if (!races.equals(other.races))
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
		builder.append("Card [track=").append(track).append(", date=").append(date).append(", races=").append(races)
				.append("]");
		return builder.toString();
	}
	public Card(String track, Date date, List<Race> races) {
		super();
		this.track = track;
		this.date = date;
		this.races = races;
	}
	
	public Card() {
		
	}

	public void save() throws Exception {
		ReplaceOptions opts = new ReplaceOptions().upsert(true);
		
		Document query = new Document()
				.append("track", track)
				.append("date", date);	
		
		MongoCollection<Card> collection = database.getCollection("cards", Card.class);
		UpdateResult result = collection.replaceOne(query, this, opts);
		if (result.getModifiedCount() == 1) {
			System.out.println(track + " - " + date + " updated.");
		} else {
			System.out.println(track + " - " + date + " saved. (ID = " + result.getUpsertedId());
		}
		
		for (Race race : this.getRaces()) {
			for (Entry entry : race.getEntries()) {
				Document horseQuery = new Document()
						.append("name", entry.getName());
				MongoCollection<Horse> horseCollection = database.getCollection("horses", Horse.class);
				Horse found = horseCollection.find(horseQuery).first();
				if (found == null) {
					Horse horse = Horse.builder()
						.withName(entry.getName())
						.build();
					horse.save();
				}
				
				for (PastPerformance pp : entry.getUnignoredPastPerformances()) {
					pp.save();
				}
			}
		}

	}
	
	public void delete() {
		Document query = new Document()
				.append("track", track)
				.append("date", date);	
		
		MongoCollection<Card> collection = database.getCollection("cards", Card.class);
		collection.findOneAndDelete(query);

	}
	
	public static Card get(String track, Date date) {
		Document query = new Document()
				.append("track", track)
				.append("date", date);	
		
		MongoCollection<Card> collection = database.getCollection("cards", Card.class);
		Card card = collection.find(query).first();
		for (Race race : card.getRaces()) {
			for (Entry entry : race.getEntries()) {
				List<PastPerformance> pps = new ArrayList<PastPerformance>();
				Document ppQuery = new Document()
						.append("name", entry.getName());
				MongoCollection<PastPerformance> ppCollection = database.getCollection("pastPerformances", PastPerformance.class);
				FindIterable<PastPerformance> iterable = ppCollection.find(ppQuery);
				iterable.into(pps);
				Collections.sort(pps);
				Collections.reverse(pps);
				entry.setPastPerformances(pps);
				entry.addHorse();
			}
		}
		return card;
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String track;
		private Date date;
		private List<Race> races = Collections.emptyList();

		private Builder() {
		}

		public Builder withTrack(String track) {
			this.track = track;
			return this;
		}

		public Builder withDate(Date date) {
			this.date = date;
			return this;
		}

		public Builder withRaces(List<Race> races) {
			this.races = races;
			return this;
		}

		public Card build() {
			return new Card(this);
		}
	}
	
}
