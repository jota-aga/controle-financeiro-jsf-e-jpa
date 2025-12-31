package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessagesUtil {
	
	public static void addMessageSemId(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public static void addMessageComId(String componenteId, FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(componenteId, new FacesMessage(severity, summary, detail));
    }
}
