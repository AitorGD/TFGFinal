package com.example.demo;
import java.sql.Date;

import org.apache.jena.atlas.json.JsonValue;

public class incidencias {
	private double latitud;
	private double longitud;
	private String time = "";
	private String type = "GeneralObstruction";
	private String ocurrencia ="objectOnTheRoad";
	
	public double getLatitud() {
		return latitud;
	}
	public String getStringLatitud() {
		return String.valueOf(latitud);
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public String getStringLongitud() {
		return String.valueOf(longitud);
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time2) {
		this.time = time2;
	}
	public String getType() {
		return type;
	}
	public void setType(String weather) {
		this.type = weather;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}
	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}
}


