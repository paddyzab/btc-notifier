package com.paddy.btc.notifier.btc_notifier.storage;

public interface KeyValueStorage {

    void write(String value);

    String get(String key);
}