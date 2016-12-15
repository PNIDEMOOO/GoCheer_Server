package webAPP;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;

/**
 * Created by orient on 2016/12/11.
 */
public class SendMail {
    public static ClientResponse SendSimpleMessage() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api",
                "key-77ed9a5a192dd6c5b90591b027c769b1"));
        WebResource webResource =
                client.resource("https://api.mailgun.net/v3/gocheer.donggu.me/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "Mailgun Sandbox <postmaster@gocheer.donggu.me>");
        formData.add("to", "hongyu <452937660@qq.com>");
        formData.add("subject", "账户激活邮件");
        formData.add("text", "Congratulations dongg, you just sent an email with Mailgun!  You are truly awesome!  You can see a record of this email in your logs: https://mailgun.com/cp/log");
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
    }

    public static void main(String[] args) {
        ClientResponse clientResponse=SendSimpleMessage();
        if(clientResponse!=null)
            System.out.println("Send Sucessfully!");
        else
            System.out.println("Failure!");
    }
}
