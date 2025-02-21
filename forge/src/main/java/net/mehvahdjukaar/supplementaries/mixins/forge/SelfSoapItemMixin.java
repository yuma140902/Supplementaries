package net.mehvahdjukaar.supplementaries.mixins.forge;

import net.mehvahdjukaar.supplementaries.common.items.SoapItem;
import net.mehvahdjukaar.supplementaries.forge.SupplementariesForge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoapItem.class)
public abstract class SelfSoapItemMixin extends Item {

    public SelfSoapItemMixin(Properties arg) {
        super(arg);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == SupplementariesForge.SOAP_CLEAN;
    }
}
