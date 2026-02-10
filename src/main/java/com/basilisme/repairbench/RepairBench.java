package com.basilisme.repairbench;
import com.basilisme.repairbench.commands.RepairCommand;
import com.basilisme.repairbench.pages.FreeRepairPageSupplier;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class RepairBench extends JavaPlugin {

    public RepairBench(@Nonnull JavaPluginInit init) {
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
