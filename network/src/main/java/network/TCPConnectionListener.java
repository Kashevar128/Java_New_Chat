package network;

import messageDTO.Message;

import java.net.SocketException;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceivePackage(TCPConnection tcpConnection, Message msg);
    void onDisconnect(TCPConnection tcpConnection) throws SocketException;
    void onException(TCPConnection tcpConnection, Exception e);
    void onSendPackage(TCPConnection tcpConnection, Message msg);
}
