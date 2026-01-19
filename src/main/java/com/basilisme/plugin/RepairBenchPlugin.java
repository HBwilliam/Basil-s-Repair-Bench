package com.basilisme.plugin;
import com.basilisme.plugin.commands.RepairCommand;
import com.basilisme.plugin.pages.FreeRepairPageSupplier;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class RepairBenchPlugin extends JavaPlugin {

    public RepairBenchPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        getCommandRegistry().registerCommand(new RepairCommand());

        OpenCustomUIInteraction.PAGE_CODEC.register(
                "FreeRepair",
                FreeRepairPageSupplier.class,
                FreeRepairPageSupplier.CODEC
        );
    }
}
