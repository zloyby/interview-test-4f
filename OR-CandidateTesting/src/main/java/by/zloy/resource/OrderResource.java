/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package by.zloy.resource;

import by.zloy.jpa.Order;
import by.zloy.service.OrderProvider;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/orders")
@RequestScoped
public class OrderResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    @Inject
    private OrderProvider orderProvider;

    public OrderResource() {
        super();
    }

    @Path("/{orderId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") String orderId) {
        Order order = orderProvider.getOrder(orderId);

        String msg = String.format("You '%s coffee' order status is %s%%.", order.getCoffee(), order.getProgress());
        return Response.ok().entity(createResponse(msg, order.getProgress().toString())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(name = "createOrder",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = SchemaType.STRING, example = "{\"coffee\" : \"Latte\"}")))
    @APIResponses({
            @APIResponse(name = "normal", responseCode = "204", description = "Order created"),
            @APIResponse(name = "invalid JSON", responseCode = "400")
    })
    public Response createOrder(JsonObject jsonObject) {
        String coffeeName = jsonObject.getString("coffee");
        String id = orderProvider.createOrder(coffeeName);

        String msg = String.format("You '%s coffee' order can be checked by ID '%s'.", coffeeName, id);
        return Response.ok().entity(createResponse(msg, id)).build();
    }

    private JsonObject createResponse(String msg, String data) {
        return JSON.createObjectBuilder().add("message", msg).add("data", data).build();
    }
}
