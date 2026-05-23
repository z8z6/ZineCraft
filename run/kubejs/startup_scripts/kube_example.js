StartupEvents.registry('item', event => {

    // 1. 普通物品
    event.create('zinecraft:magic_dust')
        .displayName('魔法粉尘')
        .glow(true); // ✅ 正确：堆叠 64
    
    // 2. 食物（1.21 新版写法）
    event.create('zinecraft:magic_apple')
        .displayName('魔法苹果')
        .food(food => {
            food.nutrition(8)        // 饱食度
            food.saturation(12.0)    // 饱和度
            food.alwaysEdible(true)  // 随时可吃
        });

});