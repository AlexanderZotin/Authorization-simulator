package simulator.model;

import simulator.exceptions.UserAllredyExistsException;
import simulator.exceptions.UserNotFoundException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.NonNull;

public class UserRepository implements Serializable {
    private final Set<User> value = new HashSet<>();
    private User currentUser;

    private static final @Serial long serialVersionUID = 5177844657444933748L;

    public void addNew(@NonNull User user) {
        if(!value.add(user)) {
            throw new UserAllredyExistsException("Такой пользователь уже существует!");
        }
    }

    public void setCurrent(User user) {
        if(user != null && !exists(user)) {
            throw new UserNotFoundException("Такого пользователя не существует!");
        }
        currentUser = user;
    }

    public boolean exists(User user) {
        return value.contains(user);
    }

    public Optional<User> getCurrent() {
        return Optional.ofNullable(currentUser);
    }
}
