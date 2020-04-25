package com.androidapp.reseau;

import io.socket.emitter.Emitter;

public interface RecevoirMessage {
    void recevoirMessage(String event, Emitter.Listener fn);
}
