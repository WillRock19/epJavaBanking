package redes;

import org.junit.Test;

import redes.json.Message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static redes.Action.TRANSFER;

public class MessageTest {

    @Test
    public void should_return_serialized_object() {
        ActionMessage message = new ActionMessage();
        message.setAction(TRANSFER);
        message.setUser(new User("conta-louca", "senha1"));
        message.setValue(10);
        String json = message.toJson();
        String expectedJson = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\",\"password\":\"senha1\"},\"value\":10}";
        assertThat(json, is(expectedJson));
    }

    @Test
    public void should_generate_correct_class_from_string(){
        ActionMessage expectedMessage = new ActionMessage();
        expectedMessage.setAction(TRANSFER);
        expectedMessage.setUser(new User("conta-louca", "senha1"));
        expectedMessage.setValue(10);
        String json = "{\"action\":\"TRANSFER\",\"user\":{\"account\":\"conta-louca\",\"password\":\"senha1\"},\"value\":10}";
        ActionMessage message = (ActionMessage) Message.fromJSON(json);
        assertThat(expectedMessage, is(message));
    }


}
