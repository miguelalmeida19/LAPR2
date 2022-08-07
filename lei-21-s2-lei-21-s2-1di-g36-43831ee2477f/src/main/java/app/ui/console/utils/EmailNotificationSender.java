package app.ui.console.utils;

import java.io.*;

public class EmailNotificationSender {

    public EmailNotificationSender() {
    }

    public void sendPasswordNotification(String name, String email, String password) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("./emailAndSMSMessages.txt", true)));
        pw.write("\n╔═══━━━───        •        ───━━━═══╗");
        pw.write("\n     User's Name: " + name);
        pw.write("\n •············· Login ·············•");
        pw.write("\n [Email]: " + email);
        pw.write("\n [Password]: " + password + "\n");
        pw.write("╚═══━━━───        •        ───━━━═══╝");
        pw.close();
    }

    public void sendTestDoneNotification() throws FileNotFoundException, UnsupportedEncodingException {
        OutputStream clientData = new FileOutputStream("./emailAndSMSMessages.txt");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientData, "UTF-8"));
        pw.write("\n╔═══━━━───            •            ───━━━═══╗");
        pw.write("\n   Hello!");
        pw.write("\nYour Test Results are now Available!");
          pw.write("\n╚═══━━━───            •            ───━━━═══╝");
        pw.close();
    }
}
