package ch.hearc.SaphirLion.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserMediaTest {

    // ========================================================================================
    // =========================== isNbPublishedGreaterThanLastSeen ===========================
    // ========================================================================================
    @Test
    public void lastSeen_le_published_resultTrue() {
        UserMedia um = new UserMedia();
        um.setLastSeen(0);
        um.setNbPublished(1);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());

        um.setLastSeen(1);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());

        um.setLastSeen(2);
        um.setNbPublished(3);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());
    }

    @Test
    public void lastSeen_gt_published_resultFalse() {
        UserMedia um = new UserMedia();
        um.setLastSeen(2);
        um.setNbPublished(1);
        assertFalse(um.isNbPublishedGreaterThanLastSeen());

        um.setLastSeen(4);
        um.setNbPublished(3);
        assertFalse(um.isNbPublishedGreaterThanLastSeen());
    }


    // ========================================================================================
    // =========================== isNbPublishedGreaterThanLastSeen ===========================
    // ========================================================================================

    @Test
    public void owned_le_published_resultTrue() {
        UserMedia um = new UserMedia();
        um.setNbOwned(0);
        um.setNbPublished(1);
        assertTrue(um.isNbPublishedGreaterThanNbOwned());

        um.setNbOwned(1);
        assertTrue(um.isNbPublishedGreaterThanNbOwned());

        um.setNbOwned(2);
        um.setNbPublished(3);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());
    }

    @Test
    public void owned_gt_published_resultFalse() {
        UserMedia um = new UserMedia();
        um.setNbOwned(2);
        um.setNbPublished(1);
        assertFalse(um.isNbPublishedGreaterThanNbOwned());

        um.setNbOwned(4);
        um.setNbPublished(3);
        assertFalse(um.isNbPublishedGreaterThanNbOwned());
    }
}
