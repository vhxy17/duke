package com.golden.exceptions.storageErrors;

import com.golden.exceptions.StorageException;

public class StorageFormatException extends StorageException {
    public StorageFormatException(String msg){
        super(msg);
    }
}
