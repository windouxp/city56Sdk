package testdemo.com.bluetoothblis.fastble.scan.exception;


public class InitiatedException extends BleException {
    public InitiatedException() {
        super(ERROR_CODE_INITIAL, "Initiated Exception Occurred! ");
    }
}
