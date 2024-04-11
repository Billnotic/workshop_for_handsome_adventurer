package moonfather.workshop_for_handsome_adventurer.initialization;

import moonfather.workshop_for_handsome_adventurer.Constants;
import moonfather.workshop_for_handsome_adventurer.block_entities.renderers.DualTableTESR;
import moonfather.workshop_for_handsome_adventurer.block_entities.renderers.SimpleTableTESR;
import moonfather.workshop_for_handsome_adventurer.block_entities.renderers.ToolRackTESR;
import moonfather.workshop_for_handsome_adventurer.block_entities.screens.DualTableCraftingScreen;
import moonfather.workshop_for_handsome_adventurer.block_entities.screens.SimpleTableCraftingScreen;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.FinderEvents;
import moonfather.workshop_for_handsome_adventurer.integration.PolymorphAccessorClient;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup
{
	public static void Initialize(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(Registration.CRAFTING_SINGLE_MENU_TYPE.get(), SimpleTableCraftingScreen::new));
		event.enqueueWork(() -> MenuScreens.register(Registration.CRAFTING_DUAL_MENU_TYPE.get(), DualTableCraftingScreen::new));
		//ItemBlockRenderTypes.setRenderLayer(Registration.SIMPLE_TABLE.get(), RenderType.cutoutMipped()); // apparently unnecessary.
		//BlockEntityRenderers.register(Registration.TOOL_RACK.get(), ToolRackTESR::new); //maybe this would be ok too

		if (ModList.get().isLoaded("polymorph"))
		{
			PolymorphAccessorClient.register();
		}
	}



	public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(Registration.TOOL_RACK_BE.get(), ToolRackTESR::new);
		event.registerBlockEntityRenderer(Registration.POTION_SHELF_BE.get(), ToolRackTESR::new);
		event.registerBlockEntityRenderer(Registration.DUAL_TABLE_BE.get(), DualTableTESR::new);
		event.registerBlockEntityRenderer(Registration.SIMPLE_TABLE_BE.get(), SimpleTableTESR::new);
	}

	public static void StitchTextures(TextureStitchEvent.Pre event)
	{
		if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
			event.addSprite(new ResourceLocation(Constants.MODID, "gui/c_slot"));
			event.addSprite(new ResourceLocation(Constants.MODID, "gui/x_slot"));
		}
	}

	public static void AddClientPack(final AddPackFindersEvent event)
	{
		FinderEvents.addClientPack(event);
	}
}
