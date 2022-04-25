package model;

public class Asiakas {
	private String asiakasnro, etunimi, sukunimi, puhelin, sposti;

	public Asiakas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Asiakas(String asiakasnro,String etunimi, String sukunimi, String puhelin, String sposti) {
		super();
		this.asiakasnro = asiakasnro;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.puhelin = puhelin;
		this.sposti = sposti;
	}

	public String getAsiakasnro() {
		return asiakasnro;
	}

	public void setAsiakasnro(String asiakasnro) {
		this.asiakasnro = asiakasnro;
	}
	
	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getPuhelin() {
		return puhelin;
	}

	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}

	public String getSposti() {
		return sposti;
	}

	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	@Override
	public String toString() {
		return "Asiakas [etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", puhelin=" + puhelin + ", sposti=" + sposti
				+ "]";
	}
	
	
}
