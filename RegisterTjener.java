import java.rmi.Naming;

class RegisterTjener {
	public static void main(String[] args) throws Exception {
		String objektnavn = "reg";
		System.out.println("Skal lage et tjenerobjekt");
		Register register = new RegisterImpl();  // lager objektet
		System.out.println("Nå er det laget!");
		Naming.rebind(objektnavn, register);  // registrerer objektet
		javax.swing.JOptionPane.showMessageDialog(null,
		                   "Trykk Ok for å stoppe tjeneren.");
		Naming.unbind(objektnavn);
		System.exit(0);
	}
}