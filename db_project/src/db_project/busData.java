package db_project;

public class busData {
	private int stationId; // 버스정류소 id
	private String stationName; // 버스정류소 이름
	private float lat; // 위도
	private float longi; // 경도
	
	public busData(int stationId, String stationName, float lat, float longi) {
		this.setStationId(stationId);
		this.setStationName(stationName);
		this.setLat(lat);
		this.setLongi(longi);
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLongi() {
		return longi;
	}

	public void setLongi(float longi) {
		this.longi = longi;
	}
}
