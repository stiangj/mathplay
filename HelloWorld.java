class HelloWorld {
	public String Sjekk() {
		return "bare tull";
	}

	public static void main (String[] args) {
		System.out.println("Andre er en FISK");
		System.out.println("en tunfisk");
// Hei alle sammen !
        String[] idioter = {"Selmer", "Paal", "Jinxen", "Greve"};
        System.out.println("Følgende personer er idioter: ");
	    for(int i=0;i<idioter.length;i++) {
			System.out.println("Idiot nr"+(i+1) +" er: "+idioter[i]+"");
		}
		System.out.println("That's it, that's all!");
	}//main
}//HelloWorld.java
