package sample;

import gnu.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.Enumeration;



public class Controller implements Initializable {

    public TextField SendSerialText;
    public Button SendButton;
    public TextArea ConsoleText;
    public ComboBox<String> PortList;
    public ComboBox<String> BaudList;
    public Button ConnectButton;


    public static SerialPort serialPort;

    private BufferedReader input;
    private OutputStream output;

    private String PortName = null;
    private String BaudRate = null;

    CommPortIdentifier portId = null;
    Enumeration portEnum;

    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
    };

    public void SendToSerial(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PortList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> PortName = newValue);
        BaudList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> BaudRate = newValue);

        // Bukan kode yang layak ditiru
        BaudList.getItems().add("4800");
        BaudList.getItems().add("9600");
        BaudList.getItems().add("19200");
        BaudList.getItems().add("115200");


    }


    public void RefreshPortList(MouseEvent mouseEvent) {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() )
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            PortList.getItems().add(portIdentifier.getName());
            System.out.println(portIdentifier.getName());
        }
    }

    public void ConnectToSerial(ActionEvent actionEvent) throws Exception {
        System.out.println("Clicked");
        if(PortName!=null&&BaudRate!=null){
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(PortName);
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            try{
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(Integer.valueOf(BaudRate),8,1,0);
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();
                serialPort.addEventListener(new SerialPortEventListener() {
                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        try {
                            if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){
                                String data = input.readLine();
                                ConsoleText.appendText(data+System.lineSeparator());
                                System.out.println(data);
                            }
                        }catch (IOException e){

                        }

                    }
                });
                serialPort.notifyOnDataAvailable(true);
            }catch (Exception e){

            }
        }else{
            new Alert(Alert.AlertType.ERROR,"You haven't select any COM port or baud rate or both").showAndWait();
        }
    }



}
