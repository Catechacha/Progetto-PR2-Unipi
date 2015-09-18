import java.util.*;
public interface SimpleTw {
/*OVERVIEW
Un microblog è una collezione di contenuti testuali di una certa lunghezza massima (stringhe lunghe al massimo 140 caratteri). Il microblog è utilizzabile solo tramite una registrazione: solo gli utenti registrati possono scrivere, mentre la lettura è aperta a tutti.*/

	public void addUser(User bob, String pass) throws UnauthorizedAccessException, 				JustHereUserException, NullPointerException;
	/*MODIFIES: lista degli utenti registrati
	EFFECTS: Aggiunge l’utente bob all’insieme degli utenti. Solleva UnauthorizedAccessException se 	la pass non è corretta. Solleva JustHereUser se l'utente è già presente. Solleva NullPointerException se bob=NULL*/

	public void deleteUser(User bob, String pass) throws UnauthorizedAccessException, 			NotHereUserException;
	/*MODIFIES: lista degli utenti registrati
	EFFECTS: Rimuove l’utente bob all’insieme degli utenti. Solleva UnauthorizedAccessException se 	la 		pass non è corretta. Solleva NotHereUser se l'utente non è presente*/

	public int tweet(String message, Tag t, User bob)throws UnauthorizedUserException, MsgException, NullPointerException;
	/*MODIFIES: lista utenti (che hanno inserito messaggi), lista dei messaggi, lista dei tag
	EFFECTS: Inserisce un messaggio di bob con intestazione t. Restituisce un codice numerico del 		messaggio. Lancia un’eccezione se bob non è un utente registrato o se il messaggio supera la 		dimensione massima o va sotto la dimensione minima (0 caratteri).
	Se bob o t o message sono =NULL lancia la NullPointerException*/

	public String readLast(User bob) throws EmptyMsgException;
	/*MODIFIES: Nothing
	EFFECTS: Restituisce l’ultimo messaggio inserito da bob. Solleva un’eccezione se non ci sono 		messaggi di bob.*/

	public String readLast() throws EmptyMsgException;
	/*MODIFIES: Nothing
	EFFECTS: Restituisce l’ultimo messaggio inserito. Solleva un’eccezione se non sono presenti 		messaggi.*/

	public List<String> readAll(Tag t) throws EmptyMsgException;
	/*MODIFIES: Nothing
	EFFECTS: Restituisce tutti i messaggi inseriti con Tag t, nell’ordine di inserimento. Solleva 		un’eccezione se non sono presenti messaggi con quel tag o se la lista dei messaggi è vuota.*/

	public List<String> readAll() throws EmptyMsgException;
	/*MODIFIES: Nothing
	EFFECTS: Restituisce tutti i messaggi inseriti, nell’ordine di inserimento. Solleva   			EmptyMsgException se non sono presenti messaggi*/

	public void delete(int code) throws WrongCodeException, EmptyMsgException;
	/*MODIFIES: lista degli user, dei messaggi e dei tag
	EFFECTS: Cancella il messaggio identificato da code. Solleva WrongCodeException se non esiste un 		messaggio con quel codice. Solleva EmptyMsgException se non ci sono messaggi*/

	public boolean empty();
	/*MODIFIES: Nothing
	EFFECTS: Restituisce true se e solo se non sono presenti messaggi, altrimenti false*/
}
