package com.softamo.gitter.cli;

import com.softamo.gitter.restapi.BlockingGitterApi;
import com.softamo.gitter.restapi.DefaultBlockingGitterApi;
import com.softamo.gitter.restapi.ManualBlockingGitterClient;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "gitter-cli", description = "Sends a message to a Gitter Room",
        mixinStandardHelpOptions = true)
public class GitterCliCommand implements Runnable {
    @Option(names = {"-t", "--token"}, description = "Gitter Token", required = true, interactive = true)
    String token;

    @Option(names = {"-r", "--room"}, description = "Gitter Room", required = true)
    String room;

    @Option(names = {"-m", "--message"}, description = "Message to send", required = true)
    String message;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(GitterCliCommand.class, args);
    }

    public void run() {
        BlockingGitterApi api = new DefaultBlockingGitterApi(() -> this.token, new ManualBlockingGitterClient());
        api.sendMessage(room, message);
    }
}
