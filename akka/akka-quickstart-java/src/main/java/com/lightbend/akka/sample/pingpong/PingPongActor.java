package com.lightbend.akka.sample.pingpong;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Random;

public class PingPongActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final String name;
    private final Random r = new java.util.Random(System.nanoTime());


    public PingPongActor(String name) {
        this.name = name;
    }


    static public Props props(String name) {
        return Props.create(PingPongActor.class, () -> new PingPongActor(name));
    }

    // messages


    public static final class Ping {
        @Override
        public String toString() {
            return "Ping";
        }
    }


    public static final class Pong {
        @Override
        public String toString() {
            return "Pong";
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Ping.class, ping -> {
                    log.info(name + " " + ping.toString());
                    sleep(20);
                    getSender().tell(new Pong(), getSelf());


                }).match(Pong.class, pong -> {
                    log.info(name + " " + pong.toString());
                    sleep(20);
                    getSender().tell(new Ping(), getSelf());

                })
                .build();
    }


    public void sleep(int sec) {
        try {
            int rSec = r.nextInt(sec);
            log.info("sleeing for " + rSec);
            Thread.sleep(rSec * 1000);
        } catch (InterruptedException e) {
        }
    }

}
