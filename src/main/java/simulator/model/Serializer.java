package simulator.model;

import javax.swing.JOptionPane;
import java.io.InvalidClassException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Optional;

import lombok.NonNull;

public class Serializer {
    private final File serialFile = new File("Authorization simulator data.ser");

    public Optional<UserRepository> deserialize() {
        try(var fileInputStream = new FileInputStream(serialFile);
                 var objectInputStream = new ObjectInputStream(fileInputStream)) {
            return Optional.of((UserRepository) objectInputStream.readObject());
        } catch(Exception exception) {
            handleDeserializeException(exception);
        }
        return Optional.empty();
    }

    private void handleDeserializeException(Exception exception) {
        showError(switch(exception) {
            case IOException _ ->  "Не удалось считать данные из файла\n" + serialFile +
                    "\nВойти в старые аккаунты не получится, но можно попытаться создать новые.\nПодробности:\n" + exception;
            case SecurityException _ -> "К файлу\n" + serialFile +
                    "\nзапрещён доступ, поэтому данные о созданных ранее аккаунтах\nсчитать не удалось. Войти в старые аккаунты не получится,\nно можно попытаться создать новые.";
            case ClassCastException _ -> "В файле\n" + serialFile +
                    "\nзаписаны сторонние данные, либо запись была сделана очень старой версией программы!";
            default -> "Произошла непредвиденная ошибка при попытке прочитать данные об аккаунтах:\n" + exception;
        });
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Симулятор авторизации", JOptionPane.ERROR_MESSAGE);
    }
    
    public void serialize(@NonNull UserRepository repository) {
        try(var fileOutputStream = new FileOutputStream(serialFile);
                var objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            if(needCreateSerialFile()) {
                createSerialFile();
            }
            objectOutputStream.writeObject(repository);
        } catch(Exception exception) {
            handleSerializeException(exception);
        }
    }

    private void handleSerializeException(Exception exception) {
        showError(switch(exception) {
            case InvalidClassException _ -> "Программа и/или файл\n" + serialFile +
                    "\nповреждены, из-за этого не удалось записать данные о созданных аккаунтах.";
            case IOException _ -> "Не удалось записать данные о созданных аккаунтах в файл\n" + serialFile + 
                    "\nПодробности:\n" + exception;
            case SecurityException _ -> "К файлу\n" + serialFile +
                    "\nзапрещён доступ, поэтому данные о созданных аккаунтах\nзаписать не удалось.";
            default -> "Произошла непредвиденная ошибка при попытке записать данные об аккаунтах.\nВнимание: аккаунты, созданые за этот сеанс, не были сохранены:\n"
                    + exception;
        });
    }
    
    private boolean needCreateSerialFile() {
        return !serialFile.exists();
    }

    private void createSerialFile() throws IOException {
        Files.createFile(serialFile.toPath());
    }
}
