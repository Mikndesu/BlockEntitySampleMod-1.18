package com.github.mikn.block_entity_sample;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CustomBlock extends Block implements EntityBlock {
    public CustomBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos));
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof CustomBlockEntity) {
            Component name = ((Nameable)blockEntity).getDisplayName();
            return new SimpleMenuProvider((id, inventory, player) -> new CustomBlockContainer(id, inventory, ContainerLevelAccess.create(level, blockPos)), name);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack stack) {
        if(stack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if(blockEntity instanceof CustomBlockEntity) {
                ((CustomBlockEntity)blockEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    public boolean isPathfindable(BlockState blockState, BlockGetter world, BlockPos blockPos, PathComputationType type) {
        return false;
    }

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CustomBlockEntity(blockPos, blockState);
    }
}
