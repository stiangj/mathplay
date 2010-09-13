import static javax.swing.JOptionPane.*;
import java.rmi.*;

class RegisterKlient {
	public static void main(String[] args)throws Exception {

		Register test = (Register) Naming.lookup("rmi://localhost/reg");



        test.regNyttUtstyr(1, "Barnemat", "Nestle", 20, 5);
        test.regNyttUtstyr(2, "Tissebleier", "Pampers", 100, 4);
        test.regNyttUtstyr(3, "Brød", "Rivertz Bakeri", 200, 50);
        test.regNyttUtstyr(4, "Poteter", "Farmz", 1000, 100);
        test.regNyttUtstyr(5, "Skjermkort", "ATI", 20, 5);
        System.out.println(test.lagDatabeskrivelse());


         String janei = "ja";

		 		while(!janei.equals("Nei")){

		 			String iVarenr = showInputDialog("Skriv inn varenr:");
		 			int varenr = Integer.parseInt(iVarenr);
		 			String nyLager = showInputDialog("Skriv inn endring i lagerbeholdning:");
		 			int nyLagerbeh = Integer.parseInt(nyLager);
		 			test.endreLagerbeholdning(varenr, nyLagerbeh);
		 			System.out.println(test.lagDatabeskrivelse());
					/*
		 			String bet = showInputDialog("Skriv inn vare-betegnelse");

		 			String lev = showInputDialog("Skriv inn leverandør");

		 			String pålag = showInputDialog("Skriv inn lagerstatus:");
		 			int påLager = Integer.parseInt(pålag);

		 			String grense = showInputDialog("Skriv inn nedre grense: ");
		 			int nedreGrense = Integer.parseInt(grense);

		 			test.regNyttUtstyr(varenr, bet, lev, påLager, nedreGrense);
		 			System.out.println(test.lagDatabeskrivelse());
					*/
		 			String fortsett = showInputDialog("Vil du fortsette?(Ja/Nei)");
		 			if(fortsett.equals("Nei") || fortsett.equals("nei") || fortsett.equals("NEI") ){
		 				janei = "Nei";
			}
		}
		System.out.println(test.lagBestillingsliste());



	 }
 }
