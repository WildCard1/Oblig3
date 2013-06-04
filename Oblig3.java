
/****************************************************************
 * @author henrihan<henrik.hansen@student.jus.uio.no>
 * Tilstandspaastander(utenom de som er spesifisert i oppg.):
 * - Vennskapsforhold er gjensidige.
 ****************************************************************/

/** 
 * Denne klassen fungerer som en beholder for personobjekter.
 * @param antall Antall personobjekter beholder inneholder.
 * @param foerste peker paa foerste objektet i beholderen.
 */
class PersonBeholder {

    private int antall = 0;
    private Node foerste = null;
    
    /** Nodeklasse som trengs for aa faa programmet til aa kjoere skikkelig, og
     *saann at programmet kan operere med samme p.objekter hele tiden,
     *og slipper aa skape nye(med javaord "new") p.obj. i testklassen.
     *Legger node-klassen i PersonBeholder-klassen slik at den har tilgang
     *paa PersonBeholder's objekter m.m.
     *@param neste Peker paa neste personobjekt.
     *@param obj Personobjektet som det opereres med i denne instansen
     */
    private class Node { 
	private Node neste;
	private Person obj;

	/** Konstruktoer for klassen "Node", som legger personobjektet i pekeren "obj"
	 * @param p Personobjektet som det jobbes med naa.
	 */
	Node(Person p) {
	    obj = p;
	}
    }
    
    /**Metode som setter inn personer i lenkelista. Denne metoden har et FIFO-oppsett
     * selv om det strengt tatt ikke betyr noe i denne sammenhengen, siden
     * ingenting skal tas ut.
     * @param p Personobjektet som skal settes inn i lenkelista.
     */
    public void settInnPerson(Person p) {
	Node n = new Node(p); // Ny peker til nodeklassen. "p" hentes opp av konstruktoren til "Node".
	if(erIBeholder(n.obj.hentNavn()))
	    System.out.println("Beklager, " + p.hentNavn() + " kan ikke legges i beholderen mer enn en gang");
	if (foerste == null) {
	    foerste = new Node(p);
	    antall++;
	}
	if(foerste != null && !erIBeholder(n.obj.hentNavn())){
	    n.neste = foerste;
	    foerste = n;
	    antall++;
	}
    }

    /** Metode for aa sjekke om objektet allerede er i lenkelista,
     *   ved aa sjekke navnet til personobjektet mot navnene som
     *   allerede er registrert i beholderen.
     * @param s Navnet paa p.obj. som skal sjekkes.
     */
    public boolean erIBeholder(String s) {
	boolean funnet = false;
	String s1;
	Node n = foerste;
	while( n != null && !funnet) {
	    s1 = n.obj.hentNavn(); // s1 settes til aa peke paa String-objektet "navn" i naavarende p.obj.
	    if (s1.equalsIgnoreCase(s)) {
		funnet = true;
	    }else {
		n = n.neste; // Ikke funnet; gaa videre til neste p.obj.
	    }
	}
	return funnet; // Returnerer true hvis det allerede finnes et obj. med navnet til "s"
    }
    
    /**Metode for aa skrive ut navnet til alle personene i beholderen.
     *  @param prefiks String-objekt som skal skrives ut uansett foerst
     */
    public void skrivAlle(String prefiks) {
	Node n = foerste;
	if(n == null) { // Listen er tom.
	    System.out.println("Det er ingen i listen enda. Legg til personer for aa bruke denne funksjonen");
	}else { 
	    while( n != null) { // Saa lenge beholderen har flere personobjekter igjen aa skrive ut.
		System.out.println(prefiks + n.obj.hentNavn());
		n = n.neste; // Videre til neste personobjekt
	    }
	}
    }
    
    /** Returnerer antall personer i lenkelista.*/
    public int hentAntall() {
	return antall;
    }
}

/** Klasse som beskriver et personobjekt.
 *   @param mineVenner Peker til en beholder med vennene til innevarende p.obj.(unikt for hver person).
 *   @param navn Navnet til innevarende p.obj.
 */
class Person {

    private PersonBeholder mineVenner = new PersonBeholder();
    private String navn;
    
    /** Konstruktoer som setter navnet til personen i objektvariabelen med samme navn*/  
    public Person(String navn) {
	this.navn = navn;
    }

    /** Metode som returnerer navnet til innevarende p.obj.*/
    public String hentNavn() {
	return navn;
    }
    
    /** Returnerer antall venner til innevarende p.obj.*/
    public int antallVenner() {
	return mineVenner.hentAntall();
    }
    
    /** Metode for aa legge inn p.objekter i beholderen. 
     *   @param p P.obj. som skal legges inn i vennebeholderen.
     *   @param p1 Personbeholderen, saa metoden vet hvem som er registrert i beholderen og ikke.
     *   Trengs da det ikke kan legges til venner som ikke er i beholderen
     */
    public void blirVennMed(Person p, PersonBeholder p1) {
	if(this.equals(p)) // Hvis personen proever aa legge til seg selv.
	    System.out.println("Beklager " + navn + " kan ikke legge til seg selv som venn");
	else { 
	    if(p1.erIBeholder(p.hentNavn())) { // Hvis p finnes i personbeholderen.
		mineVenner.settInnPerson(p);
	    }else { // Hvis personen IKKE ligger i personbeholderen.
		System.out.println("Beklager " + p.hentNavn() + " ligger ikke i personlista. Legg personen inn der foerst");
	    }
	}
    }

