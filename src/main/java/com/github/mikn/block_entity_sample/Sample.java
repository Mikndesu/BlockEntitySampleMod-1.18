package com.github.mikn.block_entity_sample;


import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Sample.MODID)
public class Sample {
    public static final String MODID = "sample";

    public Sample() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockEntityInit.BLOCK_ENTYTIES.register(bus);
        ContainerInit.CONTAINERS.register(bus);
        BlockInit.BLOCKS.register(bus);
        bus.addListener(this::clientSetup);
    }
    
    private void clientSetup(final FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerInit.CUSTOM_BLOCK_CONTAINER.get(), CustomBlockScreen::new);
        });
    }
}
