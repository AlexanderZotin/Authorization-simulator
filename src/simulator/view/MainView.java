package simulator.view;

import simulator.model.UserRepository;

public interface MainView extends Visible {
    void updateInfo(UserRepository recovery);
}
