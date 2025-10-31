package Exceptions;

public class ParseException extends BotException {
    public ParseException(String msg){
        super(ErrorCode.PARSE_ERROR, msg);
    }
}
