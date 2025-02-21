package net.mehvahdjukaar.supplementaries.mixins;

import net.mehvahdjukaar.supplementaries.common.items.RopeArrowItem;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;


@Mixin(BowItem.class)
public abstract class BowMixin {

    //TODO: use forge event
    @Inject(method = "getAllSupportedProjectiles", at = @At(
            value = "RETURN"),
            cancellable = true)
    public void getAllSupportedProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        if(CommonConfigs.Items.ROPE_ARROW_CROSSBOW.get()){
            var v = cir.getReturnValue();
            cir.setReturnValue((s)->{
                if(s.getItem() instanceof RopeArrowItem) return false;
                return v.test(s);
            });
        }
    }
}
