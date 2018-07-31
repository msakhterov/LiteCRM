package ru.msakhterov.crm_client.events.event_logger;

import ru.msakhterov.crm_client.events.EventListener;
import ru.msakhterov.crm_client.events.EventType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileLogger implements EventListener {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yy HH:mm:ss - ");
    private final String logFileName = "logfile.txt";
    private File rootDir;
    String newLine;

    public FileLogger() {
        rootDir = new File("Log\\");
        if (!rootDir.exists()) rootDir.mkdir();
        newLine = System.getProperty("line.separator");
    }

    @Override
    public void execute(EventType event) {
        String logMessage;
        switch (event){
            case LOGIN:
                logMessage = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": выполнена команда Login" + newLine;
                break;
            case REGISTRATION:
                logMessage = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": выполнена команда Registration"  + newLine;
                break;
            case DISCONNECT:
                logMessage = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": выполнена команда Disconnect" + newLine;
                break;
            default:
                logMessage = dateFormat.format(System.currentTimeMillis()) + Thread.currentThread().getName() + ": выполнена необрабатываемая команда"  + newLine;
                break;
        }
        try (FileWriter fr = new FileWriter(new File(rootDir, logFileName), true)){
            fr.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