    /** Metode for aa sjekke om personen er venn med objektet(som heter "s")
     *   @param s Navnet til personen som det skal sjekkes om personen er venn med
     */
    public boolean erVennMed(String s) {
	return mineVenner.erIBeholder(s); 
    }

    /** Skriv ut personens venner*/
    public void skrivUtMeg() {
	if (antallVenner() != 0) { // Hvis personen har venner
	    System.out.println(navn);
	    mineVenner.skrivAlle ( " > " ) ;
	}else{
	    System.out.println(navn);
	    System.out.println( " > " + " har ingen  venner, stakkar");    
	}
    }
}
/** Testklasse for aa teste programmet, feks at alle invariante tilstandspaastander holder vann.
 *   @param personbeholder Generisk beholder (som kun brukes paa p.obj.)
 *   som peker paa alle personobjektene i omloep.
 */
class Test {
    
    private PersonBeholder personbeholder = new PersonBeholder();
    private Person per, paal, espen, tor, odin, loke, frigg, balder, heimdal, fraud;
    
    /** Metode for aa opprette objektene, og legge dem inn i personbeholderen.*/  
    public void opprettObj() {
	per = new Person("Per");
	personbeholder.settInnPerson(per);
	paal = new Person("Paal");
	personbeholder.settInnPerson(paal);
	espen =  new Person("Espen");
	personbeholder.settInnPerson(espen);
	tor = new Person("Tor");
	personbeholder.settInnPerson(tor);
	odin = new Person("Odin");
	personbeholder.settInnPerson(odin);
	loke = new Person("Loke");
	personbeholder.settInnPerson(loke);
	personbeholder.settInnPerson(loke); // Tatt med for aa vise hva som skjer hvis du proever 2 ganger med samme p.obj.
	frigg = new Person("Frigg"); // Ikke tatt med i personbeholder for aa vise hva som skjer naar hun proeves aa bli venn med.
	balder = new Person("Balder"); 
	personbeholder.settInnPerson(balder);
	heimdal = new Person("Heimdal");
	personbeholder.settInnPerson(heimdal);
	fraud = new Person("Odin");
	personbeholder.settInnPerson(fraud); // For aa vise at det ikke kan legges inn to personer med samme navn.	
    }

    /** Metode for aa stadfeste vennskap mellom personene.
     *   Kaller paa blirVennMed(); som legger dem inn i mineVenner.
     */
    public void vennskap() {
	per.blirVennMed((paal), personbeholder); 
	per.blirVennMed((espen), personbeholder);
	per.blirVennMed((per), personbeholder); // Tatt med for aa vise hva som skjer hvis du proever aa bli venn med deg selv.
	paal.blirVennMed((espen), personbeholder);
	paal.blirVennMed((per), personbeholder);
	espen.blirVennMed((per), personbeholder);
	espen.blirVennMed((paal), personbeholder);
	tor.blirVennMed((odin), personbeholder);
	tor.blirVennMed((balder), personbeholder);
	tor.blirVennMed((heimdal), personbeholder);
	tor.blirVennMed((frigg), personbeholder); // Tatt med for aa vise hva som skjer naar hun proeves aa bli venn  med.
	odin.blirVennMed((balder), personbeholder);
	odin.blirVennMed((tor), personbeholder);
	odin.blirVennMed((heimdal), personbeholder);
	odin.blirVennMed((frigg), personbeholder); // Tatt med for aa vise hva som skjer naar hun proeves aa bli venn med.
	frigg.blirVennMed((balder), personbeholder);
	frigg.blirVennMed((tor), personbeholder);
	frigg.blirVennMed((odin), personbeholder);
	balder.blirVennMed((odin), personbeholder);
	balder.blirVennMed((tor), personbeholder);
	balder.blirVennMed((frigg), personbeholder); // Tatt med for aa vise hva som skjer naar hun proeves aa bli venn med.
	balder.blirVennMed((heimdal), personbeholder);
	heimdal.blirVennMed((odin), personbeholder);
	heimdal.blirVennMed((odin), personbeholder); // For aa vise hva som skjer hvis du proever aa legge til en person som allerede er i mineVenner.
	heimdal.blirVennMed((tor), personbeholder);
	heimdal.blirVennMed((balder), personbeholder);
	
    }
    
    /** Metode for aa skrive ut vennskapet mellom hverandre*/
    public void skrivUtAlt() {
	System.out.println("\nALLE PERSONENE I PERSONBEHOLDEREN: ");
	personbeholder.skrivAlle("-");
	System.out.println("\nVENNSKAPSNETTVERKET: ");
	per.skrivUtMeg();
	paal.skrivUtMeg();
	espen.skrivUtMeg();
	tor.skrivUtMeg();
	odin.skrivUtMeg();
	loke.skrivUtMeg();
	frigg.skrivUtMeg();
	balder.skrivUtMeg();
	heimdal.skrivUtMeg();
	
    }
}
/** Hovedklassen som starter programmet og kaller paa metoder i testklassen*/
class Oblig3 {
    public static void main(String[]args) {
	System.out.println("\n----------- Velkommen til Asbjoernsen's & Moe's og Valhall's vennenettverk -----------\n");
	Test t = new Test();
	t.opprettObj();
	t.vennskap();
	t.skrivUtAlt();
	System.out.println("\n-------------------------------- SLUTT PAA PROGRAMMET --------------------------------");
    }
}
