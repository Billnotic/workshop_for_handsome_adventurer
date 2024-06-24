package moonfather.workshop_for_handsome_adventurer.block_entities;

import moonfather.workshop_for_handsome_adventurer.initialization.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

public class DualTableBlockEntity extends SimpleTableBlockEntity
{
    public DualTableBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.DUAL_TABLE_BE.get(), pos, state);
        this.setCapacity(9 /*crafting*/ + 4 /*customization, was forced to put max here*/ + 9 /*crafting*/);
    }

    public DualTableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state)  // needed this for EveryCompat
    {
        super(blockEntityType, pos, state);
        this.setCapacity(9 /*crafting*/ + 4 /*customization, was forced to put max here*/ + 9 /*crafting*/);
    }

    //////////// integer data //////////////

    private int data0 = 0;
    public void StoreIntegerData(int index, int value) {
        assert index == 0;
        this.data0 = value;
    }

    public int GetIntegerData(int index) {
        assert index == 0;
        return this.data0;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider)
    {
        super.loadAdditional(tag, lookupProvider);
        this.data0 = tag.getInt("int0");
    }

    @Override
    protected CompoundTag saveInternal(CompoundTag tag, HolderLookup.Provider lookupProvider)
    {
        tag.putInt("int0", this.data0);
        return super.saveInternal(tag, lookupProvider);
    }

    ///////////////////////////////

    public Direction getDirection()
    {
        if (this.cachedDirection == null)
        {
            this.cachedDirection = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        }
        return this.cachedDirection;
    }
    private Direction cachedDirection;

    // run TESR even if main block isn't visible.
    public AABB getRenderBoundingBox()
    {
        if (this.cachedRenderingAABB == null && ! this.worldPosition.equals(BlockPos.ZERO))
        {
            this.cachedRenderingAABB = new AABB(this.worldPosition.getX() - 1, this.worldPosition.getY() + 0, this.worldPosition.getZ() - 1,
                    this.worldPosition.getX() + 2, this.worldPosition.getY() + 1, this.worldPosition.getZ() + 2);
            // 3x3 bounding box. i should make a 2x1 or 1x2, but it's complicated and i don't think it would make any impact on game speed.
        }
        return this.cachedRenderingAABB;
    }
    private AABB cachedRenderingAABB = null;
}
