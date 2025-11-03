package Exceptions;

public class StorageException extends BotException{
    public StorageException(String msg) {
        super(ErrorCode.STORAGE_ERROR, msg);
    }
}