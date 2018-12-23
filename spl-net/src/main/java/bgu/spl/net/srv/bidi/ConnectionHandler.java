package bgu.spl.net.srv.bidi;

import java.io.Closeable;

/**
 * The ConnectionHandler interface for Message of type T
 */
public interface ConnectionHandler<T> extends Closeable {
    void send(T msg) ;
}
