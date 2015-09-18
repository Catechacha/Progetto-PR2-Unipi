import java.util.*;
public class Test{
	public static void main(String[] args){
		int i;
		int c1,c2,c3,c4,c5,c6,c7;
		String s1="HaroldFinch";
		String s2="Root";
		String s3="Mr.Reese";
		String s4="SaminShaw";
		String s5="DetectiveFusco";
		String s6="Samaritan";
		String lastmess;
		User u1=new User(s1);
		User u2=new User(s2);
		User u3=new User(s3);
		User u4=new User(s4);
		User u5=new User(s5);
		User u6=new User(s6);
		Tag t1=new Tag("NEW_NUMBER");
		Tag t2=new Tag("ALARM");
		Tag t3=new Tag("CIAO");
		List<String> x=new ArrayList<String>();
		boolean valore;

		String password="numeri_irrilevanti";
		myTw macchina = new myTw(password);
		System.out.println("La password è "+password);
		
		/*Verifica che le liste create sono vuote*/
		System.out.println("Verifico che le liste create siano vuote");
		if(macchina.empty()==true)
			System.out.println("La lista messaggi è vuota");
		if(macchina.listautenti.size()==0)
			System.out.println("La lista utenti è vuota");
		if(macchina.listatag.size()==0)
			System.out.println("La lista tag è vuota");
		if(macchina.listacodici.size()==0)
			System.out.println("La lista dei codici è vuota");

		System.out.println("");
		/*INSERIMENTO DEGLI UTENTI*/
		System.out.println("TEST INSERIMENTO-CANCELLAZIONE UTENTI!");
		try{
			System.out.println("Inserimento utente "+s1);
			macchina.addUser(u1,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		try{
			System.out.println("Inserimento utente "+s2);
			macchina.addUser(u2,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		try{
			System.out.println("Inserimento utente "+s3);
			macchina.addUser(u3,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		try{
			System.out.println("Inserimento utente "+s4);
			macchina.addUser(u4,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		try{
			System.out.println("Inserimento utente "+s5);
			macchina.addUser(u5,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		/*inserimento di un utente con password errata*/
		System.out.println("Inserimento utente con password sbagliata -> mi aspetto un'eccezione");
		try{
			macchina.addUser(u1,"ciao");
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		System.out.println("Inserimento di un utente e cancellazione");
		try{
			System.out.println("Inserimento dell'utente "+s6);
			macchina.addUser(u6,password);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (JustHereUserException e){
			System.out.println("Catturata JustHereUserException");
		}
		try{
			macchina.deleteUser(u6,password);
			System.out.println("Cancellazione dell'utente "+s6);
			System.out.println("Verifico che non ci sia in lista utenti:");
			for(i=0;i<macchina.utentiregistrati.size();i++)
				System.out.println("Utente"+i+": "+macchina.utentiregistrati.get(i).nickname);
		}
		catch (UnauthorizedAccessException e){
			System.out.println("Catturata UnauthorizedAccessException");
		}
		catch (NotHereUserException e){
			System.out.println("Catturata NotHereUserException");
		}
		System.out.println("TEST INSERIMENTO-CANCELLAZIONE UTENTI SUPERATO!");

		System.out.println("TEST INSERIMENTO MESSAGGI");
		try{	
			c1=macchina.tweet("E' uscito un nuovo numero",t1,u1);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		try{	
			c2=macchina.tweet("Me ne occupo io",t1,u3);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		try{	
			c3=macchina.tweet("Vuoi una mano Mr.Reese?",t1,u4);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		try{	
			c4=macchina.tweet("Samaritan sta prendendo il controllo della città",t2,u2);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		try{	
			c5=macchina.tweet("Io sono in centrale, tenetemi aggiornato",t1,u5);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		try{	
			c6=macchina.tweet("Root dobbiamo nascondere la macchina e tenerci al sicuro",t2,u1);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		System.out.println("");
		System.out.println("L'elenco dei messaggi è:");
		for(i=0;i<macchina.utentiregistrati.size();i++)
				System.out.println(macchina.listautenti.get(i).nickname+" "+macchina.listamessaggi.get(i)+" "+macchina.listatag.get(i).t);
		System.out.println("L'utente Samaritan prova ad inserire, ma non ci riesce perchè non è nella lista utenti registrati:");
		try{	
			c7=macchina.tweet("E' la fine",t2,u6);
		}
		catch(UnauthorizedUserException e){
			System.out.println("Catturata UnauthorizedUserException");
		}
		catch(MsgException e){
			System.out.println("Catturata MsgException");
		}
		System.out.println("TEST INSERIMENTO SUPERATO!");
		System.out.println("");

		System.out.println("TEST READLAST TAG");
		System.out.println("Leggo l'ultimo messaggio dell'utente HaroldFinch:");
		try{
			lastmess=macchina.readLast(u1);
			System.out.println(lastmess);
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("Leggo l'ultimo messaggio dell'utente Samaritan (Non ci sono suoi messaggi!):");
		try{
			lastmess=macchina.readLast(u6);
			System.out.println(lastmess);
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("TEST READLAST TAG SUPERATO!");
		System.out.println("");

		System.out.println("TEST READLAST ULTIMO MESSAGGIO");
		try{
			lastmess=macchina.readLast();
			System.out.println(lastmess);
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("TEST READLAST ULTIMO MESSAGGIO SUPERATO!");
		System.out.println("");

		System.out.println("TEST READALL TAG");
		try{
			x=macchina.readAll(t1);
			for(i=0;i<x.size();i++)
				System.out.println(x.get(i));
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("Adesso provo a leggere tutti i messaggi di un tag che non ho inserito");
		try{
			x=macchina.readAll(t3);
			for(i=0;i<x.size();i++)
				System.out.println(x.get(i));
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("TEST READALL TAG SUPERATO!");
		System.out.println("");

		System.out.println("TEST READALL");
		try{
			x=macchina.readAll();
			for(i=0;i<x.size();i++)
				System.out.println(x.get(i));
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		System.out.println("TEST READALL SUPERATO!");
		System.out.println("");

		System.out.println("TEST DELETE");
		System.out.println("La lista messaggi attuale è:");
		for(i=0;i<macchina.listamessaggi.size();i++)
			System.out.println(macchina.listamessaggi.get(i));
		System.out.println("");
		System.out.println("Cancello il messaggio con codice 15 (non esistente)");
		try{
			macchina.delete(15);
			for(i=0;i<macchina.listamessaggi.size();i++)
				System.out.println(macchina.listamessaggi.get(i));
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		catch (WrongCodeException e){
			System.out.println("Catturata WrongCodeException");
		}
		System.out.println("");
		System.out.println("Cancello il messaggio con codice 3");
		try{
			macchina.delete(3);
			for(i=0;i<macchina.listamessaggi.size();i++)
				System.out.println(macchina.listamessaggi.get(i));
		}
		catch (EmptyMsgException e){
			System.out.println("Catturata EmptyMsgException");
		}
		catch (WrongCodeException e){
			System.out.println("Catturata WrongCodeException");
		}
		System.out.println("TEST DELETE SUPERATO!");
		System.out.println("");

		System.out.println("TEST EMPTY");
		valore=macchina.empty();
		if(valore==true)
			System.out.println("La coda è vuota");
		else{
			System.out.println("La coda non è vuota");
			System.out.println("TEST EMPTY SUPERATO!");
		}
	}
}
