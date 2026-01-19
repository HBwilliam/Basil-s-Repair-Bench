package com.basilisme.plugin.commands;

import com.basilisme.plugin.pages.FreeItemRepairPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class RepairCommand extends AbstractPlayerCommand {

    public RepairCommand() {
        super("repair", "Opens Free repair UI", false);
    }

    @Override
    protected void execute(@Nonnull CommandContext ctx,
                           @Nonnull Store<EntityStore> store,
                           @Nonnull Ref<EntityStore> ref,
                           @Nonnull PlayerRef playerRef,
                           @Nonnull World world) {

        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        // Get full inventory (armor + hotbar + utility) [file:123]
        ItemContainer container = player.getInventory().getCombinedArmorHotbarUtilityStorage();

        // No penalty; heldItemContext not needed in your free implementation
        FreeItemRepairPage page = new FreeItemRepairPage(playerRef, container, 0.0, null);  // matches ctor in file:162

        player.getPageManager().openCustomPage(ref, store, page);
    }
}
