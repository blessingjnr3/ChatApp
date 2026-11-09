

import quikchat.Message;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // This runs before every test to clear the array so tests don't interfere with each other
    @Before
    public void setUp() {
        Message.getStoredMessages().clear();
    }

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message();
        m.setMessage("Hi Mike, can you join us for dinner tonight?");
        assertTrue(m.getMessage().length() <= 250);
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message();
        String result = m.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testLongestMessage() {
        Message m1 = new Message();
        m1.setMessage("Short message");
        m1.SentMessage("3");

        Message m2 = new Message();
        m2.setMessage("Where are you? You are late! I have asked you to be on time.");
        m2.SentMessage("3");

        Message longest = Message.getStoredMessages().get(0);
        for (Message m : Message.getStoredMessages()) {
            if (m.getMessage().length() > longest.getMessage().length()) {
                longest = m;
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessage());
    }

    @Test
    public void testSearchByRecipient() {
        Message m1 = new Message();
        m1.setRecipient("+27838884567");
        m1.setMessage("Message 1");
        m1.SentMessage("3");

        Message m2 = new Message();
        m2.setRecipient("+27838884567");
        m2.setMessage("Message 2");
        m2.SentMessage("3");

        int count = 0;
        for (Message m : Message.getStoredMessages()) {
            if (m.getRecipient().equals("+27838884567")) {
                count++;
            }
        }
        assertEquals(2, count);
    }
}