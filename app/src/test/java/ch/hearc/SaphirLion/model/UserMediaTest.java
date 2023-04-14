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
        Media m = new Media();
        m.setNbPublished(1);

        UserMedia um = new UserMedia();
        um.setLastSeen(0);
        um.setMedia(m);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());

        um.setLastSeen(1);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());

        m.setNbPublished(3);

        um.setLastSeen(2);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());
    }

    @Test
    public void lastSeen_gt_published_resultFalse() {
        Media m = new Media();
        m.setNbPublished(1);

        UserMedia um = new UserMedia();
        um.setLastSeen(2);
        um.setMedia(m);
        assertFalse(um.isNbPublishedGreaterThanLastSeen());

        m.setNbPublished(3);

        um.setLastSeen(4);
        assertFalse(um.isNbPublishedGreaterThanLastSeen());
    }


    // ========================================================================================
    // =========================== isNbPublishedGreaterThanLastSeen ===========================
    // ========================================================================================

    @Test
    public void owned_le_published_resultTrue() {
        Media m = new Media();
        m.setNbPublished(1);

        UserMedia um = new UserMedia();
        um.setNbOwned(0);
        um.setMedia(m);
        assertTrue(um.isNbPublishedGreaterThanNbOwned());

        um.setNbOwned(1);
        assertTrue(um.isNbPublishedGreaterThanNbOwned());

        m.setNbPublished(3);
        
        um.setNbOwned(2);
        assertTrue(um.isNbPublishedGreaterThanLastSeen());
    }

    @Test
    public void owned_gt_published_resultFalse() {
        Media m = new Media();
        m.setNbPublished(1);

        UserMedia um = new UserMedia();
        um.setNbOwned(2);
        um.setMedia(m);
        assertFalse(um.isNbPublishedGreaterThanNbOwned());

        m.setNbPublished(3);

        um.setNbOwned(4);
        assertFalse(um.isNbPublishedGreaterThanNbOwned());
    }
}
