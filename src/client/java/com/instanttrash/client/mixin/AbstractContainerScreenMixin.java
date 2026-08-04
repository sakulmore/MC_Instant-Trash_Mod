package com.instanttrash.client.mixin;

import com.instanttrash.TrashPayload;
import com.instanttrash.client.InstantTrashClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Shadow protected Slot hoveredSlot;
    @Shadow @Final protected AbstractContainerMenu menu;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            boolean isSingle = !InstantTrashClient.trashSingleKey.isUnbound() && InstantTrashClient.trashSingleKey.matches(event);
            boolean isAll = !InstantTrashClient.trashAllKey.isUnbound() && InstantTrashClient.trashAllKey.matches(event);

            if (isSingle || isAll) {
                ClientPlayNetworking.send(new TrashPayload(this.menu.containerId, this.hoveredSlot.index, isAll));
                cir.setReturnValue(true);
            }
        }
    }
}