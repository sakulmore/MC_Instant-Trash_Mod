package com.instanttrash;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InstantTrash implements ModInitializer {

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.serverboundPlay().register(TrashPayload.TYPE, TrashPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(TrashPayload.TYPE, (payload, context) -> {
			context.server().execute(() -> {
				ServerPlayer player = context.player();
				AbstractContainerMenu menu = player.containerMenu;

				if (menu.containerId == payload.syncId()) {
					Slot slot = menu.getSlot(payload.slotIndex());

					if (slot != null && slot.hasItem() && slot.mayPickup(player)) {
						if (payload.matchAll()) {
							ItemStack stackToRemove = slot.getItem();

							for (Slot s : menu.slots) {
								if (s.hasItem() && ItemStack.isSameItemSameComponents(s.getItem(), stackToRemove) && s.mayPickup(player)) {
									s.set(ItemStack.EMPTY);
								}
							}
						} else {
							slot.set(ItemStack.EMPTY);
						}
						menu.broadcastChanges();
					}
				}
			});
		});
	}
}