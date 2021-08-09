package com.bootcamp.msPaymentCredit.handler;

import com.bootcamp.msPaymentCredit.models.entities.PaymentCard;
import com.bootcamp.msPaymentCredit.services.ICreditCardDTOService;
import com.bootcamp.msPaymentCredit.services.IPaymentCardService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j(topic = "payment_handler")
@Component
public class PaymentCardHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCardHandler.class);

    @Autowired
    private IPaymentCardService service;

    @Autowired
    private ICreditCardDTOService creditService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), PaymentCard.class);
    }

    public Mono<ServerResponse> findPaymentCard(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.findById(id).flatMap((c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c))
                .switchIfEmpty(ServerResponse.notFound().build()))
        );
    }

    public Mono<ServerResponse> newPaymentCard(ServerRequest request){

        Mono<PaymentCard> paymentCardMono = request.bodyToMono(PaymentCard.class);


        return paymentCardMono.flatMap( paymentRequest -> creditService.findByPan(paymentRequest.getIdentityNumber())
                .flatMap(credit -> {
                if(credit.getTotalConsumption()>paymentRequest.getAmount()) {
                    credit.setTotalConsumption(credit.getTotalConsumption() - paymentRequest.getAmount());

                }else {
                    credit.setBalanceAmount(paymentRequest.getAmount()-credit.getTotalConsumption());
                    credit.setTotalConsumption(0.0);
                }
                    return creditService.updateCredit(credit);
                }).flatMap(payment ->  service.create(paymentRequest)))
                .flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)));
    }

    public Mono<ServerResponse> deletePaymentCard(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<PaymentCard> paymentCardMono = service.findById(id);

        return paymentCardMono
                .doOnNext(c -> LOGGER.info("delete Paymencard: PaymentCardId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updatePaymentCard(ServerRequest request){
        Mono<PaymentCard> paymentCardMono = request.bodyToMono(PaymentCard.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(paymentCardMono, (db,req) -> {
                    db.setAmount(req.getAmount());
                    return db;
                }).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.create(c),PaymentCard.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
