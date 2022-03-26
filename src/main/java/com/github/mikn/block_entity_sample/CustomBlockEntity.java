package com.github.mikn.block_entity_sample;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBlockEntity extends BlockEntity implements Nameable {
    private Component customName;
    public CustomBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityInit.CUSTOM_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public CompoundTag save(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if(this.hasCustomName()) {
            compoundTag.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
        return compoundTag;
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(tag.getString("Customname"));
        }
    }

    public Component getName() {
        return this.customName != null ? this.customName: new TranslatableComponent("");
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    public Component getCustomName() {
        return this.customName;
    }
}
