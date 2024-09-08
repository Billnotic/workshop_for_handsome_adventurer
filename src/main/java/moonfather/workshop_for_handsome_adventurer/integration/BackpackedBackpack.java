package moonfather.workshop_for_handsome_adventurer.integration;

import com.mrcrayfish.backpacked.inventory.BackpackInventory;
import com.mrcrayfish.backpacked.inventory.BackpackedInventoryAccess;
import com.mrcrayfish.backpacked.item.BackpackItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class BackpackedBackpack
{
    private BackpackedBackpack()
    {
    }
    private int slotCountReal, slotCountVisible, slotRows, slotColumns;
    private ItemStack tabIcon;

    ////////////////////////////////////////////

    public static Object getToken(ItemStack storageItem)
    {
        if (! (storageItem.getItem() instanceof BackpackItem bi))
        {
            return null;
        }
        BackpackedBackpack result = new BackpackedBackpack();
        result.slotRows = bi.getRowCount();
        result.slotColumns = bi.getColumnCount();
        result.slotCountReal = bi.getColumnCount() * bi.getRowCount();
        result.slotCountVisible = result.slotCountReal <= 27 ? 27 : 54;
        result.tabIcon = storageItem.copy();
        result.tabIcon.getOrCreateTag().remove("Items");
        return result;
    }

    //////////////////////////////////////

    public static boolean isPresent(Object token)
    {
        return token != null;
    }

    public static int slotCount(Object token)
    {
        return ((BackpackedBackpack) token).slotCountReal;
    }

    public static ItemStack getTabIcon(Object token)
    {
        return ((BackpackedBackpack) token).tabIcon;
    }

    public static ItemStack getFirst(Object token)
    {
        return ItemStack.EMPTY;
    }

    public static Container getContainer(Object token, Player player, ItemStack item)
    {
        return ((BackpackedInventoryAccess) player).getBackpackedInventory();
    }
}
