import java.util.*;
/*	FUNZIONE DI ASTRAZIONE
	La password è la chiave di accesso al sistema ed è la stessa per tutti gli utenti. Si può modificare una sola volta, ovvero in fase di inizializzazione.
	listautenti: lista degli utenti che hanno scritto un messaggio. Se un utente ha scritto più di un messaggio è presente più volte in lista
	listamessaggi: lista dei messaggi inseriti
	listatag: lista dei tag inseriti
	listacodici: lista dei codici dei messaggi
	utentiregistrati: lista degli utenti che si sono registrati nel sistema	

	1 nuovo utente --> se la password è corretta si ha:
		1 inserimento in utentiregistrati

	1 nuovo messaggio --> se l'utente che vuole inserire è registrato si ha:
		1 inserimento in listautenti (utente che vuole inserire il messaggio)
		1 inserimento in listamessaggi (messaggio che l'utente vuole inserire)
		1 inserimento in listatag (tag che l'utente vuole inserire)
		1 inserimento in listacodici (codice del messaggio)
*/
	
/*	INVARIANTE DI RAPPRESENTAZIONE
	- Ogni messaggio ha lunghezza minore di 140 caratteri
	- Per ogni elemento della listautenti ce ne è uno in utentiregistrati
	- listautenti.size=listamessaggi.size=listatag.size=listacodici.size
	- Ogni utente è != null
	- Messaggi e tag sono != null
	- Per ogni elemento in utentiregistrati: l'elemento i è != dall'elemento j con i!=j
*/
public class myTw implements SimpleTw{
	public List<User> listautenti;/*utenti che scrivono messaggi*/
	public List<String> listamessaggi;/*messaggi inseriti*/
	public List<Tag> listatag;/*tag inseriti*/
	public List<Integer> listacodici;/*codici messaggi inseriti*/

	public List<User> utentiregistrati;/*per registrazione utenti*/
	private final String password;/*per la password; final mi dà la possibilità di modificare la password una sola volta la password, ovvero all'inizializzazione*/

	int i=0;/*per il codice messaggio*/

	public myTw(String s){/*il metodo costruttore mi stabilisce qual è la password*/
		password=s;
		utentiregistrati=new ArrayList<User>();
		listautenti=new ArrayList<User>();
		listamessaggi=new ArrayList<String>();
		listatag=new ArrayList<Tag>();
		listacodici=new ArrayList<Integer>();
	}

	public void addUser(User bob, String pass) throws UnauthorizedAccessException,JustHereUserException,NullPointerException{
		if(pass!=password)
			throw new UnauthorizedAccessException("Accesso non autorizzato nell'inserimento utenti");
		if(bob==null)
			throw new NullPointerException("Utente NULL");
		if(utentiregistrati.indexOf(bob)!=-1)
			throw new JustHereUserException("Nome utente già presente");/*non possono esserci utenti con il solito nickname*/
		else
			utentiregistrati.add(bob);
	}

	public void deleteUser(User bob, String pass) throws UnauthorizedAccessException,NotHereUserException{
		if(pass!=password)
			throw new UnauthorizedAccessException("Accesso non autorizzato nella rimozione utenti");
		if(utentiregistrati.indexOf(bob)==-1)
			throw new NotHereUserException("Nome utente non presente");
		else
			utentiregistrati.remove(bob);
	}

	public int tweet(String message, Tag t, User bob)throws UnauthorizedUserException, MsgException, NullPointerException{
		if(utentiregistrati.indexOf(bob)==-1)
			throw new UnauthorizedUserException("Accesso non autorizzato! Impossibile inserire il tweet: occorre prima registrarsi");
		if(message.length()<=0 || message.length()>140)
			throw new MsgException("Messaggio troppo lungo (max 140 caratteri) o troppo corto (min 1 carattere)");
		if(message==null || t==null || bob==null)
			throw new NullPointerException("Messaggio troppo lungo (max 140 caratteri) o troppo corto (min 1 carattere)");
		listautenti.add(bob);
		listamessaggi.add(message);
		listatag.add(t);
		i++;
		listacodici.add(i);
		return listamessaggi.size();	
	}

	public String readLast(User bob) throws EmptyMsgException{
		int index=listautenti.lastIndexOf(bob);
		if(index==-1)
			throw new EmptyMsgException("Non ci sono messaggi dell'utente selezionato");
		else
			return listamessaggi.get(index);
	}

	public String readLast() throws EmptyMsgException{
		if(empty()==true)
			throw new EmptyMsgException("Non ci sono tweet");
		else
			return listamessaggi.get(listamessaggi.size()-1);
	}

	public List<String> readAll(Tag t) throws EmptyMsgException{
		if(empty()==true)
			throw new EmptyMsgException("Non ci sono messaggi");
		ArrayList<String> x=new ArrayList<String>();
		String elemento;
		int j;
		for(j=0;j<listatag.size();j++)
			if(listatag.get(j)==t){
				elemento=listamessaggi.get(j);
				x.add(elemento);
			}
		if(x.isEmpty()==true)
			throw new EmptyMsgException("Non ci sono messaggi con il tag sepcificato");
		return x;
	}

	public List<String> readAll() throws EmptyMsgException{
		if(empty()==true)
			throw new EmptyMsgException("Non ci sono messaggi");
		ArrayList<String> x=new ArrayList<String>();
		String elemento;
		int j;
		for(j=0;j<listamessaggi.size();j++){
			elemento=listamessaggi.get(j);
			x.add(elemento);
		}
		if(x.isEmpty()==true)
			throw new EmptyMsgException("Non ci sono messaggi con il tag sepcificato");
		return x;
	}

	public void delete(int code) throws WrongCodeException, EmptyMsgException{
		int index;
		index=listacodici.indexOf(code);
		if(index==-1)
			throw new WrongCodeException("Codice messaggio errato: non è presente nessun messaggio corrispondente al codice desiderato");
		if(empty()==true)
			throw new EmptyMsgException("Non ci sono messaggi");
		listamessaggi.remove(index);
		listatag.remove(index);
		listautenti.remove(index);
		listacodici.remove(index);
	}

	public boolean empty(){
		if (listamessaggi.size()==0)
			return true;
		else
			return false;
	}
}
