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

import by.zloy.model.Machine;
import by.zloy.model.Order;
import by.zloy.service.MachineProvider;
import by.zloy.service.OrderProvider;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

@Path("/")
@RequestScoped
public class CoffeeResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    @Inject
    private OrderProvider orderProvider;

    @Inject
    private MachineProvider machineProvider;

    public CoffeeResource() {
        super();
    }

    @Path("machines")
    @GET
    @Operation(summary = "Get available coffee machines", description = "Find best coffee machine for you and remember the name.")
    @APIResponses({
            @APIResponse(name = "normal",
                    responseCode = "200",
                    description = "Returns available coffee machines",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JsonResponse.class))),
            @APIResponse(name = "invalid request", responseCode = "400"),
            @APIResponse(name = "unauthorized", responseCode = "401"),
            @APIResponse(name = "not found", responseCode = "404"),
            @APIResponse(name = "internal error", responseCode = "500")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoffeeMachines() {
        List<Machine> machines = machineProvider.getAll();

        String msg = "You can choose any suitable coffee machine and make a new order.";
        return Response.ok().entity(json(msg, machines)).build();
    }

    @Path("orders")
    @POST
    @Operation(summary = "Create new order", description = "For ordering you should choose coffee from list [Cappuccino, Latte, Espresso] and coffee machine name.")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(name = "createOrder",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = SchemaType.STRING, example = "{\"coffee\" : \"Latte\", \"machine\" : \"Hulk\"}")))
    @APIResponses({
            @APIResponse(name = "normal",
                    responseCode = "200",
                    description = "Order successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JsonResponse.class))),
            @APIResponse(name = "invalid request", responseCode = "400"),
            @APIResponse(name = "unauthorized", responseCode = "401"),
            @APIResponse(name = "not found", responseCode = "404"),
            @APIResponse(name = "internal error", responseCode = "500")
    })
    @io.helidon.security.annotations.Authenticated
    public Response createOrder(@HeaderParam("Authorization") String authHeader, JsonObject jsonObject) {
        String coffeeName = jsonObject.getString("coffee");
        String machineId = jsonObject.getString("machine");
        String id = orderProvider.createOrder(coffeeName, machineId, authHeader);

        String msg = String.format("Create '%s' order with order ID '%s'.", coffeeName, id);
        return Response.ok().entity(json(msg, id)).build();
    }

    @Path("orders/{orderId}")
    @GET
    @Operation(summary = "Get order status", description = "You can see order status by order id.")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(name = "normal",
                    responseCode = "200",
                    description = "Returns order status",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JsonResponse.class))),
            @APIResponse(name = "invalid request", responseCode = "400"),
            @APIResponse(name = "unauthorized", responseCode = "401"),
            @APIResponse(name = "not found", responseCode = "404"),
            @APIResponse(name = "internal error", responseCode = "500")
    })
    @io.helidon.security.annotations.Authenticated
    public Response getOrder(@HeaderParam("Authorization") String authHeader, @PathParam("orderId") String orderId) {
        Order order = orderProvider.getOrder(orderId, authHeader);

        OffsetDateTime readyDateTime = order.getReadyDateTime();
        String msg = (readyDateTime.compareTo(OffsetDateTime.now()) > 0)
                ? String.format("You '%s' will be ready at %s UTC.", order.getCoffee(), getLocalTime(readyDateTime))
                : String.format("You '%s' is ready.", order.getCoffee());
        return Response.ok().entity(json(msg, readyDateTime.toString())).build();
    }

    private String getLocalTime(OffsetDateTime readyDateTime) {
        return readyDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }

    private JsonObject json(String msg, String data) {
        return JSON.createObjectBuilder().add("message", msg).add("payload", data).build();
    }

    private JsonObject json(String msg, List<Machine> objects) {
        final OffsetDateTime now = OffsetDateTime.now();
        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();
        objects.forEach(o -> jsonArrayBuilder.add(JSON.createObjectBuilder()
                .add("Coffee machine", o.getMachineId())
                .add("Kitchen", o.getKitchen())
                .add("Floor", o.getFloor())
                .add("You coffee will be ready in (min)", calculateAvailability(o, now))
        ));
        return JSON.createObjectBuilder().add("message", msg).add("payload", jsonArrayBuilder).build();
    }

    private long calculateAvailability(Machine machine, OffsetDateTime now) {
        OffsetDateTime whenMachineIsAvailable = machine.getAvailability();
        Duration duration = Duration.between(now, whenMachineIsAvailable);
        Integer minutesToMakeCoffee = machine.getVelocity();
        return duration.toMinutes() + minutesToMakeCoffee;
    }

    @SuppressWarnings("unused")
    private static class JsonResponse {

        private String message;
        private Object payload;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getPayload() {
            return payload;
        }

        public void setPayload(Object payload) {
            this.payload = payload;
        }
    }
}
