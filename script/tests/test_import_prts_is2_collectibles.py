from __future__ import annotations

import sys
import tempfile
import unittest
from pathlib import Path


SCRIPT_DIRECTORY = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(SCRIPT_DIRECTORY))
import import_prts_is2_collectibles as importer  # noqa: E402


class PublishTransactionTest(unittest.TestCase):
    def test_rolls_back_replaced_and_new_targets_after_injected_failure(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            repository = Path(temporary) / "repository"
            staging = Path(temporary) / "staging"
            repository.mkdir()
            staging.mkdir()

            existing = repository / "existing.json"
            created = repository / "created.json"
            staged_existing = staging / "existing.json"
            staged_created = staging / "created.json"
            existing.write_text("old", encoding="utf-8")
            staged_existing.write_text("new existing", encoding="utf-8")
            staged_created.write_text("new created", encoding="utf-8")

            with self.assertRaisesRegex(RuntimeError, "injected publish failure"):
                importer.publish_staged(
                    staging,
                    [(staged_existing, existing), (staged_created, created)],
                    allowed_root=repository,
                    failure_after=2,
                )

            self.assertEqual("old", existing.read_text(encoding="utf-8"))
            self.assertFalse(created.exists())


if __name__ == "__main__":
    unittest.main()
