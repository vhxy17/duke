package Exceptions.StorageErrors;

import Exceptions.StorageException;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String msg) {
        super(msg);
    }
}