package moonfather.workshop_for_handsome_adventurer.integration;

import com.mrcrayfish.backpacked.inventory.BackpackedInventoryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BackpackedBackpack
{
    private BackpackedBackpack()
    {
    }

    //////////////////////////////////////

    public static boolean isPresent(Player player)
    {
        return player instanceof BackpackedInventoryAccess access && access.getBackpackedInventory() != null;
    }

    public static int slotCount(Player player)
    {
        return player instanceof BackpackedInventoryAccess access ? access.getBackpackedInventory().getContainerSize() : 0;
    }

    public static ItemStack getTabIcon(Player player)
    {
        ItemStack result = getContainerItem(player).copy();
        result.getOrCreateTag().remove("Items");
        return result;
    }

    public static ItemStack getContainerItem(Player player)
    {
        return player instanceof BackpackedInventoryAccess access ? access.getBackpackedInventory().getBackpackStack() : Items.DEAD_BUSH.getDefaultInstance();
    }

    public static ItemStack getFirst(Player player)
    {
        return ItemStack.EMPTY; // can obtain but i don't want to
    }

    public static Container getContainer(Player player)
    {
        return ((BackpackedInventoryAccess) player).getBackpackedInventory();
    }
}