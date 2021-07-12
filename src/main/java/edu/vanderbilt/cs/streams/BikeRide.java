package edu.vanderbilt.cs.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BikeRide {

	public static class DataFrame {
		public final double velocity;
		public final double heartRate;
		public final double grade;
		public final double altitude;
		public final LatLng coordinate;

		public DataFrame(LatLng coordinate, double grade, double altitude, double velocity, double heartRate) {
			super();
			this.velocity = velocity;
			this.heartRate = heartRate;
			this.grade = grade;
			this.altitude = altitude;
			this.coordinate = coordinate;
		}

		public double getVelocity() {
			return velocity;
		}

		public double getHeartRate() {
			return heartRate;
		}

		public double getGrade() {
			return grade;
		}

		public double getAltitude() {
			return altitude;
		}

		public LatLng getCoordinate() {
			return coordinate;
		}
	}

	public static class LatLng {
		public final double latitude;
		public final double longitude;

		@JsonCreator
		public LatLng(double[] latlng) {
			this.latitude = latlng[0];
			this.longitude = latlng[1];
		}

		public boolean equals(Object o) {
			return (o instanceof LatLng) && ((LatLng) o).latitude == this.latitude
					&& ((LatLng) o).longitude == this.longitude;
		}
	}

	public static class LatLngStream {
		public final LatLng[] data;

		@JsonCreator
		public LatLngStream(@JsonProperty("data") LatLng[] data) {
			this.data = data;
		}

		@JsonAnySetter
		public void setOther(String key, Object v) {
		}
	}

	public static class DataStream {

		public final double[] data;

		@JsonCreator
		public DataStream(@JsonProperty("data") double[] data) {
			this.data = data;
		}

		@JsonAnySetter
		public void setOther(String key, Object v) {
		}

	}

	public final double[] heartRate;
	public final double[] velocity;
	public final double[] grade;
	public final double[] altitude;
	public final LatLng[] coordinates;

	@JsonCreator
	public BikeRide(@JsonProperty("heartrate") DataStream heartRate,
			@JsonProperty("velocity_smooth") DataStream velocity, @JsonProperty("grade_smooth") DataStream grade,
			@JsonProperty("altitude") DataStream altitude, @JsonProperty("latlng") LatLngStream coordinates) {

		super();
		this.heartRate = heartRate.data;
		this.velocity = velocity.data;
		this.grade = grade.data;
		this.altitude = altitude.data;
		this.coordinates = coordinates.data;
	}

	/**
	 * 
	 * @return
	 */
	public DoubleStream heartRateStream() {
		return DoubleStream.of(this.heartRate);
	}

	/**
	 * 
	 * @return
	 */
	public DoubleStream velocityStream() {
		return DoubleStream.of(this.velocity);
	}

	/**
	 * 
	 * @return
	 */
	public DoubleStream gradeStream() {
		return DoubleStream.of(this.grade);
	}

	/**
	 * 
	 * @return
	 */
	public DoubleStream altitudeStream() {
		return DoubleStream.of(this.altitude);
	}

	/**
	 * 
	 * @return
	 */
	public Stream<LatLng> coordinateStream() {
		return Stream.of(this.coordinates);
	}

	/**
	 * 
	 * @return
	 */
	public Stream<DataFrame> fusedFramesStream() {

		List<DataFrame> list = new ArrayList<DataFrame>();
		for (int i = 0; i < coordinates.length; i++) {
			list.add(new DataFrame(coordinates[i], grade[i], altitude[i], velocity[i], heartRate[i]));
		}
		return list.stream();
	}

	// Don't change me!
	//
	// There is nothing to see here, move along.
	@JsonAnySetter
	public void setOther(String key, Object v) {

	}

}