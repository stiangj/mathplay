import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

class Utstyr {
  public static final int bestillingsfaktor = 5;
  private int nr;  // entydig identifikasjon
  private String betegnelse;
  private String leverandør;
  private int påLager;     // mengde på lager
  private int nedreGrense;

  public Utstyr(int startNr, String startBetegnelse, String startLeverandør,
    int startPåLager, int startNedreGrense) {
    nr = startNr;
    betegnelse = startBetegnelse;
    leverandør = startLeverandør;
    påLager = startPåLager;
    nedreGrense = startNedreGrense;
  }

  public int finnNr() {
    return nr;
  }

  public String finnBetegnelse() {
    return betegnelse;
  }

  public String finnLeverandør() {
    return leverandør;
  }

  public int finnPåLager() {
    return påLager;
  }

  public int finnNedreGrense() {
    return nedreGrense;
  }

  public int finnBestKvantum() {
    if (påLager < nedreGrense) return bestillingsfaktor * nedreGrense;
    else return 0;
  }

  /*
   * Endringen kan være positiv eller negativ. Men det er ikke
   * mulig å ta ut mer enn det som fins på lager. Hvis klienten
   * prøver på det, vil metoden returnere false, og intet uttak gjøres.
   */
  public boolean endreLagerbeholdning(int endring) {
    System.out.println("Endrer lagerbeholdning, utstyr nr " + nr + ", endring: " + endring);
    if (påLager + endring < 0) return false;
    else {
      påLager += endring;
      return true;
    }
  }

  public void settNedreGrense(int nyNedreGrense) {
    nedreGrense = nyNedreGrense;
  }

  public String toString() {
    String resultat = "Nr: " + nr + ", " +
      "Betegnelse: " + betegnelse + ", " + "Leverandør: " +
       leverandør + ", " + "På lager: " + påLager + ", " +
      "Nedre grense: " + nedreGrense;
    return resultat;
  }
}

class RegisterImpl extends UnicastRemoteObject implements Register {
  public static final int ok = -1;
  public static final int ugyldigNr = -2;
  public static final int ikkeNokPåLager = -3;

  private ArrayList<Utstyr> registeret = new ArrayList<Utstyr>();

  public RegisterImpl() throws RemoteException {
  }

  public synchronized boolean regNyttUtstyr(int startNr, String startBetegnelse,
    String startLeverandør, int startPåLager, int startNedreGrense) throws RemoteException  {
   if (finnUtstyrindeks(startNr) < 0) { // fins ikke fra før
     Utstyr nytt = new Utstyr(startNr, startBetegnelse, startLeverandør,
           startPåLager, startNedreGrense);
     registeret.add(nytt);
     return true;
   } else return false;
  }

  public synchronized int endreLagerbeholdning(int nr, int mengde) throws RemoteException  {
    int indeks = finnUtstyrindeks(nr);
    if (indeks < 0) return ugyldigNr;
    else {
      if (!(registeret.get(indeks)).endreLagerbeholdning(mengde)) {
        return ikkeNokPåLager;
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
