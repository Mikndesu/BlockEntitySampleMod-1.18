package com.github.mikn.block_entity_sample;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sample.MODID);
    public static final RegistryObject<Block> CUSTOM_BLOCK = BLOCKS.register("custom_block", () -> new CustomBlock(BlockBehaviour.Properties.of(Material.WOOD)));
}
