package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

public class SerialConn implements SerialPortEventListener{

    SerialPort serialPort;

    private BufferedReader input;
    private OutputStream output;

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }


    public void initialize(String portName, int baudRate){

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        try{
            serialPort = (SerialPort) portId.open(portName,2000);
            serialPort.setSerialPortParams(9600,8,1,0);



        }catch (Exception e){

        }
    }
}
