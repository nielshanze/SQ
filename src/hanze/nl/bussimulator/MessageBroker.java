package hanze.nl.bussimulator;

import hanze.nl.bussimulator.Halte.Positie;

public class MessageBroker {
	
	
	private static MessageBroker instance;
    
    private MessageBroker(){}
    
    public static synchronized MessageBroker getInstance(){
        if(instance == null){
            instance = new MessageBroker();
        }
        return instance;
    }
	
	public void sendETAs(int nu, Bus _bus){
		int i=0;
		Bericht bericht = new Bericht(_bus.getLijn().name(),_bus.getBedrijf().name(),_bus.getBusID(),nu);
		if (_bus.isBijHalte()) {
			ETA eta = new ETA(_bus.getLijn().getHalte(_bus.getHalteNummer()).name(),_bus.getLijn().getRichting(_bus.getHalteNummer()),0);
			bericht.ETAs.add(eta);
		}
		Positie eerstVolgende=_bus.getLijn().getHalte(_bus.getHalteNummer()+_bus.getRichting()).getPositie();
		int tijdNaarHalte=_bus.getTotVolgendeHalte()+nu;
		for (i = _bus.getHalteNummer()+_bus.getRichting() ; !(i>=_bus.getLijn().getLengte()) && !(i < 0); i=i+_bus.getRichting() ){
			tijdNaarHalte+= _bus.getLijn().getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(_bus.getLijn().getHalte(i).name(), _bus.getLijn().getRichting(i),tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende=_bus.getLijn().getHalte(i).getPositie();
		}
		bericht.eindpunt=_bus.getLijn().getHalte(i-_bus.getRichting()).name();
		sendBericht(bericht);
	}
	
	public void sendLastETA(int nu, Bus _bus){
		Bericht bericht = new Bericht(_bus.getLijn().name(),_bus.getBedrijf().name(),_bus.getBusID(),nu);
		String eindpunt = _bus.getLijn().getHalte(_bus.getHalteNummer()).name();
		ETA eta = new ETA(eindpunt,_bus.getLijn().getRichting(_bus.getHalteNummer()),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		sendBericht(bericht);
	}

	public void sendBericht(Bericht bericht){
		//TODO verstuur een XML bericht naar de messagebroker.	
	}
}
