package simulator.controller;

import simulator.model.Serializer;
import simulator.model.User;
import simulator.model.UserRepository;
import simulator.view.MainView;

import java.util.Objects;

public class Controller implements AuthorizationController {
    private final MainView view;
    private final UserRepository userRepository;

    public Controller(MainView view) {
        this.view = Objects.requireNonNull(view);
        userRepository = new Serializer().deserialize().orElseGet(UserRepository::new);
    }

    @Override
    public void logIn(User user) {
        userRepository.setCurrent(user);
        view.updateInfo(userRepository);
    }

    @Override
    public void sigIn(User user) {
        userRepository.addNew(user);
        userRepository.setCurrent(user);
        view.updateInfo(userRepository);
    }

    @Override
    public void logOut() {
        userRepository.setCurrent(null);
        view.updateInfo(userRepository);
    }

    @Override
    public void onClose() {
        new Serializer().serialize(userRepository);
    }
}
