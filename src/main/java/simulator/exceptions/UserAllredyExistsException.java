package simulator.exceptions;

import lombok.NonNull;

public class UserAllredyExistsException extends RuntimeException {

    public UserAllredyExistsException(@NonNull String message) {
        super(message);
    }
}
