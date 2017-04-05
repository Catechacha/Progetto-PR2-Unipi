(*	1)ASTRAZIONE	*)

type ide = string;; (* identificatori *)
(* Espressioni *)
type exp =
	| Ide of string
	| True (* booleano true *)
	| False (* booleano false *)
	| Int of int (* interi *)
	| And of exp * exp (* and di due espressioni booleane *)
	| Or of exp*exp (* or di due espressioni booleane *)
	| Not of exp (* not di un espressione booleana *)
	| Add of exp*exp (* per l'addizione tra interi *)
	| Sub of exp*exp (* per la sottrazione tra interi *)
	| Mul of exp*exp (* per la moltiplicazione tra interi *)
	| Equals of exp*exp (* confronto tra interi con = *)
	| LessEquals of exp*exp (*confronto tra interi con <= *)
	| IfThenElse of exp*exp*exp (* if then else: condizionale *)
	| Let of ide*exp*exp (* blocco let *)
	| Fun of ide*exp (*funzioni con un solo parametro non ricorsive *)
	| Apply of exp*exp(* applicazione funzionale *)
	| Trywith of ide*exp*p (* espressione try x with... *)
and p = | Dpattern of exp*exp (* pattern di default *)
	| Epattern of exp*exp (* pattern elementare *)
	| Cpattern of exp*exp*p (* composizione di pattern *)
(* FINE DEFINIZIONE EXP *)

exception UnboundException of string (*errore unbound *)
exception TypeWrongException of string (*errore di tipo*)

(*	2)INTERPRETE	*)

