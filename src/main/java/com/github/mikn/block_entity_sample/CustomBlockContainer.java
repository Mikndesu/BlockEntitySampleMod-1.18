package com.github.mikn.block_entity_sample;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CustomBlockContainer extends AbstractContainerMenu {
    private final Container outputInventory = new ResultContainer();
    private final Container inputInventory = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            CustomBlockContainer.this.slotsChanged(this);
        }
    };

    ContainerLevelAccess levelPosCallale;

    public CustomBlockContainer(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CustomBlockContainer(int id, Inventory inventory, ContainerLevelAccess levelPosCallable) {
        super(ContainerInit.CUSTOM_BLOCK_CONTAINER.get(), id);
        this.levelPosCallale = levelPosCallable;
        this.addSlot(new Slot(this.inputInventory, 0, 15, 25) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean mayPlace(ItemStack stack) {
                return Items.WHEAT_SEEDS.equals(stack.getItem());
            }

            /**
             * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
             * case of armor slots)
             */
            public int getMaxStackSize() {
                return 64;
            }
        });
        this.addSlot(new Slot(this.outputInventory, 2, 145, 25){
            public boolean mayPlace(ItemStack ItemStack) {
                return false;
            }
            public int getMaxStackSize() {
                return 64;
            }
            public void onTake(Player player, ItemStack itemStack) {
                CustomBlockContainer.this.inputInventory.setItem(0, ItemStack.EMPTY);
            }
        });
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 62 + i * 18));
            }
        }
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 120));
        }
    }

    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if(inventory == this.inputInventory) {
            this.updateRecipeOutput();
        }
    }

    private void updateRecipeOutput() {
        ItemStack seed = this.inputInventory.getItem(0);
        int numbers = seed.getCount();
        boolean ready = !seed.isEmpty();
        if (!ready) {
            this.outputInventory.setItem(0, ItemStack.EMPTY);
        } else {
            ItemStack itemStack = new ItemStack(Items.MELON_SEEDS);
            itemStack.setCount(numbers);
            this.outputInventory.setItem(0, itemStack);
        }
        this.broadcastChanges();
    }

    public void removed(Player player) {
        super.removed(player);
        this.levelPosCallale.execute((p_217004_2_, p_217004_3_) -> this.clearContainer(player, this.inputInventory));
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() == Items.BOOK) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copy();
                itemstack2.setCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).set(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public boolean stillValid(Player player) {
        return stillValid(this.levelPosCallale, player, BlockInit.CUSTOM_BLOCK.get());
    }

}
