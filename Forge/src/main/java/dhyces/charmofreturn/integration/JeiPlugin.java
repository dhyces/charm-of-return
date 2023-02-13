package dhyces.charmofreturn.integration;

import dhyces.charmofreturn.Register;
import dhyces.charmofreturn.CharmOfReturn;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = CharmOfReturn.id("ringofreturn");
    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addItemStackInfo(new ItemStack(Register.CHARM.get()), Component.translatable("jei_info.ringofreturn.ring_of_return"));
    }
}
