package com.soulflame.dragonattackslot.files;

import java.util.List;

public class SectionsData {

    private final String dragon_slot;
    private final String vanilla_slot;
    private final List<String> lore;

    public SectionsData(String dragon_slot, String vanilla_slot, List<String> lore) {
        this.dragon_slot = dragon_slot;
        this.vanilla_slot = vanilla_slot;
        this.lore = lore;
    }

    public String getDragon_slot() {
        return dragon_slot;
    }

    public String getVanilla_slot() {
        return vanilla_slot;
    }

    public List<String> getLore() {
        return lore;
    }

}