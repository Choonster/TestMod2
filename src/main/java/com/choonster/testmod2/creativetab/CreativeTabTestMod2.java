package com.choonster.testmod2.creativetab;

import com.choonster.testmod2.init.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CreativeTabTestMod2 extends CreativeTabs {

	private ItemSorter itemSorter = new ItemSorter();

	public CreativeTabTestMod2() {
		super("testmod2");
	}

	@Override
	public Item getTabIconItem() {
		return ItemRegistry.candyButton;
	}

	@Override
	public void displayAllReleventItems(List items) {
		super.displayAllReleventItems(items);

		ItemStack enchant = new ItemStack(Items.record_11);
		enchant.addEnchantment(Enchantment.unbreaking, 25);
		items.add(enchant);

		// Sort the item list using the ItemSorter instance
		Collections.sort(items, itemSorter);
	}
}