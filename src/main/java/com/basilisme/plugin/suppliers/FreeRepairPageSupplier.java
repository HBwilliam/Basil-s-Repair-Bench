package com.basilisme.plugin.pages;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class FreeRepairPageSupplier implements OpenCustomUIInteraction.CustomPageSupplier {

    // Optional config: you can ignore or expose repairPenalty later
    public static final BuilderCodec<FreeRepairPageSupplier> CODEC =
            BuilderCodec.builder(FreeRepairPageSupplier.class, FreeRepairPageSupplier::new)
                    .build();

    @Override
    public CustomUIPage tryCreate(@Nonnull Ref<EntityStore> ref,
                                  @Nonnull ComponentAccessor<EntityStore> accessor,
                                  @Nonnull PlayerRef playerRef,
                                  @Nonnull InteractionContext context) {

        Player player = (Player) accessor.getComponent(ref, Player.getComponentType());
        if (player == null) return null;

        // All equipped + hotbar + utility slots
        ItemContainer container = player.getInventory().getCombinedArmorHotbarUtilityStorage();

        return new FreeItemRepairPage(playerRef, container, 0.0, null);
    }
}
