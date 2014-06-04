package com.tennissetapp.form;

public class PostMessageForm extends AbstractForm{
	private static final long serialVersionUID = 1L;

	public String toUserAccountId;
	public String message;
    public String token;

//    tennissetapp I/GcmUtils﹕ SUCCESS! REGID IS APA91bFAD3zbyURohBfalTwCudULgZgRzjadjbY4ZW9lUZh7jcVrAxM10iWMsalxL0Px18r48NSZElRrMCrs46AGplWqL21HFQc1hI_0646fNtBAiskReP852uM2ahVk5Eb67SZwC6rdOgwRavNOyh6_5ZT6ItrASA
//    05-21 05:17:54.543    1900-1900/com.tennissetapp D/GcmUtils﹕ onPostExecute Device registered, registration ID=APA91bFAD3zbyURohBfalTwCudULgZgRzjadjbY4ZW9lUZh7jcVrAxM10iWMsalxL0Px18r48NSZElRrMCrs46AGplWqL21HFQc1hI_0646fNtBAiskReP852uM2ahVk5Eb67SZwC6rdOgwRavNOyh6_5ZT6ItrASA


    @Override
    public String toString() {
        return "PostMessageForm{" +
                "toUserAccountId='" + toUserAccountId + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
