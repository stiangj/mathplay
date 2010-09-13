import java.util.*;
import java.rmi.*;



public interface Register extends Remote {

	boolean regNyttUtstyr(int startNr, String startBetegnelse,
    String startLeverand�r, int startP�Lager, int startNedreGrense) throws RemoteException;

	int endreLagerbeholdning(int nr, int mengde) throws RemoteException;

    int finnUtstyrindeks(int nr) throws RemoteException;

    String lagBestillingsliste() throws RemoteException;

    String lagDatabeskrivelse() throws RemoteException;
}