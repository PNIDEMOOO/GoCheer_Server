package webAPP;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import entity.Achievement;
import entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by orient on 2016/12/11.
 */
public class SendMail  implements  Runnable{

    private User user;
    private Achievement achievement;
    private HttpServletRequest request;
    private  HttpServletResponse response;


    public SendMail(User user, Achievement achievement,HttpServletRequest request, HttpServletResponse response)
    {
        this.achievement=achievement;
        this.user=user;
        this.request=request;
        this.response=response;
    }

    public void run()
    {

        Client client=(Client)request.getSession().getAttribute("client");

        if(client==null) {
            client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("api",
                    "key-77ed9a5a192dd6c5b90591b027c769b1"));
            request.getSession().setAttribute("client",client);
            String alas=user.getAlias();
            String email=user.getEmail();
            String text_title="Dear "+alas+"\n.";
            String text_end="Hope you make persistent efforts!\n";
            request.getSession().setAttribute("alas",alas);
            request.getSession().setAttribute("email",email);
            request.getSession().setAttribute("text_title",text_title);
            request.getSession().setAttribute("text_end",text_end);
        }
        WebResource webResource =
                    client.resource("https://api.mailgun.net/v3/gocheer.donggu.me/messages");
        String alas=(String)request.getSession().getAttribute("alas");
        String email=(String)request.getSession().getAttribute("email");
        String text_title=(String )request.getSession().getAttribute("text_title");

        String text_content="Congratulations on obtaining the "
                +(achievement.isHidden()?"hidden ":"")+"achievement "
                +achievement.getName()+":\n "+achievement.getDescription()+"\n";

        String text_end=(String)request.getSession().getAttribute("text_end");

        String text=text_title+text_content+text_end;

        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "GoCheer Achievements <postmaster@gocheer.donggu.me>");
        formData.add("to", email);
        formData.add("subject", "New GoCheer achievement: "+achievement.getName()+"\n.");
        formData.add("text",text);

        webResource.post(ClientResponse.class,formData);
    }

    public static void SendCongratulations(User user, Achievement achievement,HttpServletRequest request, HttpServletResponse response){
          new Thread(new SendMail(user,achievement,request,response)).start();
    }

}
