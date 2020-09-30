package sreilly64.com.github.gw2salvagetool.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sreilly64.com.github.gw2salvagetool.services.ProfitService;

@RestController
public class ProfitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private ProfitService profitService;

    @Autowired
    public ProfitController(ProfitService profitService) {
        this.profitService = profitService;
    }


}
