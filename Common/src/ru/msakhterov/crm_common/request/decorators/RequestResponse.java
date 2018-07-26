package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.requests.Request;

public class RequestResponse implements Response{

    @Override
    public String getLogMessage(Request request) {
        String logMessage = null;
        switch (request.getRequestSubject()) {
            case RequestSubjects.AUTH_ACCEPT:
                logMessage = "Успешная авторизация.";
            break;
            case RequestSubjects.AUTH_DENIED:
                logMessage = "Ошибка авторизации.";
            break;
            case RequestSubjects.REG_ACCEPT:
                logMessage = "Успешная регистрация.";
            break;
            case RequestSubjects.REG_DENIED:
                logMessage = "Ошибка регистрации.";
            break;
            case RequestSubjects.REQUEST_FORMAT_ERROR:
                logMessage = "Ошибка запроса.";
            break;
        }
        return logMessage;
    }
}
