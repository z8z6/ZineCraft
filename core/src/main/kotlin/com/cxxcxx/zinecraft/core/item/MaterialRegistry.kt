package com.cxxcxx.zinecraft.core.item

import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block


class MaterialRegistry : Tier {
    var uses: Int = 1
    var speed: Float = 1.0F
    var attackDamageBonus: Float = 1.0F
    var incorrectBlocksForDrops: TagKey<Block?> = BlockTags.INCORRECT_FOR_WOODEN_TOOL
    var enchantmentValue: Int=1
    var repairIngredient: Ingredient?= Ingredient.EMPTY

    fun setUses(uses: Int): MaterialRegistry {
        this.uses = uses
        return this
    }

    override fun getUses(): Int {
        return this.uses
    }

    fun setSpeed(speed: Float): MaterialRegistry {
        this.speed = speed
        return this
    }

    override fun getSpeed(): Float {
        return this.speed
    }

    fun setAttackDamageBonus(attackDamageBonus: Float): MaterialRegistry {
        this.attackDamageBonus = attackDamageBonus
        return this
    }

    override fun getAttackDamageBonus(): Float {
        return this.attackDamageBonus
    }

    fun setIncorrectBlocksForDrops(incorrectBlocksForDrops: TagKey<Block?>): MaterialRegistry {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops
        return this
    }

    override fun getIncorrectBlocksForDrops(): TagKey<Block?> {
        return this.incorrectBlocksForDrops
    }

    fun setEnchantmentValue(enchantmentValue: Int): MaterialRegistry {
        this.enchantmentValue = enchantmentValue
        return this
    }

    override fun getEnchantmentValue(): Int {
        return this.enchantmentValue
    }

    fun setRepairIngredient(repairIngredient: Ingredient?): MaterialRegistry{
        this.repairIngredient=repairIngredient
        return this
    }

    override fun getRepairIngredient(): Ingredient? {
        return this.repairIngredient
    }

    companion object MaterialCreate{
        fun init(){
            val GUIDITE=MaterialRegistry
        }
    }
}