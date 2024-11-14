package simulator.controller;

import simulator.model.Serializer;
import simulator.model.User;
import simulator.model.UserRepository;
import simulator.view.MainView;
import lombok.Getter;
import lombok.NonNull;

public class ControllerImplementation implements AuthorizationController {
    private final @Getter MainView view;
    private UserRepository userRepository;

    public ControllerImplementation(@NonNull MainView view) {
        this.view = view;
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
