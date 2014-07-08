package io.github.achtern.AchternEngine.core.input.event.listener.trigger.util;

import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;

import java.util.ArrayList;
import java.util.Collections;

public class KeyTriggerList extends ArrayList<KeyTrigger> {


    public KeyTriggerList(Key... trigger) {
        for (Key k : trigger) {
            add(new KeyTrigger(k));
        }
    }

    public KeyTriggerList(KeyTrigger... trigger) {
        Collections.addAll(this, trigger);
    }
}
