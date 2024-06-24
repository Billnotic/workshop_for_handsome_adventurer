package moonfather.workshop_for_handsome_adventurer.block_entities;

import moonfather.workshop_for_handsome_adventurer.CommonConfig;
import moonfather.workshop_for_handsome_adventurer.initialization.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class PotionShelfBlockEntity extends ToolRackBlockEntity
{
    public PotionShelfBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.POTION_SHELF_BE.get(), pos, state, CAPACITY);
    }

    public PotionShelfBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state)
    {
        super(blockEntityType, pos, state, CAPACITY); //needed this for EveryCompat
    }

    public static final int CAPACITY = 6;
    private final List<Integer> itemCounts = new ArrayList<Integer>(CAPACITY);

//    @Override
//    protected void saveAdditional(CompoundTag compoundTag)
//    {
//        super.saveAdditional(compoundTag);
//        compoundTag.putIntArray("Counts", this.itemCounts);
//    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider)
    {
        super.loadAdditional(tag, lookupProvider);
        int[] array = tag.getIntArray("Counts");
        for (int i = 0; i < array.length; i++) {
            this.itemCounts.set(i, array[i]);
            if (array[i] == 0 && ! this.GetItem(i).isEmpty())
            {
                this.ClearItem(i);
            }
        }
    }

    @Override
    protected CompoundTag saveInternal(CompoundTag compoundTag, HolderLookup.Provider lookupProvider)
    {
        super.saveInternal(compoundTag, lookupProvider);
        compoundTag.putIntArray("Counts", this.itemCounts);
        return compoundTag;
    }

    @Override
    public int getNumberOfItemsInOneRow() {
        return 3;
    }

    //////////////////////////////////////////////////

    public void DropAll()
    {
        this.VerifyCapacity();
        for (int slot = 0; slot < this.getCapacity(); slot++)
        {
            for (int j = 0; j < this.itemCounts.get(slot); j++) {
                ItemStack potion = this.GetItem(slot);
                Block.popResource(this.level, this.getBlockPos(), potion.copy());
            }
            this.ClearItem(slot);
            this.itemCounts.set(slot, 0);
        }
    }



    public ItemStack TakeOutPotion(int slot)
    {
        this.VerifyCapacity();
        if (this.itemCounts.get(slot) == 1) {
            ItemStack result = this.GetItem(slot);
            this.ClearItem(slot);
            this.itemCounts.set(slot, 0);
            this.setChanged();
            return result;
        }
        if (this.itemCounts.get(slot) > 1) {
            ItemStack result = this.GetItem(slot).copy();
            result.setCount(1);
            this.itemCounts.set(slot, this.itemCounts.get(slot) - 1);
            this.setChanged();
            return result;
        }
        return ItemStack.EMPTY;
    }

    public void DepositPotion(int slot, ItemStack itemStack)
    {
        this.VerifyCapacity();
        if (this.itemCounts.get(slot) == 0) {
            ItemStack copy = itemStack.copy();
            copy.setCount(1);
            itemStack.shrink(1);
            this.DepositItem(slot, copy);
            this.itemCounts.set(slot, 1);
        }
        else {
            itemStack.shrink(1);
            this.itemCounts.set(slot, this.itemCounts.get(slot) + 1);
        }
        this.setChanged();
    }


    public void DepositPotionStack(int slot, ItemStack itemStack)
    {
        this.VerifyCapacity();
        int count = Math.min(itemStack.getCount(), this.GetRemainingRoom(slot));
        if (this.itemCounts.get(slot) == 0) {
            ItemStack copy = itemStack.copy();
            copy.setCount(1);
            itemStack.shrink(count);
            this.DepositItem(slot, copy);
            this.itemCounts.set(slot, count);
        }
        else {
            itemStack.shrink(count);
            this.itemCounts.set(slot, this.itemCounts.get(slot) + count);
        }
        this.setChanged();
    }

    public ItemStack TakeOutPotionStack(int slot)
    {
        this.VerifyCapacity();
        int count = Math.min(this.GetItem(slot).getMaxStackSize(), this.itemCounts.get(slot));
        if (this.itemCounts.get(slot) == 0) {
            return ItemStack.EMPTY; // shouldn't be possible
        }
        else if (this.itemCounts.get(slot) == count) {
            ItemStack result = this.GetItem(slot);
            result.setCount(count);
            this.ClearItem(slot);
            this.itemCounts.set(slot, 0);
            this.setChanged();
            return result;
        }
        else { // max stack < what we have
            ItemStack result = this.GetItem(slot).copy();
            result.setCount(count);
            this.itemCounts.set(slot, this.itemCounts.get(slot) - count);
            this.setChanged();
            return result;
        }
    }


    public boolean IsSlotMaxed(int slot)
    {
        this.VerifyCapacity();
        if (this.GetItem(slot).isEmpty()) {
            return false;
        }
        return this.itemCounts.get(slot) >= Math.min(CommonConfig.SlotRoomMaximum.get(), this.GetItem(slot).getMaxStackSize() * CommonConfig.SlotRoomMultiplier.get());
    }

    public Integer GetRemainingRoom(int slot)
    {
        this.VerifyCapacity();
        if (this.itemCounts.get(slot) == 0) {
            return CommonConfig.SlotRoomMaximum.get();
        }
        return Math.min(CommonConfig.SlotRoomMaximum.get(), this.GetItem(slot).getMaxStackSize() * CommonConfig.SlotRoomMultiplier.get()) - this.itemCounts.get(slot);
    }

    public Integer GetRemainingItems(int slot) {
        this.VerifyCapacity();
        return this.itemCounts.get(slot);
    }

    @Override
    protected void VerifyCapacity()
    {
        super.VerifyCapacity();
        for (int i = this.itemCounts.size(); i < this.getCapacity(); i++) { this.itemCounts.add(0); }
    }

}
