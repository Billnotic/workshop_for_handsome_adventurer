package moonfather.workshop_for_handsome_adventurer.integration;

import com.tiviacz.travelersbackpack.capability.AttachmentUtils;
import com.tiviacz.travelersbackpack.capability.ITravelersBackpack;
import com.tiviacz.travelersbackpack.init.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class TravelersBackpack
{
    public static boolean isPresent(Player player)
    {
        Optional<ITravelersBackpack> attachment = AttachmentUtils.getAttachment(player);
        return attachment.isPresent();
    }

    public static int slotCount(Player player)
    {
        Optional<ITravelersBackpack> attachment = AttachmentUtils.getAttachment(player);
        if (attachment.isEmpty())
        {
            return 0;
        }
        return attachment.get().getContainer().getHandler().getSlots();
    }

    public static ItemStack getTabIcon(Player player)
    {
        Optional<ITravelersBackpack> attachment = AttachmentUtils.getAttachment(player);
        if (attachment.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        ItemStack result = attachment.get().getWearable().copy();
        result.remove(ModDataComponents.BACKPACK_CONTAINER.get());
        result.remove(ModDataComponents.CRAFTING_CONTAINER.get());
        result.remove(ModDataComponents.TOOLS_CONTAINER.get());
        return result;
    }

    public static ItemStack getContainerItem(Player player)
    {
        Optional<ITravelersBackpack> attachment = AttachmentUtils.getAttachment(player);
        if (attachment.isEmpty())
        {
            return null;
        }
        return attachment.get().getWearable();
    }

    public static ItemStack getFirst(Player player)
    {
        return ItemStack.EMPTY;
    }

    public static IItemHandler getItems(Player player)
    {
        Optional<ITravelersBackpack> attachment = AttachmentUtils.getAttachment(player);
        if (attachment.isEmpty())
        {
            return null;
        }
        return attachment.get().getContainer().getHandler();
    }

    public static SimpleContainer getContainer(Player player)
    {
        return null;
    }
}
