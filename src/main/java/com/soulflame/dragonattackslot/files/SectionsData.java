package com.soulflame.dragonattackslot.files;

import java.util.List;

public class SectionsData {

    private final String mapped;
    private final String mapping;
    private final List<String> lore;

    public SectionsData(String mapped, String mapping, List<String> lore) {
        this.mapped = mapped;
        this.mapping = mapping;
        this.lore = lore;
    }

    public String getMapped() {
        return mapped;
    }

    public String getMapping() {
        return mapping;
    }

    public List<String> getLore() {
        return lore;
    }
}