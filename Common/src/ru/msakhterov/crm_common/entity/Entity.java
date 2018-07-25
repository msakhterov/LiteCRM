package ru.msakhterov.crm_common.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }
}
