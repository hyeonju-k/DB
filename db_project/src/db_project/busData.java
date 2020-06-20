package db_project;

public class busData {

	private int stationId;
	private String stationName;
	private float x;
	private float y;
	
	public busData(int stationId, String stationName, float x, float y) {
		this.stationId = stationId;
		this.stationName = stationName;
		this.x = x;
		this.y = y;
	}
	
	
	public int getStationId() {
		return stationId;
	}
	
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	
	public String getStationName(){
		return stationName;
	}
	public void setStationName(String stationName){
		this.stationName = stationName;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	
	
}
