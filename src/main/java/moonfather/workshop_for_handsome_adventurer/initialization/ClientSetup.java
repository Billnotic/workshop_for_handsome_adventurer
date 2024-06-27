package moonfather.workshop_for_handsome_adventurer.initialization;

import moonfather.workshop_for_handsome_adventurer.block_entities.screens.DualTableCraftingScreen;
import moonfather.workshop_for_handsome_adventurer.block_entities.screens.SimpleTableCraftingScreen;
import moonfather.workshop_for_handsome_adventurer.block_entities.renderers.*;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.FinderEvents;
import moonfather.workshop_for_handsome_adventurer.integration.PolymorphAccessorClient;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{
	@SubscribeEvent
	public static void Initialize(FMLClientSetupEvent event)
	{
		//ItemBlockRenderTypes.setRenderLayer(Registration.SIMPLE_TABLE.get(), RenderType.cutoutMipped()); // apparently unnecessary.
		//BlockEntityRenderers.register(Registration.TOOL_RACK.get(), ToolRackTESR::new); //maybe this would be ok too

		if (ModList.get().isLoaded("polymorph"))
		{
			PolymorphAccessorClient.register();
		}
	}



	@SubscribeEvent
	public static void RegisterScreens(RegisterMenuScreensEvent event)
	{
		event.register(Registration.CRAFTING_SINGLE_MENU_TYPE.get(), SimpleTableCraftingScreen::new);
		event.register(Registration.CRAFTING_DUAL_MENU_TYPE.get(), DualTableCraftingScreen::new);
	}



	@SubscribeEvent
	public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(Registration.TOOL_RACK_BE.get(), ToolRackTESR::new);
		event.registerBlockEntityRenderer(Registration.POTION_SHELF_BE.get(), ToolRackTESR::new);
		event.registerBlockEntityRenderer(Registration.DUAL_TABLE_BE.get(), DualTableTESR::new);
		event.registerBlockEntityRenderer(Registration.SIMPLE_TABLE_BE.get(), SimpleTableTESR::new);
	}

	// two slot textures are in minecraft's block atlas. i wanted to add my own, but it's way too much trouble.



	@SubscribeEvent
	public static void AddClientPack(final AddPackFindersEvent event)
	{
		FinderEvents.addClientPack(event);
	}
}
