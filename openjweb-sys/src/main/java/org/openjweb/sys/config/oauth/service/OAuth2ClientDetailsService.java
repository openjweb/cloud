package org.openjweb.sys.config.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommSsoClientApp;
import org.openjweb.core.service.CommSsoClientAppService;
import org.openjweb.sys.config.oauth.model.OAuth2ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OAuth2ClientDetailsService implements ClientDetailsService
{
    @Autowired
    private CommSsoClientAppService clientService;

    @Override
    public ClientDetails loadClientByClientId(String account) throws ClientRegistrationException
    {
        log.info("调用查询loadClientByClientId。。。。。。。。。");
        CommSsoClientApp client = clientService.selectByAccountId(account);
        if (client == null)
        {
            throw new ClientRegistrationException("企业客户未注册！");
        }

        return new OAuth2ClientDetails(client);
    }
}
