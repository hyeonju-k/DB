package db_project;

public class busData {
	private int stationId; // ���������� id
	private String stationName; // ���������� �̸�
	private float lat; // ����
	private float longi; // �浵
	
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
