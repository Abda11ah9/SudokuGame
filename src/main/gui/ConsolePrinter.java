package gui;

import model.Event;
import model.EventLog;
import model.exceptions.LogException;

public class ConsolePrinter implements LogPrinter {

    @Override
    public void printLog(EventLog el) throws LogException {
        for (Event e : el) {
            System.out.println(e);
        }
        EventLog.getInstance().clear();
    }
}
