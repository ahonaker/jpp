package net.derbyparty.jpp.object;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class HorseToWatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final static String dir = "/Users/ahonaker/Google Drive/pp/jpp/horsesToWatch/";
	
	private String name;
	private String flag;
	private String comment;
	
	private List<RaceNote> raceNotes;
	
	private List<PastPerformance> pastPerformances;

	@Generated("SparkTools")
	private HorseToWatch(Builder builder) {
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
		HorseToWatch other = (HorseToWatch) obj;
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
		builder2.append("HorseToWatch [name=").append(name).append(", flag=").append(flag).append(", comment=")
				.append(comment).append(", raceNotes=").append(raceNotes).append(", pastPerformances=")
				.append(pastPerformances).append("]");
		return builder2.toString();
	}
	
	public HorseToWatch(String name, String flag, String comment, List<RaceNote> raceNotes,
			List<PastPerformance> pastPerformances) {
		super();
		this.name = name;
		this.flag = flag;
		this.comment = comment;
		this.raceNotes = raceNotes;
		this.pastPerformances = pastPerformances;
	}

	public HorseToWatch() {
		
	}

	public void save() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
		File file = new File(dir + name.replaceAll("\\s\\(.+\\)", "") + ".json");
		if (!file.exists()) {
			file.createNewFile();
		}
		mapper.writeValue(file, this);

	}
		
	public void delete() throws Exception {
		
		File file = new File(dir + name.replaceAll("\\s\\(.+\\)", "") + ".json");
		if (file.exists()) {
			file.delete();
		}
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

		public HorseToWatch build() {
			return new HorseToWatch(this);
		}
	}
}
