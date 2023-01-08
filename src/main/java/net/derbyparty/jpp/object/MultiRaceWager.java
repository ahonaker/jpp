package net.derbyparty.jpp.object;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

import java.util.ArrayList;
import java.util.Collections;

public class MultiRaceWager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String Name;
	private int index;
	private float min;
	private int numRaces;
	private int firstRace;
	private String tmA;
	private float tmACost;
	private String tmAB;
	private float tmABCost;
	private List<String> tmB1;
	private float tmB1Cost;
	private List<String> tmB2;
	private float tmB2Cost;
	private List<String> tmC1;
	private float tmC1Cost;
	
	private Boolean tmAchecked = false;
	private Boolean tmABchecked = false;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + firstRace;
		result = prime * result + index;
		result = prime * result + Float.floatToIntBits(min);
		result = prime * result + numRaces;
		result = prime * result + ((tmA == null) ? 0 : tmA.hashCode());
		result = prime * result + ((tmAB == null) ? 0 : tmAB.hashCode());
		result = prime * result + Float.floatToIntBits(tmABCost);
		result = prime * result + Float.floatToIntBits(tmACost);
		result = prime * result + ((tmB1 == null) ? 0 : tmB1.hashCode());
		result = prime * result + Float.floatToIntBits(tmB1Cost);
		result = prime * result + ((tmB2 == null) ? 0 : tmB2.hashCode());
		result = prime * result + Float.floatToIntBits(tmB2Cost);
		result = prime * result + ((tmC1 == null) ? 0 : tmC1.hashCode());
		result = prime * result + Float.floatToIntBits(tmC1Cost);
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
		MultiRaceWager other = (MultiRaceWager) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (firstRace != other.firstRace)
			return false;
		if (index != other.index)
			return false;
		if (Float.floatToIntBits(min) != Float.floatToIntBits(other.min))
			return false;
		if (numRaces != other.numRaces)
			return false;
		if (tmA == null) {
			if (other.tmA != null)
				return false;
		} else if (!tmA.equals(other.tmA))
			return false;
		if (tmAB == null) {
			if (other.tmAB != null)
				return false;
		} else if (!tmAB.equals(other.tmAB))
			return false;
		if (Float.floatToIntBits(tmABCost) != Float.floatToIntBits(other.tmABCost))
			return false;
		if (Float.floatToIntBits(tmACost) != Float.floatToIntBits(other.tmACost))
			return false;
		if (tmB1 == null) {
			if (other.tmB1 != null)
				return false;
		} else if (!tmB1.equals(other.tmB1))
			return false;
		if (Float.floatToIntBits(tmB1Cost) != Float.floatToIntBits(other.tmB1Cost))
			return false;
		if (tmB2 == null) {
			if (other.tmB2 != null)
				return false;
		} else if (!tmB2.equals(other.tmB2))
			return false;
		if (Float.floatToIntBits(tmB2Cost) != Float.floatToIntBits(other.tmB2Cost))
			return false;
		if (tmC1 == null) {
			if (other.tmC1 != null)
				return false;
		} else if (!tmC1.equals(other.tmC1))
			return false;
		if (Float.floatToIntBits(tmC1Cost) != Float.floatToIntBits(other.tmC1Cost))
			return false;
		return true;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public int getNumRaces() {
		return numRaces;
	}
	public void setNumRaces(int numRaces) {
		this.numRaces = numRaces;
	}
	public int getFirstRace() {
		return firstRace;
	}
	public void setFirstRace(int firstRace) {
		this.firstRace = firstRace;
	}
	public String getTmA() {
		return tmA;
	}
	public void setTmA(String tmA) {
		this.tmA = tmA;
	}
	public float getTmACost() {
		return tmACost;
	}
	public void setTmACost(float tmACost) {
		this.tmACost = tmACost;
	}
	public String getTmAB() {
		return tmAB;
	}
	public void setTmAB(String tmAB) {
		this.tmAB = tmAB;
	}
	public float getTmABCost() {
		return tmABCost;
	}
	public void setTmABCost(float tmABCost) {
		this.tmABCost = tmABCost;
	}
	public List<String> getTmB1() {
		return tmB1;
	}
	public void setTmB1(List<String> tmB1) {
		this.tmB1 = tmB1;
	}
	public float getTmB1Cost() {
		return tmB1Cost;
	}
	public void setTmB1Cost(float tmB1Cost) {
		this.tmB1Cost = tmB1Cost;
	}
	public List<String> getTmB2() {
		return tmB2;
	}
	public void setTmB2(List<String> tmB2) {
		this.tmB2 = tmB2;
	}
	public float getTmB2Cost() {
		return tmB2Cost;
	}
	public void setTmB2Cost(float tmB2Cost) {
		this.tmB2Cost = tmB2Cost;
	}
	public List<String> getTmC1() {
		return tmC1;
	}
	public void setTmC1(List<String> tmC1) {
		this.tmC1 = tmC1;
	}
	public float getTmC1Cost() {
		return tmC1Cost;
	}
	public void setTmC1Cost(float tmC1Cost) {
		this.tmC1Cost = tmC1Cost;
	}

	public Boolean getTmAchecked() {
		return tmAchecked;
	}
	public void setTmAchecked(Boolean tmAchecked) {
		this.tmAchecked = tmAchecked;
	}
	public Boolean getTmABchecked() {
		return tmABchecked;
	}
	public void setTmABchecked(Boolean tmABchecked) {
		this.tmABchecked = tmABchecked;
	}
	public List<Boolean> getTmB1checked() {
		List<Boolean> arr = new ArrayList<Boolean>();
		for (int i =0; i < tmB1.size(); i++) {
			arr.add(false);
		}
		return arr;
	}
	public void setTmB1checked(List<Boolean> tmB1checked) {
	}
	public List<Boolean> getTmB2checked() {
		List<Boolean> arr = new ArrayList<Boolean>();
		for (int i =0; i < tmB2.size(); i++) {
			arr.add(false);
		}
		return arr;
	}
	public void setTmB2checked(List<Boolean> tmB2checked) {
	}
	public List<Boolean> getTmC1checked() {
		List<Boolean> arr = new ArrayList<Boolean>();
		for (int i =0; i < tmC1.size(); i++) {
			arr.add(false);
		}
		return arr;
	}
	public void setTmC1checked(List<Boolean> tmC1checked) {
	}
	
	@Generated("SparkTools")
	private MultiRaceWager(Builder builder) {
		this.Name = builder.Name;
		this.index = builder.index;
		this.min = builder.min;
		this.numRaces = builder.numRaces;
		this.firstRace = builder.firstRace;
		this.tmA = builder.tmA;
		this.tmACost = builder.tmACost;
		this.tmAB = builder.tmAB;
		this.tmABCost = builder.tmABCost;
		this.tmB1 = builder.tmB1;
		this.tmB1Cost = builder.tmB1Cost;
		this.tmB2 = builder.tmB2;
		this.tmB2Cost = builder.tmB2Cost;
		this.tmC1 = builder.tmC1;
		this.tmC1Cost = builder.tmC1Cost;
	}
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public static final class Builder {
		private String Name;
		private int index;
		private float min;
		private int numRaces;
		private int firstRace;
		private String tmA;
		private float tmACost;
		private String tmAB;
		private float tmABCost;
		private List<String> tmB1 = Collections.emptyList();
		private float tmB1Cost;
		private List<String> tmB2 = Collections.emptyList();
		private float tmB2Cost;
		private List<String> tmC1 = Collections.emptyList();
		private float tmC1Cost;

		private Builder() {
		}

		public Builder withName(String Name) {
			this.Name = Name;
			return this;
		}

		public Builder withIndex(int index) {
			this.index = index;
			return this;
		}

		public Builder withMin(float min) {
			this.min = min;
			return this;
		}

		public Builder withNumRaces(int numRaces) {
			this.numRaces = numRaces;
			return this;
		}

		public Builder withFirstRace(int firstRace) {
			this.firstRace = firstRace;
			return this;
		}

		public Builder withTmA(String tmA) {
			this.tmA = tmA;
			return this;
		}

		public Builder withTmACost(float tmACost) {
			this.tmACost = tmACost;
			return this;
		}

		public Builder withTmAB(String tmAB) {
			this.tmAB = tmAB;
			return this;
		}

		public Builder withTmABCost(float tmABCost) {
			this.tmABCost = tmABCost;
			return this;
		}

		public Builder withTmB1(List<String> tmB1) {
			this.tmB1 = tmB1;
			return this;
		}

		public Builder withTmB1Cost(float tmB1Cost) {
			this.tmB1Cost = tmB1Cost;
			return this;
		}

		public Builder withTmB2(List<String> tmB2) {
			this.tmB2 = tmB2;
			return this;
		}

		public Builder withTmB2Cost(float tmB2Cost) {
			this.tmB2Cost = tmB2Cost;
			return this;
		}

		public Builder withTmC1(List<String> tmC1) {
			this.tmC1 = tmC1;
			return this;
		}

		public Builder withTmC1Cost(float tmC1Cost) {
			this.tmC1Cost = tmC1Cost;
			return this;
		}

		public MultiRaceWager build() {
			return new MultiRaceWager(this);
		}
	}
	public MultiRaceWager() {
		super();
	}





}
