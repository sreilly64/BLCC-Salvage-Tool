package sreilly64.com.github.gw2salvagetool.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sreilly64.com.github.gw2salvagetool.services.CommerceService;

@RestController
public class CommerceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private CommerceService commerceService;

    @Autowired
    public CommerceController(CommerceService commerceService) {
        this.commerceService = commerceService;
    }


}
