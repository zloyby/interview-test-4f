/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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
package by.zloy.service;

import by.zloy.model.Coffee;
import by.zloy.model.Machine;
import by.zloy.model.Order;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

@Dependent
public class OrderProvider {

    @PersistenceContext(unitName = "coffeeDb")
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRED)
    public Order getOrder(String orderId, String authHeader) {
        final TypedQuery<Order> query = this.entityManager.createNamedQuery("Orders.findByIdAndToken", Order.class);
        query.setParameter("orderId", orderId);
        query.setParameter("token", authHeader);
        return query.getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String createOrder(String coffeeName, String machineId, String authHeader) {
        final TypedQuery<Machine> query = this.entityManager.createNamedQuery("Machines.findById", Machine.class);
        query.setParameter("machineId", machineId);
        Machine machine = query.getSingleResult();
        OffsetDateTime availability = machine.getAvailability().plus(machine.getVelocity(), MINUTES);
        machine.setAvailability(availability);
        machine = this.entityManager.merge(machine);

        final Order order = new Order(
                UUID.randomUUID().toString(),
                Coffee.valueOf(coffeeName),
                availability,
                machine,
                authHeader);
        this.entityManager.persist(order);
        return order.getOrderId();
    }
}
