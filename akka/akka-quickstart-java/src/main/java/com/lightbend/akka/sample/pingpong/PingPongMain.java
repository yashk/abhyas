package com.lightbend.akka.sample.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class PingPongMain {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("PingPongMain");

        try {

            ActorRef pingActor = system.actorOf(PingPongActor.props("PingActor"));

            ActorRef pongActor = system.actorOf(PingPongActor.props("PongActor"));

            pongActor.tell(new PingPongActor.Ping(),pingActor);

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        }catch (IOException ioe){

        }finally {
            system.terminate();
        }
    }
}
