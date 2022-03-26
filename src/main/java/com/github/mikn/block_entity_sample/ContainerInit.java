package com.github.mikn.block_entity_sample;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Sample.MODID);
    public static final RegistryObject<MenuType<CustomBlockContainer>> CUSTOM_BLOCK_CONTAINER = CONTAINERS.register("custom_block", () -> IForgeMenuType.create((windowId, inv, data) -> new CustomBlockContainer(windowId, inv)));
}