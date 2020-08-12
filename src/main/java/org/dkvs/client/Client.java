package org.dkvs.client;

import org.dkvs.client.handler.AsyncResponseHandler;
import org.dkvs.client.handler.ResponseHandler;

public interface Client<Req, Res> {
    void send(Req request, ResponseHandler<Res> responseHandler);
    void send(Req request, AsyncResponseHandler<Res> responseHandler);
}
