package simulator.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class User implements Serializable {
    private final String login;
    private final char[] password;

    private static final @Serial long serialVersionUID = 2841432161369539619L;
    
    @Override
    public String toString() {
        return login;
    }
}
