package com.gwf.work.mode;

import com.gwf.work.entity.ToEmail;

/**
 * @author GWF
 *
 */

public interface SendEmail {
	public String commonEmail(ToEmail toEmail);
	public String htmlEmail(ToEmail toEmail) ;
}
