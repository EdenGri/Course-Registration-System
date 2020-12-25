package bgu.spl.net.api;

import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

public interface Message {
    Message execute(Database database, Session session);
}
