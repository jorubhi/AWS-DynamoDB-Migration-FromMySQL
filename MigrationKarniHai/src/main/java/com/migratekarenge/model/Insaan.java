package com.migratekarenge.model;

public class Insaan {

	private int idinsaan;
	private String insaanKaNaam;
	private int insaanKiUmar;
	private Boolean pareshanHai;
	public int getIdinsaan() {
		return idinsaan;
	}
	public void setIdinsaan(int idinsaan) {
		this.idinsaan = idinsaan;
	}
	public String getInsaanKaNaam() {
		return insaanKaNaam;
	}
	public void setInsaanKaNaam(String insaanKaNaam) {
		this.insaanKaNaam = insaanKaNaam;
	}
	public int getInsaanKiUmar() {
		return insaanKiUmar;
	}
	public void setInsaanKiUmar(int insaanKiUmar) {
		this.insaanKiUmar = insaanKiUmar;
	}
	public Boolean getPareshanHai() {
		return pareshanHai;
	}
	public void setPareshanHai(Boolean pareshanHai) {
		this.pareshanHai = pareshanHai;
	}
	@Override
	public String toString() {
		return "Insaan [idinsaan=" + idinsaan + ", insaanKaNaam=" + insaanKaNaam + ", insaanKiUmar=" + insaanKiUmar
				+ ", pareshanHai=" + pareshanHai + "]";
	}
	
}
