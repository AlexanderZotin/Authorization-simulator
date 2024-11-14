package simulator.view;

import simulator.model.User;
import java.util.Optional;

public interface DialogView extends Visible {
    Optional<User> getUser();
}
