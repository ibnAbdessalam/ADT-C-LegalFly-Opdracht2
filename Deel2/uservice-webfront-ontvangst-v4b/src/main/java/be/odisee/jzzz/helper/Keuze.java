package be.odisee.jzzz.helper;

/**
 * Is een helper klasse
 * Kon niet als geneste klasse in de controller, 
 * want dan vindt Spring geen default constructor, hoewel die er wel is
 * 
 * @author hans
 *
 */
public class Keuze 
{
	private int selectie = 0;
	private String redengratis = "jaarlid";

	public Keuze() {}
	
	public int getSelectie() {
		return selectie;
	}

	public void setSelectie(int selectie) {
		this.selectie = selectie;
	}

	public String getRedengratis() {
		return redengratis;
	}

	public void setRedengratis(String redengratis) {
		this.redengratis = redengratis;
	}
}