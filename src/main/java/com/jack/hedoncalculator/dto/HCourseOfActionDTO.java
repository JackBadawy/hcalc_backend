package com.jack.hedoncalculator.dto;

public class HCourseOfActionDTO {
    private Long id;
    private String description;
    private double intensity;
    private double duration;
    private double certainty;
    private double propinquity;
    private double fecundity;
    private double purity;
    private double extent;
    private boolean isPublic;
    private Double hedonicValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getIntensity() {
		return intensity;
	}
	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public double getCertainty() {
		return certainty;
	}
	public void setCertainty(double certainty) {
		this.certainty = certainty;
	}
	public double getPropinquity() {
		return propinquity;
	}
	public void setPropinquity(double propinquity) {
		this.propinquity = propinquity;
	}
	public double getFecundity() {
		return fecundity;
	}
	public void setFecundity(double fecundity) {
		this.fecundity = fecundity;
	}
	public double getPurity() {
		return purity;
	}
	public void setPurity(double purity) {
		this.purity = purity;
	}
	public double getExtent() {
		return extent;
	}
	public void setExtent(double extent) {
		this.extent = extent;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Double getHedonicValue() {
		return hedonicValue;
	}
	public void setHedonicValue(Double hedonicValue) {
		this.hedonicValue = hedonicValue;
	}

  
}
