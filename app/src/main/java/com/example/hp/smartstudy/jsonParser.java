package com.example.hp.smartstudy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class jsonParser {
    public final static String ip = "192.168.42.184";
    private final static String NAMESPACE = "http://tempuri.org/";
    private final static String URL = "http://" + ip + "/smartstudy/WebService.asmx";
    public final static String FILE_URL = "http://" + ip + "/smartstudy/MaterialFile/";
    public final static String FACULTY_IMAGE_URL = "http://" + ip + "/smartstudy/FacultyImages/";

    private final static String SOAP_ACTION = "http://tempuri.org/";
    public static String responseJson = "";

    public static String invokeJSON(String methodName) {
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + methodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to static variable
            responseJson = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    public static String invokeJSON(String methodName, String varname,
                                    String valuename) {
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        PropertyInfo info = new PropertyInfo();
        info.setName(varname);
        info.setValue(valuename);
        info.setType(String.class);
        request.addProperty(info);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + methodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to static variable
            responseJson = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }
}
