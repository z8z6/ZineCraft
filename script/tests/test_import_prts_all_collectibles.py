from __future__ import annotations

import sys
import unittest
from pathlib import Path


SCRIPT_DIRECTORY = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(SCRIPT_DIRECTORY))
import import_prts_all_collectibles as importer  # noqa: E402


class EffectAdaptationTest(unittest.TestCase):
    def test_target_life_temporary_life_and_shields_reuse_max_health(self) -> None:
        adaptation = importer.adapt_effect("目标生命上限-2，护盾值+8")

        self.assertEqual("implemented", adaptation.status)
        self.assertIn("addMaxHealth(-2)", adaptation.java)
        self.assertIn("addMaxHealth(8)", adaptation.java)

    def test_condition_bound_resources_are_not_applied_unconditionally(self) -> None:
        adaptation = importer.adapt_effect(
            "投出花钱时，希望+1；投出衡钱时，源石锭+4；投出厉钱时，目标生命值+1"
        )

        self.assertEqual("unimplemented", adaptation.status)
        self.assertTrue(adaptation.java.startswith("sourceRule("))

    def test_profession_continuation_uses_the_same_skill_profession_field(self) -> None:
        adaptation = importer.adapt_effect("所有【先锋】干员的攻击力+50%，防御力+50%")

        self.assertEqual("implemented", adaptation.status)
        self.assertEqual(2, adaptation.java.count("SkillProfession.VANGUARD"))

    def test_enemy_damage_taken_uses_the_independent_damage_fields(self) -> None:
        adaptation = importer.adapt_effect("所有敌方单位受到的物理与法术伤害+30%")

        self.assertEqual("implemented", adaptation.status)
        self.assertIn("addEnemyPhysicalDamageTakenBonus(0.3)", adaptation.java)
        self.assertIn("addEnemyMagicDamageTakenBonus(0.3)", adaptation.java)

    def test_registered_fields_are_actual_implemented_effects(self) -> None:
        adaptation = importer.adapt_effect("目标生命+2，希望+1")

        self.assertEqual("implemented", adaptation.status)
        self.assertTrue(adaptation.java.startswith("registeredRule("))
        self.assertIn("addMaxHealth(2)", adaptation.java)
        self.assertIn("hope(1)", adaptation.java)


if __name__ == "__main__":
    unittest.main()
