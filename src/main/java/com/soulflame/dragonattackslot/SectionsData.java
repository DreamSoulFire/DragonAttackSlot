package com.soulflame.dragonattackslot;

import java.util.List;

public class SectionsData {

    private final String dragon_slot;
    private final int vanilla_slot;
    private final List<String> lore;

    public SectionsData(String dragon_slot, int vanilla_slot, List<String> lore) {
        this.dragon_slot = dragon_slot;
        this.vanilla_slot = vanilla_slot;
        this.lore = lore;
    }

    public String getDragon_slot() {
        return dragon_slot;
    }

    public int getVanilla_slot() {
        return vanilla_slot;
    }

    public List<String> getLore() {
        return lore;
    }

}