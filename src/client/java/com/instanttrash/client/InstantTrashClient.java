package com.instanttrash.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class InstantTrashClient implements ClientModInitializer {
	public static final KeyMapping.Category CUSTOM_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("instant-trash", "main"));

	public static KeyMapping trashSingleKey;
	public static KeyMapping trashAllKey;

	@Override
	public void onInitializeClient() {
		trashSingleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.instant-trash.trash_single",
				InputConstants.Type.KEYSYM,
				InputConstants.UNKNOWN.getValue(),
				CUSTOM_CATEGORY
		));

		trashAllKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.instant-trash.trash_all",
				InputConstants.Type.KEYSYM,
				InputConstants.UNKNOWN.getValue(),
				CUSTOM_CATEGORY
		));
	}
}