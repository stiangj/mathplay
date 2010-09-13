import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

class Utstyr {
  public static final int bestillingsfaktor = 5;
  private int nr;  // entydig identifikasjon
  private String betegnelse;
  private String leverand�r;
  private int p�Lager;     // mengde p� lager
  private int nedreGrense;

  public Utstyr(int startNr, String startBetegnelse, String startLeverand�r,
    int startP�Lager, int startNedreGrense) {
    nr = startNr;
    betegnelse = startBetegnelse;
    leverand�r = startLeverand�r;
    p�Lager = startP�Lager;
    nedreGrense = startNedreGrense;
  }

  public int finnNr() {
    return nr;
  }

  public String finnBetegnelse() {
    return betegnelse;
  }

  public String finnLeverand�r() {
    return leverand�r;
  }

  public int finnP�Lager() {
    return p�Lager;
  }

  public int finnNedreGrense() {
    return nedreGrense;
  }

  public int finnBestKvantum() {
    if (p�Lager < nedreGrense) return bestillingsfaktor * nedreGrense;
    else return 0;
  }

  /*
   * Endringen kan v�re positiv eller negativ. Men det er ikke
   * mulig � ta ut mer enn det som fins p� lager. Hvis klienten
   * pr�ver p� det, vil metoden returnere false, og intet uttak gj�res.
   */
  public boolean endreLagerbeholdning(int endring) {
    System.out.println("Endrer lagerbeholdning, utstyr nr " + nr + ", endring: " + endring);
    if (p�Lager + endring < 0) return false;
    else {
      p�Lager += endring;
      return true;
    }
  }

  public void settNedreGrense(int nyNedreGrense) {
    nedreGrense = nyNedreGrense;
  }

  public String toString() {
    String resultat = "Nr: " + nr + ", " +
      "Betegnelse: " + betegnelse + ", " + "Leverand�r: " +
       leverand�r + ", " + "P� lager: " + p�Lager + ", " +
      "Nedre grense: " + nedreGrense;
    return resultat;
  }
}

class RegisterImpl extends UnicastRemoteObject implements Register {
  public static final int ok = -1;
  public static final int ugyldigNr = -2;
  public static final int ikkeNokP�Lager = -3;

  private ArrayList<Utstyr> registeret = new ArrayList<Utstyr>();

  public RegisterImpl() throws RemoteException {
  }

  public synchronized boolean regNyttUtstyr(int startNr, String startBetegnelse,
    String startLeverand�r, int startP�Lager, int startNedreGrense) throws RemoteException  {
   if (finnUtstyrindeks(startNr) < 0) { // fins ikke fra f�r
     Utstyr nytt = new Utstyr(startNr, startBetegnelse, startLeverand�r,
           startP�Lager, startNedreGrense);
     registeret.add(nytt);
     return true;
   } else return false;
  }

  public synchronized int endreLagerbeholdning(int nr, int mengde) throws RemoteException  {
    int indeks = finnUtstyrindeks(nr);
    if (indeks < 0) return ugyldigNr;
    else {
      if (!(registeret.get(indeks)).endreLagerbeholdning(mengde)) {
        return ikkeNokP�Lager;
      } else return ok;
    }
  }

  public synchronized int finnUtstyrindeks(int nr) throws RemoteException  {
    for (int i = 0; i < registeret.size(); i++) {
      int funnetNr = (registeret.get(i)).finnNr();
      if (funnetNr == nr) return i;
    }
    return -1;
  }

  public synchronized String lagBestillingsliste() throws RemoteException  {
    String resultat = "\n\nBestillingsliste:\n";
    for (int i = 0; i < registeret.size(); i++) {
      Utstyr u = registeret.get(i);
      resultat += u.finnNr() + ", " + u.finnBetegnelse() + ": " +
                  u.finnBestKvantum() + "\n";
    }
    return resultat;
  }

  public synchronized String lagDatabeskrivelse() throws RemoteException  {
    String resultat = "Alle data:\n";
    for (int i = 0; i < registeret.size(); i++) {
      resultat += (registeret.get(i)).toString() + "\n";
    }
    return resultat;
  }
}
