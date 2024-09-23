package org.example.application;

import org.example.application.service.JournalService;
import org.example.persistence.DaoFactory;

public class ServiceFactory {
    public static JournalService createJournalService(){
        return new JournalService(
                DaoFactory.createAttendanceLogDao(),
                DaoFactory.createLessonDao(),
                DaoFactory.createStudentDao(),
                DaoFactory.createTeamDao());
    }
}
