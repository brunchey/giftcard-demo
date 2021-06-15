package io.axoniq.demo.giftcard.query;


import io.axoniq.demo.giftcard.api.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.Instant;

@Profile("query")
@Service
@ProcessingGroup("card-transaction")
public class CardTransactionProjection {

    private final EntityManager entityManager;

    public CardTransactionProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler
    public void on(IssuedEvent event, @Timestamp Instant txDate) {
        entityManager.persist(new CardTransaction(0, event.getId(), event.getAmount(), txDate));
    }

    @EventHandler
    public void on(RedeemedEvent event, @Timestamp Instant txDate) {
        entityManager.persist(new CardTransaction(0, event.getId(), event.getAmount()* -1, txDate));
    }

}
