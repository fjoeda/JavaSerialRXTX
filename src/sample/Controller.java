package sample;

import gnu.io.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.BufferedReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;



public class Controller implements Initializable {

    public TextField SendSerialText;
    public Button SendButton;
    public TextArea ConsoleText;
    public ComboBox<String> PortList;
    public ComboBox<String> BaudList;
    public Button ConnectButton;


    SerialPort serialPort;

    private BufferedReader input;
    private OutputStream output;

    private String PortName = null;
    private String BaudRate = "9600";

    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    public void SendToSerial(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void RefreshPortList(MouseEvent mouseEvent) {
    }

    public void ConnectToSerial(ActionEvent actionEvent) {
        if(PortName!=null){
            try{
                serialPort = (SerialPort) portId.open(PortName,2000);
                serialPort.setSerialPortParams(Integer.valueOf(BaudRate),8,1,0);
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();
                serialPort.addEventListener(new SerialPortEventListener() {
                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        try {
                            if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){
                                String data = input.readLine();
                                System.out.println(data);
                            }
                        }catch (IOException e){

                        }

                    }
                });
                serialPort.notifyOnDataAvailable(true);
            }catch (Exception e){

            }
        }
    }


    public synchronized void serialEvent(SerialPortEvent event){

    }
}
