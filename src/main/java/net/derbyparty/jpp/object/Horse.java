package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;

import java.util.Collections;

public class Horse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);
	
	private String name;
	private String flag;
	private String comment;
	
	private List<RaceNote> raceNotes;
	
	private List<PastPerformance> pastPerformances;

	@Generated("SparkTools")
	private Horse(Builder builder) {
		this.name = builder.name;
		this.flag = builder.flag;
		this.comment = builder.comment;
		this.raceNotes = builder.raceNotes;
		this.pastPerformances = builder.pastPerformances;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<RaceNote> getRaceNotes() {
		Collections.sort(raceNotes, Collections.reverseOrder());
		return raceNotes;
	}

	public void setRaceNotes(List<RaceNote> raceNotes) {
		this.raceNotes = raceNotes;
	}

	public List<PastPerformance> getPastPerformances() {
		Collections.sort(pastPerformances, Collections.reverseOrder());
		return pastPerformances;
	}

	public void setPastPerformances(List<PastPerformance> pastPerformances) {
		this.pastPerformances = pastPerformances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pastPerformances == null) ? 0 : pastPerformances.hashCode());
		result = prime * result + ((raceNotes == null) ? 0 : raceNotes.hashCode());
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
		Horse other = (Horse) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pastPerformances == null) {
			if (other.pastPerformances != null)
				return false;
		} else if (!pastPerformances.equals(other.pastPerformances))
			return false;
		if (raceNotes == null) {
			if (other.raceNotes != null)
				return false;
		} else if (!raceNotes.equals(other.raceNotes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Horse [name=").append(name).append(", flag=").append(flag).append(", comment=")
				.append(comment).append(", raceNotes=").append(raceNotes).append(", pastPerformances=")
				.append(pastPerformances).append("]");
		return builder2.toString();
	}
	
	public Horse(String name, String flag, String comment, List<RaceNote> raceNotes,
			List<PastPerformance> pastPerformances) {
		super();
		this.name = name;
		this.flag = flag;
		this.comment = comment;
		this.raceNotes = raceNotes;
		this.pastPerformances = pastPerformances;
	}

	public Horse() {
		
	}

	public void save() throws Exception {
		ReplaceOptions opts = new ReplaceOptions().upsert(true);
		
		Document query = new Document()
				.append("name", name);	
		
		MongoCollection<Horse> collection = database.getCollection("horses", Horse.class);
		UpdateResult result = collection.replaceOne(query, this, opts);
//		if (result.getModifiedCount() == 1) {
//			System.out.println(this.getName() + " updated.");
//		} else {
//			System.out.println(this.getName() + " inserted. (ID = " + result.getUpsertedId());
//		}

	}
		
	public void delete() throws Exception {		
		Document query = new Document()
				.append("name", name);	
		
		MongoCollection<Horse> collection = database.getCollection("horses", Horse.class);
		collection.findOneAndDelete(query);
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private String flag;
		private String comment;
		private List<RaceNote> raceNotes = Collections.emptyList();
		private List<PastPerformance> pastPerformances = Collections.emptyList();

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withFlag(String flag) {
			this.flag = flag;
			return this;
		}

		public Builder withComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder withRaceNotes(List<RaceNote> raceNotes) {
			this.raceNotes = raceNotes;
			return this;
		}

		public Builder withPastPerformances(List<PastPerformance> pastPerformances) {
			this.pastPerformances = pastPerformances;
			return this;
		}

		public Horse build() {
			return new Horse(this);
		}
	}
}