(* Definisco l'ambiente *)
type 't env = string -> 't;;
let emptyenv(x) = function y ->x;;(*definisco l'ambiente vuoto*)
let appenv (id:string) (e1:'t env) : 't = (e1 id);;
let bind (id:string) (value:'t) (e1:'t env) : ('t env) =
    fun (nuovaassociazione:string)->
        if (nuovaassociazione=id) then
            value
        else
            appenv nuovaassociazione e1;;
let nuovoambiente (nuovo:'t) = fun (i:string) -> nuovo;;
(* SPIEGAZIONE Ho definito un nuovo ambiente env; l'applicazione di env è definita da un identificatore ed un ambiente e cosa mi restituisce? Un 't che altro non è che il tipo che avevo definito prima! Bind: aggiungo una nuova associazione. Come funziona? A partire dalla stringa id, dal valore di tipo 't e dall'ambiente e1, mi restituisce un nuovo ambiente dove: se l'identificatore esiste già con un certo valore, gli do il valore nuovo value, altrimenti inserisco la nuova associazione nell'ambiente e1. *)
(*	type 'a env = string -> 'a
	val emptyenv : 'a -> 'b -> 'a = <fun>
	val appenv : string -> 'a env -> 'a = <fun>
        val bind : string -> 'a -> 'a env -> 'a env = <fun>	*)

(* Adesso mi definisco dei valori esprimibili, cioè che possono essere il risultato della valutazione di un'espressione e possono essere associati a variabili *)
type eval =
	| EvalInt of int	
	| EvalBool of bool
	| EvalFunval of efun
	| EvalUnbound
and efun= ide*exp*eval env;;

(* SEMANTICA OPERAZIONALE *)
let rec sem (e:exp) (r:eval env):eval = match e with
	| True-> EvalBool(true)
	| False -> EvalBool(false)
	| Ide s -> (match (appenv s r) with
			| EvalUnbound -> raise(UnboundException s) (*se è unbound do un'eccezione*)
			| k -> k (*se corrisponde al valore k lo ritorno*))
	| Int n -> EvalInt(n)
	| And(e1,e2) -> (match (sem e1 r) with
		| EvalBool(false) -> EvalBool(false)
		| EvalBool(true) -> ( match (sem e2 r) with
			| EvalBool(true) -> EvalBool(true)
			| EvalBool(false) -> EvalBool(false)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		|_ -> raise (TypeWrongException "Errore di tipo"))
	| Or(e1,e2) -> (match (sem e1 r) with
		| EvalBool(true) -> EvalBool(true)
		| EvalBool(false) -> (match (sem e2 r) with
			| EvalBool(true) -> EvalBool(true)
			| EvalBool(false) -> EvalBool(false)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		|_ -> raise (TypeWrongException "Errore di tipo"))
	| Not(e1) -> (match (sem e1 r) with
		| EvalBool(true) -> EvalBool(false)
		| EvalBool(false) -> EvalBool(true)
		| _ -> raise (TypeWrongException "Errore di tipo"))
	| IfThenElse(guardia,th,el) -> (match (sem guardia r) with
		| EvalBool(true) -> (sem th r)
		| EvalBool(false) -> (sem el r)
		| _ -> raise (TypeWrongException "Errore di tipo"))
	| Add(e1,e2) -> (match (sem e1 r) with
		| EvalInt(n) -> (match (sem e2 r) with
			| EvalInt(k) -> EvalInt(n+k)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		| _ ->  raise (TypeWrongException "Errore di tipo"))
	| Sub(e1,e2) -> (match (sem e1 r) with
		| EvalInt(n)  -> (match (sem e2 r) with
			| EvalInt(k) -> EvalInt(n-k)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		| _ ->  raise (TypeWrongException "Errore di tipo"))
	| Mul(e1,e2) -> (match (sem e1 r) with
		| EvalInt(n) -> (match (sem e2 r) with
			| EvalInt(k) -> EvalInt(n*k)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		| _ ->  raise (TypeWrongException "Errore di tipo"))
	|Equals(e1,e2) -> (match (sem e1 r) with
		| EvalInt(n) -> (match (sem e2 r) with
			| EvalInt(k) -> if(n=k) then
					EvalBool(true)
				else
					EvalBool(false)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		| _ ->  raise (TypeWrongException "Errore di tipo"))
	| LessEquals(e1,e2) -> (match (sem e1 r) with
		| EvalInt(n) -> (match (sem e2 r) with
			| EvalInt(k) -> if(n<=k) then
					EvalBool(true)
				else
					EvalBool(false)
			| _ -> raise (TypeWrongException "Errore di tipo"))
		| _ ->  raise (TypeWrongException "Errore di tipo"))
	| Let(i,e1,e2)-> let valore=(sem e1 r) in (* valore=valutazione e1 nell'ambiente r*)
				let nuovoambiente=bind i valore r in (*associo a i il valore creando un nuovo ambiente*)
					sem e2 nuovoambiente (*valuto nel nuovo ambiente e2*)
	| Fun(i,e) -> EvalFunval(i,e,r)
	| Apply(e1,e2) -> let parametro=(sem e2 r) in (*parametro è la valutazione di e2 nell'ambiente*)
				let funzione=(sem e1 r) in (*funzione è la valutazione di e1 nell'ambiente*)
					(match funzione with
						| EvalFunval(i,e,r)-> let nuovoambiente=bind i parametro r in (sem e nuovoambiente) (*se la funzione esiste nell'ambiente, associo i parametri attuali a quelli formali e valuto nel nuovo ambiente*)
						| _ -> raise (TypeWrongException "Errore di tipo"))(*altrimenti ho un errore di tipo*)
	| Trywith(i,e,pattern) -> let valore=(sem e r) in
				let nuovoambiente = bind i valore r in
					semanticapattern pattern nuovoambiente
and semanticapattern (pattern:p) (r: eval env) : eval = (match pattern with
	| Epattern(e1,e2) -> let sem1=(sem e1 r) in
				let sem2=(sem e2 r) in
					(match sem1 with
						| EvalBool(true) -> sem2
						| EvalBool(false) -> raise (TypeWrongException "Guardia falsa")
						| _ -> raise (TypeWrongException "Tipo guardia pattern errato"))
	| Cpattern(e1,e2,p) -> let sem1=(sem e1 r) in
				let sem2=(sem e2 r) in
					(match sem1 with
					| EvalBool(true) -> sem2
					| EvalBool(false) -> semanticapattern p r
					| _ -> raise (TypeWrongException "Tipo guardia pattern errato"))
	| Dpattern(_,e2) -> sem e2 r)
;;

(*	val sem : exp -> eval env -> eval = <fun>
	val semanticapattern : p -> eval env -> eval = <fun>	*)

(*	funzioni di ausilio	*)
let aggiungiassociazione (i:ide) (e:exp) (r:eval env): eval env =
	let valore= (sem e r) in
		bind i valore r;;

(*	3) TEST	*)
let r= emptyenv EvalUnbound;;

print_endline "-----TEST 1: AND ----";;
let a1=And(True,True);;
print_endline "risultato previsto: true";;
sem a1 r;;
let a2=And(True,False);;
print_endline "risultato previsto: false";;
sem a2 r;;
let a3=And(False,False);;
print_endline "risultato previsto: false";;
sem a3 r;;
print_endline "-----TEST 1 superato ----";;

print_endline "-----TEST 2: OR ----";;
let o1=Or(True,True);;
print_endline "risultato previsto: true";;
sem o1 r;;
let o2=Or(True,False);;
print_endline "risultato previsto: true";;
sem o2 r;;
let o3=Or(False,False);;
print_endline "risultato previsto: false";;
sem o3 r;;
print_endline "-----TEST 2 superato ----";;

print_endline "-----TEST 3: NOT ----";;
let n1=Not(True);;
print_endline "risultato previsto: false";;
sem n1 r;;
let n2=Not(False);;
print_endline "risultato previsto: true";;
sem n2 r;;
print_endline "-----TEST 3 superato ----";;

print_endline "-----TEST 4: ADD ----";;
let ad1=Add(Int (2),Int(2));;
print_endline "risultato previsto: 4";;
sem ad1 r;;
print_endline "-----TEST 4 superato ----";;

print_endline "-----TEST 5: SUB ----";;
let s1=Sub(Int (5),Int(2));;
print_endline "risultato previsto: 3";;
sem s1 r;;
print_endline "-----TEST 5 superato ----";;

print_endline "-----TEST 6: MUL ----";;
let m=Mul(Int (5),Int(2));;
print_endline "risultato previsto: 10";;
sem m r;;
print_endline "-----TEST 6 superato ----";;

print_endline "-----TEST 7: EQUALS ----";;
let e=Equals(Int (5),Int(2));;
print_endline "risultato previsto: false";;
sem e r;;
let e1=Equals(Int (2),Int(2));;
print_endline "risultato previsto: true";;
sem e1 r;;
print_endline "-----TEST 7 superato ----";;

print_endline "-----TEST 8: LESSEQUALS ----";;
let le1=LessEquals(Int (5),Int(2));;
print_endline "risultato previsto: false";;
sem le1 r;;
let le2=LessEquals(Int (1),Int(2));;
print_endline "risultato previsto: true";;
sem le2 r;;
print_endline "-----TEST 8 superato ----";;

print_endline "-----TEST 9: IF_THEN_ELSE ----";;
let ifte1=IfThenElse(le1,ad1,s1);;
print_endline "risultato previsto: 3";;
sem ifte1 r;;
let ifte2=IfThenElse(le2,ad1,s1);;
print_endline "risultato previsto: 4";;
sem ifte2 r;;
print_endline "-----TEST 9 superato ----";;

print_endline "-----TEST 10: LET ----";;
let l1=Let("x",ad1,Sub(Int (5),Ide "x"));;
print_endline "risultato previsto: 1";;
sem l1 r;;
print_endline "-----TEST 10 superato ----";;

print_endline "-----TEST 11: FUN & CALL ----";;
let f=Fun("y",Sub(Int (5),Ide "y"));;
let r=aggiungiassociazione "f1" f r;;(*metto la funzione nell'ambiente*)
let c1=Apply(Ide "f1", Int(4));;
print_endline "risultato previsto: 1";;
sem c1 r;;
print_endline "-----TEST 11 superato ----";;

print_endline "-----TEST 12: TRY WITH IN ----";;
let twi1=Trywith("x",s1,Cpattern(Equals(Ide "x",Int (5)),Int (5),Dpattern(Int(9),Int(9))));;(*se x!=5 ritorna 9, altrimenti ritorna 5*)
print_endline "risultato previsto: 9";;
sem twi1 r;;

let twi2=Trywith("x",s1,Cpattern(Equals(Ide "x",Int (5)),Int (5),Epattern(Equals(Ide "x",Int(3)),Ide "x")));;(*se x!=5, se è uguale x=3 dà x altrimenti eccezione *)
print_endline "risultato previsto: 3";;
sem twi2 r;;
print_endline "-----TEST 12 superato ----";;
