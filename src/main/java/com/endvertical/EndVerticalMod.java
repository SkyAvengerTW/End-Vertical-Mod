package com.endvertical;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.material.MapColor;

public class EndVerticalMod implements ModInitializer {
    public static final String MOD_ID = "end_vertical";

    public static final Block DARKNESS_STONE = Registry.register(
        BuiltInRegistries.BLOCK, id("darkness_stone"),
        new DropExperienceBlock(UniformInt.of(2, 5),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                .strength(8.0f, 12.0f).requiresCorrectToolForDrops()));

    public static final Item DARKNESS_STONE_ITEM = Registry.register(
        BuiltInRegistries.ITEM, id("darkness_stone"),
        new BlockItem(DARKNESS_STONE, new Item.Properties()));

    public static final Item VOID_ESSENCE = Registry.register(
        BuiltInRegistries.ITEM, id("void_essence"),
        new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final Item VOID_CORE = Registry.register(
        BuiltInRegistries.ITEM, id("void_core"),
        new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final EntityType<VoidWalker> VOID_WALKER = Registry.register(
        BuiltInRegistries.ENTITY_TYPE, id("void_walker"),
        EntityType.Builder.of(VoidWalker::new, MobCategory.MONSTER)
            .sized(0.8f, 2.6f).build(id("void_walker").toString()));

    @Override public void onInitialize() {
        // Core registration is complete here. World-generation JSON/datapack assets can be added next.
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getAllLevels().forEach(level -> {
                for (var e : level.getEntities().getAll()) {
                    if (e instanceof VoidWalker vw) vw.serverTick();
                }
            });
        });
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static AttributeSupplier.Builder voidWalkerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 160.0)
            .add(Attributes.ATTACK_DAMAGE, 15.0)
            .add(Attributes.MOVEMENT_SPEED, 0.15)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.25);
    }
}
