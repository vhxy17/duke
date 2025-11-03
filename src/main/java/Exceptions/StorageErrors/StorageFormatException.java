package Exceptions.StorageErrors;

import Exceptions.StorageException;

public class StorageFormatException extends StorageException {
    public StorageFormatException(String msg){
        super(msg);
    }
}
