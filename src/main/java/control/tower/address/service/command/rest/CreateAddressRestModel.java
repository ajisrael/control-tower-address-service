package control.tower.address.service.command.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationContext;

@Getter
@NoArgsConstructor
public class CreateAddressRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Street is a required field")
    private String street;
    @NotBlank(message = "City is a required field")
    private String city;
    @NotBlank(message = "State is a required field")
    private String state;
    @NotBlank(message = "Postal code is a required field")
    private String postalCode;
    @NotBlank(message = "Country is a required field")
    private String country;
}
