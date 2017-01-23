/*

Eksamen_h2009.java  GS 2009-11-16

*/

import static javax.swing.JOptionPane.*;
// b
class Spor{
	String navn;
	String artist;
	double lengde;

	public Spor(String navn, String artist, double lengde){
		this.navn = navn;
		this.artist = artist;
		this.lengde = lengde;
	}

	public String getNavn(){return navn; }
	public void setNavn(String nyVerdi){ navn = nyVerdi;}

	public String getArtist(){return artist;}
	public void setArtist(String nyVerdi){ artist = nyVerdi;}

	public double getLengde(){ return lengde;}
	public void setLengde(double nyVerdi){ lengde = nyVerdi;}

	public String toString(){
		return navn + " " + artist + " " + lengde;
	}

// c
	public boolean equals(Object obj){
		if (obj instanceof Spor){
			Spor t = (Spor) obj;
			if (t == this) return true;
			else{
				if(this.navn.equals(t.getNavn()) && this.artist.equals(t.getArtist())){
					return true;
				}
			}
		}
		return false;
	}
}

// d
class Album{
	private String navn;
	private Spor[] spilleliste;
	private int antSpor;
	private final int MAKS_LENGDE = 60;

	public Album (String navn, Spor[] spilleliste){
		this.navn = navn;
		this.spilleliste = new Spor[spilleliste.length];
		for(int i=0; i<spilleliste.length; i++){
			this.spilleliste[i] = spilleliste[i];
		}
		antSpor = spilleliste.length;
	}

	public Album (String navn, int maksAntSpor){
		this.navn = navn;
		spilleliste = new Spor[maksAntSpor];
		antSpor = 0;
	}

	// e
	public double getSpilleTid(){
		double totTid = 0;
		for(int i=0; i<antSpor; i++){
			totTid += spilleliste[i].getLengde();
		}
		return totTid;
	}

	// f
	public boolean regNyttSpor (Spor nyttSpor){
		if (antSpor >= spilleliste.length){
			return false;
		}else{
			if(sjekkSpor(nyttSpor)){ // spor allerede registrert
				return false;
			} else if (getSpilleTid() + nyttSpor.getLengde() > MAKS_LENGDE){
				return false;
			}else{
				spilleliste[antSpor] = new Spor(nyttSpor.getNavn(), nyttSpor.getArtist(), nyttSpor.getLengde());
				antSpor++;
				return true;
			}
		}
	}

	private boolean sjekkSpor(Spor t){
		for(int i=0; i<antSpor; i++){
			if (spilleliste[i].equals(t)) return true;
		}
		return false;
	}

	// g
	public Spor[] finnSporArtist(String artist){
		Spor[] kopi = new Spor[antSpor];
		int kopiCount = 0;
		for(int i=0; i<antSpor; i++){
			if(spilleliste[i].getArtist().equals(artist)){
				kopi[kopiCount] = new Spor(spilleliste[i].getNavn(), spilleliste[i].getArtist(), spilleliste[i].getLengde());
				kopiCount++;
			}
		}

		if( kopiCount == 0){
			return null;
		}
		else if (kopiCount < antSpor){
			Spor[] tmp = new Spor[kopiCount];
			for(int i=0; i<kopiCount; i++){
				tmp[i] = kopi[i];
			}
			return tmp;
		}else{
			return kopi;
		}
	}

	public String toString(){
		String res = navn + "\n";
		for(int i=0; i<antSpor; i++){
			res += spilleliste[i] + "\n";
		}
		return res;
	}
}

// i
class Eksamen_h2009_losningsforslag{
	public static void main (String[] args){
		final int REG_ALBUM = 0;
		final int REG_SPOR = 1;
		final int INFO = 2;
		final int SOK_ARTIST = 3;
		final int AVSLUTT = 4;

		String[] muligheter = {"Registrer Album", "Registrer nytt spor", "Info om Album", "Søk etter sanger av artist", "Avslutt"};

		//Album album = null;
		Spor[] liste = {new Spor("Beat It", "Michael Jackson", 5.33), new Spor("Billie Jean", "Michael Jackson", 6.11),new Spor("Thriller", "Michael Jackson", 8.33),new Spor("This is it", "Michael Jackson", 4.23)};
		Album album = new Album("Thriller", liste);

		int valg = showOptionDialog(null, "Velg operasjon", "HiST Musikk Katalogisering", 0, 0, null, muligheter, muligheter[0]);

		while (valg != AVSLUTT){
			if (valg == REG_ALBUM){
				String navn = showInputDialog("Navn på Album: " );
				int maks = Integer.parseInt(showInputDialog("Maks antall spor; "));
				if (album == null) album = new Album(navn, maks);
				else {
					if (showConfirmDialog(null, "Album allerede reg. sikker på at du vil opprette nytt=" ) == YES_OPTION){
						album = new Album(navn, maks);
					}
				}
			}else if(valg == REG_SPOR){
				String tittel = showInputDialog("Tittel: " );
				String artist = showInputDialog("Artist: " );
				double lengde = Double.parseDouble(showInputDialog("Lengde på spor(mm.ss); "));

				Spor nyttSpor = new Spor(tittel, artist, lengde);
				if (album.regNyttSpor(nyttSpor)) {
					showMessageDialog(null, "Spor registrert");
				}else{
					showMessageDialog(null, "Ikke plass på Album.");
				}

			}else if(valg == INFO){
				showMessageDialog(null, album);

			}else if(valg == SOK_ARTIST){
				String artist = showInputDialog("Artist du ønsker å søke etter: ");
				Spor[] spor = album.finnSporArtist(artist);
				String res = "Låter av " + artist + ":\n";
				if (spor != null){
					for(Spor s: spor){
						res += s + "\n";
					}
				} else res += "Ingen låter registrert";

				showMessageDialog(null, res);
			}
			valg = showOptionDialog(null, "Velg operasjon", "HiST Musikk Katalogisering", 0, 0, null, muligheter, muligheter[0]);
		}
	}
}