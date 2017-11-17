package net.bhtech.lygmanager.net.cxfweservice.convert;

import net.bhtech.lygmanager.net.cxfweservice.ksoap2.SoapEnvelope;
import net.bhtech.lygmanager.net.cxfweservice.ksoap2.serialization.SoapObject;
import net.bhtech.lygmanager.net.cxfweservice.ksoap2.serialization.SoapPrimitive;
import net.bhtech.lygmanager.net.cxfweservice.ksoap2.transport.SoapHelper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhangxinbiao on 2017/11/17.
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        SoapEnvelope soapEnvelope = SoapHelper.getInstance().getSoapEnvelope();
        SoapHelper.getInstance().parseResponse(soapEnvelope,value.byteStream());
        SoapObject resultsRequestSOAP = (SoapObject) soapEnvelope.bodyIn;
        SoapPrimitive obj = (SoapPrimitive) resultsRequestSOAP.getProperty(0);
        try {
            return obj.toString();
        } finally {
            value.close();
        }
    }
}