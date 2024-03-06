package net.derbyparty.jpp.object;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.Serializable;
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

public class Track implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	static CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	final static String mongoUri = "mongodb://localhost/jpp";
	static MongoClient mongoClient = MongoClients.create(mongoUri);
	static MongoDatabase database = mongoClient.getDatabase("jpp").withCodecRegistry(pojoCodecRegistry);

	private String Code;
	private String Name;
	private List<RaceDate> raceDates;


	@Generated("SparkTools")
	private Track(Builder builder) {
		this.Code = builder.Code;
		this.Name = builder.Name;
		this.raceDates = builder.raceDates;
	}
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<RaceDate> getRaceDates() {
		return raceDates;
	}
	public void setRaceDates(List<RaceDate> raceDates) {
		this.raceDates = raceDates;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Code == null) ? 0 : Code.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((raceDates == null) ? 0 : raceDates.hashCode());
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
		Track other = (Track) obj;
		if (Code == null) {
			if (other.Code != null)
				return false;
		} else if (!Code.equals(other.Code))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (raceDates == null) {
			if (other.raceDates != null)
				return false;
		} else if (!raceDates.equals(other.raceDates))
			return false;
		return true;
	}
	public Track(String code, String name, List<RaceDate> raceDates) {
		super();
		Code = code;
		Name = name;
		this.raceDates = raceDates;
	}
	
	public Track() {
		
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String Code;
		private String Name;
		private List<RaceDate> raceDates = Collections.emptyList();

		private Builder() {
		}

		public Builder withCode(String Code) {
			this.Code = Code;
			return this;
		}

		public Builder withName(String Name) {
			this.Name = Name;
			return this;
		}

		public Builder withRaceDates(List<RaceDate> raceDates) {
			this.raceDates = raceDates;
			return this;
		}

		public Track build() {
			return new Track(this);
		}
	}

	public void save() {
		
		ReplaceOptions opts = new ReplaceOptions().upsert(true);
		
		Document query = new Document()
				.append("code", Code);	
		
		MongoCollection<Track> collection = database.getCollection("tracks", Track.class);
		UpdateResult result = collection.replaceOne(query, this, opts);
		if (result.getModifiedCount() == 1) {
			System.out.println(this.getCode() + " updated.");
		} else {
			System.out.println(this.getCode() + " inserted. (ID = " + result.getUpsertedId());

		}
	}
	
	public void delete() {
		
		Document query = new Document()
				.append("code", Code);	
		
		MongoCollection<Horse> collection = database.getCollection("tracks", Horse.class);
		collection.findOneAndDelete(query);
	}
	
}
