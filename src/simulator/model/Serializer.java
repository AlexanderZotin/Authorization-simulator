package simulator.model;

import javax.swing.JOptionPane;
import java.io.InvalidClassException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Serializer {
    private final Path serialFile = Paths.get("Authorization simulator data.ser");

    public Optional<UserRepository> deserialize() {
        String message;
        try(FileInputStream fileInputStream = new FileInputStream(serialFile.toFile());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return Optional.of((UserRepository) objectInputStream.readObject());
        } catch(ClassNotFoundException _) {
            message = "Программа и/или файл\n" + serialFile +
                    "\nповреждены, из-за этого не удалось считать данные о созданных ранее аккаунтах.\nВойти в старые аккаунты не получится, но можно попытаться создать новые.\nТак же причиной этой ошибки может стать устаревание версии файла\n"
                    + serialFile;
        } catch(IOException _) {
            message = "Не удалось считать данные из файла\n" + serialFile +
                    "\nВойти в старые аккаунты не получится, но можно попытаться создать новые.";
        } catch(SecurityException _) {
            message = "К файлу\n" + serialFile +
                    "\nзапрещён доступ, поэтому данные о созданных ранее аккаунтах\nсчитать не удалось. Войти в старые аккаунты не получится,\nно можно попытаться создать новые.";
        } catch (ClassCastException _) {
            message = "В файле\n" + serialFile +
                    "\nзаписаны сторонние данные, либо запись была сделана очень старой версией программы!";
        }
        JOptionPane.showMessageDialog(null, message, "Симулятор авторизации",
                JOptionPane.ERROR_MESSAGE);
        return Optional.empty();
    }

    public void serialize(UserRepository repository) {
        if(needCreateSerialFile()) createSerialFile();
        String message;
        try(FileOutputStream fileOutputStream = new FileOutputStream(serialFile.toFile());
                ObjectOutput objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(repository);
            return;
        } catch(InvalidClassException _) {
            message = "Программа и/или файл\n" + serialFile +
                    "повреждены, из-за этого не удалось записать данные о созданных аккаунтах.";
        } catch(IOException e) {
            message = "Не удалось записать данные о созданных аккаунтах в файл\n" + serialFile;
        } catch(SecurityException  _) {
            message = "К файлу\n" + serialFile +
                    "\nзапрещён доступ, поэтому данные о созданных аккаунтах\nзаписать не удалось.";
        }
        JOptionPane.showMessageDialog(null, message, "Симулятор авторизации", JOptionPane.ERROR_MESSAGE);
    }

    private boolean needCreateSerialFile() {
        return !Files.exists(serialFile);
    }

    private void createSerialFile() {
        String message;
        try {
            Files.createFile(serialFile);
            return;
        } catch(IOException _) {
            message = "Не удалось создать файл\n" + serialFile +
                    "\nНовые аккаунты после закрытия программы не будут запомнены.";
        } catch(SecurityException _) {
            message = "Не удалось получить доступ к директории\n" + serialFile.getParent() +
                    "\nНовые аккаунты после закрытия программы не будут запомнены.";
        }
        JOptionPane.showMessageDialog(null, message, "Симулятор авторизации",
                JOptionPane.ERROR_MESSAGE);
    }
}
