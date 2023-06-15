package control.tower.address.service.command.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveAddressCommandTest {

    private final String ADDRESS_ID = "addressId";

    @Test
    void shouldBuildValidRemoveAddressCommand() {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .build();

        assertDoesNotThrow(removeAddressCommand::validate);

        assertEquals(removeAddressCommand.getAddressId(), ADDRESS_ID);
    }

    @Test
    void shouldNotBuildValidRemoveAddressCommandWhenAddressIdIsNull() {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(null)
                .build();

        assertThrows(IllegalArgumentException.class, removeAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidRemoveAddressCommandWhenAddressIdIsEmpty() {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId("")
                .build();

        assertThrows(IllegalArgumentException.class, removeAddressCommand::validate);
    }
}
