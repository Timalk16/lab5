package org.example.managers;

import org.example.models.Ticket;
import org.example.utility.Shell;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Tag;


import java.io.*;
import java.util.*;

public class FileManager {
    private Yaml yaml = new Yaml();
    private String fileName;
    private Shell shell;

    public FileManager(String fileName, Shell shell) {
        if (!(new File(fileName).exists())) {
            fileName = "../" + fileName;
        }

        this.fileName = fileName;
        this.shell = shell;

    }

    /**
     * Записывает коллекцию в файл
     * @param collection
     */
    public void writeCollection(Collection<Ticket> collection, String fileName) {
        try (FileWriter collectionWriter = new FileWriter(fileName)) {
            yaml.dump(collection, collectionWriter);
            shell.println("Коллекция успешно записана в файл " + fileName);
        } catch (IOException e) {
            shell.printErr("Загрузочный файл не может быть открыт!");
        }
    }

    public Collection<Ticket> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var scanner = new Scanner(fileName)) {
                var yamlString = new StringBuilder();
                String line;

                while ((line = scanner.nextLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        yamlString.append(line);
                    }
                }

                if (yamlString.length() == 0) {
                    yamlString = new StringBuilder("[]");
                }

                Set<Ticket> collection = yaml.load(yamlString.toString());
                shell.println("Коллекция успешно загружена!");
                return collection;



            } catch (NoSuchElementException e) {
                shell.printErr("Загрузочный файл пуст!");
            } catch (YAMLException e) {
                shell.printErr("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException e) {
                shell.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            shell.printErr("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new LinkedHashSet<>();
    }
}

