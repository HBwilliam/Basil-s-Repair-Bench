package com.basilisme.repairbench.interactions;

import com.basilisme.repairbench.pages.FreeItemRepairPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.MathUtil;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
import com.hypixel.hytale.server.core.inventory.ItemContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TempAssetIdUtil;

import javax.annotation.Nonnull;

public class FreeRepairItemInteraction extends ChoiceInteraction {
    protected final ItemContext itemContext;
    protected final double repairPenalty;

    public FreeRepairItemInteraction(ItemContext itemContext, double repairPenalty) {
        this.itemContext = itemContext;
        this.repairPenalty = repairPenalty;
    }

    @Override
    public void run(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
        Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
        assert playerComponent != null;
        PageManager pageManager = playerComponent.getPageManager();
        ItemStack itemStack = this.itemContext.getItemStack();
        double itemStackDurability = itemStack.getDurability();
        double itemStackMaxDurability = itemStack.getItem().getMaxDurability();
        double ratioAmountRepaired = (double)1.0F - itemStackDurability / itemStackMaxDurability;
        double newMaxDurability = (double) MathUtil.floor(itemStackMaxDurability - itemStack.getItem().getMaxDurability() * this.repairPenalty * ratioAmountRepaired);

        if (itemStackDurability >= newMaxDurability) {
            playerRef.sendMessage(Message.translation("server.general.repair.penaltyTooBig").color("#ff5555"));
            pageManager.openCustomPage(ref, store, new FreeItemRepairPage(playerRef, playerComponent.getInventory().getCombinedArmorHotbarUtilityStorage(), 0.0, null));
        } else {
            if (newMaxDurability <= (double) 10.0F) {
                newMaxDurability = (double) 10.0F;
                playerRef.sendMessage(Message.translation("server.general.repair.tooLowDurability").color("#ff5555"));
            }
                ItemStack newItemStack = itemStack.withRestoredDurability(newMaxDurability);
                ItemStackSlotTransaction replaceTransaction = this.itemContext.getContainer().replaceItemStackInSlot(this.itemContext.getSlot(), itemStack, newItemStack);
                if (!replaceTransaction.succeeded()) {
                    pageManager.openCustomPage(ref, store, new FreeItemRepairPage(playerRef, playerComponent.getInventory().getCombinedArmorHotbarUtilityStorage(), 0.0, null));
                } else {
                    Message newItemStackMessage = Message.translation(newItemStack.getItem().getTranslationKey());
                    playerRef.sendMessage(Message.translation("server.general.repair.successful").param("itemName", newItemStackMessage));
                    pageManager.openCustomPage(ref, store, new FreeItemRepairPage(playerRef, playerComponent.getInventory().getCombinedArmorHotbarUtilityStorage(), 0.0, null));
                    SoundUtil.playSoundEvent2d(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Item_Repair"), SoundCategory.UI, store);
                }
            }
        }
    }
