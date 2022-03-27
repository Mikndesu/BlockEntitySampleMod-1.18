package com.github.mikn.block_entity_sample;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTYTIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Sample.MODID);
    public static final RegistryObject<BlockEntityType<CustomBlockEntity>> CUSTOM_BLOCK_ENTITY = BLOCK_ENTYTIES.register("custom_block_entity", () -> BlockEntityType.Builder.of(CustomBlockEntity::new, BlockInit.CUSTOM_BLOCK.get()).build(null));
}
