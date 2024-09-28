package simulator.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {
    private final String login;
    private final char[] password;

    @Serial
    private static final long serialVersionUID = 2841432161369539619L;

    public User(String login, char[] password) {
        this.login = Objects.requireNonNull(login, "Параметр login не может быть null!");
        this.password = Objects.requireNonNull(password, "Параметр password не может быть null!");
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        User otherUser = (User) other;
        return this.login.equals(otherUser.login) && Arrays.equals(this.password, otherUser.password);
    }

    @Override
    public int hashCode() {
        return login.hashCode() * Arrays.hashCode(password);
    }
}
