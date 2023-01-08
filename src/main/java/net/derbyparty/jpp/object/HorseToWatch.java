package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import java.util.Collections;

public class HorseToWatch implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String flag;
	private String comment;
	
	private List<RaceNote> raceNotes;

	@Generated("SparkTools")
	private HorseToWatch(Builder builder) {
		this.name = builder.name;
		this.flag = builder.flag;
		this.comment = builder.comment;
		this.raceNotes = builder.raceNotes;
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
		return raceNotes;
	}

	public void setRaceNotes(List<RaceNote> raceNotes) {
		this.raceNotes = raceNotes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (raceNotes == null) {
			if (other.raceNotes != null)
				return false;
		} else if (!raceNotes.equals(other.raceNotes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HorseToWatch [name=").append(name).append(", comment=").append(comment).append(", raceNotes=")
				.append(raceNotes).append("]");
		return builder.toString();
	}

	public HorseToWatch(String name, String comment, List<RaceNote> raceNotes) {
		super();
		this.name = name;
		this.comment = comment;
		this.raceNotes = raceNotes;
	}
	
	public HorseToWatch() {
		
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

		public HorseToWatch build() {
			return new HorseToWatch(this);
		}
	}

}
