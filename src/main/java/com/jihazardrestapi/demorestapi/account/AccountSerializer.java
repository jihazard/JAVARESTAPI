package com.jihazardrestapi.demorestapi.account;

import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;

public class AccountSerializer extends JsonSerializer<Account> {


    @Override
    public void serialize(Account account, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", account.getId());
        gen.writeEndObject();

    }
}
