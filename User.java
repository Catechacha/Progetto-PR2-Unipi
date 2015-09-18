import java.util.*;
public class User{
	public String nickname;
	public User(String s){
		this.nickname=s;
	}
}

class UnauthorizedAccessException extends Exception{
	public UnauthorizedAccessException(){
		super();
	}
	public UnauthorizedAccessException(String s){
		super(s);
	}
}

class JustHereUserException extends Exception{
	public JustHereUserException(){
		super();
	}
	public JustHereUserException(String s){
		super(s);
	}
}

class NotHereUserException extends Exception{
	public NotHereUserException(){
		super();
	}
	public NotHereUserException(String s){
		super(s);
	}
}

class UnauthorizedUserException extends Exception{
	public UnauthorizedUserException(){
		super();
	}
	public UnauthorizedUserException(String s){
		super(s);
	}
}

class MsgException extends Exception{
	public MsgException(){
		super();
}
	public MsgException(String s){
		super(s);
	}
}

class EmptyMsgException extends Exception{
	public EmptyMsgException(){
		super();
	}
	public EmptyMsgException(String s){
		super(s);
	}
}

class WrongCodeException extends Exception{
	public WrongCodeException(){
		super();
	}
	public WrongCodeException(String s){
		super(s);
	}
}
