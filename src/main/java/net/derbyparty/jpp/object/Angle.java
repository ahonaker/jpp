package net.derbyparty.jpp.object;

import java.io.Serializable;
import javax.annotation.Generated;

public class Angle implements Serializable {

	private static final long serialVersionUID = 1L;

	private String source;
	private String name;
	private String method;
	private String type;
	private String text;
	private String description;

	@Generated("SparkTools")
	private Angle(Builder builder) {
		this.source = builder.source;
		this.name = builder.name;
		this.method = builder.method;
		this.type = builder.type;
		this.text = builder.text;
		this.description = builder.description;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Angle other = (Angle) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Angle [source=").append(source).append(", name=").append(name).append(", method=")
				.append(method).append(", type=").append(type).append(", text=").append(text).append(", description=")
				.append(description).append("]");
		return builder2.toString();
	}
	
	public Angle(String source, String name, String method, String type, String text, String description) {
		super();
		this.source = source;
		this.name = name;
		this.method = method;
		this.type = type;
		this.text = text;
		this.description = description;
	}

	public Angle() {
		
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private String source;
		private String name;
		private String method;
		private String type;
		private String text;
		private String description;

		private Builder() {
		}

		public Builder withSource(String source) {
			this.source = source;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withMethod(String method) {
			this.method = method;
			return this;
		}

		public Builder withType(String type) {
			this.type = type;
			return this;
		}

		public Builder withText(String text) {
			this.text = text;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Angle build() {
			return new Angle(this);
		}
	}
	
}
