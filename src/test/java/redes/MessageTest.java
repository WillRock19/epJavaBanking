package redes;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static redes.Action.TRANSFER;

public class MessageTest {

    @Test
    public void should_return_serialized_object() {
        Message message = new Message();
        message.setAction(TRANSFER);
        message.setUser(new User("conta-louca", "senha1"));
        message.setValue(10);
        String json = message.toJson();
        String expectedJson = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
        assertThat(json, is(expectedJson));
    }

    @Test
    public void should_generate_correct_class_from_string(){
        Message expectedMessage = new Message();
        expectedMessage.setAction(TRANSFER);
        expectedMessage.setUser(new User("conta-louca", "senha1"));
        expectedMessage.setValue(10);
        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\"},\"value\":10}";
        Message message = Message.fromJSON(json);
        assertThat(expectedMessage, is(message));
    }


}
