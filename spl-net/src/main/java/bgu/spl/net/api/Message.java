package bgu.spl.net.api;

import bgu.spl.net.impl.RegistrationSystem.Database;

public interface Message {
    Message execute(Database database);
}
