package moonfather.workshop_for_handsome_adventurer.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BlockItemEx extends BlockItem
{
	public BlockItemEx(Block block, Properties properties)
	{
		super(block, properties);
	}


	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
	{
		return 300;
	}
}
