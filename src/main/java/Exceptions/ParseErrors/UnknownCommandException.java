package Exceptions.ParseErrors;

import Exceptions.ParseException;

public class UnknownCommandException extends ParseException {
    public UnknownCommandException(String cmd){
        super(cmd);
    }

}
