package simulator.exceptions;

import lombok.NonNull;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(@NonNull String message) {
        super(message);
    }
}
