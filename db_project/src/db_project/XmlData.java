package db_project;

public class XmlData {

	private String restName;		// 화장실명
	private String restAd;		// 소재지도로명주소
	private int disM_b;					// 남성장애인용대변기수
	private int disM_u;					// 남성장애인용소변기수
	private int disF;					// 여성장애인용대변기수
	private String open;					// 개방시간
	private float lat;					// 위도
	private float longi;					// 경도
	
	public XmlData(String restName, String restAd,  int disM_b, int disM_u, int disF, String open, float lat, float longi) {
	this.restName = restName;
	this.restAd = restAd;
	this.disM_b = disM_b;
	this.disM_u = disM_u;
	this.disF = disF;
	this.open = open;
	this.lat = lat;
	this.longi = longi;
	}
	
	public String getRestName(){
		return restName;
	}
	public void setRestName(String restName){
		this.restName = restName;
	}
	public String getRestAd(){
		return restAd;
	}
	public void setRestAd(String restAd){
		this.restAd = restAd;
	}
	public int getDisM_b(){
		return disM_b;
	}
	public void setDisM_b(int disM_b){
		this.disM_b = disM_b;
	}
	public int getDisM_u(){
		return disM_u;
	}
	public void setDisM_u(int disM_u){
		this.disM_u = disM_u;
	}
	public int getDisF(){
		return disF;
	}
	public void setDisF(int disF){
		this.disF = disF;
	}
	public String getOpen(){
		return open;
	}
	public void setOpen(String open){
		this.open = open;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat=lat;
	}
	public float getLongi() {
		return longi;
	}
	public void setLongitude(float longi) {
		this.longi=longi;
	}
}
