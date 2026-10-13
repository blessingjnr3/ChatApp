import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message();
        m.setMessage("Hi Mike, can you join us for dinner tonight?");
        assertTrue(m.getMessage().length() <= 250);
    }

    @Test
    public void testMessageLengthFail() {
        Message m = new Message();
        String longMsg = "";
        for (int i = 0; i < 260; i++) {
            longMsg = longMsg + "a";
        }
        m.setMessage(longMsg);
        assertTrue(m.getMessage().length() > 250);
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message();
        String result = m.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientFail() {
        Message m = new Message();
        String result = m.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testHash() {
        Message m = new Message();
        m.generateMessageID();
        m.setMessageNum(0);
        m.setMessage("Hi Thanks");
        String h = m.createMessageHash();
        assertTrue(h.endsWith(":0:HITHANKS"));
    }

    @Test
    public void testMessageID() {
        Message m = new Message();
        String id = m.generateMessageID();
        assertTrue(m.checkMessageID());
        assertEquals(10, id.length());
    }
}