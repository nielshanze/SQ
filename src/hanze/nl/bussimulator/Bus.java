package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.Halte.Positie;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	public void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	public void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	
	public void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
		naarVolgendeHalte();
	}
	
	public boolean isHalteBereikt(){
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			return true;
		}
		return false;
	}
	
	public void bereikHalte(boolean eindpuntBereikt){
		halteNummer+=richting;
		bijHalte=true;
		if (eindpuntBereikt) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));			
		}
	}
	
	public boolean move(){
		boolean eindpuntBereikt = false;
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			totVolgendeHalte--;
			if (totVolgendeHalte==0){
				eindpuntBereikt=isHalteBereikt();
				bereikHalte(eindpuntBereikt);
			}
		}
		return eindpuntBereikt;
	}
	
	public Lijnen getLijn(){
		return lijn;
	}
	
	public Bedrijven getBedrijf(){
		return bedrijf;
	}
	
	public String getBusID(){
		return busID;
	}
	
	public boolean isBijHalte(){
		return bijHalte;
	}
	
	public int getRichting(){
		return richting;
	}
	
	public int getHalteNummer(){
		return halteNummer;
	}
	
	public int getTotVolgendeHalte(){
		return totVolgendeHalte;
	}

}
