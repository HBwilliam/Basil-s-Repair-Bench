package com.basilisme.plugin.pages;

import com.basilisme.plugin.interactions.FreeRepairItemInteraction;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public class FreeItemRepairElement extends ChoiceElement {
    protected ItemStack itemStack;

    public FreeItemRepairElement(ItemStack itemStack, FreeRepairItemInteraction interaction) {
        this.itemStack = itemStack;
        this.interactions = new ChoiceInteraction[]{interaction};
    }

    public void addButton(@Nonnull UICommandBuilder commandBuilder, UIEventBuilder eventBuilder, String selector, PlayerRef playerRef) {
        int durabilityPercentage = (int)Math.round(this.itemStack.getDurability() / this.itemStack.getMaxDurability() * (double)100.0F);
        commandBuilder.append("#ElementList", "Pages/ItemRepairElement.ui");
        commandBuilder.set(selector + " #Icon.ItemId", this.itemStack.getItemId().toString());
        commandBuilder.set(selector + " #Name.TextSpans", Message.translation(this.itemStack.getItem().getTranslationKey()));
        if (this.itemStack.getMaxDurability() != this.itemStack.getItem().getMaxDurability() && durabilityPercentage == 100.0F) {
            commandBuilder.set(selector + " #Durability.Text", "Lost Durability");
        } else {
            commandBuilder.set(selector + " #Durability.Text", durabilityPercentage + "%");
        }
    }
}
